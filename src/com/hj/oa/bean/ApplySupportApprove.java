package com.hj.oa.bean;

import java.io.Serializable;
/**
 * 技术支持
 * @author wqfang
 *
 */
public class ApplySupportApprove implements Serializable{

	private static final long serialVersionUID = 1L;
	/**业务主管审批结果*/
	private String approveResult01;
	/**申请表ID*/
	private int supportId;
	/**业务主管ID*/
	private int busManagerId;
	/**业务主管审批时间*/
	private String approveDate01;
	/**业务主管审批意见*/
	private String approveOpinion01;
	/**技术主管ID*/
	private int tecManagerId;
	/**当前审批人ID*/
	private int currentId;
	/**申请人ID*/
	private int applyId;
	/**申请时间*/
	private String dayStr;
	/**审批状态:1.业务主管审批。2.技术主管审批。3.技术人员审批。4.客户审批。5.技术主管确认。*/
	private int status;
	/**客户单位*/
	private String customer;
	/**开案项目*/
	private String project;
	/**技术支持内容*/
	private String supportContent;
	/**附件文档*/
	private String accessory;
	/**优先级*/
	private String priority;
	/**期望日期*/
	private String expectdTime;
	/**最后期限*/
	private String deadline;
	/**申请人姓名*/
	private String applyName;
	/**申请人部门*/
	private String deptName;
	private int id;
	/**技术主管审批结果*/
	private String approveResult02;
	/**技术主管审批意见*/
	private String approveOpinion02;
	/**技术主管审批时间*/
	private String approveDate02;
	/**附件文档*/
	private String file2;
	/**主技术支持人员*/
	private int FAEId;
	/**协助技术支持人员*/
	private int FAEId02;
	/**技术人员反馈结果*/
	private String FAEResult;
	/**技术人员完成时间*/
	private String FAETime;
	/**客户反馈结果*/
	private String customerResult;
	/**客户反馈意见*/
	private String customerOpinion;
	/**技术人员完成时间*/
	private String customerDate;
	/**1是当前使用的，0是记录*/
	private int currentStatus;
	/**技术部主管最后确认*/
	private String finalResult;
	/**技术部主管最后确认时间*/
	private String endTime;
	/**技术部主管最后确认意见*/
	private String content;
	/**协助技术人员审批*/
	private int currentId2;
	
	
	public int getCurrentId2() {
		return currentId2;
	}
	public void setCurrentId2(int currentId2) {
		this.currentId2 = currentId2;
	}
	public String getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getApproveOpinion02() {
		return approveOpinion02;
	}
	public void setApproveOpinion02(String approveOpinion02) {
		this.approveOpinion02 = approveOpinion02;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApproveResult02() {
		return approveResult02;
	}
	public void setApproveResult02(String approveResult02) {
		this.approveResult02 = approveResult02;
	}
	public String getApproveDate02() {
		return approveDate02;
	}
	public void setApproveDate02(String approveDate02) {
		this.approveDate02 = approveDate02;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public int getFAEId() {
		return FAEId;
	}
	public void setFAEId(int fAEId) {
		FAEId = fAEId;
	}
	public int getFAEId02() {
		return FAEId02;
	}
	public void setFAEId02(int fAEId02) {
		FAEId02 = fAEId02;
	}
	public String getFAEResult() {
		return FAEResult;
	}
	public void setFAEResult(String fAEResult) {
		FAEResult = fAEResult;
	}
	public String getFAETime() {
		return FAETime;
	}
	public void setFAETime(String fAETime) {
		FAETime = fAETime;
	}
	public String getCustomerResult() {
		return customerResult;
	}
	public void setCustomerResult(String customerResult) {
		this.customerResult = customerResult;
	}
	public String getCustomerOpinion() {
		return customerOpinion;
	}
	public void setCustomerOpinion(String customerOpinion) {
		this.customerOpinion = customerOpinion;
	}
	public String getCustomerDate() {
		return customerDate;
	}
	public void setCustomerDate(String customerDate) {
		this.customerDate = customerDate;
	}
	public String getApproveResult01() {
		return approveResult01;
	}
	public void setApproveResult01(String approveResult01) {
		this.approveResult01 = approveResult01;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getSupportContent() {
		return supportContent;
	}
	public void setSupportContent(String supportContent) {
		this.supportContent = supportContent;
	}
	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getExpectdTime() {
		return expectdTime;
	}
	public void setExpectdTime(String expectdTime) {
		this.expectdTime = expectdTime;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public int getSupportId() {
		return supportId;
	}
	public void setSupportId(int supportId) {
		this.supportId = supportId;
	}
	public int getBusManagerId() {
		return busManagerId;
	}
	public void setBusManagerId(int busManagerId) {
		this.busManagerId = busManagerId;
	}
	public String getApproveDate01() {
		return approveDate01;
	}
	public void setApproveDate01(String approveDate01) {
		this.approveDate01 = approveDate01;
	}
	public String getApproveOpinion01() {
		return approveOpinion01;
	}
	public void setApproveOpinion01(String approveOpinion01) {
		this.approveOpinion01 = approveOpinion01;
	}
	public int getTecManagerId() {
		return tecManagerId;
	}
	public void setTecManagerId(int tecManagerId) {
		this.tecManagerId = tecManagerId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	
	
	
}
