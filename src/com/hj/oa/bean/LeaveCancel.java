package com.hj.oa.bean;

import java.io.Serializable;

public class LeaveCancel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	private int leaveId;//原始请假id
	private int proposer;
	private String proposerName;
	
	private String type;
	private Integer deptId;
	private String createTime;
	private String content;//销假事由
	
	private Integer mgrId;
	private String mgrCmt;
	private Integer directId;
	private String directCmt;
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int status; //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
	
	private String beginTime;//销假开始
	private String endTime;//销假结束
	private int days;
	private int hours;
	private int minutes;
	private int waichu;
	
	private int cancelType;

	private Integer updateId;//注销操作人ID
	private String updateName;//注销操作人姓名
	
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
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
	public void setWaichu(int waichu) {
		this.waichu = waichu;
	}
	public int getWaichu() {
		return waichu;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public int getLeaveId() {
		return leaveId;
	}
	public void setCancelType(int cancelType) {
		this.cancelType = cancelType;
	}
	public int getCancelType() {
		return cancelType;
	}
}
