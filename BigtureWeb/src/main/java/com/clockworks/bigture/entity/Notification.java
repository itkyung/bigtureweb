package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * 사용자의 알림을 저장하는 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=Notification.TABLE_NAME)
public class Notification {
	public static final String TABLE_NAME = "BT_NOTIFICATION";
	
	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Expose
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	
	@Expose
	private Long refId;
	@Expose
	private String title;
	
	@Expose
	@Column(columnDefinition="TEXT")
	private String msg;	//owner의 국가에 맞게 적당한 글구가 들어간다.
	@Expose
	private String actorName;
	@Expose
	private Long actorId;
	@Expose
	private String actorJob;
	@Expose
	private String actorImage;
	
	@ManyToOne
	@JoinColumn(name="owner_fk")
	private User owner;
	
	private boolean deleted;
	@Expose
	private boolean readFlag;
	
	@Expose
	private long createdTime;
	
	@Expose
	private long contestStartDate;
	
	@Expose
	private long contestEndDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date readDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deleteDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public Long getActorId() {
		return actorId;
	}

	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

	public String getActorJob() {
		return actorJob;
	}

	public void setActorJob(String actorJob) {
		this.actorJob = actorJob;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	
	public boolean isReadFlag() {
		return readFlag;
	}

	public void setReadFlag(boolean readFlag) {
		this.readFlag = readFlag;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public long getContestStartDate() {
		return contestStartDate;
	}

	public void setContestStartDate(long contestStartDate) {
		this.contestStartDate = contestStartDate;
	}

	public long getContestEndDate() {
		return contestEndDate;
	}

	public void setContestEndDate(long contestEndDate) {
		this.contestEndDate = contestEndDate;
	}

	public String getActorImage() {
		return actorImage;
	}

	public void setActorImage(String actorImage) {
		this.actorImage = actorImage;
	}
	
	
}
