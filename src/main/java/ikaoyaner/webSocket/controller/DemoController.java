package ikaoyaner.webSocket.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;
import ikaoyaner.util.http.RequestUtil;
import ikaoyaner.webSocket.entity.Message;
import ikaoyaner.webSocket.ws.MyWebSocketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:14
 */
//@RestController
//@RequestMapping(produces = BaseController.CHARSET_UTF8_JSON)
@Controller
@RequestMapping("/chat")
public class DemoController extends BaseController {

    @Resource
    //@Qualifier("ikaoyaner.webSocket.ws.MyWebSocketHandler")
    MyWebSocketHandler handler;


    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest request) {
        String sessionId = request.getSession().getId()  ;
        request.getSession().setAttribute("sessionId",sessionId);
        ModelAndView mv = new ModelAndView("/cp/testChat");
        mv.addObject("sessionId",sessionId);
        return mv;
    }

    // 发布系统广播（群发）
    @ResponseBody
    @RequestMapping(value = "broadcast", method = RequestMethod.POST)
    public void broadcast(String text) throws IOException {
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setFrom("-1");
        msg.setFromName("系统广播");
        msg.setTo("0");
        msg.setText(text);
        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
    }

}
