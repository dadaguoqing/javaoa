package com.hj.oa.bean;

import java.io.Serializable;

public class OsYaopin implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	private String type;
	private String unit;
	private String code;
	private String classify;
	
	private String zhuzhi;
	private String shuoming;
	private String zhuyi;
	
	private String loc;
	private int stock;
	
	public String getZhuzhi() {
		return zhuzhi;
	}
	public void setZhuzhi(String zhuzhi) {
		this.zhuzhi = zhuzhi;
	}
	public String getShuoming() {
		return shuoming;
	}
	public void setShuoming(String shuoming) {
		this.shuoming = shuoming;
	}
	public String getZhuyi() {
		return zhuyi;
	}
	public void setZhuyi(String zhuyi) {
		this.zhuyi = zhuyi;
	}
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
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getClassify() {
		return classify;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getLoc() {
		return loc;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStock() {
		return stock;
	}
	
}
