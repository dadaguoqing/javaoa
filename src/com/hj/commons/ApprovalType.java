/**   
* @Title: EmailType.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2018年5月25日 上午10:30:29 
* @version V1.0   
*/
package com.hj.commons;

/** 
* @ClassName: ApprovalType 
* @Description: 申请审批类型
* @author mlsong 
* @date 2018年5月25日 上午10:30:29 
*  
*/
public enum ApprovalType {
	
	PRODUCT_OUTSTOCK					("PRODUCT_OUTSTOCK");	// 仅做通知

	
	ApprovalType(String type) {
		this.type = type;
	}
	
	private String type;
	/** 
	* @return type 
	*/
	public String getType() {
		return type;
	}
	/** 
	* @param type 要设置的 type 
	*/
	public void setType(String type) {
		this.type = type;
	}
}
