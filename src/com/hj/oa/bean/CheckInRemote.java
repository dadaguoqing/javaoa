package com.hj.oa.bean;

import java.io.Serializable;

public class CheckInRemote implements Serializable{

	private static final long serialVersionUID = 1L;

	private String dayStr;
	private String checkin;
	private String checkout;
	private String name;
	private String id;
	
	@Override
	public String toString() {
		return "CheckInRemote [checkin=" + checkin + ", checkout=" + checkout
				+ ", dayStr=" + dayStr + ", id=" + id + ", name=" + name + "]";
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
