/**   
* @Title: ProductServiceImpl.java 
* @Package com.hj.oa.service.impl 
* @author mlsong   
* @date 2018年6月4日 上午8:37:21 
* @version V1.0   
*/
package com.hj.oa.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.hj.commons.ApprovalStatus;
import com.hj.commons.ApprovalType;
import com.hj.commons.Const;
import com.hj.commons.DbStatus;
import com.hj.commons.EmailType;
import com.hj.commons.OutstockStatus;
import com.hj.commons.ProductOutstockRecordStatus;
import com.hj.commons.ProductStatus;
import com.hj.commons.SPStatus;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApprovalList;
import com.hj.oa.bean.ApprovalRecord;
import com.hj.oa.bean.ApprovalRecordExample;
import com.hj.oa.bean.Company;
import com.hj.oa.bean.CompanyExample;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Logistics;
import com.hj.oa.bean.LogisticsExample;
import com.hj.oa.bean.OutstockDetail;
import com.hj.oa.bean.OutstockDetailExample;
import com.hj.oa.bean.OutstockRecord;
import com.hj.oa.bean.OutstockRecordExample;
import com.hj.oa.bean.Product;
import com.hj.oa.bean.ProductExample;
import com.hj.oa.bean.ProductLogistics;
import com.hj.oa.bean.ProductOutstockDetail;
import com.hj.oa.bean.ProductOutstockDetailExample;
import com.hj.oa.bean.ProductOutstockRecord;
import com.hj.oa.bean.ProductOutstockRecordExample;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.dao.ApprovalRecordMapper;
import com.hj.oa.dao.CompanyMapper;
import com.hj.oa.dao.LogisticsMapper;
import com.hj.oa.dao.OutstockDetailMapper;
import com.hj.oa.dao.OutstockRecordMapper;
import com.hj.oa.dao.ProductLogisticsMapper;
import com.hj.oa.dao.ProductMapper;
import com.hj.oa.dao.ProductOutstockDetailMapper;
import com.hj.oa.dao.ProductOutstockRecordMapper;
import com.hj.oa.dao.RoleMapper;
import com.hj.oa.service.ApprovalService;
import com.hj.oa.service.UserService;
import com.hj.oa.service.inter.ProductService;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailUtil;
import com.hj.util.StringUtil;

