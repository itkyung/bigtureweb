package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 스토리 페이지에 속하는 아트웍스들 
 * @author itkyung
 *
 */
@Entity
@Table(name=StoryPageArtwork.TABLE_NAME)
public class StoryPageArtwork {
	public static final String TABLE_NAME = "BT_STORY_ARTWORK";
	
	@EmbeddedId
	private PageArtworkId pk;  // = new PageArtworkId();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public PageArtworkId getPk() {
		return pk;
	}

	public void setPk(PageArtworkId pk) {
		this.pk = pk;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Transient
	public StoryPage getPage(){
		return getPk().getPage();
	}
	
	public void setPage(StoryPage page){
		getPk().setPage(page);
	}
	
	@Transient
	public Artwork getArtwork(){
		return getPk().getArtwork();
	}
	
	public void setArtwork(Artwork artwork){
		getPk().setArtwork(artwork);
	}
	
	
}
