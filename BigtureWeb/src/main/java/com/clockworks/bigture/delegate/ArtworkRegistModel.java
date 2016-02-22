package com.clockworks.bigture.delegate;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ArtworkRegistModel {	
	private String title;
	
	private String comment;
	
	private String type;
	
	private Long refId;
	
	private String share;
	
	private MultipartFile file;
	
	private List<MultipartFile> files;
//	
//	private MultipartFile imageFile;
//	
//	private MultipartFile drawFile;
	
	private String osType;
	
	private String localPath;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
//
	public MultipartFile getImageFile() {
		return files.get(0);
	}
//
//	public void setImageFile(MultipartFile imageFile) {
//		this.imageFile = imageFile;
//	}
//
	public MultipartFile getDrawFile() {
		if(files.size() == 2){
			return files.get(1);
		}else{
			return null;
		}
	}
//
//	public void setDrawFile(MultipartFile drawFile) {
//		this.drawFile = drawFile;
//	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
