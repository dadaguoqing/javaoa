package com.hj.oa.bean;

import java.io.Serializable;

public class OsSy implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int proposer; //申请人
	private String proposerName;
	private int syType;
	private String createTime;
	private String content;
	private Integer mgrId; //行政主管
	private String mgrCmt;//行政主管意见
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int type;//1-办公用品，2-药品
	private int status; //1-行政主管审核，2-总经理审核，4，审核通过，-1-审核不通过
	
	
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
	public Integer getBossId() {
		return bossId;
	}
	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}
	public String getBossCmt() {
		return bossCmt;
	}
	public void setBossCmt(String bossCmt) {
		this.bossCmt = bossCmt;
	}
	public Integer getCurrentId() {
		return currentId;
	}
	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}
	public Integer getMgrId() {
		return mgrId;
	}
	public void setMgrCmt(String mgrCmt) {
		this.mgrCmt = mgrCmt;
	}
	public String getMgrCmt() {
		return mgrCmt;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setSyType(int syType) {
		this.syType = syType;
	}
	public int getSyType() {
		return syType;
	}
}
