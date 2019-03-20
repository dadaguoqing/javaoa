package com.hj.oa.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hj.commons.AjaxResult;
import com.hj.commons.AjaxResultV2;
import com.hj.commons.Constants;
import com.hj.commons.DbStatus;
import com.hj.commons.MateriaConstants;
import com.hj.commons.MateriaInsertType;
import com.hj.commons.ResultBean;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaApprove;
import com.hj.oa.bean.MateriaDept;
import com.hj.oa.bean.MateriaMould;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.MateriaPurchaseDetail2;
import com.hj.oa.bean.MateriaRecord;
import com.hj.oa.bean.MateriaWarehouse;
import com.hj.oa.bean.MateriaWarehouseExample;
import com.hj.oa.bean.Mould;
import com.hj.oa.bean.Page;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.controller.BaseController;
import com.hj.oa.service.MateriaService_v2;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.ReadExcelUtils;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;
import com.hj.util.UploadUtil;
import com.hj.util.UploadUtilV2;

/**
 * 物料管理V2.0
 * 
 * @author wqfang
 *
 *         2018年5月29日
 */
@Controller
public class MateriaController_v2 extends BaseController {
	@Autowired
	MateriaService_v2 maService;
	@Autowired
	UserService userService;

	/**
	 * =============================================物料管理=========================================
	 */
	/**
	 * 物料添加
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/addmt")
	public String addMateria(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		// 模板下载地址
		String type = Consts.ENTER;
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		// 查询所管理的仓库
		List<MateriaDept> warehouses = this.maService.findWarehousesByAdminId(loginUser.getId());
		if (warehouses.isEmpty()) {
			model.addAttribute("notAccess", "没有入库权限");
		}
		model.addAttribute("warehouses", warehouses);
		return "oa/materia_v2/addmt";
	}

	/**
	 * 物料录入功能
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/addMateria")
	public String addMateria(String submitCode, Model model, HttpSession session, @RequestParam MultipartFile file,
			HttpServletRequest request, Integer receivedType, Integer warehouseId) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "oa/materia_v2/addmt";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		User loginUser = this.getLoginUser(session);
		try {
			Map<String, String> map = UploadUtil.uploadExcel(file, request);
			String timeCode = System.currentTimeMillis() + "";
			ResultBean<String> result = this.maService.addMateria(map, receivedType, warehouseId, loginUser, timeCode);
			int code = result.getCode();
			if (code == ResultBean.SUCCESS) {
				this.maService.addMateriaDetail(loginUser.getId(), warehouseId, timeCode, 1);
				model.addAttribute("msg", Consts.SUCCESS);
			} else {
				model.addAttribute("msg", result.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", Consts.ERROR_EXCEL);
		}
		// 上传文件
		return "oa/materia_v2/addmt";
	}

	/**
	 * 物料审批列表
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/materia_v2/maApplyList2")
	public String approveList2(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		List<MateriaApply> list = this.maService.findApproveById(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/materia_v2/maApplyList2";
	}

	/**
	 * 审批详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applyDetail")
	public String applyDetail(Integer id, Model model, HttpSession session) {
		MateriaApply ma = this.maService.findApplyById(id);
		List<Materia> list = this.maService.findMtByCode(ma.getCode());
		MateriaDept md = this.maService.findWarehouseById(Integer.parseInt(ma.getWarehouse()));
		ma.setWarehouse(md.getWarehouse());
		model.addAttribute("ma", ma);
		model.addAttribute("list", list);
		Integer isManager = isStroreWareManager(session) ? 1 : 0;
		model.addAttribute("isManager", isManager);
		// // 计算预计总价
		// double cost = this.maService.countCost(list, whId);
		// model.addAttribute("cost", cost);
		return "oa/materia_v2/applyDetail2";
	}

	/**
	 * 查看申请处理记录列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myRecord")
	public String myRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<MateriaApply> list = this.maService.findMySpRecord(loginUser.getId());
		List<Integer> empIds = this.maService.findMySpIds(loginUser.getId());
		model.addAttribute("list", list);
		model.addAttribute("empIds", empIds);
		return "oa/materia_v2/myApplyRecord3";
	}

	/**
	 * 查看处理记录详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applyRecordDetail3")
	public String applyRecordDetail3(Integer id, Model model, HttpSession session) {
		MateriaApply ma = this.maService.findApplyById(id);
		List<Materia> list = this.maService.findMtByCode(ma.getCode());
		model.addAttribute("ma", ma);
		model.addAttribute("list", list);
		return "oa/materia_v2/applyRecord4";
	}

	/**
	 * =============================================物料查询========================
	 */

	/**
	 * 物料库存查询
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/querystock")
	public String queryStock() {
		return "oa/materia_v2/querystock";
	}

	/**
	 * 物料消耗查询
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/queryCost")
	public String queryCost(Model model) {
		List<User> users = this.userService.findUserByRole(MateriaConstants.RESEARCH_STAFF);
		model.addAttribute("users", users);
		List<MateriaDept> warehouses = this.maService.findAllWarehouse();
		model.addAttribute("warehouses", warehouses);
		return "oa/materia_v2/queryCost";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("oa/materia_v2/queryCostAjax")
	@ResponseBody
	public Page<MateriaRecord> queryCostAjax(Model model, @RequestParam String warehouseId,
			@RequestParam String startTime, @RequestParam String endTime, @RequestParam Integer userId,
			@RequestParam Integer type, @RequestParam String materiaCode, @RequestParam Integer status,
			@RequestParam Integer curPage) {
		MateriaRecord mr = new MateriaRecord();
		mr.setWarehouse(warehouseId);
		mr.setDayStr1(startTime);
		mr.setDayStr2(endTime);
		mr.setType(type);
		mr.setMateriaCode(materiaCode);
		mr.setStatus(status);
		mr.setPersonId(userId);
		Integer totalNum = this.maService.findtTotalCost(mr);
		Page<MateriaRecord> page = new Page<MateriaRecord>(curPage, Page.PAGE_SIZE_TEN, totalNum);
		Map map = new HashMap();
		map.put("warehouseId", warehouseId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("materiaCode", materiaCode);
		map.put("userId", userId);
		map.put("beginIndex", page.getBeginPageIndex());
		map.put("pageSize", page.getPageSize());
		List<MateriaRecord> list = this.maService.findAllCost(map);
		for (MateriaRecord li : list) {
			User user = this.userService.findById(li.getEmpId());
			li.setPersonName(user.getName());
		}
		page.setList(list);
		return page;
	}

	/**
	 * 物料入库查询
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/queryIn")
	public String queryIn(Model model) {
		List<MateriaDept> warehouses = this.maService.findAllWarehouse();
		model.addAttribute("warehouses", warehouses);
		return "oa/materia_v2/queryIn";
	}

	/**
	 * 物料出入库查询
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("oa/materia_v2/queryInAjax")
	@ResponseBody
	public Page<MateriaRecord> queryInAjax(Model model, @RequestParam String warehouseId,
			@RequestParam Integer receivedType, @RequestParam String startTime, @RequestParam String endTime,
			@RequestParam Integer type, @RequestParam String materiaCode, Integer curPage) {
			MateriaRecord mr = new MateriaRecord();
			mr.setWarehouse(warehouseId);
			mr.setReceivedType(receivedType);
			mr.setDayStr1(startTime);
			mr.setDayStr2(endTime);
			mr.setType(type);
			mr.setMateriaCode(materiaCode);
			curPage = curPage == null ? 1 : curPage;
			Integer totalNum = this.maService.findtotalInware(mr);
			Page<MateriaRecord> page = new Page<MateriaRecord>(curPage, Page.PAGE_SIZE_TEN, totalNum);
			@SuppressWarnings("rawtypes")
			Map map = new HashMap();
			map.put("warehouse", warehouseId);
			map.put("dayStr1", startTime);
			map.put("dayStr2", endTime);
			map.put("materiaCode", materiaCode);
			map.put("beginIndex", page.getBeginPageIndex());
			map.put("pageSize",page.getPageSize());
			List<MateriaRecord> list = this.maService.findAllInware(map);
			for (MateriaRecord li : list) {
				User user = this.userService.findById(li.getEmpId());
				li.setPersonName(user.getName());
			}
			page.setList(list);
		return page;
	}

	/**
	 * 物料库存查询功能
	 * 
	 * @returns
	 */
	@RequestMapping("oa/materia_v2/query")
	public String queryMateria(Model model, HttpSession session) {
		Integer manager = isStroreWareManager(session) ? 1 : 0;
		model.addAttribute("manager", manager);
		return "oa/materia_v2/query";

	}

