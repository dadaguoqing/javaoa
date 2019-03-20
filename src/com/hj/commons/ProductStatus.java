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
* @ClassName: ProductStatus 
* @Description: 产品状态枚举累
* @author mlsong 
* @date 2018年6月4日 上午8:45:27 
*/
public enum ProductStatus {
	
	USABLE						(1, "产品可用"), 
	UNUSABLE					(-1, "产品不可用");

	/** 
	* 状态码
	*/
	private int code;

	/** 
	* 状态信息描述
	*/
	private String message;

	ProductStatus(int code, String message) {
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
