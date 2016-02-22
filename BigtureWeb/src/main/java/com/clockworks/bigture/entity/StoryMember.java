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
 * 해당 스토리에 참여한 사람들.
 * @author itkyung
 *
 */
@Entity
@Table(name=StoryMember.TABLE_NAME)
public class StoryMember {
	public static final String TABLE_NAME = "BT_STORY_MEMBER";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User member;
	
	@ManyToOne
	@JoinColumn(name="story_fk")
	private Story story;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private boolean didItAll;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isDidItAll() {
		return didItAll;
	}

	public void setDidItAll(boolean didItAll) {
		this.didItAll = didItAll;
	}
	
	
}
