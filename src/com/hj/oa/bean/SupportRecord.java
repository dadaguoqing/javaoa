package com.hj.oa.bean;

import java.io.Serializable;
/**
 * 技术支持
 * @author wqfang
 *
 */
public class SupportRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	/**申请人ID*/
	private int applyId;
	/**唯一标志*/
	private int supportId;
	/**技术主管审批结果*/
	private String approveResult02;
	/**技术主管审批意见*/
	private String approveOpinion02;
	/**技术主管审批时间*/
	private String approveDate02;
	/**附件文档*/
	private String file2;
	/**主技术支持人员*/
	private int FAEId;
	/**协助技术支持人员*/
	private int FAEId02;
	/**技术人员反馈结果*/
	private String FAEResult;
	/**技术人员完成时间*/
	private String FAETime;
	/**客户反馈结果*/
	private String customerResult;
	/**客户反馈意见*/
	private String customerOpinion;
	/**技术人员完成时间*/
	private String customerDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getSupportId() {
		return supportId;
	}
	public void setSupportId(int supportId) {
		this.supportId = supportId;
	}
	public String getApproveResult02() {
		return approveResult02;
	}
	public void setApproveResult02(String approveResult02) {
		this.approveResult02 = approveResult02;
	}
	
	public String getApproveOpinion02() {
		return approveOpinion02;
	}
	public void setApproveOpinion02(String approveOpinion02) {
		this.approveOpinion02 = approveOpinion02;
	}
	public String getApproveDate02() {
		return approveDate02;
	}
	public void setApproveDate02(String approveDate02) {
		this.approveDate02 = approveDate02;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public int getFAEId() {
		return FAEId;
	}
	public void setFAEId(int fAEId) {
		FAEId = fAEId;
	}
	public int getFAEId02() {
		return FAEId02;
	}
	public void setFAEId02(int fAEId02) {
		FAEId02 = fAEId02;
	}
	public String getFAEResult() {
		return FAEResult;
	}
	public void setFAEResult(String fAEResult) {
		FAEResult = fAEResult;
	}
	public String getFAETime() {
		return FAETime;
	}
	public void setFAETime(String fAETime) {
		FAETime = fAETime;
	}
	public String getCustomerResult() {
		return customerResult;
	}
	public void setCustomerResult(String customerResult) {
		this.customerResult = customerResult;
	}
	public String getCustomerOpinion() {
		return customerOpinion;
	}
	public void setCustomerOpinion(String customerOpinion) {
		this.customerOpinion = customerOpinion;
	}
	public String getCustomerDate() {
		return customerDate;
	}
	public void setCustomerDate(String customerDate) {
		this.customerDate = customerDate;
	}
	
	
	
}
