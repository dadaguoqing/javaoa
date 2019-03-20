package com.hj.oa.bean;

import java.io.Serializable;

public class ZcPropCg implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ZcPropCg [bossCmt=" + bossCmt + ", bossId=" + bossId
				+ ", caiwuCmt=" + caiwuCmt + ", caiwuId=" + caiwuId + ", cgId="
				+ cgId + ", cgStatus=" + cgStatus + ", content=" + content
				+ ", createTime=" + createTime + ", currentId=" + currentId
				+ ", directCmt=" + directCmt + ", directId=" + directId
				+ ", id=" + id + ", proposer=" + proposer + ", proposerName="
				+ proposerName + ", status=" + status + "]";
	}
	
	private int id;
	private int proposer;
	private String proposerName;
	private String createTime;
	private String content;
	private Integer directId;
	private String directCmt;
	private Integer cgId; //采购人
	private Integer caiwuId; //财务主管
	private String caiwuCmt; //财务主管意见
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int status; //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
	
	private int cgStatus; //0-未处理，1-已经处理。 采购备注状态，只有审核通过才有状态
	private String name;
	private double money;
	
	
	
	
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getCgId() {
		return cgId;
	}
	public void setCgId(Integer cgId) {
		this.cgId = cgId;
	}
	public Integer getCaiwuId() {
		return caiwuId;
	}
	public void setCaiwuId(Integer caiwuId) {
		this.caiwuId = caiwuId;
	}
	public String getCaiwuCmt() {
		return caiwuCmt;
	}
	public void setCaiwuCmt(String caiwuCmt) {
		this.caiwuCmt = caiwuCmt;
	}
	public void setCgStatus(int cgStatus) {
		this.cgStatus = cgStatus;
	}
	public int getCgStatus() {
		return cgStatus;
	}
}
