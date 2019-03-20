/**   
* @Title: ApiResult.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2017年8月5日 下午1:42:21 
* @version V1.0   
*/
package com.hj.commons;

import java.io.Serializable;

/** 
* @ClassName: ApiResult 
* @Description: 返回结果描述类 
* @author mlsong 
* @date 2017年8月5日 下午1:42:21 
*  
*/
public class ApiResult implements Serializable {
	
	/** 
	* <p>Title: 构造函数初始化</p> 
	* <p>Description: 初始化默认为成功</p>  
	*/
	public ApiResult() {
		setResultCode(ResultCode.SUCCESS);
	}
	
	/** 
	* <p>Title: 带参数构造函数初始化</p> 
	* <p>Description: 带参数构造函数初始化</p> 
	* @param resultCode 
	*/
	public ApiResult(ResultCode resultCode) {
		setResultCode(resultCode);
	}

	/** 
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	/** 
	* @Fields state : 返回结果状态
	*/
	private int state;
	
	/** 
	* @Fields message : 状态描述
	*/
	private String message;
	
	/** 
	* @Fields data : 返回结果数据
	*/
	private Object data;

	/** 
	* @return state 
	*/
	public int getState() {
		return state;
	}

	/** 
	* @param state 要设置的 state 
	*/
	public void setState(int state) {
		this.state = state;
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

	/** 
	* @return data 
	*/
	public Object getData() {
		return data;
	}

	/** 
	* @param data 要设置的 data 
	*/
	public void setData(Object data) {
		this.data = data;
	}
	
	/** 
	* @Description: 设置结果状态码 
	* @param resultCode
	* @author mlsong
	* @date 2017年8月5日 下午1:57:08
	*/
	public void setResultCode(ResultCode resultCode) {
		this.state = resultCode.getCode();
		this.message = resultCode.getMessage();
	}
	
}
