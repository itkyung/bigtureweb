package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Cacheable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import com.google.gson.annotations.Expose;

/**
 * 모든종류의 아트웍스들의 내용이 저장되는 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=Artwork.TABLE_NAME)
public class Artwork {
	public static final String TABLE_NAME = "BT_ARTWORK";  //bigture_photo
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_fk")
	private User user;	//member
	
	@Column(columnDefinition="TEXT")
	private String photo; //photo
	
	@Column(columnDefinition="TEXT")
	private String thumbnail; //thumbnail
	
	@Column(length=64)
	private String title;
	
	@Column(length=2048)
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contest_fk")
	private Contest contest;	//category
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="story_fk")
	private Story story;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_fk")
	private ArtClass artClass;
	
	private Long referenceId;
	
	private String referenceTitle;
	
	
	private int score;
	
	private int reportCount;
	
	@Enumerated(EnumType.STRING)
	private OsType osType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reportTime;
	
	private boolean blockFlag;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date blockTime;
	
	@Enumerated(EnumType.STRING)
	private ArtworkType type;
	
	@Column(length=255)
	private String drawingPath;	 //drawing
	
	@Enumerated(EnumType.STRING)
	private ShareType shareType; //private  

	
	private boolean deleted;
	
	private int loveCount;
	
	private int awesomeCount;
	
	private int wowCount;
	
	private int funCount;
	
	private int fantasticCount;
	
	private int commentsCount;
	
	
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public boolean isBlockFlag() {
		return blockFlag;
	}

	public void setBlockFlag(boolean blockFlag) {
		this.blockFlag = blockFlag;
	}

	public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
	}

	public ArtworkType getType() {
		return type;
	}

	public void setType(ArtworkType type) {
		this.type = type;
	}

	public String getDrawingPath() {
		return drawingPath;
	}

	public void setDrawingPath(String drawingPath) {
		this.drawingPath = drawingPath;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
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

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public ArtClass getArtClass() {
		return artClass;
	}

	public void setArtClass(ArtClass artClass) {
		this.artClass = artClass;
		
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceTitle() {
		return referenceTitle;
	}

	public void setReferenceTitle(String referenceTitle) {
		this.referenceTitle = referenceTitle;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public OsType getOsType() {
		return osType;
	}

	public void setOsType(OsType osType) {
		this.osType = osType;
	}
	
	
}
