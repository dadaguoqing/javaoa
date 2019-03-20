package com.hj.oa.service;

import java.awt.FontFormatException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hj.commons.Constants;
import com.hj.commons.DbStatus;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.ExternalApply;
import com.hj.oa.bean.ExternalApplyDetail;
import com.hj.oa.bean.ExternalApplyDetailExample;
import com.hj.oa.bean.ExternalApplyExample;
import com.hj.oa.bean.ExternalDeal;
import com.hj.oa.bean.ExternalDealExample;
import com.hj.oa.bean.ExternalInbound;
import com.hj.oa.bean.ExternalInboundExample;
import com.hj.oa.bean.ManageConfig;
import com.hj.oa.bean.Page;
import com.hj.oa.bean.User;
import com.hj.oa.dao.ExternalApplyDetailMapper;
import com.hj.oa.dao.ExternalApplyMapper;
import com.hj.oa.dao.ExternalDealMapper;
import com.hj.oa.dao.ExternalInboundMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;
import com.hj.util.MailTableUtil;
import com.hj.util.UploadUtilV2;


/**
 * @ClassName:  ExternalService   
 * @Description: 外协加工
 * @author: wqfang
 * @date:   2018年11月8日 上午9:49:48   
 *
 */
@Service
public class ExternalService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ExternalApplyMapper eaApplyMapper;
	@Autowired
	private ExternalApplyDetailMapper eaApplyDetailMapper;
	@Autowired
	private ExternalDealMapper exDealMapper;
	@Autowired
	private ExternalInboundMapper exInboundMapper;
	@Autowired
	private MailTableUtil mailTableUtil;
	@Autowired
	private CommonService commService;
	
	@Transactional(rollbackFor = Exception.class)
	public ExternalApply saveApply(ExternalApply ea, User user, ApproveStaff as, String applyCode, String type) throws Exception {
		ea.setProposer(user.getId());
		ea.setApplyDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ea.setApplyCode(applyCode);
		ea.setExternalType(type);
		
		Integer dir = as.getDeptDirector();
		Integer sup = as.getDeptSupremo();
		List<User> ters = this.userMapper.findUserByRole(Constants.EXTERNAL_TREASURER1);
		if(ters.isEmpty()) {
			throw new RuntimeException("未设置外协加工财务审批角色，请联系管理员");
		}
		Integer ter = ters.get(0).getId();
		Integer man = as.getManager();
		
		int status = 1;
		int currentId = dir;
		if(dir == sup || dir == ter || dir == man) {
			status = 2;
			currentId = sup;
		}
		if((dir == sup && dir == ter) || (dir == ter && sup == man)) {
			status = 3;
			currentId = ter;
		}
		ea.setCurrentId(currentId);
		ea.setStatus(status);
		this.eaApplyMapper.insertSelective(ea);
		return ea;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public ExternalApplyDetail saveApplyDetail(String type, HttpServletRequest req, 
			String applyCode, ExternalApply ea) throws Exception {
		// 保存详细信息
		String[] types = type.split(",");
		if(types == null) {
			throw new RuntimeException("请选择加工类型！");
		}
		ExternalApplyDetail eaDetail = new ExternalApplyDetail();
		eaDetail.setApplyCode(applyCode);
		for(int i = 0; i < types.length; i++ ) {
			// PCB加工数据
			if("1".equals(types[i])) {
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile pcbDescriptFile = mReq.getFile("pcbDescript");
				MultipartFile impedanceDescriptFile = mReq.getFile("impedanceDescript");
				MultipartFile pcbGerberFile = mReq.getFile("pcbGerber");
				String pcbDescript = UploadUtilV2.uploadFileByType(pcbDescriptFile, req, 5, UploadUtilV2.ANY);
				// 阻抗说明非必选
				if(!impedanceDescriptFile.isEmpty()) {
					String impedanceDescript = UploadUtilV2.uploadFileByType(impedanceDescriptFile, req, 5, UploadUtilV2.ANY);
					eaDetail.setImpedanceDescript(impedanceDescript);
				}
				String pcbGerber = UploadUtilV2.uploadFileByType(pcbGerberFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setPcbDescription(pcbDescript);
				eaDetail.setPcbGerber(pcbGerber);
				String pcbCost = req.getParameter("pcbCost");
				try {
					eaDetail.setPcbCost(new BigDecimal(pcbCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("PCB加工费用格式错误！");
				}
				String pcbNum = req.getParameter("pcbNum");
				try {
					eaDetail.setPcbNum(Integer.parseInt(pcbNum));
				} catch (Exception e) {
					throw new FontFormatException("PCB加工数量格式错误！");
				}
			}
			// 焊接加工加工数据
			if("2".equals(types[i])) {
				String weldNum = req.getParameter("weldNum");
				try {
					eaDetail.setWeldNum(Integer.parseInt(weldNum));
				} catch (Exception e) {
					throw new RuntimeException("焊接数量格式错误！");
				}
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile weldBomFile = mReq.getFile("weldBom");
				String weldBom = UploadUtilV2.uploadFileByType(weldBomFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setWeldBom(weldBom);
				MultipartFile weldGerberFile = mReq.getFile("weldGerber");
				String weldGerber = UploadUtilV2.uploadFileByType(weldGerberFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setWeldGerber(weldGerber);
				MultipartFile weldCoordinateFile = mReq.getFile("weldCoordinate");
				String weldCoordinate = UploadUtilV2.uploadFileByType(weldCoordinateFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setWeldCoordinate(weldCoordinate);
				MultipartFile weldSilkScreenFile = mReq.getFile("weldSilkScreen");
				String weldSilkScreen = UploadUtilV2.uploadFileByType(weldSilkScreenFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setWeldSilkScreen(weldSilkScreen);
				// 非必须文件
				MultipartFile weldDescriptFile = mReq.getFile("weldDescript");
				if(!weldDescriptFile.isEmpty()) {
					String weldDescript = UploadUtilV2.uploadFileByType(weldDescriptFile, req, 5, UploadUtilV2.ANY);
					eaDetail.setWeldDescript(weldDescript);
				}
				String weldCost = req.getParameter("weldCost");
				try {
					eaDetail.setWeldCost(new BigDecimal(weldCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("焊接加工费用格式错误！");
				}
				String componentCost = req.getParameter("componentCost");
				try {
					eaDetail.setComponentCost(new BigDecimal(componentCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("焊接加工数量格式错误！");
				}
			}
			// 钢网加工参数
			if("3".equals(types[i])) {
				String steelNum = req.getParameter("steelNum");
				String steelSize1 = req.getParameter("steelSize1");
				String steelSize2 = req.getParameter("steelSize2");
				String steelThinkness = req.getParameter("steelThinkness");
				String steelMaterial = req.getParameter("steelMaterial");
				String steelUse = req.getParameter("steelUse");
				String steelPolishing = req.getParameter("steelPolishing");
				String steelCost = req.getParameter("steelCost");
				try {
					eaDetail.setSteelNum(Integer.parseInt(steelNum));
					eaDetail.setSteelSize1(new BigDecimal(steelSize1).setScale(2, BigDecimal.ROUND_HALF_UP));
					eaDetail.setSteelSize2(new BigDecimal(steelSize2).setScale(2, BigDecimal.ROUND_HALF_UP));
					eaDetail.setSteelThinkness(new BigDecimal(steelThinkness).setScale(2, BigDecimal.ROUND_HALF_UP));
					eaDetail.setSteelCost(new BigDecimal(steelCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("钢网加工参数格式错误！");
				}
				eaDetail.setSteelMaterial(steelMaterial);
				eaDetail.setSteelUse(steelUse);
				eaDetail.setSteelPolishing(steelPolishing);
			}
			// 打白胶加工数据
			if("4".equals(types[i])) {
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile glueDescriptFile = mReq.getFile("glueDescript");
				String glueDescript = UploadUtilV2.uploadFileByType(glueDescriptFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setGlueDescript(glueDescript);
				String glueCost = req.getParameter("glueCost");
				try {
					eaDetail.setGlueCost(new BigDecimal(glueCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("打白胶加工费用格式错误！");
				}
			}
			// 三防漆加工数据
			if("5".equals(types[i])) {
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile paintDescriptFile = mReq.getFile("paintDescript");
				String paintDescript = UploadUtilV2.uploadFileByType(paintDescriptFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setPaintDescript(paintDescript);
				String paintCost = req.getParameter("paintCost");
				try {
					eaDetail.setPaintCost(new BigDecimal(paintCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("三防漆加工费用格式错误！");
				}
			}
			// 亚克力加工数据
			if("6".equals(types[i])) {
				String acrylicNum = req.getParameter("acrylicNum");
				try {
					eaDetail.setAcrylicNum(Integer.parseInt(acrylicNum));
				} catch (Exception e) {
					throw new FontFormatException("亚克力加工数量格式错误！");
				}
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile acrylicCadFile = mReq.getFile("acrylicCad");
				String acrylicCad = UploadUtilV2.uploadFileByType(acrylicCadFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setAcrylicCad(acrylicCad);
				String acrylicCost = req.getParameter("acrylicCost");
				try {
					eaDetail.setAcrylicCost(new BigDecimal(acrylicCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("亚克力加工费用格式错误！");
				}
			}
			// 机箱加工
			if("7".equals(types[i])) {
				String chassisNum = req.getParameter("chassisNum");
				try {
					eaDetail.setChassisNum(Integer.parseInt(chassisNum));
				} catch (Exception e) {
					throw new FontFormatException("机箱加工数量格式错误！");
				}
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile chassisCadFile = mReq.getFile("chassisCad");
				String chassisCad = UploadUtilV2.uploadFileByType(chassisCadFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setChassisCad(chassisCad);
				String chassisCost = req.getParameter("chassisCost");
				try {
					eaDetail.setChassisCost(new BigDecimal(chassisCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("机箱加工费用格式错误！");
				}
			}
			// 线束加工
			if("8".equals(types[i])) {
				String pencilNum = req.getParameter("pencilNum");
				try {
					eaDetail.setPencilNum(Integer.parseInt(pencilNum));
				} catch (Exception e) {
					throw new FontFormatException("线束加工数量格式错误！");
				}
				MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
				MultipartFile pencilCadFile = mReq.getFile("pencilCad");
				String pencilCad = UploadUtilV2.uploadFileByType(pencilCadFile, req, 5, UploadUtilV2.ANY);
				eaDetail.setPencilCad(pencilCad);
				String pencilCost = req.getParameter("pencilCost");
				try {
					eaDetail.setPencilCost(new BigDecimal(pencilCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					throw new FontFormatException("线束加工费用格式错误！");
				}
			}
			String totalCost = req.getParameter("totalCost");
			try {
				eaDetail.setTotalCost(new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP));
			} catch (Exception e) {
				throw new FontFormatException("预算合计格式转换错误！");
			}
			String isUrgent = req.getParameter("isUrgent");
			eaDetail.setIsUrgent(isUrgent);
		}
		this.eaApplyDetailMapper.insertSelective(eaDetail);
		return eaDetail;
	}
	
	public void sendApplyMail(ExternalApply ea, ExternalApplyDetail eaDetail) {
		String table = MailTableUtil.createExternalApplyTable(ea, eaDetail);
		User user2 = this.userMapper.findById(ea.getProposer());
		User user = this.userMapper.findById(ea.getCurrentId());
		this.mailTableUtil.sendMailTable(user, user2, Constants.EXTERNAL_APPLY, table);
	}
	
	public long countApproveList(Integer id) {
		ExternalApplyExample ex = new ExternalApplyExample();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		ex.createCriteria().andCurrentIdEqualTo(id).andStatusIn(list);
		return this.eaApplyMapper.countByExample(ex);
	}
	
	public ExternalApply selectExternalApplyById(Integer id) {
		return this.eaApplyMapper.selectByPrimaryKey(id);
	}
	
	public ExternalApplyDetail selectExternalApplyDetailByApplyCode(String applyCode) {
		ExternalApplyDetailExample ex = new ExternalApplyDetailExample();
		ex.createCriteria().andApplyCodeEqualTo(applyCode);
		List<ExternalApplyDetail> lists = this.eaApplyDetailMapper.selectByExample(ex);
		return lists.isEmpty() ? null : lists.get(0);
	}
	
	
	public void updateApproveStatus(String sp, ExternalApply ea, ApproveStaff as, ExternalApplyDetail eaDetail) {
		Integer status = ea.getStatus();
		int currentId = 0;
		int currentStatus = 0;
		List<User> users = this.userMapper.findUserByRole(Constants.EXTERNAL_TREASURER1);
		
		int sup = as.getDeptSupremo();
		int ter = users.get(0).getId();
		int man = as.getManager();
				
		if("通过".equals(sp)) {
			switch(status) {
				case 1 :
					currentId = sup;
					currentStatus = 2;
					if(sup == ter || sup == man) {
						currentId = ter;
						currentStatus = 3;
					}
					break;
				case 2 : 
					currentId = ter;
					currentStatus = 3;
					if(ter == man) {
						currentId = man;
						currentStatus = 4;
					}
					break;
				case 3 : 
						currentId = man;
						currentStatus = 4;
						ManageConfig mc = this.commService.selectConfigById(3);
						BigDecimal cost = new BigDecimal(mc.getContent());
						int i = eaDetail.getTotalCost().compareTo(cost);
						if(i <= 0) {
							currentId = DbStatus.FREEZE.getType();
							currentStatus = 5;
						}
					break;
				case 4 : 
					currentId = DbStatus.FREEZE.getType();
					currentStatus = 5;
					break;
			}
		} else {
			currentId = DbStatus.FREEZE.getType();
			currentStatus = DbStatus.FREEZE.getType();
		}
		ea.setStatus(currentStatus);
		ea.setCurrentId(currentId);
		this.eaApplyMapper.updateByPrimaryKeySelective(ea);
		String table = MailTableUtil.createExternalApplyTable(ea, eaDetail);
		User user = this.userMapper.findById(ea.getCurrentId());
		User user2 = this.userMapper.findById(ea.getProposer());
		if(currentStatus > 1 && currentStatus < 5) {
			this.mailTableUtil.sendMailTable(user, user2, Constants.EXTERNAL_APPLY, table);
		} else {
			this.mailTableUtil.sendMaileResult2(user2, currentStatus, Constants.EXTERNAL_APPLY, table);
		}
		if(currentStatus == 5) {
			List<User> handlers = this.userMapper.findUserByRole(Constants.EXTERNAL_MANAGE);
			this.mailTableUtil.sendDealNotice(handlers.get(0), user2, Constants.EXTERNAL_APPLY, table);
		}
	}
	
	public void updateApproveStatus2(String sp, ExternalApply ea, ExternalApplyDetail eaDetail, ExternalDeal eaDeal) {
		Integer status = ea.getStatus();
		int currentId = 0;
		int currentStatus = 0;
		List<User> users2 = this.userMapper.findUserByRole(Constants.PRODUCTION_MAJORDOMO);
		List<User> users3 = this.userMapper.findUserByRole(Constants.EXTERNAL_TREASURER2);
		int sup = users2.get(0).getId();
		int ter = users3.get(0).getId();
		int man = Constants.GENERAL_MANAGER;
		
		if("通过".equals(sp)) {
			switch(status) {
			case 6 :
				currentId = sup;
				currentStatus = 7;
				if(sup == ter || sup == man) {
					currentId = ter;
					currentStatus = 8;
				}
				break;
			case 7 : 
				currentId = ter;
				currentStatus = 8;
				if(ter == man) {
					currentId = man;
					currentStatus = 9;
				}
				break;
			case 8 : 
				currentId = man;
				currentStatus = 9;
				ManageConfig mc = this.commService.selectConfigById(4);
				BigDecimal cost = new BigDecimal(mc.getContent());
				int i = eaDeal.getTotalCost().compareTo(cost);
				if(i <= 0) {
					currentId = DbStatus.FREEZE.getType();
					currentStatus = 10;
				}
				break;
			case 9 : 
				currentId = DbStatus.FREEZE.getType();
				currentStatus = 10;
				break;
			}
		} else {
			currentId = DbStatus.FREEZE.getType();
			currentStatus = DbStatus.FREEZE.getType();
		}
		ea.setStatus(currentStatus);
		ea.setCurrentId(currentId);
		this.eaApplyMapper.updateByPrimaryKeySelective(ea);
		String table = MailTableUtil.createExternalDealTable(ea, eaDeal, eaDetail);
		// 发送审批邮件
		User approver = this.userMapper.findById(ea.getCurrentId());
		List<User> handles = this.userMapper.findUserByRole(Constants.EXTERNAL_MANAGE);
		if(!handles.isEmpty()) {
			if(currentStatus > 6 && currentStatus < 10) {
				this.mailTableUtil.sendMailTable(approver, handles.get(0), Constants.EXTERNAL_DEAL, table);
			} else {
				this.mailTableUtil.sendMaileResult2(handles.get(0), currentStatus, Constants.EXTERNAL_DEAL, table);
			}
		}
	}
	
	public long countApplyList(Integer id) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andProposerEqualTo(id);
		return this.eaApplyMapper.countByExample(ex);
	}
	
	@SuppressWarnings("rawtypes")
	public List<ExternalApply> getApplyList(Integer id, Page page) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.setBeginIndex(page.getBeginPageIndex());
		ex.setPageSize(page.getPageSize());
		ex.createCriteria().andProposerEqualTo(id);
		ex.setOrderByClause("id desc");
		return this.eaApplyMapper.selectByExample(ex);
	}
	
	public int countApproveRecordList(Integer id) {
		List<String> applyCodes = this.eaApplyMapper.findApproveRecord(id, "WXJG");
		return applyCodes.isEmpty() ? 0 : applyCodes.size();
	}
	
	@SuppressWarnings("rawtypes")
	public List<ExternalApply> getApproveRecordList(Integer id, Page page, int type) {
		List<String> applyCodes = this.eaApplyMapper.findApproveRecord(id, "WXJG");
		List<ExternalApply> list = new ArrayList<ExternalApply>();
		for(String applyCode : applyCodes) {
			ExternalApplyExample ex = new ExternalApplyExample();
			ex.createCriteria().andApplyCodeEqualTo(applyCode);
			List<ExternalApply> eas = this.eaApplyMapper.selectByExample(ex);
			list.addAll(eas);
		}
		List<ExternalApply> list2 = new ArrayList<ExternalApply>();
		if(!list.isEmpty()) {
			list2 = list.subList(page.getBeginPageIndex(), page.getEndPageIndex() + 1);
		}
		return list2;
	}
	
	public List<ExternalApply> selectByExample(ExternalApplyExample example) {
		return this.eaApplyMapper.selectByExample(example);
	}
	
	public long countByExample(ExternalApplyExample example) {
		return this.eaApplyMapper.countByExample(example);
	}
	
	public int insertExternalDeal(ExternalDeal record) {
		return this.exDealMapper.insertSelective(record);
	}
	
	public int insertExternalApply(ExternalApply ea) {
		return this.eaApplyMapper.insertSelective(ea);
	}
	
	public int updateDealStatus(ExternalApply ea, ExternalApplyDetail eaDetail, ExternalDeal eaDeal) {
		// 查询审批人
		List<User> users = this.userMapper.findUserByRole(Constants.PRODUCTION_DIRECTOR);
		List<User> users2 = this.userMapper.findUserByRole(Constants.PRODUCTION_MAJORDOMO);
		List<User> users3 = this.userMapper.findUserByRole(Constants.EXTERNAL_TREASURER2);
		int dir = users.get(0).getId();
		int sup = users2.get(0).getId();
		int ter = users3.get(0).getId();
		int man = Constants.GENERAL_MANAGER;
		
		int curStatus = 6;
		int curId = dir;
		if(dir == sup || dir == ter || dir == man) {
			curStatus = 7;
			curId = sup;
		}
		if((dir == sup && dir == ter) 
				|| (dir == sup && dir == man)
				|| (dir == ter && sup == man)
				|| (dir == man && sup == ter)) {
			curStatus = 8;
			curId = ter;
		}
		if(dir == sup && dir == ter && dir == man) {
			curStatus = 9;
			curId = man;
		}
		ea.setCurrentId(curId);
		ea.setStatus(curStatus);
		
		// 发邮件给申请人
		String table = MailTableUtil.createExternalDealTable(ea, eaDeal, eaDetail);
		List<User> handlers = this.userMapper.findUserByRole(Constants.EXTERNAL_MANAGE);
		User proposer = this.userMapper.findById(ea.getProposer());
		this.mailTableUtil.sendStartDealNotice(proposer, Constants.EXTERNAL_APPLY, table);
				
		// 发邮件给审批人
		User approver = this.userMapper.findById(ea.getCurrentId());
		if(!handlers.isEmpty()) {
			this.mailTableUtil.sendMailTable(approver, handlers.get(0), Constants.EXTERNAL_DEAL, table);
		}
		return this.eaApplyMapper.updateByPrimaryKeySelective(ea);
	}
	
	
	public ExternalDeal saveDeal(ExternalApply ea, HttpServletRequest req) throws Exception {
		ExternalDeal exDeal = new ExternalDeal();
		exDeal.setApplyCode(ea.getApplyCode());
		MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
		String[] types = ea.getExternalType().split(",");
		for(String type : types) {
			if("1".equals(type)) {
				String pcbCost = req.getParameter("pcbCost");
				exDeal.setPcbCost(new BigDecimal(pcbCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String pcbDate = req.getParameter("pcbDate");
				exDeal.setPcbDate(pcbDate);
				MultipartFile pcbPdfFile = mreq.getFile("pcbPdf");
				String pcbPdf = UploadUtilV2.uploadFileByType(pcbPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setPcbPdf(pcbPdf);
				continue;
			}
			if("2".equals(type)) {
				String weldCost = req.getParameter("weldCost");
				exDeal.setWeldCost(new BigDecimal(weldCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String weldDate = req.getParameter("weldDate");
				exDeal.setWeldDate(weldDate);
				MultipartFile weldPdfFile = mreq.getFile("weldPdf");
				String weldPdf = UploadUtilV2.uploadFileByType(weldPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setWeldPdf(weldPdf);
				continue;
			}
			if("3".equals(type)) {
				String steelCost = req.getParameter("steelCost");
				exDeal.setSteelCost(new BigDecimal(steelCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String steelDate = req.getParameter("steelDate");
				exDeal.setSteelDate(steelDate);
				MultipartFile steelPdfFile = mreq.getFile("steelPdf");
				String steelPdf = UploadUtilV2.uploadFileByType(steelPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setSteelPdf(steelPdf);
				continue;
			}
			if("4".equals(type)) {
				String glueCost = req.getParameter("glueCost");
				exDeal.setGlueCost(new BigDecimal(glueCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String glueDate = req.getParameter("glueDate");
				exDeal.setGlueDate(glueDate);
				MultipartFile gluePdfFile = mreq.getFile("gluePdf");
				String gluePdf = UploadUtilV2.uploadFileByType(gluePdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setGluePdf(gluePdf);
				continue;
			}
			if("5".equals(type)) {
				String paintCost = req.getParameter("paintCost");
				exDeal.setPaintCost(new BigDecimal(paintCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String paintDate = req.getParameter("paintDate");
				exDeal.setPaintDate(paintDate);
				MultipartFile paintPdfFile = mreq.getFile("paintPdf");
				String paintPdf = UploadUtilV2.uploadFileByType(paintPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setPaintPdf(paintPdf);
				continue;
			}
			if("6".equals(type)) {
				String acrylicCost = req.getParameter("acrylicCost");
				exDeal.setAcrylicCost(new BigDecimal(acrylicCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String acrylicDate = req.getParameter("acrylicDate");
				exDeal.setAcrylicDate(acrylicDate);
				MultipartFile acrylicPdfFile = mreq.getFile("acrylicPdf");
				String acrylicPdf = UploadUtilV2.uploadFileByType(acrylicPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setAcrylicPdf(acrylicPdf);
				continue;
			}
			if("7".equals(type)) {
				String chassisCost = req.getParameter("chassisCost");
				exDeal.setChassisCost(new BigDecimal(chassisCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String chassisDate = req.getParameter("chassisDate");
				exDeal.setChassisDate(chassisDate);
				MultipartFile chassisPdfFile = mreq.getFile("chassisPdf");
				String chassisPdf = UploadUtilV2.uploadFileByType(chassisPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setChassisPdf(chassisPdf);
				continue;
			}
			if("8".equals(type)) {
				String pencilCost = req.getParameter("pencilCost");
				exDeal.setPencilCost(new BigDecimal(pencilCost).setScale(2, BigDecimal.ROUND_HALF_UP));
				String pencilDate = req.getParameter("pencilDate");
				exDeal.setPencilDate(pencilDate);
				MultipartFile pencilPdfFile = mreq.getFile("pencilPdf");
				String pencilPdf = UploadUtilV2.uploadFileByType(pencilPdfFile, req, 5, UploadUtilV2.PDFFILE);
				exDeal.setPencilPdf(pencilPdf);
				continue;
			}
		}
		String totalCost = req.getParameter("totalCost");
		exDeal.setTotalCost(new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP));
		int count = this.exDealMapper.insertSelective(exDeal);
		if(count == 0) {
			throw new SQLException("数据保存失败！");
		}
		return exDeal;
	}
	
	public long countDealApproveList(int id) {
		ExternalApplyExample ex = new ExternalApplyExample();
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		ex.createCriteria().andStatusIn(list).andCurrentIdEqualTo(id);
		return this.eaApplyMapper.countByExample(ex);
	}
	
	public List<ExternalApply> getDealApproveList(int id, int beginIndex, int pageSize) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.setOrderByClause("id desc");
		ex.setBeginIndex(beginIndex);
		ex.setPageSize(pageSize);
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		ex.createCriteria().andStatusIn(list).andCurrentIdEqualTo(id);
		return this.eaApplyMapper.selectByExample(ex);
	}
	
	
	public List<ExternalDeal> selectByExample(ExternalDealExample ex) {
		return this.exDealMapper.selectByExample(ex);
	}
	
	public ExternalDeal selectExternalDealByApplyCode(String applyCode) {
		ExternalDealExample ex = new ExternalDealExample();
		ex.createCriteria().andApplyCodeEqualTo(applyCode);
		List<ExternalDeal> exDeals = this.exDealMapper.selectByExample(ex);
		return exDeals.isEmpty() ? null : exDeals.get(0);
	}
	
	public long insertExternalInboundBySelective(ExternalInbound ei) {
		return this.exInboundMapper.insertSelective(ei);
	}
	
	public List<ExternalInbound> selectExternalInboundByExample(ExternalInboundExample ex) {
		return this.exInboundMapper.selectByExample(ex);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void updateExternalApplyStatus(String applyCode, Integer id, String type,
			ExternalApplyDetail eaDetail) {
		// 判断是否完成入库
		ExternalInboundExample ex = new ExternalInboundExample();
		ex.createCriteria().andApplyCodeEqualTo(applyCode);
		List<ExternalInbound> eis = this.selectExternalInboundByExample(ex);
		Set<String> set = new HashSet<String>();
		for(ExternalInbound ei : eis) {
			set.add(ei.getType());
		}
		ExternalApply ea = this.selectExternalApplyById(id);
		String[] exTypes = ea.getExternalType().split(",");
		Set<String> exType = new HashSet<String>(Arrays.asList(exTypes));
		if(set.equals(exType)) {
			// 入库完成
			ExternalApply ea2  = new ExternalApply();
			ea2.setId(id);
			ea2.setStatus(11);
			this.eaApplyMapper.updateByPrimaryKeySelective(ea2);
			String table = MailTableUtil.createExternalInboundTable(ea, eaDetail, eis);
			User user = this.userMapper.findById(ea.getProposer());
			this.mailTableUtil.sendEndDealNotice(user, Constants.EXTERNAL_APPLY, table);
		}
	} 
	
	
	
}
