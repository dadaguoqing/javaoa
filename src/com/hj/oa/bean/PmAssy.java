package com.hj.oa.bean;

public class PmAssy {

	private Integer id;
	private String fzFactory;
	private Integer pid;
	private String createTime;
	private Integer empId;
	private String empName;
	private String cpType;
	private String cpBz;
	private String ddCode;
	private String ddType;
	private int status; //0正在审批。-1审批不通过，1，审批通过，2，正在生产，3，已出库
	
	public String getCpBz() {
		return cpBz;
	}
	public void setCpBz(String cpBz) {
		this.cpBz = cpBz;
	}
	public String getDdCode() {
		return ddCode;
	}
	public void setDdCode(String ddCode) {
		this.ddCode = ddCode;
	}
	public String getDdType() {
		return ddType;
	}
	public void setDdType(String ddType) {
		this.ddType = ddType;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFzFactory() {
		return fzFactory;
	}
	public void setFzFactory(String fzFactory) {
		this.fzFactory = fzFactory;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCpType() {
		return cpType;
	}
	public void setCpType(String cpType) {
		this.cpType = cpType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
