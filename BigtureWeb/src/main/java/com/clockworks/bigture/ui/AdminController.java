package com.clockworks.bigture.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.delegate.UserSearchDelegate;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.IUserService;
import com.clockworks.bigture.ui.param.UserSearchModel;


@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	private Log log = LogFactory.getLog(AdminController.class);
			
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/home")
	public String home(Model model){
		
		return "admin/home";
	}
	
	
	@RequestMapping(value="/activateUser")
	public String activateUser(Model model){
		
		return "admin/activateUser";
	}
	
	@RequestMapping(value="/doActivateUser",method=RequestMethod.POST)
	public String doActivateUser(@RequestParam("loginId") String loginId){
		User user = userService.findByLoginId(loginId);
		try {
			userService.activateUser(user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "activateSuccess";
	}
	
	@RequestMapping(value="/listUser")
	public String listUser(){
		return "admin/user/listUser";
	}
	
	@RequestMapping(value="/searchUser",  produces = "application/json;charset=utf-8")
	@ResponseBody
	public String searchUser(@ModelAttribute UserSearchModel condition, BindingResult bindResult) throws IOException{
		int _perPage = condition.getLength();
		
		condition.setStart(condition.getStart());
		condition.setLimit(_perPage);
		
		int totalCount = userService.countUser(condition);
		List<UserSearchDelegate> results = userService.searchUser(condition);
		
		int totalPage = 0;
		if(totalCount > 0){
			totalPage =  (totalCount % _perPage) == 0 ? totalCount/_perPage : (totalCount/_perPage)+1;
		}
		
		DatatableJson json = new DatatableJson(condition.getDraw(), totalCount, results.size(), results.toArray(), condition.getStart(), condition.getLimit());
		
		return CommonUtils.toJson(json);
	}
	
	@RequestMapping(value="/testPage")
	public String testPage(@RequestParam("keyword") String keyword){
		log.info("Keyword 는 :" + keyword);
		
		try{
		    Process p = Runtime.getRuntime().exec("locale");
		    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line = null;
		   
		    while((line = br.readLine()) != null){
		        log.info(line);
		    }
		}catch(Exception e){
		    System.out.println(e);
		}
		
		
		return "";
	}
	
}
