package com.clockworks.bigture.delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ExpertListWrapper {
	@Expose
	List<WordWrapper> initialWorkList;

	public ExpertListWrapper(){
		initialWorkList = new ArrayList<WordWrapper>();
	}
	
	
	public List<WordWrapper> getInitialWorkList() {
		return initialWorkList;
	}

	public void setInitialWorkList(List<WordWrapper> initialWorkList) {
		this.initialWorkList = initialWorkList;
	}
	
	public void addWord(WordWrapper word){
		initialWorkList.add(word);
	}
	
}
