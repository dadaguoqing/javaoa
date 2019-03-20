package com.hj.oa.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hj.commons.DbStatus;
import com.hj.commons.MerchandiseConstants;
import com.hj.oa.Consts;
import com.hj.oa.bean.MateriaApprove;
import com.hj.oa.bean.MateriaSeq;
import com.hj.oa.bean.MerchandiseApply;
import com.hj.oa.bean.MerchandiseApplyDetail;
import com.hj.oa.bean.MerchandiseApplyDetailExample;
import com.hj.oa.bean.MerchandiseApplyExample;
import com.hj.oa.bean.MerchandiseDetailRecord;
import com.hj.oa.bean.MerchandiseManage;
import com.hj.oa.bean.MerchandiseManageExample;
import com.hj.oa.bean.MerchandiseRecord;
import com.hj.oa.bean.MerchandiseStock;
import com.hj.oa.bean.MerchandiseSupplier;
import com.hj.oa.bean.MerchandiseSupplierExample;
import com.hj.oa.bean.Page;
import com.hj.oa.bean.Product;
import com.hj.oa.bean.ProductExample;
import com.hj.oa.bean.User;
import com.hj.oa.dao.MateriaMapper_v2;
import com.hj.oa.dao.MerchandiseApplyDetailMapper;
import com.hj.oa.dao.MerchandiseApplyMapper;
import com.hj.oa.dao.MerchandiseManageMapper;
import com.hj.oa.dao.MerchandiseRecordMapper;
import com.hj.oa.dao.MerchandiseStockMapper;
import com.hj.oa.dao.MerchandiseSupplierMapper;
import com.hj.oa.dao.ProductMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;

@Service
public class MerchandiseService {
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	MerchandiseSupplierMapper msSupplierMapper;
	
	@Autowired
	MerchandiseManageMapper msManageMapper;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	MerchandiseApplyMapper msApplyMapper;
	
	@Autowired
	MateriaMapper_v2 maMapper;
	
	@Autowired
	MerchandiseApplyDetailMapper msApplyDetailMapper;
	
	@Autowired
	MerchandiseRecordMapper msRecordMapper;
	
	@Autowired
	MerchandiseStockMapper msStockMapper;
	
	// 成品入库申请审批状态
	private final static Integer MER_INBOUNDS_STATUS_APPROVE = 1;
	// 审批通过
	private final static Integer MER_INBOUNDS_STATUS_PASS = 2;
	// 审批取消
	private final static Integer MER_INBOUNDS_STATUS_FAIL = -1;
	// 入库成功
	private final static Integer MER_INBOUNDS_STATUS_SUCCESS= 3;
	
	public List<MerchandiseSupplier> getSuppliers() throws Exception{
		MerchandiseSupplierExample ex = new MerchandiseSupplierExample();
		ex.createCriteria().andStatusEqualTo(DbStatus.ACTIVE.getType());
		return this.msSupplierMapper.selectByExample(ex);
	}
	
	public void addSupplier(MerchandiseSupplier ms) throws Exception{
		this.msSupplierMapper.insertSelective(ms);
	}
	
	public MerchandiseSupplier findSupplierById(Integer id) throws Exception{
		return this.msSupplierMapper.selectByPrimaryKey(id);
	}
	
	public void updateSupplierBySelective(MerchandiseSupplier ms) throws Exception{
		this.msSupplierMapper.updateByPrimaryKeySelective(ms);
	} 
	
	public List<MerchandiseManage> findManageByType(String type) throws Exception{
		MerchandiseManageExample ex = new MerchandiseManageExample();
		ex.createCriteria().andTypeEqualTo(type).andStatusEqualTo(DbStatus.ACTIVE.getType());
		return this.msManageMapper.selectByExample(ex);
	}
	
	public void insertReason(MerchandiseManage mm) throws Exception{
		this.msManageMapper.insertSelective(mm);
	}
	
	public void updateReason(MerchandiseManage mm) throws Exception{
		this.msManageMapper.updateByPrimaryKeySelective(mm);
	}
	
	public List<Product> getAllProducts() throws Exception{
		ProductExample ex = new ProductExample();
		ex.createCriteria().andStateEqualTo(DbStatus.ACTIVE.getType());
		return this.productMapper.selectByExample(ex);
	}
	
