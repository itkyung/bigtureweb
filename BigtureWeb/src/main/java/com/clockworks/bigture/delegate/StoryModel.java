package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

public class StoryModel {
	private Long id;
	private String title;
	private String coverImageId;
	
	private boolean coverFromArtwork;
	
	private List<StoryPageModel> pages = new ArrayList<StoryPageModel>();

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

	public String getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(String coverImageId) {
		this.coverImageId = coverImageId;
	}

	public boolean isCoverFromArtwork() {
		return coverFromArtwork;
	}

	public void setCoverFromArtwork(boolean coverFromArtwork) {
		this.coverFromArtwork = coverFromArtwork;
	}

	public List<StoryPageModel> getPages() {
		return pages;
	}

	public void setPages(List<StoryPageModel> pages) {
		this.pages = pages;
	}
	
	
}
