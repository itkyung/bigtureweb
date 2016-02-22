package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

public class PostCardSendDelegate {
	
	private Long artworkId;
	private String comment;
	private boolean reply;
	
	
	private List<String> receiverIds = new ArrayList<String>();
	private String emails;
	
	public Long getArtworkId() {
		return artworkId;
	}
	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<String> getReceiverIds() {
		return receiverIds;
	}
	public void setReceiverIds(List<String> receiverIds) {
		this.receiverIds = receiverIds;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public boolean isReply() {
		return reply;
	}
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	
	
	
}