/**
 * @ClassName: ProductServiceImpl
 * @Description: 产品信息业务处理接口实现类
 * @author mlsong
 * @date 2018年6月4日 上午8:37:21
 * 
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
	/**
	 * 日志记录器
	 */
	// private static final Logger logger =
	// LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * t_product表dao操作接口
	 */
	@Autowired
	private ProductMapper productDao;
	
	@Autowired
	private ProductLogisticsMapper plMapper;

	/**
	 * t_product_outstock_record表dao操作接口
	 */
	@Autowired
	private ProductOutstockRecordMapper productOutstockRecordDao;

	/**
	 * t_product_outstock_detail表dao操作接口
	 */
	@Autowired
	private ProductOutstockDetailMapper productOutstockDetailDao;

	/**
	 * t_approval_record表dao操作接口
	 */
	@Autowired
	private ApprovalRecordMapper approvalRecordDao;

	/**
	 * t_outstock_record表dao操作接口
	 */
	@Autowired
	private OutstockRecordMapper outstockRecordDao;

	/**
	 * t_outstock_detail表dao操作接口
	 */
	@Autowired
	private OutstockDetailMapper outstockDetailDao;

	/**
	 * t_ompany表DAO操作接口
	 */
	@Autowired
	private CompanyMapper companyDao;

	/**
	 * 审批业务处理器
	 */
	@Autowired
	@Qualifier("approvalService")
	private ApprovalService approvalService;

	/**
	 * 邮件业务处理器
	 */
	@Autowired
	private MailUtil mailUtil;

	/**
	 * 用户业务处理器
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private RoleMapper roleDao;
	
	@Autowired
	private LogisticsMapper logisticsMapper;

	/*
	 * (non-Javadoc) <p>Title: getProducts</p> <p>Description: </p>
	 * 
	 * @param status
	 * 
	 * @return
	 * 
	 * @see com.hj.oa.service.inter.ProductService#getProducts(com.hj.commons.
	 * ProductStatus)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getProducts(ProductStatus status) {
		ProductExample example = new ProductExample();
		if (status != null) {
			example.createCriteria().andStateEqualTo(status.getCode());
		}
		List<Product> products = productDao.selectByExample(example);

		Map<String, Object> map = new HashMap<String, Object>();
		for (Product product : products) {
			if (!map.containsKey(product.getProductName())) {
				map.put(product.getProductName(), new ArrayList<Product>());
			}
			List<Product> subList = (List<Product>) map.get(product.getProductName());
			subList.add(product);
		}

		return map;
	}

	/*
	 * (non-Javadoc) <p>Title: saveOutstockRecordAndDetails</p> <p>Description:
	 * </p>
	 * 
	 * @param record
	 * 
	 * @param products
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#saveOutstockRecordAndDetails(com.
	 * hj.oa.bean.ProductOutstockRecord, java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveOutstockRecordAndDetails(ProductOutstockRecord record, List<ProductOutstockDetail> products) {
		ProductOutstockRecordExample example = new ProductOutstockRecordExample();
		example.createCriteria().andOddNumberEqualTo(record.getOddNumber());
		// 校验订单号是否已经存在
		if (productOutstockRecordDao.countByExample(example) > 0) {
			throw new RuntimeException("该订单编号已经存在，请重新填写！");
		}

		// 保存订单信息
		productOutstockRecordDao.insert(record);
		int id = record.getId();
		// 设置产品关联的订单id
		for (ProductOutstockDetail product : products) {
			product.setOrderId(id);
		}
		// 批量保存发货产品信息
		productOutstockDetailDao.insertList(products);

		// 邮件通知
		// 查询接收人邮箱
		
		int roleId = record.getApprovalId();
		Role role = roleDao.findById(roleId);
		List<User> users = userService.findUserByRole(role.getName());
		if (users == null || users.isEmpty()) {
			throw new RuntimeException("未查询到上级审批角色，请先设置员工角色！");
		}
		Dept dept = userService.findDeptById(record.getDeptId());
		String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, users.get(0).getName(),
				record.getProposer(), dept.getName(), "产品发货申请", null, record, products, dept.getName());
		mailUtil.sendEMail(users.get(0).getEmail(), null, Consts.defaultFrom, "产品发货申请审批", content);
		 
	}

	/*
	 * (non-Javadoc) <p>Title: getProductOutstockRecordById</p> <p>Description:
	 * </p>
	 * 
	 * @param recordId
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getProductOutstockRecordById(int)
	 */
	@Override
	public ProductOutstockRecord getProductOutstockRecordById(int recordId) {
		return productOutstockRecordDao.selectByPrimaryKey(recordId);
	}

	/*
	 * (non-Javadoc) <p>Title: getProductOutstockDetailsById</p> <p>Description:
	 * </p>
	 * 
	 * @param recordId
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getProductOutstockDetailsById(int)
	 */
	@Override
	public List<ProductOutstockDetail> getProductOutstockDetailsById(int recordId) {
		// 构造条件
		ProductOutstockDetailExample example = new ProductOutstockDetailExample();
		example.createCriteria().andOrderIdEqualTo(recordId);
		List<ProductOutstockDetail> details = productOutstockDetailDao.selectByExample(example);
		
		for (ProductOutstockDetail detail : details) {
			Product product = productDao.selectByPrimaryKey(detail.getProductId());
			detail.setOriRemark(product.getRemark());
		}
		// 返回查询结果
		
		return details;
	}

	/*
	 * (non-Javadoc) <p>Title: getWillApprovalRecordsByRoles</p> <p>Description:
	 * </p>
	 * 
	 * @param roles
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getWillApprovalRecordsByRoles(java
	 * .util.List)
	 */
	@Override
	public List<ProductOutstockRecord> getWillApprovalRecordsByRoles(List<Role> roles) {
		return productOutstockRecordDao.selectRecordsByRole(roles);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void handle(ApprovalRecord approRecord, ProductOutstockRecord record, String bt, Integer count,
			String jsonArr, Integer shipments) {
		// 保存审批记录
		approvalRecordDao.insert(approRecord);

		if (approRecord.getState() == SPStatus.SP_ACCESS.getCode()) { // 审批成功
			// 查询审批列表
			ApprovalList al = approvalService.getApprovalListByType(ApprovalType.PRODUCT_OUTSTOCK);
			if (al == null) {
				throw new RuntimeException("数据库中未设置该类型审批流程，请先联系管理员设置！");
			}
			// 审批角色列表
			String[] roles = al.getRoleList().split(",");

			for (int i = 0; i < roles.length; i++) { // 循环遍历判断审批流程到达的位置

				// 判断当前的审批角色
				if (roles[i].equals(record.getApprovalId().toString())) {
					// 判断审批流程是否结束（若审批列表中下一个没有了，既到头了）
					if (i < (roles.length - 1)) {
						record.setApprovalId(Integer.parseInt(roles[i + 1]));
					} else { // 审批流程已结束
						record.setApprovalId(-1);
						record.setState(ProductOutstockRecordStatus.READY_FOR_OUTSTOCK.getCode());
						// 审批结束，保存发货详细信息
						if (shipments == null) {
							throw new RuntimeException("发货类型参数为null错误！");
						}

						// 判断发货形式，分批发货和一次性发货接收的数据不同
						if (shipments == 0) { // 一次性发货
							// 发货记录
							OutstockRecord outstockRecord = new OutstockRecord();
							outstockRecord.setCount(count);
							outstockRecord.setRecordId(record.getId());
							outstockRecord.setOutstockType(false);
							outstockRecord.setType(ApprovalType.PRODUCT_OUTSTOCK.getType());
							outstockRecord.setState(OutstockStatus.READY_FOR_OUTSTOCK.getCode());

							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
							try {
								outstockRecord.setExpectedOutstockTime(sdf2.parse(bt));
							} catch (ParseException e) {
								throw new RuntimeException("日期格式化异常");
							}
							// 保存发货记录
							outstockRecordDao.insert(outstockRecord);

							// 发货记录中的详细发货产品
							List<ProductOutstockDetail> details = getProductOutstockDetailsById(record.getId());

							List<OutstockDetail> outDetails = new ArrayList<OutstockDetail>();
							// 设置详细产品关联的发货记录id
							for (ProductOutstockDetail detail : details) {
								OutstockDetail de = detail.convertToOutstockDetail();
								de.setRecordId(outstockRecord.getId());
								de.setProductId(detail.getProductId());
								outDetails.add(de);
							}

							// 批量保存详细记录
							outstockDetailDao.insertList(outDetails);
						} else if (shipments == 1) { // 分批发货
							// 分批发货的数据
							JSONArray arr = JSONArray.parseArray(jsonArr);
							List<Map<String, Object>> li = JSONArray.toJavaObject(arr, List.class);
							// 批次循环
							for (Map<String, Object> map : li) {
								OutstockRecord outRecord = new OutstockRecord();
								// 设置发货记录信息
								outRecord.setCount((Integer) map.get("count"));
								outRecord.setOrderNum((Integer) map.get("order"));
								outRecord.setRecordId(record.getId());
								outRecord.setOutstockType(true);
								outRecord.setType(ApprovalType.PRODUCT_OUTSTOCK.getType());
								outRecord.setState(OutstockStatus.READY_FOR_OUTSTOCK.getCode());
								SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");

								// 预计发货时间
								try {
									outRecord.setExpectedOutstockTime(
											sdf2.parse((String) map.get("expectedOutstockTime")));
								} catch (ParseException e) {
									e.printStackTrace();
								}

								// 保存批次记录
								outstockRecordDao.insert(outRecord);

								List<Map<String, Object>> dets = (List<Map<String, Object>>) map.get("details");
								// 新增批次详细
								List<OutstockDetail> detailList = new ArrayList<OutstockDetail>();
								for (Map<String, Object> de : dets) {
									// 数量为零不记录
									if ((Integer.parseInt((String) de.get("count"))) == 0) {
										continue;
									}
									// 发货批次的详细发货产品
									OutstockDetail detail = new OutstockDetail();
									detail.setCount(Integer.parseInt((String) de.get("count")));
									detail.setProductModel((String) de.get("productModel"));
									detail.setProductName((String) de.get("productName"));
									detail.setRecordId(outRecord.getId());
									detail.setUnit((String) de.get("unit"));
									detailList.add(detail);
								}
								// 批量插入
								outstockDetailDao.insertList(detailList);
							}
						} else {
							throw new RuntimeException("无此发货参数类型错误!");
						}
					}
					break;
				}
			}
			productOutstockRecordDao.updateByPrimaryKeySelective(record);

		} else if (approRecord.getState() == SPStatus.SP_NOT_ACCESS.getCode()) { // 审批失败
			record.setApprovalId(-1);
			record.setState(ProductOutstockRecordStatus.APPROVAL_NOT_ACCESS.getCode());
			// 更新订单状态
			productOutstockRecordDao.updateByPrimaryKeySelective(record);
		} else {
			throw new RuntimeException("无此类型审批状态");
		}

		// 发送审批邮件
//		sendApprovalMail(record, approRecord);

	}

	/*
	 * (non-Javadoc) <p>Title: getProductOutstockRecords</p> <p>Description:
	 * </p>
	 * 
	 * @param status
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getProductOutstockRecords(com.hj.
	 * commons.ProductOutstockRecordStatus)
	 */
	@Override
	public List<ProductOutstockRecord> getProductOutstockRecords(ProductOutstockRecordStatus... status) {
		// 条件
		ProductOutstockRecordExample example = new ProductOutstockRecordExample();
		if (status == null || status.length == 0) {
			throw new RuntimeException("传入参数不能为空");
		}
		for (int i = 0; i < status.length; i++) {
			ProductOutstockRecordExample.Criteria ca = example.createCriteria();
			ca.andStateEqualTo(status[i].getCode());
			if (i != 0) {
				example.or(ca);
			}
		}
		// 返回查询
		return productOutstockRecordDao.selectByExample(example);
	}

	/*
	 * (non-Javadoc) <p>Title: getOutstockRecordsByRecordId</p> <p>Description:
	 * </p>
	 * 
	 * @param recordId
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getOutstockRecordsByRecordId(int)
	 */
	@Override
	public List<OutstockRecord> getOutstockRecordsByRecordId(int recordId) {
		// 条件
		OutstockRecordExample ex = new OutstockRecordExample();
		ex.createCriteria().andRecordIdEqualTo(recordId);
		// 返回查询
		List<OutstockRecord> records = outstockRecordDao.selectByExample(ex);
		for (OutstockRecord record : records) {
			OutstockDetailExample example = new OutstockDetailExample();
			example.createCriteria().andRecordIdEqualTo(record.getId());
			List<OutstockDetail> details = outstockDetailDao.selectByExample(example);
			for (OutstockDetail detail : details) {
				Product product = productDao.selectByPrimaryKey(detail.getProductId());
				detail.setOriRemark(product.getRemark());
			}
			record.setDetails(details);
		}
		return records;
	}

	/*
	 * (non-Javadoc) <p>Title: handleReceive</p> <p>Description: </p>
	 * 
	 * @param record
	 * 
	 * @see com.hj.oa.service.inter.ProductService#handleReceive(com.hj.oa.bean.
	 * OutstockRecord)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void handleReceive(OutstockRecord record) {
		// 更新发货记录
		outstockRecordDao.updateByPrimaryKeySelective(record);
		// 判断发货形式
/*		if (record.getOutstockType() == Const.OUTSTOCK_BY_ONETIME) {
			// 更新订单。订单完成
			ProductOutstockRecord recd = new ProductOutstockRecord();
			recd.setId(record.getRecordId());
			recd.setState(ProductOutstockRecordStatus.DONE.getCode());
			productOutstockRecordDao.updateByPrimaryKeySelective(recd);
		} else if (record.getOutstockType() == Const.OUTSTOCK_BY_BATCH) {*/
		ProductOutstockRecord recd = productOutstockRecordDao.selectByPrimaryKey(record.getRecordId());
		
		// 判断出库单是否都已出库
		OutstockRecordExample example = new OutstockRecordExample();
		example.createCriteria()
			.andRecordIdEqualTo(recd.getId())
			.andStateNotEqualTo(OutstockStatus.ALREADY_RECIVE_RECEIPT.getCode());
		if (outstockRecordDao.countByExample(example) <= 0) { // 订单结束，更改申请单状态
			// 更新订单。订单完成
			ProductOutstockRecord rec = new ProductOutstockRecord();
			rec.setId(record.getRecordId());
			rec.setState(ProductOutstockRecordStatus.DONE.getCode());
			productOutstockRecordDao.updateByPrimaryKeySelective(rec);
		}
		
		/*if (recd.getState() ==  ProductOutstockRecordStatus.ALREADY_OUTSTOCKED.getCode()) {
			// 判断是否所有订单相关的出库记录都已是收到回执状态
			OutstockRecordExample example = new OutstockRecordExample();
			example.createCriteria()
				.andStateNotEqualTo(OutstockStatus.ALREADY_RECIVE_RECEIPT.getCode())
				.andRecordIdEqualTo(record.getRecordId());
			
			if (outstockRecordDao.countByExample(example) <= 0) { // 订单结束，更改申请单状态
				// 更新订单。订单完成
				ProductOutstockRecord rec = new ProductOutstockRecord();
				rec.setId(record.getRecordId());
				rec.setState(ProductOutstockRecordStatus.DONE.getCode());
				productOutstockRecordDao.updateByPrimaryKeySelective(rec);
			}
		}*/
		
/*		} else {
			throw new RuntimeException("不支持的状态类型！");
		}*/

		// 发送邮件通知
		sendConfirmMail(record.getRecordId(), "receiveConfirm");
	}

	/*
	 * (non-Javadoc) <p>Title: getProductOutstockRecordByUserId</p>
	 * <p>Description: </p>
	 * 
	 * @param userId
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getProductOutstockRecordByUserId(
	 * int)
	 */
	@Override
	public List<ProductOutstockRecord> getProductOutstockRecordByUserId(int userId) {
		// 条件
		ProductOutstockRecordExample example = new ProductOutstockRecordExample();
		example.createCriteria().andProposerIdEqualTo(userId);
		// 返回结果
		return productOutstockRecordDao.selectByExample(example);
	}

	/*
	 * (non-Javadoc) <p>Title: getOutstockDetailsByRecordId</p> <p>Description:
	 * </p>
	 * 
	 * @param recordId
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getOutstockDetailsByRecordId(int)
	 */
	@Override
	public List<OutstockDetail> getOutstockDetailsByRecordId(int recordId) {
		// 条件
		OutstockDetailExample exm = new OutstockDetailExample();
		exm.createCriteria().andRecordIdEqualTo(recordId);
		// 返回结果
		return outstockDetailDao.selectByExample(exm);
	}

	/*
	 * (non-Javadoc) <p>Title: handleOutstock</p> <p>Description: </p>
	 * 
	 * @param record
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#handleOutstock(com.hj.oa.bean.
	 * OutstockRecord)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void handleOutstock(OutstockRecord record) {
		// 更新发货记录
		outstockRecordDao.updateByPrimaryKeySelective(record);
		// 判断发货形式
		ProductOutstockRecord recd = productOutstockRecordDao.selectByPrimaryKey(record.getRecordId());
		if (recd.getState() ==  ProductOutstockRecordStatus.READY_FOR_OUTSTOCK.getCode()) {
			// 判断是否所有订单相关的出库记录都已是收到回执状态
			OutstockRecordExample example = new OutstockRecordExample();
			example.createCriteria()
				.andStateNotEqualTo(OutstockStatus.ALREADY_OUTSTOCKED.getCode())
				.andRecordIdEqualTo(record.getRecordId());
			if (outstockRecordDao.countByExample(example) <= 0) { // 订单货物已发
				// 更新订单。订单完成
				ProductOutstockRecord rec = new ProductOutstockRecord();
				rec.setId(record.getRecordId());
				rec.setState(ProductOutstockRecordStatus.ALREADY_OUTSTOCKED.getCode());
				productOutstockRecordDao.updateByPrimaryKeySelective(rec);
			}
		}
		
		// 发送邮件通知
		sendConfirmMail(record.getRecordId(), "outstockConfirm");
	}

	/**
	 * @Description: 发送确认出库/回单邮件
	 * @param id
	 * @param string
	 * @author mlsong
	 * @date 2018年6月15日 上午11:04:20
	 */
	private void sendConfirmMail(Integer id, String type) {
		// 产品发货申请
		ProductOutstockRecord record = productOutstockRecordDao.selectByPrimaryKey(id);
		// 查询审批列表
		ApprovalList al = approvalService.getApprovalListByType(ApprovalType.PRODUCT_OUTSTOCK);
		if (al == null) {
			throw new RuntimeException("数据库中未设置该类型审批流程，请先联系管理员设置！");
		}
		// 审批角色列表
		String[] roles = al.getRoleList().split(",");
		// 发货记录中的详细发货产品
		List<ProductOutstockDetail> details = getProductOutstockDetailsById(record.getId());

		// 申请人部门信息
		Dept dept = userService.findDeptById(record.getDeptId());
		if (dept == null) {
			throw new RuntimeException("未查询到部门信息");
		}
		// 申请人信息
		User user = userService.findById(record.getProposerId());
		if (user == null) {
			throw new RuntimeException("未查询到申请人信息");
		}

		if ("outstockConfirm".equals(type)) { // 确认出库邮件
			// 审批人通知
			for (int i = 0; i < roles.length; i++) {
				Role role = roleDao.findById(Integer.parseInt(roles[i]));
				List<User> users = userService.findUserByRole(role.getName());
				String content = MailContentBuilder.buildEmailContent(EmailType.CONFIRM_OUTSTOCK_NOTICE,
						users.get(0).getName(), record.getProposer(), dept.getName(), "产品发货申请", null, record, details,
						dept.getName());
				mailUtil.sendEMail(users.get(0).getEmail(), null, Consts.defaultFrom, "产品确认发货通知提醒", content);
			}
			// 申请人通知
			String content = MailContentBuilder.buildEmailContent(EmailType.CONFIRM_OUTSTOCK_NOTICE,
					record.getProposer(), record.getProposer(), dept.getName(), "产品发货申请", null, record, details,
					dept.getName());
			mailUtil.sendEMail(user.getEmail(), null, Consts.defaultFrom, "产品确认发货通知提醒", content);
		} else if ("receiveConfirm".equals(type)) { // 确认回执邮件
			// 审批人通知
			for (int i = 0; i < roles.length; i++) {
				Role role = roleDao.findById(Integer.parseInt(roles[i]));
				List<User> users = userService.findUserByRole(role.getName());
				String content = MailContentBuilder.buildEmailContent(EmailType.CONFIRM_RECEIVE_NOTICE,
						users.get(0).getName(), record.getProposer(), dept.getName(), "产品发货申请", null, record, details,
						dept.getName());
				mailUtil.sendEMail(users.get(0).getEmail(), null, Consts.defaultFrom, "产品确认收货回单通知提醒", content);
			}
			// 申请人通知
			String content = MailContentBuilder.buildEmailContent(EmailType.CONFIRM_RECEIVE_NOTICE,
					record.getProposer(), record.getProposer(), dept.getName(), "产品发货申请", null, record, details,
					dept.getName());
			mailUtil.sendEMail(user.getEmail(), null, Consts.defaultFrom, "产品确认收货回单通知提醒", content);
		} else {
			throw new RuntimeException("无此发送邮件类型");
		}

	}

	/**
	 * @Description: 发送审批通知邮件
	 * @param record
	 * @param approRecord
	 * @author mlsong
	 * @date 2018年6月15日 上午11:04:01
	 */
	private void sendApprovalMail(ProductOutstockRecord record, ApprovalRecord approRecord) {
		// 申请人信息
		int proposerId = record.getProposerId();
		User user = userService.findById(proposerId);
		if (user == null) {
			throw new RuntimeException("未查询到申请人信息");
		}
		Dept dept = userService.findDeptById(user.getDeptId());
		if (dept == null) {
			throw new RuntimeException("未查询到部门信息");
		}
		// 查询审批列表
		ApprovalList al = approvalService.getApprovalListByType(ApprovalType.PRODUCT_OUTSTOCK);
		if (al == null) {
			throw new RuntimeException("数据库中未设置该类型审批流程，请先联系管理员设置！");
		}
		// 审批角色列表
		String[] roles = al.getRoleList().split(",");
		// 发货记录中的详细发货产品
		List<ProductOutstockDetail> details = getProductOutstockDetailsById(record.getId());
		// 获取当前审批人角色
		Role curRole = roleDao.findById(approRecord.getRoleId());
		// 申请人发送邮件内容
		String content = "";
/*
		// 向前通知
		for (int i = 0; i < roles.length; i++) { // 循环遍历判断审批流程到达的位置
			if (Integer.parseInt(roles[i]) != approRecord.getRoleId()) {
				Role role = roleDao.findById(Integer.parseInt(roles[i]));
				List<User> users = userService.findUserByRole(role.getName());
				String cont = "";
				if (approRecord.getState() == SPStatus.SP_NOT_ACCESS.getCode()) {
					cont = MailContentBuilder.buildEmailContent(EmailType.OUTSTOCK_READ_ONLY_NOTICE,
							users.get(0).getName(), record.getProposer(), dept.getName(), "产品发货申请",
							ApprovalStatus.APPROVAL_NOT_ACCESS, record, details, dept.getName(), curRole.getName());
				} else {
					cont = MailContentBuilder.buildEmailContent(EmailType.OUTSTOCK_READ_ONLY_NOTICE,
							users.get(0).getName(), record.getProposer(), dept.getName(), "产品发货申请",
							ApprovalStatus.APPROVAL_ACCESS, record, details, dept.getName(), curRole.getName());
				}
				mailUtil.sendEMail(users.get(0).getEmail(), null, Consts.defaultFrom, "产品发货申请审核通知提醒", cont);
			} else {
				break;
			}
		}

		// 向后通知(审批成功，且审批流程未结束)
		if (approRecord.getState() == SPStatus.SP_ACCESS.getCode()
				&& approRecord.getRoleId() != Integer.parseInt(roles[roles.length - 1])) {
			for (int i = 0; i < roles.length; i++) { // 循环遍历判断审批流程到达的位置
				if (Integer.parseInt(roles[i]) != approRecord.getRoleId()) {
					continue;
				} else {
					Role role = roleDao.findById(Integer.parseInt(roles[i + 1]));
					List<User> users = userService.findUserByRole(role.getName());
					String cont = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE,
							users.get(0).getName(), record.getProposer(), dept.getName(), "产品发货申请", null, record,
							details, dept.getName());
					mailUtil.sendEMail(users.get(0).getEmail(), null, Consts.defaultFrom, "产品发货申请审核通知提醒", cont);
					break;
				}
			}
		}*/

		// 向申请人通知(审批成功，等待出库/审批失败)
		if (approRecord.getState() == SPStatus.SP_NOT_ACCESS.getCode()
				|| record.getState() == ProductOutstockRecordStatus.READY_FOR_OUTSTOCK.getCode()) {
			content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, record.getProposer(),
					null,
					"产品发货申请", (approRecord.getState() == SPStatus.SP_NOT_ACCESS.getCode()
							? ApprovalStatus.APPROVAL_NOT_ACCESS : ApprovalStatus.APPROVAL_ACCESS),
					record, details, dept.getName());
			// 向申请人发送通知邮件
		} else {
			content = MailContentBuilder.buildEmailContent(EmailType.OUTSTOCK_READ_ONLY_NOTICE, user.getName(),
					record.getProposer(), dept.getName(), "产品发货申请", ApprovalStatus.APPROVAL_ACCESS, record, details,
					dept.getName(), curRole.getName());
		}
		mailUtil.sendEMail(user.getEmail(), null, Consts.defaultFrom, "产品发货审核通知", content);

		// 向仓管发送邮件
		if (record.getState() == ProductOutstockRecordStatus.READY_FOR_OUTSTOCK.getCode()) {
			List<User> users = userService.findUserByRole("仓库管理员");
			if (users == null || users.isEmpty()) {
				throw new RuntimeException("未设置仓库管理员角色，请先设置仓库管理员！");
			}

			for (User us : users) {
				String cont = MailContentBuilder.buildEmailContent(EmailType.OUTSTOCK_REMIND_NOTICE, us.getName(),
						record.getProposer(), dept.getName(), "产品发货申请", ApprovalStatus.APPROVAL_ACCESS, record, details,
						dept.getName());
				mailUtil.sendEMail(us.getEmail(), null, Consts.defaultFrom, "产品发货提醒通知", cont);
			}
		}
	}

	/**
	 * @Description: 构建订单编号
	 * @param orderHead
	 * @param example
	 * @return String
	 * @author mlsong
	 * @date 2018年6月20日 下午3:28:22
	 */
	public String buildOrder(String orderHead) {
		StringBuilder builder = new StringBuilder();
		builder.append(orderHead);

		ProductOutstockRecordExample example = new ProductOutstockRecordExample();
		example.setOrderByClause("odd_number desc");

		// 排序
		List<ProductOutstockRecord> records = productOutstockRecordDao.selectByExample(example);

		String orderCode = "";
		if (records != null && !records.isEmpty()) {
			// 获取最新的订单编号
			orderCode = records.get(0).getOddNumber();
		}

		// 获取当前日期
		Date currentTime = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);

		if (StringUtils.isEmpty(orderCode)) { // 无订单记录
			builder.append(dateString);
			builder.append("001");
		} else { // 有订单记录
			// 截取orderHead后面的日期值
			String temp = orderCode.substring(orderHead.length(), orderHead.length() + 8);

			if (temp.equals(dateString)) { // 和当前日期相同，编号加1
				String str = orderCode.substring(orderHead.length());
				Long i = Long.parseLong(str) + 1;
				builder.append(String.valueOf(i));
			} else {
				builder.append(dateString);
				builder.append("001");
			}

		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc) <p>Title: changeProductState</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.hj.oa.service.inter.ProductService#changeProductState(java.lang.
	 * Integer)
	 */
	@Override
	public boolean changeProductState(Integer id) {
		Product product = productDao.selectByPrimaryKey(id);
		if (product.getState() == 0) {
			product.setState(1);
		} else {
			product.setState(0);
		}
		return productDao.updateByPrimaryKeySelective(product) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc) <p>Title: getProductById</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getProductById(java.lang.Integer)
	 */
	@Override
	public Product getProductById(Integer id) {
		return productDao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc) <p>Title: getUpdateProduct</p> <p>Description: </p>
	 * 
	 * @param product
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getUpdateProduct(com.hj.oa.bean.
	 * Product)
	 */
	@Override
	public boolean updateProduct(Product product) {
		return productDao.updateByPrimaryKeySelective(product) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc) <p>Title: addProduct</p> <p>Description: </p>
	 * 
	 * @param product
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#addProduct(com.hj.oa.bean.Product)
	 */
	@Override
	public boolean addProduct(Product product) {
		return productDao.insert(product) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc) <p>Title: getCompanys</p> <p>Description: </p>
	 * 
	 * @param state
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getCompanys(java.lang.Integer)
	 */
	@Override
	public List<Company> getCompanys(Integer state) {

		CompanyExample example = new CompanyExample();
		if (state != null) {
			example.createCriteria().andStateEqualTo(state);
		}
		return companyDao.selectByExample(example);
	}

	/*
	 * (non-Javadoc) <p>Title: getCompanyById</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#getCompanyById(java.lang.Integer)
	 */
	@Override
	public Company getCompanyById(Integer id) {
		return companyDao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc) <p>Title: addCompany</p> <p>Description: </p>
	 * 
	 * @param company
	 * 
	 * @return
	 * 
	 * @see
	 * com.hj.oa.service.inter.ProductService#addCompany(com.hj.oa.bean.Company)
	 */
	@Override
	public boolean addCompany(Company company) {
		return companyDao.insert(company) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc) <p>Title: updateCompany</p> <p>Description: </p>
	 * 
	 * @param company
	 * 
	 * @return
	 * 
	 * @see com.hj.oa.service.inter.ProductService#updateCompany(com.hj.oa.bean.
	 * Company)
	 */
	@Override
	public boolean updateCompany(Company company) {
		return companyDao.updateByPrimaryKeySelective(company) > 0 ? true : false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void handle(ApprovalRecord approRecord, ProductOutstockRecord record, Integer[] productIds, Integer[] counts,
			String[] units, String beginDate,String[] wxbhs,List<String> urls,String[] fhDates) {
		// 保存审批记录
		if (approRecord != null) {
			approvalRecordDao.insert(approRecord);
		}

		if (approRecord == null || approRecord.getState() == SPStatus.SP_ACCESS.getCode()) { // 审批成功
			if (approRecord == null) {
				ApprovalRecordExample example = new ApprovalRecordExample();
				example.createCriteria()
					.andApplyIdEqualTo(record.getId())
					.andTypeEqualTo(ApprovalType.PRODUCT_OUTSTOCK.getType());
				approRecord = approvalRecordDao.selectByExample(example).get(0);
			} 
			// 查询审批列表
			ApprovalList al = approvalService.getApprovalListByType(ApprovalType.PRODUCT_OUTSTOCK);
			if (al == null) {
				throw new RuntimeException("数据库中未设置该类型审批流程，请先联系管理员设置！");
			}
			// 审批角色列表
			String[] roles = al.getRoleList().split(",");

			for (int i = 0; i < roles.length; i++) { // 循环遍历判断审批流程到达的位置

				// 判断当前的审批角色
				if (roles[i].equals(record.getApprovalId().toString())) {
					// 判断审批流程是否结束（若审批列表中下一个没有了，既到头了）
					if (i < (roles.length - 1)) {
						record.setApprovalId(Integer.parseInt(roles[i + 1]));
					} else { // 审批流程已结束
						
						// 审批结束，保存发货详细信息

						
						int sum = 0;
						for (Integer count : counts) {
							sum += count;
						}
						// 发货记录
						OutstockRecord outstockRecord = new OutstockRecord();
						outstockRecord.setCount(sum);
						outstockRecord.setRecordId(record.getId());
						outstockRecord.setOutstockType(false);
						outstockRecord.setType(ApprovalType.PRODUCT_OUTSTOCK.getType());
						outstockRecord.setState(OutstockStatus.READY_FOR_OUTSTOCK.getCode());
						// TODO 插入如外箱编号和照片
//						outstockRecord.setBqz(url);
//						outstockRecord.setWxbh(wxbh);

						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
						try {
							outstockRecord.setExpectedOutstockTime(sdf2.parse(beginDate));
						} catch (ParseException e) {
							throw new RuntimeException("日期格式化异常");
						}
						// 保存发货记录
						outstockRecordDao.insert(outstockRecord);

						List<OutstockDetail> outDetails = new ArrayList<OutstockDetail>();
						// 发货记录中的详细发货产品
						for (int j = 0; j < productIds.length; j++) {
							ProductOutstockDetail product = productOutstockDetailDao.selectByPrimaryKey(productIds[j]);
							OutstockDetail outDetail = new OutstockDetail();
							outDetail.setCount(counts[j]);
							outDetail.setProductId(product.getProductId());
							outDetail.setProductModel(product.getProductModel());
							outDetail.setProductName(product.getProductName());
							outDetail.setRecordId(outstockRecord.getId());
							outDetail.setUnit(units[j]);
							outDetail.setRemark(product.getRemark());
							outDetail.setFhDate(fhDates[j]);
							outDetail.setWxbh(wxbhs[j]);
							outDetail.setBqz(urls.get(j));
							outDetails.add(outDetail);
						}

						// 批量保存详细记录
						outstockDetailDao.insertList(outDetails);
						
						// TODO 判断是否都已经提交发货到仓管处
						int oriCount = productOutstockDetailDao.countProductSumByRecordId(record.getId());
						int outCount = outstockDetailDao.countProductSumByRecordId(record.getId());
						
						if (oriCount == outCount) {
							record.setApprovalId(-1);
							record.setState(ProductOutstockRecordStatus.READY_FOR_OUTSTOCK.getCode());
							productOutstockRecordDao.updateByPrimaryKeySelective(record);
						}
					}
					break;
				}
			}

		} else { // 审批失败
			record.setApprovalId(-1);
			record.setState(ProductOutstockRecordStatus.APPROVAL_NOT_ACCESS.getCode());
			// 更新订单状态
			productOutstockRecordDao.updateByPrimaryKeySelective(record);
		}

		// 发送审批邮件
		sendApprovalMail(record, approRecord);
	}

	/* (non-Javadoc) 
	* <p>Title: getOutstockRecordsByStatus</p> 
	* <p>Description: </p> 
	* @param status
	* @return 
	* @see com.hj.oa.service.inter.ProductService#getOutstockRecordsByStatus(com.hj.commons.OutstockStatus[]) 
	*/
	@Override
	public List<OutstockRecord> getOutstockRecordsByStatus(OutstockStatus... status) {
		// 条件
		OutstockRecordExample example = new OutstockRecordExample();
		if (status == null || status.length == 0) {
			throw new RuntimeException("传入参数不能为空");
		}
		for (int i = 0; i < status.length; i++) {
			OutstockRecordExample.Criteria ca = example.createCriteria();
			ca.andStateEqualTo(status[i].getCode());
			if (i != 0) {
				example.or(ca);
			}
		}
		// 返回查询
		return outstockRecordDao.selectByExample(example);
	}

	/* (non-Javadoc) 
	* <p>Title: getOutstockRecordById</p> 
	* <p>Description: </p> 
	* @param recordId
	* @return 
	* @see com.hj.oa.service.inter.ProductService#getOutstockRecordById(int) 
	*/
	@Override
	public OutstockRecord getOutstockRecordById(int recordId) {
		OutstockRecord record = outstockRecordDao.selectByPrimaryKey(recordId);
		OutstockDetailExample example = new OutstockDetailExample();
		example.createCriteria().andRecordIdEqualTo(recordId);
		List<OutstockDetail> details = outstockDetailDao.selectByExample(example);
		for (OutstockDetail detail : details) {
			Product product = productDao.selectByPrimaryKey(detail.getProductId());
			detail.setOriRemark(product.getRemark());
		}
		record.setDetails(details);
		return record;
	}

	@Override
	public Map<String, Object> getProducts(Product product) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();
		if (product.getState() != null && product.getState() != -1) {
			cri.andStateEqualTo(product.getState());
		}
		if (!StringUtils.isEmpty(product.getProductModel()) ) {
			cri.andProductModelLike("%" + product.getProductModel() + "%");
		}
		int start = 0;
		if (product.getCurPage() == null || product.getCurPage() < 1) {
			example.setLimitStart(0);
		} else {
			example.setLimitStart((product.getCurPage()-1) * product.getPageSize());
			start = (product.getCurPage() - 1) * product.getPageSize();
		}
		example.setLimitEnd(product.getPageSize());
		
		List<Product> products = productDao.selectByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (Product prod : products) {
			if (!map.containsKey(prod.getProductName())) {
				map.put(prod.getProductName(), new ArrayList<Product>());
			}
			List<Product> subList = (List<Product>) map.get(prod.getProductName());
			subList.add(prod);
		}
		
		return map;
	}

	/* (non-Javadoc) 
	* <p>Title: getProductCount</p> 
	* <p>Description: </p> 
	* @param product
	* @return 
	* @see com.hj.oa.service.inter.ProductService#getProductCount(com.hj.oa.bean.Product) 
	*/
	@Override
	public int getProductCount(Product product) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();
		if (product.getState() != null && product.getState() != -1) {
			cri.andStateEqualTo(product.getState());
		}
		if (!StringUtils.isEmpty(product.getProductModel()) ) {
			cri.andProductModelLike("%" + product.getProductModel() + "%");
		}
		return productDao.countByExample(example );
	}

	@Override
	public void updateProductRecordByid(Integer id,String time) {
		this.productDao.updateById(id,time);
	}

	@Override
	public String selectShTime(Integer id) {
		return this.productOutstockRecordDao.findshTime(id);
	}
	
	public List<ProductLogistics> findAll(){
		return this.plMapper.finalAll();
	}
	
	public void insert(ProductLogistics pl){
		 this.plMapper.insertSelective(pl);
	}
	
	public ProductLogistics selectLogById(Integer id){
		return this.plMapper.selectByPrimaryKey(id);
	}
	
	public void update(ProductLogistics pl){
		this.plMapper.updateByPrimaryKeySelective(pl);
	}
	
	public List<Logistics> findAllLogistics (){
		LogisticsExample ex = new LogisticsExample();
		ex.createCriteria().andStatusEqualTo(DbStatus.ACTIVE.getType());
		return this.logisticsMapper.selectByExample(ex);
	}
	
	

}
