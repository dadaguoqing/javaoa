package com.hj.oa.bean;

import java.io.Serializable;

public class MateriaSeq implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String date;
	private Integer currentId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getCurrentId() {
		return currentId;
	}
	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}
}
