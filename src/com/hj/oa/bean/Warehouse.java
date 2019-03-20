package com.hj.oa.bean;

import java.io.Serializable;

public class Warehouse implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String materiaCode;
	private Double warehouse1;
	private Double warehouse2;
	private Double warehouse3;
	private Double cache1;
	private Double cache2;
	private Double cache3;
	
	public Double getCache1() {
		return cache1;
	}
	public void setCache1(Double cache1) {
		this.cache1 = cache1;
	}
	public Double getCache2() {
		return cache2;
	}
	public void setCache2(Double cache2) {
		this.cache2 = cache2;
	}
	public Double getCache3() {
		return cache3;
	}
	public void setCache3(Double cache3) {
		this.cache3 = cache3;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMateriaCode() {
		return materiaCode;
	}
	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
	}
	public Double getWarehouse1() {
		return warehouse1;
	}
	public void setWarehouse1(Double warehouse1) {
		this.warehouse1 = warehouse1;
	}
	public Double getWarehouse2() {
		return warehouse2;
	}
	public void setWarehouse2(Double warehouse2) {
		this.warehouse2 = warehouse2;
	}
	public Double getWarehouse3() {
		return warehouse3;
	}
	public void setWarehouse3(Double warehouse3) {
		this.warehouse3 = warehouse3;
	}
	
}
