package com.clockworks.bigture.delegate;

import net.sf.json.JSONObject;

import com.clockworks.bigture.entity.Notice;
import com.google.gson.annotations.Expose;

public class NoticeDelegate {
	@Expose
	private Long id;
	@Expose
	private String title;
	@Expose
	private String content;
	@Expose
	private long created;
	
	public NoticeDelegate(Notice notice){
		this.id = notice.getId();
		this.title = notice.getTitle();
		this.content = notice.getContent();
		this.created = notice.getCreated().getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}
	
	
	
}
