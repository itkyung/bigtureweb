package com.clockworks.bigture.ui;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.common.FileUploadOneCommand;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.delegate.CareerDelegate;
import com.clockworks.bigture.delegate.NewsDelegate;
import com.clockworks.bigture.delegate.ProfileDelegate;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.News;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;

/**
 * Profile관련 
 * @author itkyung
 *
 */
@Controller
@RequestMapping(value="/mobile/profile")
public class MobileProfileController  {
	@Autowired private ILogin login;
	@Autowired private IUserService userService;
	@Autowired private IImageService imageService;

	private final int NEWS_THUMB_WIDTH = 100;
	
	
	private Log log = LogFactory.getLog(MobileProfileController.class);
	
	@RequestMapping(value="/getProfile",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getProfile(@RequestParam("userId") Long userId){
		User currentUser = login.getCurrentUser();
		
		User target = userService.loadUser(userId);
		
		ProfileDelegate userInfo = new ProfileDelegate(target);
		if(currentUser != null && currentUser.equals(target)){
			userInfo.setLikeYou(true);
			userInfo.setGroupCount(userService.getCountGroup(currentUser));
		}else if(currentUser == null){
			userInfo.setLikeYou(false);
			userInfo.setGroupCount(0);
		}else{
			userInfo.setLikeYou(userService.alreadyLikeYou(currentUser, userId));
			userInfo.setGroupCount(0);
		}
		
		
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, userInfo);
	}
	
	
	@RequestMapping(value="/saveCareer",produces="application/json;charset=utf-8")
	@ResponseBody
	public String saveCareer(@RequestParam(value="id",required=false) Long id,
			@RequestParam("content") String content,
			@RequestParam("startTime") String startTime,
			@RequestParam(value="endTime",required=false) String endTime){
		User currentUser = login.getCurrentUser();
		try{
			userService.saveCareer(currentUser, id, content, startTime, endTime);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/deleteCareer",produces="application/json;charset=utf-8")
	@ResponseBody
	public String deleteCareer(@RequestParam(value="id") Long id){
		User currentUser = login.getCurrentUser();
		
		try{
			userService.deleteCareer(currentUser, id);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	
	@RequestMapping(value="/listCareer",produces="application/json;charset=utf-8")
	@ResponseBody
	public String listCareer(@RequestParam(value="userId") Long userId){
		
		List<Career> results = userService.findCareer(userId);
		List<CareerDelegate> dels = new ArrayList<CareerDelegate>();
		for(Career c : results){
			dels.add(new CareerDelegate(c));
		}
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, dels);
	}
	
	@RequestMapping(value="/listNews",produces="application/json;charset=utf-8")
	@ResponseBody	
	public String listNews(@RequestParam(value="userId") Long userId){
		
		List<News> results = userService.findNews(userId);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/listNewsImages",produces="application/json;charset=utf-8")
	@ResponseBody
	public String listNewsImages(@RequestParam("newsId") Long newsId){
		News news = userService.loadNews(newsId);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, news.getImages());
	}
	
	@RequestMapping(value="/uploadNewsImage",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadNewsImage(@ModelAttribute("anyFile") FileUploadOneCommand command,
			BindingResult errors){
		User currentUser = login.getCurrentUser();
		
		try{
			TempImage tmp = imageService.saveImage(currentUser, command.getAnyFile(), 100, 0, command.getLocalPath());
			
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, tmp);
		
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/saveNews",produces="application/json;charset=utf-8")
	@ResponseBody
	public String saveNews(@ModelAttribute("news") NewsDelegate newsDelegate){
		User currentUser = login.getCurrentUser();
		
		try{
			userService.saveNews(currentUser, newsDelegate);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/deleteNews",produces="application/json;charset=utf-8")
	@ResponseBody	
	public String deleteNews(@RequestParam("newsId") Long newsId){
		User currentUser = login.getCurrentUser();
		
		try{
			userService.deleteNews(currentUser, newsId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
	}
	
}
