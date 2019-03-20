/**   
* @Title: ProductStatus.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月4日 上午8:45:27 
* @version V1.0   
*/
package com.hj.commons;

/** 
* @ClassName: ProductOutstockRecordStatus 
* @Description: 产品发货申请状态枚举类
* @author mlsong 
* @date 2018年6月4日 上午8:45:27 
*/
public enum ProductOutstockRecordStatus {
	
	TIMEOUT_CANCELED						(-100, "超时未处理自动取消"),
	APPROVAL_NOT_ACCESS						(-1, "审批未通过"),
	READY_FOR_APPROVAL						(0, "待审批"),
	READY_FOR_OUTSTOCK						(1, "待发货"),
	ALREADY_OUTSTOCKED						(2, "已发货"),
	DONE									(3, "订单已完成");

	/** 
	* 状态码
	*/
	private int code;

	/** 
	* 状态信息描述
	*/
	private String message;

	ProductOutstockRecordStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的 code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            要设置的 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
