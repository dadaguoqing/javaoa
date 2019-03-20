package com.hj.oa.bean;

public class Seal {
    private Integer id;

    private String sealCompany;

    private String sealName;

    private String sealUnit;

    private Integer sealNum;

    private String content;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSealCompany() {
        return sealCompany;
    }

    public void setSealCompany(String sealCompany) {
        this.sealCompany = sealCompany == null ? null : sealCompany.trim();
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName == null ? null : sealName.trim();
    }

    public String getSealUnit() {
        return sealUnit;
    }

    public void setSealUnit(String sealUnit) {
        this.sealUnit = sealUnit == null ? null : sealUnit.trim();
    }

    public Integer getSealNum() {
        return sealNum;
    }

    public void setSealNum(Integer sealNum) {
        this.sealNum = sealNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}