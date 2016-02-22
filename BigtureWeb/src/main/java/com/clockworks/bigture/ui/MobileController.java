package com.clockworks.bigture.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.IArtClassService;
import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.IContestService;
import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.IStoryService;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ContestDelegate;
import com.clockworks.bigture.delegate.MainPageDelegate;
import com.clockworks.bigture.delegate.NoticeDelegate;
import com.clockworks.bigture.delegate.NotificationGroupDelegate;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;



/**
 * 모바일 앱에서 전송되어오는 Request를 처리한다.
 * @author itkyung
 *
 */
@Controller
@RequestMapping(value="/mobile")
public class MobileController {
	@Autowired private IArtworkService artworkService;
	@Autowired private IContestService contestService;
	@Autowired private IUserService userService;
	@Autowired private IStoryService storyService;
	@Autowired private IArtClassService artClassService;
	@Autowired private INotificationManager notiManager;
	@Autowired private ILogin login;
	
	@RequestMapping(value="/splashArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String splashArtwork(){
		ArtworkDelegate result = artworkService.getRandomArtwork();
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,result);
	}
	
	
	@RequestMapping(value="/getMainContents",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getMainContents(){
		MainPageDelegate mainDelegate = new MainPageDelegate();
		mainDelegate.setArtworks(artworkService.getSimpleArtwork(2));
		mainDelegate.setContests(contestService.getSimpleContest(1));
		mainDelegate.setExperts(userService.getSimpleExperts(1));
		mainDelegate.setStories(storyService.getSimpleStories(3));
		//mainDelegate.setArtClasses(artClassService.getSimpleClass(2));
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,mainDelegate);
	}
	
	@RequestMapping(value="/getNotifications",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getNotifications(){
		User user = login.getCurrentUser();
		List<NotificationGroupDelegate> results = notiManager.listNotification(user);
		notiManager.updateRead(user);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,results);
	}
	
	/**
	 * 
	 * @param createDate  MM-dd-yyyy 포맷 
	 * @return
	 */
	@RequestMapping(value="/deleteNotifications",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteNotifications(@RequestParam("createDate") String createDate){
		User user = login.getCurrentUser();
		
		notiManager.deleteNotification(user,createDate);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,null);
	}
	
	
	@RequestMapping(value="/findPushSettings",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findPushSettings(){
		User user = login.getCurrentUser();
		
		PushSettings settings = userService.findSetting(user);
		if(settings == null){
			settings = new PushSettings();
			settings.setUser(user);
			settings.setCardPush(true);
			settings.setClassPush(true);
			settings.setContestPush(true);
			settings.setLikePush(true);
			settings.setMyClassPush(true);
			settings.setPicMePush(true);
			settings.setTalkCommentPush(true);
			settings.setStoryPush(true);
			settings.setPushAlert(true);
			userService.updateSettings(settings);
		}
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,settings);
	}
	
	@RequestMapping(value="/listNotice",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listNotice(@RequestParam("lang") String lang){
		
		List<NoticeDelegate> results = notiManager.listNotice(lang);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,results);
	}
	
}
