package com.hj.oa.bean;

public class DayLeave {

	private String dayStr;
	private String beginTime;
	private String endTime;
	private int beginTimeInt;
	private int endTimeInt;
	private int timeLen;
	private int empId;
	
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getBeginTimeInt() {
		return beginTimeInt;
	}
	public void setBeginTimeInt(int beginTimeInt) {
		this.beginTimeInt = beginTimeInt;
	}
	public int getEndTimeInt() {
		return endTimeInt;
	}
	public void setEndTimeInt(int endTimeInt) {
		this.endTimeInt = endTimeInt;
	}
	public int getTimeLen() {
		return timeLen;
	}
	public void setTimeLen(int timeLen) {
		this.timeLen = timeLen;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
}
