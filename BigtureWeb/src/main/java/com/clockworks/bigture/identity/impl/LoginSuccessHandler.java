package com.clockworks.bigture.identity.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.clockworks.bigture.Role;
import com.clockworks.bigture.SimpleResult;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.entity.DeviceType;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserStatus;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.ui.IErrorCode;




public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication arg2) throws IOException,
			ServletException {
		
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ILogin login = (ILogin)applicationContext.getBean("UserService");
		User currentUser = login.getCurrentUser();
		
		boolean isMobile = null == request.getParameter("osType") ? false : true;
		
		if(currentUser.getStatus().equals(UserStatus.ACTIVE)){
		
			login.updateLastLoginDate(currentUser);
			
			if(isMobile || "true".equals(request.getHeader("mobile-call")) || "true".equals(request.getHeader("ajax-call"))){
				String deviceTypeStr = request.getParameter("deviceType");
				String osVersion = request.getParameter("osVersion");
				String appVersion = request.getParameter("appVersion");
				String osTypeStr = request.getParameter("osType");
			
				login.updateLoginData(currentUser, new Date(), DeviceType.valueOf(deviceTypeStr),OsType.valueOf(osTypeStr), osVersion, appVersion);
				
				//여기에서 사용자의 정보를 리턴한다.
				UserDelegate userInfo = new UserDelegate(currentUser);
				
				String result = CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,userInfo);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(result);
				response.getWriter().flush();
				return;
			}else{
				String nextUrl = "";
				if(login.isInRole(currentUser, Role.USER_ROLE)){
					nextUrl = "/";
					
				}else{
					//Admin페이지로 보낸다.
					nextUrl = "/admin/home";
				}
				
				response.sendRedirect(request.getContextPath() + nextUrl);
			}
		}else{
			//TODO 내부적으로 로그아웃처리를 해야한다.
			
			if(isMobile || "true".equals(request.getHeader("mobile-call")) || "true".equals(request.getHeader("ajax-call"))){
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(CommonUtils.toJsonResult(false,IErrorCode.ERROR_NOT_ACTIVE,null));
				response.getWriter().flush();
				return;
			
			}else{
				//Inactive 페이지로 보내야한다.
				
				
				
			}
		}
	}

}
