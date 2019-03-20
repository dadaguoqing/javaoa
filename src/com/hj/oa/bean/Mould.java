package com.hj.oa.bean;

import java.io.Serializable;

public class Mould implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String url;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
