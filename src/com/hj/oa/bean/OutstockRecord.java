package com.hj.oa.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutstockRecord implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.record_id
     *
     * @mbggenerated
     */
    private Integer recordId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.count
     *
     * @mbggenerated
     */
    private Integer count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.expected_outstock_time
     *
     * @mbggenerated
     */
    private Date expectedOutstockTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.outstock_time
     *
     * @mbggenerated
     */
    private Date outstockTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.shipment_company
     *
     * @mbggenerated
     */
    private String shipmentCompany;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.shipment_order_num
     *
     * @mbggenerated
     */
    private String shipmentOrderNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.shipment_order_pic
     *
     * @mbggenerated
     */
    private String shipmentOrderPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.outstock_order_num
     *
     * @mbggenerated
     */
    private String outstockOrderNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.outstock_order_pic
     *
     * @mbggenerated
     */
    private String outstockOrderPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.operator
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.operator_id
     *
     * @mbggenerated
     */
    private Integer operatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.outstock_type
     *
     * @mbggenerated
     */
    private Boolean outstockType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.order_num
     *
     * @mbggenerated
     */
    private Integer orderNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.comment
     *
     * @mbggenerated
     */
    private String comment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.shipment_receive_num
     *
     * @mbggenerated
     */
    private String shipmentReceiveNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.shipment_receive_pic
     *
     * @mbggenerated
     */
    private String shipmentReceivePic;
    
    private String shTime;
    private String wxbh;
    private String bqz;
    
    

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_outstock_record.receive_time
     *
     * @mbggenerated
     */
    private Date receiveTime;
    
    private List<OutstockDetail> details;

    /** 
	* @return details 
	*/
	public List<OutstockDetail> getDetails() {
		return details;
	}

	/** 
	* @param details 要设置的 details 
	*/
	public void setDetails(List<OutstockDetail> details) {
		this.details = details;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_outstock_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.id
     *
     * @return the value of t_outstock_record.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.id
     *
     * @param id the value for t_outstock_record.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.record_id
     *
     * @return the value of t_outstock_record.record_id
     *
     * @mbggenerated
     */
    public Integer getRecordId() {
        return recordId;
    }
    
    

    public String getShTime() {
		return shTime;
	}

	public void setShTime(String shTime) {
		this.shTime = shTime;
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
     * This method sets the value of the database column t_outstock_record.record_id
     *
     * @param recordId the value for t_outstock_record.record_id
     *
     * @mbggenerated
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.count
     *
     * @return the value of t_outstock_record.count
     *
     * @mbggenerated
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.count
     *
     * @param count the value for t_outstock_record.count
     *
     * @mbggenerated
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.expected_outstock_time
     *
     * @return the value of t_outstock_record.expected_outstock_time
     *
     * @mbggenerated
     */
    public Date getExpectedOutstockTime() {
        return expectedOutstockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.expected_outstock_time
     *
     * @param expectedOutstockTime the value for t_outstock_record.expected_outstock_time
     *
     * @mbggenerated
     */
    public void setExpectedOutstockTime(Date expectedOutstockTime) {
        this.expectedOutstockTime = expectedOutstockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.outstock_time
     *
     * @return the value of t_outstock_record.outstock_time
     *
     * @mbggenerated
     */
    public Date getOutstockTime() {
        return outstockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.outstock_time
     *
     * @param outstockTime the value for t_outstock_record.outstock_time
     *
     * @mbggenerated
     */
    public void setOutstockTime(Date outstockTime) {
        this.outstockTime = outstockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.shipment_company
     *
     * @return the value of t_outstock_record.shipment_company
     *
     * @mbggenerated
     */
    public String getShipmentCompany() {
        return shipmentCompany;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.shipment_company
     *
     * @param shipmentCompany the value for t_outstock_record.shipment_company
     *
     * @mbggenerated
     */
    public void setShipmentCompany(String shipmentCompany) {
        this.shipmentCompany = shipmentCompany == null ? null : shipmentCompany.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.shipment_order_num
     *
     * @return the value of t_outstock_record.shipment_order_num
     *
     * @mbggenerated
     */
    public String getShipmentOrderNum() {
        return shipmentOrderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.shipment_order_num
     *
     * @param shipmentOrderNum the value for t_outstock_record.shipment_order_num
     *
     * @mbggenerated
     */
    public void setShipmentOrderNum(String shipmentOrderNum) {
        this.shipmentOrderNum = shipmentOrderNum == null ? null : shipmentOrderNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.shipment_order_pic
     *
     * @return the value of t_outstock_record.shipment_order_pic
     *
     * @mbggenerated
     */
    public String getShipmentOrderPic() {
        return shipmentOrderPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.shipment_order_pic
     *
     * @param shipmentOrderPic the value for t_outstock_record.shipment_order_pic
     *
     * @mbggenerated
     */
    public void setShipmentOrderPic(String shipmentOrderPic) {
        this.shipmentOrderPic = shipmentOrderPic == null ? null : shipmentOrderPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.outstock_order_num
     *
     * @return the value of t_outstock_record.outstock_order_num
     *
     * @mbggenerated
     */
    public String getOutstockOrderNum() {
        return outstockOrderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.outstock_order_num
     *
     * @param outstockOrderNum the value for t_outstock_record.outstock_order_num
     *
     * @mbggenerated
     */
    public void setOutstockOrderNum(String outstockOrderNum) {
        this.outstockOrderNum = outstockOrderNum == null ? null : outstockOrderNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.outstock_order_pic
     *
     * @return the value of t_outstock_record.outstock_order_pic
     *
     * @mbggenerated
     */
    public String getOutstockOrderPic() {
        return outstockOrderPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.outstock_order_pic
     *
     * @param outstockOrderPic the value for t_outstock_record.outstock_order_pic
     *
     * @mbggenerated
     */
    public void setOutstockOrderPic(String outstockOrderPic) {
        this.outstockOrderPic = outstockOrderPic == null ? null : outstockOrderPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.type
     *
     * @return the value of t_outstock_record.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.type
     *
     * @param type the value for t_outstock_record.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.state
     *
     * @return the value of t_outstock_record.state
     *
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.state
     *
     * @param state the value for t_outstock_record.state
     *
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.operator
     *
     * @return the value of t_outstock_record.operator
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.operator
     *
     * @param operator the value for t_outstock_record.operator
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.operator_id
     *
     * @return the value of t_outstock_record.operator_id
     *
     * @mbggenerated
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.operator_id
     *
     * @param operatorId the value for t_outstock_record.operator_id
     *
     * @mbggenerated
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.update_time
     *
     * @return the value of t_outstock_record.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.update_time
     *
     * @param updateTime the value for t_outstock_record.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.outstock_type
     *
     * @return the value of t_outstock_record.outstock_type
     *
     * @mbggenerated
     */
    public Boolean getOutstockType() {
        return outstockType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.outstock_type
     *
     * @param outstockType the value for t_outstock_record.outstock_type
     *
     * @mbggenerated
     */
    public void setOutstockType(Boolean outstockType) {
        this.outstockType = outstockType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.order_num
     *
     * @return the value of t_outstock_record.order_num
     *
     * @mbggenerated
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.order_num
     *
     * @param orderNum the value for t_outstock_record.order_num
     *
     * @mbggenerated
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.comment
     *
     * @return the value of t_outstock_record.comment
     *
     * @mbggenerated
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.comment
     *
     * @param comment the value for t_outstock_record.comment
     *
     * @mbggenerated
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.shipment_receive_num
     *
     * @return the value of t_outstock_record.shipment_receive_num
     *
     * @mbggenerated
     */
    public String getShipmentReceiveNum() {
        return shipmentReceiveNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.shipment_receive_num
     *
     * @param shipmentReceiveNum the value for t_outstock_record.shipment_receive_num
     *
     * @mbggenerated
     */
    public void setShipmentReceiveNum(String shipmentReceiveNum) {
        this.shipmentReceiveNum = shipmentReceiveNum == null ? null : shipmentReceiveNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.shipment_receive_pic
     *
     * @return the value of t_outstock_record.shipment_receive_pic
     *
     * @mbggenerated
     */
    public String getShipmentReceivePic() {
        return shipmentReceivePic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.shipment_receive_pic
     *
     * @param shipmentReceivePic the value for t_outstock_record.shipment_receive_pic
     *
     * @mbggenerated
     */
    public void setShipmentReceivePic(String shipmentReceivePic) {
        this.shipmentReceivePic = shipmentReceivePic == null ? null : shipmentReceivePic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_outstock_record.receive_time
     *
     * @return the value of t_outstock_record.receive_time
     *
     * @mbggenerated
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_outstock_record.receive_time
     *
     * @param receiveTime the value for t_outstock_record.receive_time
     *
     * @mbggenerated
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_record
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
        OutstockRecord other = (OutstockRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getExpectedOutstockTime() == null ? other.getExpectedOutstockTime() == null : this.getExpectedOutstockTime().equals(other.getExpectedOutstockTime()))
            && (this.getOutstockTime() == null ? other.getOutstockTime() == null : this.getOutstockTime().equals(other.getOutstockTime()))
            && (this.getShipmentCompany() == null ? other.getShipmentCompany() == null : this.getShipmentCompany().equals(other.getShipmentCompany()))
            && (this.getShipmentOrderNum() == null ? other.getShipmentOrderNum() == null : this.getShipmentOrderNum().equals(other.getShipmentOrderNum()))
            && (this.getShipmentOrderPic() == null ? other.getShipmentOrderPic() == null : this.getShipmentOrderPic().equals(other.getShipmentOrderPic()))
            && (this.getOutstockOrderNum() == null ? other.getOutstockOrderNum() == null : this.getOutstockOrderNum().equals(other.getOutstockOrderNum()))
            && (this.getOutstockOrderPic() == null ? other.getOutstockOrderPic() == null : this.getOutstockOrderPic().equals(other.getOutstockOrderPic()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOperatorId() == null ? other.getOperatorId() == null : this.getOperatorId().equals(other.getOperatorId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOutstockType() == null ? other.getOutstockType() == null : this.getOutstockType().equals(other.getOutstockType()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getShipmentReceiveNum() == null ? other.getShipmentReceiveNum() == null : this.getShipmentReceiveNum().equals(other.getShipmentReceiveNum()))
            && (this.getShipmentReceivePic() == null ? other.getShipmentReceivePic() == null : this.getShipmentReceivePic().equals(other.getShipmentReceivePic()))
            && (this.getReceiveTime() == null ? other.getReceiveTime() == null : this.getReceiveTime().equals(other.getReceiveTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_record
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getExpectedOutstockTime() == null) ? 0 : getExpectedOutstockTime().hashCode());
        result = prime * result + ((getOutstockTime() == null) ? 0 : getOutstockTime().hashCode());
        result = prime * result + ((getShipmentCompany() == null) ? 0 : getShipmentCompany().hashCode());
        result = prime * result + ((getShipmentOrderNum() == null) ? 0 : getShipmentOrderNum().hashCode());
        result = prime * result + ((getShipmentOrderPic() == null) ? 0 : getShipmentOrderPic().hashCode());
        result = prime * result + ((getOutstockOrderNum() == null) ? 0 : getOutstockOrderNum().hashCode());
        result = prime * result + ((getOutstockOrderPic() == null) ? 0 : getOutstockOrderPic().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOperatorId() == null) ? 0 : getOperatorId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOutstockType() == null) ? 0 : getOutstockType().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getShipmentReceiveNum() == null) ? 0 : getShipmentReceiveNum().hashCode());
        result = prime * result + ((getShipmentReceivePic() == null) ? 0 : getShipmentReceivePic().hashCode());
        result = prime * result + ((getReceiveTime() == null) ? 0 : getReceiveTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_outstock_record
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
        sb.append(", count=").append(count);
        sb.append(", expectedOutstockTime=").append(expectedOutstockTime);
        sb.append(", outstockTime=").append(outstockTime);
        sb.append(", shipmentCompany=").append(shipmentCompany);
        sb.append(", shipmentOrderNum=").append(shipmentOrderNum);
        sb.append(", shipmentOrderPic=").append(shipmentOrderPic);
        sb.append(", outstockOrderNum=").append(outstockOrderNum);
        sb.append(", outstockOrderPic=").append(outstockOrderPic);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", operator=").append(operator);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", outstockType=").append(outstockType);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", comment=").append(comment);
        sb.append(", shipmentReceiveNum=").append(shipmentReceiveNum);
        sb.append(", shipmentReceivePic=").append(shipmentReceivePic);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}