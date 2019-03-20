package com.hj.oa.bean;

import java.io.Serializable;

public class OsYaopinVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int sqId;
	private int empId;
	private String empName;
	private int num;
	private String dayStr;
	private Integer deptId;
	private int sqType;
	
	private String name;
	private String type;
	private String unit;
	private String code;
	private String classify;
	
	private String zhuzhi;
	private String shuoming;
	
	private String loc;
	
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
	private String zhuyi;
	
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
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getLoc() {
		return loc;
	}
	
}
