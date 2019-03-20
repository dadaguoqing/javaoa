package com.hj.oa.bean;

import java.io.Serializable;

public class MateriaExcel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String materiaCode;
	private double num;
	private String itemNum;
	private String spec;
	private String package1;
	private String type;
	private String classify;
	private String brand;
	private Double demandNum;
	private Double lackNum;
	private String remark;
	private String content;
	private String unit;
	private boolean flag;
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMateriaCode() {
		return materiaCode;
	}
	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPackage1() {
		return package1;
	}
	public void setPackage1(String package1) {
		this.package1 = package1;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Double getDemandNum() {
		return demandNum;
	}
	public void setDemandNum(Double demandNum) {
		this.demandNum = demandNum;
	}
	public Double getLackNum() {
		return lackNum;
	}
	public void setLackNum(Double lackNum) {
		this.lackNum = lackNum;
	}
	
	
	
}
