package com.clockworks.bigture.delegate;

import java.util.List;

import com.google.gson.annotations.Expose;

public class MainPageDelegate {
	@Expose
	private List<ArtworkDelegate> artworks;
	@Expose
	private List<ContestDelegate> contests;
	@Expose
	private List<ExpertDelegate> experts;
	@Expose
	private List<StoryDelegate> stories;
	@Expose
	private List<ArtClassDelegate> artClasses;
	
	public List<ArtworkDelegate> getArtworks() {
		return artworks;
	}
	public void setArtworks(List<ArtworkDelegate> artworks) {
		this.artworks = artworks;
	}
	public List<ContestDelegate> getContests() {
		return contests;
	}
	public void setContests(List<ContestDelegate> contests) {
		this.contests = contests;
	}
	public List<ExpertDelegate> getExperts() {
		return experts;
	}
	public void setExperts(List<ExpertDelegate> experts) {
		this.experts = experts;
	}
	public List<StoryDelegate> getStories() {
		return stories;
	}
	public void setStories(List<StoryDelegate> stories) {
		this.stories = stories;
	}
	public List<ArtClassDelegate> getArtClasses() {
		return artClasses;
	}
	public void setArtClasses(List<ArtClassDelegate> artClasses) {
		this.artClasses = artClasses;
	}
	
	
}
