package com.hj.oa.bean;

import java.io.Serializable;

public class AuthSP implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "AuthSP [cmt=" + cmt + ", content=" + content + ", id=" + id
				+ ", sender=" + sender + ", senderId=" + senderId + ", status="
				+ status + ", tid=" + tid + ", tids=" + tids + ", type=" + type
				+ "]";
	}
	
	private int id;
	private int senderId;
	private String sender;
	private int tid;
	private String tids;
	private int status;//0：正在审批，1：通过，-1：未通过
	private int type;// 0：用户权限变化，1：权限对应用户变化，2：权限对应菜单变化
	private String cmt;//审批意见
	private String content;//变化的信息
	private String createTime;
	
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
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTids() {
		return tids;
	}
	public void setTids(String tids) {
		this.tids = tids;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public String getCmt() {
		return cmt;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime() {
		return createTime;
	}
}
