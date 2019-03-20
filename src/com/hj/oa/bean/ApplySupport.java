package com.hj.oa.bean;

import java.io.Serializable;
/**
 * 技术支持
 * @author wqfang
 *
 */
public class ApplySupport implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	/**申请人ID*/
	private int applyId;
	/**唯一标志*/
	private int supportId;
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
	/**协助技术人员*/
	private int FAEId02;
	
	
	public int getFAEId02() {
		return FAEId02;
	}
	public void setFAEId02(int fAEId02) {
		FAEId02 = fAEId02;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSupportId() {
		return supportId;
	}
	public void setSupportId(int supportId) {
		this.supportId = supportId;
	}
	
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
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
	
	
}
