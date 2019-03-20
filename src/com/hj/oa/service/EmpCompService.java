/**   
* @Title: EmpCompService.java 
* @Package com.hj.oa.service 
* @Description: TODO 
* @author mlsong   
* @date 2017年8月15日 上午8:42:02 
* @version V1.0   
*/
package com.hj.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.dao.EmpCompMapper;

/** 
* @ClassName: EmpCompService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author mlsong 
* @date 2017年8月15日 上午8:42:02 
*  
*/
@Service
public class EmpCompService {
	@Autowired
	private EmpCompMapper empCompMapper;
	
	/** 
	* @Description: 添加任职资格pdf 
	* @param empId 员工id
	* @param pdfPath pdf保存路径
	* @author mlsong
	* @date 2017年8月15日 上午8:43:46
	*/
	public void addEmpComp(int empId, String pdfPath) {
		empCompMapper.deleteAllCompById(empId);
		empCompMapper.addEmpComp(empId, pdfPath);
	}
}
