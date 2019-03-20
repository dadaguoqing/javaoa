package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 员工请假的每日详情，对应表t_leave_day_detail
 * @author L
 *
 */
public class LeaveDayDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "LeaveDayDetail [beginTime=" + beginTime + ", beginTimeInt="
				+ beginTimeInt + ", dayStr=" + dayStr + ", dkTimeLen="
				+ dkTimeLen + ", empId=" + empId + ", endTime=" + endTime
				+ ", endTimeInt=" + endTimeInt + ", id=" + id + ", leaveId="
				+ leaveId + ", sjTimeLen=" + sjTimeLen + ", timeLen=" + timeLen
				+ ", type=" + type + ", waichu=" + waichu + "]";
	}
	private int id;
	private int empId;	//请假员工
	private int leaveId; //所属的请假
	private String dayStr;
	private String beginTime;
	private String endTime;
	private int beginTimeInt;
	private int endTimeInt;
	private String type; //请假类型
	private int timeLen;	
	private int dkTimeLen; //年假、病假抵扣的时间
	private int sjTimeLen; //抵扣以后实际请假时间
	private int waichu; //是否外出（0-不是，1-是）
	
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
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTimeLen() {
		return timeLen;
	}
	public void setTimeLen(int timeLen) {
		this.timeLen = timeLen;
	}
	public int getDkTimeLen() {
		return dkTimeLen;
	}
	public void setDkTimeLen(int dkTimeLen) {
		this.dkTimeLen = dkTimeLen;
	}
	public int getSjTimeLen() {
		return sjTimeLen;
	}
	public void setSjTimeLen(int sjTimeLen) {
		this.sjTimeLen = sjTimeLen;
	}
	public int getWaichu() {
		return waichu;
	}
	public void setWaichu(int waichu) {
		this.waichu = waichu;
	}
	
}
