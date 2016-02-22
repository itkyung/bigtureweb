package com.clockworks.bigture.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.common.ImagePathType;
import com.clockworks.bigture.common.NotActiveException;
import com.clockworks.bigture.common.NotYourArtworkException;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ArtworkRegistModel;
import com.clockworks.bigture.delegate.CommentWrapper;
import com.clockworks.bigture.delegate.RegionCommentCount;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserService;
import com.clockworks.bigture.impl.ArtworkService;

/**
 * Artwork관련 API
 * @author itkyung
 *
 */
@Controller
@RequestMapping(value="/mobile/artwork")
public class MobileArtworkController{
	@Autowired private ILogin login;
	@Autowired private IArtworkService service;
	@Autowired private IUserService userService;
	@Autowired private IImageService imageService;
	
	private Log log = LogFactory.getLog(MobileArtworkController.class);
	private final int PAGE_SIZE = 20;
	private final int BIG_PAGE_SIZE = 80;
	private final int COMMENT_PAGE_SIZE = 150;
	
	@RequestMapping(value="/findUserArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findUserArtworks(@RequestParam(value="userId",required=false) Long userId,
			@RequestParam(value="type",required=false) String typeStr,
			@RequestParam(value="sortOption",required=false) String sortOption,
			@RequestParam(value="page",required=false) Integer _page
			){
		
		int page = 1;
		if(_page != null) page = _page;
		int start = (page-1) * PAGE_SIZE;
		int limit = PAGE_SIZE;
		
		
		try{
			User currentUser = login.getCurrentUser();
			User targetUser = null;
			if(userId == null){
				targetUser = currentUser;
			}else{
				targetUser = userService.loadUser(userId);
			}
			
			if(typeStr == null) typeStr = "NORMAL";
			
			List<ArtworkDelegate> results = service.findUserArtworks(targetUser, currentUser,targetUser.equals(currentUser) ? true : false,
					typeStr == null ? null : ArtworkType.valueOf(typeStr), 
							sortOption == null ? SortOption.RECENT : SortOption.valueOf(sortOption),start,limit);
			
			int totalCount = service.countUserArtworks(targetUser, targetUser.equals(currentUser) ? true : false,
					typeStr == null ? null : ArtworkType.valueOf(typeStr));
			totalCount = totalCount+1;
			int totalPage = (totalCount / PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/findAllArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findAllArtworks(@RequestParam(value="type",required=false) String typeStr,
			@RequestParam(value="sortOption",required=false) String sortOption,
			@RequestParam("page") int page){
		try{
			User currentUser = login.getCurrentUser();
			int start = (page-1) * PAGE_SIZE;
			int limit = PAGE_SIZE;
			
			if(typeStr == null) typeStr = "NORMAL";
			
			List<ArtworkDelegate> results = service.findAllArtworks(currentUser,
					typeStr == null ? null : ArtworkType.valueOf(typeStr), 
							sortOption == null ? SortOption.RECENT : SortOption.valueOf(sortOption),
									start,limit);
			
			int totalCount = service.countAllArtworks(typeStr == null ? null : ArtworkType.valueOf(typeStr));
			totalCount = totalCount+1;
			int totalPage = (totalCount / PAGE_SIZE) + 1;
			
			log.info("Artworks List");
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}

	@RequestMapping(value="/findFriendsArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findFriendsArtworks(@RequestParam(value="sortOption",required=false) String sortOption,
			@RequestParam("page") int page){
		try{
			User currentUser = login.getCurrentUser();
			int start = (page-1) * PAGE_SIZE;
			int limit = PAGE_SIZE;
			
			
		
			List<ArtworkDelegate> results = service.findFriendsArtworks(currentUser,
							sortOption == null ? SortOption.RECENT : SortOption.valueOf(sortOption),
									start,limit);
			
			int totalCount = service.countFriendsArtworks(currentUser);
			totalCount = totalCount+1;
			int totalPage = (totalCount / PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/findCollectedArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findCollectedArtworks(@RequestParam(value="sortOption",required=false) String sortOption,
			@RequestParam("page") int page){
		try{
			User currentUser = login.getCurrentUser();
			int start = (page-1) * PAGE_SIZE;
			int limit = PAGE_SIZE;
		
			List<ArtworkDelegate> results = service.findCollectedArtworks(currentUser,
							sortOption == null ? SortOption.RECENT : SortOption.valueOf(sortOption),
									start,limit);
			
			int totalCount = service.countCollectedArtworks(currentUser);
			totalCount = totalCount+1;
			int totalPage = (totalCount / PAGE_SIZE) + 1;
			
			return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
			
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	
	@RequestMapping(value="/getArtworkComments",produces = "application/json;charset=utf-8")
	@ResponseBody	
	public String getArtworkComments(@RequestParam("artworkId") Long artworkId,
			@RequestParam("page") int page){
		User currentUser = login.getCurrentUser();
		int start = (page-1) * COMMENT_PAGE_SIZE;
		int limits = COMMENT_PAGE_SIZE;
		
		CommentWrapper wrapper = service.getCommentInfo(currentUser,artworkId, start, limits);
		return CommonUtils.toJson(wrapper);
	}
	
	@RequestMapping(value="/sendSticker",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String sendSticker(@RequestParam("artworkId") Long artworkId,
			@RequestParam("sticker") String sticker){
		
		User currentUser = login.getCurrentUser();
		
		try{
			service.sendSticker(currentUser, artworkId, StickerType.valueOf(sticker));
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_ALREADY_PROCESSED, null);
		}
		
	}
	
	@RequestMapping(value="/deleteCommentOrSticker",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteCommentOrSticker(@RequestParam("id") Long id){
		User currentUser = login.getCurrentUser();
		
		try{
			service.deleteCommentOrSticker(currentUser, id);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NO_PERMISSION, null);
		}
	}
	
	
	@RequestMapping(value="/sendComment",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String sendComment(@RequestParam("artworkId") Long artworkId,
			@RequestParam("comment") String comment){
		
		User currentUser = login.getCurrentUser();
		
		try{
			service.sendComment(currentUser, artworkId, comment);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/addArtworkCollection",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String addCollection(@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		try{
			boolean result = service.addCollection(currentUser, artworkId);
			if(result){
				return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
			}else{
				//본인의 Artwork는 수집불가함.
				return CommonUtils.toJsonResult(false, IErrorCode.ERROR_YOUR_ARTWORK, null);
			}
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_ALREADY_PROCESSED, null);
		}
		
	}
	
	@RequestMapping(value="/removeArtworkCollection",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removeCollection(@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.removeCollection(currentUser, artworkId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_CANT_REMOVE_COLLECT, null);
		}
		
	}
	
	@RequestMapping(value="/reportSpam",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String reportSpam(@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.reportSpam(currentUser, artworkId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
	
	@RequestMapping(value="/requestToContest",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String requestToContest(@RequestParam("artworkId") Long artworkId,
			@RequestParam("contestId") Long contestId){
		
		User currentUser = login.getCurrentUser();
		
		try{
			service.requestContest(currentUser, artworkId, contestId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(NotActiveException nae){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NOT_ACTIVE_CONTEST, null);
			
		}catch(NotYourArtworkException nyae){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_YOUR_ARTWORK, null);
			
		}catch(Exception e){
			log.error("Error : ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/cancelFromContest",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String cancelFromContest(@RequestParam("artworkId") Long artworkId,
			@RequestParam("contestId") Long contestId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.cancelFromContest(currentUser, artworkId, contestId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(NotActiveException nae){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_NOT_ACTIVE_CONTEST, null);
			
		}catch(NotYourArtworkException nyae){
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_YOUR_ARTWORK, null);
			
		}catch(Exception e){
			log.error("Error : ",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
		
	}
	
	
	@RequestMapping(value="/editArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String editArtwork(@RequestParam("artworkId") Long artworkId,
			@RequestParam("title") String title,
			@RequestParam("comment") String comment,
			@RequestParam("shareType") String shareType){
		
		User currentUser = login.getCurrentUser();
		
		try{
			service.editArtwork(currentUser, artworkId, title, comment, ShareType.valueOf(shareType));
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_YOUR_ARTWORK, null);
		}
		
	}
	

	@RequestMapping(value="/deleteArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteArtwork(@RequestParam("artworkId") Long artworkId){
		User currentUser = login.getCurrentUser();
		
		try{
			service.deleteArtwork(currentUser, artworkId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, null);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_YOUR_ARTWORK, null);
		}
	}
			
	
	@RequestMapping(value="/registArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String registArtwork(HttpServletRequest request,@ModelAttribute("artwork") ArtworkRegistModel artworkModel,BindingResult result){
		User currentUser = login.getCurrentUser();
		
		try{
//			ArtworkRegistModel artworkModel = new ArtworkRegistModel();
//			MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
//			
//			MultipartFile imageFile = multipartRequest.getFile("imageFile");
//			MultipartFile drawFile = multipartRequest.getFile("drawFile");
//			
//			artworkModel.setTitle(multipartRequest.getParameter("title"));
//			artworkModel.setComment(multipartRequest.getParameter("comment"));
//			artworkModel.setType(multipartRequest.getParameter("type"));
//			artworkModel.setShare(multipartRequest.getParameter("share"));
//			artworkModel.setOsType(multipartRequest.getParameter("osType"));
//			String refId = multipartRequest.getParameter("refId");
//			if(refId != null)
//				artworkModel.setRefId(Long.parseLong(refId));
			
			
			TempImage tmp = imageService.saveImage(currentUser, artworkModel.getImageFile(), 400, 0, artworkModel.getLocalPath());
			ImageInfo info = imageService.transferFile(tmp, ImagePathType.ARTWORKS);
			
			String drawingPath = null;
			if(artworkModel.getDrawFile() != null){
				drawingPath = imageService.saveDrawingFile(currentUser, artworkModel.getDrawFile(), OsType.valueOf(artworkModel.getOsType()));
			}
			
			Artwork artwork = service.registArtwork(currentUser, artworkModel, info, drawingPath);
			ArtworkDelegate artworkDel = new ArtworkDelegate();
			artworkDel.setId(artwork.getId());
			
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, artworkDel);
			
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);			
		}
		
	}
	
	
	@RequestMapping(value="/loadArtwork",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String loadArtwork(@RequestParam("id") Long id){
		Artwork artwork = service.loadArtwork(id);
		ArtworkDelegate delegate = new ArtworkDelegate(artwork);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, delegate);
	}
	
	@RequestMapping(value="/getTalkMapData",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getTalkMapData(@RequestParam("artworkId") Long artworkId){
		List<RegionCommentCount> results = service.getRegionCountComment(artworkId);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
}
