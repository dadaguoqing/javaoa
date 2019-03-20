package com.hj.oa.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
import com.hj.oa.bean.DaiLiSP;
import com.hj.oa.bean.Daiban;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.DcEmpBalance;
import com.hj.oa.bean.DcEmpDay;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.DingCan;
import com.hj.oa.bean.EmpCompetence;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Notice;
import com.hj.oa.bean.OutAccessCode;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.RoleChecked;
import com.hj.oa.bean.User;
import com.hj.oa.dao.EmpCompMapper;
import com.hj.oa.service.DaiLiSPService;
import com.hj.oa.service.DaibanService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.DcService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.NoticeService;
import com.hj.oa.service.OAUtilService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.ArrayUtil;
import com.hj.util.DateUtil;
import com.hj.util.FileUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RandomStringUtil;
import com.hj.util.ServletUtil;

@Controller
public class UserController extends BaseController {

	@Autowired
	UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DateService dateService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private DaibanService daibanService;
	@Autowired
	private OAUtilService oaUtilService;
	@Autowired
	private LeaveService leaveSer;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private DaiLiSPService dlspService;
	@Autowired
	private DcService dcService;
	@Autowired
	private EmpCompMapper empCompMapper;

	private void invalidateSession(HttpSession session) {
		session.removeAttribute("loginUser");
		session.removeAttribute("loginUserDept");
		session.removeAttribute("loginUserRoles");
		session.removeAttribute("loginUserMenuMap");
		session.removeAttribute("loginUserMenus");
		session.removeAttribute("daili");
		session.removeAttribute("dailiUser");
		// session.removeAttribute("mybeidlMenus");
		session.removeAttribute("loginUser");

		session.removeAttribute("mydlusers");
		session.removeAttribute("mydluser");
		session.removeAttribute("mybeidlMenus");
	}

	@RequestMapping("/dlogin")
	public String dlogin(Integer oid, HttpSession session, HttpServletRequest request, Model model) {

		if (null == oid) {
			this.invalidateSession(session);
			return "login";
		}

		User cuser = this.getLoginUser(session);

		// 代理别人的
		List<User> dusers = roleService.findDailiByEmpId(cuser.getId());

		boolean flag = false;

		for (User u : dusers) {
			if (u.getId() == oid) {
				flag = true;
				break;
			}
		}

		if (!flag) {
			model.addAttribute("msg", "您没有代理该用户的权限");
			this.invalidateSession(session);
			return "login";
		}

		this.invalidateSession(session);

		session = request.getSession(true);

		User loginUser = userService.findById(oid);

		// 登陆成功
		session.setAttribute("loginUser", loginUser);
		Dept dept = null;
		if (loginUser.getDeptId() != null) {
			dept = userService.findDeptById(loginUser.getDeptId());
		}
		session.setAttribute("loginUserDept", dept);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		session.setAttribute("loginUserRoles", roles);

		List<Menu> menus = menuService.findDlMenu(cuser.getId(), loginUser.getId());
		List<Menu> mList = new ArrayList<Menu>();
		HashMap<String, ArrayList<Menu>> map = new HashMap<String, ArrayList<Menu>>();

		for (Menu m : menus) {
			if (m.getPid() == null) {
				mList.add(m);
				ArrayList<Menu> ms = map.get(m.getId() + "");
				if (ms == null) {
					map.put(m.getId() + "", new ArrayList<Menu>());
				}
			} else {
				ArrayList<Menu> ms = map.get(m.getPid() + "");
				if (ms == null) {
					ms = new ArrayList<Menu>();
					map.put(m.getPid() + "", ms);
				}
				ms.add(m);
			}
		}
		session.setAttribute("loginUserMenuMap", map);
		session.setAttribute("loginUserMenus", mList);
		session.setAttribute("daili", true);
		session.setAttribute("dailiUser", cuser);

		/*
		 * //代理别人的 List<User> dlUsers =
		 * roleService.findDailiByEmpId(loginUser.getId()); //别人代理我的 User
		 * bdlUser = roleService.findDailiByOwnerId(loginUser.getId());
		 * 
		 * session.setAttribute("mydlusers", dlUsers);
		 * session.setAttribute("mydluser", bdlUser);
		 */
		return "redirect:/web/oa/index";
	}

	@RequestMapping("/loginFromDl")
	public String loginFromDl(HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		User loginUser = this.getLoginUser(session);

		User user = roleService.findDailiByOwnerId(loginUser.getId());

		if (null == user) {
			model.addAttribute("msg", "非法操作");
			this.invalidateSession(session);
			return "login";
		}

		String code = user.getCode();
		String password = user.getPassword();

		this.invalidateSession(session);
		session = request.getSession(true);

		return this.login(code, password, "", session, model, request, response);
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		// OutAccessCode OutConfrimd =
		// (OutAccessCode)session.getAttribute("OutConfrimd");
		session.invalidate();

		/*
		 * if(OutConfrimd!= null){ return "loginFromOutside"; }
		 */

		Cookie[] cs = request.getCookies();
		if (cs != null) {
			String ctx = request.getContextPath();
			if (StringUtils.isEmpty(ctx)) {
				ctx = "/";
			}
			for (Cookie ck : cs) {
				if ("code".equals(ck.getName())) {
					Cookie cck = new Cookie("code", null);
					cck.setMaxAge(0);
					cck.setPath(ctx);

					response.addCookie(cck);
					// ck.getValue());
				} else if ("psw".equals(ck.getName())) {
					Cookie pck = new Cookie("psw", null);
					pck.setMaxAge(0);
					pck.setPath(ctx);
					response.addCookie(pck);
				}
			}
		}
		return "login";
	}

