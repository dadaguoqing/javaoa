package com.hj.oa.bean;

import java.io.Serializable;

public class EmpDaySum implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "EmpDaySum [dayStr=" + dayStr + ", dkLen=" + dkLen + ", empId="
				+ empId + ", id=" + id + ", sjLen=" + sjLen + ", timeLen="
				+ timeLen + ", uncLen=" + uncLen + ", wcLen=" + wcLen + "]";
	}
	private int id;
	private int timeLen; //原始的请假时长
	private int dkLen; //代扣的请假时长
	private int sjLen; //实际要扣除工资的请假时间长度
	private int empId;
	private String dayStr;
	private int uncLen; //不正常考勤时间
	private int wcLen; //外出时间长度
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTimeLen() {
		return timeLen;
	}
	public void setTimeLen(int timeLen) {
		this.timeLen = timeLen;
	}
	public int getDkLen() {
		return dkLen;
	}
	public void setDkLen(int dkLen) {
		this.dkLen = dkLen;
	}
	public int getSjLen() {
		return sjLen;
	}
	public void setSjLen(int sjLen) {
		this.sjLen = sjLen;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public int getUncLen() {
		return uncLen;
	}
	public void setUncLen(int uncLen) {
		this.uncLen = uncLen;
	}
	public int getWcLen() {
		return wcLen;
	}
	public void setWcLen(int wcLen) {
		this.wcLen = wcLen;
	}
	
}
