package com.clockworks.bigture.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name=PostCard.TABLE_NAME)
public class PostCard {
	public static final String TABLE_NAME = "BT_POST_CARD";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="owner_fk")
	private User owner;	//user_idx
	
	
	@ManyToOne
	@JoinColumn(name="artwork_fk")
	private Artwork artwork;
	
	@Column(columnDefinition="TEXT")
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	
	
	private String firstReceiverName;
	
	private boolean deleted;	//true시 보내는 사람의 리스트에서만 사라짐.
	
	@Enumerated(EnumType.STRING)
	private ShareType shareType; //private  
	
	private boolean reply;
	
	@OneToMany(mappedBy="card",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("created")
	private List<PostCardReceiver> receivers;
	
	private String receiverEmails;
	
	private int receiverCount;
	
	private int emailReceiverCount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(int receiverCount) {
		this.receiverCount = receiverCount;
	}


	
	public String getFirstReceiverName() {
		return firstReceiverName;
	}

	public void setFirstReceiverName(String firstReceiverName) {
		this.firstReceiverName = firstReceiverName;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}

	public List<PostCardReceiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<PostCardReceiver> receivers) {
		this.receivers = receivers;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getReceiverEmails() {
		return receiverEmails;
	}

	public void setReceiverEmails(String receiverEmails) {
		this.receiverEmails = receiverEmails;
	}

	public int getEmailReceiverCount() {
		return emailReceiverCount;
	}

	public void setEmailReceiverCount(int emailReceiverCount) {
		this.emailReceiverCount = emailReceiverCount;
	}
	
	
	
}
