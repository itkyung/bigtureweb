package com.clockworks.bigture.delegate;

import java.util.Calendar;
import java.util.Date;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ShareType;
import com.google.gson.annotations.Expose;

public class ArtworkDelegate {
	@Expose
	private Long id;
	
	@Expose
	private Long ownerId;
	
	@Expose
	private String ownerName;
	
	@Expose
	private String title;
	
	@Expose
	private String comment;
	
	@Expose
	private long created;
	
	@Expose
	private int createYear;
	
	@Expose
	private String photo;
	
	@Expose
	private String thumbnail;
	
	@Expose
	private int score;
	
	@Expose
	private boolean opend;
	
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
	private int commentsCount; 
	
	@Expose
	private String ownerPhoto;
	
	@Expose
	private String ownerCountry;
	
	@Expose
	private String ownerCountryKr;
	
	@Expose
	private String countryCode;
	
	@Expose
	private Long ownerRegion;
	
	@Expose
	private boolean spam;
	
	@Expose
	private boolean collected;
	
	@Expose
	private String referenceName;
	
	@Expose
	private String referenceType;
	
	@Expose
	private String osType;
	
	@Expose
	private String drawingPath;
	
	public ArtworkDelegate(){
		
	}
	
	public ArtworkDelegate(Artwork artwork){
		initFromArtwork(artwork);
	}

	public void initFromArtwork(Artwork artwork){
		this.id = artwork.getId();
		this.ownerId = artwork.getUser().getId();
		this.ownerName = artwork.getUser().getNickName();
		this.ownerPhoto = artwork.getUser().getPhotoPath();
		this.title = artwork.getTitle();
		this.comment = artwork.getComment();
		this.created = artwork.getCreated().getTime();
		this.photo = artwork.getPhoto();
		this.thumbnail = artwork.getThumbnail();
		this.score = artwork.getScore();
		this.awesomeCount = artwork.getAwesomeCount();
		this.commentsCount = artwork.getCommentsCount();
		this.fantasticCount = artwork.getFantasticCount();
		this.funCount = artwork.getFunCount();
		this.loveCount = artwork.getLoveCount();
		this.wowCount = artwork.getWowCount();
		this.opend = artwork.getShareType().equals(ShareType.PUBLIC) ? true : false;
		this.ownerCountry = artwork.getUser().getCountry().getName();
		this.ownerCountryKr = artwork.getUser().getCountry().getKrName();
		this.countryCode = artwork.getUser().getCountry().getCode();
		this.ownerRegion = artwork.getUser().getCountry().getRegion().getId();
		Calendar c = Calendar.getInstance();
		c.setTime(artwork.getCreated());
		this.createYear = c.get(Calendar.YEAR);
		
		this.spam = artwork.getReportCount() == 0 ? false : true;
		
		this.referenceName = artwork.getReferenceTitle();
		this.referenceType = artwork.getType().name();
		
		if(artwork.getOsType() == null){
			
		}else{
			this.osType = artwork.getOsType().name();
		}
		
		this.drawingPath = artwork.getDrawingPath();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isOpend() {
		return opend;
	}

	public void setOpend(boolean opend) {
		this.opend = opend;
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

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getOwnerPhoto() {
		return ownerPhoto;
	}

	public void setOwnerPhoto(String ownerPhoto) {
		this.ownerPhoto = ownerPhoto;
	}

	public int getCreateYear() {
		return createYear;
	}

	public void setCreateYear(int createYear) {
		this.createYear = createYear;
	}

	public boolean isSpam() {
		return spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getDrawingPath() {
		return drawingPath;
	}

	public void setDrawingPath(String drawingPath) {
		this.drawingPath = drawingPath;
	}


	
	
}