	public void insertMerApply(User user, String applyCode,String startDate2,Integer merType) throws Exception {
		// 插入申请人信息
		MerchandiseApply msApply = new MerchandiseApply();
		msApply.setEmpId(user.getId());
		msApply.setApplyCode(applyCode);
		msApply.setApplyDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		if(!StringUtils.isBlank(startDate2)) {
			msApply.setExpectDate(startDate2);
		}
		msApply.setMerType(merType);
		List<User> users = this.userMapper.findUserByRole(MerchandiseConstants.PRODUCT_BUSINESS_MANAGE);
		if(users.isEmpty()) {
			throw new Exception("未设置产品运营经理角色!");
		}
		// 审批人ID
		msApply.setCurrentId(users.get(0).getId());
		// 审批状态
		msApply.setStatus(MER_INBOUNDS_STATUS_APPROVE);
		this.msApplyMapper.insertSelective(msApply);
	}
	
	public void insertMerApplyDetail(String applyCode,HttpServletRequest req,Integer productType)
			throws Exception {
		String[] productModels = req.getParameterValues("productModel");
		if(productModels != null) {
			String[] quantitys = req.getParameterValues("quantity");
			String[] units = req.getParameterValues("unit");
			String[] productCodes = req.getParameterValues("productCode");
			String[] weekCodes = req.getParameterValues("weekCode");
			String[] packageCodes = req.getParameterValues("packageCode");
			String[] inboundReasons = req.getParameterValues("inboundReason");
			String[] suppliers = req.getParameterValues("supplier");
			String[] inboundSources = req.getParameterValues("inboundSource");
			String[] locations = req.getParameterValues("location");
			String[] suplusQuantitys = req.getParameterValues("suplusQuantity");
			String[] contents = req.getParameterValues("content");
			for(int i = 0; i < productModels.length; i++) {
				MerchandiseApplyDetail msApplyDetail = new MerchandiseApplyDetail();
				msApplyDetail.setApplyCode(applyCode);
				msApplyDetail.setProductType(productType);
				msApplyDetail.setProductModel(productModels[i]);
				if(null != quantitys) {
					msApplyDetail.setQuantity(Integer.parseInt(quantitys[i]));
					msApplyDetail.setSuplusQuantity(Integer.parseInt(quantitys[i]));
				}
				if(null != units) {
					msApplyDetail.setUnit(units[i]);
				}
				if(null != productCodes) {
					msApplyDetail.setProductCode(productCodes[i]);
				}
				if(null != weekCodes) {
					msApplyDetail.setWeekCode(weekCodes[i]);
				}
				if(null != packageCodes) {
					msApplyDetail.setPackageCode(packageCodes[i]);
				}
				if(null != inboundReasons) {
					msApplyDetail.setInboundReason(inboundReasons[i]);
				}
				if(null != suppliers) {
					msApplyDetail.setSupplier(suppliers[i]);
				}
				if(null != inboundSources) {
					msApplyDetail.setInboundSource(inboundSources[i]);
				}
				if(null != locations) {
					msApplyDetail.setLocation(locations[i]);
				}
				if(null != suplusQuantitys) {
					msApplyDetail.setSuplusQuantity(Integer.parseInt(suplusQuantitys[i]));
				}
				if(null != contents) {
					msApplyDetail.setContent(contents[i]);
				}
				this.msApplyDetailMapper.insertSelective(msApplyDetail);
			}
		} else {
			throw new Exception("数据异常");
		}
		
	}
	
	public void updateSeq(String number, Integer id,Integer subLength) {
		// 更新seq
		MateriaSeq ms = new MateriaSeq();
		ms.setId(id);
		ms.setCurrentId(Integer.parseInt(number.substring(8 + subLength, 11 + subLength)));
		ms.setDate(number.substring(subLength, subLength + 8));
		this.maMapper.updateMateriaSeq(ms);
	}
	
