package com.clockworks.bigture.identity.impl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class AjaxSessionTimeoutFilter implements Filter{

	private String ajaxHaeder = "AJAX";

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if(isAjaxRequest(req)) {
                try {
                	chain.doFilter(req, res);
                } catch (AccessDeniedException e) {
                    res.sendError(HttpServletResponse.SC_FORBIDDEN);
                } catch (AuthenticationException e) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
        } else
        	chain.doFilter(req, res);
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		boolean isMobile = null == req.getParameter("osType") ? false : true;
		
		return isMobile;
		//return req.getHeader(ajaxHaeder) != null && req.getHeader(ajaxHaeder).equals(Boolean.TRUE.toString());
	}


	public void init(FilterConfig filterConfig) throws ServletException {}

	/**
	 * Set AJAX Request Header (Default is AJAX)
	 * @param ajaxHeader
	 */
	public void setAjaxHaeder(String ajaxHeader) {
		this.ajaxHaeder = ajaxHeader;
	}

}
