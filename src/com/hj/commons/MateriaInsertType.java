package com.hj.commons;

/**
 * @ClassName:  MateriaInsertType   
 * @Description: t_materia_seq表
 * @author: wqfang
 * @date:   2018年11月7日 下午2:32:46   
 *
 */
public enum MateriaInsertType {
	MATERIA_PURCHASE				(1),// 物料采购
	MATERIA_REQUISITION				(2),// 物料请购
	MATERIA_INWARE					(3),// 物料入库
	MATERIA_REVENT					(4),// 返料入库
	MATERIA_SY						(5),// 损益申请
	MATERIA_APPLY					(6),// 物料申领
	SEAL_APPLY						(7),// 用章申请
	MATERIA_REJECT					(15),// 用章申请
	EXTERNAL_PROCESSING				(16);// 外协加工申请
	
	MateriaInsertType(int type) {
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
