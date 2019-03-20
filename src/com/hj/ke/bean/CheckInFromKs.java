package com.hj.ke.bean;

import java.io.Serializable;

public class CheckInFromKs implements Serializable {

	@Override
	public String toString() {
		return "CheckIn [checkin=" + checkin + ", checkout=" + checkout
				+ ", id=" + id + ", name=" + name + "]";
	}
	
	private static final long serialVersionUID = 1L;
	
	private String checkin;
	private String checkout;
	private String id;
	private String name;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
