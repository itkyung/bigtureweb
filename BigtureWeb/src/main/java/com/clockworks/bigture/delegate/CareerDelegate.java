package com.clockworks.bigture.delegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.User;
import com.google.gson.annotations.Expose;

public class CareerDelegate {

	@Expose
	private Long id; //idx 
	
	@Expose
	private String career;
	
	@Expose
	private Date startTime;
	
	@Expose
	private Date endTime;
	
	@Expose
	private String startTimeYear;
	
	private final DateFormat fm = new SimpleDateFormat("yyyy");
	
	public CareerDelegate(Career career){
		this.id = career.getId();
		this.career = career.getCareer();
		this.startTime = career.getStartTime();
		this.endTime = career.getEndTime();
		
		if(startTime != null){
			this.startTimeYear = fm.format(startTime);
		}
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCareer() {
		return career;
	}


	public void setCareer(String career) {
		this.career = career;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getStartTimeYear() {
		return startTimeYear;
	}


	public void setStartTimeYear(String startTimeYear) {
		this.startTimeYear = startTimeYear;
	}
	
	
}
