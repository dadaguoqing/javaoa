package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 记录用户的年假，病假还有多少可用
 * @author L
 *
 */
public class EmpNianjia implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "EmpNianjia [bingjia=" + bingjia + ", bingjiaStr=" + bingjiaStr
				+ ", empId=" + empId + ", empName=" + empName + ", id=" + id
				+ ", nianjia=" + nianjia + ", nianjiaStr=" + nianjiaStr + "]";
	}
	
	private int id; 
//	private String year;//哪一年的
	private String empName;
	private int empId;
	private int nianjia;//单位分钟
	private int bingjia;//单位分钟
	
	private String nianjiaStr;
	private String bingjiaStr;
	
	public String getNianjiaStr() {
		return nianjiaStr;
	}
	public void setNianjiaStr(String nianjiaStr) {
		this.nianjiaStr = nianjiaStr;
	}
	public String getBingjiaStr() {
		return bingjiaStr;
	}
	public void setBingjiaStr(String bingjiaStr) {
		this.bingjiaStr = bingjiaStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getNianjia() {
		return nianjia;
	}
	public void setNianjia(int nianjia) {
		this.nianjia = nianjia;
	}
	public int getBingjia() {
		return bingjia;
	}
	public void setBingjia(int bingjia) {
		this.bingjia = bingjia;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpName() {
		return empName;
	}
}
