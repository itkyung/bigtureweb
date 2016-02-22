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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.gson.annotations.Expose;

/**
 * 스토리를 나타내는 엔티티 
 * @author itkyung
 *
 */
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="MASTER")
@Entity
@Table(name=Story.TABLE_NAME)
public class Story {
	public static final String TABLE_NAME = "BT_STORY";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@Column(length=100)
	private String title; //title
	
	@Column(length=400)
	private String description; //description
	
	private boolean afterReading;	//독후감 등록허용 여부. true,false  after_reading
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_fk")
	private User owner;		//creator_idx
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;	//create_time
	
	private int memberCount;	//participants
	
	@Column(length=255)
	private String coverImage;	//cover_image
	
	private boolean hot;	//hot

	@Enumerated(EnumType.STRING)
	private StoryStatus status;
	
	private int loveCount;
	
	private int awesomeCount;
	
	private int wowCount;
	
	private int funCount;
	
	private int fantasticCount;
	
	private int pageCount;
	
	@OneToMany(mappedBy="story",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("created")
	private List<StoryMember> members;
	
	@OneToMany(mappedBy="story",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	@OrderBy("pageNo")
	private List<StoryPage> pages;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAfterReading() {
		return afterReading;
	}

	public void setAfterReading(boolean afterReading) {
		this.afterReading = afterReading;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public StoryStatus getStatus() {
		return status;
	}

	public void setStatus(StoryStatus status) {
		this.status = status;
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

	public List<StoryMember> getMembers() {
		return members;
	}

	public void setMembers(List<StoryMember> members) {
		this.members = members;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<StoryPage> getPages() {
		return pages;
	}

	public void setPages(List<StoryPage> pages) {
		this.pages = pages;
	}
	
	
	
	
}
