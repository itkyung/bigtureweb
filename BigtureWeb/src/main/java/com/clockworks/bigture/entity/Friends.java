package com.clockworks.bigture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.gson.annotations.Expose;

/**
 * 친구를 나타내는 엔티티 
 * @author itkyung
 *
 */
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="IDENTITY")
@Entity
@Table(name=Friends.TABLE_NAME)
public class Friends {
	public static final String TABLE_NAME = "BT_FRIENDS";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id; //idx 
	
	@ManyToOne
	@JoinColumn(name="owner_fk")
	private User owner;	//user_idx
	
	@ManyToOne
	@JoinColumn(name="friend_fk")
	private User friend; //friend_idx
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	
	private FriendStatus status;  //FRIEND : 1, REQUEST : 2, RECEIVE : 3  --> 앞으로는 무조건 친구가 된다. 상태가 의미가없다.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public FriendStatus getStatus() {
		return status;
	}

	public void setStatus(FriendStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Friends)) return false;
		return this.id.equals( ((Friends)obj).getId());
	}
	
	
}
