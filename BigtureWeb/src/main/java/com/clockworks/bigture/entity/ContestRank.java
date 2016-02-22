package com.clockworks.bigture.entity;

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
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * 종료된 컨테스트의 당선작들을 나타내는 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=ContestRank.TABLE_NAME)
public class ContestRank {
	public static final String TABLE_NAME = "BT_CONTEST_RANK";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="artwork_fk")
	private Artwork artwork;
	
	@ManyToOne
	@JoinColumn(name="contest_fk")
	private Contest contest;
	
	private int rank;


	
	
	
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

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
