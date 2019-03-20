package com.hj.oa.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hj.commons.AjaxResultV2;
import com.hj.commons.Constants;
import com.hj.commons.MateriaInsertType;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveBean;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.ExternalApply;
import com.hj.oa.bean.ExternalApplyDetail;
import com.hj.oa.bean.ExternalApplyExample;
import com.hj.oa.bean.ExternalDeal;
import com.hj.oa.bean.ExternalDealExample;
import com.hj.oa.bean.ExternalInbound;
import com.hj.oa.bean.ExternalInboundExample;
import com.hj.oa.bean.ManageConfig;
import com.hj.oa.bean.MateriaMould;
import com.hj.oa.bean.Page;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.CommonService;
import com.hj.oa.service.ExternalService;
import com.hj.oa.service.UserService;
import com.hj.util.UploadUtilV2;

/**
 * @ClassName:  ExternalController   
 * @Description: 外协加工控制器
 * @author: wqfang
 * @date:   2018年11月7日 上午11:34:54   
 */
@Controller
@RequestMapping(value = "oa/external")
public class ExternalController extends BaseController{
	
	@Autowired
	private ExternalService exService;
	
	@Autowired
	private CommonService commService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String apply(Model model) {
		ManageConfig mc = this.commService.selectConfigById(1);
		ManageConfig mc2 = this.commService.selectConfigById(2);
		model.addAttribute("mc", mc.getContent());
		model.addAttribute("mc2", mc2.getContent());
		return getUrl("apply");
	}
	
