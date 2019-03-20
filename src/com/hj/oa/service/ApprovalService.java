/**   
* @Title: ApprovalService.java 
* @Package com.hj.oa.service 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月12日 上午10:17:15 
* @version V1.0   
*/
package com.hj.oa.service;

import java.util.List;

import com.hj.commons.ApprovalType;
import com.hj.oa.bean.ApprovalList;
import com.hj.oa.bean.ApprovalRecord;

/** 
* @ClassName: ApprovalService 
* @Description: 审批业务处理器
* @author mlsong 
* @date 2018年6月12日 上午10:17:15 
*  
*/
public interface ApprovalService {

	/** 
	* @Description: 根据申请类型查询申请审批列表
	* @param productOutstock
	* @return ApprovalList
	* @author mlsong
	* @date 2018年6月12日 上午10:25:29
	*/
	ApprovalList getApprovalListByType(ApprovalType productOutstock);

	/** 
	* @Description: 根据申请id和申请类型查询对应的审批记录
	* @param recordId
	* 			申请id
	* @param type
	* 			申请类型
	* @return List<ApprovalRecord>
	* @author mlsong
	* @date 2018年6月12日 上午11:19:57
	*/
	List<ApprovalRecord> getApprovalRecordsByApplyId(int recordId, ApprovalType type);

	/** 
	* @Description: 根据
	* @param id
	* @param type
	* @return List<ApprovalRecord>
	* @author mlsong
	* @date 2018年6月12日 下午4:00:23
	*/
	List<ApprovalRecord> getApprovalRecordsByUserId(int userId, ApprovalType type);

}