	/**
	 * 判断登录用户是不是仓库管理员
	 * 
	 * @param roles
	 * @return
	 */
	public boolean isStroreWareManager(HttpSession session) {
		List<Role> roles = this.getLoginUserRole(session);
		boolean flag = false;
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				if (Consts.STOREWARE_MANAGER.equals(role.getName()))
					return true;
			}
		}
		return flag;
	}

	/**
	 * 物料库存查询功能
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/queryAjax")
	public String queryAjax(String materiaCode, String spec, String all,
			Integer curPage, Model model, HttpSession session) {
		if(StringUtils.isNotBlank(session.getAttribute("queryMsg") + "")) {
			session.removeAttribute("queryMsg");
		}
		Materia mm = new Materia();
		mm.setMateriaCode(materiaCode);
		mm.setSpec(spec);
		mm.setOthers(all);
		int totalPage = this.maService.findStockLength(mm);
		curPage = curPage == null ? 1 : curPage;
		// 分页查询
		// 当前页、每页显示15条
		Page<Materia> page = new Page<Materia>(curPage, Page.PAGE_SIZE_TEN, totalPage);
		List<Materia> lists = this.maService.findByCurPage(page.getBeginPageIndex(), page.getPageSize(), materiaCode,
				spec, all);
		for(Materia ma : lists) {
			Double cacheStock = this.maService.findCacheMateriaById(ma.getId(), Integer.parseInt(ma.getWarehouseId()));
			ma.setStock(cacheStock);
		}
		page.setList(lists);
		if(StringUtils.isNotBlank(spec)) {
			model.addAttribute("spec", spec);
		}
		if(StringUtils.isNotBlank(all)) {
			model.addAttribute("all", all);
		}
		if(StringUtils.isNotBlank(materiaCode)) {
			model.addAttribute("materiaCode", materiaCode);
		}
		model.addAttribute("page", page);
		return "oa/materia_v2/query";
	}

	/**
	 * 查看物料详情
	 * 
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/materia_v2/queryMore")
	public String queryMore(Model model, Integer id, HttpSession session, String whId) {
		if (!isStroreWareManager(session)) {
			model.addAttribute("xg", 0);
		}
		Materia list = this.maService.findMateriaInfoById(id, whId);
		Double price = this.maService.findMateriaPrice(id);
		model.addAttribute("list", list);
		model.addAttribute("price", price);
		model.addAttribute("whId", whId);
		return "oa/materia_v2/queryMore";
	}

	@RequestMapping("oa/materia_v2/queryMore2")
	public String queryMore2(Model model, Integer id, HttpSession session) {
		Materia list = this.maService.findMateriaInfoById2(id);
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		return "oa/materia_v2/queryMore2";
	}

	@RequestMapping("oa/materia_v2/updateMateriaInfo")
	public String updateinfo(Model model, Materia ma, Integer id, Integer whId,
			HttpSession session, @RequestParam MultipartFile file, HttpServletRequest req) {
		try {
			Integer maId = this.maService.findIdBymateriaCode2(ma.getMateriaCode());
			if (maId == null || maId == ma.getId()) {
				if(!file.isEmpty()) {
					String urlStr = UploadUtilV2.uploadFile(file, req, 5, UploadUtilV2.PDFFILE);
					String url = UploadUtilV2.getFileName(urlStr);
					ma.setUrl(url);
				}
				this.maService.updateMateriaInfo(ma);
				session.setAttribute("queryMsg", "修改成功！");
			} else {
				session.setAttribute("queryMsg", "修改失败，已存在的物料编码，请重试！");
			}
		} catch (Exception e) {
			session.setAttribute("queryMsg", e.getMessage());
		}
		return "redirect:/web/oa/materia_v2/queryMore?id=" + id + "&whId=" + whId;
	}

	/**
	 * =============================================OA申请============================================
	 */

	@RequestMapping("oa/materia_v2/maApply")
	public String maApply() {
		return "oa/materia_v2/maApply";
	}

	/**
	 * 申领物料页面
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/mtApply2")
	public String mtApply(Model model) {
		String type = Consts.APPLY;
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		String type2 = Consts.APPLY2;
		String url2 = this.maService.findMouldByType(type2);
		model.addAttribute("url2", url2);
		List<MateriaDept> warehouses = this.maService.findAllWarehouse();
		model.addAttribute("warehouses", warehouses);
		return "oa/materia_v2/mtApply";
	}

	/**
	 * 物料申领2
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/mtApply")
	public String maApply2(Model model) {
		String type = "apply";
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		return "oa/materia_v2/apply";
	}

	/**
	 * 解析Ajax上传的文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("oa/materia_v2/uploadFileAjax")
	@ResponseBody
	public AjaxResult<List<Materia>> uploadFileAjax(@RequestParam MultipartFile file, HttpServletRequest request,
			Integer warehouseId) {
		AjaxResult<List<Materia>> ar = new AjaxResult<List<Materia>>();
		List<Materia> materias = new ArrayList<Materia>();
		try {
			if (!file.isEmpty()) {
				// 解析读取excel
				UploadUtilV2.checkFile(file, 5, UploadUtilV2.EXCELFILE);
				Map<String, String> map = UploadUtil.uploadExcel_v2(file, request);
				String path = map.get("path");
				String url = map.get("url");
				List<List<Map<String, String>>> lists = new ReadExcelUtils().readExcel3(path + "/" + url, 2);
				this.maService.checkApply(lists, materias);
				if (!materias.isEmpty()) {
					for (Materia ma : materias) {
						Integer codeId = this.maService.findIdBymateriaCode2(ma.getMateriaCode());
						if (codeId != null) {
							Materia ma2 = this.maService.findMateriaInfoById2(codeId);
							ma.setSpec(ma2.getSpec() != null ? ma2.getSpec() : "-");
							ma.setPackage1(ma2.getPackage1() != null ? ma2.getPackage1() : "-");
							ma.setClassfiy(ma2.getClassfiy() != null ? ma2.getClassfiy() : "-");
							ma.setBrand(ma2.getBrand() != null ? ma2.getBrand() : "-");
							Double stock = this.maService.findCacheMateriaById(codeId, warehouseId);
							ma.setStock(stock != null ? stock : 0.0);
						} else {
							ma.setSpec("无匹配编码");
							ma.setPackage1("-");
							ma.setClassfiy("-");
							ma.setStock(0.0);
						}
					}
					ar.setData(materias);
				} else {
					throw new Exception("文件为空,请重新上传文件");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMsg(e.getMessage());
		}
		return ar;
	}

	@RequestMapping("oa/materia_v2/getMateria")
	@ResponseBody
	public Materia getMateria(Integer whId, HttpServletRequest req) {
		String spec = "";
		try {
			spec = URLDecoder.decode(req.getParameter("spec"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String specStr = spec.replace(" ", "").toUpperCase();
		Materia mas = this.maService.findMtBySpec(specStr);
		Double stock = this.maService.findStock(whId, mas.getId() + "");
		mas.setStock(stock);
		return mas;
	}

	/**
	 * 获取物料编码
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/getMaCodeList")
	@ResponseBody
	public List<String> getItekList(String maCode) {
		try {
			maCode = URLDecoder.decode(maCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<String> mas = this.maService.findListMtByMaCode(maCode);
		return mas;
	}

	/**
	 * 获取物料详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/getMateriaInfo")
	@ResponseBody
	public Materia getMateriaInfo(String maCode, Integer whId) {
		Integer id = this.maService.findIdBymateriaCode2(maCode);
		Materia ma = this.maService.findMateriaInfoById2(id);
		Double stock = this.maService.findCacheMateriaById(id, whId);
		ma.setStock(stock);
		return ma;
	}

	/**
	 * 申领物料
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/saveMateria")
	public String saveMateria(MateriaApply ma, Model model, HttpSession session, Integer type,
			@RequestParam MultipartFile file1, String submitCode, HttpServletRequest request) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			// 检查库存
			this.maService.checkApplyStock(request, ma.getWarehouseId());
			String url2 = "";
			if (!file1.isEmpty()) {
				url2 = UploadUtilV2.uploadFile2(file1, request, 10, UploadUtilV2.ZIPFILE);
			}
			String number = this.maService.getPurchaseCode("SL", MateriaInsertType.MATERIA_APPLY.getType());
			ma.setCode(number);
			User loginUser = this.getLoginUser(session);
			this.maService.insertApplyDetail(request, ma.getCode(), ma.getWarehouseId());
			this.maService.insertApplyMateria2(ma, loginUser, url2);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/apply";
		}
		// 插入申请信息
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/materia_v2/apply";
	}

	/**
	 * 损益申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applySy")
	public String applySy() {
		return "oa/materia_v2/applySy";
	}

	/**
	 * 损益申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/saveSy")
	public String saveSy(Model model, HttpServletRequest req, HttpSession session, String warehouseId,
			String submitCode, String bz) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				model.addAttribute("msg", "请勿重复提交");
				return "oa/materia_v2/applySy";
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			User user = this.getLoginUser(session);
			ApproveStaff as = this.userService.findApproveIdByUserId(user.getId());
			String number = this.maService.getPurchaseCode("SY", MateriaInsertType.MATERIA_SY.getType());
			this.maService.saveSy(number, warehouseId, req, as, user, bz);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/applySy";
		}
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/materia_v2/applySy";
	}

	

	/**
	 * 物料申领审批
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/maApprove")
	public String maApprove(Integer id, HttpSession session, String submitCode, String sp, String opinion,
			Model model) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "oa/materia_v2/mtApply";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		this.maService.maApprove(id, opinion, sp);
		if (isStroreWareManager(session)) {
			return "redirect:/web/oa/materia_v2/maApplyList2?msg=1";
		}
		return "redirect:/web/oa/materia_v2/maApplyList?msg=1";
	}

	/**
	 * 查看申领记录列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applyRecord")
	public String applyRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<MateriaApply> list = this.maService.findMyApplyRecord(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/materia_v2/myApplyRecord";
	}

	/**
	 * 查看申请记录详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applyRecordDetail")
	public String applyRecordDetail(Integer id, Model model, HttpSession session) {
		MateriaApply ma = this.maService.findApplyById(id);
		Integer whId = Integer.parseInt(ma.getWarehouse());
		MateriaDept md = this.maService.findWarehouseById(whId);
		ma.setWarehouse(md.getWarehouse());
		List<Materia> list = this.maService.findMtByCode(ma.getCode());
		model.addAttribute("ma", ma);
		model.addAttribute("list", list);
		// double cost = this.maService.countCost(list, whId);
		// model.addAttribute("cost", cost);
		return "oa/materia_v2/applyRecord";
	}

	/**
	 * 物料入库申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/inware3")
	public String inware3(Model model) {
		// 模板下载
		String type = Consts.INWARE;
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		List<User> users = this.userService.findUserByRole(Constants.INSPECTOR);
		if(!users.isEmpty()) {
			model.addAttribute("user", users.get(0));
		} else {
			model.addAttribute("msg", "未设置质检员");
		}
		return "oa/materia_v2/inware3";
	}

	/**
	 * 获取仓库
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/getWarehouses")
	@ResponseBody
	public List<MateriaDept> getWarehouses() {
		return this.maService.findAllWarehouse();
	}
	
	@RequestMapping("oa/materia_v2/uploadInboundFileAjax")
	@ResponseBody
	public AjaxResultV2<List<Materia>> uploadInboundFileAjax(@RequestParam MultipartFile file, 
			HttpServletRequest request) {
		AjaxResultV2<List<Materia>> ar = new AjaxResultV2<List<Materia>>();
		List<Materia> materias = new ArrayList<Materia>();
		try {
			if (!file.isEmpty()) {
				// 解析读取excel
				UploadUtilV2.checkFile(file, 5, UploadUtilV2.EXCELFILE);
				Map<String, String> map = UploadUtil.uploadExcel_v2(file, request);
				String path = map.get("path");
				String url = map.get("url");
				List<List<Map<String, String>>> lists = new ReadExcelUtils().readExcel3(path + "/" + url, 1);
				this.maService.checkInboundApply(lists, materias);
				if (!materias.isEmpty()) {
					ar.setData(materias);
				} else {
					throw new RuntimeException("文件为空,请重新上传文件");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(AjaxResultV2.FAIL);
			ar.setMsg(e.getMessage());
		}
		return ar;
	}

	/**
	 * 提交入库申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/inware4")
	public String inware4(HttpSession session, String submitCode, String bz, HttpServletRequest req, Integer type,
			Model model, String wl) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception(MateriaConstants.NOT_RESUBMIT);
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			String code = this.maService.getPurchaseCode("RK", MateriaInsertType.MATERIA_INWARE.getType());
			User loginUser = this.getLoginUser(session);
			List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
			// 采购转过来的信息
			String[] codes = req.getParameterValues("materiaCode");
			if (codes != null) {
				mpds = this.maService.addInwareInfo(req, mpds, codes, code);
			}
			// 申请详情
			this.maService.inwareElseDetail(mpds);
			List<User> users = this.userService.findUserByRole(Constants.INSPECTOR);
			if(!users.isEmpty()) {
				model.addAttribute("user", users.get(0));
			} else {
				model.addAttribute("msg", "未设置质检员");
			}
			ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
			this.maService.inwareElse(loginUser, code, bz, "", type, as, "", users.get(0).getId(), wl, mpds);
			model.addAttribute("msg", Consts.SUCCESS);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return "oa/materia_v2/inware3";
	}

	/** 入库申请记录 */
	@RequestMapping("oa/materia_v2/myInwareList")
	public String myInwareList(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord(user.getId(), 3);
		model.addAttribute("list", mps);
		return "oa/materia_v2/inwareRecord";
	}

	/**
	 * 入库详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myInwareDetail")
	public String myInwareDetail(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		if(!StringUtils.isBlank(mp.getProjectCode())) {
			User user = this.userService.findById(Integer.parseInt(mp.getProjectCode()));
			model.addAttribute("jyr", user);
		}
		List<MateriaPurchaseDetail> list = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		// 查询审批
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mp.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		model.addAttribute("ma", mp);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		return "oa/materia_v2/inwareDetail4";
	}

	/**
	 * 物料采购申请页面
	 */
	@RequestMapping("oa/materia_v2/purchase")
	public String purchase222(Model model) {
		String type = Consts.PURCHASE2;
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		return "oa/materia_v2/purchase3";
	}

	/**
	 * 344 物料采购申请页面获取采购编码
	 */
	@RequestMapping("oa/materia_v2/getPurchaseCode")
	@ResponseBody
	public String getPurchaseCode(String str, Integer type) {
		return this.maService.getPurchaseCode(str, type);
	}

	/**
	 * 物料采购申请页面获取物料详情
	 */
	@RequestMapping("oa/materia_v2/getMaInfo")
	@ResponseBody
	public Materia getMaInfo(@RequestParam String code) {
		// 查询物料是否存在
		Integer id = this.maService.findIdBymateriaCode2(code);
		if (id == null) {
			return null;
		}
		Double stock = 0.0;
		// 遍历所有仓库库存求和
		List<MateriaDept> mds = this.maService.findAllWarehouse();
		for (MateriaDept md : mds) {
			Double stock1 = this.maService.findStock(md.getId(), id + "");
			stock += stock1 == null ? 0.0 : stock1;
		}
		// 查询物料信息
		Materia ma = this.maService.findMateriaInfoById2(id);
		ma.setStock(stock);
		return ma;
	}

	/**
	 * 保存申请信息
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/savePurchase")
	public String savePurchase(Model model, MateriaPurchaseDetail2 mpds, String requisitionCode, HttpSession session,
			String bz, String reason, String countMoney, Integer qgId, String submitCode,
			@RequestParam MultipartFile file1, HttpServletRequest req) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception(MateriaConstants.NOT_RESUBMIT);
			}
			User user = this.getLoginUser(session);
			if (mpds == null) {
				throw new Exception("请重新提交");
			}
			String url = "";
			if(!file1.isEmpty()) {
				String str = UploadUtilV2.uploadFile(file1, req, 5, UploadUtilV2.IMGFILE);
				url = UploadUtilV2.getFileName(str);
			}
			Integer id = qgId == null ? user.getId() : qgId;
			String number = this.maService.getPurchaseCode("CG", MateriaInsertType.MATERIA_PURCHASE.getType());
			ApproveStaff as = this.userService.findApproveIdByUserId(id);
			this.maService.savePurchase(mpds, number, bz, reason, countMoney, user.getId(), as, requisitionCode, url);
			model.addAttribute("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/purchase3";
		}
		return "oa/materia_v2/purchase3";

	}

	/**
	 * 请购申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/requisition")
	public String purchase(Model model) {
		String type = Consts.PURCHASE;
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		return "oa/materia_v2/requisition";
	}

	/**
	 * 请购申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/purchase2")
	public String purchase(HttpSession session, String submitCode, @RequestParam MultipartFile file, String bz,
			@RequestParam MultipartFile file1, HttpServletRequest request, Model model, String projectName,
			String projectCode) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception(MateriaConstants.NOT_RESUBMIT);
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			User loginUser = this.getLoginUser(session);
			String number = this.maService.getPurchaseCode("QG", MateriaInsertType.MATERIA_REQUISITION.getType());
			MateriaPurchase mp = new MateriaPurchase();
			// 是否上传文件
			List<List<Map<String, String>>> lists = new ArrayList<List<Map<String, String>>>();
			if (!file.isEmpty()) {
				Map<String, String> map;
				map = UploadUtil.uploadExcel_v2(file, request);
				mp.setUrl(map.get("url"));
				String filePath = map.get("path") + "/" + map.get("url");
				lists = new ReadExcelUtils().readExcel3(filePath, Consts.TWO);
				if (lists.isEmpty()) {
					throw new Exception(MateriaConstants.EMPTY_FILE);
				}
			}
			if (!file1.isEmpty()) {
				Map<String, String> map2;
				map2 = UploadUtil.uploadFile2(file1, request);
				mp.setUrl2(map2.get("url"));
			}
			mp.setContent(bz);
			mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			mp.setEmpId(loginUser.getId());
			mp.setReason(projectName);
			mp.setType(MateriaInsertType.MATERIA_REQUISITION.getType());
			mp.setRequisitionCode(number);
			mp.setProjectCode(projectCode);
			ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());

			// 获取手动添加的物料列表
			String[] maCodes = request.getParameterValues("maCode");
			List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
			if (maCodes != null) {
				String[] specs = request.getParameterValues("spec");
				String[] classifys = request.getParameterValues("classify");
				String[] brands = request.getParameterValues("brand");
				String[] packages = request.getParameterValues("package1");
				String[] units = request.getParameterValues("unit");
				String[] others = request.getParameterValues("other");
				String[] needNums = request.getParameterValues("needNum");
				String[] needDates = request.getParameterValues("needDate");
				String[] prices = request.getParameterValues("price");
				for (int i = 0; i < maCodes.length; i++) {
					MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
					mpd.setCode(number);
					mpd.setMaCode(maCodes[i]);
					mpd.setClassify(classifys[i]);
					mpd.setBrand(brands[i]);
					mpd.setPackage1(packages[i]);
					mpd.setUnit(units[i]);
					mpd.setNeedNum(Double.parseDouble(needNums[i]));
					mpd.setNeedDate(needDates[i]);
					mpd.setLink(others[i]);
					mpd.setPrice(Double.parseDouble(prices[i]));
					mpd.setSpec(specs[i]);
					mpds.add(mpd);
				}
			}
			this.maService.saveRequestion(as, mp, lists, number, mpds);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "oa/materia_v2/requisition";
		}
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/materia_v2/requisition";
	}

	/**
	 * 返料入库申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/reventMateria")
	public String reventMateria(Model model) {
		String type = "物料申领";
		String url = this.maService.findMouldByType(type);
		model.addAttribute("url", url);
		return "oa/materia_v2/reventMateria";
	}

	/**
	 * 返料入库申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/reventApply")
	public String reventApply(String submitCode, HttpSession session, HttpServletRequest req, String content,
			Model model) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "oa/materia_v2/reventMateria";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		User user = this.getLoginUser(session);
		String number = this.maService.getPurchaseCode("FL", MateriaInsertType.MATERIA_REVENT.getType());
		try {
			this.maService.saveReventApply(req, number, user, content);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "oa/materia_v2/reventMateria";
		}
		model.addAttribute("msg", "操作成功");
		return "oa/materia_v2/reventMateria";
	}

	/**
	 * 返料入库申请记录
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myReventRecord")
	public String myReventRecord(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord(user.getId(),
				MateriaInsertType.MATERIA_REVENT.getType());
		model.addAttribute("list", mps);
		model.addAttribute("type", 1);
		return "oa/materia_v2/reventList";
	}

	/******************************************
	 * OA审批***************************************
	 * 
	 */

	/**
	 * 物料审批列表
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/materia_v2/maApplyList")
	public String approveList(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		List<MateriaApply> list = this.maService.findApproveById(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/materia_v2/maApplyList";
	}

	/**
	 * 查看审批记录列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myspRecord")
	public String mySpRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<MateriaApply> list = this.maService.findMySpRecord(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/materia_v2/myApplyRecord2";
	}

	/**
	 * 查看审批记录详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/materia_v2/applyRecordDetail2")
	public String applyRecordDetail2(Integer id, Model model, HttpSession session) {
		MateriaApply ma = this.maService.findApplyById(id);
		MateriaDept md = this.maService.findWarehouseById(Integer.parseInt(ma.getWarehouse()));
		ma.setWarehouse(md.getWarehouse());
		List<Materia> list = this.maService.findMtByCode(ma.getCode());
		model.addAttribute("ma", ma);
		model.addAttribute("list", list);
		return "oa/materia_v2/applyRecord3";
	}

	/**
	 * 入库审批列表
	 */
	@RequestMapping("oa/materia_v2/checkInbound")
	public String inwareApproveList(HttpSession session, Model model) {
		if(StringUtils.isNotBlank((String)session.getAttribute("errormsg"))) {
			session.removeAttribute("errormsg");
		}
		User user = this.getLoginUser(session);
		List<MateriaPurchase> list = this.maService.findPurchaseApprove(user.getId(), 3);
		model.addAttribute("list", list);
		return "oa/materia_v2/checkInbound";
	}
	
	/**
	 * 入库审批列表
	 */
	@RequestMapping("oa/materia_v2/checkInboundRecord")
	public String inwareApproveListRecord(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> list = this.maService.findInboundRecord(user.getId(), 3);
		model.addAttribute("list", list);
		return "oa/materia_v2/checkInboundRecord";
	}

	/**
	 * 入库审批详情
	 */
	@RequestMapping("oa/materia_v2/inwareDetailRecord")
	public String inwareDetailRecord(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		User user = this.userService.findById(Integer.parseInt(mp.getProjectCode()));
		List<MateriaPurchaseDetail> list = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		// 查询审批
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mp.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		
		MateriaWarehouseExample example = new MateriaWarehouseExample();
		example.createCriteria().andStatusEqualTo(DbStatus.ACTIVE.getType()+"");
		List<MateriaWarehouse> maWarehouses = this.maService.selectByExample(example);
		model.addAttribute("whs", maWarehouses);
		model.addAttribute("ma", mp);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("jyr", user);
		return "oa/materia_v2/inwareDetail5";
	}

	/**
	 * 入库审批
	 * @return
	 */
	@RequestMapping("oa/materia_v2/inwareApprove2")
	public String inwareApprove2(HttpSession session, Model model, String opinion, String sp, Integer id,
			String submitCode, HttpServletRequest req, @RequestParam MultipartFile file1, String wllx) {
		if(StringUtils.isNotBlank((String)session.getAttribute("errormsg"))) {
			session.removeAttribute("errormsg");
		}
		Integer type = 2;
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception(MateriaConstants.NOT_RESUBMIT);
			}
			String url2 = "";
			if(!file1.isEmpty()) {
				String path = UploadUtilV2.uploadFile(file1, req, 5, UploadUtilV2.PDFFILE);
				url2 = UploadUtilV2.getFileName(path);
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			List<Role> roles = this.getLoginUserRole(session);
			// 根据查询入库申请信息
			MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
			if(mp.getStatus() == 3) {
				type = 3;
			}
			ApproveStaff as = this.userService.findApproveIdByUserId(mp.getEmpId());
			this.maService.inwareApprove(mp, opinion, id, req, sp, as, url2, wllx);
			for (Role role : roles) {
				if (MateriaConstants.MATERIA_MANAGE.equals(role.getName())) {
					return "redirect:/web/oa/materia_v2/toInware?msg=1";
				}
			}
			return "redirect:/web/oa/materia_v2/checkInbound?msg=1";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errormsg", e.getMessage());
			return "redirect:/web/oa/materia_v2/inwareDetailRecord?id="+id+"&type=" + type;
		}
	}

	/**
	 * 入库处理列表
	 */
	@RequestMapping("oa/materia_v2/toInware")
	public String toInware(HttpSession session, Model model) {
		if(StringUtils.isNotBlank((String)session.getAttribute("errormsg"))) {
			session.removeAttribute("errormsg");
		}
		User user = this.getLoginUser(session);
		List<MateriaPurchase> list = this.maService.findPurchaseApprove(user.getId(), 3);
		model.addAttribute("list", list);
		return "oa/materia_v2/inwareDeal5";
	}

	/**
	 * 入库处理列表
	 */
	@RequestMapping("oa/materia_v2/myRk")
	public String mmyRk(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> list = this.maService.findMyPurchaseApprove(user.getId(), 3);
		model.addAttribute("list", list);
		return "oa/materia_v2/inwareDeal7";
	}

	/**
	 * 入库审批详情
	 */
	@RequestMapping("oa/materia_v2/inwareDetailRecord2")
	public String inwareDetailRecord2(HttpSession session, Model model, Integer id) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> list = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		// 查询审批
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mp.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		MateriaWarehouseExample ex = new MateriaWarehouseExample();
		ex.createCriteria().andStatusEqualTo("1");
		List<MateriaWarehouse> list2 = this.maService.selectByExample(ex);
		model.addAttribute("ma", mp);
		model.addAttribute("list", list);
		model.addAttribute("whs", list2);
		return "oa/materia_v2/inwareDetail6";
	}

	/**
	 * 入库处理列表
	 */
	@RequestMapping("oa/materia_v2/toInware3")
	public String toInware3(HttpSession session, Model model) {
		List<MateriaPurchase> list = this.maService.findRkRecord();
		model.addAttribute("list", list);
		return "oa/materia_v2/inwareDeal6";
	}

	// /**
	// * 确认入库操作，自动加入库存
	// *
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping("oa/materia_v2/doInware")
	// public String doInware(Integer id, Model model, HttpSession session,
	// HttpServletRequest req, Integer warehouseId) {
	// // 根据查询入库申请信息
	// MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
	// String[] maCodes = req.getParameterValues("maCode");
	// if (maCodes != null) {
	// this.maService.doInware(mp, id, mp.getEmpId(), req, maCodes,
	// warehouseId);
	// }
	// return "redirect:/web/oa/materia_v2/toInware?type=2&msg=1";
	// }

	/**
	 * 物料采购审批列表页面
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/purchaseList")
	public String purchaseList(HttpSession session, Model model) {
		try {
			User user = this.getLoginUser(session);
			List<MateriaPurchase> mps = this.maService.findPurchaseApprove(user.getId(),
					MateriaInsertType.MATERIA_PURCHASE.getType());
			for (MateriaPurchase mp : mps) {
				if (!mp.getRequisitionCode().isEmpty()) {
					MateriaPurchase map = this.maService.selectMateriaPurchaseByCode(mp.getRequisitionCode());
					if (map != null) {
						mp.setQgId(map.getEmpId());
						mp.setProjectCode(map.getProjectCode());
					} else {
						throw new Exception("系统错误");
					}
				}
			}
			model.addAttribute("list", mps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "oa/materia_v2/purchaseList";
	}

	/**
	 * 物料采购审批详情页面
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/purDetail")
	public String purDetail(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		model.addAttribute("app", mps);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mps.getPurchaseCode());
		model.addAttribute("list", mpds);
		model.addAttribute("type", type);
		// 查询审批记录
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mps.getPurchaseCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		return "oa/materia_v2/myPurDetail";
	}

	/**
	 * 物料采购审批提交处理
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("oa/materia_v2/purApprove")
	public String purApprove(HttpSession session, Model model, Integer id, String sp, String opinion) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		ApproveStaff as = this.userService.findApproveIdByUserId(mps.getEmpId());
		try {
			this.maService.purApprove(mps, sp, opinion, as);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/materia_v2/purchaseList?msg=1";
	}

	/**
	 * 采购申请记录列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myPurList")
	public String myPurList(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		try {
			List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord(user.getId(),
					MateriaInsertType.MATERIA_PURCHASE.getType());
			for (MateriaPurchase mp : mps) {
				if (!mp.getRequisitionCode().isEmpty()) {
					MateriaPurchase map = this.maService.selectMateriaPurchaseByCode(mp.getRequisitionCode());
					if (map != null) {
						mp.setQgId(map.getEmpId());
						mp.setProjectCode(map.getProjectCode());
					} else {
						throw new Exception("系统错误");
					}
				}
			}
			model.addAttribute("list", mps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("type", 1);
		return "oa/materia_v2/myPurList";
	}

	/**
	 * 采购审批记录
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myPurRecord")
	public String purRecord(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord2(user.getId(), 1);
		model.addAttribute("list", mps);
		model.addAttribute("type", 2);
		return "oa/materia_v2/myPurList";
	}

	/**
	 * 物料请购审批列表页面
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/requisitionList")
	public String purchaseList2(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findPurchaseApprove(user.getId(), 2);
		model.addAttribute("list", mps);
		model.addAttribute("type", 3);
		return "oa/materia_v2/requisitionList";
	}

	/**
	 * 物料请购审批详情页面 type: 1-申请记录列表 2-审批记录列表 3-审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/purDetail2")
	public String purDetail2(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		model.addAttribute("app", mps);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		model.addAttribute("list", mpds);
		model.addAttribute("type", type);
		Double totalMoney = 0.0;
		for (MateriaPurchaseDetail mpd : mpds) {
			totalMoney += mpd.getNeedNum() * mpd.getPrice();
		}
		model.addAttribute("totalMoney", totalMoney);
		// 查询审批记录
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mps.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		List<Role> roles = this.getLoginUserRole(session);
		for (Role role : roles) {
			if (role.containsRole("物料采购员")) {
				model.addAttribute("cgy", 1);
			}
		}
		return "oa/materia_v2/purDetail2";
	}

	/**
	 * 请购申请请记录列表 type: 1-申请记录列表 2-审批记录列表 3-审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myPurList2")
	public String myPurList2(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<Role> roles = this.getLoginUserRole(session);
		// 物料采购员
		if(RoleUtil.hasRole(roles, 23)) {
			List<MateriaPurchase> mps = this.maService.findAllPurchaseRecordByType(2);
			model.addAttribute("list", mps);
		} else {
			List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord(user.getId(), 2);
			model.addAttribute("list", mps);
		}
		model.addAttribute("type", 1);
		return "oa/materia_v2/requisitionList";
	}

	/**
	 * 请购审批记录 type: 1-申请记录列表 2-审批记录列表 3-审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/myPurRecord2")
	public String purRecord2(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord2(user.getId(), 2);
		model.addAttribute("list", mps);
		model.addAttribute("type", 2);
		return "oa/materia_v2/requisitionList";
	}

	/**
	 * 物料采购审批提交处理
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("oa/materia_v2/purApprove2")
	public String purApprove2(HttpSession session, Model model, Integer id, String sp, String opinion) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		ApproveStaff as = this.userService.findApproveIdByUserId(mps.getEmpId());
		try {
			this.maService.purApprove2(mps, sp, opinion, as);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/materia_v2/requisitionList?msg=1";
	}

	/**
	 * 请购转物料采购申请页面
	 */
	@RequestMapping("oa/materia_v2/toPurchase")
	public String toPurchase(HttpServletRequest request, Model model, Integer id) {
		String[] ids = request.getParameterValues("checkId");
		List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
		String code = "";
		Double totalPrice = 0.0;
		if (ids != null) {
			for (String pid : ids) {
				MateriaPurchaseDetail mpd = this.maService.selectMateriaPurchaseDetailById(Integer.parseInt(pid));
				Double stock = this.maService.findTotalStock(mpd.getMaCode());
				mpd.setStockNum(stock);
				Double num = (mpd.getNeedNum() - stock) > 0 ? (mpd.getNeedNum() - stock) : 0;
				mpd.setBuynum(num);
				mpds.add(mpd);
				totalPrice += mpd.getBuynum() * mpd.getPrice();
				code = mpd.getCode();
				// this.maService.updateMateriaPurchaseById(Integer.parseInt(pid));
			}
		}
		model.addAttribute("code", code);
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		model.addAttribute("mp", mp);
		model.addAttribute("mpds", mpds);
		model.addAttribute("index", ids.length);
		model.addAttribute("totalMoney", totalPrice);
		return "oa/materia_v2/purchase3";
	}

	/**
	 * @Title: toDeal @Description:
	 */
	@RequestMapping(value = "oa/materia_v2/toDeal", method = RequestMethod.POST)
	public String toDeal(Integer[] checkId, Model model, Integer type,Integer id) {
		String str = "";
		try {
			MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
			List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
			if (type == 1) {
				mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
				str = "oa/materia_v2/dealPurchaseList";
			} else if (type == 2) {
				mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getPurchaseCode());
				str = "oa/materia_v2/dealInwareList";
			}
			for (MateriaPurchaseDetail mpd : mpds) {
				this.maService.updateMateriaPurchaseById(mpd.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return str;
		}
		return "redirect:/web/" + str + "?msg=1";
	}

	/**
	 * 请购撤销
	 */
	@RequestMapping("oa/materia_v2/cancleApply")
	public String cancleApply(HttpServletRequest request, Model model, Integer id, Integer type) {
		String str = "";
		if (type == 1) {
			str = "oa/materia_v2/myPurList2";
		} else if (type == 2) {
			str = "oa/materia_v2/myPurList";
		}
		MateriaPurchase mp = new MateriaPurchase();
		mp.setId(id);
		mp.setStatus(-1);
		this.maService.updateMateriaPurchaseByPrimaryKeySelective(mp);
		return "redirect:/web/" + str + "?msg=1";
	}
	
	@RequestMapping("oa/materia_v2/cancelInbound")
	@ResponseBody
	public void cancleApply(HttpServletRequest request, Model model, Integer id) {
		MateriaPurchase mp = new MateriaPurchase();
		mp.setId(id);
		mp.setStatus(-1);
		this.maService.updateMateriaPurchaseByPrimaryKeySelective(mp);
	}

	/**
	 * 物料采购员请购页面
	 * 
	 * @param status
	 * @return
	 */
	@RequestMapping("oa/materia_v2/requisitionAjax")
	@ResponseBody
	public List<MateriaPurchase> requisitionAjax(String status) {
		List<MateriaPurchase> list = this.maService.selectMateriaPurchaseByStatus(Integer.parseInt(status));
		if (!list.isEmpty()) {
			for (MateriaPurchase mp : list) {
				User user = this.userService.findById(mp.getEmpId());
				mp.setEmpName(user.getName());
			}
		}
		return list;
	}

	/**
	 * 采购转入库
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/toInware2")
	public String toInware(HttpServletRequest request, Model model) {
		String[] ids = request.getParameterValues("checkId");
		List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
		if (ids != null) {
			for (String id : ids) {
				MateriaPurchaseDetail mpd = this.maService.selectMateriaPurchaseDetailById(Integer.parseInt(id));
				mpds.add(mpd);
				// this.maService.updateMateriaPurchaseById(Integer.parseInt(id));
			}
		}
		model.addAttribute("list", mpds);
		return "oa/materia_v2/inware3";
	}

	/**
	 * 返料入库详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/reventDetail")
	public String reventDetail(Integer id, Model model, Integer type) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		model.addAttribute("app", mp);
		model.addAttribute("list", mpds);
		model.addAttribute("type", type);
		return "oa/materia_v2/reventDetail";
	}

	/**
	 * 返料入库审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/reventApprove")
	public String reventApprove(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findPurchaseApprove(user.getId(),
				MateriaInsertType.MATERIA_REVENT.getType());
		model.addAttribute("list", mps);
		model.addAttribute("type", 3);
		return "oa/materia_v2/reventList";
	}
	
	@RequestMapping("oa/materia_v2/reventApproveRecord")
	public String reventApproveRecord(HttpSession session, Model model) {
		List<MateriaPurchase> mps = this.maService.findReventDealRecord();
		model.addAttribute("list", mps);
		return "oa/materia_v2/reventList2";
	}

	/**
	 * 返料入库处理
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/toRevent")
	public String toRevent(Integer status, String code, Integer id, Model model, Integer warehouseId,
			HttpSession session, Integer[] maId) {
		if (status != null) {
			User user = this.getLoginUser(session);
			try {
				this.maService.dealRevent(status, code, id, maId, warehouseId, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/web/oa/materia_v2/reventApprove?msg=1";
	}

	/**
	 * 损益申请记录
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/syList")
	public String syList(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findMyPurchaseRecord(user.getId(),
				MateriaInsertType.MATERIA_SY.getType());
		model.addAttribute("list", mps);
		model.addAttribute("type", 1);
		return "oa/materia_v2/syList";
	}

	/**
	 * 损益审批
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/syApproveList")
	public String syList2(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		List<MateriaPurchase> mps = this.maService.findPurchaseApprove(user.getId(),
				MateriaInsertType.MATERIA_SY.getType());
		model.addAttribute("list", mps);
		model.addAttribute("type", 3);
		return "oa/materia_v2/syList";
	}

	/**
	 * 损益申请详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/syDetail")
	public String syDetail(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		model.addAttribute("app", mp);
		List<Materia> mas = new ArrayList<Materia>();
		for (MateriaPurchaseDetail mpd : mpds) {
			Integer id2 = this.maService.findIdBymateriaCode2(mpd.getMaCode());
			if (id2 != null) {
				Materia ma = this.maService.findMateriaInfoById2(id2);
				ma.setNum(mpd.getBuynum());
				mas.add(ma);
			} else {
				try {
					throw new Exception("数据异常");
				} catch (Exception e) {
					e.printStackTrace();
					return "oa/materia_v2/syList";
				}
			}
		}
		model.addAttribute("list", mas);
		model.addAttribute("type", type);
		List<MateriaApprove> mass = this.maService.selectMateriaApproveByPrimaryKey(mp.getRequisitionCode());
		for (MateriaApprove ma : mass) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		return "oa/materia_v2/syDetail";
	}

	/**
	 * 损益审批
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/syApprove")
	public String syApprove(String sp, String opinion, Integer id) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		ApproveStaff as = this.userService.findApproveIdByUserId(mps.getEmpId());
		try {
			// this.maService.purApprove(mps, sp, opinion, as);
			this.maService.saveSyApprove(mps, sp, opinion, as);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/materia_v2/syApproveList?msg=1";
	}

	/**
	 * 模板上传页面
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/addmould")
	public String addMudle(Model model) {
		List<Mould> list = this.maService.selectAllMould();
		model.addAttribute("list", list);
		return "oa/materia_v2/addmould";
	}

	/**
	 * 模板上传
	 * 
	 * @param type
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("oa/materia_v2/uploadMould")
	public String uploadExcecl2(String type, @RequestParam MultipartFile file, HttpServletRequest request, Model model,
			String submitCode, HttpSession session) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			// 上传文件
			String fileName = UploadUtilV2.uploadFile22(file, request, 5, UploadUtilV2.EXCELFILE);
			this.maService.uploadMould(type, fileName);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
			return "oa/materia_v2/addmould";
		}
		// 上传模板文件
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/materia_v2/addmould";
	}

	/**
	 * 物料库存查询
	 * 
	 * @return
	 */
	@RequestMapping("oa/materia_v2/querystock2")
	public String queryStock2(Model model, Integer page, String materiaCode, String spec, String all) {
		return "oa/materia_v2/querystock2";
	}

	/**
	 * @Title: dealPurchase @Description: 请购审批完成待采购处理 @param: @param
	 *         model @param: @return @return: String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/dealPurchaseList", method = RequestMethod.GET)
	public String dealPurchase(Model model) {
		try {
			List<MateriaPurchase> mps = this.maService
					.findMateriaByType(MateriaInsertType.MATERIA_REQUISITION.getType(), 4);
			List<MateriaPurchase> list = new ArrayList<MateriaPurchase>();
			for (MateriaPurchase mp : mps) {
				List<MateriaPurchaseDetail> mpds = this.maService
						.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
				for (MateriaPurchaseDetail mpd : mpds) {
					if (mpd.getIsDeal().equals("未处理")) {
						list.add(mp);
						break;
					}
				}
			}
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/dealPurchaseList";
		}
		return "oa/materia_v2/dealPurchaseList";
	}

	/**
	 * @Title: dealPurchaseDetail @Description: 待采购处理详情 @param: @param
	 *         id @param: @param model @param: @return @return: String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/dealPurchaseDetail", method = RequestMethod.GET)
	public String dealPurchaseDetail(Integer id, Model model) {
		try {
			MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
			List<MateriaPurchaseDetail> mpds = this.maService
					.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
			double totalMoney = 0;
			for(MateriaPurchaseDetail mpd : mpds) {
				totalMoney += mpd.getNeedNum() *  mpd.getPrice();
			}
			model.addAttribute("totalMoney", totalMoney);
			model.addAttribute("app", mp);
			model.addAttribute("list", mpds);
			// 查询审批记录
			List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mp.getRequisitionCode());
			for (MateriaApprove ma : mas) {
				model.addAttribute("approve" + ma.getApproveStatus(), ma);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/dealPurchaseList";
		}
		return "oa/materia_v2/dealPurchaseDetail";
	}
	
	
//	@RequestMapping(value = "materia_v2/exportRequition", method = RequestMethod.POST)
//	public void exportRequition(Integer id, Model model, HttpServletResponse response) {
//		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
//		List<MateriaPurchaseDetail> mpds = this.maService
//				.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
//				File file = ExcelUtil.exportQg(mpds);
//		model.addAttribute("file", file);
//		ServletUtil.downloadFile(response, file, file.getName());
//	}

	/**
	 * @Title: dealPurchase @Description: 采购审批完成待入库处理 @param: @param
	 *         model @param: @return @return: String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/dealInwareList", method = RequestMethod.GET)
	public String dealInware(Model model) {
		try {
			List<MateriaPurchase> mps = this.maService.findMateriaByType(MateriaInsertType.MATERIA_PURCHASE.getType(),
					5);
			List<MateriaPurchase> list = new ArrayList<MateriaPurchase>();
			for (MateriaPurchase mp : mps) {
				List<MateriaPurchaseDetail> mpds = this.maService
						.selectMateriaPurchaseDetailByCode(mp.getPurchaseCode());
				for (MateriaPurchaseDetail mpd : mpds) {
					if (mpd.getIsDeal().equals("未处理")) {
						list.add(mp);
						break;
					}
				}
			}
			for (MateriaPurchase li : list) {
				MateriaPurchase mp = this.maService.selectMateriaPurchaseByCode(li.getRequisitionCode());
				li.setQgId(mp.getEmpId());
			}
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/dealInwareList";
		}
		return "oa/materia_v2/dealInwareList";
	}

	/**
	 * @Title: dealPurchaseDetail @Description: 待入库处理详情 @param: @param
	 *         id @param: @param model @param: @return @return: String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/dealInwareDetail", method = RequestMethod.GET)
	public String dealInwareDetail(Integer id, Model model) {
		try {
			MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
			List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getPurchaseCode());
			double totalMoney = 0;
			for(MateriaPurchaseDetail mpd : mpds) {
				totalMoney += mpd.getBuynum() * mpd.getPrice() + mpd.getCost();
			}
			model.addAttribute("totalMoney", totalMoney);
			model.addAttribute("app", mp);
			model.addAttribute("list", mpds);
			// 查询审批记录
			List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mp.getPurchaseCode());
			for (MateriaApprove ma : mas) {
				model.addAttribute("approve" + ma.getApproveStatus(), ma);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/dealInwareList";
		}
		return "oa/materia_v2/dealInwareDetail";
	}

	/**
	 * @Title: exportQg @Description: 导出请购单 @param: @return @return:
	 *         String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/exportQg", method = RequestMethod.POST)
	public void exportQg(Integer id, Model model, HttpServletResponse response) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
		File file = ExcelUtil.exportQg(mpds);
		model.addAttribute("file", file);
		ServletUtil.downloadFile(response, file, file.getName());
	}

	/**
	 * @Title: exportQg @Description: 导出请购单 @param: @return @return:
	 *         String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/exportCg", method = RequestMethod.POST)
	public void exportCg(Integer id, Model model, HttpServletResponse response) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mp.getPurchaseCode());
		File file = ExcelUtil.exportCg(mpds);
		model.addAttribute("file", file);
		ServletUtil.downloadFile(response, file, file.getName());
	}

	/**
	 * @Title: requisitionReApply @Description: 重新申请请购 @param: @param
	 *         qgIds @param: @param model @param: @return @return:
	 *         String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/requisitionReApply", method = RequestMethod.POST)
	public String requisitionReApply(Integer[] qgIds, Model model) {
		List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
		double totalMoey = 0.0;
		for (Integer qgId : qgIds) {
			MateriaPurchaseDetail mpd = this.maService.selectMateriaPurchaseDetailById(qgId);
			totalMoey += mpd.getNeedNum() * mpd.getPrice();
			mpds.add(mpd);
		}
		model.addAttribute("list", mpds);
		model.addAttribute("totalMoney", totalMoey);
		return "oa/materia_v2/requisition";
	}

	/**
	 * @Title: requisitionReApply @Description: 重新申请采购 @param: @param
	 *         qgIds @param: @param model @param: @return @return:
	 *         String @throws
	 */
	@RequestMapping(value = "oa/materia_v2/purchaseReApply", method = RequestMethod.POST)
	public String purchaseReApply(Integer[] purIds, Model model) {
		List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
		double totalMoey = 0.0;
		for (Integer purId : purIds) {
			MateriaPurchaseDetail mpd = this.maService.selectMateriaPurchaseDetailById(purId);
			totalMoey += mpd.getNeedNum() * mpd.getPrice();
			mpds.add(mpd);
		}
		model.addAttribute("mpds", mpds);
		model.addAttribute("totalMoney", totalMoey);
		return "oa/materia_v2/purchase3";
	}
	
	/**
	 * @Title: reject   
	 * @Description: 物料报废申请
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/reject", method = RequestMethod.GET)
	public String reject() {
		return "oa/materia_v2/reject";
	}
	
	
	/**
	 * 
	 * @Title: saveReject   
	 * @Description: 保存物料报废信息
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/saveReject", method = RequestMethod.POST)
	public String saveReject(MateriaPurchase mp, Model model, HttpSession session, Integer type,
			String submitCode, HttpServletRequest request, Integer warehouseId) {
		try {
			String sc = (String) session.getAttribute(Consts.submitCode);
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			// 检查库存
			this.maService.checkApplyStock(request, warehouseId);
			String number = this.maService.getPurchaseCode("BF", MateriaInsertType.MATERIA_REJECT.getType());
			mp.setRequisitionCode(number);
			User loginUser = this.getLoginUser(session);
			ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
			List<MateriaPurchaseDetail> mpds = this.maService.insertApplyDetail2(request, number, warehouseId);
			this.maService.insertApplyReject(mp, loginUser, as, warehouseId, mpds);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/materia_v2/reject";
		}
		// 插入申请信息
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/materia_v2/reject";
	}

	/**
	 * @Title: rejectRecord   
	 * @Description: 物料报废申请记录
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/rejectList", method = RequestMethod.GET)
	public String rejectRecord(HttpSession session, Model model, Integer curPage) {
		User user = this.getLoginUser(session);
		Integer totalSize = this.maService.findRecordTotalSize(user.getId(), 15);
		@SuppressWarnings("rawtypes")
		Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, totalSize);
		List<MateriaPurchase> mps = this.maService.
				findRejectList(user.getId(), 15, page.getBeginPageIndex(), page.getPageSize());
		model.addAttribute("list", mps);
		model.addAttribute("page", page);
		return "oa/materia_v2/rejectList";
	}
	
	
	/**
	 * 
	 * @Title: rejectDetail
	 * @Description:  物料报废详情
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/rejectDetail", method = RequestMethod.GET)
	public String rejectDetail(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		MateriaDept md = this.maService.findWarehouseById(Integer.parseInt(mps.getProjectCode()));
		model.addAttribute("wh", md.getWarehouse());
		model.addAttribute("ma", mps);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		model.addAttribute("list", mpds);
		// 查询审批记录
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mps.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		return "oa/materia_v2/rejectDetail";
	}
	
	/**
	 * 
	 * @Title: rejectDetail
	 * @Description:  物料报废详情
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/rejectDetail2", method = RequestMethod.GET)
	public String rejectDetail2(HttpSession session, Model model, Integer id, Integer type) {
		MateriaPurchase mps = this.maService.selectMateriaPurchaseById(id);
		MateriaDept md = this.maService.findWarehouseById(Integer.parseInt(mps.getProjectCode()));
		model.addAttribute("wh", md.getWarehouse());
		model.addAttribute("ma", mps);
		List<MateriaPurchaseDetail> mpds = this.maService.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		model.addAttribute("list", mpds);
		// 查询审批记录
		List<MateriaApprove> mas = this.maService.selectMateriaApproveByPrimaryKey(mps.getRequisitionCode());
		for (MateriaApprove ma : mas) {
			model.addAttribute("approve" + ma.getApproveStatus(), ma);
		}
		model.addAttribute("type", type);
		return "oa/materia_v2/rejectDetail2";
	}
	
	/**
	 * @Title: rejectApproveList   
	 * @Description: 物料报废审批列表   
	 * @return: String      
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "oa/materia_v2/rejectApprove", method = RequestMethod.GET)
	public String rejectApproveList(HttpSession session, Model model, Integer curPage) {
		try {
			User user = this.getLoginUser(session);
			long totalSize = this.maService.
					countRejectApprove(user.getId(), MateriaInsertType.MATERIA_REJECT.getType(), false);
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<MateriaPurchase> maPurchases = this.maService.findRejectApprove
					(user.getId(), MateriaInsertType.MATERIA_REJECT.getType(),
							page.getBeginPageIndex(), page.getPageSize(), false);
			model.addAttribute("list", maPurchases);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "oa/materia_v2/rejectList2";
	}
	
	/**
	 * @Title: rejectApprove   
	 * @Description: 物料报废审批
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/materia_v2/rejectApprove", method = RequestMethod.POST)
	public String rejectApprove(Integer id, String sp, String opinion) {
		MateriaPurchase mp = this.maService.selectMateriaPurchaseById(id);
		// 保存审批记录
		this.maService.insertApprove(mp, sp, opinion);
		ApproveStaff as = this.userService.findApproveIdByUserId(mp.getEmpId());
		this.maService.saveRejectApprove(mp, sp, as);
		return "redirect:/web/oa/materia_v2/rejectApprove?msg=1";
	}
	
	/**
	 * @Title: rejectRecord   
	 * @Description: 物料报废申批记录
	 * @return: String      
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "oa/materia_v2/rejectList3", method = RequestMethod.GET)
	public String rejectList3(HttpSession session, Model model, Integer curPage) {
		try {
			User user = this.getLoginUser(session);
			long totalSize = this.maService.
					countRejectApprove(user.getId(), MateriaInsertType.MATERIA_REJECT.getType(), true);
			Page page = new Page(curPage == null ? 1 : curPage, Page.PAGE_SIZE_TEN, (int)totalSize);
			List<MateriaPurchase> maPurchases = this.maService.findRejectApprove
					(user.getId(), MateriaInsertType.MATERIA_REJECT.getType(),
							page.getBeginPageIndex(), page.getPageSize(), true);
			model.addAttribute("list", maPurchases);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "oa/materia_v2/rejectList3";
	}
	
	/**
	 * @Title: uploadInboundFileAjax   采购文件上传
	 * @return: AjaxResultV2<List<Materia>>      
	 * @throws
	 */
	@RequestMapping("oa/materia_v2/uploadPurchaseFileAjax")
	@ResponseBody
	public AjaxResultV2<List<MateriaPurchaseDetail>> uploadPurchaseFileAjax(@RequestParam MultipartFile file, HttpServletRequest request) {
		AjaxResultV2<List<MateriaPurchaseDetail>> ar = new AjaxResultV2<List<MateriaPurchaseDetail>>();
		List<MateriaPurchaseDetail> materias = new ArrayList<MateriaPurchaseDetail>();
		try {
			if (!file.isEmpty()) {
				// 解析读取excel
				UploadUtilV2.checkFile(file, 5, UploadUtilV2.EXCELFILE);
				Map<String, String> map = UploadUtil.uploadExcel_v2(file, request);
				String path = map.get("path");
				String url = map.get("url");
				List<List<Map<String, String>>> lists = new ReadExcelUtils().readExcel3(path + "/" + url, 2);
				this.maService.checkPurchaseApply(lists, materias);
				if (!materias.isEmpty()) {
					ar.setData(materias);
				} else {
					throw new RuntimeException("文件为空,请重新上传文件");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(AjaxResultV2.FAIL);
			ar.setMsg(e.getMessage());
		}
		return ar;
	}
	
	@RequestMapping(value = "oa/materia_v2/readFile", method = RequestMethod.GET)
	public String testUpload(Model model) {
		String type = "file";
		MateriaMould mm = this.maService.selectByPrimaryKey(type);
		model.addAttribute("url", mm.getUrl());
		return "oa/materia_v2/readfile";
	}
	
	@RequestMapping(value = "oa/materia_v2/testfile", method = RequestMethod.POST)
	public String testUpload2(@RequestParam MultipartFile file, HttpServletRequest req) {
		try {
			String path = UploadUtilV2.uploadFile2(file, req, 5, UploadUtilV2.ALL);
			MateriaMould mm = new MateriaMould();
			mm.setType("file");
			mm.setUrl(path);
			this.maService.updateMateriaMold(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/materia_v2/readFile";
	}

}
