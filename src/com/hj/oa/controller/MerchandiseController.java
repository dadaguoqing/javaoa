package com.hj.oa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.commons.AjaxResultV2;
import com.hj.commons.ApplyCode;
import com.hj.commons.ApplyType;
import com.hj.commons.DbStatus;
import com.hj.commons.MerchandiseConstants;
import com.hj.oa.Consts;
import com.hj.oa.bean.MateriaApprove;
import com.hj.oa.bean.MerchandiseApply;
import com.hj.oa.bean.MerchandiseApplyDetail;
import com.hj.oa.bean.MerchandiseDetailRecord;
import com.hj.oa.bean.MerchandiseManage;
import com.hj.oa.bean.MerchandiseSupplier;
import com.hj.oa.bean.Page;
import com.hj.oa.bean.Product;
import com.hj.oa.bean.User;
import com.hj.oa.service.MateriaService_v2;
import com.hj.oa.service.MerchandiseService;
import com.hj.oa.service.UserService;
import com.hj.oa.service.inter.ProductService;

/**   
 * @ClassName:  MerchandiseController   
 * @Description:产品库存管理控制器
 * @author: wqfang
 * @date:   2018年8月31日 上午10:32:05   
 *   
 */ 
@Controller
@RequestMapping(value="oa/merchandise")
public class MerchandiseController extends BaseController{
	
	private final static String CUR_URL = "oa/merchandise/";
	private final static String HOME_PAGE = "redirect:/web/oa/index";
	
