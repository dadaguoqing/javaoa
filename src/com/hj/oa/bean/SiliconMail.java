package com.hj.oa.bean;

import java.io.Serializable;

public class SiliconMail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String brand;
	private  int num;
	private String reason;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
