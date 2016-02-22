package com.clockworks.bigture.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.clockworks.bigture.IArtClassService;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.common.FileUploadOneCommand;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.delegate.ArtClassDelegate;
import com.clockworks.bigture.delegate.ArtClassModel;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.FriendGroupDelegate;
import com.clockworks.bigture.delegate.ProfileDelegate;
import com.clockworks.bigture.delegate.PuzzleArtworkDelegate;
import com.clockworks.bigture.delegate.PuzzleDelegate;
import com.clockworks.bigture.delegate.StoryDelegate;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassCollection;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.PuzzlePart;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;

@Controller
@RequestMapping(value="/mobile/artclass")
public class MobileArtClassController {
	@Autowired ILogin login;
	@Autowired IUserService userService;
	@Autowired IArtClassService service;
	@Autowired private IImageService imageService;
	
	private final int BIG_PAGE_SIZE = 80;
	
	private Log log = LogFactory.getLog(MobileArtClassController.class);
	private final DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value="/findUserClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findUserClass(@RequestParam(value="userId",required=false) Long userId, 
			@RequestParam(value="status",required=false) String status,
			@RequestParam("page") int page){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		
		boolean isMy = false;
		User user = login.getCurrentUser();
		User target = null;
		if(userId == null || userId.equals(user.getId())){
			target = user;
			isMy = true;
		}else {
			target = userService.loadUser(userId);
			isMy = false;
		}
		try{
			List<ArtClassDelegate> results = service.findUserClass(target, isMy,start,limit, status == null ? null : ClassStatus.valueOf(status) );
		
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
	@RequestMapping(value="/findOwnClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findOwnClass(@RequestParam(value="userId") Long userId, @RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status
			){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		User target = userService.loadUser(userId);
		
		try{
			List<ArtClassDelegate> results = service.findOwnClass(target, start, limit, status == null || "ALL".equals(status) ? null : ClassStatus.valueOf(status));
			
			int totalCount = service.countOwnClass(target, status == null || "ALL".equals(status) ? null : ClassStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	
	@RequestMapping(value="/findJoinedClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findJoinedClass(@RequestParam(value="userId") Long userId, @RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status
			){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		User target = userService.loadUser(userId);
		
		try{
			List<ArtClassDelegate> results = service.findJoinedClass(target, start, limit, status == null || "ALL".equals(status)  ? null : ClassStatus.valueOf(status));
			
			int totalCount = service.countJoinedClass(target, status == null || "ALL".equals(status) ? null : ClassStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/findCollectedClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findCollectedClass(@RequestParam(value="userId") Long userId, @RequestParam("page") int page,
			@RequestParam(value="status",required=false) String status
			){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		User target = userService.loadUser(userId);
		
		try{
			List<ArtClassDelegate> results = service.findCollectedClass(target, start, limit, status == null || "ALL".equals(status)  ? null : ClassStatus.valueOf(status));
			int totalCount = service.countCollectedClass(target, status == null || "ALL".equals(status) ? null : ClassStatus.valueOf(status));
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/findAllClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findAllClass(@RequestParam(value="userId") Long userId, @RequestParam("page") int page,
			@RequestParam(value="sortOption",required=false) String sortOption
			){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		User target = userService.loadUser(userId);
		
		try{
			List<ArtClassDelegate> results = service.findAllClass(target, start, limit, sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption));
			
			int totalCount = service.countAllClass(target);
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
	@RequestMapping(value="/findClassByExperts",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findClassByExperts(@RequestParam(value="userId") Long userId, @RequestParam("page") int page,
			@RequestParam(value="sortOption",required=false) String sortOption
			){
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		User target = userService.loadUser(userId);
		
		try{
			List<ArtClassDelegate> results = service.findClassByOwner(target, start, limit, sortOption == null ? SimpleSortOption.RECENT : SimpleSortOption.valueOf(sortOption));
			
			int totalCount = service.countClassByOwner(target);
			totalCount = totalCount+1;
			int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
	@RequestMapping(value="/createArtClass",produces="application/json;charset=utf-8")
	@ResponseBody
	public String createArtClass(@ModelAttribute("artClass") ArtClassModel model,BindingResult binding){
		
		User user = login.getCurrentUser();
		
		try{
			ArtClass artClass = service.createArtClass(user, model);
			
			Map<String,Long> result = new HashMap<String, Long>();
			result.put("id", artClass.getId());
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, result);
		}catch(Exception e){
			log.error("Error ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/uploadCoverImage",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadCoverImage(@ModelAttribute("anyFile") FileUploadOneCommand command,
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
	
	@RequestMapping(value="/addClassCollection",produces="application/json;charset=utf-8")
	@ResponseBody
	public String addClassCollection(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		
		boolean result = service.addCollection(currentUser, classId);
		if(result){
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}else{
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/removeClassColleciton",produces="application/json;charset=utf-8")
	@ResponseBody
	public String removeClassCollection(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		boolean result = service.removeCollection(currentUser, classId);
		if(result){
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}else{
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
	}
	
	@RequestMapping(value="/getArtClassInfo",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getArtClassInfo(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		
		ArtClass artClass = service.loadArtClass(classId);
		ArtClassDelegate delegate = new ArtClassDelegate(artClass);
		List<ArtClassCollection> collections = service.findCollection(currentUser);
		boolean collected = false;
		for(ArtClassCollection c : collections){
			if(c.getArtClass().equals(artClass)){
				collected = true;
				break;
			}
		}
		
		delegate.setCollected(collected);
		delegate.setJoined(service.isJoined(artClass, currentUser));
		
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, delegate);
		
	}
	
	@RequestMapping(value="/getJoinedMembers",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getJoinedMembers(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		
		List<ProfileDelegate> delegates = new ArrayList<ProfileDelegate>();
		List<User> users = service.getJoinedMembers(classId);
		for(User user : users){
			ProfileDelegate delegate = new ProfileDelegate(user);
			delegates.add(delegate);
		}
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, delegates);
	}
	
	@RequestMapping(value="/changeCoverImage",produces="application/json;charset=utf-8")
	@ResponseBody
	public String changeCoverImage(@RequestParam("classId") Long classId,
			@RequestParam("coverImageId") Long coverImageId,
			@RequestParam("coverFromArtwork") boolean coverFromArtwork){
		User currentUser = login.getCurrentUser();
		try{
			String imagePath = service.changeCoverImage(classId, coverImageId, coverFromArtwork);
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, imagePath);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_NO_PERMISSION, null);
		}
	}
	
	@RequestMapping(value="/listClassArtworks",produces="application/json;charset=utf-8")
	@ResponseBody
	public String listClassArtworks(@RequestParam("classId") Long classId,
			@RequestParam("page") int page, @RequestParam(value="sortOption",required=false) String sortOption){
		User currentUser = login.getCurrentUser();
		
		int start = (page-1) * BIG_PAGE_SIZE;
		int limit = BIG_PAGE_SIZE;
		
		List<ArtworkDelegate> results = service.listClassArtworks(currentUser,classId, start,limit, SortOption.valueOf(sortOption));
		int totalCount = service.countClassArtworks(classId);
		
		totalCount = totalCount+1;
		int totalPage = (totalCount / BIG_PAGE_SIZE) + 1;
		
		return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		
	}
	
	@RequestMapping(value="/deleteArtClass",produces="application/json;charset=utf-8")
	@ResponseBody
	public String deleteArtClass(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.deleteArtClass(currentUser, classId);
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	@RequestMapping(value="/editArtClass",produces="application/json;charset=utf-8")
	@ResponseBody
	public String editArtClass(@RequestParam("classId") Long classId,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("description") String description){
		User currentUser = login.getCurrentUser();
		
		try{
			service.editArtClass(currentUser, classId, fm.parse(startDate), fm.parse(endDate), description);
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	@RequestMapping(value="/editClassMembers",produces="application/json;charset=utf-8")
	@ResponseBody
	public String editClassMembers(@ModelAttribute("member") FriendGroupDelegate delegate,
			BindingResult result){
		
		service.addClassMember(delegate.getId(), delegate.getAddedMemberIds());
		return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		
	}
	
	@RequestMapping(value="/registArtwork",produces="application/json;charset=utf-8")
	@ResponseBody
	public String registArtwork(@RequestParam("classId") Long classId,
			@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		
		
		
		try{
			service.registArtwork(currentUser, classId, artworkId);
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	@RequestMapping(value="/registPuzzleArtwork",produces="application/json;charset=utf-8")
	@ResponseBody
	public String registPuzzleArtwork(@RequestParam("classId") Long classId,
			@RequestParam("artworkId") Long artworkId,
			@RequestParam("puzzleId") Long puzzleId,
			@RequestParam("part") String part
			){
		User currentUser = login.getCurrentUser();
		
		try{
			service.registPuzzleArtwork(currentUser, classId, puzzleId, artworkId, PuzzlePart.valueOf(part));
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_ALREADY_RESERVE_PART, null);
		}
	}
	
	@RequestMapping(value="/reservePuzzleArtwork",produces="application/json;charset=utf-8")
	@ResponseBody
	public String reservePuzzleArtwork(@RequestParam("classId") Long classId,
			@RequestParam("puzzleId") Long puzzleId,
			@RequestParam("part") String part){
		User currentUser = login.getCurrentUser();
		
		try{
			service.reservePuzzleArtwork(currentUser, classId, puzzleId, PuzzlePart.valueOf(part));
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_ALREADY_RESERVE_PART, null);
		}
		
	}
	
	@RequestMapping(value="/cancelPuzzleArtwork",produces="application/json;charset=utf-8")
	@ResponseBody
	public String cancelPuzzleArtwork(@RequestParam("classId") Long classId,
			@RequestParam("puzzleId") Long puzzleId,
			@RequestParam("part") String part){
		User currentUser = login.getCurrentUser();
		
		try{
			service.cancelPuzzleArtwork(currentUser, classId, puzzleId, PuzzlePart.valueOf(part));
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_NO_PERMISSION, null);
		}
		
	}
	
	@RequestMapping(value="/getPuzzleList",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getPuzzleList(@RequestParam("classId") Long classId){
		User currentUser = login.getCurrentUser();
		
		List<PuzzleDelegate> results = service.listPuzzle(classId);
		return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, results);
		
	}
	
	@RequestMapping(value="/listPuzzleArtworks",produces="application/json;charset=utf-8")
	@ResponseBody
	public String listPuzzleArtworks(@RequestParam("classId") Long classId,
			@RequestParam("puzzleId") Long puzzleId){
		User currentUser = login.getCurrentUser();
		
		List<PuzzleArtworkDelegate> results = service.listPuzzleArtworks(classId, puzzleId);
		return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/getPuzzleInfo",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getPuzzleInfo(@RequestParam("puzzleId") Long puzzleId){
		
		ArtClassPuzzle puzzle = service.loadPuzzle(puzzleId);
		return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, new PuzzleDelegate(puzzle));
	}
	
	@RequestMapping(value="/getPuzzleArtwork",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getPuzzleArtwork(@RequestParam("puzzleId") Long puzzleId,
			@RequestParam("part") String part){
		
		ArtClassPuzzleArtwork puzzle = service.findPuzzleArtwork(puzzleId, PuzzlePart.valueOf(part));
		if(puzzle != null){
			return CommonUtils.toJsonResult(true,IErrorCode.SUCCESS, new PuzzleArtworkDelegate(puzzle));
		}else{
			return CommonUtils.toJsonResult(false,IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
}
