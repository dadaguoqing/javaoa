package com.hj.oa.bean;

import java.io.Serializable;

public class EmpDay implements Serializable {

	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj.getClass() != this.getClass()) return false;
		EmpDay ed = (EmpDay) obj;
		if(ed.getEmpId() == this.getEmpId() && this.getDay().equals(ed.getDay())){
			return true;
		}
		return false;
	}
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int empId; 
	private String day;
	private String checkin; 
	private String checkout;
	private String qjst;
	private String qjed;
	private String qjst2;
	private String qjed2;
	private Integer checkinInt; 
	private Integer checkoutInt; 
	private Integer qjstInt;
	private Integer qjedInt;
	private Integer qjst2Int;
	private Integer qjed2Int;
	private int qjLen;
	private int qj2Len;
	private int jiabanLen;
	private int unCommonTime;
	private int type;
	
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
	public String getQjst() {
		return qjst;
	}
	public void setQjst(String qjst) {
		this.qjst = qjst;
	}
	public String getQjed() {
		return qjed;
	}
	public void setQjed(String qjed) {
		this.qjed = qjed;
	}
	public String getQjst2() {
		return qjst2;
	}
	public void setQjst2(String qjst2) {
		this.qjst2 = qjst2;
	}
	public String getQjed2() {
		return qjed2;
	}
	public void setQjed2(String qjed2) {
		this.qjed2 = qjed2;
	}
	public Integer getCheckinInt() {
		return checkinInt;
	}
	public void setCheckinInt(Integer checkinInt) {
		this.checkinInt = checkinInt;
	}
	public Integer getCheckoutInt() {
		return checkoutInt;
	}
	public void setCheckoutInt(Integer checkoutInt) {
		this.checkoutInt = checkoutInt;
	}
	public Integer getQjstInt() {
		return qjstInt;
	}
	public void setQjstInt(Integer qjstInt) {
		this.qjstInt = qjstInt;
	}
	public Integer getQjedInt() {
		return qjedInt;
	}
	public void setQjedInt(Integer qjedInt) {
		this.qjedInt = qjedInt;
	}
	public Integer getQjst2Int() {
		return qjst2Int;
	}
	public void setQjst2Int(Integer qjst2Int) {
		this.qjst2Int = qjst2Int;
	}
	public Integer getQjed2Int() {
		return qjed2Int;
	}
	public void setQjed2Int(Integer qjed2Int) {
		this.qjed2Int = qjed2Int;
	}
	public int getQjLen() {
		return qjLen;
	}
	public void setQjLen(int qjLen) {
		this.qjLen = qjLen;
	}
	public int getQj2Len() {
		return qj2Len;
	}
	public void setQj2Len(int qj2Len) {
		this.qj2Len = qj2Len;
	}
	public int getJiabanLen() {
		return jiabanLen;
	}
	public void setJiabanLen(int jiabanLen) {
		this.jiabanLen = jiabanLen;
	}
	public int getUnCommonTime() {
		return unCommonTime;
	}
	public void setUnCommonTime(int unCommonTime) {
		this.unCommonTime = unCommonTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
