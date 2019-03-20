package com.hj.oa.bean;

import java.io.Serializable;

public class ZcType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Integer pId;
	private String name;
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
