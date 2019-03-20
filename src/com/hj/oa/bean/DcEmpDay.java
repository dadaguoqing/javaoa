package com.hj.oa.bean;

import java.io.Serializable;

public class DcEmpDay implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer empId;
	private String empName;
	private Integer dcId;
	private String dayStr;
	private Integer price;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getDcId() {
		return dcId;
	}
	public void setDcId(Integer dcId) {
		this.dcId = dcId;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
