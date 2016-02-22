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

/**
 * 사용자별 푸쉬알림 설정을 하는 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=PushSettings.TABLE_NAME)
public class PushSettings {
	public static final String TABLE_NAME = "BT_PUSH_SETTINGS";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;
	
	@Expose
	private boolean talkCommentPush; //댓글 달렸을때 
	
	@Expose
	private boolean likePush; //like할때 
	
	@Expose
	private boolean picMePush; //pic me 할때 
	
	@Expose
	private boolean contestPush; //컨테스트가 있을때 
	
	@Expose
	private boolean myClassPush; //내가 참여한 클래스 소식 
	
	@Expose
	private boolean cardPush; //카드 수신 알림 
	
	@Expose
	private boolean storyPush; //스토리 오픈소식 
	
	@Expose
	private boolean classPush;  //클래스 오픈소식 
	
	@Expose
	private Boolean pushAlert = true;  //푸쉬알람받기  

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isTalkCommentPush() {
		return talkCommentPush;
	}

	public void setTalkCommentPush(boolean talkCommentPush) {
		this.talkCommentPush = talkCommentPush;
	}

	public boolean isContestPush() {
		return contestPush;
	}

	public void setContestPush(boolean contestPush) {
		this.contestPush = contestPush;
	}

	public boolean isClassPush() {
		return classPush;
	}

	public void setClassPush(boolean classPush) {
		this.classPush = classPush;
	}

	

	public boolean isCardPush() {
		return cardPush;
	}

	public void setCardPush(boolean cardPush) {
		this.cardPush = cardPush;
	}

	public boolean isStoryPush() {
		return storyPush;
	}

	public void setStoryPush(boolean storyPush) {
		this.storyPush = storyPush;
	}


	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isLikePush() {
		return likePush;
	}

	public void setLikePush(boolean likePush) {
		this.likePush = likePush;
	}

	public boolean isPicMePush() {
		return picMePush;
	}

	public void setPicMePush(boolean picMePush) {
		this.picMePush = picMePush;
	}

	public boolean isMyClassPush() {
		return myClassPush;
	}

	public void setMyClassPush(boolean myClassPush) {
		this.myClassPush = myClassPush;
	}

	public Boolean getPushAlert() {
		return pushAlert == null ? true : pushAlert;
	}

	public void setPushAlert(Boolean pushAlert) {
		this.pushAlert = pushAlert;
	}
	
	
}
