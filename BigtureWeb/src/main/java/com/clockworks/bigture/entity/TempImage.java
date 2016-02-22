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
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name=TempImage.TABLE_NAME)
public class TempImage {
	public static final String TABLE_NAME = "BT_TEMP_IMG";
			
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@Expose
	@Column(columnDefinition="TEXT")
	private String photo;
	
	@Expose
	@Column(columnDefinition="TEXT")
	private String thumbnail;
	
	@Expose
	private String contentType;
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Expose
	@Transient
	private String localImagePath;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getLocalImagePath() {
		return localImagePath;
	}

	public void setLocalImagePath(String localImagePath) {
		this.localImagePath = localImagePath;
	}
	
	
}
