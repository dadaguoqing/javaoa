package com.hj.oa.bean;

import java.io.Serializable;

public class OsWupin implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String classify;
	private String name;
	private String type;
	private String unit;
	private String code;
	private int companyOnly;
	private int stock;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCompanyOnly() {
		return companyOnly;
	}
	public void setCompanyOnly(int companyOnly) {
		this.companyOnly = companyOnly;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getClassify() {
		return classify;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStock() {
		return stock;
	}
	
}
