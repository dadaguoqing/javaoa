package com.hj.oa.bean;

import java.io.Serializable;

public class ApproveStaff implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门主管
	 */
	private Integer deptDirector;
	/**
	 * 部门总监
	 */
	private Integer deptSupremo;
	/**
	 * 总经理
	 */
	private Integer manager;
	/**
	 * 财务主管
	 */
	private Integer treasurer;
	public Integer getDeptDirector() {
		return deptDirector;
	}
	public void setDeptDirector(Integer deptDirector) {
		this.deptDirector = deptDirector;
	}
	public Integer getDeptSupremo() {
		return deptSupremo;
	}
	public void setDeptSupremo(Integer deptSupremo) {
		this.deptSupremo = deptSupremo;
	}
	public Integer getManager() {
		return manager;
	}
	public void setManager(Integer manager) {
		this.manager = manager;
	}
	public Integer getTreasurer() {
		return treasurer;
	}
	public void setTreasurer(Integer treasurer) {
		this.treasurer = treasurer;
	}
	
	
}
