package com.clockworks.bigture.delegate;

import java.util.List;

import com.google.gson.annotations.Expose;

public class NameDelegate {
	@Expose
	private String name;

	@Expose
	private List<ExpertDelegate> experts;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExpertDelegate> getExperts() {
		return experts;
	}

	public void setExperts(List<ExpertDelegate> experts) {
		this.experts = experts;
	}
	
	
}
