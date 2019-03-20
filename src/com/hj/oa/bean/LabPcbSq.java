package com.hj.oa.bean;

import java.io.Serializable;

public class LabPcbSq implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private int id;
	private int proposer;
	private String proposerName;
	private String createTime;
	private int type; //1 pcb, 2, 钢板
	
	private String content;
	private String jgcs;
	private Double bj;
	private String jhsj;
	private String jhsj12;
	private String jgcs2;
	private Double bj2;
	private String jhsj2;
	private String jhsj22;
	private String jhsj32;
	private String jhsj42;
	private String jhsj52;
	private String jgcs3;
	private Double bj3;
	private String jhsj3;
	private String jgcs4;
	private Double bj4;
	private String jhsj4;
	private String jgcs5;
	private Double bj5;
	private String jhsj5;
	private Double zbj;
	
	private Integer zbId; //制版负责人
	private String zbCmt; //制版人意见
	private Integer mgrId;
	private String mgrCmt;
	private Integer caiwuId; //财务主管
	private String caiwuCmt; //财务主管意见
	private Integer bossId;
	private String bossCmt;
	private Integer currentId;
	private int status; //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
	private Integer jgStatus;
	private String jgBz;
	
	private String ddbh;
	private String fileName;
	private String filePath;
	
	
	public String getJhsj12() {
		return jhsj12;
	}
	public void setJhsj12(String jhsj12) {
		this.jhsj12 = jhsj12;
	}
	public String getJhsj22() {
		return jhsj22;
	}
	public void setJhsj22(String jhsj22) {
		this.jhsj22 = jhsj22;
	}
	public String getJhsj32() {
		return jhsj32;
	}
	public void setJhsj32(String jhsj32) {
		this.jhsj32 = jhsj32;
	}
	public String getJhsj42() {
		return jhsj42;
	}
	public void setJhsj42(String jhsj42) {
		this.jhsj42 = jhsj42;
	}
	public String getJhsj52() {
		return jhsj52;
	}
	public void setJhsj52(String jhsj52) {
		this.jhsj52 = jhsj52;
	}
	public Double getZbj() {
		return zbj;
	}
	public void setZbj(Double zbj) {
		this.zbj = zbj;
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
	public String getJgcs() {
		return jgcs;
	}
	public void setJgcs(String jgcs) {
		this.jgcs = jgcs;
	}
	public Double getBj() {
		return bj;
	}
	public void setBj(Double bj) {
		this.bj = bj;
	}
	public String getJhsj() {
		return jhsj;
	}
	public void setJhsj(String jhsj) {
		this.jhsj = jhsj;
	}
	public Integer getZbId() {
		return zbId;
	}
	public void setZbId(Integer zbId) {
		this.zbId = zbId;
	}
	public String getZbCmt() {
		return zbCmt;
	}
	public void setZbCmt(String zbCmt) {
		this.zbCmt = zbCmt;
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
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setJgBz(String jgBz) {
		this.jgBz = jgBz;
	}
	public String getJgBz() {
		return jgBz;
	}
	public void setJgStatus(Integer jgStatus) {
		this.jgStatus = jgStatus;
	}
	public Integer getJgStatus() {
		return jgStatus;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public String getDdbh() {
		return ddbh;
	}
	public String getJgcs2() {
		return jgcs2;
	}
	public void setJgcs2(String jgcs2) {
		this.jgcs2 = jgcs2;
	}
	public Double getBj2() {
		return bj2;
	}
	public void setBj2(Double bj2) {
		this.bj2 = bj2;
	}
	public String getJhsj2() {
		return jhsj2;
	}
	public void setJhsj2(String jhsj2) {
		this.jhsj2 = jhsj2;
	}
	public String getJgcs3() {
		return jgcs3;
	}
	public void setJgcs3(String jgcs3) {
		this.jgcs3 = jgcs3;
	}
	public Double getBj3() {
		return bj3;
	}
	public void setBj3(Double bj3) {
		this.bj3 = bj3;
	}
	public String getJhsj3() {
		return jhsj3;
	}
	public void setJhsj3(String jhsj3) {
		this.jhsj3 = jhsj3;
	}
	public String getJgcs4() {
		return jgcs4;
	}
	public void setJgcs4(String jgcs4) {
		this.jgcs4 = jgcs4;
	}
	public Double getBj4() {
		return bj4;
	}
	public void setBj4(Double bj4) {
		this.bj4 = bj4;
	}
	public String getJhsj4() {
		return jhsj4;
	}
	public void setJhsj4(String jhsj4) {
		this.jhsj4 = jhsj4;
	}
	public String getJgcs5() {
		return jgcs5;
	}
	public void setJgcs5(String jgcs5) {
		this.jgcs5 = jgcs5;
	}
	public Double getBj5() {
		return bj5;
	}
	public void setBj5(Double bj5) {
		this.bj5 = bj5;
	}
	public String getJhsj5() {
		return jhsj5;
	}
	public void setJhsj5(String jhsj5) {
		this.jhsj5 = jhsj5;
	}
	
	
}
