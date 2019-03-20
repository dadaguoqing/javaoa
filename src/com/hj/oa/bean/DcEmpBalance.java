package com.hj.oa.bean;

import java.io.Serializable;

public class DcEmpBalance implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int empId;
	private String empName;
	private double balance;
	
	//dingcan added variables zeroth cat
	private int isAuto;
	private int userId;
	private String menuItem;
	private String provider;
	private int price;
	
	public int getAuto(){
		return isAuto;
	}
	
	public void setAuto(int a){
		this.isAuto = a;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public void setUserId(int id){
		this.userId = id;
	}
	
	public int getPrice(){
		return price;
	}
	
	public void setPrice(int p){
		this.price = p;
	}
	
	
	public int getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
	}

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}

	public String getProvider(){
		return provider;
	}
	
	public void setProvider(String prov){
		this.provider = prov;
	}
	
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
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
