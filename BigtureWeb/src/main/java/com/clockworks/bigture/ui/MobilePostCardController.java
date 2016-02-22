package com.clockworks.bigture.ui;

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

import com.clockworks.bigture.IPostCardService;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.PostCardDelegate;
import com.clockworks.bigture.delegate.PostCardSendDelegate;
import com.clockworks.bigture.delegate.ReceiverDelegate;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.PostCardReceiver;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;

@Controller
@RequestMapping(value="/mobile/postcard")
public class MobilePostCardController {
	@Autowired private ILogin login;
	@Autowired private IPostCardService cardService;
	@Autowired private IUserService userService;
	
	final int PAGE_SIZE = 20;
	private Log log = LogFactory.getLog(MobilePostCardController.class);
	
	@RequestMapping(value="/getSendedPostCards",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getSendedPostCards(@RequestParam(value="ownerId",required=false) Long ownerId,@RequestParam("page") int page){
		User currentUser = login.getCurrentUser();
		boolean myPage = false;
		User target = null;
		if(ownerId != null){
			target = userService.loadUser(ownerId);
			if(target.getId().equals(currentUser.getId())){
				myPage = true;
			}else{
				myPage = false;
			}
		}else{
			 myPage = true;
			 target = currentUser;
		}
		
		int start = (page-1) * PAGE_SIZE;
		int limits = PAGE_SIZE;
		
		List<PostCardDelegate> results = cardService.getSendedPostCards(target, start, limits,myPage);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/getReceivedPostCards",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getReceivedPostCards(@RequestParam(value="ownerId",required=false) Long ownerId,@RequestParam("page") int page){
		User currentUser = login.getCurrentUser();
		
		boolean myPage = false;
		User target = null;
		if(ownerId != null){
			target = userService.loadUser(ownerId);
			if(target.getId().equals(currentUser.getId())){
				myPage = true;
			}else{
				myPage = false;
			}
		}else{
			target = currentUser;
			 myPage = true;
		}
		
		
		int start = (page-1) * PAGE_SIZE;
		int limits = PAGE_SIZE;
		
		List<PostCardDelegate> results = cardService.getReceivedPostCards(target, start, limits, myPage);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
		
	}
	
	@RequestMapping(value="/getAllPostCards",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getAllPostCards(@RequestParam(value="ownerId",required=true) Long ownerId,@RequestParam("page") int page){
		User target = userService.loadUser(ownerId);
		
		int start = (page-1) * PAGE_SIZE;
		int limits = PAGE_SIZE;
		
		List<PostCardDelegate> results = cardService.getAllPostCards(target, start, limits);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
		
	}
	
	
	@RequestMapping(value="/viewPostCard",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String viewPostCard(@RequestParam("cardId") Long cardId){
		User currentUser = login.getCurrentUser();
		
		PostCard card = cardService.updateViewFlag(cardId, currentUser);
		PostCardDelegate delegate = new PostCardDelegate(card,false);
		if(card.getReceivers() != null){
			delegate.setReceivers(new ArrayList<ReceiverDelegate>());
			for(PostCardReceiver receiver : card.getReceivers()){
				ReceiverDelegate rDelegate = new ReceiverDelegate(receiver);
				delegate.getReceivers().add(rDelegate);
			}
		}
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, delegate);
	}
	
	
	
	
	@RequestMapping(value="/sendPostCard",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String sendPostCard(@ModelAttribute("card") PostCardSendDelegate delegate,BindingResult binding){
		User currentUser = login.getCurrentUser();
		
		try{
			cardService.sendPostCard(delegate, currentUser);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/changeShareMode",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String changeShareMode(@RequestParam("cardId") Long cardId,@RequestParam("open") boolean open){
		User currentUser = login.getCurrentUser();
		
		try{
			cardService.changeShareMode(cardId, currentUser, open);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	@RequestMapping(value="/removeFromList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removeFromList(@RequestParam("cardId") Long cardId,@RequestParam("listType") String listType){
		User currentUser = login.getCurrentUser();
		
		try{
			cardService.removeFromList(cardId, currentUser, listType);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	
}
