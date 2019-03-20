package com.hj.oa.bean;

import java.io.Serializable;
/**
 * 物料
 * @author wqfang
 *
 */
public class Materia implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	/** 物料编码*/
	private String materiaCode;
	/** 品名分类*/
	private String classfiy;
	/** 品牌*/
	private String brand;
	/** 添加日期*/
	private String createTime;
	/** 规格型号*/
	private String spec;
	/** 封装*/
	private String package1;
	/** 单位*/
	private String unit;
	/** 单价*/
	private Double price;
	/** 供应商*/
	private String supplier;
	/** 功能简称（中）*/
	private String functionChina;
	/** 功能简称（英）*/
	private String functionEnglish;
	/** 功能分区*/
	private String diff;
	/** 照片地址*/
	private String url;
	private String url2;
	/** 数量*/
	private double num;
	/** 备注*/
	private String bz;
	private String reason;
	private String mtCode;
	private double warehouse1;
	private double warehouse2;
	private double warehouse3;
	private Double realNum;
	private String all;
	private double mininum;
	private double once;
	private double spNum;
	private double pcsNum;
	private Integer oc;
	private String warehouse;
	private String warehouseId;
	private String others;
	private double needNum;
	private String access;
	private Double stock;
	private double cost;
	
	
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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
	public String getClassfiy() {
		return classfiy;
	}
	public void setClassfiy(String classfiy) {
		this.classfiy = classfiy;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPackage1() {
		return package1;
	}
	public void setPackage1(String package1) {
		this.package1 = package1;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getFunctionChina() {
		return functionChina;
	}
	public void setFunctionChina(String functionChina) {
		this.functionChina = functionChina;
	}
	public String getFunctionEnglish() {
		return functionEnglish;
	}
	public void setFunctionEnglish(String functionEnglish) {
		this.functionEnglish = functionEnglish;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMtCode() {
		return mtCode;
	}
	public void setMtCode(String mtCode) {
		this.mtCode = mtCode;
	}
	public double getWarehouse1() {
		return warehouse1;
	}
	public void setWarehouse1(double warehouse1) {
		this.warehouse1 = warehouse1;
	}
	public double getWarehouse2() {
		return warehouse2;
	}
	public void setWarehouse2(double warehouse2) {
		this.warehouse2 = warehouse2;
	}
	public double getWarehouse3() {
		return warehouse3;
	}
	public void setWarehouse3(double warehouse3) {
		this.warehouse3 = warehouse3;
	}
	public Double getRealNum() {
		return realNum;
	}
	public void setRealNum(Double realNum) {
		this.realNum = realNum;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public double getMininum() {
		return mininum;
	}
	public void setMininum(double mininum) {
		this.mininum = mininum;
	}
	public double getOnce() {
		return once;
	}
	public void setOnce(double once) {
		this.once = once;
	}
	public double getSpNum() {
		return spNum;
	}
	public void setSpNum(double spNum) {
		this.spNum = spNum;
	}
	public double getPcsNum() {
		return pcsNum;
	}
	public void setPcsNum(double pcsNum) {
		this.pcsNum = pcsNum;
	}
	public Integer getOc() {
		return oc;
	}
	public void setOc(Integer oc) {
		this.oc = oc;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public double getNeedNum() {
		return needNum;
	}
	public void setNeedNum(double needNum) {
		this.needNum = needNum;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	
	
	
	
}
