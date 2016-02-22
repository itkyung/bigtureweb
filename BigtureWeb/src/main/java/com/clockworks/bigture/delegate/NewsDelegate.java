package com.clockworks.bigture.delegate;

import java.util.List;

public class NewsDelegate {
	private Long id;
	
	private String title;
	private String contents;
	
	private List<String> addedImages;
	private List<String> deletedImages;
	
	
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public List<String> getAddedImages() {
		return addedImages;
	}
	public void setAddedImages(List<String> addedImages) {
		this.addedImages = addedImages;
	}
	public List<String> getDeletedImages() {
		return deletedImages;
	}
	public void setDeletedImages(List<String> deletedImages) {
		this.deletedImages = deletedImages;
	}
	
	
}
