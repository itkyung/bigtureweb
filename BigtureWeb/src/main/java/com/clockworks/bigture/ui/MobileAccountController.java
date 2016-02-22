package com.clockworks.bigture.ui;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.IMasterService;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.common.FileUploadOneCommand;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.entity.Country;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;

/**
 * Account관련 Controller
 * @author itkyung
 *
 */
@Controller
@RequestMapping(value="/mobile/account")
public class MobileAccountController{
	private Log log = LogFactory.getLog(MobileAccountController.class);
	
	@Autowired
	private IMasterService masterService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired private ILogin login;
	@Autowired private IImageService imageService;
	
	
	
	@RequestMapping(value="/getCountries",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getCountries() throws IOException{
		
		List<Country> results = masterService.getCountries();
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/registAccount",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String registAccount(@ModelAttribute("userDelegate") UserDelegate delegate,BindingResult result){
		
		synchronized (this) {
			if(userService.findByLoginId(delegate.getLoginId()) != null){
				//로그인 아이디 중복.
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_DUPLICATE, null);
			}
			try{
				User user = userService.registUser(delegate);
				UserDelegate resultUser = new UserDelegate(user);
				return CommonUtils.toJsonResult(true,  IErrorCode.SUCCESS, resultUser);
			}catch(Exception e){
				log.error("Error :",e);
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_MANDATORY, null);
			}
		}
	}
	
	@RequestMapping("/activateUser/{userId}")
	public String activateUser(@PathVariable("userId") String userId,ModelMap model){
		synchronized (this) {
			try{
				User user = userService.activateUser(new Long(userId));
				model.addAttribute("success",true);
				model.addAttribute("user",user);
			}catch(Exception e){
				log.error("Error :",e);
				model.addAttribute("success",false);
			}
		}
		return "activateSuccess";
	}
	
	@RequestMapping("/activateUser/86848ds91919/{userId}")
	public String activateUser2(@PathVariable("userId") String userId,ModelMap model){
		synchronized (this) {
			try{
				User user = userService.activateUser(new Long(userId));
				model.addAttribute("success",true);
				model.addAttribute("user",user);
			}catch(Exception e){
				log.error("Error :",e);
				model.addAttribute("success",false);
			}
		}
		return "activateSuccess";
	}
	
	@RequestMapping(value="/resendActivateLink",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String resendActivateLink(@RequestParam(value="userId",required=false) Long userId,
			@RequestParam(value="loginId",required=false) String loginId
			){
		
		try{
			User user = null;
			if(userId != null){
				user = userService.loadUser(userId);
			}else{
				user = userService.findByLoginId(loginId);
			}
			userService.sendVerifyEmail(user);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN,null);
		}
		
	}
	
	@RequestMapping(value="/resetPassword",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String resetPassword(@RequestParam("loginId") String loginId){
		
		try{
			userService.resetPassword(loginId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_USER_NOT_EXIST, null);
		}
		
	}
	
	@RequestMapping(value="/updateAccount",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String updateAccount(@ModelAttribute("userDelegate") UserDelegate delegate,BindingResult result, HttpServletRequest request){
		User user = login.getCurrentUser();
		
		log.info("한글은? " + delegate.toString());
		
		Enumeration em = request.getHeaderNames();
		while(em.hasMoreElements()){
			String n = (String)em.nextElement();
			String v = request.getHeader(n);
			log.info(n + ":" + v);
		}
			
		
		
		try{
			userService.updateUser(user, delegate);
			UserDelegate userDel = new UserDelegate(user);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, userDel);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/uploadProfileImage",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadProfileImage(@ModelAttribute("anyFile") FileUploadOneCommand command,
			BindingResult errors){
		User currentUser = login.getCurrentUser();
		
		try{
			TempImage tmp = imageService.saveImage(currentUser, command.getAnyFile(), 160, 0, command.getLocalPath());
			
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, tmp);
		
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/changePassword",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String changePassword(@RequestParam("currentPasswd") String currentPasswd,
			@RequestParam("newPasswd") String newPasswd){
		User user = login.getCurrentUser();
		
		try{
			userService.changePassword(user, currentPasswd, newPasswd);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_PASSWORD, null);
		}
		
		
	}
	
}
