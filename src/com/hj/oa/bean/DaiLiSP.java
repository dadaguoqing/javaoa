package com.hj.oa.bean;

import java.io.Serializable;

public class DaiLiSP implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int senderId; //发起请求的人
	private String sender; //姓名
	private int uid; //被代理人
	private String mids; //拥有的权限
	private int status;//0：正在审批，1：通过，-1：未通过
	private String cmt;//审批意见
	private String content;//申请的内容
	private String reason;//申请的理由
	private String createTime;//申请的时间
	
	private String beginTime;
	private String endTime;
	private int lifeStatus;
	private String uname;//代理人姓名
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getMids() {
		return mids;
	}
	public void setMids(String mids) {
		this.mids = mids;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setLifeStatus(int lifeStatus) {
		this.lifeStatus = lifeStatus;
	}
	public int getLifeStatus() {
		return lifeStatus;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUname() {
		return uname;
	}
	
}
