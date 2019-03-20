package com.hj.oa.bean;

import java.io.Serializable;

public class MateriaDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;
	private String materiaCode;
	private Double num;
	private String reason;
	private Double realNum;
	private Integer applyId;
	private String createdate;
	private int type;
	private String warehouse;
	private Integer warehouseId;
	private Double price;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMateriaCode() {
		return materiaCode;
	}
	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Double getRealNum() {
		return realNum;
	}
	public void setRealNum(Double realNum) {
		this.realNum = realNum;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Integer getApplyId() {
		return applyId;
	}
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