	@RequestMapping(value = "/saveExternalApply", method = RequestMethod.POST)
	public String saveApply(ExternalApply ea, HttpServletRequest req, 
			HttpSession session, String type, Model model, String submitCode) {
		try {
			// 判断重复提交
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {
				throw new RuntimeException("请勿重复提交！");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			// 保存申请人信息
			User user = this.getLoginUser(session);
			ApproveStaff as = this.userService.findApproveIdByUserId(user.getId());
			String applyCode = this.commService.getApplyCode("WXJG", MateriaInsertType.EXTERNAL_PROCESSING.getType());
			ea = this.exService.saveApply(ea, user, as, applyCode, type);
			this.commService.updateSeq(applyCode, MateriaInsertType.EXTERNAL_PROCESSING.getType(), 4);
			ExternalApplyDetail eaDetail = this.exService.saveApplyDetail(type, req, applyCode, ea);
			this.exService.sendApplyMail(ea, eaDetail);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return getUrl("apply");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approveList", method = RequestMethod.GET)
	public String approveList(HttpSession session, Integer currentPage, Model model) {
		User user = this.getLoginUser(session);
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.setOrderByClause("id desc");
		List<Integer> status = new ArrayList<Integer>();
		status.add(1);
		status.add(2);
		status.add(3);
		status.add(4);
		ex.createCriteria().andCurrentIdEqualTo(user.getId()).andStatusIn(status);
		long totalSize = this.exService.countByExample(ex);
		if(totalSize != 0) {
			Page page = new Page(currentPage == null ? 1 : currentPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			ex.setBeginIndex(page.getBeginPageIndex());
			ex.setPageSize(page.getPageSize());
			List<ExternalApply> list = this.exService.selectByExample(ex);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("approveList");
	}
	
	@RequestMapping(value = "/detail/{type}", method = RequestMethod.GET)
	public String approveDetail(Integer id, Model model, @PathVariable String type) {
			// 申请人信息
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			model.addAttribute("ea", ea);
			String[] types = ea.getExternalType().split(",");
			model.addAttribute("types", types);
			ExternalInboundExample ex = new ExternalInboundExample();
			ex.createCriteria().andApplyCodeEqualTo(ea.getApplyCode());
			List<ExternalInbound> eis = this.exService.selectExternalInboundByExample(ex);
			for(ExternalInbound ei : eis) {
				model.addAttribute("ei" + ei.getType(), ei);
			}
			return getUrl(type + "Detail");
	}
	
	@RequestMapping(value = "/getContent", method = RequestMethod.GET)
	public String getContent(Integer id, Model model, HttpSession session) {
		try {
			User user = this.getLoginUser(session);
			// 申请人信息
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			if(ea == null) {
				throw new Exception("记录不存在！");
			}
			if(ea.getProposer() == user.getId()) {
				model.addAttribute("isProposer", 1);
			}
			// 申请详情
			ExternalApplyDetail eaDetail = this.exService.selectExternalApplyDetailByApplyCode(ea.getApplyCode());
			model.addAttribute("detail", eaDetail);
			model.addAttribute("ea", ea);
			String[] types = ea.getExternalType().split(",");
			model.addAttribute("types", types);
			if(StringUtils.isNotBlank(eaDetail.getIsUrgent())) {
				String[] isUrgents = eaDetail.getIsUrgent().split(",");
				model.addAttribute("isUrgents", isUrgents);
			}
			// 审批记录
			List<ApproveBean> approves = this.commService.getApproveByApplyCode(ea.getApplyCode());
			for (ApproveBean approve : approves) {
				model.addAttribute("approve" + approve.getApproveStatus(), approve);
			}
			// 处理详情
			ExternalDealExample ex = new ExternalDealExample();
			ex.createCriteria().andApplyCodeEqualTo(ea.getApplyCode());
			List<ExternalDeal> exDeal = this.exService.selectByExample(ex);
			if(!exDeal.isEmpty()) {
				model.addAttribute("deal", exDeal.get(0));
			} 
		}catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return getUrl("ajaxDetail");
	}
	
	
	@RequestMapping(value = "/handApprove", method = RequestMethod.POST)
	public String handApprove(String submitCode, HttpSession session,
			String sp, String opinion, Integer id, Model model) {
		try {
			// 判断重复提交
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {
				throw new RuntimeException("请勿重复提交！");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			User user = this.getLoginUser(session);
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			ExternalApplyDetail eaDetail = this.exService.selectExternalApplyDetailByApplyCode(ea.getApplyCode());
			int status = ea.getStatus();
			// 保存审批记录
			this.commService.saveApprove(opinion, sp, status, ea.getApplyCode(), user.getId());
			ApproveStaff as = this.userService.findApproveIdByUserId(ea.getProposer());
			// 更新审批人和状态
			this.exService.updateApproveStatus(sp, ea, as, eaDetail);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return getUrl("approveList");
		}
		return redirectUrl("approveList?msg=1");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/applyList", method = RequestMethod.GET)
	public String applyList(HttpSession session, Integer curPage, Model model) {
		User user = this.getLoginUser(session);
		long totalSize = this.exService.countApplyList(user.getId());
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<ExternalApply> list = this.exService.getApplyList(user.getId(), page);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("applyList");
	}

	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approveRecordList", method = RequestMethod.GET)
	public String approveRecordList(HttpSession session, Integer curPage, Model model) {
		User user = this.getLoginUser(session);
		long totalSize = this.exService.countApproveRecordList(user.getId());
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<ExternalApply> list = this.exService.getApproveRecordList(user.getId(), page, 1);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("approveRecordList");
	}
	
	private final static Integer APPROVE_PASS = 5;
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dealList", method = RequestMethod.GET)
	public String dealList(Model model, Integer curPage) {
		// 查询状态为5的外协加工申请
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andStatusEqualTo(APPROVE_PASS);
		long totalSize = this.exService.countByExample(ex);
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			ex.setBeginIndex(page.getBeginPageIndex());
			ex.setPageSize(page.getPageSize());
			ex.setOrderByClause("id desc");
			List<ExternalApply> list = this.exService.selectByExample(ex);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("dealList");
	} 
	
	
	@RequestMapping(value = "/handDeal", method = RequestMethod.POST)
	public String saveDeal(Integer id, HttpSession session,
			String submitCode, Model model, HttpServletRequest req) {
		try {
			// 判断重复提交
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {
				throw new RuntimeException("请勿重复提交！");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			ExternalApplyDetail eaDetail = this.exService.selectExternalApplyDetailByApplyCode(ea.getApplyCode());
			// 存储处理数据
			ExternalDeal eaDeal = this.exService.saveDeal(ea, req);
			// 更新申请表状态
			int count2 = this.exService.updateDealStatus(ea, eaDetail, eaDeal);
			if(count2 == 0) {
				throw new SQLException("审批状态更新失败！");
			}
			session.setAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
			e.printStackTrace();
			return getUrl("dealList");
		}
		return redirectUrl("dealList");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dealApproveList", method = RequestMethod.GET)
	public String dealApproveList(HttpSession session, Integer curPage, Model model) {
		User user = this.getLoginUser(session);
		long totalSize = this.exService.countDealApproveList(user.getId());
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<ExternalApply> eas = this.exService.getDealApproveList(user.getId(), page.getBeginPageIndex(), page.getPageSize());
			model.addAttribute("list", eas);
			model.addAttribute("page", page);
		}
		return getUrl("dealApproveList");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dealRecordList", method = RequestMethod.GET)
	public String dealRecordList(Model model, Integer curPage) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andStatusGreaterThan(APPROVE_PASS);
		long totalSize = this.exService.countByExample(ex);
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			ex.setBeginIndex(page.getBeginPageIndex());
			ex.setPageSize(page.getPageSize());
			List<ExternalApply> list = this.exService.selectByExample(ex);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("dealRecordList");
	} 
	
	
	
	@RequestMapping(value = "/handDealApprove", method = RequestMethod.POST)
	public String handDealApprove(String submitCode, HttpSession session,
			String sp, String opinion, Integer id, Model model) {
		try {
			// 判断重复提交
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {
				throw new RuntimeException("请勿重复提交！");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			User user = this.getLoginUser(session);
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			ExternalApplyDetail eaDetail = this.exService.selectExternalApplyDetailByApplyCode(ea.getApplyCode());
			ExternalDeal eaDeal = this.exService.selectExternalDealByApplyCode(ea.getApplyCode());
			int status = ea.getStatus();
			// 保存审批记录
			this.commService.saveApprove(opinion, sp, status, ea.getApplyCode(), user.getId());
			// 更新审批人和状态
			this.exService.updateApproveStatus2(sp, ea, eaDetail, eaDeal);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return redirectUrl("dealApproveList?msg=1");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dealApproveRecordList", method = RequestMethod.GET)
	public String dealApproveRecordList(HttpSession session, Integer curPage, Model model) {
		User user = this.getLoginUser(session);
		long totalSize = this.exService.countApproveRecordList(user.getId());
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<ExternalApply> list = this.exService.getApproveRecordList(user.getId(), page, 2);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		}
		return getUrl("dealApproveRecordList");
	}
	
	
	private final static Integer EXTERNAL_PASS = 10;
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inboundList", method = RequestMethod.GET)
	public String inbound(Model model, Integer curPage) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andStatusEqualTo(EXTERNAL_PASS);
		long totalSize = this.exService.countByExample(ex);
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			ex.setBeginIndex(page.getBeginPageIndex());
			ex.setPageSize(page.getPageSize());
			List<ExternalApply> eaApplys = this.exService.selectByExample(ex);
			model.addAttribute("list", eaApplys);
			model.addAttribute("page", page);
		}
		return getUrl("inboundList");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/handInbound", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultV2 handInbound(String type, Integer inboundNum, String inboundDate,
			String applyCode, Integer id) {
		AjaxResultV2 res = new AjaxResultV2();
		try {
			ExternalApply ea = this.exService.selectExternalApplyById(id);
			ExternalInboundExample ex = new ExternalInboundExample();
			ex.createCriteria().andApplyCodeEqualTo(ea.getApplyCode());
			ExternalApplyDetail eaDetail = this.exService.selectExternalApplyDetailByApplyCode(ea.getApplyCode());
			ExternalInbound ei = new ExternalInbound();
			ei.setApplyCode(applyCode);
			ei.setInboundNum(inboundNum);
			ei.setInboundDate(inboundDate);
			ei.setType(type);
			long rt = this.exService.insertExternalInboundBySelective(ei);
			// 发邮件给申请人
			if(rt < 1) {
				throw new RuntimeException("数据存储失败！");
			}
			this.exService.updateExternalApplyStatus(applyCode, id, type, eaDetail);
		} catch (Exception e) {
			res.setSuccess(AjaxResultV2.FAIL);
			res.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inboundRecordList", method = RequestMethod.GET)
	public String inboundRecordList(Model model, Integer curPage) {
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andStatusEqualTo(11);
		long totalSize = this.exService.countByExample(ex);
		if(totalSize != 0) {
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			ex.setBeginIndex(page.getBeginPageIndex());
			ex.setPageSize(page.getPageSize());
			List<ExternalApply> eaApplys = this.exService.selectByExample(ex);
			model.addAttribute("list", eaApplys);
			model.addAttribute("page", page);
		}
		return getUrl("inboundRecordList");
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manage(Model model, HttpSession session) {
		ManageConfig mc = this.commService.selectConfigById(1);
		ManageConfig mc2 = this.commService.selectConfigById(2);
		ManageConfig mc3 = this.commService.selectConfigById(3);
		ManageConfig mc4 = this.commService.selectConfigById(4);
		model.addAttribute("mc", mc.getContent());
		model.addAttribute("mc2", mc2.getContent());
		model.addAttribute("mc3", mc3.getContent());
		model.addAttribute("mc4", mc4.getContent());
		List<Role> roles = this.getLoginUserRole(session);
		for(Role role : roles) {
			if(role.containsRole(Constants.EXTERNAL_TREASURER1)) {
				model.addAttribute("caiwu", 1);
				break;
			}
			if(role.containsRole(Constants.EXTERNAL_MANAGE)) {
				model.addAttribute("shenchanyuan", 1);
				break;
			}
		}
		
		return getUrl("manage");
	}
	
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	public String manage(Integer fileId, HttpServletRequest req, Model model) {
		try {
			String content = "";
			if(fileId < 3) {
				MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
				MultipartFile file = mreq.getFile("file");
				content = UploadUtilV2.uploadFileByType(file, req, 5, UploadUtilV2.IMGFILE);
			} else {
				content = req.getParameter("cost");
			}
			ManageConfig mc = new ManageConfig();
			mc.setId(fileId);
			mc.setContent(content);
			this.commService.updateManageConfig(mc);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return getUrl("manage");
		}
		return redirectUrl("manage");
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest req, HttpServletResponse res) {
		try {
			String path = req.getParameter("path");
			path = path.replace("+", "%2B");
			path = URLDecoder.decode(path, "UTF-8");
			String realPath = req.getSession().getServletContext().getRealPath("\\");
			UploadUtilV2.downloadLocal(realPath + path, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*************************************************************基础********************************************************/
	
	
	private String getUrl(String str) {
		return "oa/external/" + str;
	}
	
	private String redirectUrl(String str) {
		return "redirect:/web/oa/external/" + str;
	}
	
	@RequestMapping(value = "oa/materia_v2/uploadFile", method = RequestMethod.GET)
	public String testUpload(Model model) {
		List<MateriaMould> list = this.commService.selectAllMould();
		model.addAttribute("list", list);
		return getUrl("uploadFile");
	}
	
	@RequestMapping(value = "oa/materia_v2/uploadFile", method = RequestMethod.POST)
	public String testUpload2(@RequestParam MultipartFile file, HttpServletRequest req) {
		try {
			String path = UploadUtilV2.uploadFile2(file, req, 5, UploadUtilV2.ALL);
			MateriaMould mm = new MateriaMould();
			mm.setType("file");
			mm.setUrl(path);
			this.commService.updateMateriaMould(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectUrl("uploadFile");
	}
	
	@RequestMapping(value = "/getApplyCode", method = RequestMethod.GET)
	@ResponseBody
	public String getApplyCode(String str, Integer type) {
		return this.commService.getApplyCode(str, type);
	}
	
	
}
