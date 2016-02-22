package com.clockworks.bigture.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * 스토리의 페이지 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=StoryPage.TABLE_NAME)
public class StoryPage {
	public static final String TABLE_NAME = "BT_STORY_PAGE";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	private int pageNo;	//page_no;
	
	@ManyToOne
	@JoinColumn(name="story_fk")
	private Story story;	//story_idx
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;	//create_time

	
	
	private int loveCount;
	
	private int awesomeCount;
	
	private int wowCount;
	
	private int funCount;
	
	private int fantasticCount;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StoryPage)) return false;
		if(this.getId() == null) return false;
		
		return this.getId().equals(((StoryPage)obj).getId());
	}
	
	
	
}
