package com.hj.oa.bean;

import java.io.Serializable;

public class ZcPropLog implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private int id;
	private int propId;
	private String info;
	private String dayStr;
	
	private Integer empId;
	private String empName;
	private String type;
	
	private String propName;
	private String propSpec;
	private String propCode;
	
	private String name;
	private String code;
	private int typeId;
	private int placeId;
	private String  spec;
	private String placeName;
	
	
	
	
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropSpec() {
		return propSpec;
	}
	public void setPropSpec(String propSpec) {
		this.propSpec = propSpec;
	}
	public String getPropCode() {
		return propCode;
	}
	public void setPropCode(String propCode) {
		this.propCode = propCode;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	
}
