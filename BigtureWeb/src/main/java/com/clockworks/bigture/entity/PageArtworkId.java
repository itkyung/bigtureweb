package com.clockworks.bigture.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PageArtworkId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -467272912563605104L;
	
	@ManyToOne
	private StoryPage page;
	
	@ManyToOne
	private Artwork artwork;

	
	public StoryPage getPage() {
		return page;
	}

	public void setPage(StoryPage page) {
		this.page = page;
	}

	
	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}
	
	
}
