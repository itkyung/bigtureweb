package com.clockworks.bigture.delegate;

import com.clockworks.bigture.Role;
import com.clockworks.bigture.entity.Country;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.google.gson.annotations.Expose;

public class ProfileDelegate {
	@Expose
	private Long id;
	
	@Expose
	private String photoPath;
	
	@Expose
	private String comment;
	
	@Expose
	private String countryCode;
	
	@Expose
	private String countryName;
	
	//iOS때문에 임시로 제거함.
	@Expose
	private String countryNameKr;
	
	@Expose
	private String job;
	
	@Expose
	private String companyName;

	@Expose
	private String regionName;
	
	@Expose
	private Long regionId;
	
	@Expose
	private boolean pro;
	
	@Expose
	private String nickName;
	
	@Expose
	private String gender;
	
	@Expose 
	private boolean likeYou;

	@Expose
	private int groupCount;
	
	@Expose
	private int loveCount;
	
	@Expose
	private int awesomeCount;
	
	@Expose
	private int wowCount;
	
	@Expose
	private int funCount;
	
	@Expose
	private int fantasticCount;
	
	public ProfileDelegate(User user){
		super();
		initFromUser(user);
	}
	
	public void initFromUser(User user){
		this.id = user.getId();
	
		this.photoPath = user.getPhotoPath();
		this.comment = user.getComment();
	
		Country country = user.getCountry();
		if(country != null){
			this.countryCode = country.getCode();
			this.countryName = country.getName();
			this.countryNameKr = country.getKrName();
			this.regionName = country.getRegion().getName();
			this.regionId = country.getRegion().getId();
		}
		this.job = user.getJob();
		this.companyName = user.getCompany();
		this.nickName = user.getNickName();
		
		this.pro = false;
		for(UserRoles role : user.getRoles()){
			if(role.getRoleName().equals(Role.USER_EXPERT)){
				this.pro = true;
				break;
			}
		}
		if(user.getGender() != null){
			this.gender = user.getGender().name();
		}
		
		this.loveCount = user.getLoveCount();
		this.wowCount = user.getWowCount();
		this.awesomeCount = user.getAwesomeCount();
		this.fantasticCount = user.getFantasticCount();
		this.funCount = user.getFunCount();
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public boolean isPro() {
		return pro;
	}

	public void setPro(boolean pro) {
		this.pro = pro;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isLikeYou() {
		return likeYou;
	}

	public void setLikeYou(boolean likeYou) {
		this.likeYou = likeYou;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}

	public int getAwesomeCount() {
		return awesomeCount;
	}

	public void setAwesomeCount(int awesomeCount) {
		this.awesomeCount = awesomeCount;
	}

	public int getWowCount() {
		return wowCount;
	}

	public void setWowCount(int wowCount) {
		this.wowCount = wowCount;
	}

	public int getFunCount() {
		return funCount;
	}

	public void setFunCount(int funCount) {
		this.funCount = funCount;
	}

	public int getFantasticCount() {
		return fantasticCount;
	}

	public void setFantasticCount(int fantasticCount) {
		this.fantasticCount = fantasticCount;
	}
	
	
	
	
}
