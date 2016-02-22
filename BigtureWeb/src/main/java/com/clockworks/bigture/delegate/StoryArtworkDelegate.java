package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.StoryPageArtwork;
import com.google.gson.annotations.Expose;

public class StoryArtworkDelegate extends ArtworkDelegate{
	@Expose
	private Long pageId;

	
	public StoryArtworkDelegate(StoryPageArtwork pa){
		super();
		Artwork artwork = pa.getArtwork();
		initFromArtwork(artwork);
		this.pageId = pa.getPage().getId();
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	
	
	
}
