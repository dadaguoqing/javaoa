package com.hj.oa.bean;

import java.io.Serializable;

public class DcCaidan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String description;//菜单描述
	private int price; //价格
	private int providerId; //所属供应商
	private String providerName;
	private int dcId;//所属订餐
	
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
	
}
