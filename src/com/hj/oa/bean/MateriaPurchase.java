package com.hj.oa.bean;

public class MateriaPurchase {
    private Integer id;

    private Integer empId;
    
    private String empName;

	private String requisitionCode;

    private String reason;

    private String daystr;

    private String purchaseCode;

    private Double countMoney;

    private String content;

    private Integer currentId;

    private Integer status;

    private Integer type;

    private String url;

    private String url2;
    
    private Integer qgId;
    
    private String projectCode;
    
    
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getQgId() {
		return qgId;
	}

	public void setQgId(Integer qgId) {
		this.qgId = qgId;
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

    public String getRequisitionCode() {
        return requisitionCode;
    }

    public void setRequisitionCode(String requisitionCode) {
        this.requisitionCode = requisitionCode == null ? null : requisitionCode.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getDaystr() {
        return daystr;
    }

    public void setDaystr(String daystr) {
        this.daystr = daystr == null ? null : daystr.trim();
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode == null ? null : purchaseCode.trim();
    }

    public Double getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Double countMoney) {
        this.countMoney = countMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2 == null ? null : url2.trim();
    }
    public String getEmpName() {
  		return empName;
  	}

  	public void setEmpName(String empName) {
  		this.empName = empName;
  	}
}