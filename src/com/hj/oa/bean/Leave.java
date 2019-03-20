package com.hj.oa.bean;

import java.io.Serializable;

public class Leave implements Serializable {

	@Override
	public String toString() {
		return "Leave [beginTime=" + beginTime + ", bossCmt=" + bossCmt
				+ ", bossId=" + bossId + ", content=" + content
				+ ", createTime=" + createTime + ", currentId=" + currentId
				+ ", deptId=" + deptId + ", directCmt="
				+ directCmt + ", directId=" + directId + ", endTime=" + endTime
				+  ", id=" + id + ", mgrCmt=" + mgrCmt
				+ ", mgrId=" + mgrId + ", proposer="
				+ proposer + ", proposerName=" + proposerName + ", status="
				+ status + ", type=" + type + ", timeCount=" + timeCount + "]";
	}
	private static final long serialVersionUID = 1L;

	private int id;
	private int proposer;
	private String proposerName;
	private Integer dailiId;
	private String dailiName;
	private String type;
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
	private String beginTime;
	private String endTime;
	private String timeCount;//已经不用了
	private int days;
	private int hours;
	private int minutes;
	private int waichu;
	private String dlMgr;//代理审批的主管
	private String dlDirect;//代理审批的总监
	private String dlBoss;//代理审批的总经理
	public String getDlMgr() {
		return dlMgr;
	}
	public void setDlMgr(String dlMgr) {
		this.dlMgr = dlMgr;
	}
	public String getDlDirect() {
		return dlDirect;
	}
	public void setDlDirect(String dlDirect) {
		this.dlDirect = dlDirect;
	}
	public String getDlBoss() {
		return dlBoss;
	}
	public void setDlBoss(String dlBoss) {
		this.dlBoss = dlBoss;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}
	public String getProposerName() {
		return proposerName;
	}
	public void setTimeCount(String timeCount) {
		this.timeCount = timeCount;
	}
	public String getTimeCount() {
		return timeCount;
	}
	public void setWaichu(int waichu) {
		this.waichu = waichu;
	}
	public int getWaichu() {
		return waichu;
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
}
