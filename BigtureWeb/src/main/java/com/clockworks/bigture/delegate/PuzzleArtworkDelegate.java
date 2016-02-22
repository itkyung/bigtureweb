package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.google.gson.annotations.Expose;

public class PuzzleArtworkDelegate {
	@Expose
	private Long partId;
	@Expose
	private String part;
	@Expose
	private Long classId;
	@Expose
	private Long puzzleId;
	@Expose
	private Long artworkId;
	
	@Expose
	private String status;
	@Expose
	private long started;
	@Expose
	private long expiredTime;
	@Expose
	private Long ownerId;
	@Expose
	private String ownerName;
	@Expose
	private String ownerCountry;
	@Expose
	private String ownerPhoto;
	@Expose
	private String photo;
	@Expose
	private String thumbnail;
	@Expose
	private String baseSketch;
	
	public PuzzleArtworkDelegate(ArtClassPuzzleArtwork pa){
		this.partId = pa.getId();
		this.part = pa.getPart().name();
		this.classId = pa.getPuzzle().getArtClass().getId();
		this.puzzleId = pa.getPuzzle().getId();
		if(pa.getArtwork() != null){
			this.artworkId = pa.getArtwork().getId();
			this.photo = pa.getArtwork().getPhoto();
			this.thumbnail = pa.getArtwork().getThumbnail();
		}
		this.status = pa.getStatus().name();
		this.started = pa.getStarted().getTime();
		if(pa.getExpireTime() != null)
			this.expiredTime = pa.getExpireTime().getTime();
		
		this.ownerId = pa.getOwner().getId();
		this.ownerCountry = pa.getOwner().getCountry().getName();
		this.ownerPhoto = pa.getOwner().getPhotoPath();
		this.baseSketch = pa.getPuzzle().getBaseSketch();
		
	}
	
	public Long getPartId() {
		return partId;
	}
	public void setPartId(Long partId) {
		this.partId = partId;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Long getPuzzleId() {
		return puzzleId;
	}
	public void setPuzzleId(Long puzzleId) {
		this.puzzleId = puzzleId;
	}
	public Long getArtworkId() {
		return artworkId;
	}
	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getStarted() {
		return started;
	}
	public void setStarted(long started) {
		this.started = started;
	}
	public long getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
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
	public String getBaseSketch() {
		return baseSketch;
	}
	public void setBaseSketch(String baseSketch) {
		this.baseSketch = baseSketch;
	}
	
	
	
	
}
