package com.hj.oa.bean;

import java.io.Serializable;

public class OsWupinVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int sqId;
	private int empId;
	private String empName;
	private int num;
	private String dayStr;
	private Integer deptId;
	private int sqType;
	
	private String classify;
	private String name;
	private String type;
	private String unit;
	private String code;
	private int companyOnly;
	private int stock;
	
	public int getSqId() {
		return sqId;
	}
	public void setSqId(int sqId) {
		this.sqId = sqId;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public int getCompanyOnly() {
		return companyOnly;
	}
	public void setCompanyOnly(int companyOnly) {
		this.companyOnly = companyOnly;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getClassify() {
		return classify;
	}
	public void setSqType(int sqType) {
		this.sqType = sqType;
	}
	public int getSqType() {
		return sqType;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStock() {
		return stock;
	}
	
}
