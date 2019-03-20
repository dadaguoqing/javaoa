package com.hj.oa.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.oa.Consts;
import com.hj.oa.bean.DcCaidan;
import com.hj.oa.bean.DcCaidanVo;
import com.hj.oa.bean.DcEmpBalance;
import com.hj.oa.bean.DcEmpBalanceRecord;
import com.hj.oa.bean.DcEmpCaidan;
import com.hj.oa.bean.DcEmpDay;
import com.hj.oa.bean.DcProvider;
import com.hj.oa.bean.DingCan;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.DcService;
import com.hj.oa.service.UserService;
import com.hj.util.DateHelperUtil;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.FileUtil;
import com.hj.util.MailUtil;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;

/**
 * 订餐状态0-正在订餐，1-结束订餐、但是没有扣款，2-已经扣款
 * 
 * @author L
 *
 */
@Controller
public class DingCanController extends BaseController {

	@Autowired
	private DcService dcService;
	@Autowired
	private UserService userService;
	@Autowired
	private MailUtil mailUtil;

	private Timer curTimer;

	@RequestMapping("oa/dc/din_can_record/undefined/undefined")
	public String redirectBadFile() {
		return "redirect:/web/oa/dc/sDetail";
	}

	/**
	 * zeroth cat newest updates
	 */

	@RequestMapping("oa/dc/newpath/{date1}/undefined")
	public String redirectFirstBadDate() {
		return "redirect:/web/oa/dc/sDetail";
	}

	@RequestMapping("oa/dc/newpath/undefined/{date2}")
	public String redirectSecondBadDate() {
		return "redirect:/web/oa/dc/sDetail";
	}

	@RequestMapping("oa/dc/din_can_record/{date1}/undefined")
	public String redirectDinFirstBadDate() {
		return "redirect:/web/oa/dc/sDetail";
	}

	@RequestMapping("oa/dc/din_can_record/undefined/{date2}")
	public String redirectDinSecondBadDate() {
		return "redirect:/web/oa/dc/sDetail";
	}

	/**
	 * zeroth cat
	 * 
	 * @return
	 */
	@RequestMapping("oa/dc/newpath/undefined/undefined")
	public String redirectBadDate() {
		return "redirect:/web/oa/dc/sDetail";
	}

	/**
	 * zeroth cat
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/dc/endAutoDc")
	public String endAutoDingCan(String cdIds, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DcEmpDay empDc = this.dcService.findEmpDcByDay(loginUser.getId(), dayStr);
		String[] cds = cdIds.split(",");
		for (int i = 0; i < cds.length; i++) {
			this.dcService.removeEmpDcCaidan(empDc, Integer.parseInt(cds[i]));
		}
		this.dcService.endAuto(loginUser.getId());
		return "redirect:/web/oa/dc/dingcan";
	}

	/**
	 * zeroth_cat's mapping to something that is able to compute all the costs
	 * between date1 and date2 this function only considers currently months
	 * that are in the same year format is yyyy年mm月dd日
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/dc/newpath/{date1}/{date2}")
	public String newpath(Model model, @PathVariable String date1, @PathVariable String date2) throws ParseException {

		int total_cost_between_day1_day2 = 0;

		// parse the dates into year, month and day
		int year_1 = Integer.parseInt(date1.substring(0, 4));
		int month_1 = Integer.parseInt(date1.substring(5, 7));
		int day_1 = Integer.parseInt(date1.substring(8, 10));

		int year_2 = Integer.parseInt(date2.substring(0, 4));
		int month_2 = Integer.parseInt(date2.substring(5, 7));
		int day_2 = Integer.parseInt(date2.substring(8, 10));

		// if the first month is earlier than the second mont
		if (month_2 < month_1 && year_1 >= year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		// checks when the two months are equal but the first day is later than
		// the 2nd
		if (month_2 == month_1 && day_1 > day_2 && year_1 >= year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		if (year_1 > year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		String beginDate = year_1 + "/" + month_1 + "/" + day_1;
		String endDate = year_2 + "/" + month_2 + "/" + day_2;

		List<String> dateList = DateHelperUtil.getDates(beginDate, endDate);

		List<String> chineseDateList = DateHelperUtil.getChineseDates(dateList);

		// now we grab each pair and get the DingCan info for that day
		for (String curDay : chineseDateList) {

			DingCan dc = dcService.findDcByDay(curDay);

			if (dc != null) {
				List<DcCaidanVo> list = this.dcService.findDcDetail(dc.getId(), null);

				for (DcCaidanVo vo : list) {
					total_cost_between_day1_day2 += vo.getPriceAll();
				}
			}

		}

		model.addAttribute("total_cost", total_cost_between_day1_day2);
		model.addAttribute("zeroth_cat_message", "总金额");

		return "oa/dingcan/newpath";
	}

	/**
	 * zeroth cat's function to return all the cbRec as an excel
	 * 
	 * @param begin
	 *            begining of the time
	 * @param end
	 *            end of the time that we are checking
	 * @return
	 */
	@RequestMapping("oa/dc/cbRecExcel/{begin}/{end}")
	public String cbRecord(@PathVariable String begin, @PathVariable String end, HttpSession session,
			HttpServletResponse response) {

		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			Calendar c = Calendar.getInstance();
			Date e = c.getTime();

			c.add(Calendar.MONTH, -1);
			Date b = c.getTime();
			String fmt = Consts.chinaDateFmt;
			begin = DateUtil.getTimeString(b, fmt);
			end = DateUtil.getTimeString(e, fmt);
		}

