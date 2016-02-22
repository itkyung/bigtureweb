package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name=PostCardReceiver.TABLE_NAME)
public class PostCardReceiver {
	public static final String TABLE_NAME = "BT_POST_RECEIVER";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="receiver_fk")
	private User receiver;
	
	@ManyToOne
	@JoinColumn(name="card_fk")
	private PostCard card;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private boolean deleted;	//true시 받는사람의 리스트에서만 사라짐.
	
	private boolean viewed;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date viewDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public PostCard getCard() {
		return card;
	}

	public void setCard(PostCard card) {
		this.card = card;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}
	
	
	
}
