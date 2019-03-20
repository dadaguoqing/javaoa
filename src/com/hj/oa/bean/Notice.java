package com.hj.oa.bean;

import java.io.Serializable;

public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title;
	private String content;
	private String publisher;//发布者名称
	private String pubName;//发布单位名称
	private String createTime;
	private int status;
	private String type;
	private int showType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public int getShowType() {
		return showType;
	}
	
}
