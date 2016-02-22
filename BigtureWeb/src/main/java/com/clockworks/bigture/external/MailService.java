package com.clockworks.bigture.external;

import java.io.IOException;
import java.util.Map;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;

import com.clockworks.bigture.common.IScripting;
import com.clockworks.bigture.common.ResourceUtil;



public class MailService implements IMailService {
	@Qualifier("velocity") @Autowired(required=false) private IScripting scripting;
	private Log log = LogFactory.getLog(MailService.class);
	private String sender;
	private String senderName;
	
	@Async
	@Override
	public void sendEmail(String email, String title,
			String templateName, Map<String, Object> vars) throws Exception {
		//final String userName = "bigture@clockworks.co.kr";
		//final String password = "bigture301";
		final String userName = "hello.bigture@clockworks.co.kr";
		final String password = "bigture1114";
		
		try{
			java.util.Properties props = System.getProperties();
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.auth", "true");
			
			
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName,password);
						}
					});
		 	
			String contents = getTemplateContents(templateName + ".vm");
	        String result = (String) scripting.evaluate(contents, vars);
	        
	        Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject(title);
			message.setContent(result, "text/html;charset=UTF-8");
			
 
			Transport.send(message);
			
		}catch(Exception e){
			log.error("Error :",e);
			throw e;
		}
		

	}

	private String getTemplateContents(String templateName) throws IOException{
		return ResourceUtil.loadResource("META-INF/emailTemplates/" + templateName);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	
}
