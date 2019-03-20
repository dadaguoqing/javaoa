package com.hj.oa.bean;

import java.io.Serializable;

public class OsCgWupinVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int num;
	private double money;
	private double totalPrice;
	private double subTotal;
	
	private int wpId;
	
	private String classify;
	private String name;
	private String type;
	private String unit;
	private int cgStatus;
	private int stock;
	private int syType;
	private String bz;
	
	
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setCgStatus(int cgStatus) {
		this.cgStatus = cgStatus;
	}
	public int getCgStatus() {
		return cgStatus;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStock() {
		return stock;
	}
	public void setSyType(int syType) {
		this.syType = syType;
	}
	public int getSyType() {
		return syType;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getBz() {
		return bz;
	}
	public void setWpId(int wpId) {
		this.wpId = wpId;
	}
	public int getWpId() {
		return wpId;
	}
}
