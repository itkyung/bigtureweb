package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class CommentWrapper {
	@Expose
	private boolean success;
	
	@Expose
	private boolean alreadySendSticker;
	
	@Expose
	private String sendedSticker;
	
	@Expose
	private long sendedDate;
	
	@Expose
	private List<CommentDelegate> comments = new ArrayList<CommentDelegate>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isAlreadySendSticker() {
		return alreadySendSticker;
	}

	public void setAlreadySendSticker(boolean alreadySendSticker) {
		this.alreadySendSticker = alreadySendSticker;
	}

	

	public String getSendedSticker() {
		return sendedSticker;
	}

	public void setSendedSticker(String sendedSticker) {
		this.sendedSticker = sendedSticker;
	}

	public long getSendedDate() {
		return sendedDate;
	}

	public void setSendedDate(long sendedDate) {
		this.sendedDate = sendedDate;
	}

	public List<CommentDelegate> getComments() {
		return comments;
	}

	public void setComments(List<CommentDelegate> comments) {
		this.comments = comments;
	}
	
	
	
}
