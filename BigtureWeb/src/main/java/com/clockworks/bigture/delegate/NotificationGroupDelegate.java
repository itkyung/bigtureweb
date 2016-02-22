package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

import com.clockworks.bigture.entity.Notification;
import com.google.gson.annotations.Expose;

public class NotificationGroupDelegate {
	@Expose
	private String date;	//MM-dd-yyyy
	@Expose
	private List<Notification> notifications = new ArrayList<Notification>();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	
}
