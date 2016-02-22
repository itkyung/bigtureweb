package com.clockworks.bigture.identity.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;



public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ILogin login = (ILogin)applicationContext.getBean("UserService");
		
		Map map = request.getParameterMap();
		if(map.containsKey("id")){
			Long userId = (Long)map.get("id");
			User user = login.loadUser(userId);
			user.setGcmId(null);
			login.updateUser(user);
		}
		
		// 로그아웃이후에 돌아갈 곳으로 redirect시킨다.
		response.sendRedirect(request.getContextPath());
		
	}
}
