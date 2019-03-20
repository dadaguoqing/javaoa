package com.hj.oa.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.hj.ke.bean.CheckInFromKs;
import com.hj.ke.bean.JsonResult;
import com.hj.oa.Consts;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.CheckInRemote;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.EmpDay;
import com.hj.oa.bean.EmpDaySum;
import com.hj.oa.bean.EmpDayVo;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.JiaBan;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.JiaBanService;
import com.hj.oa.service.KqService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.OAUtilService;
import com.hj.oa.service.UserService;
import com.hj.util.CheckInUtil;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.FileUtil;
import com.hj.util.HttpUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;
import com.hj.util.StringtoByteUtil;
import com.hj.util.TaskUtil;

@Controller
public class KqController extends BaseController {

	@Autowired
	private DateService dateService;
	@Autowired
	private KqService kqService;
	@Autowired
	private CheckInService cSer;
	@Autowired
	private UserService userSer;
	@Autowired
	private LeaveService leaveSer;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private TaskUtil taskUtil;

	@Autowired
	private JiaBanService jiabanService;

	@Autowired
	private OAUtilService oaUtilService;

	/*
	 * //处理刘江，刘伟，冀玉东的历史打卡。
	 * 
	 * @RequestMapping("oa/kq/llj")
	 * 
	 * @ResponseBody public HashMap<String,String> llj(Model model){
	 * 
	 * try{ int empId = 1;//刘伟 //int empId = 2;//刘江 //int empId = 3;//冀玉东
	 * 
	 * List<CheckIn> cis = new ArrayList<CheckIn>();//this.cSer.findByEmp(1);
	 * 
	 * List<EmpDayVo> empDays = this.kqService.findByEmpAll(empId);
	 * 
	 * for(EmpDayVo ed : empDays){
	 * 
	 * if(ed.getUncLen()>0){ CheckIn ci = new CheckIn(); String checkin =
	 * ed.getCheckin(); String checkout = ed.getCheckout();
	 * 
	 * if(StringUtils.isEmpty(checkin) || "08:30".compareTo(checkin)<0){ checkin
	 * = "08:30"; }
	 * 
	 * if(StringUtils.isEmpty(checkout) || "17:30".compareTo(checkout)>0){
	 * checkout = "17:30"; } ci.setCheckin(checkin); ci.setCheckout(checkout);
	 * ci.setDayStr(ed.getDayStr()); ci.setEmpId(empId);
	 * ci.setCheckinInt(OtherUtil.time2Int(checkin));
	 * ci.setCheckoutInt(OtherUtil.time2Int(checkout));
	 * 
	 * cis.add(ci); } } this.cSer.addEmpCheckIns(cis); }catch(Exception e){
	 * throw new RuntimeException(e); }
	 * 
	 * HashMap<String,String> map = new HashMap<String,String>(); map.put("ret",
	 * "ok"); return map; }
	 */

	// 处理销售部
	@RequestMapping("oa/kq/sxb")
	@ResponseBody
	public HashMap<String, String> llj(Integer empId, HttpSession session, Model model) {

		try {
			/*
			 * User loginUser = this.getLoginUser(session); if(loginUser.getId()
			 * != Consts.managerId){ throw new RuntimeException("您没有权限"); }
			 */

			// int empId = 51;//王书涛
			// int empId = 53;//杨晓晨

			List<CheckIn> cis = new ArrayList<CheckIn>();// this.cSer.findByEmp(1);
			// List<EmpDayVo> empDays = this.kqService.findByEmpAll(empId);
			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			Calendar c = Calendar.getInstance();
			c.set(2014, Calendar.APRIL, 1);
			int month = c.get(Calendar.MONTH);
			int date = c.get(Calendar.DATE);

			SimpleDateFormat sdf = new SimpleDateFormat(Consts.chinaDateFmt);
			while (!(month == 11 && date == 27)) {

				Date d = c.getTime();
				String dayStr = sdf.format(d);

				Day dsy = this.dateService.findByDayStrChina(dayStr);

				CheckIn ci = new CheckIn();
				ci.setDayStr(dayStr);
				ci.setEmpId(empId);
				if (dsy.getType().equals(Consts.nameOfWorkDay)) {

					String checkin = "08:30";
					String checkout = "17:30";

					ci.setCheckin(checkin);
					ci.setCheckout(checkout);
					ci.setCheckinInt(OtherUtil.time2Int(checkin));
					ci.setCheckoutInt(OtherUtil.time2Int(checkout));
				}

				EmpDaySum eds = new EmpDaySum();
				eds.setDayStr(dayStr);
				eds.setDkLen(0);
				eds.setEmpId(empId);

				cis.add(ci);
				empDays.add(eds);

				c.add(Calendar.DATE, 1);
				month = c.get(Calendar.MONTH);
				date = c.get(Calendar.DATE);
			}
			this.cSer.addEmpCheckInsForXs(cis, empDays);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ret", "ok");
		return map;
	}

	@RequestMapping("oa/kq/exportRetroactiveRecord")
	public String exportRetroactiveRecord(int style, String beginDate, String endDate, Model model,
			HttpServletResponse response) {
		if (style == 0) {
			model.addAttribute("msg", "请选择补签类型！");
			return "oa/kq/mysq";
		}
		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			model.addAttribute("msg", "请选择日期！");
			return "oa/kq/mysq";
		}
		ParamBean pb = new ParamBean();
		pb.setStyle(style);
		pb.setBegin(beginDate);
		pb.setEnd(endDate);
		List<BKqSp> list = this.kqService.findRetroactiveRecord(pb);
		for (BKqSp li : list) {
			if (li.getStatus() == -1) {
				li.setStatusName("审批不通过");
			} else if (li.getStatus() == 1) {
				li.setStatusName("审批通过");
			}
			int styleName = Integer.parseInt(li.getStyle());
			if (styleName == 1) {
				li.setStyle("迟到打卡补签");
			} else if (styleName == 2) {
				li.setStyle("忘记打卡补签");
			} else if (styleName == 3) {
				li.setStyle("公事出差打卡补签");
			} else if (styleName == 4) {
				li.setStyle("工牌丢失打卡补签");
			} else if (styleName == 5) {
				li.setStyle("先研院授课打卡补签");
			} else if (styleName == 6) {
				li.setStyle("公事外出打卡补签");
			}
		}
		File file = ExcelUtil.retroactiveRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/kq/mysq";
	}

