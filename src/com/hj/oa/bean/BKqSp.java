package com.hj.oa.bean;

import java.io.Serializable;

public class BKqSp implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int proposer;
	private String proposerName;
	private String dayStr;
	private String checkin;
	private String checkout;
	private int status;//0：正在审批，1：通过，-1：未通过
	private String cmt;//审批意见
	private int spId; //审批人
	private Integer dailiId;//代理审批人
	private String dailiName;//代理审批人姓名
	private String createTime;//申请的时间
	private String spTime;
	private String content;//补考勤理由
	private String style ;//补考勤的类型
	private String statusName;
	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProposer() {
		return proposer;
	}
	public void setProposer(int proposer) {
		this.proposer = proposer;
	}
	public String getProposerName() {
		return proposerName;
	}
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public int getSpId() {
		return spId;
	}
	public void setSpId(int spId) {
		this.spId = spId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getDailiId() {
		return dailiId;
	}
	public void setDailiId(Integer dailiId) {
		this.dailiId = dailiId;
	}
	public String getDailiName() {
		return dailiName;
	}
	public void setDailiName(String dailiName) {
		this.dailiName = dailiName;
	}
	public void setSpTime(String spTime) {
		this.spTime = spTime;
	}
	public String getSpTime() {
		return spTime;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	
}
