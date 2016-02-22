package com.clockworks.bigture.delegate;

import com.clockworks.bigture.Role;
import com.clockworks.bigture.entity.Friends;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.google.gson.annotations.Expose;

public class SimpleProfileDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String nickName;
	
	@Expose
	private String country;
	
	@Expose
	private String countryKr;
	
	@Expose
	private String region;
	
	@Expose
	private Long regionId;
	
	@Expose
	private String photoPath;
	@Expose
	private String gender;
	
	@Expose
	private boolean alreadyLike;
	
	@Expose
	private boolean pro;
	
	@Expose
	private String job;

	public SimpleProfileDelegate(User user){
		this.id = user.getId();
		this.nickName = user.getNickName();
		this.country = user.getCountry().getName();
		this.countryKr = user.getCountry().getKrName();
		this.region = user.getCountry().getRegion().getName();
		this.photoPath = user.getPhotoPath();
		this.gender = user.getGender().name();
		this.regionId = user.getCountry().getRegion().getId();
		
		this.pro = false;
		for(UserRoles role : user.getRoles()){
			if(role.getRoleName().equals(Role.USER_EXPERT)){
				this.pro = true;
				break;
			}
		}
		this.job = user.getJob();
		
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public boolean isAlreadyLike() {
		return alreadyLike;
	}

	public void setAlreadyLike(boolean alreadyLike) {
		this.alreadyLike = alreadyLike;
	}



	public boolean isPro() {
		return pro;
	}



	public void setPro(boolean pro) {
		this.pro = pro;
	}



	public String getJob() {
		return job;
	}



	public void setJob(String job) {
		this.job = job;
	}
	
	
	
}
