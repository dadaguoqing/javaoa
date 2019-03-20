package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 物料邮件辅助类
 * 
 * @author wqfang
 *
 */
public class MateriaMail implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 物料编码 */
	private String materiaCode;
	/** 规格型号 */
	private String spec;
	/** 封装 */
	private String package1;
	/** 单位 */
	private String unit;
	/** 数量 */
	private double num;
	private double price;
	private double elseCost;
	private double totalMoney;

	public String getMateriaCode() {
		return materiaCode;
	}

	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getElseCost() {
		return elseCost;
	}

	public void setElseCost(double elseCost) {
		this.elseCost = elseCost;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
}
