package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.StoryAfterReading;
import com.google.gson.annotations.Expose;

public class AfterReadingDelegate {
	@Expose
	private Long id;
	@Expose
	private Long artworkId;
	@Expose
	private String title;
	@Expose
	private String photo;
	@Expose
	private String thumbnail;
	@Expose
	private String ownerName;
	
	public AfterReadingDelegate(StoryAfterReading read){
		this.id = read.getId();
		this.artworkId = read.getArtwork().getId();
		this.title = read.getArtwork().getTitle();
		this.photo = read.getArtwork().getPhoto();
		this.thumbnail = read.getArtwork().getThumbnail();
		this.ownerName = read.getArtwork().getUser().getNickName();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArtworkId() {
		return artworkId;
	}
	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	
}
