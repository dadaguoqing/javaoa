package com.hj.oa.bean;

public class EmpDayVo extends EmpDaySum{

	private static final long serialVersionUID = 1L;
	
	private String checkin;
	private String checkout;
	private String empName;
	private int jiaban;
	private String type;
	private String email;
	
	private String timeLenStr;
	private String jiabanStr;
	private String sjLenStr;
	private String dkLenStr;
	private String wcLenStr;
	
	public String getTimeLenStr() {
		return timeLenStr;
	}
	public void setTimeLenStr(String timeLenStr) {
		this.timeLenStr = timeLenStr;
	}
	public String getJiabanStr() {
		return jiabanStr;
	}
	public void setJiabanStr(String jiabanStr) {
		this.jiabanStr = jiabanStr;
	}
	public String getSjLenStr() {
		return sjLenStr;
	}
	public void setSjLenStr(String sjLenStr) {
		this.sjLenStr = sjLenStr;
	}
	public String getDkLenStr() {
		return dkLenStr;
	}
	public void setDkLenStr(String dkLenStr) {
		this.dkLenStr = dkLenStr;
	}
	public String getWcLenStr() {
		return wcLenStr;
	}
	public void setWcLenStr(String wcLenStr) {
		this.wcLenStr = wcLenStr;
	}
	public String getUncLenStr() {
		return uncLenStr;
	}
	public void setUncLenStr(String uncLenStr) {
		this.uncLenStr = uncLenStr;
	}
	private String uncLenStr;
	
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getJiaban() {
		return jiaban;
	}
	public void setJiaban(int jiaban) {
		this.jiaban = jiaban;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
}
