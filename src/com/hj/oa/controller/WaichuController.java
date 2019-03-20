package com.hj.oa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.commons.ApiResult;
import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.commons.MateriaConstants;
import com.hj.commons.ResultCode;
import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.SpRecord;
import com.hj.oa.bean.User;
import com.hj.oa.dao.OAUtilMapper;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.DateHelperUtil;
import com.hj.util.DateUtil;
import com.hj.util.LeaveUtil;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;

@Controller
public class WaichuController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private DateService dateService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	OAUtilMapper oaUtilMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private CheckInService checkInService;

	/**
	 * @Description: 主管代理申请出差
	 * @param leave
	 *            出差信息
	 * @param model
	 *            数据model
	 * @param session
	 *            http Session
	 * @return
	 * @throws ParseException
	 * @author mlsong
	 * @date 2017年8月7日 下午1:16:34
	 */
	@RequestMapping("oa/waichu/dlsq")
	public String dlsq(Leave leave, String beginDate, String beginHour, String beginMin, String endDate, String endHour,
			String endMin, Model model, HttpSession session) throws ParseException {
		// 当前代理人信息
		User curUser = getLoginUser(session);
		List<Role> curoles = this.getLoginUserRole(session);

		// 查询可以代理的部门人员
		List<User> users = userService.findDirectSubordinates(curUser.getId(), curoles);
		model.addAttribute("users", users);

		// 直接去申请页面
		if (leave == null || beginDate == null) {
			return "oa/waichu/dlsq";
		}

		model.addAttribute("beginDate", beginDate);
		model.addAttribute("beginHour", beginHour);
		model.addAttribute("beginMin", beginMin);

		model.addAttribute("endDate", endDate);
		model.addAttribute("endHour", endHour);
		model.addAttribute("endMin", endMin);

		String beginTime = beginDate + " " + beginHour + beginMin;
		String endTime = endDate + " " + endHour + endMin;
		leave.setBeginTime(beginTime);
		leave.setEndTime(endTime);

		// 查询被代理人信息
		User propUser = userService.findById(leave.getProposer());
		leave.setProposerName(propUser.getName());
		leave.setDeptId(propUser.getDeptId());
		leave.setDailiId(curUser.getId());
		leave.setDailiName(curUser.getName());

		// 获取登陆用户的所有角色
		List<Role> roles = roleService.findRolesByEmpId(propUser.getId());

		List<Leave> isBeenLeave = this.leaveService.findIsBeenLeave(leave.getProposer(), leave.getBeginTime(),
				leave.getEndTime());
		if (!isBeenLeave.isEmpty()) {
			model.addAttribute("msg", "申请失败，用户在这段时间内，已经申请请假或者有外出（出差）。");
			return "oa/waichu/dlsq";
		}

		// 计算时间
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		Date d1 = sdf1.parse(leave.getBeginTime());
		Date d2 = sdf1.parse(leave.getEndTime());
		Date d3 = sdf1.parse(leave.getCreateTime());
		Calendar beginTimeCal = Calendar.getInstance();
		beginTimeCal.setTime(d1);
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.setTime(d2);
		Calendar createTimeCal = Calendar.getInstance();
		createTimeCal.setTime(d3);

		// 校验时间
		if (!beginTimeCal.before(endTimeCal)) {
			model.addAttribute("msg", "起止时间不合法。");
			return "oa/waichu/dlsq";
		}

		// 获取登陆用户的所属部门（可能为空） add3
		Dept dept = null;// 申请用户的部门
		if (propUser.getDeptId() != null) {
			dept = userService.findDeptById(propUser.getDeptId());
		}

		// 获取节假日情况
		List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));
		DateUtil du = new DateUtil();
		// 计算请假的时间长度 （要严格监控这个算法，比较容易出错。另外工作日历一定要提前至少5天。）
		int mits = du.countMunitesForWaichu(d1, d2, days);

		if (mits <= 0) { // 外出时间小于0
			model.addAttribute("msg", "根据工作日历，这段时间不需要申请。");
			return "oa/waichu/dlsq";
		}

		if (mits % 30 != 0) {
			model.addAttribute("msg", "根据公司规定，外出（出差）时长必须为半小时的倍数。");
			return "oa/waichu/dlsq";
		}

		// 出差申请
		ApiResult result = LeaveUtil.travelApplication(leave, mits, dept, roles, beginTimeCal, createTimeCal, true,
				dateService);

		// 申请失败
		if (result.getState() != ResultCode.SUCCESS.getCode()) {
			model.addAttribute("msg", result.getData());
			return "oa/waichu/sq";
		}

		leave.setType(Consts.BUSINESS_TRIP);
		leave.setWaichu(1);// 表示是外出或者出差

		leaveService.addLeave(leave);

		// 自动审批一次 add4
		if (curUser.getId() == leave.getCurrentId()) {// 我来审批
			// 自动审批
			autoSp(leave, "同意（代理申请）", curUser, session);
		} else {
			// 通知发送邮件
			User user = userService.findById(leave.getCurrentId());
			User loginUser = getLoginUser(session);
			String deptName = null;
			if (loginUser.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();
			String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, user.getName(),
					leave.getProposerName(), deptName, "出差申请", null, leave);

			mailUtil.sendForNotice(user, userService, roleService, menuService, content, "员工外出（出差）审批");
		}

		model.addAttribute("msg", "您的申请已提交。");

		return "oa/waichu/dlsq";
	}

	/**
	 * @Description: 代理申请的自动审批
	 * @param leave
	 *            出差申请信息
	 * @param yj
	 *            审批意见
	 * @param loginUser
	 *            登录用户信息
	 * @param session
	 *            http Session
	 * @author mlsong
	 * @date 2017年8月7日 下午1:34:36
	 */
	public void autoSp(Leave leave, String yj, User loginUser, HttpSession session) {
		int status = leave.getStatus();

		int currentSpId = leave.getCurrentId();

		if (status == 1) { // 部门经理审核
			leave.setMgrCmt(yj);
			// leave = spNext(leave, loginUser, status, yj);
			leave = chuChaiSpNext(leave, loginUser, status, yj);
		} else if (status == 2) { // 总监审核
			leave.setDirectCmt(yj);
			// leave = spNext(leave, loginUser, status, yj);
			leave = chuChaiSpNext(leave, loginUser, status, yj);
		} else if (status == 3) { // 总经理审核
			leave.setBossCmt(yj);
			leave.setStatus(4);
		}

		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		if (isDaili) {
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
		}

		if (leave.getStatus() == 4) {// add leave day
			leaveService.updateSthForWc(leave, currentSpId, dailiId);
		} else {
			leaveService.updateLeaveForWc(leave, currentSpId, dailiId);
		}

		User pps = userService.findById(leave.getProposer());
		User handler = userService.findById(leave.getCurrentId());

		// 别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.qjsp, menus)) {

				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		if (leave.getStatus() == 4) {// 结束
			String to = pps.getEmail();

			String subject = "出差审核通知";
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject,
					getBusinessTripAccessContent(pps.getName(), leave, null));
		} else if (leave.getStatus() == -1) {
			String to = pps.getEmail();

			String subject = "出差审核通知";
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject,
					getBusinessTripAccessContent(pps.getName(), leave, null));
		} else {// 通知有审批
			Dept dept = userService.findDeptById(pps.getDeptId());
			String deptName = null;
			if (loginUser.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();
			String to = handler.getEmail();

			String subject = "出差申请审批通知";
			String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, handler.getName(),
					leave.getProposerName(), deptName, "出差申请", null, leave);
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject,
					text/*
						 * getEmailContent(handler.getName(), leave, deptName)
						 */);
		}

	}

	/**
	 * 出差申请页面，或者提交申请
	 * 
	 * @return
	 */
	@RequestMapping("oa/waichu/sq")
	public String sq(Leave leave, String beginDate, String beginHour, String beginMin, String endDate, String endHour,
			String endMin, String submitCode, Model model, HttpSession session,Integer ccType) throws ParseException {

		if (leave == null || beginDate == null) {// 直接去申请页面
			return "oa/waichu/sq";
		}
		
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			model.addAttribute("msg", "操作失败，您正在重复提交数据。");
			return "oa/waichu/sq";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		model.addAttribute("beginDate", beginDate);
		model.addAttribute("beginHour", beginHour);
		model.addAttribute("beginMin", beginMin);

		model.addAttribute("endDate", endDate);
		model.addAttribute("endHour", endHour);
		model.addAttribute("endMin", endMin);

		String beginTime = beginDate + " " + beginHour + beginMin;
		String endTime = endDate + " " + endHour + endMin;
		leave.setBeginTime(beginTime);
		leave.setEndTime(endTime);

		// 获取登陆用户的所有角色
		List<Role> roles = this.getLoginUserRole(session);
		if (RoleUtil.hasRole(roles, "总经理")) {// 如果是总经理，不需要提交
			model.addAttribute("msg", "您不需要申请");
			return "oa/waichu/sq";
		}

		User loginUser = getLoginUser(session);
		leave.setProposer(loginUser.getId());
		leave.setProposerName(loginUser.getName());

		List<Leave> isBeenLeave = this.leaveService.findIsBeenLeave(leave.getProposer(), leave.getBeginTime(),
				leave.getEndTime());
		if (!isBeenLeave.isEmpty()) {
			model.addAttribute("msg", "申请失败，您在这段时间内，已经申请请假或者有出差。");
			return "oa/waichu/sq";
		}

		// 计算时间
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		Date d1 = sdf1.parse(leave.getBeginTime());
		Date d2 = sdf1.parse(leave.getEndTime());
		Date d3 = sdf1.parse(leave.getCreateTime());
		Calendar beginTimeCal = Calendar.getInstance();
		beginTimeCal.setTime(d1);
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.setTime(d2);
		Calendar createTimeCal = Calendar.getInstance();
		createTimeCal.setTime(d3);

		// 校验时间
		if (!beginTimeCal.before(endTimeCal)) {
			model.addAttribute("msg", "起止时间不合法。");
			return "oa/waichu/sq";
		}

		// 获取登陆用户的所属部门（可能为空）
		Dept dept = (Dept) session.getAttribute("loginUserDept");

		List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));// 获取节假日情况
		DateUtil du = new DateUtil();
		// 计算请假的时间长度 （要严格监控这个算法，比较容易出错。另外工作日历一定要提前至少5天。）
		int mits = du.countMunitesForWaichu(d1, d2, days);

		if (mits <= 0) {// 外出时间小于0
			model.addAttribute("msg", "根据工作日历，这段时间不需要申请。");
			return "oa/waichu/sq";
		}

		if (mits % 30 != 0) {
			model.addAttribute("msg", "根据公司规定，出差时长必须为半小时的倍数。");
			return "oa/waichu/sq";
		}

		// 出差申请
		ApiResult result  = new ApiResult();
		if(ccType == 1) {
			result = LeaveUtil.travelApplication(leave, mits, dept, roles, beginTimeCal, createTimeCal, false, dateService);
		} else {
			// 出差结束一天之内可以补申请
			// 出差结束时间 晚于 当前时间
			endTimeCal = this.dateService.addWorkDate(endTimeCal, 1);
			Calendar currentCal = Calendar.getInstance();
			createTimeCal.setTime(new Date());
			if(endTimeCal.after(currentCal)) {
				result = LeaveUtil.travelApplication(leave, mits, dept, roles, beginTimeCal, createTimeCal, true, dateService);
			} else {
				model.addAttribute("msg", "补出差必须在出差结束一个工作日之内申请");
				return "oa/waichu/sq";
			}
			
		}
		
		// 申请失败
		if (result.getState() != ResultCode.SUCCESS.getCode()) {
			model.addAttribute("msg", result.getData());
			return "oa/waichu/sq";
		}
		if(ccType == 1) {
			leave.setType(Consts.BUSINESS_TRIP);
		}else if (ccType == 2) {
			leave.setType(MateriaConstants.ADD_BUSSINESS_TRIP);
		}
		leave.setWaichu(1); // 表示是外出或者出差

		leaveService.addLeave(leave);
		model.addAttribute("msg", "您的申请已提交。");

		// 通知发送邮件
		User user = userService.findById(leave.getCurrentId());
		String deptName = null;

		if (loginUser.getId() == Consts.directorId) {
			deptName = "研发中心";
		}
		if (dept != null)
			deptName = dept.getName();
		
		// 通知发送邮件
		User manager = userService.findById(leave.getCurrentId());
		String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, manager.getName(), leave.getProposerName(), deptName, "出差申请", null, leave);

		mailUtil.sendForNotice(user, userService, roleService, menuService, content, "出差申请审批通知");
		return "oa/waichu/sq";
	}

	@RequestMapping("oa/waichu/mysq")
	public String mysq(HttpSession session, Model model) {

		User loginUser = getLoginUser(session);// 获取当前登陆的用户
		List<Leave> list = leaveService.findByEmpId(loginUser.getId(), 1);// 1表示外出
		model.addAttribute("list", list);
		return "oa/waichu/mysq";
	}

	@RequestMapping("oa/waichu/mydlsq")
	public String mydlsq(HttpSession session, Model model) {

		User loginUser = getLoginUser(session);// 获取当前登陆的用户
		List<Leave> list = leaveService.findmydlChuChai(loginUser.getId());// 1表示外出
		model.addAttribute("list", list);
		return "oa/waichu/mydlsq";
	}

	/**
	 * 我的审批
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("oa/waichu/mysp")
	public String mysp(HttpSession session, Model model) {

		User loginUser = getLoginUser(session);// 获取当前登陆的用户
		List<Leave> list = leaveService.findMySp(loginUser.getId(), 1);// leaveService.findByEmpId(loginUser.getId(),
																		// 1);//1表示外出
		model.addAttribute("list", list);
		return "oa/waichu/mysp";
	}

	@RequestMapping("oa/waichu/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Leave leave = leaveService.findById(id);
		/*
		 * if (leave == null || leave.getCurrentId() != loginUser.getId()) {
		 * return "redirect:/web/logout"; }
		 */
		model.addAttribute("leave", leave);
		return "oa/waichu/edit";
	}

	@RequestMapping("oa/waichu/info/{id}")
	public String detail(@PathVariable int id, Model model) {
		Leave leave = leaveService.findById(id);

		// 非法操作
		/*
		 * if (leave == null || leave.getProposer() != loginUser.getId()) {
		 * return "redirect:/web/logout"; }
		 */

		model.addAttribute("leave", leave);
		return "oa/waichu/detail";
	}

	@RequestMapping("oa/waichu/showForSpRecord/{id}")
	public String showForSpRecord(@PathVariable int id, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		Leave leave = leaveService.findById(id);

		// 非法操作
		if (leave == null) {
			return "redirect:/web/logout";
		}
		SpRecord spr = this.leaveService.findSpRecord(loginUser.getId(), leave.getId());
		// 非法操作
		if (spr == null) {
			return "redirect:/web/logout";
		}

		model.addAttribute("leave", leave);
		return "oa/waichu/showForSpRecord";
	}

	@RequestMapping("oa/waichu/spRecord")
	public String spRecord(HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);// (User)session.getAttribute("loginUser");
		List<SpRecord> list = leaveService.findMySpRecordForWc(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/waichu/spRecord";
	}

	@RequestMapping("oa/waichu/sp")
	public String sp(int id, String yj, String sp, HttpSession session, Model model) {
		Leave leave = leaveService.findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = leave.getCurrentId();

		if (leave == null || loginUser.getId() != currentSpId) {// 不归我审批
			return "redirect:/web/oa/leave/mysp";
		}
		int status = leave.getStatus();
		if (status == -1 || status == 4) {// 已经结束
			return "redirect:/web/oa/leave/mysp";
		}

		if (status == 1) {
			leave.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				leave = spNext(leave, loginUser, status, yj);
			} else {
				leave.setStatus(-1);
			}
		} else if (status == 2) {
			leave.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				leave = spNext(leave, loginUser, status, yj);
			} else {
				leave.setStatus(-1);
			}

		} else if (status == 3) {
			leave.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				leave.setStatus(4);
			} else {
				leave.setStatus(-1);
			}
		}

		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		if (isDaili) {
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
		}

		if (leave.getStatus() == 4) {// 审批通过。需要做一系列事情
			leaveService.updateSthForWc(leave, currentSpId, dailiId);
		} else {
			leaveService.updateLeaveForWc(leave, currentSpId, dailiId);
		}

		return "redirect:/web/oa/waichu/mysp?msg=1";
	}

	/**
	 * @Description: 出差审批
	 * @param id
	 *            出差申请Id
	 * @param yj
	 *            审批意见
	 * @param sp
	 *            审批通过/审批不通过
	 * @param session
	 *            session
	 * @param model
	 *            数据model
	 * @return String
	 * @author mlsong
	 * @date 2017年8月7日 上午8:50:25
	 */
	@RequestMapping("oa/waichu/chuchaisp")
	public String chuChaiSp(int id, String yj, String sp, HttpSession session, Model model) {
		// 获取当前审批的出差申请信息
		Leave leave = leaveService.findById(id);

		// 当前登录用户信息
		User loginUser = (User) session.getAttribute("loginUser");

		// 判断申请是否归当前用户审批
		int currentSpId = leave.getCurrentId();
		if (leave == null || loginUser.getId() != currentSpId) { // 不归当前用户审批
			return "redirect:/web/oa/leave/mysp";
		}

		// 判断当前申请是否审批结束
		int status = leave.getStatus();
		if (status == -1 || status == 4) { // 审批已经结束
			return "redirect:/web/oa/leave/mysp";
		}

		if (status == 1) {
			leave.setMgrCmt(yj);
			if ("审批通过".equals(sp)) { // 审批通过
				leave = chuChaiSpNext(leave, loginUser, status, yj);
			} else { // 审批不通过
				leave.setStatus(-1);
			}
		} else if (status == 2) {
			leave.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				leave = chuChaiSpNext(leave, loginUser, status, yj);
			} else {
				leave.setStatus(-1);
			}

		} else if (status == 3) {
			leave.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				leave.setStatus(4);
			} else {
				leave.setStatus(-1);
			}
		}

		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		if (isDaili) {
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
		}

		User pps = userService.findById(leave.getProposer());
		Dept pDept = userService.findDeptById(pps.getDeptId());

		String deptName = null;
		if (leave.getProposer() == Consts.directorId) {
			deptName = "研发中心";
		}
		if (pDept != null) {
			deptName = pDept.getName();
		}

		String mailTitle = "";
		String content = "";
		User user = new User();
		if (leave.getStatus() == 4) {// 审批通过。需要做一系列事情
			leaveService.updateSthForWc(leave, currentSpId, dailiId);
			mailTitle = "出差申请审批结果通知";
			content = getBusinessTripAccessContent(leave.getProposerName(), leave, deptName);
			user = userService.findById(leave.getProposer());
		} else if (leave.getStatus() == -1) {
			leaveService.updateLeaveForWc(leave, currentSpId, dailiId);
			mailTitle = "出差申请审批结果通知";
			content = getBusinessTripAccessContent(leave.getProposerName(), leave, deptName);
			user = userService.findById(leave.getProposer());
		} else {
			leaveService.updateLeaveForWc(leave, currentSpId, dailiId);
			mailTitle = "出差申请审批通知";
			User manager = userService.findById(leave.getCurrentId());
			content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, manager.getName(),
					leave.getProposerName(), deptName, "出差申请", null, leave);
			user = userService.findById(leave.getCurrentId());
		}

		// 通知发送邮件
		mailUtil.sendForNotice(user, userService, roleService, menuService, content, mailTitle);

		return "redirect:/web/oa/waichu/mysp?msg=1";
	}

	/**
	 * @Description: 注销出差页面和数据展示
	 * 
	 * @param dayStr
	 *            注销日期
	 * @param empId
	 *            员工ID
	 * @param empName
	 *            员工姓名
	 * @param model
	 *            数据model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月7日 下午4:57:35
	 */
	@RequestMapping("oa/waichu/goCancelBusiness")
	public String goCancelBusiness(String dayStr, Integer empId, String empName, Model model, HttpSession session) {

		if (empId == null) {
			return "oa/waichu/goCancelBusiness";
		}

		String beginTime = dayStr + " 08时30分";
		String endTime = dayStr + " 17时30分";

		List<Leave> list = this.leaveService.findLeaveByType(empId, beginTime, endTime, Consts.CANCEL_BUSINESS);

		model.addAttribute("list", list);
		model.addAttribute("empId", empId);
		model.addAttribute("empName", empName);
		model.addAttribute("dayStr", dayStr);

		return "oa/waichu/goCancelBusiness";
	}

	/**
	 * @Description: 注销出差的详细页面
	 * 
	 * @param id
	 *            出差申请编号ID
	 * @param model
	 *            数据Model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月7日 下午4:59:00
	 */
	@RequestMapping("oa/waichu/cancelBusiness/{id}")
	public String cancelLeave(@PathVariable int id, Model model) {
		Leave leave = leaveService.findById(id);

		model.addAttribute("leave", leave);
		return "oa/waichu/cancelBusiness";
	}

	@RequestMapping("oa/waichu/cancel")
	public String cancel(Integer updateId, String updateName, Integer id, int cancelType, String content,
			String beginDate, String beginHour, String beginMin, String submitCode, Model model, HttpSession session) {

		try {
			Leave leave = this.leaveService.findById(id);// 原始请假信息

			model.addAttribute("leave", leave);

			// 查询是否有销假，正在处理中。。。。（重要）

			String cancelBeginTime = beginDate + " " + beginHour + beginMin;

			LeaveCancel leaveCancel = new LeaveCancel();

			leaveCancel.setLeaveId(leave.getId());
			leaveCancel.setContent(content);
			leaveCancel.setCreateTime(DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
			leaveCancel.setWaichu(leave.getWaichu());
			leaveCancel.setType(leave.getType());
			leaveCancel.setProposerName(leave.getProposerName());
			leaveCancel.setProposer(leave.getProposer());
			leaveCancel.setDeptId(leave.getDeptId());
			leaveCancel.setUpdateId(updateId);
			leaveCancel.setUpdateName(updateName);
			// 请假开始时间
			Date t1 = DateHelperUtil.stringToDate(leave.getBeginTime());
			// 请假结束时间
			Date t2 = DateHelperUtil.stringToDate(leave.getEndTime());
			// 销假时间
			Date t3 = DateHelperUtil.stringToDate(cancelBeginTime);
			if (0 == cancelType) {
				if (t3.after(t2)) {
					model.addAttribute("msg", "注销出差时间不能晚于出差结束时间！");
					return "oa/waichu/cancelBusiness";
				}
				leaveCancel.setBeginTime(cancelBeginTime);
				leaveCancel.setEndTime(leave.getEndTime());
			}

			if (1 == cancelType) {
				leaveCancel.setBeginTime(leave.getBeginTime());
				leaveCancel.setEndTime(cancelBeginTime);
			}

			leaveCancel.setCancelType(cancelType);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leaveCancel.getBeginTime());
			Date d4 = sdf1.parse(leaveCancel.getEndTime());

			Date d3 = sdf1.parse(leave.getCreateTime());
			Calendar oldBeginTimeCal = Calendar.getInstance();
			oldBeginTimeCal.setTime(d1);

			Calendar newBeginTimeCal = Calendar.getInstance();
			newBeginTimeCal.setTime(d2);

			Calendar createTimeCal = Calendar.getInstance();
			createTimeCal.setTime(d3);
			Calendar tc = Calendar.getInstance();

			// 校验时间
			if (oldBeginTimeCal.after(newBeginTimeCal)) {
				model.addAttribute("msg", "注销出差开始时间不能早于出差开始时间");
				return "oa/waichu/cancelBusiness";
			}

			tc.add(Calendar.DAY_OF_MONTH, -1);
			tc.set(Calendar.HOUR_OF_DAY, 17);
			tc.set(Calendar.MINUTE, 30);
			tc.set(Calendar.SECOND, 0);
			tc.set(Calendar.MILLISECOND, 0);

			/*
			 * // // 有待考虑 // if(tc.after(newBeginTimeCal)){ //
			 * model.addAttribute("msg","销假开始时间不能早于昨天下班（系统考勤已经日结）"); // return
			 * "oa/waichu/cancelBusiness"; // }
			 */
			List<Day> days = dateService.findDays(sdf2.format(d2), sdf2.format(d4));
			DateUtil du = new DateUtil();
			int mits = du.countMunitesForLeave(d2, d4, days);

			if (mits % 30 != 0) {
				model.addAttribute("msg", "请重新计算注销开始时间，使出差时长为半小时的倍数");
				return "oa/waichu/cancelBusiness";
			}

			// 根据时间长短，结算审批的流程
			List<Role> roles = this.roleService.findRolesByEmpId(leaveCancel.getProposer());
			User ppuser = this.userService.findById(leaveCancel.getProposer());
			Dept dept = null;
			if (leaveCancel.getDeptId() != null) {
				dept = this.userService.findDeptById(leaveCancel.getDeptId());
			}

			LeaveUtil.cancelBusiness(leaveCancel, mits, dept, roles);

			this.leaveService.addLeaveCancel(leaveCancel);
			model.addAttribute("msg", "注销出差申请已提交，请等待审批。");

			// 发送邮件通知
			User user = userService.findById(leaveCancel.getCurrentId());
			// 别人代理我的
			User bdlUser = roleService.findDailiByOwnerId(user.getId());
			String copyTo = null;

			if (bdlUser != null) {
				List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), user.getId());
				if (OtherUtil.containsMenu(Consts.qjsp, menus)) {

					// copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
					copyTo = bdlUser.getEmail();// bdlUser.getEmail();
					if (Consts.devMode == 0) {// 应用环境
						copyTo = bdlUser.getEmail();
					}
				}
			}

			String email = user.getEmail();

			if (email != null && email.length() != 0) {
				// 发送通知，要审批了。
				String deptName = null;

				if (ppuser.getId() == Consts.directorId) {
					deptName = "研发中心";
				}
				if (dept != null) {
					deptName = dept.getName();
				}
				sendMailForCancelBusiness(leaveCancel, leave, user.getName(), deptName, email, copyTo);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "oa/waichu/cancelBusiness";

	}

	/**
	 * @Description: 出差注销审批页面所有信息
	 * @param model
	 *            model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月8日 下午4:50:41
	 */
	@RequestMapping("oa/waichu/myWaichuCancelSp")
	public String myLeaveCancelSp(Model model, HttpSession session) {
		User loginUesr = (User) session.getAttribute("loginUser");
		// 查询所有当前登录人需要审批的出差注销记录
		List<LeaveCancel> list = leaveService.findMyLeaveCancelSpByType(loginUesr.getId(), Consts.CANCEL_BUSINESS);
		model.addAttribute("list", list);
		return "oa/waichu/myWaichuCancelSp";
	}

	/**
	 * @Description: 出差注销审批详细页面和处理
	 * @param id
	 *            需要处理的申请ID
	 * @param model
	 *            model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月8日 下午4:52:24
	 */
	@RequestMapping("oa/waichu/editCancel/{id}")
	public String editLeaveCancel(@PathVariable int id, Model model, HttpSession session) {
		LeaveCancel leaveCancel = leaveService.findLeaveCancelById(id);
		User loginUser = this.getLoginUser(session);
		int currentSpId = leaveCancel.getCurrentId();

		if (leaveCancel == null || loginUser.getId() != currentSpId) {// 不归我审批
			return "redirect:/web/oa/waichu/mysp";
		}

		int leaveId = leaveCancel.getLeaveId();
		Leave leave = this.leaveService.findById(leaveId);

		model.addAttribute("leave", leaveCancel);
		model.addAttribute("l", leave);
		return "oa/waichu/editWaichuCancel";
	}

	/**
	 * @Description: 审批出差注销流程
	 * @param id
	 *            注销记录id
	 * @param yj
	 *            意见
	 * @param sp
	 *            审批
	 * @param submitCode
	 *            提交码，校验是否重复提交
	 * @param model
	 *            model
	 * @param session
	 *            session
	 * @return
	 * @throws ParseException
	 *             String
	 * @author mlsong
	 * @date 2017年8月11日 上午11:01:38
	 */
	@RequestMapping("oa/waichu/spWaichuCancel")
	public String spLeaveCancel(int id, String yj, String sp, String submitCode, Model model, HttpSession session)
			throws ParseException {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/waichu/myWaichuCancelSp";
		}
		session.removeAttribute(Consts.submitCode);

		LeaveCancel leave = leaveService.findLeaveCancelById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = leave.getCurrentId();

		if (leave == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/waichu/myWaichuCancelSp";
		}
		int status = leave.getStatus();
		if (status == -1 || status == 4) { // 已经结束
			return "redirect:/web/oa/waichu/myWaichuCancelSp";
		}

		if (status == 1) {
			leave.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				leave = xjSpNext(leave, loginUser, status, yj);
			} else {
				leave.setStatus(-1);
			}
		} else if (status == 2) {
			leave.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				leave = xjSpNext(leave, loginUser, status, yj);
			} else {
				leave.setStatus(-1);
			}

		} else if (status == 3) {
			leave.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				leave.setStatus(4);
			} else {
				leave.setStatus(-1);
			}
		}

		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		if (isDaili) {
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
		}

		Leave l = this.leaveService.findById(leave.getLeaveId());
		if (leave.getStatus() == 4) { // 审批通过。需要做一系列事情

			if (leave.getCancelType() == 0) {
				l.setEndTime(leave.getBeginTime());
			} else {
				l.setBeginTime(leave.getEndTime());
			}

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = sdf1.parse(l.getBeginTime());
			Date d2 = sdf1.parse(l.getEndTime());
			Calendar beginTimeCal = Calendar.getInstance();
			beginTimeCal.setTime(d1);
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.setTime(d2);

			List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));
			DateUtil du = new DateUtil();
			int mits = du.countMunitesForLeave(d1, d2, days);

			int ddays = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			l.setDays(ddays);
			l.setHours(hours);
			l.setMinutes(minutes);

			leaveService.updateLeaveCancel(leave, l, currentSpId, dailiId);

			updateEmpSumAndDetail(leave, l);
			leaveService.updateBusinessCancel(l, currentSpId, dailiId);

		} else {
			leaveService.updateLeaveCancel(leave, null, currentSpId, dailiId);
		}

		User pps = userService.findById(leave.getProposer());
		Dept dept = this.userService.findDeptById(pps.getDeptId());
		User handler = userService.findById(leave.getCurrentId());

		// 别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.qjsp, menus)) {

				// copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				copyTo = bdlUser.getEmail();// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		if (leave.getStatus() == 4) { // 结束
			String to = pps.getEmail();

			String subject = "出差注销审核通知";
			String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, pps.getName(),
					null, "出差注销申请", ApprovalStatus.APPROVAL_ACCESS, leave, l);
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject,
					content/* getBusinessCancelAccessContent(leave, l) */);
		} else if (leave.getStatus() == -1) {
			String to = pps.getEmail();

			String subject = "出差注销审核通知";
			String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, pps.getName(),
					null, "出差注销申请", ApprovalStatus.APPROVAL_NOT_ACCESS, leave, l);
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject,
					content/* getBusinessCancelAccessContent(leave, l) */);
		} else {// 通知有审批
			String deptName = "";

			if (pps.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();

			String to = handler.getEmail();
			String subject = "员工出差注销审批";
			String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, handler.getName(),
					leave.getProposerName(), deptName, "出差注销申请", null, leave, l);
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject,
					content/*
							 * getCancelBusinessContent(leave, l,
							 * handler.getName(), deptName)
							 */);
		}

		return "redirect:/web/oa/waichu/myWaichuCancelSp?msg=1";
	}

	/**
	 * @Description:
	 * @param curpage
	 *            页码
	 * @param model
	 *            model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月9日 上午11:50:17
	 */
	@RequestMapping("oa/waichu/findAllCancelBusiness")
	public String findAllXj(int curpage, Model model, HttpSession session) {
		List<LeaveCancel> list = leaveService.findLeaveCancelByType(curpage, Consts.CANCEL_BUSINESS);
		int totalPage = leaveService.countLeaveCancelByType(Consts.CANCEL_BUSINESS);// 总页数
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curpage", curpage);
		model.addAttribute("arr", new int[totalPage]);
		return "oa/waichu/listBusinessCancelSp";
	}

	/**
	 * @Description: 查看注销出差详细信息
	 * @param id
	 *            出差注销ID
	 * @param model
	 *            model
	 * @param session
	 *            session
	 * @return String
	 * @author mlsong
	 * @date 2017年8月9日 下午12:53:05
	 */
	@RequestMapping("oa/waichu/findCancel/{id}")
	public String findLeaveCancel(@PathVariable int id, Model model, HttpSession session) {
		LeaveCancel leaveCancel = leaveService.findLeaveCancelById(id);
		int leaveId = leaveCancel.getLeaveId();
		Leave leave = this.leaveService.findById(leaveId);

		model.addAttribute("leave", leaveCancel);
		model.addAttribute("l", leave);
		return "oa/waichu/findBusinessCancel";
	}

	private LeaveCancel xjSpNext(LeaveCancel leave, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = leave.getDirectId();
			if (did == null) {// 审批结束
				leave.setStatus(3);
				leave.setCurrentId(Consts.managerId);
			} else {
				if (did == user.getId()) {// 同一个人
					leave.setDirectCmt(yj);
					return xjSpNext(leave, user, status, yj);
				} else {
					leave.setCurrentId(did);
					leave.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer bid = leave.getBossId();
			if (bid == null) {// 审批结束
				leave.setStatus(4);
			} else {
				if (bid == user.getId()) {// 同一个人
					leave.setBossCmt(yj);
					// return spNext(leave, user, status, yj);
					leave.setStatus(4);
				} else {
					leave.setCurrentId(bid);
					leave.setStatus(status);
				}
			}

		}

		return leave;
	}

	private Leave spNext(Leave leave, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = leave.getDirectId();
			if (did == null) {// 审批结束
				leave.setStatus(4);
			} else {
				if (did == user.getId()) {// 同一个人
					leave.setDirectCmt(yj);
					return spNext(leave, user, status, yj);
				} else {
					leave.setCurrentId(did);
					leave.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer bid = leave.getBossId();
			if (bid == null) {// 审批结束
				leave.setStatus(4);
			} else {
				if (bid == user.getId()) {// 同一个人
					leave.setBossCmt(yj);
					// return spNext(leave, user, status, yj);
					leave.setStatus(4);
				} else {
					leave.setCurrentId(bid);
					leave.setStatus(status);
				}
			}
		}

		return leave;
	}

	/**
	 * @Description: 出差审批通过后，下一步处理流程
	 * @param leave
	 *            出差申请信息
	 * @param user
	 *            当前用户信息
	 * @param status
	 *            申请信息状态
	 * @param yj
	 *            审批意见
	 * @return Leave
	 * @author mlsong
	 * @date 2017年8月7日 上午9:35:27
	 */
	private Leave chuChaiSpNext(Leave leave, User user, int status, String yj) {
		status++;
		if (status == 2) { // 提交总监审批
			Integer did = leave.getDirectId();

			// 是否需要总监审批，直接提交到总经理审批
			if (did == null) { // 不需要总监审批,直接提交总经理审批
				leave.setStatus(3);
				leave.setCurrentId(Consts.managerId);
			} else {
				if (did == user.getId()) { // 同一个人
					leave.setDirectCmt(yj);
					return chuChaiSpNext(leave, user, status, yj);
				} else {
					leave.setCurrentId(did);
					leave.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer bid = leave.getBossId();
			if (bid == null) { // 审批结束
				leave.setStatus(4);
			} else {
				if (bid == user.getId()) { // 同一个人
					leave.setBossCmt(yj);
					leave.setStatus(4);
				} else {
					leave.setCurrentId(bid);
					leave.setStatus(status);
				}
			}
		}

		return leave;
	}

	public String getBusinessTripAccessContent(String name, Leave leave, String deptName) {
		if (null == deptName) {
			deptName = "";
		}
		return MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, name, deptName, "出差申请",
				(leave.getStatus() == 4 ? ApprovalStatus.APPROVAL_ACCESS : ApprovalStatus.APPROVAL_NOT_ACCESS), leave);
	}

	private void sendMailForCancelBusiness(LeaveCancel leaveCancel, Leave leave, String name, String deptName,
			String to, String copyTo) {
		String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, name,
				leave.getProposerName(), deptName, "出差注销申请", null, leaveCancel, leave);
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "员工出差注销审批", content);
	}

	/**
	 * @Description: 删除出差的所有在时间段内t_emp_day_sum和t_leave_day_detail中的信息
	 * @param leave
	 *            出差注销信息
	 * @param l
	 *            更新后的出差信息
	 * @author mlsong
	 * @date 2017年8月11日 上午11:06:08
	 */
	private void updateEmpSumAndDetail(LeaveCancel leave, Leave l) {
		try {
			// 原始的出差起止时间
			String beginDay = l.getBeginTime();
			String endDay = leave.getEndTime();

			SimpleDateFormat localeFmt = new SimpleDateFormat("yyyy年MM月dd日");

			Date beginDate = localeFmt.parse(beginDay);
			Date endDate = localeFmt.parse(endDay);

			Calendar tc = Calendar.getInstance();
			tc.setTime(beginDate);

			// 循环将出差时间内的所有信息清除
			while (true) {
				Date date = tc.getTime();
				if (date.getTime() > endDate.getTime()) {
					break;
				}

				String dateStr = localeFmt.format(date);

				// 查找当天的考勤信息
				CheckIn checkIn = checkInService.findByDayAndEmp(dateStr, leave.getProposer());

				List<CheckIn> listCheckIn = new ArrayList<CheckIn>();
				if (null != checkIn) {
					listCheckIn.add(checkIn);
				}

				// 删除表中记录
				this.oaUtilMapper.deleteByDayAndEmpId(dateStr, leave.getProposer());
				oaUtilMapper.deleteLeaveDayDetailByDay(dateStr, leave.getProposer());

				// 时间自增1天
				tc.add(tc.DATE, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
