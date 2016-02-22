package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.ContestRank;
import com.google.gson.annotations.Expose;

public class ContestRankDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String photo;
	
	@Expose
	private Long winnerId;
	
	@Expose
	private String winnerPhoto;
	
	@Expose
	private String winnerName;
	
	@Expose
	private String winnerCountry;
	
	@Expose
	private int rank;
	
	@Expose
	private Long artworkId;
	
	@Expose
	private String artworkTitle;
	
	@Expose
	private String artworkComment;
	
	public ContestRankDelegate(ContestRank contestRank){
		this.id = contestRank.getId();
		this.photo = contestRank.getArtwork().getPhoto();
		this.rank = contestRank.getRank();
		this.winnerId = contestRank.getArtwork().getUser().getId();
		this.winnerPhoto = contestRank.getArtwork().getUser().getPhotoPath();
		this.winnerName = contestRank.getArtwork().getUser().getNickName();
		this.winnerCountry = contestRank.getArtwork().getUser().getCountry().getName();
		this.artworkId = contestRank.getArtwork().getId();
		this.artworkTitle = contestRank.getArtwork().getTitle();
		this.artworkComment = contestRank.getArtwork().getComment();
		
		
	}

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

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}

	public String getWinnerPhoto() {
		return winnerPhoto;
	}

	public void setWinnerPhoto(String winnerPhoto) {
		this.winnerPhoto = winnerPhoto;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getWinnerCountry() {
		return winnerCountry;
	}

	public void setWinnerCountry(String winnerCountry) {
		this.winnerCountry = winnerCountry;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Long getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}

	public String getArtworkTitle() {
		return artworkTitle;
	}

	public void setArtworkTitle(String artworkTitle) {
		this.artworkTitle = artworkTitle;
	}

	public String getArtworkComment() {
		return artworkComment;
	}

	public void setArtworkComment(String artworkComment) {
		this.artworkComment = artworkComment;
	}
	
	
	
}
