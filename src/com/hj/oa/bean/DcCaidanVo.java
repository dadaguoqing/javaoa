package com.hj.oa.bean;

import java.io.Serializable;

public class DcCaidanVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String description;//菜单描述
	private int price; //价格
	private int providerId; //所属供应商
	private String providerName;
	private int dcId;//所属订餐
	private int cdId;
	private int num;
	private int priceAll;
	private String empName;
	private int empId;
	private String dayStr;
	private int empDcId;
	
	
	public int getEmpDcId() {
		return empDcId;
	}
	public void setEmpDcId(int empDcId) {
		this.empDcId = empDcId;
	}
	public int getCdId() {
		return cdId;
	}
	public void setCdId(int cdId) {
		this.cdId = cdId;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPriceAll() {
		return priceAll;
	}
	public void setPriceAll(int priceAll) {
		this.priceAll = priceAll;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	public int getDcId() {
		return dcId;
	}
	public void setDcId(int dcId) {
		this.dcId = dcId;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getDayStr() {
		return dayStr;
	}
	
}