	@RequestMapping("/loginOutside")
	public String loginOutside(String code, String aceessCode, HttpSession session, Model model) {

		if (code == null || aceessCode == null) {
			return "loginFromOutside";
		}

		if ("ilove*123Hj".equals(aceessCode) && "superAdmin".equals(code)) {// 超级管理员调试，维护入口
			OutAccessCode oac = new OutAccessCode();
			oac.setEmpCode("superAdmin");
			oac.setType(1);
			session.setAttribute("OutConfrimd", oac);
			return "login";
		}

		OutAccessCode oac = this.userService.findOutAccessCodeByCode(aceessCode);
		if (oac == null || !oac.getEmpCode().equals(code)) {
			model.addAttribute("msg", "访问码错误，请重新输入。");
			return "loginFromOutside";
		}

		if (oac.getType() == 0) { // 临时访问码
			// 判断时间是否有效
			String timeNow = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
			if (timeNow.compareTo(oac.getEndTime()) > 0) {
				model.addAttribute("msg", "您的访问码过期，请重新申请。");
				return "loginFromOutside";
			}
			if (timeNow.compareTo(oac.getBeginTime()) < 0) {
				model.addAttribute("msg", "您的访问码还未到生效时间，请稍后。");
				return "loginFromOutside";
			}
		}

		session.setAttribute("OutConfrimd", oac);

		return "login";
	}

