package com.clockworks.bigture.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.google.gson.annotations.Expose;

@Entity
@Table(name=ArtClass.TABLE_NAME)
public class ArtClass {
	public static final String TABLE_NAME = "BT_CLASS";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@Column(columnDefinition="TEXT")
	private String className;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private ClassStatus status;
	
	@Enumerated(EnumType.STRING)
	private ClassShare shareType;
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(length=255)
	private String coverImage;
	

	
	private int memberCount;
	
	private boolean hot;
	
	@Enumerated(EnumType.STRING)
	private ClassType classType;

	@OneToMany(mappedBy="artClass",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("created")
	private List<ArtClassMember> members;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ClassStatus getStatus() {
		return status;
	}

	public void setStatus(ClassStatus status) {
		this.status = status;
	}

	public ClassShare getShareType() {
		return shareType;
	}

	public void setShareType(ClassShare shareType) {
		this.shareType = shareType;
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

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public List<ArtClassMember> getMembers() {
		return members;
	}

	public void setMembers(List<ArtClassMember> members) {
		this.members = members;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((ArtClass)obj).getId());
	}
	
	
}
