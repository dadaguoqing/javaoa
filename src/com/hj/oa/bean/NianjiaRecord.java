package com.hj.oa.bean;

import java.io.Serializable;

public class NianjiaRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "NianjiaRecord [bz=" + bz + ", dayStr=" + dayStr + ", empId="
				+ empId + ", empName=" + empName + ", timeLeft=" + timeLeft
				+ ", timeLeftStr=" + timeLeftStr + ", timeLen=" + timeLen
				+ ", timeLenStr=" + timeLenStr + ", type=" + type + "]";
	}
	
	private int id;
	private int empId;
	private String empName;
	private int leaveId;
	private String dayStr;
	private String type; //年假，或者病假抵扣
	private int timeLen; //抵扣的时间
	private int timeLeft; //抵扣之后剩余的时间
	private String bz;
	
	private String tempName;
	
	private String timeLenStr;
	private String timeLeftStr;
	
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
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getBz() {
		return bz;
	}
	public void setTimeLenStr(String timeLenStr) {
		this.timeLenStr = timeLenStr;
	}
	public String getTimeLenStr() {
		return timeLenStr;
	}
	public void setTimeLeftStr(String timeLeftStr) {
		this.timeLeftStr = timeLeftStr;
	}
	public String getTimeLeftStr() {
		return timeLeftStr;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public String getTempName() {
		return tempName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpName() {
		return empName;
	}
	
}
