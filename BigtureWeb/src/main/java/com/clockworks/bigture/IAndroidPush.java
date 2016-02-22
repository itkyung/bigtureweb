package com.clockworks.bigture;

import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.User;

public interface IAndroidPush {
	public void sendPush(String title,String msg,User receiver);
}
