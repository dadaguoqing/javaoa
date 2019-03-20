package com.hj.oa.bean;

import java.io.Serializable;

public class Management implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int articleId;
	private String empName;
	private String classify;
	private String createTime;
	private String name;
	private String type;
	private String unit;
	/**备注*/
	private String remark;
	/**行政主管审批意见*/
	private String approveOpinion;
	private int companyOnly;
	/**1 表示删除操作 2表示修改*/
	private int operate;
	/**1 表示审批中 2表示审批通过 -1表示审批不通过*/
	private int status;
	private int adminDirectorId;
	
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getAdminDirectorId() {
		return adminDirectorId;
	}
	public void setAdminDirectorId(int adminDirectorId) {
		this.adminDirectorId = adminDirectorId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public int getCompanyOnly() {
		return companyOnly;
	}
	public void setCompanyOnly(int companyOnly) {
		this.companyOnly = companyOnly;
	}
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
