package com.hj.oa.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hj.commons.DbStatus;
import com.hj.commons.MateriaConstants;
import com.hj.oa.Consts;
import com.hj.oa.bean.Seal;
import com.hj.oa.bean.SealApply;
import com.hj.oa.bean.SealApplyDetail;
import com.hj.oa.bean.SealApprove;
import com.hj.oa.bean.SealDate;
import com.hj.oa.bean.SealElse;
import com.hj.oa.bean.SealType;
import com.hj.oa.bean.User;
import com.hj.oa.service.MateriaService_v2;
import com.hj.oa.service.SealService;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.ServletUtil;

@Controller
@RequestMapping("oa")
public class SealController extends BaseController {

	@Autowired
	SealService sealService;
	@Autowired
	MateriaService_v2 maService;
	@Autowired
	UserService userService;

	/**
	 * 印章信息管理
	 * 
	 * @return
	 */
	@RequestMapping("seal/manage")
	public String manage(Model model) {
		List<Seal> seals = this.sealService.selectAllSeal();
		model.addAttribute("list", seals);
		return "oa/seal/manage";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 */
	@RequestMapping("seal/addSeal")
	public String addSeal() {
		return "oa/seal/addSeal";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 */
	@RequestMapping("seal/updateSeal")
	public String updateSeal(Integer id, Model model) {
		Seal seal = this.sealService.selectSealById(id);
		model.addAttribute("seal", seal);
		return "oa/seal/updateSeal";
	}

	/**
	 * 添加印章信息
	 * 
	 * @return
	 */
	@RequestMapping("seal/add")
	public String add(Seal seal, String submitCode, HttpSession session, Model model) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		try {
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			if (seal != null) {
				this.sealService.addSeal(seal);
			} else {
				throw new Exception("数据读取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/manage";
		}
		return "redirect:/web/oa/seal/manage?msg=1";
	}

	@RequestMapping("seal/update")
	public String updateSeal(Seal seal, String submitCode, HttpSession session, Model model) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		try {
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
			if (seal != null) {
				this.sealService.updateSeal(seal);
			} else {
				throw new Exception("数据读取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/manage";
		}
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/seal/manage";
	}

	@RequestMapping("seal/delSeal")
	public String delSeal(Integer id, Model model) {
		try {
			if (id != null) {
				Seal seal = new Seal();
				seal.setId(id);
				seal.setStatus(-1);
				this.sealService.updateSeal(seal);
			} else {
				throw new Exception("数据读取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/manage";
		}
		model.addAttribute("msg", Consts.SUCCESS);
		return "oa/seal/manage";
	}

	@RequestMapping("seal/applySeal")
	public String applySeal(HttpSession session, Model model) {
		try {
			User user = this.getLoginUser(session);
			Integer access = this.sealService.getSealAccess(user.getId());
			model.addAttribute("access", access);
			// 用章日期
			List<String> dates = this.sealService.countSealDate();
			model.addAttribute("dates", dates);
			List<String> dates2 = this.sealService.countSealDate2();
			model.addAttribute("dates2", dates2);
			// 用章时间
			SealDate sd = this.sealService.findSealDateById(8);
			model.addAttribute("sd", sd);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/seal/applySeal";
	}

	/**
	 * 获取编码
	 */
	@RequestMapping("seal/getPurchaseCode")
	@ResponseBody
	public String getPurchaseCode(String str, Integer type) {
		return this.maService.getPurchaseCode(str, type);
	}

	/**
	 * 获取用章类型
	 */
	@RequestMapping("seal/getSealType")
	@ResponseBody
	public List<SealType> getSealType() {
		return this.sealService.selectAllSealType();
	}

	/**
	 * 获取印章信息
	 */
	@RequestMapping("seal/getSealCompany")
	@ResponseBody
	public List<Seal> getSealCompany() {
		return this.sealService.selectAllSeal();
	}

	/**
	 * 获取印章信息
	 */
	@RequestMapping("seal/getSealPtr")
	@ResponseBody
	public List<User> getSealPtr(HttpSession session) {
		User user = this.getLoginUser(session);
		List<Integer> deptIds = this.sealService.findMyDepts(user.getId());
		List<User> lists = new ArrayList<User>();
		for(Integer deptId : deptIds) {
			List<User> list = this.userService.findByDept(deptId);
			lists.addAll(list);
		}
		return lists;
	}

	/**
	 * 保存印章申请信息
	 * 
	 * @return
	 */
	@RequestMapping("seal/saveSealApply")
	public String saveSealApply(Model model, SealApply sa, HttpSession session, HttpServletRequest req,
			String submitCode, String startDate2, String endDate2, String wdrStr) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		try {
			if (!sc.equals(submitCode)) {// 重复提交
				throw new Exception("请勿重复提交");
			}
			// 外带用章时间限制
			if(sa.getSealType() == 3) {
				SealDate sd = this.sealService.findSealDateById(10);
				Integer min = Integer.parseInt(sd.getWeekday());
				Date date = new Date();
				date.setTime(date.getTime() + min * 60 * 1000);
				String yzDateStr = startDate2.substring(0, 11) + " " + endDate2.substring(0, 6);
				Date date2 = DateUtil.stringToDate(yzDateStr, Consts.chinaDateTimeFmt);
				if(date.after(date2)) {
					model.addAttribute("msg", "外带用章时间至少提前" + min + "分钟申请！");
					return "oa/seal/applySeal";
				}
			}
			session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
				if (sa.getSealType() == 3) {
					sa.setStartDate(startDate2);
					sa.setEndDate(endDate2);
					sa.setWdr(wdrStr);
				}
				String number = this.getPurchaseCode("YZ", 7);
				sa.setNumber(number);
				User user = this.getLoginUser(session);
				this.sealService.saveSealApply(user, sa, req);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/applySeal";
		}
		return "redirect:/web/oa/seal/applySeal?msg1=1";
	}

	/**
	 * 申请记录
	 * 
	 * @return
	 */
	@RequestMapping("seal/sealList")
	public String myApplyList(Model model, HttpSession session) {
		User user = this.getLoginUser(session);
		List<SealApply> sas = this.sealService.findMyApply(user.getId());
		model.addAttribute("list", sas);
		model.addAttribute("type", 1);
		return "oa/seal/sealList";
	}

	/**
	 * 申请详情
	 * 
	 * @return
	 */
	@RequestMapping("seal/sealDetail")
	public String sealDetail(Model model, HttpSession session, Integer id, Integer type) {
		SealApply sa = this.sealService.findSealApplyById(id);
		model.addAttribute("app", sa);
		List<SealApplyDetail> lists = this.sealService.findSealDetail(sa.getNumber());
		List<SealApprove> sap = this.sealService.findApproveByNumber(sa.getNumber());
		for (SealApprove sealp : sap) {
			model.addAttribute("approve" + sealp.getApproveStatus(), sealp);
		}
		model.addAttribute("list", lists);
		model.addAttribute("type", type);
		List<SealElse> ptrs = this.sealService.getEealElseByType(MateriaConstants.SEAL_PTR);
		model.addAttribute("ptrs", ptrs);
		return "oa/seal/sealDetail";
	}

	/**
	 * 审批列表
	 * 
	 * @return
	 */
	@RequestMapping("seal/approveList")
	public String approveList(Model model, HttpSession session) {
		User user = this.getLoginUser(session);
		List<SealApply> sas = this.sealService.findMyApprove(user.getId());
		model.addAttribute("list", sas);
		model.addAttribute("type", 2);
		return "oa/seal/sealList";
	}

	/**
	 * 审批列表
	 * 
	 * @return
	 */
	@RequestMapping("seal/approveRecord")
	public String approveRecord(Model model) {
		List<SealApply> sas = this.sealService.findAllApply();
		model.addAttribute("list", sas);
		model.addAttribute("type", 3);
		return "oa/seal/sealList";
	}

	/**
	 * 审批处理
	 * 
	 * @return
	 */
	@RequestMapping("seal/saveApprove")
	public String saveApprove(String sp, String opinion, Integer id, String ptr,Model model) {
		try {
			this.sealService.saveApprove(sp, opinion, id, ptr);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/approveList";
		}
		return "redirect:/web/oa/seal/approveList?msg=1";
	}

	@RequestMapping("seal/sealDeal")
	public String sealDeal(Model model) {
		List<SealApplyDetail> sads = this.sealService.findSealDeal(1);
		Map<String, String> map = new HashMap<String, String>();
		JSONArray arr = new JSONArray();
		for (SealApplyDetail sad : sads) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", this.userService.findById(sad.getEmpId()).getName());
			obj1.put("sealType", sad.getSealType() == 1 ? "内部用章" : "外带用章");
			obj1.put("id", sad.getId());
			obj1.put("number", sad.getNumber());
			obj1.put("fileName", sad.getFileName());
			obj1.put("sealCompany", sad.getSealCompany());
			obj1.put("sealName", sad.getSealName());
			obj1.put("fileNum", sad.getFileNum());
			obj1.put("fileDetail", sad.getFileDetail() == null ? "" : sad.getFileDetail());
			obj1.put("userNum", sad.getUserNum());
			obj1.put("loaction", sad.getLoaction());
			obj1.put("content", sad.getContent());
			obj1.put("date", sad.getYzDate());
			obj1.put("time", sad.getEndDate());
			arr.add(obj1);
			map.put(sad.getYzDate(), "date");
		}
		model.addAttribute("list", arr);
		model.addAttribute("type", 4);
		model.addAttribute("map", map);
		return "oa/seal/sealDeal";
	}

	// @RequestMapping("seal/sealDealAjax")
	// @ResponseBody
	// public JSONObject sealDealAjax(Model model) {
	// JSONObject obj = new JSONObject();
	// List<SealApplyDetail> sads = this.sealService.findSealDeal();
	// JSONArray arr = new JSONArray();
	// for(SealApplyDetail sad : sads) {
	// JSONObject obj1 = new JSONObject();
	// obj1.put("id", sad.getId());
	// obj1.put("number", sad.getNumber());
	// obj1.put("fileName", sad.getFileName());
	// obj1.put("sealCompany", sad.getSealCompany());
	// obj1.put("sealName", sad.getSealName());
	// obj1.put("fileNum", sad.getFileNum());
	// obj1.put("userNum", sad.getUserNum());
	// obj1.put("loaction", sad.getLoaction());
	// obj1.put("content", sad.getContent());
	// arr.add(obj1);
	// }
	// obj.put("msg","");
	// obj.put("code", 0);
	// obj.put("count", sads.size());
	// obj.put("data", arr);
	// System.out.println(obj.toString());
	// return obj;
	// }

	// @RequestMapping("seal/sealDealRecord")
	// public String sealDeal2(Model model) {
	// List<SealApply> list = this.sealService.findSealDeal(-1);
	// model.addAttribute("list", list);
	// model.addAttribute("type", 5);
	// return "oa/seal/sealDeal";
	// }

	@RequestMapping("seal/dealSealApply")
	public String dealSealApply(Integer[] checkBoxs, Model model) {
		try {
			for (Integer id : checkBoxs) {
				this.sealService.updateById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/sealDeal";
		}
		return "redirect:/web/oa/seal/sealDeal?msg=1";
	}

	@RequestMapping("seal/sealDate")
	public String sealDate(Model model) {
		try {
			List<SealDate> sds = this.sealService.findSealDate();
			model.addAttribute("list", sds);
			SealDate sd = this.sealService.findSealDateById(8);
			model.addAttribute("date", sd);
			SealDate sd2 = this.sealService.findSealDateById(9);
			model.addAttribute("date2", sd2);
			SealDate sd3 = this.sealService.findSealDateById(10);
			model.addAttribute("date3", sd3);
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		return "oa/seal/sealDate";
	}

	/**
	 * 修改内部用章可申领日期
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("seal/dealSealDate")
	public String dealSealDate(Integer id, Integer status) {
		try {
			SealDate sd = new SealDate();
			sd.setId(id);
			sd.setStatus(status);
			this.sealService.updateSealDateBySelect(sd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/seal/sealDate";
	}

	/**
	 * 修改内部用章申请时间
	 * 
	 * @param bHour
	 * @param bMin
	 * @param eHour
	 * @param eMin
	 * @return
	 */
	@RequestMapping("seal/dealSealTime")
	public String dealSealTime(String bHour, String bMin, String eHour, String eMin) {
		try {
			SealDate sd = new SealDate();
			sd.setId(8);
			sd.setWeekday(bHour + bMin + "--" + eHour + eMin);
			this.sealService.updateSealDateBySelect(sd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/seal/sealDate";
	}
	
	@RequestMapping("seal/dealWdTime")
	public String dealWdTime(String jzsj2) {
		try {
			SealDate sd = new SealDate();
			sd.setId(10);
			sd.setWeekday(jzsj2);
			this.sealService.updateSealDateBySelect(sd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/seal/sealDate";
	}
	
	
	@RequestMapping("seal/dealSealTime2")
	public String dealSealTime(String jzsj) {
		try {
			SealDate sd = new SealDate();
			sd.setId(9);
			sd.setWeekday(jzsj);
			this.sealService.updateSealDateBySelect(sd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/web/oa/seal/sealDate";
	}

	@RequestMapping("seal/exportRecord")
	public String exportRepairtRecord(HttpSession session, Model model, HttpServletResponse response,
			String startDate2) {
		List<SealApplyDetail> sads = this.sealService.findSealDeal(1);
		List<SealApplyDetail> list = new ArrayList<SealApplyDetail>();
		for (SealApplyDetail sad : sads) {
			if (startDate2.isEmpty() || startDate2.equals(sad.getYzDate().substring(0, 11))) {
				sad.setName(this.userService.findById(sad.getEmpId()).getName());
				sad.setSealTypeStr(sad.getSealType() == 1 ? "内部用章" : "外带用章");
				list.add(sad);
			}
		}
		try {
			if (!list.isEmpty()) {
				File file = ExcelUtil.exportSealRecord(list);
				model.addAttribute("file", file);
				ServletUtil.downloadFile(response, file, file.getName());
			} else {
				throw new Exception("查询结果为空,请刷新后重试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/sealDeal";
		}
		return "redirect:/web/oa/seal/sealDeal";
	}

	@RequestMapping("seal/getYzsy")
	@ResponseBody
	public List<SealElse> getYzsy() {
		return this.sealService.getYzsy();
	}

	@RequestMapping("seal/getYyc")
	@ResponseBody
	public List<SealElse> getYyc() {
		return this.sealService.getYyc();
	}
	
	@RequestMapping("seal/deleteSealElse")
	public String deleteSealElse(Integer id,Integer status,Model model) {
		try {
			SealElse se = new SealElse();
			se.setId(id);
			se.setStatus(status);
			this.sealService.updateSealElse(se);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/sealManage";
		}
		return "redirect:/web/oa/seal/sealManage?msg=1";
	}
	
	@RequestMapping("seal/addSealElse")
	public String addSealElse(Integer type,String content,Model model,String yzsy,String yyc,String ptr) {
		try {
			SealElse se = new SealElse();
			if(type == 1) {
				se.setContent(yzsy);
				se.setType(MateriaConstants.SEAL_YZSY);
			}
			if(type == 2) {
				se.setContent(yyc);
				se.setType(MateriaConstants.SEAL_YYC);
			}
			if(type == 3) {
				se.setContent(ptr);
				se.setType(MateriaConstants.SEAL_PTR);
			}
			this.sealService.insertSealElse(se);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/sealManage";
		}
		return "redirect:/web/oa/seal/sealManage?msg=1";
	}
	
	/**
	 * 用章事由
	 * @return
	 */
	@RequestMapping("seal/sealManage")
	public String sealManage(Model model) {
		List<SealElse> yzsys = this.sealService.getEealElseByType(MateriaConstants.SEAL_YZSY);
		List<SealElse> yycs = this.sealService.getEealElseByType(MateriaConstants.SEAL_YYC);
		List<SealElse> ptrs = this.sealService.getEealElseByType(MateriaConstants.SEAL_PTR);
		model.addAttribute("yzsys", yzsys);
		model.addAttribute("yycs", yycs);
		model.addAttribute("ptrs", ptrs);
		return "oa/seal/sealManage";
	}
	
	/**
	 * @Title: cancleApply   
	 * @Description: 撤销申请
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "seal/cancleApply" ,method = RequestMethod.POST)
	public String cancleApply(Integer id,Model model) {
		try {
			SealApply sa = new SealApply();
			sa.setId(id);
			sa.setStatus(DbStatus.FREEZE.getType());
			this.sealService.updateSealApplyBySelect(sa);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/seal/sealList";
		}
		return "redirect:/web/oa/seal/sealList?msg=1";
	}

}
