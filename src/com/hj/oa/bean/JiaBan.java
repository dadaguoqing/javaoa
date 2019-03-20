package com.hj.oa.bean;

import java.io.Serializable;

public class JiaBan implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	
	@Override
	public String toString() {
		return "JiaBan [bossCmt=" + bossCmt + ", bossId=" + bossId
				+ ", content=" + content + ", createTime=" + createTime
				+ ", currentId=" + currentId + ", dayte=" + dayte + ", deptId="
				+ deptId + ", directCmt=" + directCmt + ", directId="
				+ directId + ", hours=" + hours + ", id=" + id + ", mgrCmt="
				+ mgrCmt + ", mgrId=" + mgrId + ", proposer=" + proposer
				+ ", proposerName=" + proposerName + ", status=" + status
				+ ", type=" + type + "]";
	}
	private int id;
	private int proposer;
	private String proposerName;
	private String dayte;
	private int hours;

	private Integer deptId;
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
	private int type;//0-普通工作日加班，1-国家法定节假日加班。
	
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
	public String getDayte() {
		return dayte;
	}
	public void setDayte(String dayte) {
		this.dayte = dayte;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
