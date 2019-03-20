package com.hj.oa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.oa.Consts;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.SpRecord;
import com.hj.oa.bean.User;
import com.hj.oa.service.DateService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.OAUtilService;
import com.hj.oa.service.OfficeSupplyService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.LeaveUtil;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;

@Controller
public class LeaveController extends BaseController {

	@Autowired
	private LeaveService leaveService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private DateService dateService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private OAUtilService oaUtilService;
	@Autowired
	private OfficeSupplyService osService;

	/**
	 * 帮助直接下属提交请假申请页面
	 * 
	 * @param leave
	 * @param model
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("oa/leave/alfs")
	public String askLeaveForSub(Leave leave, String beginDate, String beginHour, String beginMin, String endDate,
			String endHour, String endMin, String submitCode, Model model, HttpSession session) throws ParseException {

		User curUser = getLoginUser(session);
		// 应该获取申请人的role.
		List<Role> curoles = this.getLoginUserRole(session);

		List<User> users = userService.findDirectSubordinates(curUser.getId(), curoles);
		model.addAttribute("users", users);

		if (leave == null || beginDate == null) {
			return "oa/leave/askLeaveForSub";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			model.addAttribute("msg", "操作失败，您正在重复提交数据。");
			return "oa/leave/askLeaveForSub";
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

		User propUser = userService.findById(leave.getProposer());
		leave.setProposerName(propUser.getName());
		leave.setDeptId(propUser.getDeptId());
		leave.setDailiId(curUser.getId());
		leave.setDailiName(curUser.getName());

		List<Leave> isBeenLeave = this.leaveService.findIsBeenLeave(leave.getProposer(), leave.getBeginTime(),
				leave.getEndTime());
		if (!isBeenLeave.isEmpty()) {
			model.addAttribute("msg", "申请失败，用户在这段时间内，已经申请请假或者有外出（出差）。");
			return "oa/leave/askLeaveForSub";
		}

		List<Role> roles = roleService.findRolesByEmpId(propUser.getId());
		// 校验时间
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf1.parse(leave.getBeginTime()); // 开始时间
		Date end = sdf1.parse(leave.getEndTime()); // 结束时间
		Date now = new Date(); // 当前时间
		Date tempDate = new Date();// 临时使用时间
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(begin);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		Calendar tempCal = beginCal;
		// add:2018-02-08 10:53 新增代理请假时间限制：结束时间不能早于开始时间，开始时间不设限制
		if (begin.after(end)) {
			model.addAttribute("msg", "结束时间不能早于开始时间");
			return "oa/leave/askForLeave";
		}

		// 代理请假没有时间限制
		// if (begin.after(end) || begin.before(now)) {
		// model.addAttribute("msg", "开始时间不能早于结束时间或当前时间");
		// return "oa/leave/askForLeave";
		// }
		// 计算请假时长
		List<Day> days = dateService.findDays(sdf2.format(begin), sdf2.format(end));// 获取节假日情况
		DateUtil du = new DateUtil();
		// 计算请假的时间长度 （要严格监控这个算法，比较容易出错。另外工作日历一定要提前至少5天。）
		int mins = du.countMunitesForLeave(begin, end, days);
		// 节假日期间不用请假
		if (mins <= 0) {
			model.addAttribute("msg", "根据工作日历，这段时间不需要请假");
			return "oa/leave/askForLeave";
		}
		// 新增连续请假
		List<Leave> leaves = this.leaveService.findByEmpAndTime(leave.getProposer(), beginTime, endTime);
		int ld = 0;
		int lh = 0;
		int lm = 0;
		for (Leave l : leaves) {
			ld += l.getDays();
			lh += l.getHours();
			lm += l.getMinutes();
		}
		int lmits = (ld * 8 * 60) + lh * 60 + lm;

		String type = leave.getType();
		boolean daili = true;// 是代理

		Dept dept = this.getLoginUserDept(session);

		// 暂时这样处理吧！刘总没有部门信息，所以要加上
		List<Role> loginUserRoles = this.getLoginUserRole(session);

		boolean isManager = RoleUtil.hasRole(loginUserRoles, "总经理");
		if (isManager) {
			dept = userService.findDeptById(propUser.getDeptId());
		}
		dept = userService.findDeptById(propUser.getDeptId());

		if ("事假".equals(type) || "病假".equals(type)) {
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else if ("婚假".equals(type)) {
			String r = LeaveUtil.hunjia(leave, mins, dept, roles, beginCal, nowCal, daili);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("产假".equals(type)) {
			// 产假包括节假日等。
			String r = LeaveUtil.chanjia(leave, mins, dept, roles, beginCal, nowCal, daili, endCal);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("丧假".equals(type)) {
			String r = LeaveUtil.sangjia(leave, mins, dept, roles, daili);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("调休".equals(type)) {
			// 获取可调休时间
			EmpNianjia nj = oaUtilService.getEmpNianjiaById(propUser.getId());
			int tiaoxiu = 0;
			if (nj != null) {
				tiaoxiu = nj.getNianjia();
			}
			if (mins > tiaoxiu) {// 调休时间不够
				model.addAttribute("msg", "您可用的调休时间为" + OtherUtil.miniute2String(tiaoxiu) + "，不够您这次请假。");
				return "oa/leave/askForLeave";
			}
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else if ("年假".equals(type)) {
			EmpNianjia nj = oaUtilService.getEmpNianjiaById(propUser.getId());
			int tiaoxiu = 0;
			if (nj != null) {
				tiaoxiu = nj.getNianjia();
			}

			if (mins > tiaoxiu) {// 年假时间不够
				model.addAttribute("msg", "您可用的年假时间为" + OtherUtil.miniute2String(tiaoxiu) + "，不够您这次请假。");
				return "oa/leave/askForLeave";
			}
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else {
			model.addAttribute("msg", "非法操作，请假类型错误");
			return "oa/leave/askForLeave";
		}
		leaveService.addLeave(leave);
		model.addAttribute("msg", "您的请假申请已提交，请等待审批");
		// 因为是代理请求所以，要自动审批一次。
		// if(curUser.getId() == leave.getCurrentId()){//我来审批
		// autoSp(leave, "同意（代理申请）", curUser, session);
		// }else{

		// 通知发送邮件
		User user = userService.findById(leave.getCurrentId());

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
		String toName = user.getName();

		if (email != null && email.length() != 0) {
			// 发送通知，要审批了。
			String deptName = null;

			if (propUser.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();

			sendMail(toName, email, copyTo, leave, deptName);
		}
		// }

		model.addAttribute("msg", "请假申请已提交");

		model.addAttribute("beginDate", "");
		model.addAttribute("beginHour", "");
		model.addAttribute("beginMin", "");

		model.addAttribute("endDate", "");
		model.addAttribute("endHour", "");
		model.addAttribute("endMin", "");

		leave.setType(null);
		leave.setContent("");

		return "oa/leave/askLeaveForSub";
	}

	/**
	 * 1.计算请假时长 2.获取当前时间 3.获取请假开始和结束时间
	 */
	/**
	 * 用户提交请假申请
	 * 
	 * @param leave
	 * @param model
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("oa/leave/afl")
	public String askForLeave(Leave leave, String beginDate, String beginHour, String beginMin, String endDate,
			String endHour, String endMin, String submitCode, Model model, HttpSession session) throws ParseException {
		logger.debug("oa/leave/afl");
		User loginUser = this.getLoginUser(session);
		if (leave == null || beginDate == null) {// 判断是否是提交请假单，如果不是直接去请假页面
			EmpNianjia nj = oaUtilService.getEmpNianjiaById(loginUser.getId());
			if (nj != null) {
				nj.setBingjiaStr(OtherUtil.miniute2StringWithF(nj.getBingjia()));
				nj.setNianjiaStr(OtherUtil.miniute2StringWithF(nj.getNianjia()));
			}
			model.addAttribute("nianjia", nj);
			return "oa/leave/askForLeave";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			model.addAttribute("msg", "操作失败，您正在重复提交数据。");
			return "oa/leave/askForLeave";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());

		// 获取登陆用户的所有角色
		List<Role> roles = this.getLoginUserRole(session);

		if (RoleUtil.hasRole(roles, "总经理")) {// 如果是总经理，不需要提交
			model.addAttribute("msg", "您不需要申请");
			return "oa/leave/askForLeave";
		}

		// 查询这个时间是否已经申请请假或者有外出
		String beginTime = beginDate + " " + beginHour + beginMin;
		String endTime = endDate + " " + endHour + endMin;
		leave.setBeginTime(beginTime);
		leave.setEndTime(endTime);
		leave.setProposer(loginUser.getId());
		leave.setProposerName(loginUser.getName());
		List<Leave> isBeenLeave = this.leaveService.findIsBeenLeave(leave.getProposer(), leave.getBeginTime(),
				leave.getEndTime());
		if (!isBeenLeave.isEmpty()) {
			model.addAttribute("msg", "申请失败，您在这段时间内，已经申请请假或者有外出（出差）。");
			return "oa/leave/askForLeave";
		}

		// 校验时间
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf1.parse(leave.getBeginTime()); // 开始时间
		Date end = sdf1.parse(leave.getEndTime()); // 结束时间
		Date now = new Date(); // 当前时间
		Date tempDate = new Date();// 临时使用时间
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(begin);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		Calendar tempCal = beginCal;
		//
		// if (begin.after(end) || begin.before(now)) {
		// model.addAttribute("msg", "开始时间不能早于结束时间或当前时间");
		// return "oa/leave/askForLeave";
		// }
		// 计算请假时长
		List<Day> days = dateService.findDays(sdf2.format(begin), sdf2.format(end));// 获取节假日情况
		DateUtil du = new DateUtil();
		// 计算请假的时间长度 （要严格监控这个算法，比较容易出错。另外工作日历一定要提前至少5天。）
		int mins = du.countMunitesForLeave(begin, end, days);
		// 新增连续请假
		List<Leave> leaves = this.leaveService.findByEmpAndTime(leave.getProposer(), beginTime, endTime);
		int ld = 0;
		int lh = 0;
		int lm = 0;
		for (Leave l : leaves) {
			ld += l.getDays();
			lh += l.getHours();
			lm += l.getMinutes();
		}
		int lmits = (ld * 8 * 60) + lh * 60 + lm;

		String type = leave.getType();
		boolean daili = false; // 不属于上级代理请假

		Dept dept = this.getLoginUserDept(session);
		if ("事假".equals(type) || "病假".equals(type)) {
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else if ("婚假".equals(type)) {
			String r = LeaveUtil.hunjia(leave, mins, dept, roles, beginCal, nowCal, daili);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("产假".equals(type)) {
			// 产假包括节假日等。
			String r = LeaveUtil.chanjia(leave, mins, dept, roles, beginCal, nowCal, daili, endCal);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("丧假".equals(type)) {
			String r = LeaveUtil.sangjia(leave, mins, dept, roles, daili);
			if (r != null) {
				model.addAttribute("msg", r);
				return "oa/leave/askForLeave";
			}
		} else if ("调休".equals(type)) {
			// 获取可调休时间
			EmpNianjia nj = oaUtilService.getEmpNianjiaById(loginUser.getId());
			int tiaoxiu = 0;
			if (nj != null) {
				tiaoxiu = nj.getNianjia();
			}
//			if (mins > tiaoxiu) {// 调休时间不够
//				model.addAttribute("msg", "您可用的调休时间为" + OtherUtil.miniute2String(tiaoxiu) + "，不够您这次请假。");
//				return "oa/leave/askForLeave";
//			}
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else if ("年假".equals(type)) {
			EmpNianjia nj = oaUtilService.getEmpNianjiaById(loginUser.getId());
			int tiaoxiu = 0;
			if (nj != null) {
				tiaoxiu = nj.getNianjia();
			}

//			if (mins > tiaoxiu) {// 年假时间不够
//				model.addAttribute("msg", "您可用的年假时间为" + OtherUtil.miniute2String(tiaoxiu) + "，不够您这次请假。");
//				return "oa/leave/askForLeave";
//			}
			String str = newLeave(leave, mins, lmits, dept, roles, beginCal, beginCal, daili, model, begin, nowCal, end,
					now, beginCal, tempCal);
			if (str != null) {
				model.addAttribute("msg", str);
				return "oa/leave/askForLeave";
			}
		} else {
			model.addAttribute("msg", "非法操作，请假类型错误");
			return "oa/leave/askForLeave";
		}
		leaveService.addLeave(leave);
		model.addAttribute("msg", "您的请假申请已提交，请等待审批");

		// 通知发送邮件
		User user = userService.findById(leave.getCurrentId());
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

			if (loginUser.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();

			sendMail(user.getName(), email, copyTo, leave, deptName);
		}

		return "oa/leave/askForLeave";
	}

	/**
	 * 新请假制度
	 * 
	 * @return
	 * 
	 * @return
	 */
	public String newLeave(Leave leave, int mins, int lmits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean daili, Model model, Date begin, Calendar nowCal, Date end, Date now,
			Calendar beginCal, Calendar tempCal) {
		String str = null;
		if (!daili) { // 如果是代理，不需要合符这些规定。
			if (mins <= 0) {// 请假时间没有
				str = "根据工作日历，这段时间不需要请假";
			} else if (mins <= 30) {
				// 小于等于半个小时 -30' now<end
				if (now.after(end)) {
					str = "请假结束时间不能早于当前时间";
				}
			} else if (mins <= 60) {
				// 小于一小时 0' now<start
				if (now.after(begin)) {
					str = "请假开始时间不能早于当前时间";
				}
			} else if (mins <= 120) {
				// 小于两个小时 30'
				if (beginCal.get(Calendar.HOUR_OF_DAY) == 8 && beginCal.get(Calendar.MINUTE) == 30) {
					// 等于8点30 前天17点
					tempCal = this.dateService.setCalDate(tempCal, 1);
					tempCal.add(Calendar.HOUR, 9);
					tempCal.add(Calendar.MINUTE, -30);
				} else if (beginCal.get(Calendar.HOUR_OF_DAY) == 13 && beginCal.get(Calendar.MINUTE) == 0) {
					// 等于1点 当天11点半
					tempCal.add(Calendar.HOUR, -2);
					tempCal.add(Calendar.MINUTE, 30);
				} else if (beginCal.get(Calendar.MINUTE) == 0) {
					// 整点 小时-1，分钟+30
					tempCal.add(Calendar.HOUR, -1);
					tempCal.add(Calendar.MINUTE, 30);
				} else {
					// 非整点 分钟-30
					tempCal.add(Calendar.MINUTE, -30);
				}
				if (nowCal.after(tempCal)) {
					str = "请假时间必须提前30分钟工作时长";
				}
			} else if (mins <= 240) {
				// 小于4个小时 1h
				if ((beginCal.get(Calendar.HOUR_OF_DAY) == 8 && beginCal.get(Calendar.MINUTE) == 30)
						|| ((beginCal.get(Calendar.HOUR_OF_DAY) == 9 && beginCal.get(Calendar.MINUTE) == 0))) {
					// 等于8点30 前天16点30 等于9点 前天17点
					tempCal = this.dateService.setCalDate(tempCal, 1);
					tempCal.add(Calendar.HOUR, 8);
				} else if ((beginCal.get(Calendar.HOUR_OF_DAY) == 13 && beginCal.get(Calendar.MINUTE) == 0)
						|| ((beginCal.get(Calendar.HOUR_OF_DAY) == 13 && beginCal.get(Calendar.MINUTE) == 30))) {
					// 等于13点 当天11点 等于13点30 当天11点30
					tempCal.add(Calendar.HOUR, -2);
				} else {
					// 其余时段
					tempCal.add(Calendar.HOUR, -1);
				}
				if (nowCal.after(tempCal)) {
					str = "请假时间必须提前1小时工作时长";
				}
			} else if (mins <= 480) {
				// 小于8个小时 4h
				if ((beginCal.get(Calendar.HOUR_OF_DAY) == 8 && beginCal.get(Calendar.MINUTE) == 30)
						|| ((beginCal.get(Calendar.HOUR_OF_DAY) > 8 && beginCal.get(Calendar.HOUR_OF_DAY) < 12))
						|| (beginCal.get(Calendar.HOUR_OF_DAY) == 12 && beginCal.get(Calendar.MINUTE) == 0)
						|| (beginCal.get(Calendar.HOUR_OF_DAY) == 13 && beginCal.get(Calendar.MINUTE) == 0)) {
					// 8:30 - 12:00 1:00 前天13:30-17:30
					tempCal = this.dateService.setCalDate(tempCal, 1);
					tempCal.add(Calendar.HOUR, 5);
				} else if ((beginCal.get(Calendar.HOUR_OF_DAY) > 13 && beginCal.get(Calendar.HOUR_OF_DAY) < 16)
						|| (beginCal.get(Calendar.HOUR_OF_DAY) == 16 && beginCal.get(Calendar.MINUTE) == 0)
						|| (beginCal.get(Calendar.HOUR_OF_DAY) == 13 && beginCal.get(Calendar.MINUTE) == 30)) {
					// 13:30 - 16:30 当天8:30-11:30
					tempCal.add(Calendar.HOUR, -5);
				} else {
					// 其余时段
					tempCal.add(Calendar.HOUR, -4);
				}
				if (nowCal.after(tempCal)) {
					str = "请假时间必须提前4小时工作时长";
				}
			} else if (mins <= 1280) {
				// 小于16个小时 8h
				tempCal = this.dateService.setCalDate(tempCal, 1);
				if (nowCal.after(tempCal)) {
					str = "请假时间必须提前8小时工作时长";
				}
			} else if (mins <= 2400) {
				// 小于40个小时 16h
				tempCal = this.dateService.setCalDate(tempCal, 2);
				if (nowCal.after(tempCal)) {
					str = "请假时间必须提前16小时工作时长";
				}
			} else {
				str = "请假时间不能超过40小时";
			}
		}
		int days = mins / 480;
		int minutes = mins % 480;
		int hours = minutes / 60;
		minutes = minutes % 60;
		mins += lmits;
		leave.setDays(days);
		leave.setHours(hours);
		leave.setMinutes(minutes);

		// TODO
		// 获取总监ID
		Integer directorId = this.osService.getDeptPIdByName(dept.getName());

		if (LeaveUtil.hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
			leave.setStatus(3);
			leave.setBossId(Consts.managerId); // 总经理的id必须为1
			leave.setCurrentId(Consts.managerId);
		} else if (LeaveUtil.hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管
			leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
			leave.setDirectId(directorId); // 总监的id目前定为12
			leave.setCurrentId(directorId);
			// 超过4小时需要总监批准
			if (mins >= 960) {
				leave.setBossId(Consts.managerId);
			}
		} else {// 普通员工
			leave.setStatus(1);// 主管审核
			leave.setMgrId(dept.getMgrId());
			leave.setCurrentId(dept.getMgrId());
			// 4小时-2天
			if (mins >= 240 && mins < 960) {// 总监批准
				leave.setDirectId(directorId);
			} else if (mins >= 960) {// 两天以上,总经理批准
				leave.setDirectId(directorId);
				leave.setBossId(Consts.managerId);
			}
		}
		return str;
	}