	// 分页
	public List<MerchandiseApply> findMyApplyRecord(Integer userId,Integer applyType,
			Integer beginIndex,Integer pageSize,Integer type) {
		MerchandiseApplyExample ex = new MerchandiseApplyExample();
		ex.setBeginIndex(beginIndex);
		ex.setPageSize(pageSize);
		ex.setOrderByClause("id desc");
		if(1 == type) {
			ex.createCriteria().andEmpIdEqualTo(userId).andMerTypeEqualTo(applyType);
		} else if(2 == type) {
			ex.createCriteria().andMerTypeEqualTo(applyType).
				andCurrentIdEqualTo(userId).andStatusEqualTo(MER_INBOUNDS_STATUS_APPROVE);
		} else if (3 == type) {
			List<Integer> status = new ArrayList<Integer>();
			status.add(MER_INBOUNDS_STATUS_FAIL);
			status.add(MER_INBOUNDS_STATUS_PASS);
			ex.createCriteria().andMerTypeEqualTo(applyType).andStatusIn(status);
		}
		List<MerchandiseApply> msApply = this.msApplyMapper.selectByExample(ex);
		return msApply;
	}
	
	public long findAllRecord(Integer userId,Integer applyType,Integer type) {
		MerchandiseApplyExample ex = new MerchandiseApplyExample();
		if(1 == type) {
			ex.createCriteria().andEmpIdEqualTo(userId).andMerTypeEqualTo(applyType);
		} else if(2 == type){
			ex.createCriteria().andMerTypeEqualTo(applyType).
			andCurrentIdEqualTo(userId).andStatusEqualTo(MER_INBOUNDS_STATUS_APPROVE);
		} else if(3 == type) {
			List<Integer> status = new ArrayList<Integer>();
			status.add(MER_INBOUNDS_STATUS_FAIL);
			status.add(MER_INBOUNDS_STATUS_PASS);
			ex.createCriteria().andMerTypeEqualTo(applyType).andStatusIn(status);
		}
		long totalSize = this.msApplyMapper.countByExample(ex);
		return totalSize;
	}
	
	public MerchandiseApply findApplyById(Integer id) {
		return this.msApplyMapper.selectByPrimaryKey(id);
	}
	
	public List<MerchandiseApplyDetail> findApplyDetailByApplyCode(String applyCode) {
		MerchandiseApplyDetailExample ex = new MerchandiseApplyDetailExample();
		ex.setOrderByClause("id");
		ex.createCriteria().andApplyCodeEqualTo(applyCode);
		return this.msApplyDetailMapper.selectByExample(ex);
	}
	
	public void approveApply(String sp, String opinion, Integer id, User user) throws Exception {
		MerchandiseApply msApply = this.msApplyMapper.selectByPrimaryKey(id);
		if(null == msApply) {
			throw new Exception("审批出错，记录不存在");
		} else if(user.getId() != msApply.getCurrentId()) {
			throw new Exception("您没有权限审批此申请");
		}
		
		// 更新审批信息
		Integer status = msApply.getStatus();
		if(status == 1) {
			if("通过".equals(sp)) {
				msApply.setStatus(status + 1);
			} else {
				// 审批不通过
				msApply.setStatus(-1);
			}
			msApply.setCurrentId(-1);
		}
		this.msApplyMapper.updateByPrimaryKeySelective(msApply);
		
		// 插入审批记录
		MateriaApprove maApprove = new MateriaApprove();
		maApprove.setCode(msApply.getApplyCode());
		maApprove.setApproveId(user.getId());
		maApprove.setApproveStatus(status);
		maApprove.setApproveResult(sp);
		maApprove.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		maApprove.setApproveOpinion(opinion);
		this.maMapper.insertMateriaApproveSelective(maApprove);
	}
	
	public List<MerchandiseApply> findHandleByType(Integer type, @SuppressWarnings("rawtypes") Page page, Integer type2) {
		MerchandiseApplyExample ex = new MerchandiseApplyExample();
		ex.setBeginIndex(page.getBeginPageIndex());
		ex.setPageSize(page.getPageSize());
		ex.setOrderByClause("apply_date desc");
		if(type2 == 1) {
			ex.createCriteria().andStatusEqualTo(MER_INBOUNDS_STATUS_PASS).andMerTypeEqualTo(type);
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(MER_INBOUNDS_STATUS_SUCCESS);
			list.add(MER_INBOUNDS_STATUS_FAIL);
			ex.createCriteria().andStatusIn(list).andMerTypeEqualTo(type);
		}
		return this.msApplyMapper.selectByExample(ex);
	}
	
