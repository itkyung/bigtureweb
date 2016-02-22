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

import com.google.gson.annotations.Expose;

@Entity
@Table(name=ArtClassMember.TABLE_NAME)
public class ArtClassMember {
	public static final String TABLE_NAME = "BT_CLASS_MEMBER";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User member;
	
	@ManyToOne
	@JoinColumn(name="class_fk")
	private ArtClass artClass;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public ArtClass getArtClass() {
		return artClass;
	}

	public void setArtClass(ArtClass artClass) {
		this.artClass = artClass;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof ArtClassMember)) return false;
		
		ArtClassMember target = (ArtClassMember)obj;
		
		if(this.artClass.equals(target.getArtClass()) && this.member.equals(target.getMember()))
			return true;
		else return false;
	}
	
	
	
	
}