	/** 自动导入考勤（用于初始化历史数据） */
	@RequestMapping("oa/kq/autoImport")
	@ResponseBody
	public HashMap<String, String> autoImportKq(Model model) {
		String dakaLoc = "D:\\nfs\\打卡2";
		// String qingjiaLoc = "D:\\nfs\\外出";
		// String jiabanLoc = "D:\\nfs\\加班";
		try {
			List<User> users = this.userSer.findAllUsers();
			Map<String, User> uMap = new HashMap<String, User>();
			for (User u : users) {
				uMap.put(u.getName(), u);
			}

			// 打卡写入
			File dakaDir = new File(dakaLoc);
			File[] dakaFiles = dakaDir.listFiles();
			List<File> list = new ArrayList<File>();
			for (File dakaXls : dakaFiles) {
				String fileName = dakaXls.getName();
				if (FileUtil.getFileExtName(fileName).equalsIgnoreCase(".xls")) {
					list.add(dakaXls);
				}
			}
			this.cSer.importEmpCheckIns(list, uMap);
			/* */

			/*
			 * //请假写入 File qinajiaDir = new File(qingjiaLoc); File[]
			 * qingjiaFiles = qinajiaDir.listFiles(); List<File> list = new
			 * ArrayList<File>(); for(File dakaXls: qingjiaFiles){ String
			 * fileName = dakaXls.getName();
			 * if(FileUtil.getFileExtName(fileName).equalsIgnoreCase(".xls")){
			 * list.add(dakaXls); } } leaveSer.importLeaves(list, uMap);
			 */

			/*
			 * //外出写入 File qinajiaDir = new File(qingjiaLoc); File[]
			 * qingjiaFiles = qinajiaDir.listFiles(); List<File> list = new
			 * ArrayList<File>(); for(File dakaXls: qingjiaFiles){ String
			 * fileName = dakaXls.getName();
			 * if(FileUtil.getFileExtName(fileName).equalsIgnoreCase(".xls")){
			 * list.add(dakaXls); } } leaveSer.importWaichu(list, uMap);
			 */

			/*
			 * //加班导入 File jiabanDir = new File(jiabanLoc); File[] jiabanFiles =
			 * jiabanDir.listFiles(); List<File> list = new ArrayList<File>();
			 * for(File jiabanXls: jiabanFiles){ String fileName =
			 * jiabanXls.getName();
			 * if(FileUtil.getFileExtName(fileName).equalsIgnoreCase(".xls")){
			 * list.add(jiabanXls); } } this.jiabanService.importJiaban(list,
			 * uMap);
			 */
			/**/
			// this.jiabanService.addJiaban(list);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ret", "ok");
		return map;
	}

	// 导入考勤（历史数据）
	@RequestMapping("oa/kq/import")
	public String importKq(String type, Integer empId,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session, Model model) {

		List<User> users = this.userSer.findAllUsers();
		model.addAttribute("users", users);

		if (StringUtils.isEmpty(type)) {
			return "oa/kq/import";
		}

		try {
			if ("1".equals(type)) {// 打卡
				List<CheckIn> list = ExcelUtil.getFromFile(empId, file.getInputStream());
				this.cSer.addEmpCheckIns(list);
			} else if ("2".equals(type)) {// 请假
				List<Leave> list = ExcelUtil.getQjFromFile(empId, file.getInputStream(), 0, dateService);
				this.leaveSer.updateForExcelLeaves(list);
			} else if ("3".equals(type)) {// 外出
				List<Leave> list = ExcelUtil.getQjFromFile(empId, file.getInputStream(), 1, dateService);
				this.leaveSer.updateForExcelWcs(list);
			} else if ("4".equals(type)) {// 加班
				List<JiaBan> list = ExcelUtil.getJiabanFromFile(empId, file.getInputStream());
				this.jiabanService.addJiaban(list);
			} else {// 错误的类型

			}

			model.addAttribute("msg", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作失败，请检查Excel文件格式是否符合要求");

		}
		return "oa/kq/import";
	}

	@RequestMapping("oa/kq/search")
	public String searchKq(String begin, String end, String beginHour, String beginMin, String endHour, String endMin,
			HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
		List<Role> roles = this.getLoginUserRole(session);

		if (StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)) {
			return "oa/kq/search";
		}

		model.addAttribute("begin", begin);
		model.addAttribute("end", end);

		String beginTime = beginHour + ":" + beginMin;
		String endTime = endHour + ":" + endMin;

		// beginTime = beginTime.length()!=5 ? "23:59" : beginTime;
		// endTime = endTime.length() != 5 ? "00:00" : endTime;

		if (beginTime.length() != 5) {
			beginTime = "23:59";
			beginHour = "";
			beginMin = "";
		}

		if (endTime.length() != 5) {
			endTime = "00:00";
			endHour = "";
			endMin = "";
		}

		model.addAttribute("endHour", endHour);
		model.addAttribute("endMin", endMin);
		model.addAttribute("beginHour", beginHour);
		model.addAttribute("beginMin", beginMin);

		// List<User> users = this.userSer.findSubordinates(loginUser.getId(),
		// roles);

		List<CheckIn> list = new ArrayList<CheckIn>();

		if (RoleUtil.hasRole(roles, "总经理")) {
			list = this.cSer.findByTimeSum(beginTime, endTime, begin, end);
		} else if (RoleUtil.hasRole(roles, "技术总监")) {
			list = this.cSer.findByTimeSumForD(Consts.techDeptId, beginTime, endTime, begin, end);
		} else if (RoleUtil.hasRole(roles, "部门主管")) {
			List<Dept> depts = userSer.findDeptsByMgr(loginUser.getId());
			int deptId = 0;

			for (Dept dpt : depts) {
				deptId = dpt.getId();
				List<CheckIn> list1 = this.cSer.findByTimeSumForDeptMgr(deptId, beginTime, endTime, begin, end);
				list.addAll(list1);
			}
			/*
			 * if(depts.size()>0){ deptId = depts.get(0).getId(); }
			 */
			// list = this.cSer.findByTimeSumForDeptMgr(deptId,beginTime,
			// endTime, begin, end);
		}

		// List<CheckIn> list = this.cSer.findByTimeSum(beginTime, endTime,
		// begin, end);

		model.addAttribute("list", list);

		return "oa/kq/search";
	}

	// 年假损益
	@RequestMapping("oa/nianjia/sy")
	public String nianjiaSy(String type, Integer[] empIds, Integer zj, Integer day, Integer hour, Integer min,
			String bz, HttpSession session, Model model) {
		List<User> users = this.userSer.findAllUsers();

		model.addAttribute("users", users);

		if (StringUtils.isEmpty(type)) {
			return "oa/kq/sy";
		}
		Integer timeLen = day * 60 * 8 + hour * 60 + min;
		try {
			if (zj == 1) {
				timeLen = -timeLen;
			}
			for (Integer empId : empIds) {
				EmpNianjia nj = oaUtilService.getEmpNianjiaById(empId);
				int timeLeft = 0;
				int bingjia = nj.getBingjia();
				int nianjia = nj.getNianjia();

				if ("年假".equals(type)) {
					nianjia -= timeLen;
					nj.setNianjia(nianjia);
					timeLeft = nianjia;
				} else if ("病假".equals(type)) { // 病假
					bingjia -= timeLen;
					nj.setBingjia(bingjia);
					timeLeft = bingjia;
				} else {// 类型错误
					throw new RuntimeException("记录类型错误，只有年假或者病假");
				}
				NianjiaRecord nr = new NianjiaRecord();
				nr.setBz(bz);
				nr.setDayStr(DateUtil.getCurrentTime("yyyy年MM月dd日"));// today
				nr.setEmpId(empId);
				// nr.setTempName(tempName)
				nr.setTimeLen(timeLen);
				nr.setType(type);
				nr.setTimeLeft(timeLeft);

				this.oaUtilService.nianjiaSy(nj, nr);
			}
			model.addAttribute("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/kq/sy";
	}

	@RequestMapping("oa/nianjia/record/all")
	public String allNianjiaRecord(HttpSession session, Model model) {

		List<NianjiaRecord> nr = oaUtilService.findAllNianjiaRecord();

		for (NianjiaRecord n : nr) {
			n.setTimeLenStr(OtherUtil.miniute2String(n.getTimeLen()));
			n.setTimeLeftStr(OtherUtil.miniute2StringWithF(n.getTimeLeft()));
		}
		model.addAttribute("list", nr);

		return "oa/emp/nianjiaRecordAll";
	}

	@RequestMapping("oa/nianjia/record")
	public String myNianjiaRecord(HttpSession session, Model model) {
		String type = "年假";
		User loginUser = this.getLoginUser(session);
		List<NianjiaRecord> nr = oaUtilService.findNianjiaRecordByEmpId(loginUser.getId(), type);

		for (NianjiaRecord n : nr) {
			n.setTimeLenStr(OtherUtil.miniute2String(n.getTimeLen()));
			n.setTimeLeftStr(OtherUtil.miniute2StringWithF(n.getTimeLeft()));
		}
		model.addAttribute("list", nr);
		model.addAttribute("type", type);

		return "oa/emp/nianjiaRecord";
	}

	@RequestMapping("oa/bingjia/record")
	public String myBingjiaRecord(HttpSession session, Model model) {
		String type = "病假";
		User loginUser = this.getLoginUser(session);
		List<NianjiaRecord> nr = oaUtilService.findNianjiaRecordByEmpId(loginUser.getId(), type);

		for (NianjiaRecord n : nr) {
			n.setTimeLenStr(OtherUtil.miniute2String(n.getTimeLen()));
			n.setTimeLeftStr(OtherUtil.miniute2String(n.getTimeLeft()));
		}
		model.addAttribute("list", nr);
		model.addAttribute("type", type);

		return "oa/emp/nianjiaRecord";
	}

	@RequestMapping("oa/nianjia/empRecord")
	public String empNianjiaRecord(Integer empId, Integer jtype, HttpSession session, Model model) {
		String type = "年假";
		if (jtype == 1) {
			type = "病假";
		}
		// User loginUser = this.getLoginUser(session);
		User emp = this.userSer.findById(empId);
		List<NianjiaRecord> nr = oaUtilService.findNianjiaRecordByEmpId(emp.getId(), type);

		for (NianjiaRecord n : nr) {
			n.setTimeLenStr(OtherUtil.miniute2String(n.getTimeLen()));
			n.setTimeLeftStr(OtherUtil.miniute2StringWithF(n.getTimeLeft()));
		}
		model.addAttribute("list", nr);
		model.addAttribute("emp", emp);
		model.addAttribute("type", type);

		return "oa/kq/nianjiaRecord";
	}
	
	@RequestMapping(value = "oa/nianjia/clearSeal", method = RequestMethod.POST)
	public void clearSeal() {
		taskUtil.cleanOutTimeSealApply();
	}
	
	@RequestMapping("oa/nianjia/addNianjia")
	public void addNianjia() {
		System.out.println("新入职员工年假结算开始");
		// 年假公式X/12=Y/10，（X为入职的月份到次年的4月总月数，Y为计入年假天数）
		// 病假公式X/12=Y/7,（X为入职的月份到次年的4月总月数，Y为计入病假天数）

		// 每月1号早上一点结算，当前时间减一个月，截取年月
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// TODO 修改数值
		cal.add(Calendar.MONTH, -1);
		Date date = cal.getTime();
		String dateStr = DateUtil.getTimeString(date, Consts.chinaDateFmt);
		// 计算年假和病假
		int ys = 15 - date.getMonth();
		int nj = (int) Math.ceil(ys * 10.0 / 12);
		int bj = (int) (Math.ceil(ys * 7.0 / 12));
		String  str = dateStr.substring(0, 7);
		// 查询这个月入职的员工信息
		List<User> users = this.userSer.findUsersByEntryDate(str);

		List<NianjiaRecord> nrs = new ArrayList<NianjiaRecord>();
		List<EmpNianjia> njs = new ArrayList<EmpNianjia>();
		for (User user : users) {
			EmpNianjia empNj = this.oaUtilService.getEmpNianjiaById(user.getId());
			// 年假病假
			empNj.setBingjia(bj * 60 * 8);
			empNj.setNianjia(nj * 60 * 8);
			njs.add(empNj);

			// 年假记录
			NianjiaRecord record = new NianjiaRecord();
			record.setEmpId(user.getId());
			record.setType("年假");
			record.setBz("新入职员工假期计入");
			record.setDayStr(DateUtil.getTimeString(new Date(), Consts.chinaDateFmt));
			record.setTimeLen(-nj * 60 * 8);
			record.setTimeLeft(nj * 60 * 8);
			record.setLeaveId(0);
			nrs.add(record);
			// 病假
			NianjiaRecord record2 = new NianjiaRecord();
			record2.setEmpId(user.getId());
			record2.setType("病假");
			record2.setBz("新入职员工假期计入");
			record2.setDayStr(DateUtil.getTimeString(new Date(), Consts.chinaDateFmt));
			record2.setTimeLen(-bj * 60 * 8);
			record2.setTimeLeft(bj * 60 * 8);
			record2.setLeaveId(0);
			nrs.add(record2);
		}
		this.oaUtilService.nianjiaClear(njs, nrs);
//		getMailUtil().sendToMe("年度结算成功", "年度结算成功");
		System.out.println("新入职员工年假结算结束");
	}

	@RequestMapping("oa/nianjia/mine")
	public String myNianjia(HttpSession session, Model model) {
		User loginUser = this.getLoginUser(session);

		// 我当前的年假剩余
		EmpNianjia nj = oaUtilService.getEmpNianjiaById(loginUser.getId());

		if (nj != null) {
			nj.setBingjiaStr(OtherUtil.miniute2StringWithF(nj.getBingjia()));
			nj.setNianjiaStr(OtherUtil.miniute2StringWithF(nj.getNianjia()));
		}

		model.addAttribute("nianjia", nj);

		if (Consts.devMode == 1) {
			model.addAttribute("devMode", 1);
			String msg = (String) session.getAttribute("msg");
			if (!StringUtils.isEmpty(msg)) {
				model.addAttribute("msg", msg);
			}
			session.removeAttribute("msg");
		}
		return "oa/emp/myNianjia";
	}

	@RequestMapping("oa/kq/reset")
	public String resetDk(Integer[] empIds, String dayStr, String checkin, String checkout, HttpSession session,
			Model model) {
		// TODO 修正打卡记录
		List<User> list = this.userSer.findAllUsers();
		model.addAttribute("users", list);
		if (null != empIds) {
			for (Integer empId : empIds) {
				this.cSer.reRijieEmp(dayStr, checkin, checkout, empId);
			}
			model.addAttribute("msg", "考勤记录修改成功");
		}
		return "oa/kq/reset";
	}

	@RequestMapping("oa/kq/export")
	public String export(String begin, String end, HttpSession session, Model model, HttpServletResponse response) {

		// DateUtil.getCurrentTime("yyyy年MM月");

		// String begin = yf+"01日";
		// String end = yf + "31日";
		if (StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)) {
			return "oa/kq/export";
		}
		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			model.addAttribute("msg", "汇总失败，请填写开始日期，以及结束日期。");
			return "oa/kq/export";
		}
		// String yf = begin.substring(0, 8);
		String yf = begin + "-" + end + "-" + System.currentTimeMillis();
		User user = (User) session.getAttribute("loginUser");
		List<User> allUser = this.userSer.findAllUsers();
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String path = servletContext.getRealPath("/") + "upload\\kq" + user.getId() + "\\";
		Set<File> kqFiles = new HashSet<File>();// 增加存放导出excel文件的集合
		if (FileUtil.deleteDirectory(path + yf + "\\")) {
			for (User u : allUser) {
				List<HashMap> list = this.kqService.findByEmpAndDayHuiZong(u.getId(), begin, end);
				// 为每个人生成一个excel
				File file = ExcelUtil.generateForKqHuiZong(list, yf, u.getName(), path);
				// kqFiles.add(yf +"/" +u.getName());//存放导出的考勤文件名称
				kqFiles.add(file);
			}
		}
		model.addAttribute("path", path);
		session.setAttribute("kqlist", kqFiles);
		model.addAttribute("yf", yf);
		return "redirect:/web/oa/kq/exportDown";
	}

	@RequestMapping("oa/kq/exportDown")
	public void exportDown(String path, String yf, HttpSession session, HttpServletResponse response) {
		try {
			Set<File> kqFiles = (Set<File>) session.getAttribute("kqlist");
			File file = FileUtil.zipFile(kqFiles, path, yf);
			ServletUtil.downloadFile(response, file, file.getName(), "application/octet-stream");
			FileUtil.deleteDirectory(path + yf + "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("oa/kq/empCheckin")
	public String empCheckin(Integer empId, HttpServletRequest req, Model model) {
		// get方式提交的参数编码，只支持iso8859-1编码
		// TODO 格式转换问题
		String dayStr = "";
		try {
			dayStr = new String(req.getParameter("dayStr").getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CheckIn ci = this.cSer.findByDayAndEmp(dayStr, empId);
		model.addAttribute("ci", ci);
		return "oa/kq/ajaxEmpCheckin";
	}

	@RequestMapping("oa/kq/all")
	public String allKq(HttpSession session, Model model) {
		String begin = "2014年04月01日";
		String end = "2014年10月13日";

		List<EmpDayVo> empDays = this.kqService.findByEmpAndDayAll(begin, end);

		List<EmpDayVo> list = new ArrayList<EmpDayVo>();

		for (EmpDayVo ed : empDays) {

			if (ed.getUncLen() > 0 && ed.getEmpId() != 1) {

				list.add(ed);
				changetime(ed);
			}
		}

		model.addAttribute("list", list);

		return "oa/kq/allKq";
	}

	@RequestMapping("oa/kq/mine")
	public String mykq(String yf, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);

		if (OtherUtil.isSpecialEmp(loginUser.getId())) {
			return "redirect:/web/oa/user/info";
		}

		Date today = new Date();
		Calendar tc = Calendar.getInstance();
		tc.setTime(today);

		if (StringUtils.isEmpty(yf)) {
			yf = DateUtil.getCurrentTime("yyyy年MM月");
		}

		String begin = yf + "01日";
		String end = yf + "" + 31 + "日";

		// User loginUser = this.getLoginUser(session);

		List<EmpDayVo> empDays = this.kqService.findByEmpAndDay(loginUser.getId(), begin, end, "desc");

		EmpDayVo total = new EmpDayVo();

		int qjLen = 0;
		int sjLen = 0;
		int unCt = 0;
		int jb = 0;
		int wcLen = 0;

		for (EmpDayVo ed : empDays) {
			qjLen += ed.getTimeLen();
			sjLen += ed.getSjLen();
			unCt += ed.getUncLen();
			jb += ed.getJiaban();
			wcLen += ed.getWcLen();
			changetime(ed);
		}
		total.setJiaban(jb);
		total.setUncLen(unCt);
		total.setSjLen(sjLen);
		total.setTimeLen(qjLen);
		total.setWcLen(wcLen);

		changetime(total);

		model.addAttribute("total", total);

		model.addAttribute("list", empDays);
		model.addAttribute("yf", yf);

		return "oa/kq/mykq";
	}

	@RequestMapping("oa/kq/emp")
	public String empKq(int id, String yf, Integer deptId, HttpSession session, Model model) {

		User emp = this.userSer.findById(id);

		if (StringUtils.isEmpty(yf)) {
			yf = DateUtil.getCurrentTime("yyyy年MM月");
		}

		String begin = yf + "01日";
		String end = yf + "" + 31 + "日";

		model.addAttribute("deptId", deptId);
		model.addAttribute("yf", yf);
		model.addAttribute("month", StringtoByteUtil.chineseToString(yf));

		if (emp == null) {
			EmpDay total = new EmpDay();
			model.addAttribute("total", total);
			return "oa/kq/empKq";
		}

		List<EmpDayVo> empDays = this.kqService.findByEmpAndDay(emp.getId(), begin, end, "asc");
		model.addAttribute("list", empDays);

		EmpDayVo total = new EmpDayVo();

		int qjLen = 0;
		int sjLen = 0;
		int unCt = 0;
		int jb = 0;
		int wcLen = 0;

		for (EmpDayVo ed : empDays) {
			qjLen += ed.getTimeLen();
			sjLen += ed.getSjLen();
			unCt += ed.getUncLen();
			jb += ed.getJiaban();
			wcLen += ed.getWcLen();
			changetime(ed);
		}

		total.setJiaban(jb);
		total.setUncLen(unCt);
		total.setSjLen(sjLen);
		total.setTimeLen(qjLen);
		total.setWcLen(wcLen);

		changetime(total);

		model.addAttribute("total", total);
		model.addAttribute("list", empDays);
		model.addAttribute("emp", emp);

		return "oa/kq/empKq";
	}

	@RequestMapping("oa/kq/njList")
	public String njList(Integer deptId, Integer njsy, HttpSession session, Model model) {

		if (deptId == null) {
			deptId = 0;
		}

		if (njsy == null) {
			njsy = 0;
		}

		User loginUser = this.getLoginUser(session);
		List<Role> roles = this.getLoginUserRole(session);

		List<EmpNianjia> list = new ArrayList<EmpNianjia>(); //

		List<Dept> deptList = new ArrayList<Dept>();

		if (RoleUtil.hasRole(roles, "总经理") || RoleUtil.hasRole(roles, "考勤管理员") || loginUser.getId() == 8) {
			if (deptId == 0) {
				list = this.oaUtilService.findAllNianjia2();
			} else {
				list = this.oaUtilService.findAllNianjiaByDept(deptId + "");
			}
			deptList = this.userSer.findAllSubDept();
		} else if (RoleUtil.hasRole(roles, "技术总监")) {
			if (deptId == 0) {
				list = this.oaUtilService.findAllNianjiaByTechDept();
			} else {
				list = this.oaUtilService.findAllNianjiaByDept(deptId + "");
			}
			List<Dept> dps = userSer.findAllSubDept();

			for (Dept d : dps) {
				if (d.getPid() == Consts.techDeptId)// bug...
					deptList.add(d);
			}
		} else if (RoleUtil.hasRole(roles, "部门主管")) {
			deptList = this.userSer.findDeptsByMgr(loginUser.getId());

			if (deptId == 0) {
				List<Dept> ds = deptList;
				String depts = "";
				int size = ds.size();
				for (int i = 0; i < size; i++) {
					Dept d = ds.get(i);
					depts += d.getId();
					if (i != size - 1) {
						depts += ",";
					}
				}
				if (StringUtils.isNotEmpty(depts))
					list = this.oaUtilService.findAllNianjiaByDept(depts);
			} else {
				list = this.oaUtilService.findAllNianjiaByDept(deptId + "");
			}
		}

		List<EmpNianjia> list2 = new ArrayList<EmpNianjia>();
		for (EmpNianjia emj : list) {
			if (njsy == 1) {
				if (emj.getNianjia() < 0) {
					list2.add(emj);
				}
			} else if (njsy == 2) {
				if (emj.getNianjia() >= 0 && (emj.getNianjia() < (2 * 480))) {
					list2.add(emj);
				}
			} else if (njsy == 3) {
				if (emj.getNianjia() >= (2 * 480)) {
					list2.add(emj);
				}
			} else {
				list2.add(emj);
			}

			emj.setNianjiaStr(OtherUtil.miniute2StringWithF(emj.getNianjia()));
			emj.setBingjiaStr(OtherUtil.miniute2StringWithF(emj.getBingjia()));
		}

		model.addAttribute("list", list2);
		model.addAttribute("depts", deptList);
		model.addAttribute("deptId", deptId);
		model.addAttribute("njsy", njsy);

		return "oa/kq/njList";
	}
	/*
	 * public String empKqOld(@PathVariable Integer id, String yf, Integer
	 * deptId, HttpSession session, Model model){
	 * 
	 * User emp = this.userSer.findById(id);
	 * 
	 * model.addAttribute("deptId",deptId);
	 * 
	 * if(emp == null){ EmpDay total = new EmpDay();
	 * model.addAttribute("total",total); return "oa/kq/empKq"; }
	 * 
	 * List<EmpDay> empDays = edSer.findByEmpIdAndDayStr(begin, end,
	 * emp.getId());
	 * 
	 * int qjLen = 0; int qj2Len = 0; int unCt = 0; int jb = 0; for(EmpDay ed :
	 * empDays){ qjLen += ed.getQjLen(); qj2Len += ed.getQj2Len(); unCt +=
	 * ed.getUnCommonTime(); jb += ed.getJiabanLen(); } EmpDay total = new
	 * EmpDay(); total.setJiabanLen(jb); total.setUnCommonTime(unCt);
	 * total.setQjLen(qjLen); total.setQj2Len(qj2Len);
	 * 
	 * model.addAttribute("total",total); model.addAttribute("list",empDays);
	 * model.addAttribute("emp",emp);
	 * 
	 * return "oa/kq/empKq"; }
	 */

	@RequestMapping("oa/kq/list")
	public String listOld(Integer deptId, String month, String yf, Integer uid, HttpSession session, Model model) {

		month = StringtoByteUtil.stringToChinese(month);

		User loginUser = this.getLoginUser(session);

		if ("lhe".equals(loginUser.getCode())) {
			return "redirect:/web/oa/date/mgr";
		}

		Date today = new Date();
		Calendar tc = Calendar.getInstance();
		tc.setTime(today);

		if (StringUtils.isEmpty(yf)) {
			yf = DateUtil.getCurrentTime("yyyy年MM月");
		}

		if (!("".equals(month)) && month != null) {
			yf = month;
		}
		model.addAttribute("yf", yf);

		String begin = yf + "01日";
		String end = yf + "" + 31 + "日";

		List<Role> roles = this.getLoginUserRole(session);

		List<Dept> depts = new ArrayList<Dept>();

		if (RoleUtil.hasRole(roles, "总经理") || RoleUtil.hasRole(roles, "考勤管理员")) {
			depts = userSer.findAllSubDept();
			Dept d = new Dept();
			d.setId(0);
			d.setName("全体员工");
			depts.add(0, d);
		} else if (RoleUtil.hasRole(roles, "技术总监")) {
			List<Dept> dps = userSer.findAllSubDept();

			for (Dept d : dps) {
				if (d.getPid() == Consts.techDeptId)// bug...
					depts.add(d);

			}
		} else if (RoleUtil.hasRole(roles, "部门主管")) {
			List<Dept> mdp = this.userSer.findDeptsByMgr(loginUser.getId());
			depts.addAll(mdp);
		}
		if (41 == loginUser.getId()) {// 汪桃红 请假，刘总带主管
			Dept dept = new Dept();
			dept.setId(8);
			dept.setName("系统设计部");
			depts.add(dept);
		}

		model.addAttribute("depts", depts);

		if (deptId == null || deptId == 0) {
			if (RoleUtil.hasRole(roles, "总经理") || RoleUtil.hasRole(roles, "考勤管理员")) { // 默认查询所有
				List<EmpDayVo> totals = this.kqService.findSumAll(begin, end);

				EmpDayVo total = new EmpDayVo();

				int qjLen = 0;
				int sjLen = 0;
				int unCt = 0;
				int jb = 0;
				int wcLen = 0;

				for (EmpDayVo ed : totals) {
					qjLen += ed.getTimeLen();
					sjLen += ed.getSjLen();
					unCt += ed.getUncLen();
					jb += ed.getJiaban();
					wcLen += ed.getWcLen();
					changetime(ed);
				}

				total.setWcLen(wcLen);
				total.setJiaban(jb);
				total.setUncLen(unCt);
				total.setSjLen(sjLen);
				total.setTimeLen(qjLen);

				changetime(total);

				model.addAttribute("total", total);
				model.addAttribute("all", totals);

			} else if (RoleUtil.hasRole(roles, "部门主管")) {
				Dept dept = depts.get(0);

				List<EmpDayVo> totals = this.kqService.findByDayAndDeptSum(dept.getId(), begin, end);

				EmpDayVo total = new EmpDayVo();

				int qjLen = 0;
				int sjLen = 0;
				int unCt = 0;
				int jb = 0;
				int wcLen = 0;

				for (EmpDayVo ed : totals) {
					qjLen += ed.getTimeLen();
					sjLen += ed.getSjLen();
					unCt += ed.getUncLen();
					jb += ed.getJiaban();
					wcLen += ed.getWcLen();
					changetime(ed);
				}

				total.setWcLen(wcLen);
				total.setJiaban(jb);
				total.setUncLen(unCt);
				total.setSjLen(sjLen);
				total.setTimeLen(qjLen);

				changetime(total);

				model.addAttribute("dept", dept);
				model.addAttribute("total", total);
				model.addAttribute("all", totals);
			}

			return "oa/kq/listkq";
		}

		Dept dept = this.userSer.findDeptById(deptId);

		List<EmpDayVo> totals = this.kqService.findByDayAndDeptSum(dept.getId(), begin, end);

		EmpDayVo total = new EmpDayVo();

		int qjLen = 0;
		int sjLen = 0;
		int unCt = 0;
		int jb = 0;
		int wcLen = 0;

		for (EmpDayVo ed : totals) {
			qjLen += ed.getTimeLen();
			sjLen += ed.getSjLen();
			unCt += ed.getUncLen();
			jb += ed.getJiaban();
			wcLen += ed.getWcLen();
			changetime(ed);
		}

		total.setWcLen(wcLen);
		total.setJiaban(jb);
		total.setUncLen(unCt);
		total.setSjLen(sjLen);
		total.setTimeLen(qjLen);

		changetime(total);

		model.addAttribute("dept", dept);
		model.addAttribute("total", total);
		model.addAttribute("all", totals);

		return "oa/kq/listkq";
	}

	public static void changetime(EmpDayVo ed) {
		int timeLen = ed.getTimeLen();
		int jiaban = ed.getJiaban();
		int sjLen = ed.getSjLen();
		int dkLen = ed.getDkLen();
		int wcLen = ed.getWcLen();
		int uncLen = ed.getUncLen();

		ed.setTimeLenStr(miniute2String(timeLen));
		ed.setJiabanStr(miniute2String(jiaban));
		ed.setSjLenStr(miniute2String(sjLen));
		ed.setDkLenStr(miniute2String(dkLen));
		ed.setWcLenStr(miniute2String(wcLen));
		ed.setUncLenStr(miniute2String(uncLen));
	}

	public static String miniute2String(int mits) {
		if (mits < 0) {
			mits = -mits;
		}
		int days = mits / 480;
		int minutes = mits % 480;
		int hours = minutes / 60;
		minutes = minutes % 60;
		String str = "";
		str += (days == 0) ? "" : (days + "天");
		str += (hours == 0) ? "" : (hours + "小时");
		str += (minutes == 0) ? "" : (minutes + "分钟");
		if (StringUtils.isEmpty(str)) {
			str = "0";
		}
		return str;
	}

	/*
	 * public String list(Integer deptId, Integer uid, HttpSession session,
	 * Model model){
	 * 
	 * User loginUser = this.getLoginUser(session); List<Role> roles =
	 * this.getLoginUserRole(session);
	 * 
	 * List<Dept> depts = new ArrayList<Dept>();
	 * 
	 * if(RoleUtil.hasRole(roles, "总经理") || RoleUtil.hasRole(roles, "考勤管理员")){
	 * depts = userSer.findAllSubDept(); }else if( RoleUtil.hasRole(roles,
	 * "技术总监") ){ List<Dept> dps = userSer.findAllSubDept();
	 * 
	 * for(Dept d : dps){ if(d.getPid()!= null && d.getPid() ==
	 * Consts.techDeptId)//bug... depts.add(d);
	 * 
	 * } }else if( RoleUtil.hasRole(roles, "部门主管") ){ Dept dp =
	 * this.userSer.findDeptById(loginUser.getDeptId()); depts.add(dp); }
	 * 
	 * model.addAttribute("depts", depts);
	 * 
	 * if(deptId == null){ if(RoleUtil.hasRole(roles, "总经理") ||
	 * RoleUtil.hasRole(roles, "考勤管理员")){ //默认查询所有 List<EmpDay> totals =
	 * edSer.findByDaySum(begin, end); EmpDay total = new EmpDay();
	 * 
	 * int qjLen = 0; int qj2Len = 0; int unCt = 0; int jb = 0; for(EmpDay ed :
	 * totals){ qjLen += ed.getQjLen(); qj2Len += ed.getQj2Len(); unCt +=
	 * ed.getUnCommonTime(); jb += ed.getJiabanLen(); } total.setJiabanLen(jb);
	 * total.setUnCommonTime(unCt); total.setQjLen(qjLen);
	 * total.setQj2Len(qj2Len);
	 * 
	 * model.addAttribute("total",total); model.addAttribute("all",totals);
	 * }else if( RoleUtil.hasRole(roles, "部门主管") ){ Dept dept = depts.get(0);
	 * 
	 * List<EmpDay> totals = edSer.findByDayAndDeptSum(begin, end,
	 * dept.getId()); EmpDay total = new EmpDay();
	 * 
	 * int qjLen = 0; int qj2Len = 0; int unCt = 0; int jb = 0; for(EmpDay ed :
	 * totals){ qjLen += ed.getQjLen(); qj2Len += ed.getQj2Len(); unCt +=
	 * ed.getUnCommonTime(); jb += ed.getJiabanLen(); } total.setJiabanLen(jb);
	 * total.setUnCommonTime(unCt); total.setQjLen(qjLen);
	 * total.setQj2Len(qj2Len);
	 * 
	 * model.addAttribute("dept",dept); model.addAttribute("total",total);
	 * model.addAttribute("all",totals);
	 * 
	 * }
	 * 
	 * return "oa/kq/listkq"; }
	 * 
	 * Dept dept = this.userSer.findDeptById(deptId);
	 * 
	 * List<EmpDay> totals = edSer.findByDayAndDeptSum(begin, end, deptId);
	 * EmpDay total = new EmpDay();
	 * 
	 * int qjLen = 0; int qj2Len = 0; int unCt = 0; int jb = 0; for(EmpDay ed :
	 * totals){ qjLen += ed.getQjLen(); qj2Len += ed.getQj2Len(); unCt +=
	 * ed.getUnCommonTime(); jb += ed.getJiabanLen(); } total.setJiabanLen(jb);
	 * total.setUnCommonTime(unCt); total.setQjLen(qjLen);
	 * total.setQj2Len(qj2Len);
	 * 
	 * model.addAttribute("dept",dept); model.addAttribute("total",total);
	 * model.addAttribute("all",totals);
	 * 
	 * return "oa/kq/listkq"; }
	 */
	private ObjectMapper objMapper = new ObjectMapper();

	// 为补考勤获取打卡信息
	@RequestMapping("oa/kq/day")
	@ResponseBody
	public HashMap<String, String> ajaxDK2(String dayStr, Integer empId, HttpSession session) {

		String today = "2014年07月17日";// 本地测试

		if ("http://localhost/ks".equals(Consts.ksurl)) { // 如果不是本地测试
			dayStr = today;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		User loginUser = this.getLoginUser(session);

		if (empId == null) {
			empId = loginUser.getId();
		}

		CheckIn ci = this.cSer.findByDayAndEmp(dayStr, empId);

		String checkin = "";
		String checkout = "";

		if (ci == null) {
			map.put("ret", "-1");
		} else {
			map.put("ret", "1");
			if (StringUtils.isNotEmpty(ci.getCheckin())) {
				checkin = ci.getCheckin();
			}
			if (StringUtils.isNotEmpty(ci.getCheckout())) {
				checkout = ci.getCheckout();
			}

			if (StringUtils.isEmpty(checkout) && StringUtils.isEmpty(checkin)) {
				map.put("ret", "-1");
			}

			map.put("checkin", checkin);
			map.put("checkout", checkout);
		}
		return map;
	}

	@RequestMapping("oa/kq/today")
	@ResponseBody
	public HashMap<String, String> ajaxDk(HttpSession session) {
		String today = "2014-07-17";// 本地测试
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		HashMap<String, String> map = new HashMap<String, String>();

		User loginUser = this.getLoginUser(session);

		try {
			String url = Consts.ksurl + "/rest/ks/emp";

			if (!"http://localhost/ks".equals(Consts.ksurl)) { // 如果不是本地测试
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date tod = new Date();

				today = sdf.format(tod);
			}

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			Map<String, String> params = new HashMap<String, String>();

			params.put("day", today);
			params.put("name", loginUser.getName());
			String content = HttpUtil.getContent(url, params, null, null);
			CheckIn c1 = null;
			if (content != null) {
				JsonResult jr = objMapper.readValue(content, JsonResult.class);

				if (jr.getRet() == -1) {
					throw new RuntimeException(jr.getMsg());
				}

				CheckInFromKs cifk = jr.getCheckin();
				c1 = new CheckIn();

				String checkin = null;// cifk.getCheckin();
				String checkout = null;// cifk.getCheckout();

				if (cifk != null) {
					checkin = cifk.getCheckin();
					checkout = cifk.getCheckout();
					Date d = sdf1.parse(checkin);
					checkin = sdf2.format(d);

					if (null != checkout) {
						d = sdf1.parse(checkout);
						checkout = sdf2.format(d);
					}
				}

				c1.setCheckin(checkin);
				c1.setCheckout(checkout);
			}

			CheckIn c2 = null;
			CheckInRemote cir = this.cSer.findRemoteCheckInByName(loginUser.getName(), dayStr);
			if (cir != null) {
				c2 = new CheckIn();
				c2.setCheckin(cir.getCheckin());
				c2.setCheckout(cir.getCheckout());
				c2.setName(cir.getName());

			}

			CheckIn c = CheckInUtil.mergeCheckIn(c1, c2);

			map.put("ret", "1");
			map.put("checkin", c.getCheckin());
			map.put("checkout", c.getCheckout());

		} catch (Exception e) {
			e.printStackTrace();
			map.put("ret", "-1");
			map.put("msg", e.getMessage());
			// 发邮件。。。。
			if (Consts.devMode == 0)// 非开发模式
				mailUtil.sendToMe("获取今日考勤数据出问题", e.getMessage());
		}
		return map;
	}

	@RequestMapping("oa/kq/uploadKq")
	@ResponseBody
	// ?content={"ret":1,"msg":"","data":[{"checkin":"2017-09-05
	// 07:44:06","checkout":"2017-09-05
	// 18:34:49","id":"116","name":"王伟"}],"checkin":null}&day=2017-09-05
	public HashMap<String, String> uploadKq(String content, String day, HttpServletResponse response,
			HttpServletRequest request) { // 上传考勤数据
		response.setHeader("Access-Control-Allow-Origin", "*");
		String remoteIP = request.getRemoteAddr();
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			JsonResult jr = objMapper.readValue(content, JsonResult.class);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			List<CheckInFromKs> data = jr.getData();

			if (data.isEmpty()) { // 如果是空数据
				map.put("ret", "1");
				TaskUtil.A3Status = System.currentTimeMillis();
				map.put("msg", "考勤数据已【更新】成功。");
				return map;
			}

			List<CheckInRemote> remotes = new ArrayList<CheckInRemote>();

			for (CheckInFromKs cifk : data) {
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if (null != checkout) {
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}

				CheckInRemote cir = new CheckInRemote();
				cir.setCheckin(checkin);
				cir.setCheckout(checkout);
				cir.setDayStr(day);
				cir.setName(cifk.getName());
				cir.setId(cifk.getId());

				remotes.add(cir);
			}

			// 添加到本地数据库
			this.cSer.addRemoteCheckIn(remotes, day);

			TaskUtil.A3Status = System.currentTimeMillis();
			map.put("ret", "1");
			map.put("msg", "考勤数据已成功导入。");

		} catch (Exception e) {
			map.put("ret", "-1");
			map.put("msg", "上传考勤数据出现问题！");
			// 发邮件。。。。
			if (Consts.devMode == 0)// 非开发模式
				mailUtil.sendToMe("从IP:" + remoteIP + "上传今日考勤数据出问题", e.getMessage());
		}
		return map;
	}

	@RequestMapping("oa/kq/rjs")
	public String rjs(HttpSession session) throws ParseException {
		taskUtil.rijie();
		taskUtil.checkInEmail();
		session.setAttribute("msg", "手动日结成功！");
		return "redirect:/web/oa/nianjia/mine";
	}

	@RequestMapping("oa/kq/njs")
	public String njs(HttpSession session) {
		taskUtil.ninajiaClear();
		session.setAttribute("msg", "手动年假结算成功！");
		return "redirect:/web/oa/nianjia/mine";
	}

}
