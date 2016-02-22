package com.clockworks.bigture.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.gson.annotations.Expose;

/**
 * 전문가의 뉴스를 저장하는 엔티티  
 * @author itkyung
 *
 */
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="IDENTITY")
@Entity
@Table(name=News.TABLE_NAME)
public class News {
	public static final String TABLE_NAME = "BT_NEWS";
	

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@Expose
	private String title;
	
	@Expose
	@Column(length=230)
	private String contents;
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;	//member
	
	@Expose
	@Column(columnDefinition="TEXT")
	private String photo;
	
	@Expose
	@Column(columnDefinition="TEXT")
	private String thumbnail;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;	//create_time
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="news")
	@OrderBy("created")
	private List<NewsImage> images;
	
	@Expose
	private int imageCount;
	
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

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	

	public List<NewsImage> getImages() {
		return images;
	}

	public void setImages(List<NewsImage> images) {
		this.images = images;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	
	
	
}
