package com.clockworks.bigture.delegate;

import com.google.gson.annotations.Expose;

public class RegionCommentCount {
	@Expose
	private Long regionId;
	@Expose
	private int count;
	
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
