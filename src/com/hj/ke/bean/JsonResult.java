package com.hj.ke.bean;

import java.util.List;

public class JsonResult {

	private int ret;
	private String msg;
	private List<CheckInFromKs> data;
	private CheckInFromKs checkin;
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<CheckInFromKs> getData() {
		return data;
	}
	public void setData(List<CheckInFromKs> data) {
		this.data = data;
	}
	public void setCheckin(CheckInFromKs checkin) {
		this.checkin = checkin;
	}
	public CheckInFromKs getCheckin() {
		return checkin;
	}
}
