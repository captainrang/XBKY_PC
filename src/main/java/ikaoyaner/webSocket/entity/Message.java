package ikaoyaner.webSocket.entity;

import java.util.Date;

/**
 * 消息类
 * @author Goofy
 * @Date 2015年6月12日 下午7:32:39
 */
public class Message {

	//发送者
	public String from;
	//发送者名称
	public String fromName;
	//接收者
	public String to;
	//发送的文本
	public String text;
	//发送日期
	public Date date;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
