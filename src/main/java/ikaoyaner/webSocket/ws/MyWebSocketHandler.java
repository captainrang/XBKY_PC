package ikaoyaner.webSocket.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ikaoyaner.webSocket.entity.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by cyx  on 2018/3/30
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {
    public static final Map<String, WebSocketSession> userSocketSessionMap;
    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }
    /**
     * 建立连接后
     */
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String sessionId = (String) session.getAttributes().get("sessionId");
        if (userSocketSessionMap.get(sessionId) == null) {
            userSocketSessionMap.put(sessionId, session);
        }
    }
    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(message.getPayloadLength()==0)return;
        Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
        msg.setDate(new Date());
        sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
    }
    /**
     * 消息传输错误处理
     */
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
// 移除Socket会话
        while (it.hasNext()) {
            Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }
    }
    /**
     * 关闭连接后
     */
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        System.out.println("Websocket:" + session.getId() + "已经关闭");
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
// 移除Socket会话
        while (it.hasNext()) {
            Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }
    }
    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void broadcast(final TextMessage message) throws IOException {
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
// 多线程群发
        while (it.hasNext()) {
            final Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().isOpen()) {
// entry.getValue().sendMessage(message);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
    /**
     * 给某个用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(String sessionId, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }

    /**
     * 判断这个用户 是否登录过 已记录websocket
     * @param sessionId
     * @return
     */
    public Boolean isIn(String sessionId){
        Boolean flag = false;
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        while (it.hasNext()) {
            final Entry<String, WebSocketSession> entry = it.next();
            String key = entry.getKey();
            if(sessionId.equals(key) &&entry.getValue().isOpen()) flag = true; break;
        }
        return flag;
    }
}