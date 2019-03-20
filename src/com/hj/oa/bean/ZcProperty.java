package com.hj.oa.bean;

import java.io.Serializable;

public class ZcProperty implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String code;
	private String name;
	private String spec;//规格型号
	private int typeId; //所属类型
	private String type;//
	private String buyDate;
	private Integer empId;
	private String empName;
	private Integer placeId;
	private String placeName;
	private Double price;
	private String unit;
	private String brand;
	private int place;
	private int handerId;
	
	private String bz;
	private int status;
	private int ghStatus;//归还状态
	
	private Integer lyId;
	private Integer cgId;
	private Integer cgStatus;
	private double money;
	private String dayStr;//领用日期
	private int num;//专门用于资产偏好，t_zc_prop_code
	
	private String brk;//不入库原因。
	
	private int addType;
	/**
	 * 入库时间
	 */
	private String storeTime;
	
	
	public String getStoreTime() {
		return storeTime;
	}
	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}
	public int getAddType() {
		return addType;
	}
	public void setAddType(int addType) {
		this.addType = addType;
	}
	public int getHanderId() {
		return handerId;
	}
	public void setHanderId(int handerId) {
		this.handerId = handerId;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getCgId() {
		return cgId;
	}
	public void setCgId(Integer cgId) {
		this.cgId = cgId;
	}
	public Integer getCgStatus() {
		return cgStatus;
	}
	public void setCgStatus(Integer cgStatus) {
		this.cgStatus = cgStatus;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setLyId(Integer lyId) {
		this.lyId = lyId;
	}
	public Integer getLyId() {
		return lyId;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setGhStatus(int ghStatus) {
		this.ghStatus = ghStatus;
	}
	public int getGhStatus() {
		return ghStatus;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public void setBrk(String brk) {
		this.brk = brk;
	}
	public String getBrk() {
		return brk;
	}
	
}