	public long findTotalHandleByType(Integer type, Integer type2) {
		MerchandiseApplyExample ex = new MerchandiseApplyExample();
		if(type2 == 1) {
			ex.createCriteria().andStatusEqualTo(MER_INBOUNDS_STATUS_PASS).andMerTypeEqualTo(type);
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(MER_INBOUNDS_STATUS_SUCCESS);
			list.add(MER_INBOUNDS_STATUS_FAIL);
			ex.createCriteria().andStatusIn(list).andMerTypeEqualTo(type);
		}
		return this.msApplyMapper.countByExample(ex);
	}
	
	public void updateApplyBySelective(MerchandiseApply msApply) {
		this.msApplyMapper.updateByPrimaryKeySelective(msApply);
	}
	
	// 入库
	private final static String INBOUND_TYPE = "入库";
	
	@Transactional(rollbackFor = Exception.class)  // 事务处理
	public void toInbound(Integer id, String[] productModels,
			String[] quantitys, String[] productIds) throws Exception {
		// 查询申请信息
		String date = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		MerchandiseApply msApply = this.findApplyById(id);
		msApply.setSendStatus(msApply.getSendStatus()+1);
		// 查询申请详情
		List<MerchandiseApplyDetail> msApplyDetails = this.findApplyDetailByApplyCode(msApply.getApplyCode());
		// 验证入库数量是否合法
		for(int i = 0; i < productModels.length; i++) {
			for(MerchandiseApplyDetail msApplyDetail : msApplyDetails) {
				if(productModels[i].equals(msApplyDetail.getProductModel())) {
					Integer suplusQuantity = msApplyDetail.getSuplusQuantity();
					Integer inboundQuantity = Integer.parseInt(quantitys[i]);
					Integer curQuantity = suplusQuantity - inboundQuantity;
					if(curQuantity < 0) {
						throw new Exception(productModels[i] + "剩余入库数量不足");
					} else { 
						msApplyDetail.setSuplusQuantity(curQuantity);
						// 更新剩余库存数量
						this.msApplyDetailMapper.updateByPrimaryKeySelective(msApplyDetail);
						// 入库
						MerchandiseStock msStock = new MerchandiseStock();
						msStock.setProductType(msApplyDetail.getProductType());
						msStock.setProductModel(msApplyDetail.getProductModel());
						msStock.setQuantity(Integer.parseInt(quantitys[i]));
						msStock.setUnit(msApplyDetail.getUnit());
						msStock.setProductCode(msApplyDetail.getProductCode());
						msStock.setWeekCode(msApplyDetail.getWeekCode());
						msStock.setPackageCode(msApplyDetail.getPackageCode());
						msStock.setInboundReason(msApplyDetail.getInboundReason());
						msStock.setSupplier(msApplyDetail.getSupplier());
						msStock.setInboundSource(msApplyDetail.getInboundSource());
						msStock.setInboundDate(date);
						msStock.setLocation(msApplyDetail.getLocation());
						this.msStockMapper.insertSelective(msStock);
					}
				}
			}
		}
		// 插入入库记录
				MerchandiseRecord msRecord = new MerchandiseRecord();
				msRecord.setRecordType(INBOUND_TYPE);
				msRecord.setChangeDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
				msRecord.setSendStatus(msApply.getSendStatus());
				msRecord.setApplyCode(msApply.getApplyCode());
				for(int i = 0; i < productModels.length; i++) {
					msRecord.setApplyDetailId(Integer.parseInt(productIds[i]));
					msRecord.setQuantity(Integer.parseInt(quantitys[i]));
					this.msRecordMapper.insertSelective(msRecord);
				}
				
				// 若剩余入库数量都为0，则入库完成 状态改为3
				boolean flag = false;
				for(MerchandiseApplyDetail msApplyDetail : msApplyDetails) {
					if(msApplyDetail.getSuplusQuantity() != 0) {
						flag = true;
						break;
					}
				}
				
				// 入库完成
				if(!flag) {
					// 更新申请状态 -1 不通过
					msApply.setStatus(MER_INBOUNDS_STATUS_SUCCESS);
				}
				this.updateApplyBySelective(msApply);
		}
		
		
	
	public List<MerchandiseDetailRecord> findDetailRecordByApplyCode(String applyCode) {
		return this.msRecordMapper.findDetailRecordByApplyCode(applyCode);
	}
	
	
	
	
}