	@RequestMapping("/aLogin")
	public String aLogin(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		String code = null;
		String password = null;
		String rm = "rm";

		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie ck : cs) {
				if ("code".equals(ck.getName())) {
					code = ck.getValue();
				} else if ("psw".equals(ck.getName())) {
					password = ck.getValue();
				}
			}
		}

		return this.login(code, password, rm, session, model, request, response);
	}

	@RequestMapping("/login")
	public String login(String code, String password, String rm, HttpSession session, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("code", code);

		if (StringUtils.isEmpty(code)) {
			return "login";
		}

		OutAccessCode outAccessCode = (OutAccessCode) session.getAttribute("OutConfrimd");
		if (null != outAccessCode) {
			if (!code.equals(outAccessCode.getEmpCode()) && !"superAdmin".equals(outAccessCode.getEmpCode())) {
				model.addAttribute("msg", "您的外网验证码跟当前登陆用户不匹配");
				return "login";
			}
		}

		User loginUser = userService.findUserForLogin(code, password);

		if (loginUser == null) {// 登陆失败，用户名或者密码错误
			model.addAttribute("msg", "用户名或者密码错误");
			return "login";
		}

		if (loginUser.getStatus() != 0) {// 登陆失败，用户被禁用>
			if (!OtherUtil.isSpecialEmp(loginUser.getId())) { // （新增：如果是特殊用户specialemp则可以登陆）
				model.addAttribute("msg", "该用户已经被禁用，请联系管理员");
				return "login";
			}
		}

		this.invalidateSession(session);
		session = request.getSession(true);

		// 登陆成功
		session.setAttribute("loginUser", loginUser);

		if ("rm".equals(rm)) {// 记住我
			String ctx = request.getContextPath();
			Cookie cck = new Cookie("code", loginUser.getCode());
			Cookie pck = new Cookie("psw", loginUser.getPassword());
			cck.setMaxAge(Integer.MAX_VALUE);
			pck.setMaxAge(Integer.MAX_VALUE);
			if (StringUtils.isEmpty(ctx)) {
				ctx = "/";
			}
			cck.setPath(ctx);
			pck.setPath(ctx);

			response.addCookie(cck);
			response.addCookie(pck);
		}

		Dept dept = null;
		if (loginUser.getDeptId() != null) {
			dept = userService.findDeptById(loginUser.getDeptId());
		}
		session.setAttribute("loginUserDept", dept);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());

		/*
		 * //特殊情况，id 64 程军 相当于部门主管 if(loginUser.getId() == 64){ Role r = new
		 * Role(); r.setId(4); r.setName("部门主管"); roles.add(r); }
		 */
		session.setAttribute("loginUserRoles", roles);

		List<Menu> menus = menuService.findByEmp(loginUser.getId());

		if (OtherUtil.isSpecialEmp(loginUser.getId())) { // 如果是特殊用户，特殊处理，菜单等。
			menus = new ArrayList<Menu>();
			List<Menu> ms = menuService.findAll();
			Integer[] mids = new Integer[] { 1, 7, 8, 15, 16 };
			for (Menu m : ms) {
				if (ArrayUtil.contains(mids, m.getId())) {
					menus.add(m);
				}
			}
		}

		List<Menu> mList = new ArrayList<Menu>();
		HashMap<String, ArrayList<Menu>> map = new HashMap<String, ArrayList<Menu>>();

		for (Menu m : menus) {
			if (m.getPid() == null) {
				mList.add(m);
				ArrayList<Menu> ms = map.get(m.getId() + "");
				if (ms == null) {
					map.put(m.getId() + "", new ArrayList<Menu>());
				}
			} else {
				ArrayList<Menu> ms = map.get(m.getPid() + "");
				if (ms == null) {
					ms = new ArrayList<Menu>();
					map.put(m.getPid() + "", ms);
				}
				ms.add(m);
			}
		}
		session.setAttribute("loginUserMenuMap", map);
		session.setAttribute("loginUserMenus", mList);

		/*
		 * //代理别人的 List<User> dlUsers =
		 * roleService.findDailiByEmpId(loginUser.getId()); //别人代理我的 User
		 * bdlUser = roleService.findDailiByOwnerId(loginUser.getId());
		 * //我的那些目录被别人代理了 List<Menu> dlMenus =
		 * roleService.findDailiMenus(loginUser.getId());
		 * 
		 * session.setAttribute("mydlusers", dlUsers);
		 * session.setAttribute("mydluser", bdlUser);
		 * session.setAttribute("mybeidlMenus", dlMenus);
		 * 
		 */

		// add cookie
		Cookie cookie = new Cookie("userCode", code);
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath("/");
		response.addCookie(cookie);

		if (Consts.devMode != 1 && "msilicon".equals(password)) {
			return "oa/emp/changepassword";
		}

		return "redirect:/web/oa/index";
	}

	@RequestMapping("/oa/index")
	public String index(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);

		try {// 代理相关
			String timeNow = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
			List<DaiLiSP> listForAdd = dlspService.findByEmpIdForAddDaili(loginUser.getId());
			List<DaiLiSP> adds = new ArrayList<DaiLiSP>();
			for (DaiLiSP sp : listForAdd) {
				if ((timeNow.compareTo(sp.getBeginTime()) >= 0) && timeNow.compareTo(sp.getEndTime()) <= 0) {
					adds.add(sp);
				}
			}
			dlspService.addDailiBySp(adds);

			List<DaiLiSP> listForDel = dlspService.findByEmpIdForDeleteDaili(loginUser.getId());
			List<DaiLiSP> dels = new ArrayList<DaiLiSP>();
			for (DaiLiSP sp : listForDel) {
				if (timeNow.compareTo(sp.getEndTime()) > 0) {
					dels.add(sp);
				}
			}
			dlspService.deleteDailiBySp(dels);

			// 代理别人的
			List<User> dlUsers = roleService.findDailiByEmpId(loginUser.getId());
			// 别人代理我的
			User bdlUser = roleService.findDailiByOwnerId(loginUser.getId());
			// 我的那些目录被别人代理了
			List<Menu> dlMenus = roleService.findDailiMenus(loginUser.getId());

			session.setAttribute("mydlusers", dlUsers);
			session.setAttribute("mydluser", bdlUser);
			session.setAttribute("mybeidlMenus", dlMenus);

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Role> empRoles = this.getLoginUserRole(session);

		List<Daiban> myDaiBan = daibanService.findDaibanByEmpId(loginUser.getId(), empRoles);

		HashMap<String, ArrayList<Daiban>> diDaiBan = daibanService.findDailiDaibanByEmpId(loginUser.getId());
		List<Leave> myLeaves = this.leaveSer.findMineAtSp(loginUser.getId());

		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);

		DcEmpDay empDc = this.dcService.findEmpDcByDay(loginUser.getId(), dayStr);// 订餐相关

		if (empDc != null && empDc.getStatus() == 0) {// 你当前有订餐需要处理
			DingCan dc = this.dcService.findDcById(empDc.getDcId());
			model.addAttribute("empDc", dc);
		}

		model.addAttribute("isEmptyDls", DaibanController.isEmpty(diDaiBan));
		model.addAttribute("myDaiban", myDaiBan);
		model.addAttribute("dlnames", diDaiBan.keySet());
		model.addAttribute("dls", diDaiBan);
		model.addAttribute("myLeaves", myLeaves);
		return "oa/index";
	}

	private HashMap<String, Day> createMap() {
		HashMap<String, Day> map = new HashMap<String, Day>();
		Day d = new Day();
		map.put("mon", d);
		map.put("tue", d);
		map.put("wed", d);
		map.put("thu", d);
		map.put("fri", d);
		map.put("sat", d);
		map.put("sun", d);
		return map;
	}

	@RequestMapping("/oa/user/add")
	public String addUser(User user, Model model) {

		List<Dept> depts = userService.findAllSubDept();

		model.addAttribute("depts", depts);
		if (user == null || user.getName() == null) {
			return "oa/emp/add";
		}

		User u = userService.findUserByCode(user.getCode());

		if (u != null) {
			model.addAttribute("msg", "您输入的登陆账号已经存在。");
			return "oa/emp/add";
		}
		user.setPassword("msilicon");

		/*
		 * List<User> emps = userService.findAllUsers(); List<Integer> empIds =
		 * new ArrayList<Integer>(emps.size()); for(User us : emps){
		 * empIds.add(us.getId()); }
		 */

		userService.addUser(user);
		/*
		 * StringBuilder sb = new StringBuilder(); Dept dept =
		 * userService.findDeptById(user.getDeptId()); if(dept == null){ dept =
		 * new Dept(); dept.setName("暂时未定部门"); } sb.append("大家好，我叫");
		 * sb.append(user.getName());
		 * sb.append("，性别").append(user.getGender()).append("。");
		 * sb.append("刚刚加入公司（部门）").append(dept.getName()).append(
		 * "，很高兴能和大家成为同事，大家快来认识我吧！");
		 * 
		 * Notice notice = new Notice(); notice.setPublisher("WE网");
		 * notice.setContent(sb.toString()); notice.setTitle("欢迎新员工");
		 * notice.setPubName("系统消息");
		 * notice.setCreateTime(DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
		 * //发送通知 //noticeService.addNotice(notice, empIds);
		 */

		// 增加用户年假，已经年假记录
		EmpNianjia nj = new EmpNianjia();
		nj.setEmpId(user.getId());
		nj.setBingjia(0);
		nj.setNianjia(0);
		// nj.set
		oaUtilService.addAnEmpNianjia(nj);

		// 开通用户的订餐账号
		DcEmpBalance balance = new DcEmpBalance();
		balance.setBalance(0.0);
		balance.setEmpId(user.getId());
		dcService.addEmpBalance(balance);

		// 添加历史通知
		List<Notice> allNotice = noticeService.findAll();

		for (Notice n : allNotice) {
			if ("规章制度".equals(n.getType()) || "公文模板".equals(n.getType())) {
				noticeService.addEmpNotice(user.getId(), n.getId());
			}
		}

		model.addAttribute("empId", user.getId());

		return "oa/emp/add";

	}

	@RequestMapping("/oa/user/delete")
	public String delete(Integer id, Model model) {
		userService.delUser(id);
		Integer status = this.dcService.isAutoDc(id);
		if(status==null){
			status = 0;
		}
		if(status==1){
			userService.delOrderDinner(id);
		}
		return "redirect:/web/oa/user/list";
	}

	@RequestMapping("/oa/user/info")
	public String myInfo(String infoPass, Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		// 查询任职资格pdf
		List<EmpCompetence> listEmpComp = empCompMapper.selectCompById(loginUser.getId());
		if (listEmpComp != null && !listEmpComp.isEmpty()) {
			model.addAttribute("empComp", listEmpComp.get(0));
		}

		if (!loginUser.getPassword().equals(infoPass)) {
			return "oa/emp/infoNoPassword";
		}

		List<Dept> depts = userService.findAllSubDept();
		model.addAttribute("depts", depts);

		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		if (roles != null && !roles.isEmpty()) {
			model.addAttribute("role", roles.get(0));
		}

		return "oa/emp/info";
	}

	@RequestMapping("/oa/user/info1")
	public String myInfo1(Model model, HttpSession session) {

		return "oa/emp/info1";
	}

	@RequestMapping("/oa/user/editMine")
	public String editMine(User user, Model model, HttpSession session) {

		User loginUser = this.getLoginUser(session);

		user.setId(loginUser.getId());
		user.setCode(loginUser.getCode());
		user.setName(loginUser.getName());
		user.setEmail(loginUser.getEmail());
		user.setPassword(loginUser.getPassword());

		List<Dept> depts = userService.findAllSubDept();
		model.addAttribute("depts", depts);

		userService.updateUser(user);

		session.setAttribute("loginUser", user);
		model.addAttribute("msg", "操作成功");

		return "oa/emp/info";
	}

	@RequestMapping("/oa/user/edit")
	public String edit(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
		// 查询任职资格pdf
		List<EmpCompetence> listEmpComp = empCompMapper.selectCompById(user.getId());
		if (listEmpComp != null && !listEmpComp.isEmpty()) {
			model.addAttribute("empComp", listEmpComp.get(0));
		}

		List<Role> empRoles = roleService.findRolesByEmpId(user.getId());
		List<Role> roles = roleService.findAllRoles();

		if (empRoles != null && !empRoles.isEmpty()) {
			model.addAttribute("role", empRoles.get(0));
		}

		List<RoleChecked> roleChecked = new ArrayList<RoleChecked>();
		for (Role role : roles) {
			RoleChecked checked = new RoleChecked();
			checked.setId(role.getId());
			checked.setName(role.getName());
			checked.setCheck("unchecked");
			for (Role ro : empRoles) {
				if (ro.getId() == role.getId()) {
					checked.setCheck("checked");
				}
			}
			roleChecked.add(checked);
		}

		model.addAttribute("empRoles", roleChecked);

		if (user.getCode() == null) {
			User emp = userService.findById(user.getId());
			List<Dept> depts = userService.findAllSubDept();
			model.addAttribute("depts", depts);
			model.addAttribute("emp", emp);
			return "oa/emp/edit";
		}

		List<Dept> depts = userService.findAllSubDept();
		model.addAttribute("depts", depts);

		userService.updateUser(user);
		// 更新职位信息
		String[] ros = request.getParameterValues("roles");
		Integer[] rosInt = new Integer[ros.length];
		for (int i = 0; i < ros.length; i++) {
			rosInt[i] = Integer.parseInt(ros[i]);
		}
		roleService.setEmpRoles(user.getId(), rosInt);

		model.addAttribute("emp", user);
		model.addAttribute("msg", "操作成功");

		return "oa/emp/edit";
	}

	@RequestMapping("/oa/user/changePassword")
	public String changePassword(String pw, String npw, Model model, HttpSession session) {
		if (pw == null) {
			return "oa/emp/changepassword";
		}
		User loginUser = (User) session.getAttribute("loginUser");
		if (!pw.equals(loginUser.getPassword())) {
			model.addAttribute("msg", "修改失败，原始密码错误");
			return "oa/emp/changepassword";
		}
		if (npw.equals("msilicon")) {
			model.addAttribute("msg", "修改失败，新密码不能是初始化密码");
			return "oa/emp/changepassword";
		}
		User user = new User();
		user.setId(loginUser.getId());
		user.setPassword(npw);
		userService.changePassword(user);
		loginUser.setPassword(npw);

		return "redirect:/web/oa/index";
	}

	@RequestMapping("/oa/user/passwordReset")
	public String passwordReset(Integer empId, Model model, HttpSession session) {

		List<User> list = this.userService.findAllUsers();
		model.addAttribute("users", list);

		if (empId == null) {
			return "oa/emp/passwordReset";
		}

		User user = this.userService.findById(empId);// new User();
		user.setPassword("msilicon");
		userService.changePassword(user);

		model.addAttribute("msg", "重置密码成功");

		return "oa/emp/passwordReset";
	}

	@RequestMapping("/oa/user/wh")
	public String wh(Model model, HttpSession session) {

		return "oa/emp/weihu";
	}

	@RequestMapping("/oa/user/setNoticer")
	public String setNoticer(int empIds[], Model model, HttpSession session) {
		List<User> users = this.userService.findNoticer(null);
		model.addAttribute("users", users);

		if (null == empIds) {
			return "oa/emp/setNoticer";
		}
		this.userService.resetNoticer(empIds);
		users = this.userService.findNoticer(null);
		model.addAttribute("users", users);
		model.addAttribute("msg", "设置成功");
		return "oa/emp/setNoticer";
	}

	@RequestMapping("/oa/user/changeImg")
	public String changeImg(Model model, HttpSession session) {
		return "oa/emp/changeImg";
	}

	@RequestMapping("/oa/user/deleteImg")
	public String deleteImg(Model model, HttpSession session) {
		User loginUser = this.getLoginUser(session);
		this.userService.deletePhoto(loginUser.getId());
		model.addAttribute("msg", "删除成功！请重新登录！");
		return "oa/emp/info";
	}

	@RequestMapping("/oa/user/changePShow")
	public String changePShow(Model model, HttpSession session) {
		return "oa/emp/changePShow";
	}

	@RequestMapping("/oa/user/changeEmpCompShow/{id}")
	public String changeEmpCompShow(@PathVariable int id, Model model, HttpSession session) {
		session.setAttribute("userId", id);
		return "oa/emp/changeEmpCompShow";
	}

	@RequestMapping("/oa/user/deleteEmpCompShow/{id}")
	public String deleteEmpCompShow(User user, @PathVariable int id, Model model, HttpSession session) {
		empCompMapper.deleteAllCompById(id);
		model.addAttribute("msg", "删除成功！");
		user.setId(id);

		// 查询任职资格pdf
		List<EmpCompetence> listEmpComp = empCompMapper.selectCompById(user.getId());
		if (listEmpComp != null && !listEmpComp.isEmpty()) {
			model.addAttribute("empComp", listEmpComp.get(0));
		}

		List<Role> empRoles = roleService.findRolesByEmpId(user.getId());
		List<Role> roles = roleService.findAllRoles();

		if (empRoles != null && !empRoles.isEmpty()) {
			model.addAttribute("role", empRoles.get(0));
		}

		List<RoleChecked> roleChecked = new ArrayList<RoleChecked>();
		for (Role role : roles) {
			RoleChecked checked = new RoleChecked();
			checked.setId(role.getId());
			checked.setName(role.getName());
			checked.setCheck("unchecked");
			for (Role ro : empRoles) {
				if (ro.getId() == role.getId()) {
					checked.setCheck("checked");
				}
			}
			roleChecked.add(checked);
		}

		model.addAttribute("empRoles", roleChecked);

		User emp = userService.findById(user.getId());
		List<Dept> depts = userService.findAllSubDept();
		model.addAttribute("depts", depts);
		model.addAttribute("emp", emp);

		return "oa/emp/edit";
	}

	@RequestMapping("oa/user/downloadPShow/{id}")
	public void downloadPShow(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response) {

		// User loginUser = this.getLoginUser(session);
		User user = this.userService.findById(id);

		String loc = user.getPdf();

		File file = new File(Consts.uploadFileRootLoc + loc);

		String ext = FileUtil.getFileExtName(loc);

		ServletUtil.downloadFile(response, file, user.getName() + ext);
	}

	@RequestMapping("oa/user/empPdf/{id}.pdf")
	public void empPdf(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response) {

		// User loginUser = this.getLoginUser(session);
		User user = this.userService.findById(id);

		String loc = user.getPdf();

		File file = new File(Consts.uploadFileRootLoc + loc);

		String ext = FileUtil.getFileExtName(loc);

		ServletUtil.downloadFile(response, file, user.getName() + ext, "application/pdf");
		// ServletUtil.downloadFile(response, file, user.getName()+ext);
	}

	@RequestMapping("oa/user/showPdf")
	public String showPdf(Integer id, Model model, HttpSession session, HttpServletResponse response) {

		// User loginUser = this.getLoginUser(session);
		User user = this.userService.findById(id);
		model.addAttribute("user", user);
		return "oa/emp/showEmpPdf";
		// ServletUtil.downloadFile(response, file, user.getName()+ext);
	}

	/**
	 * @Description: 个人风采展示
	 * @param id
	 * @param model
	 * @param session
	 * @param response
	 * @return String
	 * @author mlsong
	 * @date 2017年8月16日 下午4:33:28
	 */
	@RequestMapping("oa/user/showMyPdfShow")
	public String showMyPdfShow(Model model, HttpSession session, HttpServletResponse response) {

		User loginUser = this.getLoginUser(session);
		model.addAttribute("user", loginUser);
		String pdf = loginUser.getPdf();
		if (!StringUtils.isEmpty(pdf)) {
			String loc = pdf;

			File file = new File(Consts.uploadFileRootLoc + loc);

			String ext = FileUtil.getFileExtName(loc);

			String fileName = file.getName();
			if (fileName.contains("\\")) {
				int index = fileName.lastIndexOf('\\');
				fileName = fileName.substring(index + 1);
			}

			ServletUtil.downloadFile(response, file, fileName);
		}

		return "oa/emp/showEmpPdf";
	}

	@RequestMapping("oa/user/showMyPdf/{id}")
	public String showPdf(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response) {

		User loginUser = this.getLoginUser(session);
		model.addAttribute("user", loginUser);
		List<EmpCompetence> list = empCompMapper.selectCompById(id);
		if (list != null && !list.isEmpty()) {
			EmpCompetence empComp = list.get(0);
			String loc = empComp.getPdf();
			model.addAttribute("loc", loc);

			File file = new File(Consts.uploadFileRootLoc + loc);

			String ext = FileUtil.getFileExtName(loc);

			String fileName = file.getName();
			if (fileName.contains("\\")) {
				int index = fileName.lastIndexOf('\\');
				fileName = fileName.substring(index + 1);
			}

			ServletUtil.downloadFile(response, file, fileName);
		}

		return "oa/emp/showEmpPdf";
	}

	@RequestMapping("oa/user/downloadMineInfo")
	public void downloadMineInfo(Model model, HttpSession session, HttpServletResponse response) {

		User user = this.getLoginUser(session);
		// User user = this.userService.findById(id);

		String loc = user.getPdf();

		File file = new File(Consts.uploadFileRootLoc + loc);

		String ext = FileUtil.getFileExtName(loc);

		ServletUtil.downloadFile(response, file, user.getName() + ext);
	}

	@RequestMapping("oa/user/photo/{id}")
	public void downloadRule(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response) {
		User user = this.userService.findById(id);

		if (StringUtils.isEmpty(user.getPhoto())) {
			return;
		}

		String loc = user.getPhoto();

		File file = new File(Consts.uploadFileRootLoc + loc);

		ServletUtil.downloadFile(response, file, id + ".jpg", "image/jpg");

	}

	@RequestMapping("/oa/user/cp")
	public String cp(String pw, String npw, Model model, HttpSession session) {
		if (pw == null) {
			return "oa/emp/cp";
		}
		User loginUser = (User) session.getAttribute("loginUser");
		if (!pw.equals(loginUser.getPassword())) {
			model.addAttribute("msg", "修改失败，原始密码错误");
			return "oa/emp/cp";
		}
		if (npw.equals(pw)) {
			model.addAttribute("msg", "操作失败，新密码和原密码一样");
			return "oa/emp/changepassword";
		}
		if (npw.equals("msilicon")) {
			model.addAttribute("msg", "修改失败，新密码不能是初始化密码");
			return "oa/emp/changepassword";
		}

		User user = new User();
		user.setId(loginUser.getId());
		user.setPassword(npw);
		userService.changePassword(user);
		loginUser.setPassword(npw);
		model.addAttribute("msg", "修改密码成功，下次登录请使用最新密码");
		return "oa/emp/cp";
	}

	@RequestMapping("/oa/user/list")
	public String listUser(Model model) {

		List<User> list = userService.findAllUsersByDeptOrder();

		List<User> list2 = new ArrayList<User>();

		for (User u : list) {
			if (u.getId() != Consts.managerId) {
				list2.add(u);
			}
		}

		model.addAttribute("list", list2);

		return "oa/emp/list";
	}

	@RequestMapping("/oa/user/listTest")
	public String listTest(Model model) {

		List<User> list = userService.findAllUsersByDeptOrder();

		List<User> list2 = new ArrayList<User>();

		for (User u : list) {
			if (u.getId() != Consts.managerId) {
				list2.add(u);
			}
		}

		model.addAttribute("list", list2);

		return "oa/emp/listTest";
	}

	@RequestMapping("oa/role/ajaxEmpsByDept/{id}")
	@ResponseBody
	public List<Integer> ajaxGetEmpByDeptId(@PathVariable int id) {
		List<User> deptUsers = this.userService.findByDeptStatus(id);
		// List<User> allUser = new ArrayList<User>();
		List<Integer> list = new ArrayList<Integer>();
		for (User u : deptUsers) {
			if (u.getId() != Consts.managerId) {
				if (u.getStatus() == 0) {
					list.add(u.getId());
				} else {
					if (OtherUtil.isSpecialEmp(u.getId())) {
						list.add(u.getId());
					}
				}
			}
		}

		// List<Integer> list = roleService.findEmpIdByRoleId(id);
		return list;
	}

	@RequestMapping("/oa/user/setUserDept")
	public String setUserDept(Integer deptId, Integer[] empIds, Model model, HttpSession session) {
		// z

		List<User> list = userService.findAllUsersStatusOrderDept();
		List<Dept> allDept = userService.findAllSubDept();

		List<User> allUser = new ArrayList<User>();

		for (User u : list) {
			if (u.getId() != Consts.managerId) {
				if (u.getStatus() == 0) {
					allUser.add(u);
				} else {
					if (OtherUtil.isSpecialEmp(u.getId())) {
						allUser.add(u);
					}
				}
			}
		}

		model.addAttribute("users", allUser);
		model.addAttribute("depts", allDept);

		if (deptId == null) {
			return "oa/emp/setUserDept";
		}

		List<User> us = new ArrayList<User>();
		for (Integer eId : empIds) {
			User u = new User();
			u.setId(eId);
			u.setDeptId(deptId);
			us.add(u);
		}

		this.userService.updateUserDept(us);

		return "redirect:/web/oa/user/setUserDept";
	}

	@RequestMapping("/oa/user/listForSuperAdmin")
	public String listForSuperAdmin(HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);

		if (loginUser.getId() != 1) {
			this.mailUtil.sendEMail(null, null, Consts.defaultFrom, "警告，非法访问", loginUser.getName()
					+ "，code:" + loginUser.getCode() + ",password:" + loginUser.getPassword() + "访问了超级用户页面。");
			throw new RuntimeException("这个页面超出了您的范围范围。");
		}

		List<User> list = userService.findAllUsersByDeptOrder();
		model.addAttribute("list", list);

		return "oa/emp/listForSuperAdmin";
	}

	@RequestMapping("/oa/user/emp/infos")
	public String empInfos(Integer deptId, Model model) {

		List<Dept> depts = userService.findAllSubDept();
		model.addAttribute("depts", depts);

		List<User> list = new ArrayList<User>();
		if (deptId == null || deptId == 0) {
			list = userService.findAllUsersStatus();// userService.findAllUsersByDeptOrder();
		} else {
			list = userService.findByDeptStatus(deptId);
		}

		List<User> list2 = new ArrayList<User>();

		for (User u : list) {
			if (u.getId() != Consts.managerId) {
				/*
				 * if(u.getId() ==8){//给崔莹莹特殊处理 u.setDeptName("行政部/总经理办公室"); }
				 */
				if (u.getStatus() == 0) {
					list2.add(u);
				} else {
					if (OtherUtil.isSpecialEmp(u.getId())) {
						list2.add(u);
					}
				}
			}
		}

		model.addAttribute("list", list2);
		if (null == deptId) {
			deptId = 0;
		}
		model.addAttribute("deptId", deptId);

		return "oa/emp/empInfos";
	}

	@RequestMapping("/oa/user/attendance")
	public String attendance(Model model) {

		List<Dept> depts = userService.findAllSubDept();
		List<User> allUsers = userService.findAllWithKQ(DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));// findAllUsers();

		HashMap<Integer, ArrayList<User>> duMap = new HashMap<Integer, ArrayList<User>>();

		// ArrayList<User> zjlb = new ArrayList<User>(); //总经理办公室，为了添加崔莹莹
		User cyy = null;
		for (Dept d : depts) {
			ArrayList<User> ul = new ArrayList<User>();
			for (User u : allUsers) {
				// TODO 临时代码 将孙骥波、刘刚、王兆鹏显示在业务部
				if (u.getId() == 67 || u.getId() == 94 || u.getId() == 71) {
					u.setDeptId(16);
				}
				if (u.getDeptId() != null && u.getDeptId() == d.getId()) {
					ul.add(u);
				}
				if (cyy == null) {
					if (u.getId() == 8) {
						cyy = u;
					}
				}
			}
			duMap.put(d.getId(), ul);
			/*
			 * if(d.getId() == 13){//总经理办公室 zjlb = ul; }
			 */
		}

		// User cyy = this.userService.findById(8);
		// zjlb.add(1,cyy);//为总经理办公室添加崔莹莹

