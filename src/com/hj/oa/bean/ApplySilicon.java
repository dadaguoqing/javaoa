package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 芯片申请表
 * @author wqfang
 *
 */
public class ApplySilicon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int applyId;//芯片申请ID
	private int empId;//申请人ID
	private String brand;//芯片型号
	private int num;//芯片申请数量
	private String reason;//申请原因
	private String dayStr;//申请日期
	private String useDayStr;//需求日期
	private int deptId;
	private String empName;
	private String packageType;
	private String unit;
	
	
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getUseDayStr() {
		return useDayStr;
	}
	public void setUseDayStr(String useDayStr) {
		this.useDayStr = useDayStr;
	}
	
	
}
