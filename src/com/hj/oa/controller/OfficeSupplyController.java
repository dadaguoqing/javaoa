package com.hj.oa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApplyApprove;
import com.hj.oa.bean.ApplySilicon;
import com.hj.oa.bean.ApplySiliconApprove;
import com.hj.oa.bean.ApplySupport;
import com.hj.oa.bean.ApplySupportApprove;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Management;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.OsCg;
import com.hj.oa.bean.OsCgWupinVo;
import com.hj.oa.bean.OsSq;
import com.hj.oa.bean.OsSy;
import com.hj.oa.bean.OsWupin;
import com.hj.oa.bean.OsWupinVo;
import com.hj.oa.bean.OsYaopin;
import com.hj.oa.bean.OsYaopinSq;
import com.hj.oa.bean.OsYaopinVo;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.PurchaseRecord;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.SpecialApply;
import com.hj.oa.bean.SupportRecord;
import com.hj.oa.bean.User;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.OfficeSupplyService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.FileUtil;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailTableUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;
import com.hj.util.StringtoByteUtil;
import com.hj.util.UploadUtil;

@Controller
public class OfficeSupplyController extends BaseController {

	@Autowired
	private OfficeSupplyService osService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("oa/os/chooseWp")
	public String chooseWp(Integer companyOnly, HttpSession session, Model model) {

		if (companyOnly != 1) {
			companyOnly = 0;
		}

		// 普通员工0，管理员1，财务2
		User loginUser = this.getLoginUser(session);
		Dept dept = this.getLoginUserDept(session);

		if (dept != null) {
			if (dept.getId() == 5) {
				companyOnly = 2;
			}
		}

		List<OsWupin> list = osService.findWupinByType(companyOnly);

		List<String> classifys = this.osService.findWupinClassify();// new
																	// ArrayList<String>();
		/*
		 * String[] ords = new
		 * String[]{"笔类","刀尺剪","便签类","打印纸","胶体类","笔记本","订书用品类","回针/夹子类","财务单据",
		 * "生活用品类","工牌类","电池类","台球/桌球用具","文件袋/框类"}; for(String str : ords){
		 * classifys.add(str); }
		 */
		HashMap<String, ArrayList<OsWupin>> map = new HashMap<String, ArrayList<OsWupin>>();
		for (OsWupin wp : list) {
			String classify = wp.getClassify();
			ArrayList<OsWupin> wps = map.get(classify);
			if (null == wps) {
				if (!classifys.contains(classify)) {
					classifys.add(classify);
				}
				wps = new ArrayList<OsWupin>();
				map.put(classify, wps);
			}
			wps.add(wp);
		}

		List<String> keys = new ArrayList<String>();

		for (String key : classifys) {
			ArrayList<OsWupin> v = map.get(key);
			if (null == v || v.isEmpty()) {
				map.remove(key);
				keys.add(key);
			}
		}

		for (String key : keys) {
			classifys.remove(key);
		}

		model.addAttribute("classifys", classifys);
		model.addAttribute("map", map);
		return "oa/os/chooseWp";
	}

	@RequestMapping("oa/os/chooseMed")
	public String chooseMed(HttpSession session, Model model) {
		List<OsYaopin> list = osService.findAllYaopin();

		model.addAttribute("list", list);

		return "oa/os/chooseMed";
	}

	@RequestMapping("oa/os/chooseMedForCg")
	public String chooseMedForCg(HttpSession session, Model model) {
		List<OsYaopin> list = osService.findAllYaopin();

		model.addAttribute("list", list);

		return "oa/os/chooseMedForCg";
	}

