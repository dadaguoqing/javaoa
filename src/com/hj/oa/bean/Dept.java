package com.hj.oa.bean;

import java.io.Serializable;

public class Dept implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private Integer pid;
	private Integer mgrId;
	private Integer isLeefNode;
	
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getMgrId() {
		return mgrId;
	}
	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}
	public Integer getIsLeefNode() {
		return isLeefNode;
	}
	public void setIsLeefNode(Integer isLeefNode) {
		this.isLeefNode = isLeefNode;
	}
}
