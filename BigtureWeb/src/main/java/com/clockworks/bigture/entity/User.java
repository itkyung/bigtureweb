package com.clockworks.bigture.entity;



import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import com.google.gson.annotations.Expose;

/**
 * 회원 엔티티 
 * @author itkyung
 *
 */
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="IDENTITY")
@Cacheable
@Entity
@Table(name=User.TABLE_NAME)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9037659250275328393L;

	public static final String TABLE_NAME = "BT_USER";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 

	@Expose
	@Column(length=320)
	private String loginId;
	
	@Expose
	@Column(length=320)
	private String email;
	
	@Column(length=128)
	private String pwd;
	
	@Expose
	private String twitter;		//twitter
	
	@Expose
	private String facebook;	//facebook
	
	@Expose
	private String firstName;  //f_name
	
	@Expose
	private String lastName;  //l_name
	
	@Expose
	private String nickName; //예전에는 없음. 
	
	@ManyToOne
	@JoinColumn(name="country_fk")
	private Country country; //nation
	
	@Expose
	@Column(length=256)
	private String comment; //comment
	
	@Expose
	@Column(length=255)
	private String photoPath;	//photo
	
	@OneToMany(mappedBy="user",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private Set<UserRoles> roles;   //isPro값에 따라서 expert인지 일반 사용자인지를 결정 
	
	@Expose
	@Column(length=128)
	private String company;		
	
	@Expose
	@Column(length=128)
	private String job;
	
	@Expose
	@Enumerated(EnumType.STRING)
	private Gender gender;	//gender가 null이면 모르는걸로 연결시킴 

	
	@Temporal(TemporalType.DATE)
	private Date birthday;	
	
	@Column(length=200)
	private String gcmId;	//gcm_reg_id
	
	@Enumerated(EnumType.STRING)
	private OsType osType;	//os_type 
	
	@Enumerated(EnumType.STRING)	
	private DeviceType deviceType;	//device_type
	
	@Column(length=50)
	private String osVersion;	//os_version
	
	@Column(length=50)
	private String appVersion; //app_version
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;	//create_time
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	@Enumerated(EnumType.STRING)
	private UserStatus status;	//is_deleted에 따라서 inactive,active정리 
	
	@Expose
	@Column(length=128)
	private String loginToken;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenExpireDate;	//loginToken만료일 - 한달뒤로 설정. 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActivityDate;
	
	
	private int loveCount;
	
	private int awesomeCount;
	
	private int wowCount;
	
	private int funCount;
	
	private int fantasticCount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Set<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRoles> roles) {
		this.roles = roles;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}



	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public OsType getOsType() {
		return osType;
	}

	public void setOsType(OsType osType) {
		this.osType = osType;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public Date getTokenExpireDate() {
		return tokenExpireDate;
	}

	public void setTokenExpireDate(Date tokenExpireDate) {
		this.tokenExpireDate = tokenExpireDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof User)) return false;
		User target = (User)obj;
		return this.id.equals(target.getId());
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

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}
	
	@Transient
	public boolean isKorean(){
		Country ct = getCountry();
		if(ct == null){
			return false;
		}
		return ct.getCode().equals("KOR") ? true : false;
	}
	
}
