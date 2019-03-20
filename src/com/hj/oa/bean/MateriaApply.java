package com.hj.oa.bean;

import java.io.Serializable;

public class MateriaApply implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer empId;
	private String use1;
	private String code;
	private String warehouse;
	private String warehouse2;
	private String dayStr;
	private String bz;
	/**1.申请 2.报废 3.调拨 4.出库*/
	private Integer type;
	private Integer dId;
	private String dResult;
	private String dOpinion;
	private String dDayStr;
	private Integer pId;
	private String pResult;
	private String pOpinion;
	private String pDayStr;
	private Integer mId;
	private String mResult;
	private String mOpinion;
	private String mDayStr;
	private Integer aId;
	private String aResult;
	private String aOpinion;
	private String aDayStr;
	private Integer faId;
	private String faResult;
	private String faOpinion;
	private String faDayStr;
	private Integer fId;
	private String fResult;
	private String fOpinion;
	private String fDayStr;
	private Integer currentId;
	private Integer status;
	private Integer warehouseId;
	private String url;
	private String url2;
	private String name;
	
	
	
	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWarehouse2() {
		return warehouse2;
	}

	public void setWarehouse2(String warehouse2) {
		this.warehouse2 = warehouse2;
	}

	public Integer getFaId() {
		return faId;
	}

	public void setFaId(Integer faId) {
		this.faId = faId;
	}

	public String getFaResult() {
		return faResult;
	}

	public void setFaResult(String faResult) {
		this.faResult = faResult;
	}

	public String getFaOpinion() {
		return faOpinion;
	}

	public void setFaOpinion(String faOpinion) {
		this.faOpinion = faOpinion;
	}

	public String getFaDayStr() {
		return faDayStr;
	}

	public void setFaDayStr(String faDayStr) {
		this.faDayStr = faDayStr;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfResult() {
		return fResult;
	}

	public void setfResult(String fResult) {
		this.fResult = fResult;
	}

	public String getfOpinion() {
		return fOpinion;
	}

	public void setfOpinion(String fOpinion) {
		this.fOpinion = fOpinion;
	}

	public String getfDayStr() {
		return fDayStr;
	}

	public void setfDayStr(String fDayStr) {
		this.fDayStr = fDayStr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getdResult() {
		return dResult;
	}

	public void setdResult(String dResult) {
		this.dResult = dResult;
	}

	public String getpResult() {
		return pResult;
	}

	public void setpResult(String pResult) {
		this.pResult = pResult;
	}

	public String getmResult() {
		return mResult;
	}

	public void setmResult(String mResult) {
		this.mResult = mResult;
	}

	public String getaResult() {
		return aResult;
	}

	public void setaResult(String aResult) {
		this.aResult = aResult;
	}

	public Integer getaId() {
		return aId;
	}

	public void setaId(Integer aId) {
		this.aId = aId;
	}

	public String getaOpinion() {
		return aOpinion;
	}

	public void setaOpinion(String aOpinion) {
		this.aOpinion = aOpinion;
	}

	public String getaDayStr() {
		return aDayStr;
	}

	public void setaDayStr(String aDayStr) {
		this.aDayStr = aDayStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getUse1() {
		return use1;
	}

	public void setUse1(String use1) {
		this.use1 = use1;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getDayStr() {
		return dayStr;
	}

	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public String getdOpinion() {
		return dOpinion;
	}

	public void setdOpinion(String dOpinion) {
		this.dOpinion = dOpinion;
	}

	public String getdDayStr() {
		return dDayStr;
	}

	public void setdDayStr(String dDayStr) {
		this.dDayStr = dDayStr;
	}

	
	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getpOpinion() {
		return pOpinion;
	}

	public void setpOpinion(String pOpinion) {
		this.pOpinion = pOpinion;
	}

	public String getpDayStr() {
		return pDayStr;
	}

	public void setpDayStr(String pDayStr) {
		this.pDayStr = pDayStr;
	}

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public String getmOpinion() {
		return mOpinion;
	}

	public void setmOpinion(String mOpinion) {
		this.mOpinion = mOpinion;
	}

	public String getmDayStr() {
		return mDayStr;
	}

	public void setmDayStr(String mDayStr) {
		this.mDayStr = mDayStr;
	}

	public Integer getCurrentId() {
		return currentId;
	}

	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
