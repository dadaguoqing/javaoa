package com.hj.oa.bean;

import java.io.Serializable;

public class SpRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int empId;
	private int dailiId;
	private String empName;
	private String dailiName;
	private int type;
	private int tid;
	private String createTime;
	
	private String sqTime;
	private String sqName;
	private int status;
	
	public String getSqTime() {
		return sqTime;
	}
	public void setSqTime(String sqTime) {
		this.sqTime = sqTime;
	}
	public String getSqName() {
		return sqName;
	}
	public void setSqName(String sqName) {
		this.sqName = sqName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getDailiId() {
		return dailiId;
	}
	public void setDailiId(int dailiId) {
		this.dailiId = dailiId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDailiName() {
		return dailiName;
	}
	public void setDailiName(String dailiName) {
		this.dailiName = dailiName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
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
}
