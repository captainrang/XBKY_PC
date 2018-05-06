package ikaoyaner.util.mail;

import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import ikaoyaner.util.ServiceLocator;
import ikaoyaner.util.common.StringUtils;



/**
 * 类名称:  MailUtil
 * 创建人:  Bukaa
 * 创建时间: 2017-08-30 上午9:20:16 
 * @version  V1.0  
 */
public class MailUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	/**
	 * sendMail
	 * not support attachedFile
	 * @param from
	 * @param subject
	 * @param targetAddress
	 * @param model
	 * @return
	 */
	public static void sendMail(String from, String subject, String targetAddress, HashMap<String, Object> model){
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl)ServiceLocator.getBean("mailSender");
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		// 设置收件人邮箱
		String[] toEmailArray = targetAddress.split(",");
		List<String> toEmailList = new ArrayList<String>();
		if (null == toEmailArray || toEmailArray.length <= 0) {
			throw new RuntimeException("收件人邮箱不得为空！");
		} else {
			for (String s : toEmailArray) {
				if (StringUtils.isNotEmpty(s)) {
					toEmailList.add(s);
				}
			}
		}
		//设置默认参数
		if(StringUtils.isEmpty(from)){
			from = "小白考研技术团队";
		}
		if(StringUtils.isEmpty(subject)){
			subject = "小白考研邮件通知";
		}
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, false, "UTF-8");
			
            String text = geFreeMarkerTemplateContent(model);
            helper.setText(text, true);
			
			helper.setTo(toEmailArray);
			helper.setSubject(subject);
			helper.setFrom(new InternetAddress(mailSender.getUsername(), from, "UTF-8"));
			mailSender.send(mimeMsg);
			logger.info("成功发送邮件至：" + targetAddress);
		} catch (Exception e) {
			logger.error("发送邮件失败，错误信息："+e.getMessage());
		}
	}
	
	/**
	 * sendMail with SSL
	 * not support attachedFile
	 * @param from
	 * @param subject
	 * @param targetAddress
	 * @param model
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static boolean sslSendMail(String from, String subject, String targetAddress, HashMap<String, Object> model){
		
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl)ServiceLocator.getBean("mailSender");
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", mailSender.getHost());
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		final String username = mailSender.getUsername();
		final String password = mailSender.getPassword();
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		//设置默认参数
		if(StringUtils.isEmpty(from)){
			from = "小白考研技术团队";
		}
		if(StringUtils.isEmpty(subject)){
			subject = "小白考研邮件通知";
		}
		Message msg = new MimeMessage(session);
		try{
			// 设置发件人和收件人
			msg.setFrom(new InternetAddress(mailSender.getUsername(), from, "UTF-8"));
			String[] toEmailArray = targetAddress.split(",");
			Address to[] = new InternetAddress[toEmailArray.length];
			for (int i = 0; i < toEmailArray.length; i++) {
				to[i] = new InternetAddress(toEmailArray[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject); 
			// 设置内容
			String htmlContent = geFreeMarkerTemplateContent(model);
			Multipart multipart = new MimeMultipart();
	        if (htmlContent != null){
	            BodyPart htmlPart = new MimeBodyPart();
//	            BodyPart imgaePart = new MimeBodyPart();
//	            imgaePart.setFileName("http://ontxmqd6x.bkt.clouddn.com/xbky/logo.png");
	            htmlPart.setContent(htmlContent, "text/html;charset=UTF-8");
	            multipart.addBodyPart(htmlPart);
	        }
	        msg.setContent(multipart);
	        
			msg.setSentDate(new Date());
			Transport.send(msg);
			logger.info("成功发送邮件(SSL)至：" + targetAddress);
			return true;
		} catch(Exception e){
			e.printStackTrace();
			logger.error("发送邮件(SSL)失败，错误信息："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * sendMail with SSL
	 * not support attachedFile
	 * @param from
	 * @param subject
	 * @param targetAddress
	 * @param model
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static boolean sslSendMailByContent(String from, String subject, String targetAddress, String content){
		
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl)ServiceLocator.getBean("mailSender");
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", mailSender.getHost());
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		final String username = mailSender.getUsername();
		final String password = mailSender.getPassword();
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		//设置默认参数
		if(StringUtils.isEmpty(from)){
			from = "小白考研技术团队";
		}
		if(StringUtils.isEmpty(subject)){
			subject = "小白考研邮件通知";
		}
		Message msg = new MimeMessage(session);
		try{
			// 设置发件人和收件人
			msg.setFrom(new InternetAddress(mailSender.getUsername(), from, "UTF-8"));
			String[] toEmailArray = targetAddress.split(",");
			Address to[] = new InternetAddress[toEmailArray.length];
			for (int i = 0; i < toEmailArray.length; i++) {
				to[i] = new InternetAddress(toEmailArray[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject); 
			// 设置内容
			String htmlContent = content;
			Multipart multipart = new MimeMultipart();
	        if (htmlContent != null){
	            BodyPart htmlPart = new MimeBodyPart();
	            htmlPart.setContent(htmlContent, "text/html;charset=UTF-8");
	            multipart.addBodyPart(htmlPart);
	        }
	        msg.setContent(multipart);
	        
			msg.setSentDate(new Date());
			Transport.send(msg);
			logger.info("成功发送邮件(SSL)至：" + targetAddress);
			return true;
		} catch(Exception e){
			e.printStackTrace();
			logger.error("发送邮件(SSL)失败，错误信息："+e.getMessage());
			return false;
		}
	}
	
	public static String geFreeMarkerTemplateContent(Map<String, Object> model){
        try
        {
        	return StringUtils.geFreeMarkerTemplateContent("emailTemplate.html", model);
        }
        catch (Exception e)
        {
            logger.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
        }
        return "";
    }
	
	
}
