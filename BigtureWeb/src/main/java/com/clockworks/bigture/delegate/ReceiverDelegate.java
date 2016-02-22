package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.PostCardReceiver;
import com.google.gson.annotations.Expose;

public class ReceiverDelegate {
	@Expose
	private Long receiverId;
	@Expose
	private String receiverName;
	@Expose
	private String receiverPhotoPath;
	@Expose
	private String receiverCountry;
	@Expose
	private String receiverJob;
	@Expose
	private boolean viewed;
	@Expose
	private long viewDate;
	
	public ReceiverDelegate(PostCardReceiver receiver){
		this.receiverId = receiver.getReceiver().getId();
		this.receiverName = receiver.getReceiver().getNickName();
		this.receiverPhotoPath = receiver.getReceiver().getPhotoPath();
		this.receiverCountry = receiver.getReceiver().getCountry().getName();
		this.viewed = receiver.isViewed();
		this.viewDate = receiver.getViewDate() == null ? 0 : receiver.getViewDate().getTime();
		this.receiverJob = receiver.getReceiver().getJob();
	}
	
	
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhotoPath() {
		return receiverPhotoPath;
	}
	public void setReceiverPhotoPath(String receiverPhotoPath) {
		this.receiverPhotoPath = receiverPhotoPath;
	}
	public String getReceiverCountry() {
		return receiverCountry;
	}
	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
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


	public String getReceiverJob() {
		return receiverJob;
	}


	public void setReceiverJob(String receiverJob) {
		this.receiverJob = receiverJob;
	}
	
	
}
