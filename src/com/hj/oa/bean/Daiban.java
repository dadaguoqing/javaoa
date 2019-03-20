package com.hj.oa.bean;

import java.io.Serializable;

public class Daiban implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int count;
	private String url;
	private int dialiId;
	private String dailiName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setDialiId(int dialiId) {
		this.dialiId = dialiId;
	}
	public int getDialiId() {
		return dialiId;
	}
	public void setDailiName(String dailiName) {
		this.dailiName = dailiName;
	}
	public String getDailiName() {
		return dailiName;
	}
	
}
