package com.hj.oa.bean;

import java.io.Serializable;

public class ApplyApprove implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int empId; //申请人
	private String empName;
	private String createTime;
	private String content;
	private Integer deptDirectorId; //部门主管
	private String deptOpinion; //部门主管意见
	private Integer adminDirectorId; //行政主管
	private String adminOpinion;//行政主管意见
	private String dayStr;//行政主管意见
	private Integer articleManager;
	private Integer deptId;
	private int currentId;
	private int status; //1-部门主管审核，2-行政主管审核，3-物品管理员，4，审核通过，-1-审核不通过
	private int applyStatus; //0-未处理，1-已经处理。 采购备注状态，只有审核通过才有状态
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getDeptDirectorId() {
		return deptDirectorId;
	}
	public void setDeptDirectorId(Integer deptDirectorId) {
		this.deptDirectorId = deptDirectorId;
	}
	public String getDeptOpinion() {
		return deptOpinion;
	}
	public void setDeptOpinion(String deptOpinion) {
		this.deptOpinion = deptOpinion;
	}
	public Integer getAdminDirectorId() {
		return adminDirectorId;
	}
	public void setAdminDirectorId(Integer adminDirectorId) {
		this.adminDirectorId = adminDirectorId;
	}
	public String getAdminOpinion() {
		return adminOpinion;
	}
	public void setAdminOpinion(String adminOpinion) {
		this.adminOpinion = adminOpinion;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public Integer getArticleManager() {
		return articleManager;
	}
	public void setArticleManager(Integer articleManager) {
		this.articleManager = articleManager;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	
	
	
}
