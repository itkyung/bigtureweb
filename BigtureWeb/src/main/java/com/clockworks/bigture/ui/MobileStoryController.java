package com.clockworks.bigture.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.SimpleSortOption;
import com.clockworks.bigture.IStoryService;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.AfterReadingDelegate;
import com.clockworks.bigture.delegate.ArtClassDelegate;
import com.clockworks.bigture.delegate.StoryArtworkDelegate;
import com.clockworks.bigture.delegate.StoryDelegate;
import com.clockworks.bigture.delegate.StoryModel;
import com.clockworks.bigture.delegate.StoryPageDelegate;
import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;

@Controller
@RequestMapping(value="/mobile/story")
public class MobileStoryController {
	@Autowired private ILogin login;
	@Autowired private IStoryService service;
	@Autowired private IUserService userService;
	private final int BIG_PAGE_SIZE = 80;
	
	private Log log = LogFactory.getLog(MobileStoryController.class);
	
	/**
	 * 여기에서는 Draft는 검색되면 안된다.
	 * @param userId
	 * @param status
	 * @param sortOption
	 * @return
	 */
	@RequestMapping(value="/findUserStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findUserStory(@RequestParam(value="userId",required=false) Long userId, 
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="sortOption",required=false) String sortOption){
		boolean isMy = false;
		User user = login.getCurrentUser();
		User target = null;
		if(user != null && (userId == null || userId.equals(user.getId()))){
			target = user;
			isMy = true;
		}else {
			target = userService.loadUser(userId);
			isMy = false;
		}
		try{
			List<StoryDelegate> results = service.findUserStory(target, user, isMy, status == null ? null : StoryStatus.valueOf(status), 
					sortOption == null ? SortOption.RECENT : SortOption.valueOf(sortOption));
		
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/findOwnStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findOwnStory(
			@RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="sortOption",required=false) String sortOption){
		User user = login.getCurrentUser();
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		try{
			List<StoryDelegate> results = service.findOwnedStory(user, status == null ? null : StoryStatus.valueOf(status), 
					sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption),start,limit);
		
			int totalCount = service.countOwnedStory(user, status == null ? null : StoryStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/findJoinedStory",produces="application/json;charset=utf-8")
	@ResponseBody
	public String findJoinedStory(
			@RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="sortOption",required=false) String sortOption){
		User user = login.getCurrentUser();
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		try{
			List<StoryDelegate> results = service.findJoinedStory(user, status == null ? null : StoryStatus.valueOf(status), 
					sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption),start,limit);
		
			int totalCount = service.countJoinedStory(user, status == null ? null : StoryStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
		
	}
	
	@RequestMapping(value="/findCollectedStory",produces="application/json;charset=utf-8")
	@ResponseBody
	public String findCollectedStory(
			@RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="sortOption",required=false) String sortOption){
		User user = login.getCurrentUser();
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		try{
			List<StoryDelegate> results = service.findCollectedStory(user, status == null ? null : StoryStatus.valueOf(status), 
					sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption),start,limit);
		
			int totalCount = service.countCollectedStory(user, status == null ? null : StoryStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	

	@RequestMapping(value="/findAllStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findAllStory(@RequestParam("page") int page,
			@RequestParam(value="sortOption",required=false) String sortOption
			){
		User user = login.getCurrentUser();
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		try{
			
			List<StoryDelegate> results = service.findAllStory(user, sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption),start,limit);
			
			int totalCount = service.countAllStory();
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
	@RequestMapping(value="/findStoryByExperts",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findStoryByExperts(
			@RequestParam("targetId") Long targetId,
			@RequestParam("page") int page,
			@RequestParam(value="sortOption",required=false) String sortOption
			){
		User target = userService.loadUser(targetId);
		User user = login.getCurrentUser();
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
			
		try{
			List<StoryDelegate> results = service.findStoryByOwner(target, user,sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption), start, limit);
			
			int totalCount = service.countStoryByOwner(target);
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/addStoryCollection",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String addStoryCollection(
			@RequestParam("storyId") Long storyId){
		try{
			User currentUser = login.getCurrentUser();
			boolean success = service.addCollection(storyId, currentUser);
			
			return CommonUtils.toJsonResult(success, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/removeStoryCollection",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removeStoryCollection(
			@RequestParam("storyId") Long storyId){
		try{
			User currentUser = login.getCurrentUser();
			boolean success = service.removeCollection(storyId, currentUser);
			if(success){
				return CommonUtils.toJsonResult(success, IErrorCode.SUCCESS, null);
			}else{
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
			}
			
		}catch(Exception e){
			log.error("Error",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/deleteStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteStory(@RequestParam("storyId") Long storyId){
		try{
			User currentUser = login.getCurrentUser();
			boolean success = service.deleteStory(storyId, currentUser);
			if(success){
				return CommonUtils.toJsonResult(success, IErrorCode.SUCCESS, null);
			}else{
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_DOES_NOT_DRAFT, null);
			}
			
		}catch(Exception e){
			log.error("Error",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/listPages",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listPages(@RequestParam("storyId") Long storyId){
		
		List<StoryPageDelegate> pages = service.listPages(storyId);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, pages);
	}
	
	
	@RequestMapping(value="/listStoryArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listStoryArtworks(@RequestParam("storyId") Long storyId){
		User user = login.getCurrentUser();
		
		
		List<StoryArtworkDelegate> artworks = service.listStoryArtworks(storyId,user);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, artworks);
	}
	
	@RequestMapping(value="/listStoryArtworksByPage",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listStoryArtworksByPage(@RequestParam("pageId") Long pageId){
		User user = login.getCurrentUser();
		List<StoryArtworkDelegate> artworks = service.listStoryArtworksByPage(pageId,user);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, artworks);
	}
	
	
	
	@RequestMapping(value="/saveOrUpdateStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String saveOrUpdateStory(@ModelAttribute("model") StoryModel storyModel,BindingResult binding){
		User currentUser = login.getCurrentUser();
		try{
			Story story = service.saveOrUpdateStory(currentUser, storyModel);
			Map<String,Long> result = new HashMap<String, Long>();
			result.put("id", story.getId());
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, result);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/shareStory",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String shareStory(@ModelAttribute("model") StoryModel storyModel,BindingResult binding){
		User currentUser = login.getCurrentUser();
		try{
			service.shareStory(currentUser, storyModel);
			
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/registArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String registArtwork(@RequestParam("storyId") Long storyId,
			@RequestParam("artworkId") Long artworkId,
			@RequestParam("pageId") Long pageId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.registArtwork(currentUser, storyId, artworkId, pageId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/registAfterReading",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String registAfterReading(@RequestParam("storyId") Long storyId,
			@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.registAfterReading(currentUser, storyId, artworkId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/listAfterRead",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listAfterRead(@RequestParam("storyId") Long storyId){
		List<AfterReadingDelegate> results = service.listAfterReads(storyId);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/getStoryInfo",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getStoryInfo(@RequestParam("storyId") Long storyId){
		
		Story story = service.loadStory(storyId);
		StoryDelegate delegate = new StoryDelegate(story);
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, delegate);
	}
	
	
	
}
