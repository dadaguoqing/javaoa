/**   
* @Title: ResultCode.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2017年8月5日 下午1:45:32 
* @version V1.0   
*/
package com.hj.commons;

/**
 * @ClassName: ResultCode
 * @Description: 结果状态码
 * @author mlsong
 * @date 2017年8月5日 下午1:45:32
 * 
 */
public enum ResultCode {
	SUCCESS					(0, "请求成功！"), 
	FAILED					(1, "请求失败！"),
	SYSTEM_ERROR			(500, "系统异常！");
	

	/** 
	* 状态码
	*/
	private int code;

	/** 
	* 状态信息描述
	*/
	private String message;

	ResultCode(int code, String message) {
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