		begin += " 00时00分";
		end += " 23时59分";

		List<DcEmpBalanceRecord> list = dcService.findBalanceRecord(begin, end);

		File rFile = ExcelUtil.cb_rec_excel(list, begin, end);

		// String filePath = "D:/cb_rec/";
		ServletUtil.downloadFile(response, rFile, rFile.getName(), "application/octet-stream");
		FileUtil.deleteDirectory("D:/cb_rec/");

		return "redirect:/web/oa/dc/cbRecord";
	}

	/**
	 * zeroth cat's function for dingcan records,it exports the dingcan record
	 * between date1 and date2
	 * 
	 * @param model
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("oa/dc/din_can_record/{date1}/{date2}")
	public String d_can_excel(Model model, @PathVariable String date1, @PathVariable String date2,
			HttpServletResponse response) throws ParseException {

		int year_1 = Integer.parseInt(date1.substring(0, 4));
		int month_1 = Integer.parseInt(date1.substring(5, 7));
		int day_1 = Integer.parseInt(date1.substring(8, 10));

		int year_2 = Integer.parseInt(date2.substring(0, 4));
		int month_2 = Integer.parseInt(date2.substring(5, 7));
		int day_2 = Integer.parseInt(date2.substring(8, 10));

		// if the first month is earlier than the second mont
		if (month_2 < month_1 && year_1 >= year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		// checks when the two months are equal but the first day is later than
		// the 2nd
		if (month_2 == month_1 && day_1 > day_2 && year_1 >= year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		if (year_1 > year_2) {
			return "redirect:/web/oa/dc/sDetail";
		}

		String beginDate = year_1 + "/" + month_1 + "/" + day_1;
		String endDate = year_2 + "/" + month_2 + "/" + day_2;

		List<String> dateList = DateHelperUtil.getDates(beginDate, endDate);

		List<String> chineseDateList = DateHelperUtil.getChineseDates(dateList);

		// now we grab each pair and get the DingCan info for that day
		List<DcCaidanVo> di_can_rec = new ArrayList<DcCaidanVo>();
		for (String curDay : chineseDateList) {
			DingCan dc = dcService.findDcByDay(curDay);
			if (dc != null) {
				List<DcCaidanVo> list = this.dcService.findDcDetail(dc.getId(), null);
				for (DcCaidanVo cur_di_can : list) {
					di_can_rec.add(cur_di_can);
				}
			}

		}
		// 倒序输出list
		Collections.reverse(di_can_rec);
		File rFile = ExcelUtil.di_can_rec(di_can_rec, date1, date2);
		// D:/dian_can_rec/

		// String filePath = "D:/cb_rec/";
		ServletUtil.downloadFile(response, rFile, rFile.getName(), "application/octet-stream");
		FileUtil.deleteDirectory("D:/dian_can_rec/");

		return "redirect:/web/oa/dc/sDetail";
	}

	/**
	 * zeroth_cat helps to generate the excel for employee balances, currently
	 * stored in D/emp_chong_zhi/{date}
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/dc/get_balance")
	public String getting_balance(Model model, HttpServletResponse response) {
		List<DcEmpBalance> balances = dcService.findAllBalance();
		File rFile = ExcelUtil.chong_zhi_excel(balances);
		// "D:/emp_chong_zhi"

		// String filePath = "D:/cb_rec/";
		ServletUtil.downloadFile(response, rFile, rFile.getName(), "application/octet-stream");
		FileUtil.deleteDirectory("D:/emp_chong_zhi/");

		return "redirect:/web/oa/dc/cBalance";
	}

	/**
	 * zeroth cat
	 * 
	 * @param empDcId
	 * @param cdId
	 * @param num
	 * @param des
	 * @param prov
	 * @param pri
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/dc/autoDatabaseInsert")
	public String autoDataInsert(Integer empDcId, String cdId, String num, String des, String prov, String pri,
			HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
		int userId = loginUser.getId();

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DingCan dc = this.dcService.findDcByDay(dayStr);

		// this gets all of the cdid of today.
//		int TodayDcId = dc.getId();
//		List<DcCaidan> temp = this.dcService.findCaidanByDcId(TodayDcId);

		if (dc.getStatus() != 0) {// 不是正在订餐
			return "redirect:/web/oa/dc/dingcan";
		}

		String[] providers = prov.split(",");
		String[] prices = pri.split(",");
		String[] descriptions = des.split(",");

		DcEmpDay empDc = this.dcService.findEmpDcById(empDcId);
		List<DcEmpCaidan> cds = this.dcService.addTodayOrder(cdId, num, empDcId, empDc);
		this.dcService.empDingcai(cds, empDc);

		DcEmpBalance dcb = new DcEmpBalance();
		dcb.setUserId(userId);
		dcb.setPrice(Integer.parseInt(prices[0]));
		dcb.setProvider(providers[0]);
		dcb.setMenuItem(descriptions[0]);
		this.dcService.insertAuto(dcb);

		return "redirect:/web/oa/dc/autoDC";// "oa/dingcan/autoDataInsert";
	}

	/**
	 * zeroth cat
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/dc/autoDC")
	public String autoDingCan(Model model, HttpSession session) {

		User loginUser = this.getLoginUser(session);

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);

		DingCan dcs = this.dcService.findDcByDay(dayStr);
		model.addAttribute("dc", dcs);

		// 这个地方要修改，可能某一天会有多次订餐。（目前确定，每天就一个午餐）
		DcEmpDay empDc = this.dcService.findEmpDcByDay(loginUser.getId(), dayStr);
		model.addAttribute("empDc", empDc);

		if (empDc != null && dcs.getStatus() == 0) {// 正在订餐
			List<DcCaidan> cds = this.dcService.findCaidanByDcId(empDc.getDcId());
			model.addAttribute("cds", cds);
		}

		// 如果订餐结束了，显示我定的什么菜
		if (empDc != null && empDc.getStatus() == 1) { // 订餐结束
			// List<DcCaidan> cds =
			// this.dcService.findCaidanByDcId(empDc.getDcId());

			List<DcEmpCaidan> empCaidans = this.dcService.findEmpDcCaidanByDcId(empDc.getDcId(), empDc.getId());
			if (null != empCaidans && empCaidans.size() != 0) {
				model.addAttribute("empCaidans", empCaidans);
				DcEmpCaidan total = new DcEmpCaidan();
				total.setProviderName("总计");
				int num = 0;
				int price = 0;
				for (DcEmpCaidan ecd : empCaidans) {
					num += ecd.getNum();
					price += ecd.getPrice();
				}
				total.setNum(num);
				total.setPrice(price);
				model.addAttribute("total", total);
			}
		}

		return "redirect:/web/oa/dc/dingcan";
	}

	@RequestMapping("oa/dc/showDetail")
	public String showDetail(String dayStr, HttpSession session, Model model) {

		try {
			dayStr = new String(dayStr.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		User loginUser = this.getLoginUser(session);
		DcEmpDay empDc = this.dcService.findEmpDcByDay(loginUser.getId(), dayStr);

		if (empDc == null) {
			return "oa/dingcan/showDetail";
		}

		model.addAttribute("empDc", empDc);

		List<DcEmpCaidan> empCaidans = this.dcService.findEmpDcCaidanByDcId(empDc.getDcId(), empDc.getId());
		if (null != empCaidans && empCaidans.size() != 0) {
			model.addAttribute("empCaidans", empCaidans);
			DcEmpCaidan total = new DcEmpCaidan();
			total.setProviderName("总计");
			int num = 0;
			int price = 0;
			for (DcEmpCaidan ecd : empCaidans) {
				num += ecd.getNum();
				price += ecd.getPrice();
			}
			total.setNum(num);
			total.setPrice(price);
			model.addAttribute("total", total);
		}
		// List<DcCaidan> cds =
		// this.dcService.findCaidanByDcId(empDc.getDcId());

		return "oa/dingcan/showDetail";
	}

	@RequestMapping("oa/dc/dingcan")
	public String dingcan(HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DcEmpBalance tempBal = this.dcService.findBalanceByEmpId(loginUser.getId());
		

		DingCan dcs = this.dcService.findDcByDay(dayStr);
		model.addAttribute("dc", dcs);

		// 这个地方要修改，可能某一天会有多次订餐。（目前确定，每天就一个午餐）
		DcEmpDay empDc = this.dcService.findEmpDcByDay(loginUser.getId(), dayStr);
		model.addAttribute("empDc", empDc);

		if (empDc != null && dcs.getStatus() == 0) {// 正在订餐
			List<DcCaidan> cds = this.dcService.findCaidanByDcId(empDc.getDcId());
			model.addAttribute("cds", cds);
			if (tempBal.getAuto() == 1) {
				// 查今日订餐菜单
				String today = DateUtil.getCurrentTime(Consts.chinaDateFmt);
				List<DcCaidan> orders = this.dcService.findCaidanByDate(today);
				// TODO 比较发布的订餐是否包含自动订餐
				List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
				for (DcCaidan order : orders) {
					Map<String, Integer> map = new HashMap<String, Integer>();
					map.put(order.getProviderName() + order.getDescription(), order.getPrice());
					list.add(map);
				}
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(tempBal.getProvider() + tempBal.getMenuItem(), tempBal.getPrice());

				if (list.contains(map)) {
					model.addAttribute("AutoStatus", 1);
				} else {
					model.addAttribute("AutoStatus", 0);
					model.addAttribute("msg", "您自动订餐套餐今日没有提供,请重新订餐");
				}
			} else {
				model.addAttribute("AutoStatus", 0);
			}

		}

		// 如果订餐结束了，显示我定的什么菜
		if (empDc != null && empDc.getStatus() == 1) { // 订餐结束
			// List<DcCaidan> cds =
			// this.dcService.findCaidanByDcId(empDc.getDcId());

			List<DcEmpCaidan> empCaidans = this.dcService.findEmpDcCaidanByDcId(empDc.getDcId(), empDc.getId());
			if (null != empCaidans && empCaidans.size() != 0) {
				model.addAttribute("empCaidans", empCaidans);
				DcEmpCaidan total = new DcEmpCaidan();
				total.setProviderName("总计");
				int num = 0;
				int price = 0;
				String cdIds = "";
				for (DcEmpCaidan ecd : empCaidans) {
					cdIds += ecd.getCdId() + ",";
					num += ecd.getNum();
					price += ecd.getPrice();
				}
				model.addAttribute("cdIds", cdIds);
				total.setNum(num);
				total.setPrice(price);
				model.addAttribute("total", total);
			}
		}

		return "oa/dingcan/dingcan";
	}

	@RequestMapping("oa/dc/cancelCd")
	public String cancelCd(Integer empId, Integer cdId, Integer num, HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);

		if (empId == null) {
			empId = loginUser.getId();
		}

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DingCan dc = this.dcService.findDcByDay(dayStr);
		DcEmpDay empDc = this.dcService.findEmpDcByDay(empId, dayStr);
		// DingCan dc = this.dcService.findDcById(empDc.getDcId());
		// DcCaidan cd = this.dcService.findCaidanById(cdId);

		if (dc.getStatus() != 0) { // 不是正在订餐
			/*
			 * 客户订餐 if(empId ==0){ return
			 * "redirect:/web/oa/dc/khDingcan?cancelDc=0"; }
			 */

			return "redirect:/web/oa/dc/dingcan?cancelDc=0";

		}

		this.dcService.removeEmpDcCaidan(empDc, cdId);

		/*
		 * 客户订餐 if(empId ==0){ return "redirect:/web/oa/dc/khDingcan"; }
		 */
		return "redirect:/web/oa/dc/dingcan";

	}

	@RequestMapping("oa/dc/cancelOrder")
	public String cancelOrder(Integer empDcId, Integer cdId, Integer id) {
		DcEmpDay empDc = this.dcService.findEmpDcById(empDcId);
		this.dcService.removeEmpDcCaidan(empDc, cdId);
		return "redirect:/web/oa/dc/mgr";
	}

	// 管理员为用户取消订餐
	@RequestMapping("oa/dc/cancelEmpCd")
	public String cancelEmpCd(Integer empId, Integer cdId, Integer dcId, Integer num, HttpSession session,
			Model model) {
//		User loginUser = this.getLoginUser(session);

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);

		DingCan dc = this.dcService.findDcByDay(dayStr);
		DcEmpDay empDc = this.dcService.findEmpDcByDay(empId, dayStr);

		if (dc.getStatus() != 0) { // 不是正在订餐

			return "redirect:/web/oa/dc/cDetail?dcId=" + dcId + "&cancelDc=0";

		}

		this.dcService.removeEmpDcCaidan(empDc, cdId);

		return "redirect:/web/oa/dc/cDetail?dcId=" + dcId + "&cancelDc=1";

	}

	@RequestMapping("oa/dc/empDc")
	public String empDc(Integer empDcId, String cdId, String num, HttpSession session, Model model) {

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DingCan dc = this.dcService.findDcByDay(dayStr);

		DcEmpDay empDc = this.dcService.findEmpDcById(empDcId);

		if (dc.getStatus() != 0) {// 不是正在订餐
			return "redirect:/web/oa/dc/dingcan";
		}
		// 添加今天订餐信息
		List<DcEmpCaidan> cds = this.dcService.addTodayOrder(cdId, num, empDcId, empDc);

		// empDc.setPrice(price);
		// empDc.setStatus(1);

		this.dcService.empDingcai(cds, empDc);

		/*
		 * 客户订餐 if(empDc.getEmpId() == 0){ return
		 * "redirect:/web/oa/dc/khDingcan"; }
		 */

		return "redirect:/web/oa/dc/dingcan";
	}

	@RequestMapping("oa/dc/empCancelDc")
	public String empCancelDc(Integer empDcId, HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);

		DcEmpDay empDc = this.dcService.findEmpDcById(empDcId);
		if (empDc.getStatus() != 0 || empDc.getEmpId() != loginUser.getId()) {// 不是正在订餐
			return "redirect:/web/oa/dc/dingcan";
		}
		empDc.setStatus(1);

		this.dcService.empCancelDingcai(empDc);

		return "redirect:/web/oa/dc/dingcan";
	}

	// 管理员取消订餐
	@RequestMapping("oa/dc/cancelDc")
	public String cancelDc(Integer dcId, HttpSession session, Model model) {
//		User loginUser = this.getLoginUser(session);

		DingCan dc = this.dcService.findDcById(dcId);

		if (dc.getStatus() != 1 && dc.getStatus() != 0) {// 不是正在订餐 且不是订餐结束
			return "redirect:/web/oa/dc/mgr";
		}

		if (curTimer != null) {
			curTimer.cancel();
			curTimer.purge();
			curTimer = null;
		}

		this.dcService.delDc(dcId);
		// model.addAttribute("dc", dc);

		// return "redirect:/web/oa/dc/mgr";
		return "redirect:/web/oa/dc/mgr?msg=-1";

		/*
		 * DcEmpDay empDc = this.dcService.findEmpDcById(empDcId);
		 * if(empDc.getStatus() != 0 || empDc.getEmpId() !=
		 * loginUser.getId()){//不是正在订餐 return "redirect:/web/oa/dc/dingcan"; }
		 * empDc.setStatus(1);
		 * 
		 * this.dcService.empCancelDingcai(empDc);
		 * 
		 * return "redirect:/web/oa/dc/dingcan";
		 */
	}

	@RequestMapping("oa/dc/mgr")
	public String mgr(HttpSession session, Model model) {

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DingCan dc = this.dcService.findDcByDay(dayStr);
		model.addAttribute("dc", dc);

		if (dc != null) {
			List<DcCaidanVo> list = dcService.findHuizongByDcId(dc.getId());

			model.addAttribute("list", list);
			DcCaidanVo total = new DcCaidanVo();
			int num = 0;
			int priceAll = 0;
			for (DcCaidanVo vo : list) {
				num += vo.getNum();
				priceAll += vo.getPriceAll();
			}
			total.setNum(num);
			total.setPriceAll(priceAll);
			model.addAttribute("total", total);
		}

		return "oa/dingcan/mgr";
	}

	// 订餐详情页面
	@RequestMapping("oa/dc/cDetail")
	public String cDetail(HttpSession session, Model model,Integer id) {
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		DingCan dc = this.dcService.findDcByDay(dayStr);
		if (dc != null) {
			List<DcCaidanVo> list = dcService.findTotalByDcId(dc.getId(),id);
			model.addAttribute("list", list);
		}
		DingCan dd = new DingCan();
		dd.setStatus(0);
		dd.setDayStr(dayStr);
		List<DingCan> orders = this.dcService.findOrderStatus(dd);
		model.addAttribute("order", this.dcService.findEmpName(orders));
		DingCan df = new DingCan();
		df.setStatus(0);
		df.setDayStr(dayStr);
		List<DingCan> autoOrders = this.dcService.findAutoOrder(1);
		model.addAttribute("autoOrder", this.dcService.findEmpNameByEmpId(autoOrders));
		return "oa/dingcan/dcDetail";
	}

	@RequestMapping("oa/dc/sDetail")
	public String sDetail(String yf, HttpSession session, Model model) {

		if (StringUtils.isEmpty(yf)) {
			yf = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		}

		DingCan dc = dcService.findDcByDay(yf);

		if (dc != null) {
			List<DcCaidanVo> list = this.dcService.findDcDetail(dc.getId(), null);
			model.addAttribute("list", list);
			model.addAttribute("dc", dc);

			DcCaidanVo total = new DcCaidanVo();
			int num = 0;
			int priceAll = 0;
			for (DcCaidanVo vo : list) {
				num += vo.getNum();
				priceAll += vo.getPriceAll();
			}
			total.setNum(num);
			total.setPriceAll(priceAll);
			model.addAttribute("total", total);
		}
		model.addAttribute("yf", yf);

		return "oa/dingcan/searchDetail";
	}

	// 结束订餐
	@RequestMapping("oa/dc/endDc")
	public String endDc(Integer id, HttpSession session, Model model) {

		// User loginUser = this.getLoginUser(session);
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);

		if (curTimer != null) {
			curTimer.cancel();
			curTimer.purge();
			curTimer = null;
		}

		DingCan dc = this.dcService.findDcById(id);
		// TODO 订餐结束看不到 未订餐用户
		dc.setStatus(1);
		dc.setId(id);
		// List<DcEmpDay> eds = this.dcService.findEmpDcByDcId(dc.getId());
		// this.dcService.updateDingcan(dc, eds);
		this.dcService.updateStatus(dc);

		return "redirect:/web/oa/dc/mgr";
	}

	// 添加订餐界面
	@RequestMapping("oa/dc/adds")
	public String addDingcan(HttpSession session, Model model) {
		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("providers", providers);
		return "oa/dingcan/add";
	}

	/**
	 * zeroth cat
	 * 
	 * @param providerId
	 * @param providerName
	 * @param price
	 * @param description
	 * @param endHour
	 * @param endMin
	 * @param submitCode
	 * @param session
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("oa/dc/addDc")
	public String addDc(Integer[] providerId, String[] providerName, String[] price, String[] description,
			String endHour, String endMin, String submitCode, HttpSession session, Model model) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			// model.addAttribute("msg","操作失败，您正在重复提交数据。");
			return "redirect:/web/oa/dc/adds";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);// 获取系统当前日期
		DingCan dcs = this.dcService.findDcByDay(dayStr);

		if (dcs != null) { // 今天已经订餐的

			List<DcProvider> providers = dcService.findAllProvider();
			model.addAttribute("providers", providers);
			model.addAttribute("msg", "添加失败，今天已经发布订餐");
			return "oa/dingcan/add";
		}

		// DcProvider prov = this.dcService.findProviderById(provider);
		User loginUser = this.getLoginUser(session);
		List<User> allUsers = userService.findAllUsers();

		DingCan dc = new DingCan();
		dc.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		dc.setDayStr(dayStr);
		dc.setEmpId(loginUser.getId());
		dc.setEmpName(loginUser.getName());
		dc.setEndTime(dayStr + " " + endHour + "时" + endMin + "分");

		int len = price.length;
		if (len < 1) {
			model.addAttribute("msg", "发布失败，请至少添加一个菜单");
			return "oa/dingcan/add";
		}

		List<DcCaidan> cds = new ArrayList<DcCaidan>();
		for (int i = 0; i < len; i++) {
			DcCaidan cd = new DcCaidan();
			cd.setDescription(description[i]);
			cd.setPrice(Integer.parseInt(price[i]));
			cd.setProviderId(providerId[i]);
			cd.setProviderName(providerName[i]);
			// cd.set
			cds.add(cd);
		}
		dcService.addDc(dc, cds, allUsers);
		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("providers", providers);
		model.addAttribute("msg", "发布订餐成功");

		// zeroth cat
		// this is where zeroth cat's code for automation shall begin

		loginUser = this.getLoginUser(session);

		dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		dc = this.dcService.findDcByDay(dayStr);

		// this gets all of the cdid of today.
		int TodayDcId = dc.getId();
		List<DcCaidan> temp = this.dcService.findCaidanByDcId(TodayDcId);

		// start writing the automation code
		List<DcEmpBalance> balances = this.dcService.findAllBalance();
		// for each balances we have
		for (DcEmpBalance curBal : balances) {
			DcEmpBalance tempBal = this.dcService.findBalanceByEmpId(curBal.getEmpId());
			if (tempBal.getAuto() == 1) {
				DcEmpDay eDc = this.dcService.findEmpDcByDay(curBal.getEmpId(), dayStr);

				for (DcCaidan nowCai : temp) {
					if (nowCai.getDescription().equals(tempBal.getMenuItem())) {
						if (nowCai.getPrice() == tempBal.getPrice()) {

							int empdcId = this.dcService.findEmpDcIdByEmpIDandDcId(eDc.getEmpId(), TodayDcId);

							List<DcEmpCaidan> cds_second = new ArrayList<DcEmpCaidan>();

							int cdIdInt = nowCai.getId();
							int numInt = 1;

//							DcCaidan cd = this.dcService.findCaidanById(cdIdInt);

							int price_second = nowCai.getPrice();
							DcEmpCaidan caidan = new DcEmpCaidan();
							caidan.setCdId(cdIdInt);
							caidan.setNum(numInt);
							caidan.setEmpDcId(empdcId); // get empdcid
							caidan.setDcId(eDc.getDcId());
							caidan.setPrice(price_second);
							cds_second.add(caidan);

							this.dcService.AutoEmpDingcai(cds_second, eDc);

						}
					}
				}

			}

		}
		// end writing the automation code

		String timeAutoStart = dayStr + " " + endHour + "时" + endMin + "分";
		try {
			curTimer = this.dcService.autoDC(timeAutoStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "oa/dingcan/add";
	}

	@RequestMapping("oa/dc/showProvider")
	public String showProvider(Integer id, HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);

		DcProvider p = dcService.findProviderById(id);
		model.addAttribute("p", p);

		return "oa/dingcan/showProvider";
	}

	@RequestMapping("oa/dc/editProvider")
	public String editProvider(DcProvider provider, HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);

		this.dcService.updateProvider(provider);

		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("list", providers);
		model.addAttribute("msg", "操作成功");

		return "oa/dingcan/providers";
	}

	@RequestMapping("oa/dc/listProviders")
	public String listProviders(HttpSession session, Model model) {
		// User loginUser = this.getLoginUser(session);

		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("list", providers);

		return "oa/dingcan/providers";
	}

	// 订餐充值记录
	@RequestMapping("oa/dc/cbRecord")
	public String cbRecord(HttpSession session, String begin, String end, Model model) { // 日期必选
		// User loginUser = this.getLoginUser(session);

		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			Calendar c = Calendar.getInstance();
			Date e = c.getTime();

			c.add(Calendar.MONTH, -1);
			Date b = c.getTime();
			String fmt = Consts.chinaDateFmt;
			begin = DateUtil.getTimeString(b, fmt);
			end = DateUtil.getTimeString(e, fmt);
		}
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		begin += " 00时00分";
		end += " 23时59分";

		List<DcEmpBalanceRecord> list = dcService.findBalanceRecord(begin, end);

		model.addAttribute("list", list);

		return "oa/dingcan/cbRecord";
	}

	@RequestMapping("oa/dc/addProvider")
	public String addProviders(DcProvider provider, String submitCode, HttpSession session, Model model) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			model.addAttribute("msg", "操作失败，您正在重复提交数据。");
			return "redirect:/web/oa/dc/listProviders";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		dcService.addProvider(provider);

		model.addAttribute("msg", "操作成功");
		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("list", providers);

		return "oa/dingcan/providers";
	}

	@RequestMapping("oa/dc/ajaxMenus")
	public String ajaxMenus(Integer pId, String submitCode, HttpSession session, Model model) {
		if (null == pId) {
			pId = 0;
		}
		model.addAttribute("pId", pId);

		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("providers", providers);

		List<DcCaidan> menus = null;// this.dcService.findAllMenu();
		if (0 == pId) {
			menus = this.dcService.findAllMenu();
		} else {
			menus = this.dcService.findMenuByProvider(pId);
		}

		model.addAttribute("menus", menus);

		return "oa/dingcan/ajaxMenus";
	}

	@RequestMapping("oa/dc/delMenu")
	public String delMenu(Integer id, Integer pId, String submitCode, HttpSession session, Model model) {
		if (null == pId) {
			pId = 0;
		}
		model.addAttribute("pId", pId);

		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("providers", providers);

		dcService.deleteMenu(id);

		List<DcCaidan> menus = null;// this.dcService.findAllMenu();
		if (0 == pId) {
			menus = this.dcService.findAllMenu();
		} else {
			menus = this.dcService.findMenuByProvider(pId);
		}

		model.addAttribute("menus", menus);

		return "oa/dingcan/menus";
	}

	@RequestMapping("oa/dc/addMenu")
	public String addMenu(DcCaidan caidan, Integer pId, String submitCode, HttpSession session, Model model) {

		if (null == pId) {
			pId = 0;
		}

		model.addAttribute("pId", pId);

		List<DcProvider> providers = dcService.findAllProvider();
		model.addAttribute("providers", providers);

		if (caidan == null || StringUtils.isEmpty(caidan.getDescription())) {

			List<DcCaidan> menus = null;// this.dcService.findAllMenu();
			if (0 == pId) {
				menus = this.dcService.findAllMenu();
			} else {
				menus = this.dcService.findMenuByProvider(pId);
			}

			model.addAttribute("menus", menus);

			return "oa/dingcan/menus";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交

			List<DcCaidan> menus = null;// this.dcService.findAllMenu();
			if (0 == pId) {
				menus = this.dcService.findAllMenu();
			} else {
				menus = this.dcService.findMenuByProvider(pId);
			}

			model.addAttribute("menus", menus);

			return "oa/dingcan/menus";
		}
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		DcProvider provider = this.dcService.findProviderById(caidan.getProviderId());
		caidan.setProviderName(provider.getName());

		dcService.addMenu(caidan);

		List<DcCaidan> menus = null;// this.dcService.findAllMenu();
		if (0 == pId) {
			menus = this.dcService.findAllMenu();
		} else {
			menus = this.dcService.findMenuByProvider(pId);
		}
		model.addAttribute("menus", menus);

		model.addAttribute("msg", "操作成功");

		return "oa/dingcan/menus";
	}

	@RequestMapping("oa/dc/myRecord")
	public String myRecord(String yf, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);

		if (StringUtils.isEmpty(yf)) {
			yf = DateUtil.getCurrentTime("yyyy年MM月");
		}

		String begin = yf + "01日";
		String end = yf + "" + 31 + "日";

		// List<DcCaidanVo> list =
		// this.dcService.findDcDetailByEmp(loginUser.getId(), begin, end);

		List<DcEmpBalanceRecord> list = dcService.findBalanceRecordByEmpId(loginUser.getId(), begin, end + " 23时59分");
		DcEmpBalance balance = dcService.findBalanceByEmpId(loginUser.getId());
		model.addAttribute("list", list);
		model.addAttribute("yf", yf);
		model.addAttribute("balance", balance);

		return "oa/dingcan/myRecord";
	}

	@RequestMapping("oa/dc/cBalance")
	public String cBalance(HttpSession session, Model model) {
		List<DcEmpBalance> balances = dcService.findAllBalance();
		model.addAttribute("users", balances);

		return "oa/dingcan/updateBalance";
	}

	// 为订餐扣款
	@RequestMapping("oa/dc/kcDc")
	public String koukuang(Integer id, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
		List<Role> roles = this.getLoginUserRole(session);

		if (!RoleUtil.hasRole(roles, "订餐管理员")) {
			return "redirect:/web/oa/index";
		}

		DingCan dc = this.dcService.findDcById(id);
		int dcStatus = dc.getStatus();

		if (dcStatus != 1) {// 当前订餐的状态必须是 （1-结束订餐)
			return "redirect:/web/oa/dc/mgr";
		}

		List<DcEmpDay> empDays = dcService.findEmpDcByDcId(dc.getId());
		List<DcEmpBalance> balances = dcService.findAllBalance();

		Map<Integer, DcEmpBalance> bMap = new HashMap<Integer, DcEmpBalance>();

		for (DcEmpBalance b : balances) {
			bMap.put(b.getEmpId(), b);
		}

		List<DcEmpBalance> updateBalance = new ArrayList<DcEmpBalance>();
		List<DcEmpBalanceRecord> addRecords = new ArrayList<DcEmpBalanceRecord>();

		/*
		 * String dayTime = dc.getDayStr() + " 12时00分"
		 * ;//DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		 * if(DateUtil.getCurrentTime(Consts.chinaDateFmt).equals(dc.getDayStr()
		 * )){ dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt); }
		 */

		String dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		String dayStr = dc.getDayStr();// dayTime.substring(0,11);
		String bz = null;
		if (!DateUtil.getCurrentTime(Consts.chinaDateFmt).equals(dc.getDayStr())) {
			// dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
			bz = "扣除" + dayStr + "订餐餐费";
		}

		for (DcEmpDay empDay : empDays) {
			int money = empDay.getPrice(); // 用户订餐总额必须计算正确
			if (money == 0) { // 没有订餐、或者不需要扣款
				continue;
			}

			DcEmpBalance balance = bMap.get(empDay.getEmpId());
			DcEmpBalanceRecord record = new DcEmpBalanceRecord();
			double yue = balance.getBalance() - money;

			balance.setBalance(yue);

			record.setBalance(yue);
			record.setMoney(money);
			record.setType("订餐扣除");
			record.setEmpId(balance.getEmpId());
			record.setDayTime(dayTime);
			record.setDayStr(dayStr);
			record.setBz(bz);

			updateBalance.add(balance);
			addRecords.add(record);
		}

		dcService.updateEmpBalance(updateBalance, addRecords, dc);

		// 给所有扣款用户发送扣款邮件
		List<User> allUsers = this.userService.findAllUsers();
		HashMap<Integer, User> uMap = new HashMap<Integer, User>();
		for (User user : allUsers) {
			uMap.put(user.getId(), user);
		}
		sendKkMail(uMap, addRecords, loginUser.getName());

		return "redirect:/web/oa/dc/mgr?msg=2";
	}

	/**
	 * zeroth cat
	 * 
	 * @param empId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/dc/managerRemoveAuto")
	public String managerRemoveAuto(Integer empId, HttpSession session, Model model) {

		List<Role> roles = this.getLoginUserRole(session);

		if (!RoleUtil.hasRole(roles, "考勤管理员")) {
			return "redirect:/web/oa/index";
		}

		this.dcService.endAuto(empId);

		return "redirect:/web/oa/dc/cBalance";
	}

	@RequestMapping("oa/dc/updateBalance")
	public String updateBalance(Integer empId, Double money, String bz, String type, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
//		List<Role> roles = this.getLoginUserRole(session);

		DcEmpBalance balance = dcService.findBalanceByEmpId(empId);
		DcEmpBalanceRecord record = new DcEmpBalanceRecord();
		double yue = 0.0;

		if ("充值".equals(type)) {
			yue = balance.getBalance() + money;
		} else if ("扣款".equals(type)) {
			yue = balance.getBalance() - money;
		} else {
			throw new RuntimeException("类型错误");
		}
		balance.setBalance(yue);

		record.setBalance(yue);
		record.setMoney(money);
		record.setBz(bz);
		record.setType(type);
		record.setEmpId(balance.getEmpId());
		String dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		record.setDayTime(dayTime);
		String dayStr = dayTime.substring(0, 11);
		record.setDayStr(dayStr);

		dcService.updateEmpBalance(balance, record);

		List<DcEmpBalance> balances = dcService.findAllBalance();
		model.addAttribute("users", balances);
		model.addAttribute("msg", "操作成功");

		// 发送邮件通知用户
		User toUser = userService.findById(balance.getEmpId());
		String to = toUser.getEmail();
		this.sendBalanceMail(toUser.getName(), loginUser.getName(), type, money, yue, to, null);

		return "oa/dingcan/updateBalance";
	}

	// 测试方法
	@RequestMapping("oa/dc/initEmpBalance")
	@ResponseBody
	public HashMap<String, String> huizong(HttpSession session, String kqDay, String sysDay, Model model) {

		List<User> users = this.userService.findAllUsers();
		List<DcEmpBalance> tmpBalance = this.dcService.findTempBalance();

		HashMap<String, Double> bMap = new HashMap<String, Double>();
		for (DcEmpBalance b : tmpBalance) {
			bMap.put(b.getEmpName(), b.getBalance());
		}

		List<DcEmpBalance> balances = new ArrayList<DcEmpBalance>();

		for (User u : users) {
			DcEmpBalance balance = new DcEmpBalance();
			Double bal = bMap.get(u.getName());
			if (null == bal) {
				bal = 0.0;
			}
			balance.setBalance(bal);
			balance.setEmpId(u.getId());
			balances.add(balance);
		}

		// this.dcService.addEmpBalances(balances);
		this.dcService.updateEmpBalance(balances);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("result", "ok");
		return map;
	}

	private void sendBalanceMail(String toName, String fromName, String bType, double money, double yue, String to,
			String copyTo) {

		String text = "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，"
				+ toName + "。</p>" + "<p style='padding:15px 0 15px 0px;'>订餐管理员" + fromName + "为您的账户" + bType + money
				+ "元。目前您的账户余额" + yue + "元。如有疑问，请联系" + fromName + "。</p>"
				+ "<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";

		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "订餐账户充值（或扣款）通知", text);

	}

	private void sendKkMail(HashMap<Integer, User> uMap, List<DcEmpBalanceRecord> addRecords, String fromName) {

		for (DcEmpBalanceRecord r : addRecords) {
			int uid = r.getEmpId();
			User user = uMap.get(uid);
			String to = user.getEmail();
			sendDckkMail(user.getName(), fromName, r.getMoney(), r.getBalance(), to, r.getDayStr());
		}

	}

	// 订餐扣款邮件
	private void sendDckkMail(String toName, String fromName, double money, double yue, String to, String dayStr) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，");
		sb.append(toName);
		sb.append("。</p>");
		sb.append("<p style='padding:15px 0 15px 0px;'>您的订餐账户因为").append(dayStr).append("订餐扣除").append(money)
				.append("元。目前您的账户余额").append(yue).append("元。如有疑问，请联系");
		sb.append(fromName).append("。</p>");
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "订餐账户充值（或扣款）通知", text);

	}

	/*
	 * 客户订餐
	 * 
	 * @RequestMapping("oa/dc/khDingcan") public String khDingcan(HttpSession
	 * session, Model model){
	 * 
	 * //User loginUser = this.getLoginUser(session); String dayStr =
	 * DateUtil.getCurrentTime(Consts.chinaDateFmt);
	 * 
	 * DingCan dcs = this.dcService.findDcByDay(dayStr);
	 * model.addAttribute("dc", dcs);
	 * 
	 * DcEmpDay empDc = this.dcService.findEmpDcByDay(0, dayStr);
	 * model.addAttribute("empDc", empDc);
	 * 
	 * if(empDc!= null && dcs.getStatus()==0){//正在订餐 List<DcCaidan> cds =
	 * this.dcService.findCaidanByDcId(empDc.getDcId());
	 * model.addAttribute("cds", cds); }
	 * 
	 * //如果订餐结束了，显示我定的什么菜 if(empDc!= null && empDc.getStatus()==1){ //订餐结束
	 * //List<DcCaidan> cds = this.dcService.findCaidanByDcId(empDc.getDcId());
	 * List<DcEmpCaidan> empCaidans =
	 * this.dcService.findEmpDcCaidanByDcId(empDc.getDcId(), empDc.getId());
	 * if(null != empCaidans && empCaidans.size()!=0){
	 * model.addAttribute("empCaidans", empCaidans); DcEmpCaidan total = new
	 * DcEmpCaidan(); total.setProviderName("总计"); int num = 0; int price = 0;
	 * for(DcEmpCaidan ecd: empCaidans){ num += ecd.getNum(); price+=
	 * ecd.getPrice(); } total.setNum(num); total.setPrice(price);
	 * model.addAttribute("total", total); } }
	 * 
	 * return "oa/dingcan/khDingcan"; }
	 */
}