	@RequestMapping("oa/leave/myLeaves")
	public String myLeaves(Model model, HttpSession session) {

		User loginUesr = this.getLoginUser(session);// (User)session.getAttribute("loginUser");
		List<Leave> list = leaveService.findByEmpId(loginUesr.getId(), 0); // 0表示是请假
		model.addAttribute("list", list);
		return "oa/leave/myLeaves";
	}

	@RequestMapping("oa/leave/empRecords")
	public String empRecords(Integer empId, Model model, HttpSession session) {
		User emp = this.userService.findById(empId);
		// User loginUesr =
		// this.getLoginUser(session);//(User)session.getAttribute("loginUser");
		List<Leave> list = leaveService.findByEmpId(empId, 0); // 0表示是请假
		model.addAttribute("list", list);
		model.addAttribute("emp", emp);
		return "oa/leave/empRecords";
	}

	@RequestMapping("oa/leave/mydl")
	public String mydl(Model model, HttpSession session) {

		User loginUesr = this.getLoginUser(session);
		List<Leave> list = leaveService.findMyDaili(loginUesr.getId());
		model.addAttribute("list", list);
		return "oa/leave/mydl";
	}

	@RequestMapping("oa/leave/mysp")
	public String mysp(Model model, HttpSession session) {
		User loginUesr = (User) session.getAttribute("loginUser");
		List<Leave> list = leaveService.findMySp(loginUesr.getId(), 0);// 0代表请假
		model.addAttribute("list", list);
		return "oa/leave/mysp";
	}

