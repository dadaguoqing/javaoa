package com.hj.oa.bean;

import java.io.Serializable;

public class TodayOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int empId;
	/**菜单种类id*/
	private int classifyId;
	private int num;
	/**1是自动订餐，-1是不自动订餐*/
	private int autoStatus;
	/**0是没有操作的，1是订餐，2是不订餐*/
	private int todayStatus;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(int classifyId) {
		this.classifyId = classifyId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAutoStatus() {
		return autoStatus;
	}
	public void setAutoStatus(int autoStatus) {
		this.autoStatus = autoStatus;
	}
	public int getTodayStatus() {
		return todayStatus;
	}
	public void setTodayStatus(int todayStatus) {
		this.todayStatus = todayStatus;
	}
	
}
