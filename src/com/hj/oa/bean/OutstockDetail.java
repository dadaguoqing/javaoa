package com.hj.oa.bean;

import java.io.Serializable;

public class OutstockDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.id
     *
     * @mbggenerated
     */
    private Integer id;
    private String fhDate;
    private String wxbh;
    private String bqz;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.record_id
     *
     * @mbggenerated
     */
    private Integer recordId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.product_model
     *
     * @mbggenerated
     */
    private String productModel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.unit
     *
     * @mbggenerated
     */
    private String unit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.count
     *
     * @mbggenerated
     */
    private Integer count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.product_id
     *
     * @mbggenerated
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_detail.remark
     *
     * @mbggenerated
     */
    private String remark;
    
    private String oriRemark;
    

    /** 
	* @return oriRemark 
	*/
	public String getOriRemark() {
		return oriRemark;
	}

	/** 
	* @param oriRemark 要设置的 oriRemark 
	*/
	public void setOriRemark(String oriRemark) {
		this.oriRemark = oriRemark;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_outstock_detail
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;
    
    

    public String getFhDate() {
		return fhDate;
	}

	public void setFhDate(String fhDate) {
		this.fhDate = fhDate;
	}

	public String getWxbh() {
		return wxbh;
	}

	public void setWxbh(String wxbh) {
		this.wxbh = wxbh;
	}

	public String getBqz() {
		return bqz;
	}

	public void setBqz(String bqz) {
		this.bqz = bqz;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.id
     *
     * @return the value of t_outstock_detail.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.id
     *
     * @param id the value for t_outstock_detail.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.record_id
     *
     * @return the value of t_outstock_detail.record_id
     *
     * @mbggenerated
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.record_id
     *
     * @param recordId the value for t_outstock_detail.record_id
     *
     * @mbggenerated
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.product_name
     *
     * @return the value of t_outstock_detail.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.product_name
     *
     * @param productName the value for t_outstock_detail.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.product_model
     *
     * @return the value of t_outstock_detail.product_model
     *
     * @mbggenerated
     */
    public String getProductModel() {
        return productModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.product_model
     *
     * @param productModel the value for t_outstock_detail.product_model
     *
     * @mbggenerated
     */
    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.unit
     *
     * @return the value of t_outstock_detail.unit
     *
     * @mbggenerated
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.unit
     *
     * @param unit the value for t_outstock_detail.unit
     *
     * @mbggenerated
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.count
     *
     * @return the value of t_outstock_detail.count
     *
     * @mbggenerated
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.count
     *
     * @param count the value for t_outstock_detail.count
     *
     * @mbggenerated
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.product_id
     *
     * @return the value of t_outstock_detail.product_id
     *
     * @mbggenerated
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.product_id
     *
     * @param productId the value for t_outstock_detail.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_detail.remark
     *
     * @return the value of t_outstock_detail.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_detail.remark
     *
     * @param remark the value for t_outstock_detail.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_detail
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OutstockDetail other = (OutstockDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getProductModel() == null ? other.getProductModel() == null : this.getProductModel().equals(other.getProductModel()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_detail
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getProductModel() == null) ? 0 : getProductModel().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_detail
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", recordId=").append(recordId);
        sb.append(", productName=").append(productName);
        sb.append(", productModel=").append(productModel);
        sb.append(", unit=").append(unit);
        sb.append(", count=").append(count);
        sb.append(", productId=").append(productId);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}