package com.hj.commons;

import java.io.Serializable;

/**
 * Ajax返回结果集
 * @author wqfang
 *
 * 2018年8月17日
 */
public class AjaxResult<T> implements Serializable {

	private static final long serialVersionUID = 8568272340570209931L;
	
	private boolean success = true;
	
	private T data;
	
	private String msg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
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
