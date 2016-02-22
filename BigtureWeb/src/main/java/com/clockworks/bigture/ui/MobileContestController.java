package com.clockworks.bigture.ui;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.IContestService;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ContestDelegate;
import com.clockworks.bigture.delegate.ContestRankDelegate;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.ILogin;

/**
 * Contest관련 
 * @author itkyung
 *
 */
@Controller
@RequestMapping(value="/mobile/contest")
public class MobileContestController {
	@Autowired private ILogin login;
	@Autowired private IContestService service;
	
	private Log log = LogFactory.getLog(MobileContestController.class);
	private final int PAGE_SIZE = 20;
	
	@RequestMapping(value="/listActiveContest",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listActiveContest(@RequestParam(value="page",required=true) int page){
		int start = (page-1) * PAGE_SIZE;
		int limits = PAGE_SIZE;
		
		List<ContestDelegate> results = service.listActiveContest(start, limits);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	
	@RequestMapping(value="/listAllContest",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listAllContest(@RequestParam(value="page",required=true) int page){
		
		int start = (page-1) * PAGE_SIZE;
		int limit = PAGE_SIZE;
		
		List<ContestDelegate> results = service.listAllContest(start, limit);
		int totalCount = service.countAllContest();
		totalCount = totalCount+1;
		int totalPage = (totalCount / PAGE_SIZE) + 1;
		return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
	}
	
	@RequestMapping(value="/listContestArtworks",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listContestArtworks(@RequestParam("contestId") Long contestId, 
			@RequestParam(value="sortOption") String sortOption, 
			@RequestParam(value="page",required=false) int page){
		User currentUser = login.getCurrentUser();
		int start = (page-1) * PAGE_SIZE;
		int limits = PAGE_SIZE;
		
		List<ArtworkDelegate> results = service.listContestArtworks(currentUser,contestId, start, limits, SortOption.valueOf(sortOption));
		int totalCount = service.countContestArtworks(contestId);
		totalCount = totalCount+1;
		int totalPage = (totalCount / PAGE_SIZE) + 1;
		return CommonUtils.toJsonPagingResult(true, IErrorCode.SUCCESS, totalPage, results);
		
	}
	
	@RequestMapping(value="/listWinner",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listWinner(@RequestParam("contestId") Long contestId){
		
		try{
			List<ContestRankDelegate> results = service.listWinner(contestId);
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
		}catch(Exception e){
			log.error("Error :",e);
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
	}
	
	@RequestMapping(value="/getContestInfo",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getContestInfo(@RequestParam("contestId") Long contestId){
	
		Contest contest = service.loadContest(contestId);
		if(contest != null){
			return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, new ContestDelegate(contest));
		}else{
			return CommonUtils.toJsonResult(false, IErrorCode.ERROR_UNKNOWN, null);
		}
		
	}
}
