package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.StoryPage;
import com.google.gson.annotations.Expose;

public class StoryPageDelegate {
	@Expose
	private Long id;
	@Expose
	private Long storyId;
	@Expose
	private String description;
	@Expose
	private int pageNo;

	public StoryPageDelegate(StoryPage page){
		this.id = page.getId();
		this.storyId = page.getStory().getId();
		this.description = page.getDescription();
		this.pageNo = page.getPageNo();
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStoryId() {
		return storyId;
	}

	public void setStoryId(Long storyId) {
		this.storyId = storyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
}
