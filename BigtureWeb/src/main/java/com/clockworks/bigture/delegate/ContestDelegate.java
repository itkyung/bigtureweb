package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.Contest;
import com.google.gson.annotations.Expose;

public class ContestDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String title;
	
	@Expose
	private String subTitle;
	
	@Expose
	private String status;
	
	@Expose
	private String contents;
	
	@Expose
	private String logoPath;
	
	@Expose
	private long startTime;
	
	@Expose
	private long endTime;
	
	@Expose
	private Long winnerId;
	
	@Expose
	private String winnerName;
	
	@Expose
	private String winnerPhoto;
	
	@Expose
	private String winnerCountry;
	
	@Expose
	private String contestImgPath;	//complete인경우 winner의 artworks이미지. 아니면 최신 artworks이미지.

	public ContestDelegate(Contest contest){
		this.id = contest.getId();
		this.title = contest.getMainTitle();
		this.subTitle = contest.getSubTitle();
		this.status = contest.getStatus().name();
		this.contents = contest.getContents();
		this.logoPath = contest.getLogo1();
		this.startTime = contest.getStartTime().getTime();
		this.endTime = contest.getEndTime().getTime();
		if(contest.getWinner() != null){
			this.winnerId = contest.getWinner().getId();
			this.winnerName = contest.getWinner().getNickName();
			this.winnerPhoto = contest.getWinner().getPhotoPath();
			this.winnerCountry = contest.getWinner().getCountry().getName();
		}
		
		
	}
	
	
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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getWinnerPhoto() {
		return winnerPhoto;
	}

	public void setWinnerPhoto(String winnerPhoto) {
		this.winnerPhoto = winnerPhoto;
	}

	public String getWinnerCountry() {
		return winnerCountry;
	}

	public void setWinnerCountry(String winnerCountry) {
		this.winnerCountry = winnerCountry;
	}


	public String getContestImgPath() {
		return contestImgPath;
	}


	public void setContestImgPath(String contestImgPath) {
		this.contestImgPath = contestImgPath;
	}
	
	
}
