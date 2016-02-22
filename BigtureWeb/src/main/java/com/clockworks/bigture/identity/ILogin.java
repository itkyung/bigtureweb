package com.clockworks.bigture.identity;

import java.util.Date;

import com.clockworks.bigture.entity.DeviceType;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.User;





public interface ILogin {
	User loadUser(Long id);
	void updateUser(User user);
	public User getCurrentUser();
	public boolean isInRole(User user,String roleName);
	public void updateLastLoginDate(User user);
	public void updateLoginData(User user,Date lastLoginDate,DeviceType deviceType,OsType osType,String osVersion,String appVersion);
}