	Logger log = Logger.getLogger(MerchandiseController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	MerchandiseService msService;
	@Autowired
	MateriaService_v2 maService;
	@Autowired
	ProductService productService;
	
	/**
	 * @Title: supplierManage   
	 * @Description: 通用
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="{type}/{pageName}", method=RequestMethod.GET)
	public String supplierManage(@PathVariable("type") String type, @PathVariable("pageName") String pageName) {
		return CUR_URL + type + "/" + pageName;
	}
	
	/**
	 * @Title: supplierManage
	 * @Description: 供应商管理界面
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping(value="manage/supplier", method=RequestMethod.GET)
	public String supplierManage(Model model) {
		List<MerchandiseSupplier> ms;
		try {
			ms = this.msService.getSuppliers();
			model.addAttribute("list", ms);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return "oa/merchandise/manage/supplier";
	}
	
	/**
	 * 
	 * @Title: addSupplie   
	 * @Description: 添加供应商
	 * @param: @param model
	 * @param: @param ms
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="manage/addSupplier", method=RequestMethod.POST)
	public String addSupplie(Model model,MerchandiseSupplier ms) {
		try {
			this.msService.addSupplier(ms);
		} catch (Exception e) {
			model.addAttribute("msg", "添加供应商信息出错" + e.getMessage());
			e.printStackTrace();
			return "oa/merchandise/manage/supplier";
		}
		return "redirect:/web/oa/merchandise/manage/supplier?msg=1";
	}
	
	/**
	 * @Title: updateSupplier   
	 * @Description: 修改供应商
	 * @param: @param mode
	 * @param: @param id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="manage/updateSupplier", method=RequestMethod.GET)
	public String updateSupplier(Model model,Integer id) {
		try {
			MerchandiseSupplier ms = this.msService.findSupplierById(id);
			model.addAttribute("ms", ms);
		} catch (Exception e) {
			model.addAttribute("msg", "查询供应商信息出错:" + e.getMessage());
			e.printStackTrace();
			return "oa/merchandise/manage/supplier";
		}
		return "oa/merchandise/manage/updateSupplier";
	}

	@RequestMapping(value="manage/updateSupplier", method=RequestMethod.POST)
	public String updateSupplier(Model model,MerchandiseSupplier ms) {
		try {
			 this.msService.updateSupplierBySelective(ms);
		} catch (Exception e) {
			model.addAttribute("msg", "修改供应商信息出错:" + e.getMessage());
			e.printStackTrace();
			return "oa/merchandise/manage/supplier";
		}
		return "redirect:/web/oa/merchandise/manage/supplier?msg=1";
	}
	
	@RequestMapping(value="manage/deleteSupplier", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResultV2<String> deleteSupplier(Integer id) {
		AjaxResultV2<String> result = new AjaxResultV2<String>();
		result.setMsg(Consts.SUCCESS);
		MerchandiseSupplier ms = new MerchandiseSupplier();
		ms.setId(id);
		ms.setStatus(DbStatus.FREEZE.getType());
		try {
			this.msService.updateSupplierBySelective(ms);
		} catch (Exception e) {
			result.setSuccess(AjaxResultV2.FAIL);
			result.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Title: getSupplier   
	 * @Description: 修改页面获取供应商信息
	 * @param: @param id
	 * @param: @return      
	 * @return: AjaxResult<MerchandiseSupplier>      
	 * @throws
	 */
	@RequestMapping(value="manage/getSupplier", method=RequestMethod.GET)
	@ResponseBody
	public AjaxResultV2<MerchandiseSupplier> getSupplier(Integer id) {
		AjaxResultV2<MerchandiseSupplier> result = new AjaxResultV2<MerchandiseSupplier>();
		try {
			MerchandiseSupplier ms = this.msService.findSupplierById(id);
			result.setData(ms);
		} catch (Exception e) {
			result.setSuccess(AjaxResultV2.FAIL);
			result.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @Title: reason   
	 * @Description: 出入库管理界面
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "manage/stockReason", method = RequestMethod.GET)
	public String reason(Model model) {
		try {
			List<MerchandiseManage> mm1 = this.msService.findManageByType(MerchandiseConstants.OUTWARE_REASON);
			List<MerchandiseManage> mm2 = this.msService.findManageByType(MerchandiseConstants.INWARE_REASON);
			model.addAttribute("list1", mm1);
			model.addAttribute("list2", mm2);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		return "oa/merchandise/manage/stockReason";
	}
	
	/**
	 * 
	 * @Title: addReason   
	 * @Description: 增加出入库原因
	 * @param: @param mm
	 * @param: @param insertType
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "manage/addReason", method = RequestMethod.POST)
	public String addReason(MerchandiseManage mm,Integer insertType,Model model) {
		try {
			mm.setType(insertType == 1 ? MerchandiseConstants.OUTWARE_REASON : MerchandiseConstants.INWARE_REASON);
			this.msService.insertReason(mm);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/merchandise/manage/stockReason";
		}
		return "redirect:/web/oa/merchandise/manage/stockReason?msg=1";
	}
	
	/**
	 * @Title: deleteReason   
	 * @Description: 删除出入库原因
	 * @param: @param id
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "manage/deleteReason", method = RequestMethod.POST)
	public String deleteReason(Integer id,Model model) {
		try {
			MerchandiseManage mm = new MerchandiseManage();
			mm.setId(id);
			mm.setStatus(DbStatus.FREEZE.getType());
			this.msService.updateReason(mm);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/merchandise/manage/stockReason";
		}
		return "redirect:/web/oa/merchandise/manage/stockReason?msg=1";
	}
	
	
	/**
	 * TODO 页面还没做
	 * @Title: searchMerchandise   
	 * @Description: 库存查询 
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String searchMerchandise() {
		return "oa/merchandise/search";
	}
	
	/**
	 * @Title: getApplyCode   
	 * @Description: 获取申请编号
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/getApplyCode", method = RequestMethod.GET)
	@ResponseBody
	public String getApplyCode(@RequestParam String str,@RequestParam Integer id) {
		return this.maService.getPurchaseCode(str, id);
	}
	
	
	/**
	 * @Title: getProductCode   
	 * @Description: 获取产品型号
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/getProductCode", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getProductCode() {
		List<Product> products = new ArrayList<Product>();
		try {
			products = this.msService.getAllProducts();
			for(Product product : products) {
				String remark = product.getRemark().replaceAll("[\\t\\n\\r]", "");
				product.setRemark(remark);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	/**
	 * @Title: getProductCode   
	 * @Description: 获取供应商
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/getSuppliers", method = RequestMethod.GET)
	@ResponseBody
	public List<MerchandiseSupplier> getSuppliers() {
		List<MerchandiseSupplier> suppliers = new ArrayList<MerchandiseSupplier>();
		try {
			suppliers = this.msService.getSuppliers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suppliers;
	}
	
	
	/**
	 * @Title: getInboundReasons   
	 * @Description: 获取出入库原因
	 * @param: @return      
	 * @return: List<MerchandiseSupplier>
	 * @throws
	 */
	@RequestMapping(value = "apply/getInboundReasons", method = RequestMethod.GET)
	@ResponseBody
	public List<MerchandiseManage> getInboundReasons(@RequestParam Integer type) {
		String str = "";
		if(1 == type) {
			str = "入库原因";
		} else {
			str = "出库原因";
		}
		List<MerchandiseManage> reasons = new ArrayList<MerchandiseManage>();
		try {
			reasons = this.msService.findManageByType(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reasons;
	}
	
	/**
	 * @Title: merInBound   
	 * @Description: 保存成品入库申请信息
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/saveMerInbound", method = RequestMethod.POST)
	public String merInBound(HttpServletRequest req,
			String startDate2,HttpSession session,Model model) {
		try {
			User user = this.getLoginUser(session);
			// 获取申请编号
			String applyCode = this.maService.
					getPurchaseCode(ApplyCode.MERCHANDISE_INBOUND.getCode(), ApplyCode.MERCHANDISE_INBOUND.getId());
			// 插入申请人信息
			this.msService.insertMerApply(user, applyCode, startDate2,ApplyType.MERCHANDISE_INBOUND.getType());
			// 申请详情
			this.msService.insertMerApplyDetail(
					applyCode ,req, ApplyType.MERCHANDISE_INBOUND.getType());
			// 申请编号增加
			this.msService.updateSeq(applyCode, ApplyCode.MERCHANDISE_INBOUND.getId(),4);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/merchandise/apply/merInbound";
	}
	
	/**
	 * @Title: merInBound   
	 * @Description: 保存成品出库申请信息
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/saveMerOutbound", method = RequestMethod.POST)
	public String merOutBound(HttpServletRequest req,
			String startDate2,HttpSession session,Model model) {
		try {
			User user = this.getLoginUser(session);
			// 获取申请编号
			String applyCode = this.maService.
					getPurchaseCode(ApplyCode.MERCHANDISE_OUTBOUND.getCode(), ApplyCode.MERCHANDISE_OUTBOUND.getId());
			// 插入申请人信息
			this.msService.insertMerApply(user, applyCode, startDate2,ApplyType.MERCHANDISE_OUTBOUND.getType());
			// 申请详情
			this.msService.insertMerApplyDetail(
					applyCode ,req, ApplyType.MERCHANDISE_OUTBOUND.getType());
			// 申请编号增加
			this.msService.updateSeq(applyCode, ApplyCode.MERCHANDISE_OUTBOUND.getId(),4);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/merchandise/apply/merOutbound";
	}
	
	/**
	 * @Title: halfMerInBound   
	 * @Description: 半成品入库
	 * @param: @param req
	 * @param: @param startDate2
	 * @param: @param session
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/saveHalfMerInbound", method = RequestMethod.POST)
	public String halfMerInBound(HttpServletRequest req,
			String startDate2,HttpSession session,Model model) {
		try {
			User user = this.getLoginUser(session);
			// 获取申请编号
			String applyCode = this.maService.
					getPurchaseCode(ApplyCode.HALF_MERCHANDISE_INBOUND.getCode(),
							ApplyCode.HALF_MERCHANDISE_INBOUND.getId());
			// 插入申请人信息
			this.msService.insertMerApply(user, applyCode, startDate2, ApplyType.HALF_MERCHANDISE_INBOUND.getType());
			// 申请详情
			this.msService.insertMerApplyDetail(
					applyCode , req, ApplyType.HALF_MERCHANDISE_INBOUND.getType());
			// 申请编号增加
			this.msService.updateSeq(applyCode, ApplyCode.HALF_MERCHANDISE_INBOUND.getId(), 5);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/merchandise/apply/halfMerInbound";
	}
	
	
	/**
	 * @Title: rejectInBound   
	 * @Description: 不良品入库申请
	 * @param: @param req
	 * @param: @param session
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/saveRejectInbound", method = RequestMethod.POST)
	public String rejectInBound(HttpServletRequest req, HttpSession session,Model model) {
		try {
			User user = this.getLoginUser(session);
			// 获取申请编号
			String applyCode = this.maService.
					getPurchaseCode(ApplyCode.REJECTS_INBOUND.getCode(), ApplyCode.REJECTS_INBOUND.getId());
			// 插入申请人信息
			this.msService.insertMerApply(user, applyCode, null, ApplyType.REJECTS_INBOUND.getType());
			// 申请详情
			this.msService.insertMerApplyDetail(
					applyCode, req, ApplyType.REJECTS_INBOUND.getType());
			// 申请编号增加
			this.msService.updateSeq(applyCode, ApplyCode.REJECTS_INBOUND.getId(), 5);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/merchandise/apply/rejectInbound";
	}
	
	
	
	/**
	 * @Title: merInboundList   
	 * @Description: 记录列表
	 * @param: @param type 1 我的申请, 2 审批, 3 审批记录  
	 * @param: @param applyType 1 入库申请
	 * @param: @param session
	 * @param: @return      
	 * 
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/recordList/{applyType}/{type}", method = RequestMethod.GET)
	public String merInboundList(@PathVariable Integer type, 
			@PathVariable Integer applyType, HttpSession session,Model model,Integer curPage) {
		User user = this.getLoginUser(session);
		List<MerchandiseApply> msApply = new ArrayList<MerchandiseApply>();
		Page<MerchandiseApply> page = null;
		long totalSize = 0;
		totalSize = this.msService.findAllRecord(user.getId(), applyType, type);
		curPage = null == curPage ? 1 : curPage;
		page = new Page<MerchandiseApply>(curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
		msApply = this.msService.findMyApplyRecord(user.getId(),
				applyType, page.getBeginPageIndex(), page.getPageSize(), type);
		// 查询列表分页
		model.addAttribute("list", msApply);
		model.addAttribute("type", type);
		model.addAttribute("applyType", applyType);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageCount", page.getPageCount());
		switch (applyType) {
			case 1 :
				// 成品入库申请
				return CUR_URL + "apply/merInboundList";
			case 2 :
				// 成品出库申请
				return CUR_URL + "apply/merOutboundList";
			case 3 :
				// 半成品入库申请
				return CUR_URL + "apply/halfMerInboundList";
			case 5 :
				// 不良品入库申请
				return CUR_URL + "apply/rejectInboundList";
			default :
				return HOME_PAGE;
			}
	}
	
	
	/**
	 * @Title: recordDetail   
	 * @Description: 记录详情
	 * @param: @param applyType
	 * @param: @param type
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/recordDetail/{applyType}/{type}", method = RequestMethod.GET)
	public String recordDetail(@PathVariable Integer applyType,@PathVariable Integer type,Integer id, Model model) {
		// 查询申请信息
		MerchandiseApply msApply = this.msService.findApplyById(id);
		// 查询申请详情
		List<MerchandiseApplyDetail> msApplyDetails = 
				this.msService.findApplyDetailByApplyCode(msApply.getApplyCode());
		// 查询审批信息
		List<MateriaApprove> appApproves = 
				this.maService.selectMateriaApproveByPrimaryKey(msApply.getApplyCode());
		for (MateriaApprove ma : appApproves) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		model.addAttribute("app", msApply);
		model.addAttribute("list", msApplyDetails);
		model.addAttribute("type", type);
		model.addAttribute("applyType", applyType);
		switch (applyType) {
		// 入库申请
		case 1 :
			return CUR_URL + "apply/merInboundDetail";
		case 2 :
			return CUR_URL + "apply/merOutboundDetail";
		case 3 :
			return CUR_URL + "apply/halfMerInboundDetail";
		case 5 :
			return CUR_URL + "apply/rejectInboundDetail";
		default:
			return HOME_PAGE;
		}
	}
	
	/**
	 * @Title: applyApprove   
	 * @Description: 审批
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "apply/approve/{applyType}/{type}", method = RequestMethod.POST)
	public String applyApprove(@PathVariable Integer type, @PathVariable Integer applyType, String sp, String opinion,Integer id,Model model,HttpSession session) {
		User user = this.getLoginUser(session);
		try {
			this.msService.approveApply(sp,opinion,id,user);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
//			return "oa/merchandise/apply/rejectInboundList";
		}
		return "redirect:/web/oa/merchandise/apply/recordList/" + applyType +"/" + type + "?msg=1";
	}
	
	/**
	 * 
	 * @Title: inboundHandle   
	 * @Description: 入库处理
	 * @param: @param type 
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "handle/{type}/{type2}", method = RequestMethod.GET)
	public String inboundHandle(@PathVariable Integer type, Model model,
			Integer curPage, @PathVariable Integer type2) {
		long totalSize = this.msService.findTotalHandleByType(type, type2);
		@SuppressWarnings("rawtypes")
		Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
		List<MerchandiseApply> meApply = this.msService.findHandleByType(type, page, type2);
		model.addAttribute("list", meApply);
		model.addAttribute("page", page);
		switch(type) {
			// 成品入库处理
			case 1 : 
				if(type2 == 1) 
					return CUR_URL + "handle/merInbound";
				else 
					return CUR_URL + "handle/merInboundRecord";
			// 半成品入库处理
			case 3 : 
				if(type2 == 1) 
					return CUR_URL + "handle/halfMerInbound";
				else 
					return CUR_URL + "handle/halfMerInboundRecord";
			// 不良品入库处理
			case 5 : 
				if(type2 == 1) 
					return CUR_URL + "handle/rejectMerInbound";
				else 
					return CUR_URL + "handle/rejectMerInboundRecord";
			// 错误页面，返回到首页
			default : 
				return HOME_PAGE;
		}
	}
	
	/**
	 * @Title: applyDetail   
	 * @Description: 成品处理详情
	 * @param: @param id
	 * @param: @param type
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "handle/{pageName}/detail/{type2}", method = RequestMethod.GET)
	public String applyDetail(Integer id, Integer type, Model model, @PathVariable Integer type2,
			@PathVariable String pageName) {
		// 查询申请信息
		MerchandiseApply msApply = this.msService.findApplyById(id);
		// 查询申请详情
		List<MerchandiseApplyDetail> msApplyDetails = 
					this.msService.findApplyDetailByApplyCode(msApply.getApplyCode());
		// 查询入库信息
		List<MerchandiseDetailRecord> mdrs = this.msService.findDetailRecordByApplyCode(msApply.getApplyCode());
		// 查询审批信息
		List<MateriaApprove> appApproves = 
					this.maService.selectMateriaApproveByPrimaryKey(msApply.getApplyCode());
		for (MateriaApprove ma : appApproves) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		model.addAttribute("app", msApply);
		model.addAttribute("type", type);
		model.addAttribute("type2", type2);
		model.addAttribute("list", msApplyDetails);
		model.addAttribute("detail", mdrs);
		return CUR_URL + "handle/" + pageName + "Detail";
	}
	
	
	/**
	 * @Title: toInware   
	 * @Description: 入库处理
	 * @param: @param id
	 * @param: @param req
	 * @param: @param sp
	 * @param: @return      
	 * @return: AjaxResultV2<T>      
	 * @throws
	 */
	@RequestMapping(value = "handle/toInware", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultV2<T> toInware(@RequestParam Integer id, HttpServletRequest req,
			@RequestParam String sp) {
		AjaxResultV2<T> res = new AjaxResultV2<T>();
		try {
			if("通过".equals(sp)) {
				String[] proModels = req.getParameterValues("productModel");
				String[] quantitys = req.getParameterValues("quantity");
				String[] productIds = req.getParameterValues("productId");
				this.msService.toInbound(id, proModels, quantitys, productIds);
			} else {
				// 更新申请状态 -1 不通过
				MerchandiseApply msApply = new MerchandiseApply();
				msApply.setId(id);
				msApply.setStatus(DbStatus.FREEZE.getType());
				this.msService.updateApplyBySelective(msApply);
			}
		} catch (Exception e) {
			res.setSuccess(AjaxResultV2.FAIL);
			res.setMsg(StringUtils.isBlank(e.getMessage()) ? "系统出错" : e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	
}
