package com.clockworks.bigture.identity.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.clockworks.bigture.identity.IUserDAO;





public class UserDetailsServiceImpl implements UserDetailsService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2747621806596801687L;

	@Autowired private IUserDAO dao;
	
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String loginId)
			throws UsernameNotFoundException {
		
		User user = dao.findActiveByLoginId(loginId);
		
		if(user == null){
			throw new UsernameNotFoundException("User[" + loginId + "] does not exist");
		
		}
		
		return buildSpringUser(user);
	}

	public org.springframework.security.core.userdetails.User buildSpringUser(User user){
		
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for(final UserRoles role : user.getRoles()){
			authorities.add(new GrantedAuthority() {
				private static final long serialVersionUID = 1751733950989952303L;

				public String getAuthority() {
					return role.getRoleName();
				}
			});
		}
		
		String facebookId = user.getFacebook();
		String twitterId = user.getTwitter();
		
		String password = user.getPwd();
		if(password == null){
			password = facebookId == null ? twitterId : facebookId;
		}
	
		//spring user를 만들때에 userName은 그대로 loginId를 이용한다.
		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(user.getLoginId(),
				password ,true,true,true,true,authorities);
		return springUser;
	}

}
