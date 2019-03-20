package com.hj.oa.bean;

import java.io.Serializable;

public class DingCan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String dayStr; //哪一天
	private String createTime; //啥时候开始的
	private int empId; //谁发布的
	private String empName;//谁发布的
	private String endTime; //预计结束时间
	private int status; //状态0，真正订餐，1结束订餐。-1该订餐被删除
	private String bz; //备注，被删除的原因。
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpName() {
		return empName;
	}
}
