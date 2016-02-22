package com.clockworks.bigture.delegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ClassShare;
import com.google.gson.annotations.Expose;

public class ArtClassDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String coverImage;
	
	@Expose
	private String className;
	
	@Expose
	private Long ownerId;
	
	@Expose
	private String ownerName;
	
	@Expose
	private String ownerPhoto;
	
	@Expose
	private String ownerCountry;
	
	@Expose
	private String ownerJob;
	
	@Expose
	private boolean opened;
	
	@Expose
	private boolean collected;
	
	@Expose
	private String status;

	@Expose
	private boolean joined;
	
	@Expose
	private String startDate;
	
	@Expose
	private String endDate;
	
	@Expose
	private String description;
	
	@Expose
	private String classType;
	
	@Expose
	private String shareType;
	
	private DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	public ArtClassDelegate(ArtClass artClass){
		this.id = artClass.getId();
		this.coverImage = artClass.getCoverImage();
		this.className = artClass.getClassName();
		this.ownerId = artClass.getOwner().getId();
		this.ownerName = artClass.getOwner().getNickName();
		this.ownerPhoto = artClass.getOwner().getPhotoPath();
		this.ownerCountry = artClass.getOwner().getCountry().getName();
		this.ownerJob = artClass.getOwner().getJob();
		this.opened = artClass.getShareType().equals(ClassShare.OPEN) ? true : false;
		this.status = artClass.getStatus().name();
		if(artClass.getStartDate() != null)
			this.startDate = fm.format(artClass.getStartDate());
		if(artClass.getEndDate() != null)
			this.endDate = fm.format(artClass.getEndDate());
		this.description = artClass.getDescription();
		this.classType = artClass.getClassType().name();
		this.shareType = artClass.getShareType().name();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhoto() {
		return ownerPhoto;
	}

	public void setOwnerPhoto(String ownerPhoto) {
		this.ownerPhoto = ownerPhoto;
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getOwnerJob() {
		return ownerJob;
	}

	public void setOwnerJob(String ownerJob) {
		this.ownerJob = ownerJob;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	
	
	
}
