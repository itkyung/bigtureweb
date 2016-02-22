package com.clockworks.bigture.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FriendGroupMemberId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3160649108895495549L;

	@ManyToOne
	private FriendGroup group;
	
	@ManyToOne
	private Friends friend;

	public FriendGroup getGroup() {
		return group;
	}

	public void setGroup(FriendGroup group) {
		this.group = group;
	}

	public Friends getFriend() {
		return friend;
	}

	public void setFriend(Friends friend) {
		this.friend = friend;
	}
	
	
	
}
