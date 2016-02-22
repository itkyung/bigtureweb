package com.clockworks.bigture;

import com.google.gson.annotations.Expose;

public class SimpleResult {
	@Expose
	private boolean success;
	@Expose
	private int count;
	@Expose
	private String errorMsg;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	
}
