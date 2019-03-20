/**  
 * All rights Reserved, Designed By www.itek-training.com
 * @Title:  BaseService.java   
 * @Package com.hj.oa.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wqfang
 * @date:   2018年12月4日 下午5:56:31   
 * @version V1.0 
 * @Copyright: 2018 www.itek-training.com Inc. All rights reserved. 
 * 注意：本内容仅限于宏晶信息科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.hj.oa.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hj.oa.dao.UserMapper;

/**   
 * @ClassName:  BaseService   
 * @Description: 基础service类
 * @author: wqfang
 * @date:   2018年12月4日 下午5:56:31
 *   
 */
public class BaseService {
	
	@Autowired
	UserMapper userMapper;
	
	
	
	
}
