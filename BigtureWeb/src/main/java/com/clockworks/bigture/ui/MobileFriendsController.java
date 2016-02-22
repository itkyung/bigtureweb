package com.clockworks.bigture.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.clockworks.bigture.delegate.CommonListDelegate;
import com.clockworks.bigture.delegate.FriendGroupDelegate;
import com.clockworks.bigture.delegate.SimpleProfileDelegate;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.entity.FriendGroup;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;


@Controller
@RequestMapping(value="/mobile/friends")
public class MobileFriendsController {
	@Autowired ILogin login;
	@Autowired IUserService userService;
	
	final int USER_PAGE_SIZE = 20;
	private Log log = LogFactory.getLog(MobileFriendsController.class);
	
	@RequestMapping(value="/getLikeMeList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getLikeMe(@RequestParam(value="userId",required=false) Long userId,
			@RequestParam(value="regionId",required=false) Long regionId,
			HttpServletResponse response) throws IOException{
		User user = login.getCurrentUser();
		User target = user;
		if(userId != null){
			target = userService.loadUser(userId);
		}
		
		List<User> users = userService.getLikeMe(target,regionId);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, convertDelegate(users));
	}
	
	@RequestMapping(value="/getLikeYouList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getLikeYou(@RequestParam(value="userId",required=false) Long userId,
			@RequestParam(value="regionId",required=false) Long regionId,
			@RequestParam(value="keyword",required=false) String keyword,
			HttpServletResponse response) throws IOException{
		User user = login.getCurrentUser();
		User target = user;
		if(userId != null){
			target = userService.loadUser(userId);
		}
		
		List<User> users = userService.getLikeYou(target,regionId,keyword);
		
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, convertDelegate(users));
	}
	
	private List<SimpleProfileDelegate> convertDelegate(List<User> users){
		List<SimpleProfileDelegate> results = new ArrayList<SimpleProfileDelegate>();
		
		for(User user : users){
			SimpleProfileDelegate delegate = new SimpleProfileDelegate(user);
			delegate.setAlreadyLike(true);
			results.add(delegate);
		}
		
		return results;
	}
	
	@RequestMapping(value="/getFriendGroups",produces = "application/json;charset=utf-8")
	@ResponseBody	
	public String getFriendGroups(){
		User currentUser = login.getCurrentUser();
		
		List<FriendGroup> groups = userService.getGroups(currentUser);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,groups);
	}
	
	@RequestMapping(value="/deleteFriendGroup",produces = "application/json;charset=utf-8")
	@ResponseBody		
	public String deleteFriendGroup(@RequestParam("groupId") Long groupId){
		User currentUser = login.getCurrentUser();
		
		try{
			userService.deleteGroup(groupId, currentUser);
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS,null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION,null);
		}
		
	}
	
	@RequestMapping(value="/saveFriendGroup",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String saveFriendGroup(@RequestParam(value="groupId",required=false) Long groupId,
			@RequestParam("groupName") String groupName){
		User currentUser = login.getCurrentUser();
		
		try{
			userService.saveGroup(groupId, currentUser, groupName);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS,null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION,null);
		}
		
	}
	
	@RequestMapping(value="/getFriendGroupMembers",produces = "application/json;charset=utf-8")
	@ResponseBody	
	public String getFriendGroupMembers(@RequestParam("groupId") Long groupId,
			@RequestParam("page") int page){
		
		User currentUser = login.getCurrentUser();
		
		int start = (page-1) * USER_PAGE_SIZE;
		int limits = USER_PAGE_SIZE;
		
		
		List<SimpleProfileDelegate> friends = userService.getGroupMembers(groupId,start,limits);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, friends);
	}
	
	
	
	@RequestMapping(value="/likeYou",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String likeYou(@RequestParam("taregetUserId") String taregetUserId) throws IOException{
		User user = login.getCurrentUser();
		
		userService.likeYou(user, taregetUserId);
		
		return CommonUtils.toJsonResult(true,  IErrorCode.SUCCESS, null);
		
	}
	
	@RequestMapping(value="/unlike",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String unlike(@RequestParam("taregetUserId") String taregetUserId) throws IOException{
		User user = login.getCurrentUser();
		
		userService.unlike(user, taregetUserId);
		
		return CommonUtils.toJsonResult(true,  IErrorCode.SUCCESS, null);
		
	}
	
	@RequestMapping(value="/searchUsers",produces = "application/json;charset=utf-8")
	@ResponseBody	
	public String searchUsers(@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam("page") int page){
		User user = login.getCurrentUser();
		
		int start = (page-1) * USER_PAGE_SIZE;
		int limits = USER_PAGE_SIZE;
		
		List<SimpleProfileDelegate> results = userService.searchUser(keyword, user, start, limits);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/editGroupMember",produces = "application/json;charset=utf-8")
	@ResponseBody		
	public String editGroupMember(@ModelAttribute("group") FriendGroupDelegate delegate,
			BindingResult result){
		User user = login.getCurrentUser();
		
		try{
			userService.editGroupMember(delegate, user);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}

	@RequestMapping(value="/getGroupAndMembers",produces = "application/json;charset=utf-8")
	@ResponseBody		
	public String getGroupAndMembers(){
		User owner = login.getCurrentUser();
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, userService.findGroupAndMember(owner));
	}
	
	@RequestMapping(value="/searchLikeYous",produces = "application/json;charset=utf-8")
	@ResponseBody	
	public String searchLikeYous(@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam("page") int page){
		User user = login.getCurrentUser();
		
		int start = (page-1) * USER_PAGE_SIZE;
		int limits = USER_PAGE_SIZE;
		
		List<SimpleProfileDelegate> results = userService.searchLikeYous(keyword, user, start, limits);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	
	
}
