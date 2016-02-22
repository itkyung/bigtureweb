package com.clockworks.bigture.delegate;

import java.util.List;

import com.google.gson.annotations.Expose;

public class WordWrapper {
	@Expose
	private String initialWord;
	@Expose
	private List<ExpertDelegate> experts;
	public String getInitialWord() {
		return initialWord;
	}
	public void setInitialWord(String initialWord) {
		this.initialWord = initialWord;
	}
	public List<ExpertDelegate> getExperts() {
		return experts;
	}
	public void setExperts(List<ExpertDelegate> experts) {
		this.experts = experts;
	}
	
	
}
