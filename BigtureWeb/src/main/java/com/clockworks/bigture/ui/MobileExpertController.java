package com.clockworks.bigture.ui;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.ExpertDelegate;
import com.clockworks.bigture.delegate.ExpertListWrapper;
import com.clockworks.bigture.delegate.NameDelegate;
import com.clockworks.bigture.identity.IUserService;

@Controller
@RequestMapping(value="/mobile/expert")
public class MobileExpertController {
	private Log log = LogFactory.getLog(MobileExpertController.class);
	
	@Autowired
	private IUserService service;
	
	
	@RequestMapping(value="/listByName",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listByName(){
		
		ExpertListWrapper results = service.findExpertByName();
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results.getInitialWorkList());
	}
	
	@RequestMapping(value="/listJobs",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listJobs(){
		
		List<NameDelegate> results = service.listJobs();
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	
	@RequestMapping(value="/findByJob",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findByJob(@RequestParam("jobName") String jobName){
		
		List<ExpertDelegate> results = service.findExpertByJob(jobName);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/findByNameCountry",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findByNameCountry(@RequestParam(value="keyword",required=false) String keyword){
		
		List<ExpertDelegate> results = service.findExpertByNameCountry(keyword);
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
	@RequestMapping(value="/listGroupByJob",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String listGroupByJob(){
		List<NameDelegate> results = service.listGroupByJob();
		return CommonUtils.toJsonResult(true, IErrorCode.SUCCESS, results);
	}
	
}
