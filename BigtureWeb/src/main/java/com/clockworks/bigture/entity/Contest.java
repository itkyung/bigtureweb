package com.clockworks.bigture.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import javax.persistence.Entity;

import com.google.gson.annotations.Expose;

/**
 * 컨테스트가 저장되는 엔티티 
 * @author itkyung
 *
 */
@Entity
@Table(name=Contest.TABLE_NAME)
public class Contest {
	public static final String TABLE_NAME = "BT_CONTEST";  //hall_of_fame
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 

	private String mainTitle;
	
	private String menuTitle;
	
	private String subTitle;
	
	@Column(columnDefinition="TEXT")
	private String contents;
	
	private String logo1;
	
	private String link1;
	
	private String logo2;
	
	private String link2;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private boolean deleted;
	
	@Temporal(TemporalType.TIMESTAMP)	
	private Date created;
	
	@Enumerated(EnumType.STRING)
	private ContestStatus status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="winner_fk")
	private User winner;	//winner중에 rank1인사람을 연결한다.
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLogo1() {
		return logo1;
	}

	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}

	public String getLink1() {
		return link1;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}

	public String getLogo2() {
		return logo2;
	}

	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}

	public String getLink2() {
		return link2;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
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
	
	@Transient
	public boolean isActive(){
//		if(startTime != null && endTime != null){
//			
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.HOUR, 0);
//			cal.set(Calendar.MINUTE,0);
//			cal.set(Calendar.SECOND, 0);
//			if(startTime.before(cal.getTime()) && endTime.after(cal.getTime()))
//				return true;
//		}
		if(status.equals(ContestStatus.ING)){
			return true;
		}
		return false;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public ContestStatus getStatus() {
		return status;
	}

	public void setStatus(ContestStatus status) {
		this.status = status;
	}

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
	}
	
	
	
}
