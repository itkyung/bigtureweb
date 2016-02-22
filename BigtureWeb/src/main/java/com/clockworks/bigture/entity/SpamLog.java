package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * 사용자가 아트웍스를 신고한 이력 
 * @author itkyung
 *
 */
@Entity
@Table(name=SpamLog.TABLE_NAME)
public class SpamLog {
	public static final String TABLE_NAME = "BT_SPAM_LOG";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="artwork_fk")
	private Artwork artwork;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_fk")
	private User owner;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
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
	
	
	
}
