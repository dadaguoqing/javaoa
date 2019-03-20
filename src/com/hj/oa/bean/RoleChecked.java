package com.hj.oa.bean;

import java.io.Serializable;

public class RoleChecked implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String check;
	
	/** 
	* @return check 
	*/
	public String getCheck() {
		return check;
	}
	/** 
	* @param check 要设置的 check 
	*/
	public void setCheck(String check) {
		this.check = check;
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
}
