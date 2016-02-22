package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.entity.Notice;
import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.User;

public interface INotificationDAO {
	
	public void create(Notification entity);
	public void update(Notification entity);
	public List<Notification> listNotification(User owner,int start,int limits);
	public Notification load(Long id);
	public void updateReadFlag(User owner);
	public void updateDeleteFlag(User owner,String createString);
	
	public List<Notice> listNotice(String language);
}
