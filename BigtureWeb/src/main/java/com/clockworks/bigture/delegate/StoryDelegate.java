package com.clockworks.bigture.delegate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.User;
import com.google.gson.annotations.Expose;

public class StoryDelegate {
	
	@Expose
	private Long id;
	
	@Expose
	private String title; //title
	
	@Expose
	private String description; //description
	
	@Expose
	private boolean afterReading;	//독후감 등록허용 여부. true,false  after_reading
	
	@Expose
	private Long startDate;
	
	@Expose
	private Long endDate;
	
	@Expose
	private Long ownerId;
	
	@Expose
	private String ownerName;		//creator_idx
	
	@Expose
	private String ownerPhoto;
	
	@Expose
	private String ownerJob;
	
	@Expose
	private String ownerCountry;
	
	@Expose
	private Long created;	//create_time
	@Expose
	private int memberCount;	//participants
	
	@Expose
	private String coverImage;	//cover_image
	@Expose
	private boolean hot;	//hot
	
	@Expose
	private StoryStatus status;
	@Expose
	private boolean didItAll;
	@Expose
	private boolean joined;
	@Expose
	private boolean collected;
	@Expose
	private int loveCount;
	@Expose
	private int awesomeCount;
	@Expose
	private int wowCount;
	@Expose
	private int funCount;
	@Expose
	private int fantasticCount;
	@Expose
	private String countryCode;
	
	@Expose
	private int pageCount;
	
	public StoryDelegate(Story story){
		this.id = story.getId();
		this.title = story.getTitle();
		this.description = story.getDescription();
		this.afterReading = story.isAfterReading();
		if(story.getStartDate() != null)
			this.startDate = story.getStartDate().getTime();
		if(story.getEndDate() != null)
			this.endDate = story.getEndDate().getTime();
		this.ownerId = story.getOwner().getId();
		this.ownerName = story.getOwner().getNickName();
		this.ownerJob = story.getOwner().getJob();
		this.ownerCountry = story.getOwner().getCountry().getName();
		this.countryCode = story.getOwner().getCountry().getCode();
		this.ownerPhoto = story.getOwner().getPhotoPath();		
				
		this.created = story.getCreated().getTime();
		this.memberCount = story.getMemberCount();
		this.coverImage = story.getCoverImage();
		this.status = story.getStatus();
		this.hot = story.isHot();
		this.loveCount = story.getLoveCount();
		this.awesomeCount = story.getAwesomeCount();
		this.wowCount = story.getWowCount();
		this.funCount = story.getFunCount();
		this.fantasticCount = story.getFantasticCount();
		this.pageCount = story.getPageCount();
		
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAfterReading() {
		return afterReading;
	}
	public void setAfterReading(boolean afterReading) {
		this.afterReading = afterReading;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
	public StoryStatus getStatus() {
		return status;
	}
	public void setStatus(StoryStatus status) {
		this.status = status;
	}
	public boolean isDidItAll() {
		return didItAll;
	}
	public void setDidItAll(boolean didItAll) {
		this.didItAll = didItAll;
	}
	public boolean isJoined() {
		return joined;
	}
	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	public boolean isCollected() {
		return collected;
	}
	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	public int getLoveCount() {
		return loveCount;
	}
	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}
	public int getAwesomeCount() {
		return awesomeCount;
	}
	public void setAwesomeCount(int awesomeCount) {
		this.awesomeCount = awesomeCount;
	}
	public int getWowCount() {
		return wowCount;
	}
	public void setWowCount(int wowCount) {
		this.wowCount = wowCount;
	}
	public int getFunCount() {
		return funCount;
	}
	public void setFunCount(int funCount) {
		this.funCount = funCount;
	}
	public int getFantasticCount() {
		return fantasticCount;
	}
	public void setFantasticCount(int fantasticCount) {
		this.fantasticCount = fantasticCount;
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


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public String getOwnerJob() {
		return ownerJob;
	}


	public void setOwnerJob(String ownerJob) {
		this.ownerJob = ownerJob;
	}


	public String getOwnerCountry() {
		return ownerCountry;
	}


	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}
	
	
}	
