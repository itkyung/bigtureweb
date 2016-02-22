package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.User;
import com.google.gson.annotations.Expose;

public class ExpertDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String name;
	
	@Expose
	private String photo;
	
	@Expose
	private String jobName;
	
	@Expose
	private String country;
	
	@Expose
	private String countryKr;
	
	public ExpertDelegate(User user){
		this.id = user.getId();
		this.name = user.getNickName();
		this.photo = user.getPhotoPath();
		this.jobName = user.getJob();
		this.country = user.getCountry().getName();
		this.countryKr = user.getCountry().getKrName();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
