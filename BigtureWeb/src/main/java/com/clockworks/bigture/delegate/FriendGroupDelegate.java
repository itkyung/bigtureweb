package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

public class FriendGroupDelegate {
	private Long id;
	
	private List<String> addedMemberIds = new ArrayList<String>();
	private List<String> deletedMemberIds = new ArrayList<String>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getAddedMemberIds() {
		return addedMemberIds;
	}
	public void setAddedMemberIds(List<String> addedMemberIds) {
		this.addedMemberIds = addedMemberIds;
	}
	public List<String> getDeletedMemberIds() {
		return deletedMemberIds;
	}
	public void setDeletedMemberIds(List<String> deletedMemberIds) {
		this.deletedMemberIds = deletedMemberIds;
	}
	
	
	
	
	
	
}
