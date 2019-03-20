package com.hj.oa.bean;

public class SealApply {
    private Integer id;

    private Integer empId;

    private String number;

    private String daystr;

    private Integer sealType;

    private Integer currentId;

    private Integer status;

    private String content;
    
    private String startDate;
    
    private String endDate;
    
    private Integer dealStatus;
    
    private String ptr;
    
    private String wdr;
    
    
    public String getWdr() {
		return wdr;
	}

	public void setWdr(String wdr) {
		this.wdr = wdr;
	}

	public String getPtr() {
		return ptr;
	}

	public void setPtr(String ptr) {
		this.ptr = ptr;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getDaystr() {
        return daystr;
    }

    public void setDaystr(String daystr) {
        this.daystr = daystr == null ? null : daystr.trim();
    }

    public Integer getSealType() {
        return sealType;
    }

    public void setSealType(Integer sealType) {
        this.sealType = sealType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate.replace(",", "");
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate.replace(",", "");
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
    
}