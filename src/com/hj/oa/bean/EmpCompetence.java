package com.hj.oa.bean;

import java.io.Serializable;

public class EmpCompetence implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int empId;
	private String pdf;//个人风采
	/** 
	* @return empId 
	*/
	public int getEmpId() {
		return empId;
	}
	/** 
	* @param empId 要设置的 empId 
	*/
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	/** 
	* @return pdf 
	*/
	public String getPdf() {
		return pdf;
	}
	/** 
	* @param pdf 要设置的 pdf 
	*/
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	
}
