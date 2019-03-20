/**  
 * All rights Reserved, Designed By www.itek-training.com
 * @Title:  ApplyType.java   
 * @Package com.hj.commons   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wqfang
 * @date:   2018年9月26日 下午6:07:22   
 * @version V1.0 
 * @Copyright: 2018 www.itek-training.com Inc. All rights reserved. 
 * 注意：本内容仅限于宏晶信息科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.hj.commons;

/**   
 * @ClassName:  ApplyType   
 * @Description: 产品申请类型
 * @author: wqfang
 * @date:   2018年9月26日 下午6:07:22   
 *   
 */
public enum ApplyType {
	MERCHANDISE_INBOUND						(1,"成品入库"),
	MERCHANDISE_OUTBOUND					(2,"成品出库"),
	HALF_MERCHANDISE_INBOUND				(3,"半成品入库"),
	HALF_MERCHANDISE_OUTBOUND				(4,"半成品出库"),
	REJECTS_INBOUND							(5,"不良品入库"),
	REJECTS_OUTBOUND						(6,"不良品出库");
	private Integer type;
	private String descript;
	
	/**   
	 * @Title:  ApplyType   
	 * @Description:    TODO(这里用一句话描述这个方法的作用)   
	 * @param:    
	 * @throws   
	 */
	private ApplyType(Integer type,String descript) {
		this.type = type;
		this.descript = descript;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}
