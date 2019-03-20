package com.hj.oa.bean;

import java.io.Serializable;
/**
 * 物料出入库记录
 * @author wqfang
 *
 */
public class MateriaRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mtCode;
	private Integer personId;
	private String personName;
	private String dayStr;
	private String warehouse;
	private double num;
	private String bz;
	private Integer type;//1是入库，0是出库
	private Integer receivedType;//入库类型
	private double stock;
	private String dayStr1;
	private String dayStr2;
	private String use1;
	private String materiaCode;
	private Integer empId;
	private Integer realNum;
	private Integer status;
	private String reason;
	private String temp;
	private String brand;
	private String package1;
	private String spec;
	private Integer warehouseId;
	
	
	
	
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPackage1() {
		return package1;
	}
	public void setPackage1(String package1) {
		this.package1 = package1;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUse1() {
		return use1;
	}
	public void setUse1(String use1) {
		this.use1 = use1;
	}
	public String getMateriaCode() {
		return materiaCode;
	}
	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Integer getRealNum() {
		return realNum;
	}
	public void setRealNum(Integer realNum) {
		this.realNum = realNum;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getDayStr1() {
		return dayStr1;
	}
	public void setDayStr1(String dayStr1) {
		this.dayStr1 = dayStr1;
	}
	public String getDayStr2() {
		return dayStr2;
	}
	public void setDayStr2(String dayStr2) {
		this.dayStr2 = dayStr2;
	}
	public String getMtCode() {
		return mtCode;
	}
	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getReceivedType() {
		return receivedType;
	}
	public void setReceivedType(Integer receivedType) {
		this.receivedType = receivedType;
	}
}
