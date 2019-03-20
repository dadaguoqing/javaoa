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
* @ClassName: EmailType 
* @Description: 邮件类型
* @author mlsong 
* @date 2018年5月25日 上午10:30:29 
*  
*/
public enum EmailType {
	APPROVAL_RESULT_NOTICE				(0),	// 审批结果通知
	APPROVAL_REMIND_NOTICE				(1),	// 审批提醒通知
	
	READ_ONLY_NOTICE					(2),	// 仅做通知
	OUTSTOCK_READ_ONLY_NOTICE			(3),  	// 产品发货审批通知
	OUTSTOCK_REMIND_NOTICE				(4), 	// 产品发货提醒
	CONFIRM_OUTSTOCK_NOTICE				(5), 	// 确认产品发货提醒	
	CONFIRM_RECEIVE_NOTICE				(6), 	// 确认产品收到回执提醒
	OUTSTOCK_TIMEOUT_NOTICE				(7), 	// 超时未处理取消通知
	OUTSTOCK_EVERYDAY_APPROVAL_NOTICE	(8), 	// 每天发货审批提醒通知
	OUTSTOCK_CONFIRM_REMIND_NOTICE		(9), 	// 产品待出库提醒
	RECEIVE_CONFIRM_REMIND_NOTICE		(10);	// 产品待收回单提醒
	

	
	EmailType(int type) {
		this.type = type;
	}
	
	private int type;
	/** 
	* @return type 
	*/
	public int getType() {
		return type;
	}
	/** 
	* @param type 要设置的 type 
	*/
	public void setType(int type) {
		this.type = type;
	}
}
