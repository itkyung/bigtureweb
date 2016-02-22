package com.clockworks.bigture.delegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.clockworks.bigture.Role;
import com.clockworks.bigture.entity.Country;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.google.gson.annotations.Expose;

public class UserDelegate {
	
	@Expose
	private Long id;
	
	@Expose
	private String firstName;
	
	@Expose
	private String lastName;
	
	@Expose
	private String loginId;
	
	@Expose
	private String email;
	
	@Expose
	private String twitter;
	
	@Expose
	private String facebook;
	
	@Expose
	private String photoPath;
	
	@Expose
	private String comment;
	
	@Expose
	private String countryCode;
	
	@Expose
	private String countryName;
	
	//iOS 때문에 임시로 제거함.
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
	private String loginToken;
	
	@Expose
	private String nickName;
	
	@Expose
	private String socialType;
	
	@Expose
	private String socialId;
	
	private String password;
	
	@Expose
	private String gender;
	
	@Expose
	private String birthDay;

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
	
	
	private String createStr;
	
	
	private String lastLoginDateStr;
	
	
	private String osType;
	
	
	private String appVersion;
	
	private String saveMode="ALL";	//ALL,ONLY_PROFILE,ONLY_SETTING
	
	private Long tempImageId;
	
	
	private boolean talkCommentPush; //댓글 달렸을때 
	
	
	private boolean likePush; //like할때 
	
	
	private boolean picMePush; //pic me 할때 
	
	
	private boolean contestPush; //컨테스트가 있을때 
	
	
	private boolean myClassPush; //내가 참여한 클래스 소식 
	
	
	private boolean cardPush; //카드 수신 알림 
	
	
	private boolean storyPush; //스토리 오픈소식 
	
	
	private boolean classPush;  //클래스 오픈소식 
	
	private boolean pushAlert;
	
	private DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	public UserDelegate(){
		super();
	}
	
	public UserDelegate(User user){
		super();
		
		initFromUser(user);
	}
	
	public void initFromUser(User user){
		this.id = user.getId();
		this.loginId = user.getLoginId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.twitter = user.getTwitter();
		this.facebook = user.getFacebook();
		this.photoPath = user.getPhotoPath();
		this.comment = user.getComment();
		this.loginToken = user.getLoginToken();
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
		
		if(twitter != null){
			this.socialId = twitter;
			this.socialType = "TWITTER";
		}else if(facebook != null){
			this.socialId = facebook;
			this.socialType = "FACEBOOK";
		}
		
		if(user.getBirthday() != null){
			this.birthDay = fm.format(user.getBirthday());
		}
		
		this.loveCount = user.getLoveCount();
		this.wowCount = user.getWowCount();
		this.awesomeCount = user.getAwesomeCount();
		this.fantasticCount = user.getFantasticCount();
		this.funCount = user.getFunCount();
		
		this.createStr = fm.format(user.getCreated());
		
		if(user.getLastLoginDate() == null){
			this.lastLoginDateStr = "없음";
		}else{
			this.lastLoginDateStr = fm.format(user.getLastLoginDate());
		}
		
		this.osType = user.getOsType() == null ? "없음" : user.getOsType().name();
		this.appVersion = user.getAppVersion();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
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



	public boolean isPro() {
		return pro;
	}

	public void setPro(boolean pro) {
		this.pro = pro;
	}


	public String getLoginToken() {
		return loginToken;
	}


	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSocialType() {
		return socialType;
	}

	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
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

	public Long getTempImageId() {
		return tempImageId;
	}

	public void setTempImageId(Long tempImageId) {
		this.tempImageId = tempImageId;
	}

	public boolean isTalkCommentPush() {
		return talkCommentPush;
	}

	public void setTalkCommentPush(boolean talkCommentPush) {
		this.talkCommentPush = talkCommentPush;
	}

	public boolean isLikePush() {
		return likePush;
	}

	public void setLikePush(boolean likePush) {
		this.likePush = likePush;
	}

	public boolean isPicMePush() {
		return picMePush;
	}

	public void setPicMePush(boolean picMePush) {
		this.picMePush = picMePush;
	}

	public boolean isContestPush() {
		return contestPush;
	}

	public void setContestPush(boolean contestPush) {
		this.contestPush = contestPush;
	}

	public boolean isMyClassPush() {
		return myClassPush;
	}

	public void setMyClassPush(boolean myClassPush) {
		this.myClassPush = myClassPush;
	}

	public boolean isCardPush() {
		return cardPush;
	}

	public void setCardPush(boolean cardPush) {
		this.cardPush = cardPush;
	}

	public boolean isStoryPush() {
		return storyPush;
	}

	public void setStoryPush(boolean storyPush) {
		this.storyPush = storyPush;
	}

	public boolean isClassPush() {
		return classPush;
	}

	public void setClassPush(boolean classPush) {
		this.classPush = classPush;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public boolean isPushAlert() {
		return pushAlert;
	}

	public void setPushAlert(boolean pushAlert) {
		this.pushAlert = pushAlert;
	}
	
	@Override
	public String toString(){
		return this.nickName + ":" + this.comment;
	}
	
}
