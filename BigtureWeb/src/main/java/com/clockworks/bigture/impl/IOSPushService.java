package com.clockworks.bigture.impl;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.clockworks.bigture.IAndroidPush;
import com.clockworks.bigture.IIOsPush;
import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.User;

@Service
public class IOSPushService implements IIOsPush {
	private String apnsHost;
	private String apnsPort;
	private String apnsCertificate;
	private String apnsPassword;
	
	private int maxMessageCharacter = 100;	//최대 100자의 메시지를 보낸다. 
	
	public String getApnsHost() {
		return apnsHost;
	}

	@Value("${apns.host}")
	public void setApnsHost(String apnsHost) {
		this.apnsHost = apnsHost;
	}

	public String getApnsPort() {
		return apnsPort;
	}
	@Value("${apns.port}")
	public void setApnsPort(String apnsPort) {
		this.apnsPort = apnsPort;
	}
	
	public String getApnsCertificate() {
		return apnsCertificate;
	}
	@Value("${apns.certificate}")
	public void setApnsCertificate(String apnsCertificate) {
		this.apnsCertificate = apnsCertificate;
	}

	public String getApnsPassword() {
		return apnsPassword;
	}
	
	@Value("${apns.pw}")
	public void setApnsPassword(String apnsPassword) {
		this.apnsPassword = apnsPassword;
	}
	
	@Async
	@Override
	public void sendPush(String title,String msg,User receiver){
		if(receiver.getGcmId() == null) return;
		try {
			Push.alert(msg,apnsCertificate, apnsPassword, true, receiver.getGcmId());
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
