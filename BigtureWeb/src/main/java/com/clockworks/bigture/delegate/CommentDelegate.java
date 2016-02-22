package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.ArtworkComment;
import com.google.gson.annotations.Expose;

public class CommentDelegate {
	@Expose
	private Long id;
	@Expose
	private String comment;
	@Expose
	private String sticker;
	@Expose
	private Long ownerId;
	@Expose
	private String ownerName;
	@Expose
	private String ownerCountry;
	@Expose
	private String countryCode;
	@Expose
	private String ownerCountryKr;
	@Expose
	private String ownerPhoto;
	@Expose
	private String ownerJob;
	@Expose
	private Long ownerRegionId;
	@Expose
	private long created;

	public CommentDelegate(ArtworkComment comment){
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.sticker = comment.getSticker().name();
		this.ownerId = comment.getUser().getId();
		this.ownerName = comment.getUser().getNickName();
		this.ownerCountry = comment.getUser().getCountry().getName();
		this.countryCode = comment.getUser().getCountry().getCode();
		this.ownerCountryKr = comment.getUser().getCountry().getKrName();
		this.ownerPhoto = comment.getUser().getPhotoPath();
		this.ownerJob = comment.getUser().getJob();
		this.ownerRegionId = comment.getUser().getCountry().getRegion().getId();
		this.created = comment.getCreated().getTime();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSticker() {
		return sticker;
	}

	public void setSticker(String sticker) {
		this.sticker = sticker;
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

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getOwnerPhoto() {
		return ownerPhoto;
	}

	public void setOwnerPhoto(String ownerPhoto) {
		this.ownerPhoto = ownerPhoto;
	}

	public String getOwnerJob() {
		return ownerJob;
	}

	public void setOwnerJob(String ownerJob) {
		this.ownerJob = ownerJob;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}


	public Long getOwnerRegionId() {
		return ownerRegionId;
	}


	public void setOwnerRegionId(Long ownerRegionId) {
		this.ownerRegionId = ownerRegionId;
	}
	
	
}	
