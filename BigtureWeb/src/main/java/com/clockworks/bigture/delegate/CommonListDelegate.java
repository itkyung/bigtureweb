package com.clockworks.bigture.delegate;

import java.util.List;

import com.google.gson.annotations.Expose;

public class CommonListDelegate {
	@Expose
	private boolean success;
	@Expose
	private int errno;
	@Expose
	private List<? extends Object> datas;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public List<? extends Object> getDatas() {
		return datas;
	}
	public void setDatas(List<? extends Object> datas) {
		this.datas = datas;
	}
	
	
}
