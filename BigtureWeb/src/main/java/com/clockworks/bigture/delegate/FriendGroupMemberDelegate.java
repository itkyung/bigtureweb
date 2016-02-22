package com.clockworks.bigture.delegate;

import java.util.List;

import com.google.gson.annotations.Expose;

public class FriendGroupMemberDelegate {
	@Expose
	private Long id;
	@Expose
	private String groupName;
	@Expose
	private int friendCount;
	@Expose
	private List<SimpleProfileDelegate> members;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getFriendCount() {
		return friendCount;
	}
	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}
	public List<SimpleProfileDelegate> getMembers() {
		return members;
	}
	public void setMembers(List<SimpleProfileDelegate> members) {
		this.members = members;
	}
	
	
}
