package com.hj.oa.bean;

public class ExternalInbound {
    private Integer id;

    private String applyCode;

    private Integer inboundNum;

    private String inboundDate;

    private String type;

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

    public Integer getInboundNum() {
        return inboundNum;
    }

    public void setInboundNum(Integer inboundNum) {
        this.inboundNum = inboundNum;
    }

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate == null ? null : inboundDate.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}