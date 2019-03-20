package com.hj.commons;

/**
 * 物料申请类型
 * 
 * @author wqfang
 *
 *         2018年6月6日
 */
public enum DbStatus {
	ACTIVE(1, "激活"), FREEZE(-1, "冻结");

	DbStatus(int type, String descript) {
		this.type = type;
		this.descript = descript;
	}

	private int type;

	private String descript;

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