	@RequestMapping("oa/os/articleApprove") // 审批列表
	public String articleApprove(String content, String name, int id, int operateType, String classify, String type,
			String unit, Integer companyOnly, String submitCode, String newClassify, HttpSession session, Model model) {

		User user = this.getLoginUser(session);

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/modifyArticle";
		}
		String now = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		Management ma = new Management();
		ma.setCreateTime(now);
		ma.setStatus(1);
		ma.setRemark(content);
		ma.setEmpName(user.getName());
		ma.setArticleId(id);
		;
		// 行政主管为8
		ma.setAdminDirectorId(8);
		if (operateType == 1) {
			OsWupin op = this.osService.findWupinById(id);
			ma.setClassify(op.getClassify());
			ma.setName(op.getName());
			ma.setType(op.getType());
			ma.setUnit(op.getUnit());
			ma.setCompanyOnly(op.getCompanyOnly());
			ma.setOperate(1);
		} else if (operateType == 2) {
			if (StringUtils.isEmpty(name) && StringUtils.isEmpty(classify)) {
				return "oa/os/modifyArticle";
			}
			if ("新增分类".equals(classify) && newClassify != null) {
				ma.setClassify(newClassify);
			} else {
				ma.setClassify(classify);
			}
			ma.setName(name);
			ma.setType(type);
			ma.setUnit(unit);
			ma.setCompanyOnly(companyOnly);
			ma.setOperate(2);
		}
		this.osService.addManagement(ma);
		model.addAttribute("msg", "操作成功！");
		return "oa/os/modifyArticle";
	}

	@RequestMapping("oa/os/addWp") // 审批列表
	public String addWp(String name, String classify, String type, String unit, Integer companyOnly, String submitCode,
			String newClassify, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) {

		List<String> classifys = this.osService.findWupinClassify();
		model.addAttribute("classifys", classifys);

		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(classify)) {
			return "oa/os/addWp";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/addWp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsWupin wp = new OsWupin();
		wp.setName(name);
		if ("新增分类".equals(classify)) {
			wp.setClassify(newClassify);
		} else {
			wp.setClassify(classify);
		}
		wp.setType(type);
		wp.setUnit(unit);
		wp.setCompanyOnly(companyOnly);

		this.osService.addWp(wp);

		model.addAttribute("msg", "操作成功。");

		return "oa/os/addWp";
	}

	@RequestMapping("oa/os/modifyArticle")
	public String modifyArticle(int id, int type, HttpSession session, Model model) {

		Management ma = this.osService.findManagementByArtId(id);

		if (ma != null && ma.getStatus() == 1) {
			model.addAttribute("msg", "此物品正在审批！不能进行该操作！");
			return "oa/os/stock";
		}

		List<String> classifys = this.osService.findWupinClassify();
		model.addAttribute("classifys", classifys);
		OsWupin op = this.osService.findWupinById(id);
		model.addAttribute("app", op);
		model.addAttribute("type", type);
		// model.addAttribute("msg", "操作成功。");
		return "oa/os/modifyArticle";
	}

	@RequestMapping("oa/os/addYp") // 审批列表
	public String addYp(String name, String shuoming, String type, String unit, String submitCode,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(type)) {
			return "oa/os/addYp";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/addYp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile mutiFile = (CommonsMultipartFile) multipartRequest.getFile("upload");
			FileItem fileItem = mutiFile.getFileItem();

			String fileName = fileItem.getName();
			String extName = FileUtil.getFileExtName(fileName);

			if (!".pdf".equalsIgnoreCase(extName)) {
				model.addAttribute("msg", "操作失败，您上传的文件格式不符合要求。");
				return "oa/os/addYp";
			}

			fileName = String.valueOf(System.currentTimeMillis());
			fileName += extName;

			File path = new File(Consts.YaopinRootLoc);
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File(Consts.YaopinRootLoc + fileName);
			fileItem.write(file);

			OsYaopin yp = new OsYaopin();
			yp.setName(name);
			yp.setShuoming(shuoming);
			yp.setType(type);
			yp.setUnit(unit);
			yp.setLoc(fileName);

			this.osService.addYp(yp);

			model.addAttribute("msg", "操作成功");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "oa/os/addYp";
	}

	@RequestMapping("oa/os/goEditYp/{id}") //
	public String goEditYp(@PathVariable("id") Integer id, Model model) {
		OsYaopin yp = this.osService.findYaopinById(id);
		model.addAttribute("yp", yp);
		return "oa/os/editYp";
	}

	@RequestMapping("oa/os/editYp") // 审批列表
	public String editYp(Integer id, String shuoming, String type, String unit, String submitCode,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/goEditYp/" + id;
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsYaopin yp = this.osService.findYaopinById(id);
		model.addAttribute("yp", yp);
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile mutiFile = (CommonsMultipartFile) multipartRequest.getFile("upload");

			String fileName = null;
			if (!mutiFile.isEmpty()) {
				FileItem fileItem = mutiFile.getFileItem();

				fileName = fileItem.getName();
				String extName = FileUtil.getFileExtName(fileName);

				if (!".pdf".equalsIgnoreCase(extName)) {
					model.addAttribute("msg", "操作失败，您上传的文件格式不符合要求。");
					return "oa/os/editYp";
				}

				fileName = String.valueOf(System.currentTimeMillis());
				fileName += extName;

				File path = new File(Consts.YaopinRootLoc);
				if (!path.exists()) {
					path.mkdirs();
				}
				File file = new File(Consts.YaopinRootLoc + fileName);
				fileItem.write(file);

			}

			yp.setShuoming(shuoming);
			yp.setType(type);
			yp.setUnit(unit);
			if (null != fileName) {
				yp.setLoc(fileName);
			}

			this.osService.updateYp(yp);

			model.addAttribute("msg", "操作成功");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "oa/os/editYp";
	}

	@RequestMapping("oa/os/wpCgSp") // 审批列表
	public String wpCgSp(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findMyCgSp(loginUser.getId(), 1);
		model.addAttribute("list", list);
		return "oa/os/wpCgSp";
	}

	@RequestMapping("oa/os/myWpCgRecord") // 我的物品申请列表
	public String myWpCgRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findMyCgRecord(loginUser.getId(), 1);
		model.addAttribute("list", list);
		return "oa/os/myWpCgRecord";
	}

	@RequestMapping("oa/os/myYpCgRecord") // 我的药品申请列表
	public String myYpCgRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findMyCgRecord(loginUser.getId(), 2);
		model.addAttribute("list", list);
		return "oa/os/myYpCgRecord";
	}

	@RequestMapping("oa/os/ypCgSp") // 审批列表
	public String ypCgSp(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findMyCgSp(loginUser.getId(), 2);
		model.addAttribute("list", list);
		return "oa/os/ypCgSp";
	}

	@RequestMapping("oa/os/specialApplyApprove")
	public String sprApplyApprove(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		if (loginUser.getId() != 1) {
			List<ApplyApprove> list = this.osService.findSpeApprove(loginUser.getDeptId(), loginUser.getId());
			model.addAttribute("list", list);
		}
		return "oa/os/specialApplyApprove";
	}

	@RequestMapping("oa/os/modifyApprove")
	public String modifyApprove(HttpSession session, Model model) {
		List<Management> list = this.osService.findModifyApproveList();
		model.addAttribute("list", list);
		return "oa/os/modifyApprove";
	}

	@RequestMapping("oa/os/modifyRecode")
	public String modifyRecode(HttpSession session, Model model) {
		User user = this.getLoginUser(session);
		Management ma = new Management();
		ma.setEmpName(user.getName());
		List<Management> list = this.osService.findManagementListByMan(ma);
		model.addAttribute("list", list);
		return "oa/os/modifyRecode";
	}

	@RequestMapping("oa/os/modifyRecodeDetail")
	public String modifyRecodeDetail(int id, HttpSession session, Model model) {
		Management ma = new Management();
		ma.setId(id);
		Management list = this.osService.findManagementByMan(ma);
		model.addAttribute("list", list);
		return "oa/os/modifyRecodeDetail";
	}

	@RequestMapping("oa/os/modifyResult")
	public String modifyResult(int id, String opinion, String sp, HttpSession session, Model model) {

		Management ma = this.osService.findManagementById(id);
		int operate = ma.getOperate();
		ma.setApproveOpinion(opinion);
		if ("审批通过".equals(sp)) {
			ma.setStatus(2);
			if (operate == 1) {
				this.osService.deleteManagementById(ma.getArticleId());
			} else if (operate == 2) {
				this.osService.updateArticleById(ma);
			}
		} else {
			ma.setStatus(-1);
		}
		this.osService.updateManagement(ma);
		return "oa/os/modifyApprove";
	}

	@RequestMapping("oa/os/modifyApproveDetail")
	public String modifyApproveDetail(int id, HttpSession session, Model model) {
		Management ma = this.osService.findManagementById(id);
		model.addAttribute("ma", ma);
		return "oa/os/modifyApproveDetail";
	}

	@RequestMapping("oa/os/speApplyList")
	public String speApplyList(Model model) {
		List<ApplyApprove> list = this.osService.findSpeApplyList(1);
		model.addAttribute("list", list);
		return "oa/os/speApplyList";
	}

	@RequestMapping("oa/os/myWpSySp") // 物品损益审批列表
	public String myWpSySp(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsSy> list = this.osService.findMySySp(loginUser.getId(), 1);
		model.addAttribute("list", list);
		return "oa/os/myWpSySp";
	}

	@RequestMapping("oa/os/myYpSySp") // 药品损益审批列表
	public String myYpSySp(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsSy> list = this.osService.findMySySp(loginUser.getId(), 2);
		model.addAttribute("list", list);
		return "oa/os/myYpSySp";
	}

	@RequestMapping("oa/os/myWpSyRecord") // 物品损益审批列表
	public String myWpSyRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsSy> list = this.osService.findMySyRecord(loginUser.getId(), 1);
		model.addAttribute("list", list);
		return "oa/os/myWpSyRecord";
	}

	@RequestMapping("oa/os/myYpSyRecord") // 物品损益审批列表
	public String myYpSyRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<OsSy> list = this.osService.findMySyRecord(loginUser.getId(), 2);
		model.addAttribute("list", list);
		return "oa/os/myYpSyRecord";
	}

	@RequestMapping("oa/os/wpCgRkList") // 待处理物品采购列表
	public String wpCgRkList(HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findCgRkList(1);
		model.addAttribute("list", list);
		return "oa/os/wpCgRkList";
	}

	@RequestMapping("oa/os/ypCgRkList") // 待处理药品采购列表
	public String ypCgRkList(HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findCgRkList(2);
		model.addAttribute("list", list);
		return "oa/os/ypCgRkList";
	}

	@RequestMapping("oa/os/wpCgRecords") // 物品采购记录
	public String wpCgRecords(HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findCgRecords(1);
		model.addAttribute("list", list);
		return "oa/os/wpCgRecords";
	}

	@RequestMapping("oa/os/ypCgRecords") // 药品采购记录
	public String ypCgRecords(HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);
		List<OsCg> list = this.osService.findCgRecords(2);
		model.addAttribute("list", list);
		return "oa/os/ypCgRecords";
	}

	@RequestMapping("oa/os/wpCgRk/{id}")
	public String wpCgRk(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/wpCgRk";
	}

	@RequestMapping("oa/os/ypCgRk/{id}")
	public String ypCgRk(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/ypCgRk";
	}

	@RequestMapping("oa/os/wpCgDetail/{id}")
	public String wpCgDetail(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/wpCgDetail";
	}

	@RequestMapping("oa/os/ypCgDetail/{id}")
	public String ypCgDetail(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/ypCgDetail";
	}

	@RequestMapping("oa/os/myWpCgDetail/{id}")
	public String myWpCgDetail(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/myWpCgDetail";
	}

	@RequestMapping("oa/os/myYpCgDetail/{id}")
	public String myYpCgDetail(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/myYpCgDetail";
	}

	@RequestMapping("oa/os/wpAllRk")
	public String wpRk(int sqId, Model model) {
		OsCg sq = this.osService.findCgById(sqId);
		sq.setCgStatus(1);
		this.osService.updateCg(sq);
		return "redirect:/web/oa/os/wpCgRkList";
	}

	private boolean isInAry(int i, int[] ary) {
		for (int j : ary) {
			if (j == i) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping("oa/os/wpRkPl")
	public String wpRkPl(int[] wpId, int sqId, String submitCode, Model model) {
		OsCg sq = this.osService.findCgById(sqId);

		List<OsCgWupinVo> list = this.osService.findWupinByCgId(sqId);
		List<OsCgWupinVo> rkList = new ArrayList<OsCgWupinVo>();

		// OsCgWupinVo cgWp = null;
		for (OsCgWupinVo vo : list) {
			if (isInAry(vo.getId(), wpId)) {
				rkList.add(vo);
			}
		}

		if (sq.getType() == 1) {// 物品入库
			this.osService.updateCgWupinPl(rkList);
		} else { // 药品入库
			this.osService.updateCgYaopinPl(rkList);
		}

		list = this.osService.findWupinByCgId(sqId);
		boolean flag = true;
		for (OsCgWupinVo vo : list) {
			if (vo.getCgStatus() == 0) {
				flag = false;
				break;
			}
		}

		if (flag) {
			sq.setCgStatus(1);
			this.osService.updateCg(sq);
			return "redirect:/web/oa/os/wpCgRkList";
		}

		return "redirect:/web/oa/os/wpCgRk/" + sqId;
	}

	@RequestMapping("oa/os/ypRkPl")
	public String ypRkPl(int[] wpId, int sqId, String submitCode, Model model) {
		OsCg sq = this.osService.findCgById(sqId);

		List<OsCgWupinVo> list = this.osService.findWupinByCgId(sqId);
		List<OsCgWupinVo> rkList = new ArrayList<OsCgWupinVo>();

		// OsCgWupinVo cgWp = null;
		for (OsCgWupinVo vo : list) {
			if (isInAry(vo.getId(), wpId)) {
				rkList.add(vo);
			}
		}

		if (sq.getType() == 2) {// 药品入库
			this.osService.updateCgYaopinPl(rkList);
		} else { // 药品入库
			this.osService.updateCgYaopinPl(rkList);
		}

		list = this.osService.findYaopinByCgId(sqId);
		boolean flag = true;
		for (OsCgWupinVo vo : list) {
			if (vo.getCgStatus() == 0) {
				flag = false;
				break;
			}
		}

		if (flag) {
			sq.setCgStatus(1);
			this.osService.updateCg(sq);
			return "redirect:/web/oa/os/ypCgRkList";
		}

		return "redirect:/web/oa/os/ypCgRk/" + sqId;
	}

	@RequestMapping("oa/os/wpRk")
	public String wpRk(int wpId, int sqId, int cgStatus, String bz, Model model) {
		OsCg sq = this.osService.findCgById(sqId);

		List<OsCgWupinVo> list = this.osService.findWupinByCgId(sqId);
		OsCgWupinVo cgWp = null;
		for (OsCgWupinVo vo : list) {
			if (vo.getId() == wpId) {
				cgWp = vo;
			}
		}

		cgWp.setCgStatus(cgStatus);
		if (sq.getType() == 1) {// 物品入库
			if (cgStatus == 1) {// 入库
				OsWupin wp = this.osService.findWupinById(cgWp.getWpId());
				int stock = wp.getStock();
				wp.setStock(stock + cgWp.getNum());
				this.osService.updateWpStock(wp.getId(), wp.getStock());
			}
		} else { // 药品入库
			OsYaopin yp = this.osService.findYaopinById(cgWp.getWpId());
			int stock = yp.getStock();
			yp.setStock(stock + cgWp.getNum());
			this.osService.updateYpStock(yp.getId(), yp.getStock());
		}
		this.osService.updateCgWupin(wpId, cgStatus, bz);

		list = this.osService.findWupinByCgId(sqId);
		boolean flag = true;
		for (OsCgWupinVo vo : list) {
			if (vo.getCgStatus() == 0) {
				flag = false;
				break;
			}
		}

		if (flag) {
			sq.setCgStatus(1);
			this.osService.updateCg(sq);
			return "redirect:/web/oa/os/wpCgRkList";
		}

		return "redirect:/web/oa/os/wpCgRk/" + sqId;
	}

	@RequestMapping("oa/os/ypRk")
	public String ypRk(int wpId, int sqId, int cgStatus, String bz, Model model) {
		OsCg sq = this.osService.findCgById(sqId);

		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(sqId);
		OsCgWupinVo cgWp = null;
		for (OsCgWupinVo vo : list) {
			if (vo.getId() == wpId) {
				cgWp = vo;
			}
		}

		cgWp.setCgStatus(cgStatus);
		if (sq.getType() == 2) {// 药品入库
			if (cgStatus == 1) {// 入库
				OsYaopin yp = this.osService.findYaopinById(cgWp.getWpId());
				int stock = yp.getStock();
				yp.setStock(stock + cgWp.getNum());
				this.osService.updateYpStock(yp.getId(), yp.getStock());
				// this.osService.updateWpStock(wp.getId(), wp.getStock());
			}
		} else { // 药品入库
			OsYaopin yp = this.osService.findYaopinById(cgWp.getWpId());
			int stock = yp.getStock();
			yp.setStock(stock + cgWp.getNum());
			this.osService.updateYpStock(yp.getId(), yp.getStock());
		}
		this.osService.updateCgWupin(wpId, cgStatus, bz);

		list = this.osService.findYaopinByCgId(sqId);
		boolean flag = true;
		for (OsCgWupinVo vo : list) {
			if (vo.getCgStatus() == 0) {
				flag = false;
				break;
			}
		}

		if (flag) {
			sq.setCgStatus(1);
			this.osService.updateCg(sq);
			return "redirect:/web/oa/os/ypCgRkList";
		}

		return "redirect:/web/oa/os/ypCgRk/" + sqId;
	}

	@RequestMapping("oa/os/wpCgSp2") // 正式审批
	public String wpCgSp2(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/wpCgSp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);

		int status = sq.getStatus();
		
		ApproveStaff as = this.userService.findApproveIdByUserId(sq.getProposer());

		if (status == 1) {
			sq.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getCaiwuId());
				sq.setStatus(2);
				if(as.getTreasurer() == as.getManager()) {
					sq.setCurrentId(as.getManager());
					sq.setStatus(3);
				}
			} else {
				sq.setStatus(-1);
			}
		} else if (status == 2) {
			sq.setCaiwuCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getBossId());
				sq.setStatus(3);
			} else {
				sq.setStatus(-1);
			}

		} else if (status == 3) {
			sq.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setStatus(4);
			} else {
				sq.setStatus(-1);
			}
		}

		this.osService.updateCg(sq);

		// String table = this.generalCgTable(list, sq.getType());

		User propUser = this.userService.findById(sq.getProposer());
		User handUser = this.userService.findById(sq.getCurrentId());
		int sts = sq.getStatus();
		String table = MailTableUtil.createBgCg(sq.getContent(), list);
		if (sts >= 0 && sts < 4) {
			this.sendMailTable(handUser, propUser, Consts.MAIL_WP_PURCHASE, table);
		} else {
			this.sendMaileResult(propUser, sts, Consts.MAIL_WP_PURCHASE, table);
		}
		return "redirect:/web/oa/os/wpCgSp?msg=1";
	}

	@RequestMapping("oa/os/speApprove") // 正式审批
	public String speApplyApprove(String sp, String opinion, int id, String submitCode, Model model,
			HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/specialApplyApprove";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		ApplyApprove app = this.osService.findSpeApproveById(id);
		List<SpecialApply> list = this.osService.findSpeApplyBySqId(id);
		// 获取部门主管ID
		User user = this.userService.findById(app.getEmpId());

		int status = app.getStatus();

		if (status == 1) {
			app.setDeptOpinion(opinion);
			if ("审批通过".equals(sp)) {
				app.setCurrentId(8);
				app.setStatus(2);
			} else {
				app.setStatus(-1);
				app.setApplyStatus(-1);
			}
		} else if (status == 2) {
			app.setAdminOpinion(opinion);
			if ("审批通过".equals(sp)) {
				// 行政主管ID
				app.setStatus(4);
				app.setApplyStatus(1);
			} else {
				app.setStatus(-1);
				app.setApplyStatus(-1);
			}
		}
		this.osService.updateApply(app);

		String table = this.generalSpeApplyTable(list);

		User propUser = this.userService.findById(app.getEmpId());
		String deptName = OtherUtil.getDeptName(propUser, this.userService);
		User handUser = this.userService.findById(app.getCurrentId());

		User bdlUser = roleService.findDailiByOwnerId(handUser.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handUser.getId());
			if (OtherUtil.containsMenu(Consts.qjsp, menus)) {
				copyTo = bdlUser.getEmail();
			}
		}

		if (app.getStatus() == 4) {
			// 审批完全通过了
			this.sendMailForCgResutl1(propUser.getName(), propUser.getEmail(), table, 0, deptName);
		} else {//
			if (app.getStatus() == -1) {// 没有通过
				this.sendMailForCgResutl2(propUser.getName(), propUser.getEmail(), table, 0, deptName);
			} else {// 通知下面的人有审批
				this.sendMailForCgSp(handUser.getName(), propUser.getName(), handUser.getEmail(), copyTo, table, 0,
						deptName);
			}
		}

		return "redirect:/web/oa/os/specialApplyApprove?msg=1";
	}

	@RequestMapping("oa/os/ypCgSp2") // 正式审批
	public String ypCgSp2(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/ypCgSp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(id);
		String table = MailTableUtil.createDrug(list, 1);
		ApproveStaff as = this.userService.findApproveIdByUserId(sq.getProposer());
		int status = sq.getStatus();
		if (status == 1) {
			sq.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getCaiwuId());
				sq.setStatus(2);
				if(as.getTreasurer() == as.getManager()) {
					sq.setCurrentId(as.getManager());
					sq.setStatus(3);
				}
			} else {
				sq.setStatus(-1);
			}
		} else if (status == 2) {
			sq.setCaiwuCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getBossId());
				sq.setStatus(3);
			} else {
				sq.setStatus(-1);
			}

		} else if (status == 3) {
			sq.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setStatus(4);
			} else {
				sq.setStatus(-1);
			}
		}

		this.osService.updateCg(sq);
		User propUser = this.userService.findById(sq.getProposer());
		User handUser = this.userService.findById(sq.getCurrentId());
		if (sq.getStatus() < 4 && sq.getStatus() > 0) {
			this.sendMailTable(handUser, propUser, Consts.MAIL_DRUG_PURCHASE, table);
		} else {
			this.sendMaileResult(propUser, sq.getStatus(), Consts.MAIL_DRUG_PURCHASE, table);
		}

		return "redirect:/web/oa/os/ypCgSp?msg=1";
	}

	@RequestMapping("oa/os/wpSySp") // 损益审批
	public String wpSySp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/myWpSySp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findWupinBySyId(id);

		int status = sq.getStatus();

		if (status == 1) {
			sq.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getBossId());
				sq.setStatus(2);
			} else {
				sq.setStatus(-1);
			}
		} else if (status == 2) {
			sq.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setStatus(4);
			} else {
				sq.setStatus(-1);
			}
		}

		User propUser = this.userService.findById(sq.getProposer());
		User handUser = this.userService.findById(sq.getCurrentId());
		User bdlUser = roleService.findDailiByOwnerId(handUser.getId());
		String table = MailTableUtil.createDrugSunYi(list, 2, sq.getSyType(), sq.getContent());
		if (sq.getStatus() == 4) {
			this.osService.updateSy(sq, list);
		} else {//
			this.osService.updateSy(sq);
		}
		int sts = sq.getStatus();
		if(sts >= 0 && sts < 4) {
			this.sendMailTable(handUser, propUser, Consts.MAIL_WP_SUNYI, table);
		} else {
			this.sendMaileResult(propUser, sts, Consts.MAIL_WP_SUNYI, table);
		}

		return "redirect:/web/oa/os/myWpSySp?msg=1";
	}

	@RequestMapping("oa/os/ypSySp") // 损益审批
	public String ypSySp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/myYpSySp";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinBySyId(id);

		int status = sq.getStatus();

		if (status == 1) {
			sq.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setCurrentId(sq.getBossId());
				sq.setStatus(2);
			} else {
				sq.setStatus(-1);
			}
		} else if (status == 2) {
			sq.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				sq.setStatus(4);
			} else {
				sq.setStatus(-1);
			}
		}

		this.osService.updateSy(sq, list);
		String table = MailTableUtil.createDrugSunYi(list, 1, sq.getSyType(), sq.getContent());
		User user2 = this.userService.findById(sq.getProposer());
		User user = this.userService.findById(sq.getCurrentId());
		int st = sq.getStatus();
		if (st < 4 && st > 0) {
			this.sendMailTable(user, user2, Consts.MAIL_DRUG_SUNYI, table);
		} else {
			this.sendMaileResult(user2, st, Consts.MAIL_DRUG_SUNYI, table);
		}

		return "redirect:/web/oa/os/myYpSySp?msg=1";
	}

	@RequestMapping("oa/os/editWpCg/{id}")
	public String editWpCg(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		for (OsCgWupinVo li : list) {
			model.addAttribute("totalPrice", li.getTotalPrice());
		}
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/editWpCg";
	}

	@RequestMapping("oa/os/editSpeApprove/{id}")
	public String editSpeApprove(@PathVariable int id, Model model) {
		ApplyApprove app = this.osService.findSpeApproveById(id);
		List<SpecialApply> list = this.osService.findSpeApplyBySqId(id);
		for (SpecialApply li : list) {
			model.addAttribute("content", li.getContent());
			model.addAttribute("useTime", li.getUseTime());
		}
		model.addAttribute("app", app);
		model.addAttribute("list", list);
		return "oa/os/editSpeApprove";
	}

	@RequestMapping("oa/os/wpcg/{id}")
	public String modifyWpcg(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findWupinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/wpcg";
	}

	@RequestMapping("oa/os/editYpCg/{id}")
	public String editYpCg(@PathVariable int id, Model model) {
		OsCg sq = this.osService.findCgById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinByCgId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/editYpCg";
	}

	@RequestMapping("oa/os/editWpSy/{id}")
	public String editWpSy(@PathVariable int id, Model model) {
		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findWupinBySyId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/editWpSy";
	}

	@RequestMapping("oa/os/editYpSy/{id}")
	public String editYpSy(@PathVariable int id, Model model) {
		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinBySyId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/editYpSy";
	}

	@RequestMapping("oa/os/wpSyDetail/{id}")
	public String wpSyDetail(@PathVariable int id, Model model) {
		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findWupinBySyId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/wpSyDetail";
	}

	@RequestMapping("oa/os/ypSyDetail/{id}")
	public String ypSyDetail(@PathVariable int id, Model model) {
		OsSy sq = this.osService.findSyById(id);
		List<OsCgWupinVo> list = this.osService.findYaopinBySyId(id);
		User propUser = this.userService.findById(sq.getProposer());
		model.addAttribute("propEmp", propUser);
		model.addAttribute("sq", sq);
		model.addAttribute("list", list);
		return "oa/os/ypSyDetail";
	}

	@RequestMapping("oa/os/wpsy")
	public String wpsy(Integer[] ids, String[] names, String[] units, String[] types, Integer syType, int[] nums,
			String content, String submitCode, HttpSession session, Model model) {
		if (null == ids || ids.length == 0) {
			return "oa/os/wpsy";
		}
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/wpsy";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		User loginUser = this.getLoginUser(session);
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);
		OsSy sq = new OsSy();
		sq.setSyType(syType);
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		// 这里都写死了
		sq.setMgrId(8);
		sq.setBossId(1);
		sq.setCurrentId(8);
		sq.setStatus(1);// 部门经理审批
		sq.setType(1);// 1表示办公用品采购
		List<OsCgWupinVo> wps = new ArrayList<OsCgWupinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			OsCgWupinVo vo = new OsCgWupinVo();
			vo.setId(id);
			vo.setName(names[i]);
			vo.setUnit(units[i]);
			vo.setType(types[i]);
			vo.setNum(nums[i]);
			// vo.setMoney(prices[i]);
			wps.add(vo);
		}
		this.osService.addSy(sq, wps);
		model.addAttribute("msg", "您的申请已经提交，请等待审批");
		// 通知发送邮件
		User user = userService.findById(sq.getCurrentId());
		String table = MailTableUtil.createDrugSunYi(wps, 2, syType, content);
		this.sendMailTable(user, loginUser, Consts.MAIL_WP_SUNYI, table);
		return "oa/os/wpsy";
	}

	@RequestMapping("oa/os/ypsy")
	public String ypsy(Integer[] ids, String[] names, String[] units, String[] types, Integer syType, int[] nums,
			String content, String submitCode, HttpSession session, Model model) {

		if (null == ids || ids.length == 0) {
			return "oa/os/ypsy";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/ypsy";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		User loginUser = this.getLoginUser(session);
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);

		OsSy sq = new OsSy();
		sq.setSyType(syType);
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		// 这里都写死了
		sq.setMgrId(8);
		sq.setBossId(1);
		sq.setCurrentId(8);
		sq.setStatus(1);// 部门经理审批
		sq.setType(2);// 2表示应急药品损益

		List<OsCgWupinVo> wps = new ArrayList<OsCgWupinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			OsCgWupinVo vo = new OsCgWupinVo();
			vo.setId(id);
			vo.setName(names[i]);
			vo.setUnit(units[i]);
			vo.setType(types[i]);
			vo.setNum(nums[i]);
			wps.add(vo);
		}
		this.osService.addSy(sq, wps);
		model.addAttribute("msg", "您的申请已经提交，请等待审批");
		String table = MailTableUtil.createDrugSunYi(wps, 1, syType, content);
		// 通知发送邮件
		User user = userService.findById(sq.getCurrentId());
		this.sendMailTable(user, loginUser, Consts.MAIL_DRUG_SUNYI, table);
		return "oa/os/ypsy";
	}

	@RequestMapping("oa/os/wpcg")
	public String wpcg(double[] subTotals, String totalPrice, Integer[] ids, String[] names, String[] units,
			String[] types, Integer[] nums, double[] prices, String content, String submitCode, HttpSession session,
			Model model) {

		if (null == ids || ids.length == 0) {
			return "oa/os/wpcg";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/wpcg";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		User loginUser = this.getLoginUser(session);
		OsCg sq = new OsCg();
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		// 审批角色
		ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
		Integer dir = as.getDeptDirector();
		Integer tre = as.getTreasurer();
		Integer man = as.getManager();
		sq.setMgrId(dir);
		sq.setCaiwuId(tre);
		sq.setBossId(man);
		sq.setCurrentId(dir);
		sq.setStatus(1);
		if(dir == loginUser.getId() || dir == tre) {
			sq.setMgrId(null);
			sq.setCurrentId(tre);
			sq.setStatus(2);
		}
		if(loginUser.getId() == tre || (dir == tre && tre == man)) {
			sq.setCurrentId(man);
			sq.setStatus(3);
			sq.setMgrId(null);
			sq.setCaiwuId(null);
		}
		sq.setType(1);// 1表示办公用品采购

		List<OsCgWupinVo> wps = new ArrayList<OsCgWupinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			OsCgWupinVo vo = new OsCgWupinVo();
			vo.setSubTotal(subTotals[i]);
			vo.setId(id);
			vo.setName(names[i]);
			vo.setUnit(units[i]);
			vo.setType(types[i]);
			vo.setNum(nums[i]);
			vo.setMoney(prices[i]);
			vo.setBz(content);
			vo.setTotalPrice(Double.parseDouble(totalPrice));
			wps.add(vo);
		}
		this.osService.addCg(sq, wps);
		model.addAttribute("msg", "您的申请已经提交，请等待审批");
		// 通知发送邮件
		User user = userService.findById(sq.getCurrentId());
		String table = MailTableUtil.createBgCg(content, wps);
		this.sendMailTable(user, loginUser, Consts.MAIL_WP_PURCHASE, table);
		return "oa/os/wpcg";
	}

	@RequestMapping("oa/os/ypcg")
	public String ypcg(Integer[] ids, String[] names, String[] units, String[] types, Integer[] nums, double[] prices,
			String content, String submitCode, HttpSession session, Model model) {

		if (null == ids || ids.length == 0) {
			return "oa/os/ypcg";
		}
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/ypcg";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		User loginUser = this.getLoginUser(session);
		OsCg sq = new OsCg();
		sq.setContent(content);
		String date = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		sq.setCreateTime(date);
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		
		ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
		
		Integer dir = as.getDeptDirector();
		Integer tre = as.getTreasurer();
		Integer man = as.getManager();
		sq.setMgrId(dir);
		sq.setCaiwuId(tre);
		sq.setBossId(man);
		sq.setCurrentId(dir);
		sq.setStatus(1);
		if(dir == loginUser.getId() || dir == tre) {
			sq.setMgrId(null);
			sq.setCurrentId(tre);
			sq.setStatus(2);
		}
		if(loginUser.getId() == tre || (dir == tre && tre == man)) {
			sq.setCurrentId(man);
			sq.setStatus(3);
			sq.setMgrId(null);
			sq.setCaiwuId(null);
		}
		sq.setType(2);// 2表示应急药品采购
		List<OsCgWupinVo> wps = new ArrayList<OsCgWupinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			OsCgWupinVo vo = new OsCgWupinVo();
			vo.setId(id);
			vo.setName(names[i]);
			vo.setUnit(units[i]);
			vo.setType(types[i]);
			vo.setNum(nums[i]);
			vo.setMoney(prices[i]);
			wps.add(vo);
		}
		this.osService.addCg(sq, wps);

		model.addAttribute("msg", "您的申请已经提交，请等待审批");

		// 通知发送邮件
		User user = userService.findById(sq.getCurrentId());
		User bdlUser = roleService.findDailiByOwnerId(user.getId());
		String copyTo = null;
		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), user.getId());
			if (OtherUtil.containsMenu(Consts.qjsp, menus)) {
				copyTo = bdlUser.getEmail();
			}
		}
		// 1表示应急药品
		String table = MailTableUtil.createDrug(wps, 1);
		this.sendMailTable(user, loginUser, Consts.MAIL_DRUG_PURCHASE, table);
		return "oa/os/ypcg";
	}

	@RequestMapping("oa/os/sqList")
	public String sqList(Model model) {
		List<OsSq> list = this.osService.findSqByStatus(0);
		model.addAttribute("list", list);
		return "oa/os/sqList";
	}

	@RequestMapping("oa/os/medSqList")
	public String medSqList(Model model) {
		List<OsYaopinSq> list = this.osService.findMedSqByStatus(0);
		model.addAttribute("list", list);
		return "oa/os/medSqList";
	}

	@RequestMapping("oa/os/myMedSq")
	public String myMedSqList(Model model, HttpSession session) {

		User loginUser = this.getLoginUser(session);
		List<OsYaopinSq> list = this.osService.findMedSqByEmpId(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/os/myMedSqList";
	}

	@RequestMapping("oa/os/mySq")
	public String mySqList(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		List<OsSq> list = this.osService.findSqByEmpId(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/os/mySqList";
	}

	@RequestMapping("oa/os/mySpeApply")
	public String mySpecialApplyList(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		List<ApplyApprove> list = this.osService.findSpeApplyByEmpId(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/os/mySpeApplyList";
	}

	@RequestMapping("oa/os/compSq")
	public String compSqList(Model model, HttpSession session) {

		// User loginUser = this.getLoginUser(session);
		List<OsSq> list = this.osService.findCompSq();
		model.addAttribute("list", list);
		return "oa/os/compSqList";
	}

	@RequestMapping("oa/os/compMedSq")
	public String compMedSqList(Model model, HttpSession session) {

		// User loginUser = this.getLoginUser(session);
		List<OsYaopinSq> list = this.osService.findCompMedSq();
		model.addAttribute("list", list);
		return "oa/os/compMedSqList";
	}

	@RequestMapping("oa/os/editSq")
	public String editSq(int id, Model model) {
		// List<OsSq> list = this.osService.findSqByStatus(0);

		OsSq sq = this.osService.findSqById(id);
		if (sq.getStatus() != 0) {
			return "redirect:/web/oa/os/sqList";
		}
		List<OsWupinVo> list = this.osService.findSqWpBySqId(id);
		boolean isEnough = this.osService.compareArticle(list);
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);
		model.addAttribute("isEnough", isEnough);
		return "oa/os/editSq";
	}

	@RequestMapping("oa/os/editMedSq")
	public String editMedSq(int id, Model model) {
		// List<OsSq> list = this.osService.findSqByStatus(0);

		OsYaopinSq sq = this.osService.findYaopinSqById(id);
		if (sq.getStatus() != 0) {
			return "redirect:/web/oa/os/medSqList";
		}

		List<OsYaopinVo> list = this.osService.findSqYpBySqId(id);
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);

		return "oa/os/editMedSq";
	}

	@RequestMapping("oa/os/ajaxGetMed")
	public String ajaxGetMed(int id, Model model) {
		OsYaopin yp = this.osService.findYaopinById(id);
		model.addAttribute("yp", yp);
		return "oa/os/ajaxGetMed";
	}

	@RequestMapping("oa/os/detail")
	public String detail(int id, Model model, HttpSession session) {
		// List<OsSq> list = this.osService.findSqByStatus(0);
		User loginUser = this.getLoginUser(session);
		OsSq sq = this.osService.findSqById(id);
		if (sq.getEmpId() != loginUser.getId()) {
			return "redirect:/web/oa/os/mySq";
		}
		List<OsWupinVo> list = this.osService.findSqWpBySqId(id);
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);
		return "oa/os/detail";
	}

	@RequestMapping("oa/os/specialApplyDetail")
	public String speApplyDetail(int id, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		ApplyApprove sq = this.osService.findSpeApplyById(id);
		if (sq.getEmpId() != loginUser.getId()) {
			return "redirect:/web/oa/os/mySpeApply";
		}
		List<SpecialApply> list = this.osService.findSpeApplyBySqId(id);
		for (SpecialApply li : list) {
			model.addAttribute("useTime", li.getUseTime());
		}
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);
		return "oa/os/specialApplyDetail";
	}

	@RequestMapping("oa/os/speApplyDetail/{id}")
	public String speApplyDetail2(@PathVariable int id, Model model, HttpSession session) {
		ApplyApprove sq = this.osService.findSpeApplyById(id);
		List<SpecialApply> list = this.osService.findSpeApplyBySqId(id);
		for (SpecialApply li : list) {
			model.addAttribute("useTime", li.getUseTime());
		}
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);
		return "oa/os/speApplyDetail";
	}

	@RequestMapping("oa/os/sendMessage")
	public String sendMessage(int id, Model model, HttpSession session) {
		ApplyApprove aa = this.osService.findSpeApproveById(id);
		this.osService.updateSpeApplyStatus(id);
		User user = this.userService.findById(aa.getEmpId());
		String subject = "特别申请物品领用通知";
		String content = "您的特别申请已经通过审批，已经入库，可以在办公用品申领里申请了！";
		mailUtil.sendEMail(user.getEmail(), null, Consts.defaultFrom, subject, content);
		return "redirect:/web/oa/os/speApplyList?msg=1";
	}

	@RequestMapping("oa/os/medDetail")
	public String medDetail(int id, Model model, HttpSession session) {
		// List<OsSq> list = this.osService.findSqByStatus(0);
		User loginUser = this.getLoginUser(session);
		OsYaopinSq sq = this.osService.findYaopinSqById(id);
		if (sq.getEmpId() != loginUser.getId()) {
			return "redirect:/web/oa/os/myMedSq";
		}
		List<OsYaopinVo> list = this.osService.findSqYpBySqId(id);
		model.addAttribute("list", list);
		model.addAttribute("sq", sq);
		return "oa/os/medDetail";
	}

	@RequestMapping("oa/os/showMedPdf")
	public String showMedPdf(int id, Model model, HttpSession session) {
		// User loginUser = this.getLoginUser(session);
		OsYaopin yp = this.osService.findYaopinById(id);
		model.addAttribute("yp", yp);

		return "oa/os/showMedPdf";
	}

	@RequestMapping("oa/os/{id}.pdf")
	public void getPdf(@PathVariable int id, Model model, HttpServletResponse response) {

		OsYaopin yp = this.osService.findYaopinById(id);
		model.addAttribute("yp", yp);

		if (yp == null) {
			return;
		}
		String loc = yp.getLoc();

		File file = new File(Consts.YaopinRootLoc + loc);


		ServletUtil.downloadFile(response, file, yp.getName(), "application/pdf");
		// return "oa/notice/showRule";
	}

	@RequestMapping("oa/os/medSp") // 确认领用或者，取消申领 应急药品
	public String medSp(int id, Integer status, Model model, HttpSession session) {
		OsYaopinSq sq = this.osService.findYaopinSqById(id);
		if (sq.getStatus() != 0) {
			return "redirect:/web/oa/os/medSqList";
		}
		User propUser = this.userService.findById(sq.getEmpId());
		String deptName = OtherUtil.getDeptName(propUser, this.userService);
		if (status == 1 || status == -1) {
			sq.setStatus(status);
			osService.updateYpSq(sq);
			List<OsYaopinVo> yps = this.osService.findSqYpBySqId(id);
			if (status == 1) {// 申领成功
				for (OsYaopinVo vo : yps) {
					this.osService.updateYpStock(-1, vo.getId(), vo.getNum());// 跟新库存
				}
			}
			String table = MailTableUtil.createGetDrug(yps, 1, sq.getType());
			User sqUser = this.userService.findById(sq.getEmpId());
			this.sendMaileResult(sqUser, status, Consts.MAIL_DRUG_APPLY, table);
		}
		return "redirect:/web/oa/os/medSqList?msg=1";
	}

	@RequestMapping("oa/os/sp") // 确认领用或者，取消申领
	public String sp(int id, Integer status, Model model, HttpSession session) {
		// List<OsSq> list = this.osService.findSqByStatus(0);

		User loginUser = this.getLoginUser(session);
		OsSq sq = this.osService.findSqById(id);
		if (sq.getStatus() != 0) {
			return "redirect:/web/oa/os/sqList";
		}
		if (status == 1 || status == -1) {
			sq.setStatus(status);
			osService.updateSq(sq);
			List<OsWupinVo> wps = this.osService.findSqWpBySqId(id);
			List<OsYaopinVo> yps = new ArrayList<OsYaopinVo>();
			for(OsWupinVo wp : wps) {
				OsYaopinVo osy = new OsYaopinVo();
				osy.setName(wp.getName());
				osy.setType(wp.getType());
				osy.setNum(wp.getNum());
				osy.setUnit(wp.getUnit());
				yps.add(osy);
			}
			for (OsWupinVo vo : wps) {
				this.osService.updateWpStock(-1, vo.getId(), vo.getNum());// 跟新库存
			}
			String table = MailTableUtil.createGetDrug(yps, 2, sq.getType());
			User user = this.userService.findById(sq.getEmpId());
			this.sendMaileResult(user, status, Consts.MAIL_WP_APPLY, table);
		}
		return "redirect:/web/oa/os/sqList?msg=1";
	}

	@RequestMapping("oa/os/wpDetail")
	public String wpDetail(int wpId, String begin, String end, Integer showType, Model model) {

		List<OsWupinVo> list = this.osService.findSqWpByWpId(begin, end, wpId);
		model.addAttribute("list", list);

		OsWupinVo total = new OsWupinVo();
		int count = 0;
		for (OsWupinVo v : list) {
			count += v.getNum();
			total = v;
		}
		// if(total!=null)
		model.addAttribute("total", total);
		model.addAttribute("count", count);

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("showType", showType);

		return "oa/os/wpDetail";
	}

	@RequestMapping("oa/os/ypDetail")
	public String ypDetail(int wpId, String begin, String end, Integer showType, Model model) {

		List<OsYaopinVo> list = this.osService.findSqYpByYpId(begin, end, wpId);
		model.addAttribute("list", list);

		OsYaopinVo total = new OsYaopinVo();
		int count = 0;
		for (OsYaopinVo v : list) {
			count += v.getNum();
			total = v;
		}
		// if(total!=null)
		model.addAttribute("total", total);
		model.addAttribute("count", count);

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("showType", showType);

		return "oa/os/ypDetail";
	}

	@RequestMapping("oa/os/medSearch")
	public String medSearch(String begin, String end, Integer uType, Integer showType, String uid, String uname,
			Model model) {

		if (null == showType) {
			showType = 1;
		}

		if (StringUtils.isEmpty(begin)) {
			String yf = DateUtil.getCurrentTime("yyyy年MM月");
			begin = yf + "01日";
			end = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		}

		if (uType == null || uType == 0) {
			uid = "";
			uname = "";
			List<OsYaopinVo> list = this.osService.findSqYpSum(begin, end, null, showType, null);
			model.addAttribute("list", list);
		} else if (uType == 2) {
			uid = "";
			uname = "";
			List<OsYaopinVo> list = this.osService.findSqYpSum(begin, end, null, showType, 1);
			model.addAttribute("list", list);
		} else {
			uType = 1;
			List<OsYaopinVo> list = this.osService.findSqYpSum(begin, end, Integer.parseInt(uid), showType, null);
			model.addAttribute("list", list);
		}

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		if (null == uType) {
			uType = 0;
		}
		model.addAttribute("uType", uType);
		model.addAttribute("showType", showType);
		model.addAttribute("uid", uid);
		model.addAttribute("uname", uname);

		return "oa/os/medSearch";
	}

	@RequestMapping("oa/os/stock")
	public String stock(String type, Integer uType, Integer wpId, HttpSession session, Model model) {
		if (null == uType) {
			uType = 0;
		}
		if (null == type) {
			type = "0";
		}

		List<String> classifys = this.osService.findWupinClassify();// new
																	// ArrayList<String>();
		/*
		 * String[] ords = new
		 * String[]{"笔类","刀尺剪","便签类","打印纸","胶体类","笔记本","订书用品类","回针/夹子类","财务单据",
		 * "生活用品类","工牌类","电池类","台球/桌球用具","文件袋/框类"}; for(String str : ords){
		 * classifys.add(str); }
		 */

		List<OsWupin> list = new ArrayList<OsWupin>();
		String uname = null;
		if (uType == 1) {
			OsWupin wp = this.osService.findWupinById(wpId);
			uname = wp.getName();
			list.add(wp);
		} else {
			list = this.osService.findWupinByTypeName(type);
		}

		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("uType", uType);
		model.addAttribute("uname", uname);
		model.addAttribute("uid", wpId);
		model.addAttribute("classifys", classifys);

		return "oa/os/stock";
	}

	@RequestMapping("oa/os/medStock")
	public String medStock(HttpSession session, Model model) {
		List<OsYaopin> list = this.osService.findAllYaopin();
		model.addAttribute("list", list);
		return "oa/os/medStock";
	}

	/**
	 * 芯片申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/siliconApply")
	public String siliconApply(HttpSession session, Model model, String submitCode, Integer[] ids, String[] reasons,
			Integer[] nums, String useDayStr, String[] brands) {
		User loginUser = this.getLoginUser(session);
		if (null == ids || ids.length == 0) {
			if (loginUser.getId() == 1) {// 总经理
				model.addAttribute("alert", 1);
			}
			return "oa/os/siliconApply";
		}
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "oa/os/siliconApply";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		// 插入到申请表
		ApplySilicon as = new ApplySilicon();
		int applyId = StringtoByteUtil.getDateCode();
		as.setApplyId(applyId);
		for (int i = 0; i < nums.length; i++) {
			as.setBrand(brands[i]);
			as.setNum(nums[i]);
			as.setReason(reasons[i]);
			this.osService.insertSilicon(as);
		}
		// 插入到审批表
		ApplySiliconApprove asa = new ApplySiliconApprove();
		asa.setApplyId(applyId);
		asa.setDayStr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		asa.setUseDayStr(useDayStr);
		
		
		// 获取部门主管ID
		int mgrId = this.osService.getDeptDirectorIdByName(loginUser.getDeptName());
		// 获取部门总监ID
		Integer pId = this.osService.getDeptPIdByName(loginUser.getDeptName());
		// 获取生产运营部总监ID
		Integer pId2 = this.osService.getDeptPIdByName("生产运营部");
		// 芯片管理员
		List<User> siliconManagers = this.userService.findUserByRole("芯片管理员");
		int siliconManager = 0;
		for (User li : siliconManagers) {
			siliconManager = li.getId();
		}
		asa.setApproveId01(mgrId);
		asa.setApproveId02(pId);
		asa.setApproveId03(pId2);
		// 这里写死，指定为王莉君
		asa.setApproveId04(35);
		// 这里指定为芯片管理员
		asa.setApproveId05(siliconManager);
		asa.setStatus(0);
		asa.setCurrentId(mgrId);
		asa.setEmpId(loginUser.getId());
		if(loginUser.getDeptId() == 17 || (mgrId == pId && pId == pId2)) {
			asa.setApproveId01(0);
			asa.setApproveId02(0);
			asa.setStatus(2);
			asa.setCurrentId(asa.getApproveId03());
		}
		if(loginUser.getId() == pId2) {
			asa.setApproveId01(0);
			asa.setApproveId02(0);
			asa.setApproveId03(0);
			asa.setStatus(3);
			asa.setCurrentId(asa.getApproveId04());
		}
		if(loginUser.getId() == 35) {
			asa.setApproveId04(0);
		}
		
		
		this.osService.insertSiliconApprove(asa);
		
		model.addAttribute("msg", "申请成功！");
		List<ApplySilicon> list = this.osService.findSiliconByApplyId(applyId);
		String table = MailTableUtil.addSiliconApply(list, useDayStr);
		User user = this.userService.findById(asa.getCurrentId());
		this.sendMailTable(user, loginUser, Consts.MAIL_SILICON_APPLY, table);
		return "oa/os/siliconApply";
	}

	/**
	 * 芯片申请记录列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/os/siliconApplyList")
	public String siliconApplyList(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySiliconApprove> list = this.osService.findSiliconApply(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/os/siliconApplyList";
	}

	/**
	 * 芯片申请记录详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/siliconDetail")
	public String siliconDetail(int id, Model model) {
		ApplySiliconApprove as = this.osService.findSiliconUser(id);
		User applyUser = this.userService.findById(as.getEmpId());
		as.setEmpName(applyUser.getName());
		as.setDeptId(applyUser.getDeptId());
		List<ApplySilicon> list = this.osService.findSiliconById(id);
		model.addAttribute("list", list);
		model.addAttribute("app", as);
		return "oa/os/siliconDetail";
	}

	/**
	 * 芯片审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/siliconApprove")
	public String siliconApprove(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySiliconApprove> list = this.osService.findSiliconApprove(loginUser.getId());
		for (ApplySiliconApprove li : list) {
			li.setApplyName(userService.findById(li.getEmpId()).getName());
		}
		List<Role> roles = this.getLoginUserRole(session);
		model.addAttribute("list", list);
		// 是芯片管理员
		if(RoleUtil.hasRole(roles, 18)) {
			return "oa/os/siliconApprove2";
		}
		return "oa/os/siliconApprove";
	}
	
	@RequestMapping("oa/os/siliconApprove2")
	public String siliconApprove2(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySiliconApprove> list = this.osService.findSiliconApprove(loginUser.getId());
		for (ApplySiliconApprove li : list) {
			li.setApplyName(userService.findById(li.getEmpId()).getName());
		}
		model.addAttribute("list", list);
		return "oa/os/siliconApprove2";
	}

	/**
	 * 芯片审批详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/editSiliconApprove/{id}")
	public String editSiliconApprove(@PathVariable int id, Model model) {
		ApplySiliconApprove as = this.osService.findSiliconUser(id);
		User applyUser = this.userService.findById(as.getEmpId());
		as.setEmpName(applyUser.getName());
		as.setDeptId(applyUser.getDeptId());
		List<ApplySilicon> list = this.osService.findSiliconById(id);
		model.addAttribute("list", list);
		model.addAttribute("app", as);
		return "oa/os/editSiliconApprove";
	}

	/**
	 * 芯片审批记录列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/siliconApproveList")
	public String siliconApproveList(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySiliconApprove> list = this.osService.findSiliconApproveRecord(loginUser.getId());
		for (ApplySiliconApprove li : list) {
			li.setApplyName(userService.findById(li.getEmpId()).getName());
		}
		model.addAttribute("list", list);
		return "oa/os/siliconApproveList";
	}

	/**
	 * 芯片审批记录详情
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/siliconApproveDetail")
	public String siliconApproveDetail(int id, Model model) {
		ApplySiliconApprove as = this.osService.findSiliconUser(id);
		User applyUser = this.userService.findById(as.getEmpId());
		as.setEmpName(applyUser.getName());
		as.setDeptId(applyUser.getDeptId());
		List<ApplySilicon> list = this.osService.findSiliconById(id);
		model.addAttribute("list", list);
		model.addAttribute("app", as);
		return "oa/os/siliconApproveDetail";
	}

	/**
	 * 审批结果
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/saveSiliconApprove")
	public String saveSiliconApprove(HttpSession session, Model model, String submitCode, int id, String sp,
			String opinion) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/specialApplyApprove";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		ApplySiliconApprove as = this.osService.findSiliconUser(id);
		User user = this.userService.findById(as.getEmpId());
		List<ApplySilicon> list = this.osService.findSiliconById(id);
		String table = MailTableUtil.addSiliconApply(list, as.getUseDayStr());
		int status = as.getStatus();
		ApplySiliconApprove app = new ApplySiliconApprove();
		app.setApplyId(as.getApplyId());
		if (status == 0) {
			app.setApproveReason01(opinion);
			if ("审批通过".equals(sp)) {
				if (as.getApproveId02() == 0 || as.getApproveId02() == as.getApproveId03()) {
					app.setCurrentId(as.getApproveId03());
					app.setStatus(2);
				} else {
					app.setCurrentId(as.getApproveId02());
					app.setStatus(1);
				}
			} else {
				app.setStatus(-1);
			}
		} else if (status == 1) {
			app.setApproveReason02(opinion);
			if ("审批通过".equals(sp)) {
				if (as.getApproveId03() == 0) {
					app.setCurrentId(as.getApproveId04());
					app.setStatus(3);
				} else {
					app.setCurrentId(as.getApproveId03());
					app.setStatus(2);
				}
			} else {
				app.setStatus(-1);
			}
		} else if (status == 2) {
			app.setApproveReason03(opinion);
			if ("审批通过".equals(sp)) {
				if (as.getApproveId04() == 0 || as.getEmpId() == 35) {
					app.setCurrentId(as.getApproveId05());
					app.setStatus(4);
				} else {
					app.setCurrentId(as.getApproveId04());
					app.setStatus(3);
				}
			} else {
				app.setStatus(-1);
			}
		} else if (status == 3) {
			app.setApproveReason04(opinion);
			if ("审批通过".equals(sp)) {
				app.setCurrentId(as.getApproveId05());
				app.setStatus(4);
			} else {
				app.setStatus(-1);
			}
		} else if (status == 4) {
			app.setApproveReason05(opinion);
			if ("审批通过".equals(sp)) {
				app.setStatus(5);
			} else {
				app.setStatus(-1);
			}
		}
		this.osService.saveSiliconApprove(app);
		int statu = app.getStatus();
		if (statu < 5 && statu > 0) {
			User user2 = this.userService.findById(app.getCurrentId());
			this.sendMailTable(user2, user, Consts.MAIL_SILICON_APPLY, table);
		} else {
			this.sendMaileResult(user, statu, Consts.MAIL_SILICON_APPLY, table);
		}
		return "redirect:/web/oa/os/siliconApprove?msg=1";
	}

	/**
	 * 技术支持申请
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("oa/os/tecSupport")
	public String tecSupport() {
		return "oa/os/tecSupport";
	}

	// 技术支持申请
	/**
	 * 技术支持申请
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("oa/os/tecSupportAction")
	public String tecSupportAction(HttpSession session, Model model, HttpServletRequest request, String submitCode,
			String customer, String project, String supportContent, String priority, String expectdTime,
			String deadline, @RequestParam MultipartFile file) {
		if (customer == null) {
			return "oa/os/tecSupport";
		}
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "oa/os/tecSupport";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		User loginUser = this.getLoginUser(session);
		int dateCode = StringtoByteUtil.getDateCode();
		ApplySupport as = new ApplySupport();
		as.setApplyId(loginUser.getId());
		as.setCustomer(customer);
		as.setDeadline(deadline);
		as.setSupportId(dateCode);
		as.setDayStr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		as.setExpectdTime(expectdTime);
		as.setPriority(priority);
		as.setProject(project);
		as.setSupportContent(supportContent);
		String str = UploadUtil.uploadFile(file, request);
		as.setAccessory(str.split(",")[0]);
		String msg = str.split(",")[1];
		model.addAttribute("msg", msg);
		if ("操作成功".equals(msg)) {
			this.osService.insertSupportApply(as);
		}
		// 插入数据到审批表
		// Integer busManagerId = this.osService.getDeptDirectorIdByName("业务部");
		// 产品技术部
		Integer tecManagerId = this.osService.getDeptDirectorIdById(14);
		ApplySupportApprove asa = new ApplySupportApprove();
		asa.setSupportId(dateCode);
		// asa.setBusManagerId(busManagerId);
		asa.setTecManagerId(tecManagerId);
		asa.setStatus(2);
		asa.setCurrentId(tecManagerId);
		asa.setCurrentId2(0);
		this.osService.insertSupportApprove(asa);

		/**
		 * 发邮件
		 */
		// 发送邮件给所有审批人员
		// int bnsMgrId =
		// this.osService.getDeptDirectorIdByName(loginUser.getDeptName());
		int tecMgrId = this.osService.getDeptDirectorIdById(14);
		// 把审批人员加到List中
		List<User> users = new ArrayList<User>();
		// users.add(this.userService.findById(bnsMgrId));
		users.add(this.userService.findById(tecMgrId));
		User sUser = loginUser;

		StringBuilder sb = new StringBuilder();
		sb.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
				+ "<tr><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>优先级</th>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>期望完成时间</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>最晚完成时间</th>");
		sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(priority);
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(expectdTime);
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(deadline);
		sb.append("</td></tr>");
		sb.append("<tr><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>客户单位(全称)</th>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>开案项目</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>	技术支持内容</th>");
		sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(customer);
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(project);
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append(supportContent);
		sb.append("</td></tr>");
		sb.append("</table>");

		for (User user : users) {
			String to = user.getEmail();
			String copyTo = null;
			if (Consts.devMode == 1) {
				to = "";
			}
			String fromName = sUser.getName();
			String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, user.getName(), fromName, sUser.getDeptName(), "技术支持申请", null, as);
			this.sendMailForSupsForSupport(to, copyTo, text);
		}
		return "oa/os/tecSupport";
	}

	/**
	 * 技术支持申请记录列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/mySupportRecord")
	public String mySupportRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySupport> list = this.osService.findSupportApplyRecord(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/os/supportApplyList";
	}

	/**
	 * 技术支持申请记录详情
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("oa/os/supportApplyRecord")
	public String supportApply(HttpSession session, Model model, HttpServletResponse response, int id) {
		User loginUser = this.getLoginUser(session);
		ApplySupportApprove list = this.osService.findSupportApplyById(id);
		User user = userService.findById(list.getApplyId());
		list.setApplyName(user.getName());
		list.setDeptName(user.getDeptName());
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		model.addAttribute("loginUserId", loginUser.getId());
		// 技术主管审批记录
		List<SupportRecord> support = this.osService.findSupportById(list.getSupportId());
		model.addAttribute("support", support);
		return "oa/os/supportApplyRecord";
	}

	/**
	 * 技术支持审批列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/supportApproveList")
	public String supportApproveList(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySupport> list = this.osService.findSupportApprove(loginUser.getId());
		for (ApplySupport li : list) {
			li.setApplyName(userService.findById(li.getApplyId()).getName());
		}
		model.addAttribute("list", list);
		return "oa/os/supportApproveList";
	}

	/**
	 * 技术支持审批
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("oa/os/tecSupportApprove")
	public String tecSupportApprove(HttpSession session, Model model, HttpServletResponse response, int id) {
		User loginUser = this.getLoginUser(session);
		ApplySupportApprove list = this.osService.findSupportApplyById(id);
		User user = userService.findById(list.getApplyId());
		// String url = UploadUtil.downloadFile(list.getAccessory(), "zipFile");
		// list.setAccessory(url);
		list.setApplyName(user.getName());
		list.setDeptName(user.getDeptName());
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		model.addAttribute("loginUserId", loginUser.getId());
		// 技术主管审批记录
		List<SupportRecord> support = this.osService.findSupportById(list.getSupportId());
		// for(SupportRecord li:support){
		// String url2 = UploadUtil.downloadFile(li.getFile2(), "zipFile");
		// li.setFile2(file2);
		// }
		model.addAttribute("support", support);
		// 技术部所有人员
		if (loginUser.getId() == 112) {
			List<User> users = this.userService.findByDeptId(14);
			List<User> users2 = new ArrayList<User>();
			for (User li : users) {
				if (!"文中海".equals(li.getName())) {
					users2.add(li);
				}
			}
			model.addAttribute("users", users2);
		}
		// 技术人员
		Integer FAEID = this.osService.findFAEId(list.getSupportId());
		model.addAttribute("FAEID", FAEID);
		return "oa/os/tecSupportApprove";
	}

	/**
	 * 技术支持审批记录列表
	 * 
	 * @return
	 */
	@RequestMapping("oa/os/mySupportApproveRecord")
	public String mySupportApproveRecord(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);
		List<ApplySupportApprove> list = this.osService.findSupportApproveList(loginUser.getId());
		for (ApplySupportApprove li : list) {
			li.setApplyName(userService.findById(li.getApplyId()).getName());
		}
		model.addAttribute("list", list);
		return "oa/os/supportApproveRecordList";
	}

	/**
	 * 文件下载
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping("oa/os/downloadFile")
	public void downloadFile(HttpSession session, Model model, HttpServletResponse response, String filename)
			throws FileNotFoundException {
		String fileName = filename.toString(); // 文件的默认保存名
		InputStream inStream = new FileInputStream(Consts.uploadFileRootLoc + filename);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		try {
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 技术支持申请记录详情
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("oa/os/supportApplyRecord2")
	public String supportApply2(HttpSession session, Model model, HttpServletResponse response, int id) {
		User loginUser = this.getLoginUser(session);
		ApplySupportApprove list = this.osService.findSupportApplyById(id);
		User user = userService.findById(list.getApplyId());
		list.setApplyName(user.getName());
		list.setDeptName(user.getDeptName());
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		model.addAttribute("loginUserId", loginUser.getId());
		// 技术主管审批记录
		List<SupportRecord> support = this.osService.findSupportById(list.getSupportId());
		model.addAttribute("support", support);
		return "oa/os/supportApplyRecord2";
	}

	/**
	 * 保存审批结果
	 * 
	 * @param session
	 * @param model
	 * @param response
	 * @param result
	 * @param opininon
	 * @return
	 */
	@RequestMapping("oa/os/saveApproveResult")
	public String saveApproveResult(HttpSession session, Model model, HttpServletResponse response, String result1,
			String opinion1, String submitCode, int id, Integer FAEId, Integer FAEId02, String result2, String opinion2,
			String cusResult, String cusOpinion, String finalResult, String finalOpinion) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/tecSupportApprove";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		ApplySupportApprove list = this.osService.findSupportApplyById(id);
		int status = list.getStatus();
		ApplySupportApprove asa = new ApplySupportApprove();
		ApplySupportApprove asa2 = new ApplySupportApprove();
		ApplySupportApprove asa3 = new ApplySupportApprove();
		int supportId = list.getSupportId();
		asa.setSupportId(supportId);
		String now = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		if (status == 1) {
			asa.setApproveOpinion01(opinion1);
			asa.setApproveDate01(now);
			asa.setApproveResult01(result1);
			if ("通过".equals(result1)) {
				asa.setCurrentId(list.getTecManagerId());
				asa.setStatus(2);
			} else {
				asa.setStatus(-1);
			}
		} else if (status == 2) {
			asa3.setCurrentStatus(0);
			asa3.setSupportId(supportId);
			this.osService.setSupportStatus(asa3);
			asa2.setSupportId(supportId);
			asa2.setApproveOpinion02(opinion2);
			asa2.setApproveDate02(now);
			asa2.setApproveResult02(result2);
			if ("通过".equals(result2)) {
				asa.setCurrentId(FAEId);
				asa.setCurrentId2(FAEId02);
				asa2.setFAEId(FAEId);
				asa2.setFAEId02(FAEId02);
				asa.setStatus(3);
				asa2.setCurrentStatus(1);
				// 再次审批的话，将currentStatus设为0，仅当前使用的为1
				/**
				 * 发邮件
				 */
				// 发送邮件给所有技术支持人员
				// 把审批人员加到List中
				List<User> users = new ArrayList<User>();
				users.add(this.userService.findById(FAEId));
				users.add(this.userService.findById(FAEId02));
				User sUser = this.userService.findById(list.getApplyId());
				
				for (User user : users) {
					String to = user.getEmail();
					String copyTo = null;
					if (Consts.devMode == 1) {
						to = "";
					}
					String fromName = sUser.getName();
					String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, user.getName(), fromName, sUser.getDeptName(), "技术支持申请", null, list);
					this.sendMailForSupsForSupport(to, copyTo, text);
				}
			} else {
				asa.setStatus(-1);
			}
			this.osService.saveApproveResult2(asa2);
		} else if (status == 4) {
			asa2.setSupportId(supportId);
			asa2.setCustomerResult(cusResult);
			asa2.setCustomerDate(now);
			asa2.setCustomerOpinion(cusOpinion);
			this.osService.updateSupportRecord(asa2);
			if ("已解决".equals(cusResult)) {
				asa.setStatus(5);
			} else {
				asa.setStatus(2);
			}
			asa.setCurrentId(list.getTecManagerId());
			/**
			 * 发邮件
			 */
			// 把审批人员加到List中
			List<User> users = new ArrayList<User>();
			users.add(this.userService.findById(list.getTecManagerId()));
			User loginUser = this.getLoginUser(session);
				for (User user : users) {
					String to = user.getEmail();
					String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, user.getName(), loginUser.getName(), loginUser.getDeptName(), "技术支持申请处理结果", null, list);
					this.sendMailForSupportResult(to, text);
				}
		} else if (status == 5) {
			asa2.setSupportId(supportId);
			asa2.setFinalResult(finalResult);
			asa2.setEndTime(now);
			asa2.setContent(finalOpinion);
			this.osService.insertSupportResult(asa2);
			asa.setStatus(6);
		} else {
			model.addAttribute("msg", "系统出错，请稍后再试");
			return "redirect:/web/oa/os/supportApproveList";
		}
		this.osService.saveApproveResult(asa);

		int status2 = asa.getStatus();
		User applyUser = userService.findById(list.getApplyId());
		String to = applyUser.getEmail();
		String copyTo = null;
		if (Consts.devMode == 1) {
			to = "";
		}
		String text = null;
		if (status2 == 6) {
			text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, applyUser.getName(), null, "技术支持申请", ApprovalStatus.APPLY_SUPPORT_HANDLED, list);
		} else if (status2 == -1) {
			text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, applyUser.getName(), null, "技术支持申请", ApprovalStatus.APPLY_SUPPORT_CANCELED, list);
		}
		supportApproveResult(to, copyTo, text);
		return "redirect:/web/oa/os/supportApproveList?msg=1";
	}

	@RequestMapping("oa/os/saveApproveResult2")
	public String saveApproveResult(HttpSession session, Model model, HttpServletResponse response, String submitCode,
			String tecOpinion, @RequestParam MultipartFile file2, int id, HttpServletRequest request) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/tecSupportApprove";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		ApplySupportApprove list = this.osService.findSupportApplyById(id);
		
		int status = list.getStatus();
		String msg = "操作成功";
		if (status == 3) {
			ApplySupportApprove asa = new ApplySupportApprove();
			ApplySupportApprove asa3 = new ApplySupportApprove();
			asa3.setFAEResult(tecOpinion);
			asa3.setFAETime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			if (file2.getSize() != 0) {
				String str = UploadUtil.uploadFile(file2, request);
				asa3.setFile2(str.split(",")[0]);
				msg = str.split(",")[1];
			}
			asa3.setSupportId(list.getSupportId());
			if ("操作成功".equals(msg)) {
				asa.setStatus(4);
				asa.setCurrentId(list.getApplyId());
				asa.setSupportId(list.getSupportId());
				this.osService.saveApproveResult(asa);
				this.osService.updateSupportRecord(asa3);
				/**
				 * 发邮件
				 */
				// 把审批人员加到List中
				List<User> users = new ArrayList<User>();
				users.add(this.userService.findById(list.getApplyId()));
				User loginUser = this.getLoginUser(session);
				for (User user : users) {
					String to = user.getEmail();
					if (Consts.devMode == 1) {
						to = "";
					}
					String receive = user.getName();
					String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, receive, loginUser.getName(), loginUser.getDeptName(), "技术支持申请处理结果", null, list);
					this.sendMailForSupportResult(to, text);
				}
			} else {
				model.addAttribute("msg", msg);
				return "redirect:/web/oa/os/supportApproveList";
			}
		} else {
			model.addAttribute("msg", "您没有权限");
			return "redirect:/web/oa/os/supportApproveList";
		}
		return "redirect:/web/oa/os/supportApproveList?msg=1";
	}

	@RequestMapping("oa/os/seach")
	public String seach(String begin, String end, Integer uType, Integer showType, String uid, String uname,
			HttpSession session, Model model, HttpServletResponse response) {

		User loginUser = this.getLoginUser(session);
		// 写死，如果是李雪
		if (loginUser.getId() == 13) {
			return "redirect:/web/oa/prop/mgr";
		}

		if (null == showType) {
			showType = 1;
		}

		if (StringUtils.isEmpty(begin)) {
			String yf = DateUtil.getCurrentTime("yyyy年MM月");
			begin = yf + "01日";
			end = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		}

		if (uType == null || uType == 0) {
			uid = "";
			uname = "";
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, null, showType, null);
			model.addAttribute("list", list);
		} else if (uType == 2) {
			uid = "";
			uname = "";
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, null, showType, 1);
			model.addAttribute("list", list);
		} else {
			uType = 1;
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, Integer.parseInt(uid), showType, null);
			model.addAttribute("list", list);
		}

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		if (null == uType) {
			uType = 0;
		}
		model.addAttribute("uType", uType);
		model.addAttribute("showType", showType);
		model.addAttribute("uid", uid);
		model.addAttribute("uname", uname);
		return "oa/os/search";
	}

	@RequestMapping("oa/os/exportConsume")
	public String exportConsume(String begin, String end, Integer uType, Integer showType, String uid, String uname,
			HttpSession session, Model model, HttpServletResponse response) {

		User loginUser = this.getLoginUser(session);

		// 写死，如果是李雪
		if (loginUser.getId() == 13) {
			return "redirect:/web/oa/przop/mgr";
		}

		if (null == showType) {
			showType = 1;
		}

		if (StringUtils.isEmpty(begin)) {
			String yf = DateUtil.getCurrentTime("yyyy年MM月");
			begin = yf + "01日";
			end = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		}

		if (uType == null || uType == 0) {
			uid = "";
			uname = "";
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, null, showType, null);
			File file = ExcelUtil.exportConsume(list);
			ExcelUtil.exportExcel(file, model, response);
		} else if (uType == 2) {
			uid = "";
			uname = "";
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, null, showType, 1);
			File file = ExcelUtil.exportConsume(list);
			ExcelUtil.exportExcel(list, file, model, response);
		} else {
			uType = 1;
			List<OsWupinVo> list = this.osService.findSqWpSum1(begin, end, Integer.parseInt(uid), showType, null);
			File file = ExcelUtil.exportConsume(list);
			ExcelUtil.exportExcel(list, file, model, response);
		}
		return "oa/os/search";
	}

	@RequestMapping("oa/os/exportPurchase")
	public String exportPurchase(String begin, String end, HttpSession session, Model model,
			HttpServletResponse response) {
		if (begin == null || begin.isEmpty() || end == null || end.isEmpty()) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/os/wpCgRecords";
		}
		ParamBean pb = new ParamBean();
		pb.setBegin(begin);
		pb.setEnd(end);
		List<PurchaseRecord> list = this.osService.findPurchaseAll(pb);
		File file = ExcelUtil.exportPurchase(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/os/wpCgRecords";
	}

	@RequestMapping("oa/os/exportStockRecord")
	public String exportStockRecord(HttpSession session, Model model, HttpServletResponse response) {
		List<OsWupin> list = this.osService.findStockAll();
		File file = ExcelUtil.exportStock(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/os/wpCgRecords";
	}

	@RequestMapping("oa/os/medSq")
	public String medSq(Integer[] ids, Integer[] nums, Integer type, String content, String submitCode,
			HttpSession session, Model model) {

		if (null == ids || ids.length == 0) {
			return "oa/os/medSq";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/medSq";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		if (null == type) {
			type = 0;
		}

		User loginUser = this.getLoginUser(session);
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);

		OsYaopinSq sq = new OsYaopinSq();

		sq.setBz(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setDayStr(dayStr);
		sq.setDeptId(loginUser.getDeptId());
		sq.setEmpId(loginUser.getId());
		sq.setEmpName(loginUser.getName());
		sq.setStatus(0);
		sq.setType(type);

		List<OsYaopinVo> yps = new ArrayList<OsYaopinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			OsYaopin oyp = this.osService.findYaopinById(ids[i]);
			Integer id = ids[i];
			OsYaopinVo vo = new OsYaopinVo();
			vo.setId(id);
			vo.setEmpId(sq.getEmpId());
			vo.setEmpName(sq.getEmpName());
			vo.setDayStr(dayStr);
			vo.setNum(nums[i]);
			vo.setDeptId(sq.getDeptId());
			vo.setSqType(type);
			vo.setName(oyp.getName());
			vo.setType(oyp.getType());
			vo.setUnit(oyp.getUnit());
			yps.add(vo);
		}

		this.osService.addMedSq(sq, yps);
		model.addAttribute("msg", "您的申领已经提交，请等待应急药品管理员通知");
		String table = MailTableUtil.createGetDrug(yps, 1, type);
		// 发送邮件给管理员
		List<User> gly = this.userService.findUserByRole("考勤管理员");
		for (User user : gly) {
			this.sendMailTable(user, loginUser, Consts.MAIL_DRUG_APPLY, table);
		}

		// 发送邮件给所有上级以及， 刘伟、刘江、崔莹莹
		List<Role> uRoles = this.getLoginUserRole(session);
		User sUser = loginUser;
		// 获取所有的上级
		List<User> sups = this.userService.findSuperiors(sUser, uRoles);

		for (User user : sups) {
			this.sendMailTable2(user, loginUser, Consts.MAIL_DRUG_APPLY, table);
		}

		return "oa/os/medSq";
	}

	@RequestMapping("oa/os/sq")
	public String sq(Integer[] ids, Integer[] nums, Integer type, String content, String submitCode,
			HttpSession session, Model model) {

		if (null == ids || ids.length == 0) {
			return "oa/os/sq";
		}
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/sq";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		if (type == null) {
			type = 0;
		}

		User loginUser = this.getLoginUser(session);
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);
		
		OsSq sq = new OsSq();
		sq.setBz(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setDayStr(dayStr);
		sq.setDeptId(loginUser.getDeptId());
		sq.setEmpId(loginUser.getId());
		sq.setEmpName(loginUser.getName());
		sq.setStatus(0);
		sq.setType(type);
		
		List<OsWupinVo> wps = new ArrayList<OsWupinVo>();
		List<OsYaopinVo> yps = new ArrayList<OsYaopinVo>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			OsWupinVo vo = new OsWupinVo();
			OsWupin oswp = this.osService.findWupinById(id);
			OsYaopinVo osyp = new OsYaopinVo();
			osyp.setName(oswp.getName());
			osyp.setType(oswp.getType());
			osyp.setNum(nums[i]);
			osyp.setUnit(oswp.getUnit());
			yps.add(osyp);
			vo.setId(id);
			vo.setEmpId(sq.getEmpId());
			vo.setEmpName(sq.getEmpName());
			vo.setDayStr(dayStr);
			vo.setNum(nums[i]);
			vo.setDeptId(sq.getDeptId());
			vo.setSqType(type);
			wps.add(vo);
		}
		this.osService.addSq(sq, wps);

		model.addAttribute("msg", "您的申领已经提交，请等待办公用品管理员通知");
		String table = MailTableUtil.createGetDrug(yps, 2, type);
		
		// TODO 发送邮件给管理员
		List<User> gly = this.userService.findUserByRole("办公用品管理员");
		for (User user : gly) {
			this.sendMailTable(user, loginUser, Consts.MAIL_WP_APPLY, table);
		}
		List<Role> uRoles = this.getLoginUserRole(session);
		User sUser = loginUser;
		List<User> sups = this.userService.findSuperiors(sUser, uRoles);
		for (User user : sups) {
			this.sendMailTable2(user, loginUser, Consts.MAIL_WP_APPLY, table);
		}
		return "oa/os/sq";
	}

	@RequestMapping("oa/os/applyNewArticle")
	public String applyNewArticle(String beginDate, String[] names, String[] brands, String[] artSizes, String[] units,
			String[] remarks, Integer[] nums, Integer type, String content, String submitCode, HttpSession session,
			Model model) {

		if (null == names || names.length == 0) {
			return "oa/os/applyNewArticle";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/os/applyNewArticle";
		}
		// session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		User loginUser = this.getLoginUser(session);
		int deptDirectorId = this.osService.getDeptDirectorIdByName(loginUser.getDeptName());
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);
		ApplyApprove sq = new ApplyApprove();
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setDayStr(dayStr);
		sq.setDeptId(loginUser.getDeptId());
		sq.setEmpId(loginUser.getId());
		sq.setEmpName(loginUser.getName());
		sq.setDeptDirectorId(deptDirectorId);
		sq.setAdminDirectorId(8);
		sq.setCurrentId(deptDirectorId);
		if (deptDirectorId == 8) {
			sq.setStatus(2);
		} else {
			sq.setStatus(1);
		}
		List<SpecialApply> wps = new ArrayList<SpecialApply>();
		int len = names.length;
		for (int i = 0; i < len; i++) {
			SpecialApply vo = new SpecialApply();
			vo.setName(names[i]);
			vo.setBrand(brands[i]);
			vo.setArtSize(artSizes[i]);
			vo.setNum(nums[i]);
			vo.setUnit(units[i]);
			vo.setRemark(remarks[i]);
			vo.setUseTime(beginDate);
			vo.setEmpId(sq.getEmpId());
			vo.setDayStr(dayStr);
			vo.setDeptId(sq.getDeptId());
			vo.setContent(content);
			wps.add(vo);
		}
		this.osService.addSpecialApply(sq, wps);

		model.addAttribute("msg", "您的申领已经提交，请等待办公用品管理员通知");
		// 发送邮件给管理员
		List<User> gly = this.userService.findUserByRole("考勤管理员");
		for (User user : gly) {
			String to = user.getEmail();
			String copyTo = null;
			if (Consts.devMode == 1) {
				to = "";
			}
			String fromName = loginUser.getName();
			// this.sendMailForSp(user.getName(), fromName, to, copyTo,
			// sb.toString(), sq.getType(), deptName);
		}

		// 发送邮件给所有上级以及， 刘伟、刘江、崔莹莹
		List<Role> uRoles = this.getLoginUserRole(session);
		User sUser = loginUser;
		// 获取所有的上级
		List<User> sups = this.userService.findSuperiors(sUser, uRoles);

		StringBuilder sb = new StringBuilder();

		sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>名称</th>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>品牌</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>规格尺寸</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>数量</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>单位</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>备注</th>" + "</tr>");
		for (SpecialApply vo : wps) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getBrand());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getArtSize());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getRemark());
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		for (User user : sups) {
			String to = user.getEmail();
			String copyTo = null;
			if (Consts.devMode == 1) {
				to = "";
			}
			String fromName = sUser.getName();
			this.sendMailForSups(user.getName(), fromName, to, copyTo, sb.toString(), 0, deptName);
		}

		return "oa/os/applyNewArticle";
	}

	private void sendMailForSups2(String toName, String fromName, String to, String copyTo, String table, Integer type,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("成功申领了下列物品");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司日耗）</span>");
		}
		sb.append("：</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "办公用品成功领取提醒", text);

	}

	/*private void sendMailForSupsForYp2(String toName, String fromName, String to, String copyTo, String table,
			Integer type, String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("成功申领了下列药品");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司消耗）</span>");
		}
		sb.append("：</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "应急药品成功领取提醒", text);

	}*/

	/**
	 * 发送人姓名，收件人姓名，发件人邮箱，抄送，内容，类型，部门名称
	 * 
	 * @param toName
	 * @param fromName
	 * @param to
	 * @param copyTo
	 * @param table
	 * @param type
	 * @param deptName
	 */
	private void sendMailForSups(String toName, String fromName, String to, String copyTo, String table, Integer type,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("提交的一个");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司日耗）</span>");
		}
		sb.append("办公用品申领<span style='color:red'>（仅作为通知，不需要您处理）</span>，内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "办公用品申领提醒", text);
	}

	/*private void sendMailForSupsForYp(String toName, String fromName, String to, String copyTo, String table,
			Integer type, String deptName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("提交的一个");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司消耗）</span>");
		}
		sb.append("应急药品申领<span style='color:red'>（仅作为通知，不需要您处理）</span>，内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "应急药品申领提醒", text);

	}*/

	private void sendMailForSupsForSilicon(String toName, String fromName, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("提交的一个");
		sb.append("芯片申请<span style='color:red'>（仅作为通知，不需要您处理）</span>，内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "芯片申请提醒", text);
	}

	private void sendMailForSupsForSupport(String to, String copyTo, String text) {
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "技术支持申请提醒", text);
	}

	/**
	 * 
	 * @param receive
	 *            收件人姓名
	 * @param send
	 *            发件人姓名
	 * @param to
	 *            收件人邮箱
	 * @param sendname
	 *            发件人部门
	 */
	private void sendMailForSupportResult(String to, String text) {
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "技术支持申请处理结果提醒", text);
	}

	private void sendMailForSp(String toName, String fromName, String to, String copyTo, String table, Integer type,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("提交的一个");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司日耗）</span>");
		}
		sb.append("办公用品申领<span style='color:red'>（仅作为通知，不需要您处理）</span>，内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "办公用品申领通知", text);
	}

	private void sendMailForYpSp(String toName, String fromName, String to, String copyTo, String deptName,
			String table) {

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("提交了一个应急药品申领，请及时处理。");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "应急药品申领通知", text);
	}

	private void sendMailForSiliconApprove(String toName, String fromName, String to, String copyTo, String deptName) {

		String text = "<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'>"
				+ "<p style='padding:5px; padding-bottom:0;'>您好，" + toName + "。</p>"
				+ "<p style='padding:15px 0 15px 0px;'>" + deptName + "<span style='color:red'>" + fromName
				+ "</span>提交了一个芯片申请，请及时处理。</p>"
				+ "<p style='padding-left:5px; color:#333;'>"
				+ "本邮件为OA系统提醒，请不要回复。</p></body></html>";
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "芯片申请通知", text);
	}

	private void sendSpMailOk(String toName, String to, String copyTo, String table, Integer type) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您成功申领了下列物品");
		if (type == 1) {
			sb.append("<span style='color:red'>（公司日耗）</span>");
		}
		sb.append("：</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "办公用品成功领取提醒", text);
	}

	private void siliconApproveResult(String toName, String fromName, String to, String copyTo, int status) {
		String text = null;
		if (status == 5) {
			text = "<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，"
					+ toName + "。</p>" + "<p style='padding:15px 0 15px 0px;'>您的芯片申请已经成功处理，请知晓。</p>"
					+ "<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
		}
		if (status == -1) {
			text = "<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，"
					+ toName + "。</p>" + "<p style='padding:15px 0 15px 0px;'>您的芯片申请已经取消，请知晓。</p>"
					+ "<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
		}
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "芯片申请处理结果", text);
	}

	private void supportApproveResult(String to, String copyTo, String text) {
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "技术支持申请处理结果", text);
	}

	private void sendSpMailOkForYp(String toName, String fromName, String to, String copyTo, List<OsYaopinVo> yps) {
		StringBuilder sb = new StringBuilder();

		String[] filePath = new String[yps.size()];
		String[] fileNames = new String[yps.size()];

		sb.append(
				"<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append(
				"<p style='padding:15px 0 0px 0px;'>您的一个应急药品申领已经成功处理，请知晓。<span style='color:red;'>（使用前请点击下列超链接并且仔细阅读药品详情）</span></p>");

		for (int i = 0; i < yps.size(); i++) {
			OsYaopinVo yp = yps.get(i);
			// filePath[i] = Consts.YaopinRootLoc + yp.getLoc();
			// fileNames[i] = yp.getName() + ".pdf";

			sb.append("<p style='padding:5px 0 15px 0px;'>");
			sb.append("<span style=\"font-weight:bold;\">药品名称：</span>").append(yp.getName()).append("（" + yp.getType())
					.append(")<br/>");
			if (Consts.devMode == 1) {
				sb.append("<span style=\"font-weight:bold;\">药品详情：</span>")
						.append("<a href='http://localhost/oa/web/oa/os/showMedPdf?id=").append(yp.getId())
						.append("' target='_blank'>http://localhost/oa/web/oa/os/showMedPdf?id=").append(yp.getId())
						.append("</a>");
			} else {
				// sb.append("<span style=\"font-weight:bold;\">药品详情：</span>"
				// ).append( "<a
				// href='http://www.macrosilicon.com:8080/web/oa/os/showMedPdf?id=").append(yp.getId()).append("'
				// target='_blank'>http://www.macrosilicon.com:8080/web/oa/os/showMedPdf?id=").append(yp.getId()).append("</a>");
			}
			sb.append("</p>");

		}
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "应急药品申领处理结果", text);
		// mailUtil.sendEMailWithFile(to, copyTo, Consts.defaultFrom,
		// "应急药品申领处理结果", text, filePath, fileNames);
		// mailUtil.sendEMailWithFile(to, copyTo, from, subject, content,
		// filePath);
		// sendEMailWithFile

	}

	private void sendSpMailCancel(String toName, String fromName, String to, String copyTo, String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您的一个办公用品申领被管理员取消,详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("如有疑问，请咨询其本人。</p>");
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "办公用品申领处理结果", text);

	}

	private void sendSpMailCancelForYp(String toName, String fromName, String to, String copyTo) {

		String text = "<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，"
				+ toName + "。</p>" + "<p style='padding:15px 0 15px 0px;'>您的一个应急药品申领被管理员" + fromName
				+ "取消，如有疑问，请咨询其本人。</p>"
				+ "<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";

		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "应急药品申领处理结果", text);

	}

	private String generalCgTableWithNoMoney(List<OsCgWupinVo> list) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>物品名称</th><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>规格型号</th><th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>数量</th><th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>单位</th><th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>单价</th></tr>");
		for (OsCgWupinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getMoney());
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	private String generalCgTable(List<OsCgWupinVo> list, int type, String date) {
		String type2 = type == 1 ? "物品" : "药品";
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>申请时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;' colspan='3'>"
				+ date + "</td><、tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>规格型号</th>");
		sb.append("<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>数量</th>");
		sb.append("<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>单位</th>");
		sb.append("<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>单价（元）</th>");
		sb.append(
				"<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'>金额小计（元）</th></tr>");
		double zjje = 0.0;
		for (OsCgWupinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney() * vo.getNum());
			sb.append("</td></tr>");
			zjje += vo.getNum() * vo.getMoney();
		}
		sb.append("<tr><td style='padding:3px 7px; border:1px solid #888; color:red;text-align:center;'>");
		sb.append("总计金额");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888; color:red;' colspan='5'>");
		sb.append("   " + zjje + "元");
		sb.append("</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	private String generalSpeApplyTable(List<SpecialApply> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>名称</th>"
				+ "<th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>品牌</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>规格尺寸</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>数量</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>单位</th>"
				+ "<th style='width:30px;text-align:left; padding:3px 7px; border:1px solid #888;'>备注</th></tr>");
		for (SpecialApply vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getBrand());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getArtSize());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getRemark());
			sb.append("</td></tr>");
		}
		sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;;'>");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888; '>");
		sb.append("</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	private String generalSyTable(List<OsCgWupinVo> list, int sqType) {
		String type = sqType == 1 ? "物品" : "药品";
		StringBuilder sb = new StringBuilder();

		sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr>"
				+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + type
				+ "名称</th><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
				+ "规格型号</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
				+ "数量</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
				+ "单位</th></tr>");
		for (OsCgWupinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getUnit());
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	private void sendMailForSySp(String toName, String fromName, String to, String copyTo, String table, Integer type,
			int syType, String content, String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";
		String typeName2 = type == 1 ? "物品" : "药品";

		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("向您提交的一个")
				.append(typeName).append("损益申请");

		sb.append("，申请").append(syType == 1 ? "增加" : "减少").append("下列" + typeName2 + "的库存。").append("</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();

		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, typeName + "损益审批", text);

	}

	private void sendMailForCgSp(String toName, String fromName, String to, String copyTo, String table, Integer type,
			String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";

		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName).append("向您提交的一个")
				.append(typeName).append("采购申请");

		sb.append("，请及时审批。采购内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();

		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, typeName + "采购审批", text);

	}

	private void sendMailForSyResutl1(String toName, String to, String table, Integer type, int syType,
			String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";
		String typeName2 = type == 1 ? "物品" : "药品";

		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您的的一个").append(typeName).append("损益申请");

		sb.append("通过了审批。您申请了").append(syType == 1 ? "增加" : "减少").append("了下列" + typeName2 + "的库存。</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();

		mailUtil.sendEMail(to, null, Consts.defaultFrom, typeName + "损益审批结果", text);

	}

	private void sendMailForSyResutl2(String toName, String to, String table, Integer type, int syType,
			String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";
		String typeName2 = type == 1 ? "物品" : "药品";

		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您的的一个").append(typeName).append("损益申请");

		sb.append("没有通过审批。您申请了").append(syType == 1 ? "增加" : "减少").append("了下列" + typeName2 + "的库存。</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();

		mailUtil.sendEMail(to, null, Consts.defaultFrom, typeName + "损益审批结果", text);

	}

	private void sendMailForCgResutl1(String toName, String to, String table, Integer type, String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您的一个").append(typeName).append("采购申请");
		sb.append("通过了审批，请及时处理。采购内容如下：</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, typeName + "采购审批结果", text);
	}

	private void sendMailForCgResutl2(String toName, String to, String table, Integer type, String deptName) {
		String typeName = type == 1 ? "办公用品" : "应急药品";

		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您的的一个").append(typeName).append("采购申请");

		sb.append("没有通过审批，请登录系统查看详情。采购内容如下：</p>");

		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();

		mailUtil.sendEMail(to, null, Consts.defaultFrom, typeName + "采购审批结果", text);
	}

	private void sendMaileResult2(User user, int status, String type, String table) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == 1 ? "<a style='color:green;'>审批通过</a>" : "<a style='color:red;'>审批取消</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您提交的").append(type).append(result);
		sb.append("，内容如下：</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}

	/****************************************
	 * MINE MAIL
	 ****************************************************/

	/**
	 * 物料申请信息详情
	 * 
	 * @param user收件人
	 * @param user2
	 *            发件人
	 * @param type
	 *            邮件类型
	 * @param table
	 */
	private void sendMailTable(User user, User user2, String type, String table) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("向您提交了一个")
				.append(type).append("，请及时登录系统审批。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}

	private void sendMailTable2(User user, User user2, String type, String table) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("提交了一个").append(type)
				.append("<span style='color:red;''>（仅作为通知，不需要您处理）</span>。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}

	private void sendMaileResult(User user, int status, String type, String table) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == -1 ? "<a style='color:green;'>未通过审批</a>" : "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type).append(result);
		sb.append("，请登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}

}
