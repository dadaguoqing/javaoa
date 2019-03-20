package com.hj.oa.bean;

public class MateriaPurchaseDetail {
	private Integer id;

	private String code;

	private String maCode;

	private String classify;

	private String brand;

	private String spec;

	private String package1;

	private String unit;

	private Double needNum;

	private Double stockNum;

	private String needDate;

	private String useDate;

	private Double buynum;

	private Double price;

	private Double cost;

	private String supplier;

	private String link;

	private String isDeal;

	public String getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
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
		this.code = code == null ? null : code.trim();
	}

	public String getMaCode() {
		return maCode;
	}

	public void setMaCode(String maCode) {
		this.maCode = maCode == null ? null : maCode.trim();
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify == null ? "-" : classify.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? "-" : brand.trim();
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec == null ? "-" : spec.trim();
	}

	public String getPackage1() {
		return package1;
	}

	public void setPackage1(String package1) {
		this.package1 = package1 == null ? "-" : package1.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public Double getNeedNum() {
		return needNum;
	}

	public void setNeedNum(Double needNum) {
		this.needNum = needNum;
	}

	public Double getStockNum() {
		return stockNum;
	}

	public void setStockNum(Double stockNum) {
		this.stockNum = stockNum;
	}

	public String getNeedDate() {
		return needDate;
	}

	public void setNeedDate(String needDate) {
		this.needDate = needDate == null ? null : needDate.trim();
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate == null ? null : useDate.trim();
	}

	public Double getBuynum() {
		return buynum;
	}

	public void setBuynum(Double buynum) {
		this.buynum = buynum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier == null ? null : supplier.trim();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link == null ? null : link.trim();
	}
}