	/**
	 * 查看销假详细 2017年6月12日
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/leave/findCancel/{id}")
	public String findLeaveCancel(@PathVariable int id, Model model, HttpSession session) {
		LeaveCancel leaveCancel = leaveService.findLeaveCancelById(id);
		int leaveId = leaveCancel.getLeaveId();
		Leave leave = this.leaveService.findById(leaveId);

		model.addAttribute("leave", leaveCancel);
		model.addAttribute("l", leave);
		return "oa/leave/findLeaveCancel";
	}

	/**
	 * 查询销假记录 2017年6月12日
	 * 
	 * @param curpage
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("oa/leave/findAllXj")
	public String findAllXj(int curpage, Model model, HttpSession session) {
		List<LeaveCancel> list = leaveService.findLeaveCancelByType(curpage, Consts.CANCEL_LEAVE);
		int totalPage = leaveService.countLeaveCancelByType(Consts.CANCEL_LEAVE);// 总页数
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curpage", curpage);
		model.addAttribute("arr", new int[totalPage]);
		return "oa/leave/listLeaveCancelSp";
	}

	@RequestMapping("oa/leave/myLeaveCancelSp")
	public String myLeaveCancelSp(Model model, HttpSession session) {
		User loginUesr = (User) session.getAttribute("loginUser");
		// List<LeaveCancel> list =
		// leaveService.findMyLeaveCancelSpTypeAll(loginUesr.getId());
		List<LeaveCancel> list = leaveService.findMyLeaveCancelSpByType(loginUesr.getId(), Consts.CANCEL_LEAVE);
		model.addAttribute("list", list);
		return "oa/leave/myLeaveCancelSp";
	}

	@RequestMapping("oa/leave/myspRecord")
	public String myspRecord(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);// (User)session.getAttribute("loginUser");
		List<SpRecord> list = leaveService.findMySpRecord(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/leave/myspRecord";
	}

	@RequestMapping("oa/leave/editCancel/{id}")
	public String editLeaveCancel(@PathVariable int id, Model model, HttpSession session) {
		LeaveCancel leaveCancel = leaveService.findLeaveCancelById(id);
		User loginUser = this.getLoginUser(session);
		int currentSpId = leaveCancel.getCurrentId();

		if (leaveCancel == null || loginUser.getId() != currentSpId) {// 不归我审批
			return "redirect:/web/oa/leave/mysp";
		}

		int leaveId = leaveCancel.getLeaveId();
		Leave leave = this.leaveService.findById(leaveId);

		model.addAttribute("leave", leaveCancel);
		model.addAttribute("l", leave);
		return "oa/leave/editLeaveCancel";
	}

	@RequestMapping("oa/leave/edit/{id}")
	public String edit(@PathVariable int id, Model model, HttpSession session) {
		Leave leave = leaveService.findById(id);
		User loginUser = this.getLoginUser(session);
		int currentSpId = leave.getCurrentId();

		if (leave == null || loginUser.getId() != currentSpId) {// 不归我审批
			return "redirect:/web/oa/leave/mysp";
		}
		model.addAttribute("leave", leave);
		return "oa/leave/editLeave";
	}

	@RequestMapping("oa/leave/info/{id}")
	public String detail(@PathVariable int id, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		Leave leave = leaveService.findById(id);
		// 非法操作
		if (leave == null || leave.getProposer() != loginUser.getId()) {
			return "redirect:/web/logout";
		}

		model.addAttribute("leave", leave);
		return "oa/leave/detail";
	}

	@RequestMapping("oa/leave/mydldeatil")
	public String mydldeatil(int id, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		Leave leave = leaveService.findById(id);
		// 非法操作
		if (leave == null || leave.getDailiId() != loginUser.getId()) {
			return "redirect:/web/logout";
		}
		model.addAttribute("leave", leave);
		return "oa/leave/dlDetail";
	}

	@RequestMapping("oa/leave/info2/{id}")
	public String detail2(@PathVariable int id, Model model, HttpSession session) {
		Leave leave = leaveService.findById(id);
		String deptname = this.userService.findById(leave.getProposer()).getDeptName();
		model.addAttribute("deptName", deptname);
		model.addAttribute("leave", leave);
		return "oa/leave/detailForEmp";
	}

	@RequestMapping("oa/leave/showForNjRecordByDate2")
	public String showForSpRecordByDate2(String dayStr, Integer id, Integer empId, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);

		if (empId == null) {
			empId = loginUser.getId();
		}

		model.addAttribute("dayStr", dayStr);

		String beginTime = dayStr + " 08时30分";
		String endTime = dayStr + " 17时30分";

		Leave leave = null;
		List<Leave> list = leaveService.findBeenLeaveByTimeAndEmp(empId, beginTime, endTime);

		if (null != id) {
			for (Leave l : list) {
				if (l.getId() == id) {
					leave = l;
				}
			}
		} else {
			if (list.size() > 0)
				leave = list.get(0);
		}

		model.addAttribute("list", list);
		model.addAttribute("size", list.size());
		model.addAttribute("leave", leave);

		return "oa/leave/show4DateNianjia";
	}

	@RequestMapping("oa/leave/showForNjRecordByDate")
	public String showForSpRecordByDate(String dayStr, Integer id, Integer empId, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);

		if (empId == null) {
			empId = loginUser.getId();
		}

		model.addAttribute("dayStr", dayStr);

		String beginTime = dayStr + " 08时30分";
		String endTime = dayStr + " 17时30分";

		Leave leave = null;
		List<Leave> list = leaveService.findBeenLeaveByTimeAndEmp(empId, beginTime, endTime);

		if (null != id) {
			for (Leave l : list) {
				if (l.getId() == id) {
					leave = l;
				}
			}
		} else {
			if (list.size() > 0)
				leave = list.get(0);
		}

		model.addAttribute("list", list);
		model.addAttribute("size", list.size());
		model.addAttribute("leave", leave);

		/*
		 * if(list ==null || list.isEmpty() ){ //当日没有请假
		 * 
		 * }else if(list.size()>1){ model.addAttribute("list",list); return
		 * "oa/leave/showForNianjia"; }else{
		 * 
		 * model.addAttribute("leave",list.get(0)); }
		 */

