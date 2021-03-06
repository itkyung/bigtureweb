package com.clockworks.bigture.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;





public class JSPVariableInterceptor extends HandlerInterceptorAdapter {
	@Autowired private ILogin login;
	
	private DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			ModelMap model = modelAndView.getModelMap();
			String uri = request.getRequestURI();
		    String contextPath = request.getContextPath();
		    String subUri = uri.substring(contextPath.length());
		    model.addAttribute("_isMobile",CommonUtils.isGalaxyNote(request));
		    
		    User currentUser = login.getCurrentUser();
		    model.addAttribute("_currentUser", currentUser);
		    
		    model.addAttribute("_imageServerPath","http://" + request.getServerName());
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}
	
}
