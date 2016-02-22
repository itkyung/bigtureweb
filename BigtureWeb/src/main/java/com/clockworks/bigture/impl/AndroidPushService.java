package com.clockworks.bigture.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.clockworks.bigture.IAndroidPush;
import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.User;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service
public class AndroidPushService implements IAndroidPush {
	private String apiKey;
	
	
	
	public String getApiKey() {
		return apiKey;
	}


	@Value("${gcm.server.key}")
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	@Async
	@Override
	public void sendPush(String title,String msg,User receiver){
		if(receiver.getGcmId() == null) return;
		Sender sender = new Sender(apiKey);
		Message message = new Message.Builder().addData("msg",msg).build();
		
		List<String> list = new ArrayList<String>();
		list.add(receiver.getGcmId());
		try {
			MulticastResult multiResult = sender.send(message, list, 5);

			if (multiResult != null) {
			 
				List<Result> resultList = multiResult.getResults();
				for (Result result : resultList) {
					System.out.println(result.getMessageId());
				 
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