		return "oa/leave/showForDateNianjia";
	}

	@RequestMapping("oa/leave/showNjDetail")
	public String showNjDetail(Integer id, Model model, HttpSession session) {
		// User loginUser = this.getLoginUser(session);
		Leave leave = leaveService.findById(id);
		String deptname = this.userService.findById(leave.getProposer()).getDeptName();
		model.addAttribute("deptName", deptname);
		model.addAttribute("leave", leave);
		return "oa/leave/show4Nianjia";
	}

	@RequestMapping("oa/leave/showForNianjia/{id}")
	public String showForNianjia(@PathVariable int id, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		Leave leave = leaveService.findById(id);

		if (id == 0) {
			model.addAttribute("leave", leave);
			return "oa/leave/showForNianjia";
		}
		// 非法操作
		if (leave == null || leave.getProposer() != loginUser.getId()) {
			return "redirect:/web/logout";
		}

		model.addAttribute("leave", leave);
		return "oa/leave/showForNianjia";
	}

	@RequestMapping("oa/leave/showForSpRecord/{id}")
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
		return "oa/leave/showForSpRecord";
	}

	@RequestMapping("oa/leave/cancel")
	public String cancelLeave(Integer id, String beginDate, String beginHour, String beginMin, String submitCode,
			Model model, HttpSession session) {

		try {
			Leave leave = this.leaveService.findById(id);
			model.addAttribute("leave", leave);

			// String beginTime = leave.getBeginTime();
			String oldEndTime = leave.getEndTime();
			String endTime = beginDate + " " + beginHour + beginMin;

			leave.setEndTime(endTime);

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
			Calendar tc = Calendar.getInstance();

			// 校验时间
			if (beginTimeCal.after(endTimeCal)) {
				leave.setEndTime(oldEndTime);
				model.addAttribute("msg", "销假开始时间不能早于请假开始时间");
				return "oa/leave/cancelLeave";
			}

			tc.add(Calendar.DAY_OF_MONTH, -1);
			tc.set(Calendar.HOUR_OF_DAY, 17);
			tc.set(Calendar.MINUTE, 30);
			tc.set(Calendar.SECOND, 0);
			tc.set(Calendar.MILLISECOND, 0);

			if (tc.after(endTimeCal)) {
				leave.setEndTime(oldEndTime);
				model.addAttribute("msg", "销假开始时间不能早于昨天下班（系统考勤已经日结）");
				return "oa/leave/cancelLeave";
			}

			List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));
			DateUtil du = new DateUtil();
			int mits = du.countMunitesForLeave(d1, d2, days);

			if (mits % 30 != 0) {
				leave.setEndTime(oldEndTime);
				model.addAttribute("msg", "请重新计算销假开始时间，使请假时长为半小时的倍数");
				return "oa/leave/cancelLeave";
			}

			int ddays = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(ddays);
			leave.setHours(hours);
			leave.setMinutes(minutes);
			this.leaveService.updateLeaveForCancel(leave);
			model.addAttribute("msg", "操作成功");
			// model.addAttribute("leave",leave);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "oa/leave/cancelLeave";

	}

	// @RequestMapping("oa/leave/cancelLeave/{id}")
	// public String cancelLeave(@PathVariable int id, Model model) {
	// Leave leave = leaveService.findById(id);
	// model.addAttribute("leave", leave);
	// return "oa/leave/cancelLeave";
	// }

	@RequestMapping("oa/leave/goCancelLeave")
	public String goCancelLeave(String empName, Model model, HttpSession session) {

		// if (empId == null) {
		// return "oa/leave/goCancelLeave";
		// }
		Date date = new Date();
		String dayStr = DateUtil.getTimeString(date, Consts.chinaDateFmt);
		String beginTime = dayStr + " 08时30分";
		String endTime = dayStr + " 17时30分";

		// 查询可以销假的记录
		// List<Leave> list = this.leaveService.findLeaveByType(empId,
		// beginTime, endTime, Consts.CANCEL_LEAVE);
		List<Leave> list = this.leaveService.findLeaveByDate(beginTime, endTime, Consts.CANCEL_LEAVE);
		model.addAttribute("list", list);
		return "oa/leave/goCancelLeave";
	}

	@RequestMapping("oa/leave/sp")
	public String sp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/leave/mysp";
		}
		session.removeAttribute(Consts.submitCode);

		Leave leave = leaveService.findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = leave.getCurrentId();

		if (leave == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/leave/mysp";
		}
		int status = leave.getStatus();
		if (status == -1 || status == 4) { // 已经结束
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

		if (leave.getStatus() == 4) { // 审批通过。需要做一系列事情
			// leaveService.updateSth(leave,currentSpId, dailiId);
			// //最新请假最大的区别是，不在这里处理年假，已经每日汇总都在第二天凌晨做

			// 这里以后也要判断，如果审批是在日结过后。应该单独给这个人做之前的日结，以后单独为这个请假扣除年假。或者直接审核过期。
			String today = DateUtil.getCurrentTime("yyyy年MM月dd日");
			String beginTime = leave.getBeginTime();
			if (beginTime.compareTo(today) < 0) {// 需要扣除之前的日结
				leaveService.updateLeaveAndCountNianjia(leave, currentSpId, dailiId);
			} else {
				leaveService.updateLeave(leave, currentSpId, dailiId);
			}

		} else {
			leaveService.updateLeave(leave, currentSpId, dailiId);
		}

		User pps = userService.findById(leave.getProposer());
		User handler = userService.findById(leave.getCurrentId());

		Dept pDept = userService.findDeptById(pps.getDeptId());

		String deptName = null;
		if (leave.getProposer() == Consts.directorId) {
			deptName = "研发中心";
		}
		if (pDept != null) {
			deptName = pDept.getName();
		}

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
			String subject = "请假申请审批结果通知";
			String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, pps.getName(),
					deptName, "请假申请", ApprovalStatus.APPROVAL_ACCESS, leave);
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, text);
		} else if (leave.getStatus() == -1) {
			String to = pps.getEmail();
			String subject = "请假申请审批结果通知";
			String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, pps.getName(),
					deptName, "请假申请", ApprovalStatus.APPROVAL_NOT_ACCESS, leave);
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, text);
		} else {// 通知有审批
			String to = handler.getEmail();
			String subject = "请假申请审批通知";
			String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, handler.getName(),
					leave.getProposerName(), deptName, "请假申请", null, leave);
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject, text);
		}

		return "redirect:/web/oa/leave/mysp?msg=1";
	}

	// 获取请假的用户
	@RequestMapping("leave/alu")
	@ResponseBody
	public Integer[] ajaxLeaveUser() {
		String n = DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分");
		List<Integer> uids = this.userService.findUserOnLeave(n);
		return uids.toArray(new Integer[] {});
	}

	@RequestMapping("leave/ajaxUld")
	public String ajaxUld(int uid, Model model, HttpSession session) {

		// 判断是否直接上级，如果不是，不能点击
		List<Role> roles = this.getLoginUserRole(session);
		User loginUser = this.getLoginUser(session);

		boolean flag = false;

		if (loginUser.getId() == Consts.mgrOfXz || loginUser.getId() == 60) {// 崔莺莺，许路
			flag = true;
		}

		if (!flag) {
			List<User> subs = this.userService.findSubordinates(loginUser.getId(), roles);
			for (User u : subs) {
				if (u.getId() == uid) {
					flag = true;
					break;
				}
			}
		}

		if (!flag) {
			model.addAttribute("msg", "not");
			return "oa/leave/ajaxUld";
		}

		String n = DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分");
		List<Leave> ls = this.leaveService.findLeaveByUserAndTime(uid, n);

		String msg = null;
		if (!ls.isEmpty()) {
			Leave l = ls.get(0);
			if (l.getWaichu() == 0) {
				msg = "员工：" + l.getProposerName() + "，请假时间【" + l.getBeginTime() + "-->" + l.getEndTime() + "】，请假类型："
						+ l.getType() + "，原因【" + l.getContent() + "】";
			} else {
				msg = "员工：" + l.getProposerName() + "，外出时间【" + l.getBeginTime() + "-->" + l.getEndTime() + "】，外出类型："
						+ l.getType() + "，原因【" + l.getContent() + "】";
			}
			model.addAttribute("msg", msg);
		} else {
			model.addAttribute("msg", "没有相关数据，请刷新页面重试。");
		}

		return "oa/leave/ajaxUld";
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

	private LeaveCancel xjSpNext(LeaveCancel leave, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = leave.getDirectId();
			if (did == null) {// 审批结束
				leave.setStatus(4);
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

	private void sendMail(String toName, String to, String copyTo, Leave leave, String deptName) {
		String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, toName,
				leave.getProposerName(), deptName, "请假申请", null, leave);
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "请假申请审批通知", text);
	}

	private void sendMailForXj(String name, String to, String copyTo, String deptName, Leave leave,
			LeaveCancel leaveCancel) {
		if (deptName == null)
			deptName = "";
		String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, name,
				leaveCancel.getProposerName(), deptName, "销假申请", null, leaveCancel, leave);
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "员工销假审批", text);
	}

	private String buildMailForXj(String name, String deptName, Leave leave, LeaveCancel leaveCancel) {
		if (deptName == null)
			deptName = "";
		return MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, name,
				leaveCancel.getProposerName(), deptName, "销假申请", null, leaveCancel, leave);
	}

	private String buildAccessMailForXj(Leave leave, LeaveCancel leaveCancel) {
		return MailContentBuilder
				.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null,
						leaveCancel.getProposerName(), null, "请假注销申请", (leaveCancel.getStatus() == 4
								? ApprovalStatus.APPROVAL_ACCESS : ApprovalStatus.APPROVAL_NOT_ACCESS),
						leaveCancel, leave);
	}

	@RequestMapping("oa/leave/cancel1")
	public String cancelLeaveTest(Integer id, String content, String beginDate, String beginHour, String beginMin,
			String endDate, String endHour, String endMin, Integer type, String submitCode, Model model,
			HttpSession session) {
		try {
			Leave leave = this.leaveService.findById(id);// 原始请假信息
			LeaveCancel leaveCancel = new LeaveCancel();
			leaveCancel.setLeaveId(leave.getId());
			leaveCancel.setContent(content);
			leaveCancel.setCreateTime(DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
			leaveCancel.setWaichu(leave.getWaichu());
			leaveCancel.setType(leave.getType());
			leaveCancel.setProposerName(leave.getProposerName());
			leaveCancel.setProposer(leave.getProposer());
			leaveCancel.setDeptId(leave.getDeptId());
			leaveCancel.setUpdateId(leave.getProposer());
			leaveCancel.setUpdateName(leave.getProposerName());
			leaveCancel.setCancelType(type);
			if (type == 0) {
				String cancelBeginTime = beginDate + " " + beginHour + beginMin;
				String cancelEndTime = endDate + " " + endHour + endMin;
				leaveCancel.setBeginTime(cancelBeginTime);
				leaveCancel.setEndTime(cancelEndTime);
			} else {
				leaveCancel.setBeginTime(leave.getBeginTime());
				leaveCancel.setEndTime(leave.getBeginTime());
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date d2 = sdf1.parse(leaveCancel.getBeginTime());
			Date d4 = sdf1.parse(leaveCancel.getEndTime());

			Calendar tc = Calendar.getInstance();

			tc.add(Calendar.DAY_OF_MONTH, -1);
			tc.set(Calendar.HOUR_OF_DAY, 17);
			tc.set(Calendar.MINUTE, 30);
			tc.set(Calendar.SECOND, 0);
			tc.set(Calendar.MILLISECOND, 0);

			if (tc.after(leaveCancel.getBeginTime())) {
				model.addAttribute("msg", "销假开始时间不能早于昨天下班（系统考勤已经日结）");
				return "oa/leave/goCancelLeave";
			}

			List<Day> days = dateService.findDays(sdf2.format(d2), sdf2.format(d4));
			DateUtil du = new DateUtil();
			int mits = du.countMunitesForLeave(d2, d4, days);

			if (mits % 30 != 0) {
				model.addAttribute("msg", "请重新计算销假开始时间，使请假时长为半小时的倍数");
				return "oa/leave/goCancelLeave";
			}
			// 根据时间长短，结算审批的流程
			List<Role> roles = this.roleService.findRolesByEmpId(leaveCancel.getProposer());
			User ppuser = this.userService.findById(leaveCancel.getProposer());
			Dept dept = null;
			if (leaveCancel.getDeptId() != null) {
				dept = this.userService.findDeptById(leaveCancel.getDeptId());
			}

			LeaveUtil.xiaojia(leaveCancel, mits, dept, roles);

			this.leaveService.addLeaveCancel(leaveCancel);
			model.addAttribute("msg", "销假申请已提交，请等待审批。");

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
				if (dept != null)
					deptName = dept.getName();
				sendMailForXj(user.getName(), email, copyTo, deptName, leave, leaveCancel);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "redirect:/web/oa/leave/goCancelLeave?msg=1";
	}
	
//	@RequestMapping(value = "oa/leave/cancel2", method = RequestMethod.POST)
//	public String cancelApplyLeave(Integer id) {
//		// 取消请假
//		this.leaveService.cancelLeaveById(id);
//		// 添加
//		return "redirect:/web/oa/leave/goCancelLeave?msg=1";
//	}
	
	
	
	

	// 备份
	@RequestMapping("oa/leave/cancel1Old")
	public String cancel1Old(Integer id, String content, String beginDate, String beginHour, String beginMin,
			String submitCode, Model model, HttpSession session) {

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
			leaveCancel.setBeginTime(cancelBeginTime);
			leaveCancel.setEndTime(leave.getEndTime());

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
				model.addAttribute("msg", "销假开始时间不能早于请假开始时间");
				return "oa/leave/cancelLeave";
			}

			tc.add(Calendar.DAY_OF_MONTH, -1);
			tc.set(Calendar.HOUR_OF_DAY, 17);
			tc.set(Calendar.MINUTE, 30);
			tc.set(Calendar.SECOND, 0);
			tc.set(Calendar.MILLISECOND, 0);

			if (tc.after(newBeginTimeCal)) {
				model.addAttribute("msg", "销假开始时间不能早于昨天下班（系统考勤已经日结）");
				return "oa/leave/cancelLeave";
			}

			List<Day> days = dateService.findDays(sdf2.format(d2), sdf2.format(d4));
			DateUtil du = new DateUtil();
			int mits = du.countMunitesForLeave(d2, d4, days);

			if (mits % 30 != 0) {
				model.addAttribute("msg", "请重新计算销假开始时间，使请假时长为半小时的倍数");
				return "oa/leave/cancelLeave";
			}

			// 根据时间长短，结算审批的流程
			List<Role> roles = this.roleService.findRolesByEmpId(leaveCancel.getProposer());
			User ppuser = this.userService.findById(leaveCancel.getProposer());
			Dept dept = null;
			if (leaveCancel.getDeptId() != null) {
				dept = this.userService.findDeptById(leaveCancel.getDeptId());
			}

			LeaveUtil.xiaojia(leaveCancel, mits, dept, roles);

			this.leaveService.addLeaveCancel(leaveCancel);
			model.addAttribute("msg", "销假申请已提交，请等待审批。");

			// 发送邮件通知
			User user = userService.findById(leave.getCurrentId());
			// 别人代理我的
			User bdlUser = roleService.findDailiByOwnerId(user.getId());
			String copyTo = null;

			if (bdlUser != null) {
				List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), user.getId());
				if (OtherUtil.containsMenu(Consts.qjsp, menus)) {

					copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
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
				if (dept != null)
					deptName = dept.getName();
				sendMailForXj(leave.getProposerName(), email, copyTo, deptName, leave, leaveCancel);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "oa/leave/cancelLeave";

	}

	@RequestMapping("oa/leave/spLeaveCancel")
	public String spLeaveCancel(int id, String yj, String sp, String submitCode, Model model, HttpSession session)
			throws ParseException {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/leave/myLeaveCancelSp";
		}
		session.removeAttribute(Consts.submitCode);

		LeaveCancel leave = leaveService.findLeaveCancelById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = leave.getCurrentId();

		if (leave == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/leave/myLeaveCancelSp";
		}
		int status = leave.getStatus();
		if (status == -1 || status == 4) { // 已经结束
			return "redirect:/web/oa/leave/myLeaveCancelSp";
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

		if (leave.getStatus() == 4) { // 审批通过。需要做一系列事情

			Leave l = this.leaveService.findById(leave.getLeaveId());

			if (leave.getCancelType() == 0) {
				l.setEndTime(leave.getEndTime());
				l.setBeginTime(leave.getBeginTime());
			}
				if(leave.getBeginTime().equals(leave.getEndTime())) {
					this.leaveService.cancelLeaveById(leave.getLeaveId());
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
	
				// this.leaveService.updateLeaveForCancel(l);
				leaveService.updateLeaveCancel(leave, l, currentSpId, dailiId);
		} else {
			leaveService.updateLeaveCancel(leave, null, currentSpId, dailiId);
			// leaveService.updateLeave(leave, currentSpId, dailiId);
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
		Leave l = this.leaveService.findById(leave.getLeaveId());
		if (leave.getStatus() == 4) { // 结束
			String to = pps.getEmail();
			String subject = "销假审核通知";
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, buildAccessMailForXj(l, leave));
		} else if (leave.getStatus() == -1) {
			String to = pps.getEmail();
			String subject = "销假审核通知";
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, buildAccessMailForXj(l, leave));
		} else {// 通知有审批
			String deptName = "";
			if (pps.getId() == Consts.directorId) {
				deptName = "研发中心";
			}
			if (dept != null)
				deptName = dept.getName();

			String to = handler.getEmail();
			String subject = "员工销假审批";
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject,
					buildMailForXj(handler.getName(), deptName, l, leave));
		}

		return "redirect:/web/oa/leave/myLeaveCancelSp?msg=1";
	}
}
