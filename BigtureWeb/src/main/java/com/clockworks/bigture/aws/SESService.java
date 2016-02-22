package com.clockworks.bigture.aws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.*;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.clockworks.bigture.common.IScripting;
import com.clockworks.bigture.common.ResourceUtil;
import com.clockworks.bigture.external.MailService;


public class SESService implements ISESService {
	@Qualifier("velocity") @Autowired(required=false) private IScripting scripting;
	private Log log = LogFactory.getLog(MailService.class);
	private String sender;
	private String senderName;
	private String accessKey;
	private String secretKey;
	
	@Override
	public void sendEmail(String email, String title, String templateName,
			Map<String, Object> vars) throws Exception {

		try {
			
			List<String> toAddresses = new ArrayList<String>();
	        toAddresses.add(email);
	        Destination dest = new Destination().withToAddresses(toAddresses);
	       // request.setDestination(dest);
			
	        String contents = getTemplateContents(templateName + ".vm");
	        String result = (String) scripting.evaluate(contents, vars);
	        
	        
	        Content subjContent = new Content().withData(title);
	        Message msg = new Message().withSubject(subjContent);
	        
	        Content htmlContent = new Content().withData(result);
	        Body body = new Body().withHtml(htmlContent);
	        msg.setBody(body);
	        
	       // request.setMessage(msg);
	        
	        SendEmailRequest request = new SendEmailRequest(sender, dest, msg);
	        
	        
	        
            // Set AWS access credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(
                    new BasicAWSCredentials(accessKey,secretKey));
	        
            Region region = Region.getRegion(Regions.US_WEST_2);
            client.setRegion(region);
            
            client.sendEmail(request);
			
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

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
}
