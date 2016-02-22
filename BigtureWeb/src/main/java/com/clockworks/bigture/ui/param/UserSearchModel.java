package com.clockworks.bigture.ui.param;

public class UserSearchModel extends DataTableSearchModel {
	
	private boolean verified = true;
	private String keyword;
	private UserSearchType userSearchType;
	private UserSearchRole userSearchRole = UserSearchRole.NORMAL;
	
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public UserSearchType getUserSearchType() {
		return userSearchType;
	}
	public void setUserSearchType(UserSearchType userSearchType) {
		this.userSearchType = userSearchType;
	}
	public UserSearchRole getUserSearchRole() {
		return userSearchRole;
	}
	public void setUserSearchRole(UserSearchRole userSearchRole) {
		this.userSearchRole = userSearchRole;
	}
	
	
	
}
