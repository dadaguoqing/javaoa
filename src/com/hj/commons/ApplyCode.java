package com.hj.commons;

/**
 * @ClassName:  ApplyCode   
 * @Description: 产品出入库申请编号
 * @author: wqfang
 * @date:   2018年9月26日 下午5:12:53   
 *
 */
public enum ApplyCode {
	MERCHANDISE_INBOUND						(9,"CPRK","成品入库"),
	MERCHANDISE_OUTBOUND					(10,"CPCK","成品出库"),
	HALF_MERCHANDISE_INBOUND				(11,"BCPRK","半成品入库"),
	HALF_MERCHANDISE_OUTBOUND				(12,"BCPCK","半成品出库"),
	REJECTS_INBOUND							(13,"BLPRK","不良品入库"),
	REJECTS_OUTBOUND						(14,"BLPRK","不良品出库");
	
	private Integer id;
	private String code;
	private String descript;
	
	ApplyCode(int id, String code,String descript) {
		this.id = id;
		this.code = code;
		this.descript = descript;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}
