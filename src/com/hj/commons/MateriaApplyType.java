package com.hj.commons;

/**
 * 物料申请类型
 * @author wqfang
 *
 * 2018年6月6日
 */
public enum MateriaApplyType {
	MATERIA_CLAIM				(1),	// 物料申领
	MATERIA_INWARE					(2);	// 物料入库申请

	
	MateriaApplyType(int type) {
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