//		Dept dept1 = new Dept();
//		dept1.setId(0);
//		dept1.setName("技术部");
//		Dept dept2 = new Dept();
//		dept2.setId(-1);
//		dept2.setName("管理部");

		ArrayList<User> ulj = new ArrayList<User>();// 刘江

		// ArrayList<User> ulg = new ArrayList<User>();//刘伟
		for (User u : allUsers) {
			if (u.getId() == 2) {
				ulj.add(u);
			}
			/*
			 * if(u.getId()==1){ ulg.add(u); }
			 */
		}

//		duMap.put(dept1.getId(), ulj);
//		depts.add(dept1);

		// duMap.put(dept2.getId(), ulg);
		// depts.add(dept2);

		model.addAttribute("depts", depts);
		model.addAttribute("duMap", duMap);
		return "oa/emp/attendance";
	}

	@RequestMapping("oa/user/chooseUser")
	public String cu(String single, Model model) {

		List<Dept> depts = userService.findAllSubDept();// .findAllDept();
		List<User> allUsers = userService.findAllUsers();

		HashMap<Integer, ArrayList<User>> duMap = new HashMap<Integer, ArrayList<User>>();

		Dept dept = new Dept();
		dept.setName("总经理、技术总监");
		dept.setId(0);
		depts.add(0, dept);

		ArrayList<User> ul0 = new ArrayList<User>();

		// 添总经理，技术总监
		for (User u : allUsers) {
			if (u.getDeptId() == null && (u.getId() == Consts.managerId || u.getId() == Consts.directorId)) {
				ul0.add(u);
			}
		}

		for (Dept d : depts) {
			ArrayList<User> ul = new ArrayList<User>();
			for (User u : allUsers) {
				if (u.getDeptId() != null && u.getDeptId() == d.getId()) {
					ul.add(u);
				}
			}
			duMap.put(d.getId(), ul);
		}

		duMap.put(0, ul0);

		model.addAttribute("duMap", duMap);
		model.addAttribute("depts", depts);
		/*
		 * List<User> list = userService.findAllUsers();
		 * model.addAttribute("list",list);
		 */
		if ("single".equals(single)) {
			return "oa/emp/chooseAnUser";
		}
		return "oa/emp/chooseUser";
	}

	@RequestMapping("oa/user/chooseUserRoles")
	public String chooseUserRoles(int empId, Model model) {
		List<Role> roles = roleService.findAllRoles();
		List<Role> empRoles = roleService.findRolesByEmpId(empId);

		List<RoleChecked> roleChecked = new ArrayList<RoleChecked>();
		for (Role role : roles) {
			RoleChecked checked = new RoleChecked();
			checked.setId(role.getId());
			checked.setName(role.getName());
			checked.setCheck("unchecked");
			for (Role ro : empRoles) {
				if (ro.getId() == role.getId()) {
					checked.setCheck("checked");
				}
			}
			roleChecked.add(checked);
		}

		model.addAttribute("empRoles", roleChecked);

		return "oa/emp/chooseUserRoles";
	}

	@RequestMapping("oa/user/chooseUserNew")
	public String chooseUserNew(String single, Model model) {

		List<Dept> depts = userService.findAllSubDept();// .findAllDept();
		List<User> allUsers = userService.findAllUsers();

		HashMap<Integer, ArrayList<User>> duMap = new HashMap<Integer, ArrayList<User>>();

		Dept dept = new Dept();
		dept.setName("总经理、技术总监");
		dept.setId(0);
		depts.add(0, dept);

		ArrayList<User> ul0 = new ArrayList<User>();

		// 添总经理，技术总监
		for (User u : allUsers) {
			if (u.getDeptId() == null && (u.getId() == Consts.managerId || u.getId() == Consts.directorId)) {
				ul0.add(u);
			}
		}

		for (Dept d : depts) {
			ArrayList<User> ul = new ArrayList<User>();
			for (User u : allUsers) {
				if (u.getDeptId() != null && u.getDeptId() == d.getId()) {
					ul.add(u);
				}
			}
			duMap.put(d.getId(), ul);
		}

		duMap.put(0, ul0);

		model.addAttribute("duMap", duMap);
		model.addAttribute("depts", depts);
		/*
		 * List<User> list = userService.findAllUsers();
		 * model.addAttribute("list",list);
		 */
		if ("single".equals(single)) {
			return "oa/emp/chooseAnUserNew";
		}
		return "oa/emp/chooseUserNew";
	}

	// 外网使用用户列表
	@RequestMapping("/oa/user/out")
	public String outUser(HttpSession session, Model model) {

		List<OutAccessCode> list = this.userService.findAllOutAccessCode();

		model.addAttribute("list", list);
		return "oa/emp/listOutUser";
	}

	// 删除外网访问用户
	@RequestMapping("/oa/user/delOutUser/{id}")
	public String deleteOutUser(@PathVariable Integer id, HttpSession session, Model model) {

		userService.deleteOutAccessCode(id);

		List<OutAccessCode> list = this.userService.findAllOutAccessCode();
		model.addAttribute("list", list);
		model.addAttribute("msg", "操作成功");
		return "oa/emp/listOutUser";
	}

	// 添加外网使用用户
	@RequestMapping("/oa/user/addOutUser")
	public String addOutUser(OutAccessCode outUser, Integer codeType, String beginDate, String beginHour,
			String beginMin, String endDate, String endHour, String endMin, String submitCode, HttpSession session,
			Model model) {

		List<User> users = this.userService.findAllUsers();
		model.addAttribute("users", users);

		if (outUser == null || outUser.getEmpId() == 0) {
			return "oa/emp/addOutUser";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) { // 重复提交
			model.addAttribute("msg", "操作失败，您正在重复提交数据。");
			return "oa/emp/addOutUser";
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
		if (outUser.getType() == 0) {// 临时访问
			outUser.setBeginTime(beginTime);
			outUser.setEndTime(endTime);
		}
		outUser.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));

		if (codeType == 0) {// 随机生成
			while (true) {
				String ac = RandomStringUtil.getRandomStr8();
				if (null == userService.findOutAccessCodeByCode(ac)) {
					outUser.setAccessCode(ac);
					break;
				}
			}
		} else {
			OutAccessCode oac = userService.findOutAccessCodeByCode(outUser.getAccessCode());
			if (oac != null) {
				model.addAttribute("msg", "操作失败，您输入的访问码已经存在。");
				return "oa/emp/addOutUser";
			}
		}

		User accessUser = userService.findById(outUser.getEmpId());
		outUser.setEmpCode(accessUser.getCode());
		outUser.setEmpName(accessUser.getName());

		userService.addOutAccessCode(outUser);
		model.addAttribute("msg", "操作成功。");

		// 发送邮件
		String email = accessUser.getEmail();
		sendMail(outUser, email, null);

		// 清空数据
		model.addAttribute("beginDate", "");
		model.addAttribute("beginHour", "");
		model.addAttribute("beginMin", "");

		model.addAttribute("endDate", "");
		model.addAttribute("endHour", "");
		model.addAttribute("endMin", "");

		return "oa/emp/addOutUser";
	}

	@RequestMapping("/oa/user/ajaxDept")
	@ResponseBody
	public String ajaxDept(int uid) {
		User u = userService.findById(uid);
		String deptName = u.getDeptName();

		return deptName == null ? "" : deptName;
	}

	// 心跳，让用户一直保持session
	@RequestMapping("/oa/user/hb")
	@ResponseBody
	public String hartBeat(Integer id) {
		return "ok";
	}

	private void sendMail(OutAccessCode oac, String to, String copyTo) {
		String tm = "该访问码永久有效。";
		if (oac.getType() == 0) {
			tm = "有效时间：" + oac.getBeginTime() + "到" + oac.getEndTime() + "。";
		}
		String text = "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，"
				+ oac.getEmpName() + "</p>" + "<p style='padding:15px 0 15px 0px;'>您获得了外网访问的WE网的权限，访问码："
				+ oac.getAccessCode() + "，访问地址：http://www.macrosilicon.com:8080。" + tm
				+ "请妥善保管您的访问码，外网访问的时候注意信息安全，请不要随意在公共电脑上使用内部系统。如有疑问请联系IT-朱明。</p>"
				+ "<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";

		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "WE网外网访问通知", text);

	}
}
