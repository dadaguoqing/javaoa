package com.hj.oa.bean;

import java.io.Serializable;

public class DcEmpBalanceRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int empId;
	private String empName;
	private String type;
	private double money;
	private double balance;
	private String dayTime;
	private String dayStr;
	private String bz;
	
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDayTime() {
		return dayTime;
	}
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getDayStr() {
		return dayStr;
	}
}
