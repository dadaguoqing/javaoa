package com.hj.oa.bean;

import java.io.Serializable;

public class ZcPropWb implements Serializable {

	@Override
	public String toString() {
		return "ZcPropWb [bossCmt=" + bossCmt + ", bossId=" + bossId
				+ ", content=" + content + ", createTime=" + createTime
				+ ", currentId=" + currentId + ", directCmt=" + directCmt
				+ ", directId=" + directId + ", id=" + id + ", mgrCmt="
				+ mgrCmt + ", mgrId=" + mgrId + ", proposer=" + proposer
				+ ", proposerName=" + proposerName + ", status=" + status + "]";
	}
	
	private static final long serialVersionUID = 1L;

	private int id;
	private int proposer;
	private String proposerName;
	private String createTime;
	private String content;
	private Integer mgrId;
	private String mgrCmt;
	private Integer directId;
	private String directCmt;
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int status; //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
	private int wb;//维修0，注销1。
	private int propId;//报修物品id
	private int money;//预计费用
	private String reason;
	
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
	public Integer getMgrId() {
		return mgrId;
	}
	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}
	public String getMgrCmt() {
		return mgrCmt;
	}
	public void setMgrCmt(String mgrCmt) {
		this.mgrCmt = mgrCmt;
	}
	public Integer getDirectId() {
		return directId;
	}
	public void setDirectId(Integer directId) {
		this.directId = directId;
	}
	public String getDirectCmt() {
		return directCmt;
	}
	public void setDirectCmt(String directCmt) {
		this.directCmt = directCmt;
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
	public void setWb(int wb) {
		this.wb = wb;
	}
	public int getWb() {
		return wb;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getPropId() {
		return propId;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getMoney() {
		return money;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReason() {
		return reason;
	}
	
}
