package com.hj.commons;

import java.io.Serializable;

/**
 * @ClassName:  AjaxResultV2   
 * @Description:AJAX返回结果
 * @author: wqfang
 * @date:   2018年9月10日 上午9:46:05   
 *
 */
public class AjaxResultV2<T> implements Serializable {
	
	
	public final static byte SUCCESS = 1;    // 结果成功
	
	public final static byte FAIL = -1;      // 结果失败

	private static final long serialVersionUID = 8568272340570209931L;
	
	private byte success = SUCCESS; 		 //默认成功
	
	private T data;
	
	private String msg;

	
	public byte getSuccess() {
		return success;
	}

	public void setSuccess(byte success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
