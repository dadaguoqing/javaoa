/**   
* @Title: ApprovalStatus.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2018年5月25日 上午10:52:53 
* @version V1.0   
*/
package com.hj.commons;

/** 
* @ClassName: ApprovalStatus 
* @Description: 审批状态
* @author mlsong 
* @date 2018年5月25日 上午10:52:53 
*/
public enum ApprovalStatus {
	APPROVAL_ACCESS					(0, 	"通过审批"),		// 审批通过
	APPROVAL_NOT_ACCESS				(-1, 	"未通过审批"),		// 审批未通过
	
	APPLY_SUPPORT_HANDLED			(1001,	"已经成功处理"),	// 技术支持申请得到处理
	APPLY_SUPPORT_CANCELED			(1002, 	"已经取消");		// 技术支持申请取消
	
	ApprovalStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	private int status;
	private String message;

	/** 
	* @return status 
	*/
	public int getStatus() {
		return status;
	}

	/** 
	* @param status 要设置的 status 
	*/
	public void setStatus(int status) {
		this.status = status;
	}

	/** 
	* @return message 
	*/
	public String getMessage() {
		return message;
	}

	/** 
	* @param message 要设置的 message 
	*/
	public void setMessage(String message) {
		this.message = message;
	}
}
