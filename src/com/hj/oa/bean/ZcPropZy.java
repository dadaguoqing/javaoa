package com.hj.oa.bean;

import java.io.Serializable;

public class ZcPropZy implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int proposer;
	private String proposerName;
	private int uid;
	private String uName;
	private String createTime;
	private String content;
	private Integer mgrId;
	private String mgrCmt;
	private Integer mgr2Id;//转入部门
	private String mgr2Cmt;//转入部门
	private Integer directId;
	private String directCmt;
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int status; //1-转出部门经理审核，2-转入部门经理审核，3-总监审核，4-总经理审核，5，审核通过，-1-审核不通过
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public Integer getMgr2Id() {
		return mgr2Id;
	}
	public void setMgr2Id(Integer mgr2Id) {
		this.mgr2Id = mgr2Id;
	}
	public String getMgr2Cmt() {
		return mgr2Cmt;
	}
	public void setMgr2Cmt(String mgr2Cmt) {
		this.mgr2Cmt = mgr2Cmt;
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
	
}
