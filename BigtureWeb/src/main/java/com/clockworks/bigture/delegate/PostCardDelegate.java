package com.clockworks.bigture.delegate;

import java.util.List;

import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.ShareType;
import com.google.gson.annotations.Expose;

public class PostCardDelegate {
	@Expose
	private Long id;
	
	@Expose
	private Long ownerId;
	
	@Expose
	private String ownerName;
	
	@Expose
	private String ownerPhoto;
	
	@Expose
	private String ownerCountry;
	
	@Expose
	private String countryCode;
	
	@Expose
	private String ownerJob;
	
	@Expose
	private long created;
	
	@Expose
	private String comment;
	
	@Expose
	private int receiverCount;
	
	@Expose
	private String firstReceiverName; 
	
	@Expose
	private String photoPath;
	
	@Expose
	private String photoTitle;
	
	@Expose
	private String photoComment;
	
	@Expose
	private String photoOwnerName;
	
	@Expose
	private String photoOrigin;	//출처
	
	@Expose
	private long photoDate;
	
	@Expose
	private boolean opened;
	
	@Expose
	private String receiverEmails;
	
	@Expose
	private boolean viewed;
	
	@Expose
	private long viewDate;
	
	@Expose
	private List<ReceiverDelegate> receivers;

	public PostCardDelegate(PostCard card,boolean list){
		this.id = card.getId();
		this.ownerId = card.getOwner().getId();
		this.ownerName = card.getOwner().getNickName();
		this.ownerPhoto = card.getOwner().getPhotoPath();
		this.ownerCountry = card.getOwner().getCountry().getName();
		this.countryCode = card.getOwner().getCountry().getCode();
		this.created = card.getCreated().getTime();
		this.comment = card.getComment();
		this.receiverCount = card.getReceiverCount();
		this.firstReceiverName = card.getFirstReceiverName();
		this.photoPath = card.getArtwork().getPhoto();
		this.photoTitle = card.getArtwork().getTitle();
		this.photoComment = card.getArtwork().getComment();
		this.photoOwnerName = card.getArtwork().getUser().getNickName();
		this.photoDate = card.getArtwork().getCreated().getTime();
		this.opened = card.getShareType().equals(ShareType.PRIVATE) ? false : true;
		this.receiverEmails = card.getReceiverEmails();
		this.ownerJob = card.getOwner().getJob();
		
		if(!list){
			ArtworkType type = card.getArtwork().getType();
			
			switch(type){
			case CONTEST:
				this.photoOrigin = "From Contest " + card.getArtwork().getContest().getMainTitle();
				break;
			case STORY:
				this.photoOrigin = "From Bigture story " + card.getArtwork().getStory().getTitle();
				break;
			case CLASS:
				this.photoOrigin = "From Special class " + card.getArtwork().getArtClass().getClassName();
				break;
			}
		}
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

	public String getOwnerPhoto() {
		return ownerPhoto;
	}

	public void setOwnerPhoto(String ownerPhoto) {
		this.ownerPhoto = ownerPhoto;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(int receiverCount) {
		this.receiverCount = receiverCount;
	}


	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoTitle() {
		return photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}

	public String getPhotoComment() {
		return photoComment;
	}

	public void setPhotoComment(String photoComment) {
		this.photoComment = photoComment;
	}

	public String getPhotoOwnerName() {
		return photoOwnerName;
	}

	public void setPhotoOwnerName(String photoOwnerName) {
		this.photoOwnerName = photoOwnerName;
	}

	public long getPhotoDate() {
		return photoDate;
	}

	public void setPhotoDate(long photoDate) {
		this.photoDate = photoDate;
	}

	

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getFirstReceiverName() {
		return firstReceiverName;
	}

	public void setFirstReceiverName(String firstReceiverName) {
		this.firstReceiverName = firstReceiverName;
	}

	public String getReceiverEmails() {
		return receiverEmails;
	}

	public void setReceiverEmails(String receiverEmails) {
		this.receiverEmails = receiverEmails;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public long getViewDate() {
		return viewDate;
	}

	public void setViewDate(long viewDate) {
		this.viewDate = viewDate;
	}

	public List<ReceiverDelegate> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<ReceiverDelegate> receivers) {
		this.receivers = receivers;
	}

	public String getPhotoOrigin() {
		return photoOrigin;
	}

	public void setPhotoOrigin(String photoOrigin) {
		this.photoOrigin = photoOrigin;
	}

	public String getOwnerJob() {
		return ownerJob;
	}

	public void setOwnerJob(String ownerJob) {
		this.ownerJob = ownerJob;
	}
	
	
	
	
}
