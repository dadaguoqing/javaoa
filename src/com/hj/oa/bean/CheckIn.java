package com.hj.oa.bean;

import java.io.Serializable;

public class CheckIn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "CheckIn [checkin=" + checkin + ", checkinInt=" + checkinInt
				+ ", checkout=" + checkout + ", checkoutInt=" + checkoutInt
				+ ", dayStr=" + dayStr + ", empId=" + empId + "]";
	}
	private String dayStr;
	private String checkin;
	private String checkout;
	private int empId;
	private int checkinInt;
	private int checkoutInt;
	private String name;
	private int proposer;
	private String begin;
	private String end;
	
	
	
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public int getProposer() {
		return proposer;
	}
	public void setProposer(int proposer) {
		this.proposer = proposer;
	}
	public int getCheckinInt() {
		return checkinInt;
	}
	public void setCheckinInt(int checkinInt) {
		this.checkinInt = checkinInt;
	}
	public int getCheckoutInt() {
		return checkoutInt;
	}
	public void setCheckoutInt(int checkoutInt) {
		this.checkoutInt = checkoutInt;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
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
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
