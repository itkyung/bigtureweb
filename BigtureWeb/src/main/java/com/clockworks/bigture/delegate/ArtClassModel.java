package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

public class ArtClassModel {
	
	private Long id;
	
	private String className;
	
	private String description;
	
	private String classType;
	
	private String shareType;
	
	private String startDate;
	
	private String endDate;
	
	private String coverImageId;
	
	private boolean coverFromArtwork;
	
	private List<Long> sharedFriends = new ArrayList<Long>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	


	public String getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(String coverImageId) {
		this.coverImageId = coverImageId;
	}

	public List<Long> getSharedFriends() {
		return sharedFriends;
	}

	public void setSharedFriends(List<Long> sharedFriends) {
		this.sharedFriends = sharedFriends;
	}

	public boolean isCoverFromArtwork() {
		return coverFromArtwork;
	}

	public void setCoverFromArtwork(boolean coverFromArtwork) {
		this.coverFromArtwork = coverFromArtwork;
	}
	
	
	
}
