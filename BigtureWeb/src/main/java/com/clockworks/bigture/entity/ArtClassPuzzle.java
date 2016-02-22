package com.clockworks.bigture.entity;

import java.util.Date;
import java.util.List;

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

import com.google.gson.annotations.Expose;

/**
 * ArtClass의 puzzle들을 표현한다.
 * @author itkyung
 *
 */
@Entity
@Table(name=ArtClassPuzzle.TABLE_NAME)
public class ArtClassPuzzle {
	public static final String TABLE_NAME = "BT_CLASS_PUZZLE";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="class_fk")	
	private ArtClass artClass;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	
	private String baseSketch;
	
	private boolean completed;
	
	@OneToMany(mappedBy="puzzle",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("created")
	private List<ArtClassPuzzleArtwork> fragments;	//퍼즐조각들 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBaseSketch() {
		return baseSketch;
	}

	public void setBaseSketch(String baseSketch) {
		this.baseSketch = baseSketch;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<ArtClassPuzzleArtwork> getFragments() {
		return fragments;
	}

	public void setFragments(List<ArtClassPuzzleArtwork> fragments) {
		this.fragments = fragments;
	}
	
	
}
