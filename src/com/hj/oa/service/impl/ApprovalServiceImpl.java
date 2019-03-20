/**   
* @Title: ApprovalServiceImpl.java 
* @Package com.hj.oa.service.impl 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月12日 上午10:17:49 
* @version V1.0   
*/
package com.hj.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.commons.ApprovalType;
import com.hj.oa.bean.ApprovalList;
import com.hj.oa.bean.ApprovalListExample;
import com.hj.oa.bean.ApprovalRecord;
import com.hj.oa.bean.ApprovalRecordExample;
import com.hj.oa.dao.ApprovalListMapper;
import com.hj.oa.dao.ApprovalRecordMapper;
import com.hj.oa.service.ApprovalService;

/** 
* @ClassName: ApprovalServiceImpl 
* @Description: 审批业务处理接口实现类
* @author mlsong 
* @date 2018年6月12日 上午10:17:49 
*  
*/
@Service("approvalService")
public class ApprovalServiceImpl implements ApprovalService {
	/**
	 * t_approval_list表操作dao接口
	 */
	@Autowired
	private ApprovalListMapper approvalListDao;
	
	/**
	 * t_approval_record表操作dao接口
	 */
	@Autowired
	private ApprovalRecordMapper approvalRecordDao;

	/* (non-Javadoc) 
	* <p>Title: getApprovalListByType</p> 
	* <p>Description: </p> 
	* @param productOutstock
	* @return 
	* @see com.hj.oa.service.ApprovalService#getApprovalListByType(com.hj.commons.ApprovalType) 
	*/
	@Override
	public ApprovalList getApprovalListByType(ApprovalType productOutstock) {
		// 构造条件
		ApprovalListExample example = new ApprovalListExample();
		example.createCriteria().andTypeEqualTo(productOutstock.getType());
		// 执行查询
		List<ApprovalList> list = approvalListDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/* (non-Javadoc) 
	* <p>Title: getApprovalRecords</p> 
	* <p>Description: </p> 
	* @param recordId
	* @param type
	* @return 
	* @see com.hj.oa.service.ApprovalService#getApprovalRecords(int, com.hj.commons.ApprovalType) 
	*/
	@Override
	public List<ApprovalRecord> getApprovalRecordsByApplyId(int applyId, ApprovalType type) {
		// 构造
		ApprovalRecordExample exam = new ApprovalRecordExample();
		exam.createCriteria().andApplyIdEqualTo(applyId).andTypeEqualTo(type.getType());
		// 返回查询结果
		return approvalRecordDao.selectByExample(exam);
	}

	/* (non-Javadoc) 
	* <p>Title: getApprovalRecordsByUserId</p> 
	* <p>Description: </p> 
	* @param userId
	* @param type
	* @return 
	* @see com.hj.oa.service.ApprovalService#getApprovalRecordsByUserId(int, java.lang.String) 
	*/
	@Override
	public List<ApprovalRecord> getApprovalRecordsByUserId(int userId, ApprovalType type) {
		// 条件
		ApprovalRecordExample example = new ApprovalRecordExample();
		example.createCriteria().andTypeEqualTo(type.getType())
				.andEmpIdEqualTo(userId);
		// 返回结果
		return approvalRecordDao.selectByExample(example);
	}

}
