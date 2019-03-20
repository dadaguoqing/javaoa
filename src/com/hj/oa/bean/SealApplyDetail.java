package com.hj.oa.bean;

public class SealApplyDetail {
    private Integer id;

    private String number;

    private String fileName;

    private String sealCompany;

    private String sealName;

    private Integer fileNum;

    private Integer userNum;

    private String loaction;

    private String content;
    
    private String yzDate;
    
    private String endDate;
    
    private Integer empId;
    
    private Integer sealType;
    
    private Integer status;
    
    private String name;
    
    private String sealTypeStr;
    
    private String fileDetail;
    
    
    public String getFileDetail() {
		return fileDetail;
	}

	public void setFileDetail(String fileDetail) {
		this.fileDetail = fileDetail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSealTypeStr() {
		return sealTypeStr;
	}

	public void setSealTypeStr(String sealTypeStr) {
		this.sealTypeStr = sealTypeStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getSealType() {
		return sealType;
	}

	public void setSealType(Integer sealType) {
		this.sealType = sealType;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
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

    public Integer getFileNum() {
        return fileNum;
    }

    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction == null ? null : loaction.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getYzDate() {
		return yzDate;
	}

	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
    
}