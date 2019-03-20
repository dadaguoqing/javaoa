package com.hj.oa.bean;

public class MerchandiseApplyDetail {
    private Integer id;

    private String applyCode;

    private Integer productType;

    private String productModel;

    private Integer quantity;

    private String unit;

    private String productCode;

    private String weekCode;

    private String packageCode;

    private String inboundReason;

    private String supplier;

    private String inboundSource;

    private String location;

    private Integer suplusQuantity;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public MerchandiseApplyDetail(Integer id, String applyCode, Integer productType, String productModel,
			Integer quantity, String unit, String productCode, String weekCode, String packageCode,
			String inboundReason, String supplier, String inboundSource, String location, Integer suplusQuantity,
			String content) {
		super();
		this.id = id;
		this.applyCode = applyCode;
		this.productType = productType;
		this.productModel = productModel;
		this.quantity = quantity;
		this.unit = unit;
		this.productCode = productCode;
		this.weekCode = weekCode;
		this.packageCode = packageCode;
		this.inboundReason = inboundReason;
		this.supplier = supplier;
		this.inboundSource = inboundSource;
		this.location = location;
		this.suplusQuantity = suplusQuantity;
		this.content = content;
	}

	public MerchandiseApplyDetail() {
		super();
	}

	public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getWeekCode() {
        return weekCode;
    }

    public void setWeekCode(String weekCode) {
        this.weekCode = weekCode == null ? null : weekCode.trim();
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode == null ? null : packageCode.trim();
    }

    public String getInboundReason() {
        return inboundReason;
    }

    public void setInboundReason(String inboundReason) {
        this.inboundReason = inboundReason == null ? null : inboundReason.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getInboundSource() {
        return inboundSource;
    }

    public void setInboundSource(String inboundSource) {
        this.inboundSource = inboundSource == null ? null : inboundSource.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getSuplusQuantity() {
        return suplusQuantity;
    }

    public void setSuplusQuantity(Integer suplusQuantity) {
        this.suplusQuantity = suplusQuantity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}