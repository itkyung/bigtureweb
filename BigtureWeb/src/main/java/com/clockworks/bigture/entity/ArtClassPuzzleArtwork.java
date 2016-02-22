package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name=ArtClassPuzzleArtwork.TABLE_NAME)
public class ArtClassPuzzleArtwork {
	public static final String TABLE_NAME="BT_CLASS_PUZZ_ARTWORK";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="puzzle_fk")	
	private ArtClassPuzzle puzzle;
	
	
	@ManyToOne
	@JoinColumn(name="artwork_fk")	
	private Artwork artwork;
	
	@ManyToOne
	@JoinColumn(name="user_fk")	
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Enumerated(EnumType.STRING)
	private PuzzleStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date started;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireTime;	//해당 퍼즐의 종료 예상시간.
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ended;
	
	@Enumerated(EnumType.STRING)
	private PuzzlePart part;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArtClassPuzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(ArtClassPuzzle puzzle) {
		this.puzzle = puzzle;
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


	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getEnded() {
		return ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}

	

	public PuzzlePart getPart() {
		return part;
	}

	public void setPart(PuzzlePart part) {
		this.part = part;
	}

	public PuzzleStatus getStatus() {
		return status;
	}

	public void setStatus(PuzzleStatus status) {
		this.status = status;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	
}

