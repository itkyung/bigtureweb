package com.clockworks.bigture.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.aws.ISESService;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.external.IMailService;
import com.clockworks.bigture.identity.IUserService;
import com.clockworks.bigture.identity.SocialType;

@Controller
@RequestMapping(value="/login")
public class LoginController {
	@Autowired private IUserService userService;
	@Autowired private IMailService mailService;
	@Autowired private ISESService sesService;
	
	private Log log = LogFactory.getLog(LoginController.class);
	
	@RequestMapping(value="/loginForm",method=RequestMethod.GET)
	public String loginForm(Model model){
		
		return "/login/loginForm";
	}
	
	@RequestMapping(value="/initAdmin",method=RequestMethod.GET)
	public String initAdmin(Model model){
		userService.initAdmin();
		return "/init";
	}
	
	@RequestMapping(value="/testSend")
	public String testSend(){
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("name", "TEST");
		vars.put("id","121");
		
		String title = "[Bigture] Please verify your registration";
		String template = "verifyEn";
		
		try{
			sesService.sendEmail("itkyung@gmail.com", "test", template, vars);
		}catch(Exception e){
			log.error("Error ",e);
		}
		return "";
	}
	
	@RequestMapping(value="/updateGCMRegistrationId",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String updateGCMRegistrationId(@RequestParam("userId") Long userId,
			@RequestParam("regId") String regId){
		if(regId == null || "".equals(regId)){
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}
		boolean result = userService.updateRegistrationId(userId, regId);
		if(result){
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}else{
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/checkSnsAccount",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String checkSNSAccount(@RequestParam("socialType") String socialType,
			@RequestParam("socialId") String socialId){
		try{
			User user = userService.findBySocialId(SocialType.valueOf(socialType), socialId);
			if(user != null){
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("loginId", user.getLoginId());
				if(user.getLoginId() == null || user.getLoginId().length() == 0){
					data.put("needInit",true);
				}else{
					data.put("needInit",false);
				}
				return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,data);
			}else{
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_USER_NOT_EXIST, null);
			}
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
}
