package com.hj.oa.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.oa.Consts;
import com.hj.oa.bean.AuthSP;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.DaiLiSP;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.dao.RoleMapper;
import com.hj.oa.service.AuthSPService;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.DaiLiSPService;
import com.hj.oa.service.KqService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.ArrayUtil;
import com.hj.util.DateUtil;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;

@Controller
public class SPController extends BaseController {

	@Autowired
	private AuthSPService aspService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private DaiLiSPService dlspService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private KqService kqService;
	
	@Autowired
	private CheckInService checkInSer;
	
	public static String strToJ(String []a){
		String str = "";
		for(String b:a){
			str += b +",";
		}
		return str;
	}
	
	//补考勤申请
	@RequestMapping("oa/kq/sq")
	public String kqsq(BKqSp sp, String submitCode, HttpSession session, Model model){
		//显示当前月份的考勤记录
		
		List<User> users = userService.findAllUsers();
		model.addAttribute("users",users);
		
		if(sp == null || sp.getDayStr() == null){ //申请补考勤
			return "oa/kq/sq";
		}
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "oa/kq/sq";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		
		//User loginUser = this.getLoginUser(session);
		User propUser = this.userService.findById(sp.getProposer());
		String deptName = OtherUtil.getDeptName(propUser, this.userService);
		CheckIn ci = checkInSer.findByDayAndEmp(sp.getDayStr().split(",")[0], propUser.getId());
		
		if(ci == null){
			model.addAttribute("msg","申请失败，系统中没有该用户当日考勤数据。");
			return "oa/kq/sq";
		}
		sp.setProposerName(propUser.getName());
		
		List<Role> roles = this.roleService.findRolesByEmpId(sp.getProposer());//this.getLoginUserRole(session);
		
		if(RoleUtil.hasRole(roles, "总经理")){
			model.addAttribute("msg","您不需要申请");
			return "oa/kq/sq";
		}else if(RoleUtil.hasRole(roles, "技术总监")){
			sp.setSpId(Consts.managerId);
		}else if(RoleUtil.hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(sp.getProposer())){
			Integer deptId = propUser.getDeptId();
			Dept dept = null;//this.userService.findDeptById(deptId);
			if(null != deptId || deptId != 0){
				dept = this.userService.findDeptById(deptId);
			}
			
			if(dept != null && dept.getPid() == Consts.techDeptId){
				sp.setSpId(Consts.directorId);
			}else{
				sp.setSpId(Consts.managerId);
			}
		}else{//普通员工
			Integer deptId = propUser.getDeptId();
			
			if(deptId == null){
				sp.setSpId(Consts.managerId);
			}else{
				Dept dept = this.userService.findDeptById(deptId);
				sp.setSpId(dept.getMgrId());
			}
		}
		
		
		kqService.addBKqSp(sp);
		
		model.addAttribute("msg","您的申请已经提交，请等待审批");
		
		User spUser = userService.findById(sp.getSpId());
		
		String content = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, spUser.getName(), propUser.getName(), deptName, "打卡不签申请", null, sp);
		
		mailUtil.sendForNotice(spUser, userService, roleService, menuService, content, "补考勤申请");
		
		return "oa/kq/sq";
	}
	
	@RequestMapping("oa/kq/sqInsert")
	public String sqInsert(int style,String content,int proposer,String[] dayStrs,
			String submitCode, HttpSession session, Model model,HttpServletRequest request){
		//显示当前月份的考勤记录
		List<User> users = userService.findAllUsers();
		model.addAttribute("users",users);
		
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "oa/kq/sq";
		}
		String[]  checkins = request.getParameterValues("checkin"); 
		String checkouts[] = request.getParameterValues("checkout"); 
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		
		//User loginUser = this.getLoginUser(session);
		User propUser = this.userService.findById(proposer);
		String deptName = OtherUtil.getDeptName(propUser, this.userService);
		
		if(dayStrs== null){
			model.addAttribute("msg","申请失败，系统中没有该用户当日考勤数据。");
			return "oa/kq/sq";
		}
		BKqSp sp = new BKqSp();
		sp.setProposerName(propUser.getName());
		sp.setProposer(propUser.getId());
		List<Role> roles = this.roleService.findRolesByEmpId(sp.getProposer());//this.getLoginUserRole(session);
		
		if(RoleUtil.hasRole(roles, "总经理")){
			model.addAttribute("msg","您不需要申请");
			return "oa/kq/sq";
		}else if(RoleUtil.hasRole(roles, "技术总监")){
			sp.setSpId(Consts.managerId);
		}else if(RoleUtil.hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(sp.getProposer())){
			Integer deptId = propUser.getDeptId();
			Dept dept = null;//this.userService.findDeptById(deptId);
			if(null != deptId || deptId != 0){
				dept = this.userService.findDeptById(deptId);
			}
			
			if(dept != null && dept.getPid() == Consts.techDeptId){
				sp.setSpId(Consts.directorId);
			}else{
				sp.setSpId(Consts.managerId);
			}
		}else{//普通员工
			Integer deptId = propUser.getDeptId();
			
			if(deptId == null){
				sp.setSpId(Consts.managerId);
			}else{
				Dept dept = this.userService.findDeptById(deptId);
				sp.setSpId(dept.getMgrId());
			}
		}
		sp.setProposer(proposer);
		sp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
		sp.setContent(content);
		sp.setStatus(0);
		sp.setStyle(style+"");
		for(int i=0;i<dayStrs.length;i++){
			sp.setDayStr(dayStrs[i]);
			sp.setCheckin(checkins[i]);
			sp.setCheckout(checkouts[i]);
			this.kqService.insertBKqSp(sp);
		}
		
		model.addAttribute("msg","您的申请已经提交，请等待审批");
		
		User spUser = userService.findById(sp.getSpId());
		
		String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, spUser.getName(), propUser.getName(), deptName, "打卡补签申请", null, sp);
		mailUtil.sendForNotice(spUser, userService, roleService, menuService, text, "打卡补签申请审批通知");
		
		return "oa/kq/sq";
	}
	
	
	//TODO
	@RequestMapping("oa/kq/sqList")
	public String kqsqList(int style,int proposer ,String dayStr1,String dayStr2, String submitCode, HttpSession session, Model model){
		//显示当前月份的考勤记录
		CheckIn ci = new CheckIn();
		ci.setProposer(proposer);
		User selectUser = this.userService.findById(proposer);
		Dept dept = this.userService.findDeptById(selectUser.getDeptId());
		ci.setBegin(dayStr1);
		ci.setEnd(dayStr2);
		List<CheckIn> list = this.kqService.findCheckInByEmpAndDay(ci);
		for(CheckIn li:list){
			if(li.getCheckin()==null){
				li.setCheckin("未打卡");
			}
			if(li.getCheckout()==null){
				li.setCheckout("未打卡");
			}
		}
		List<User> users = userService.findAllUsers();
		model.addAttribute("users",users);
		model.addAttribute("dept",dept);
		model.addAttribute("selectUser",selectUser);
		model.addAttribute("style",style);
		model.addAttribute("begin",dayStr1);
		model.addAttribute("end",dayStr2);
		model.addAttribute("list",list);
		return "oa/kq/sq";
	}
	
	@RequestMapping("oa/kq/mysp")
	public String mybkqsp(Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		
		List<BKqSp> list = kqService.findMySp(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/kq/mysp";
	}
	
	@RequestMapping("oa/kq/mysq")
	public String mybkqs1(String style,int curpage,Model model, HttpSession session){
		List<BKqSp> list = kqService.findBKqSq(style,curpage);
		int totalPage = kqService.countBkqSq(style);
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("style", style);
		model.addAttribute("curpage", curpage);
		model.addAttribute("arr", new int[totalPage]);
		return "oa/kq/mysq";
	}
	

	@RequestMapping("oa/kq/spRecord")
	public String mybkqspRecord(Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		
		List<BKqSp> list = kqService.findMySpRecord(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/kq/myspRecord";
	}
	
	@RequestMapping("oa/kq/edit")
	public String mybkqspRecord(int id, Model model, HttpSession session){
		
		BKqSp bqk = this.kqService.findBKqById(id);
		User loginUser = this.getLoginUser(session);
		
		if(bqk!=null && bqk.getSpId()!=loginUser.getId()){
			return "redirect:/web/oa/kq/mysp";
		}
		model.addAttribute("bqk", bqk);
		return "oa/kq/edit";
	}
	
	@RequestMapping("oa/kq/sp")
	public String kqsp(int id, String yj, String sp, Model model, HttpSession session){
		
		BKqSp bkq = this.kqService.findBKqById(id);
		
		User loginUser = this.getLoginUser(session);
		if(bkq ==null || bkq.getSpId() != loginUser.getId()){//不归我审批
			return "redirect:/web/oa/kq/mysp";
		}
		
		int status = bkq.getStatus();
		if(status != 0){//已经结束
			return "redirect:/web/oa/kq/mysp";
		}
		
		bkq.setCmt(yj);
		
		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		String dailiName = null;
		if(isDaili){
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
			dailiName = dlUser.getName();
		}
		
		bkq.setDailiId(dailiId);
		bkq.setDailiName(dailiName);
		bkq.setSpTime(DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
		if("审批通过".equals(sp)){
			bkq.setStatus(1);
			CheckIn ci = this.checkInSer.findByDayAndEmp(bkq.getDayStr(), bkq.getProposer());
			if("未打卡".equals(bkq.getCheckin())){
				ci.setCheckin(null);
				ci.setCheckinInt(0);
			}else{
				ci.setCheckin(bkq.getCheckin());
				ci.setCheckinInt(OtherUtil.time2Int(bkq.getCheckin()));
			}
			if("未打卡".equals(bkq.getCheckout())){
				ci.setCheckout(null);
				ci.setCheckoutInt(0);
			}else{
				ci.setCheckout(bkq.getCheckout());
				ci.setCheckoutInt(OtherUtil.time2Int(bkq.getCheckout()));
			}
			this.checkInSer.updateCheckIn(bkq, bkq.getDayStr(), ci);
			//kqService.updateBKqSp(bkq);
		}else{
			bkq.setStatus(-1);
			kqService.updateBKqSp(bkq);
		}
		status = bkq.getStatus();
		
		//发送邮件
		User pps = userService.findById(bkq.getProposer());
		
		String to = pps.getEmail();
		String subject = "补考勤审核通知";
		
		String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, pps.getName(), null, "打卡补签申请", (bkq.getStatus() == 1 ? ApprovalStatus.APPROVAL_ACCESS : ApprovalStatus.APPROVAL_NOT_ACCESS ), bkq);
		
		mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, text);
		
		return "redirect:/web/oa/kq/mysp";
	}
	
	//代理权限申请
	@RequestMapping("oa/asp/dlsq")
	public String dailisq(HttpSession session,String beginDate, String beginHour, String beginMin,
			String endDate, String endHour, String endMin, String submitCode,
			Model model, Integer uid, Integer[] mids, String reason){
		
		List<User> usres = userService.findAllUsers();
		List<Menu> ms = this.getLoginUserMenus(session);
		HashMap<String,ArrayList<Menu>> mp = this.getLoginUserMenuMap(session);
		
		HashMap<Integer,ArrayList<Menu>> map2 = new HashMap<Integer,ArrayList<Menu>>();
		
		Set<String> set = mp.keySet();
		
		for(String sid : set){
			map2.put(Integer.parseInt(sid), mp.get(sid));
		}
		
		model.addAttribute("shouye", Consts.shouyeId);
		model.addAttribute("tuichu", Consts.tuchuId);
		model.addAttribute("usres", usres);
		model.addAttribute("menus", ms);
		model.addAttribute("map",map2);
		
		if(mids != null && mids.length>0){ //用户提交申请了
			
			User emp = userService.findById(uid); //代理人
			
			User loginUser = this.getLoginUser(session);
			//查询是否已经被代理了，
			User user = roleMapper.findDailiByOwnerId(loginUser.getId());
			if(user != null){
				model.addAttribute("msg","您已经设置过代理了，不能重复申请。");
				return "oa/asp/dailisq";
			}
			
			
			List<Integer> mList = Arrays.asList(mids);
			
			List<Role> roles = this.getLoginUserRole(session);
			if(RoleUtil.hasRole(roles, "总经理")){//直接提交
				
				DaiLiSP dlsp = new DaiLiSP();
				
				dlsp.setUid(uid);
				dlsp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
				dlsp.setSender(loginUser.getName());
				dlsp.setSenderId(loginUser.getId());
				dlsp.setReason(reason);
				dlsp.setMids(ArrayUtil.toString(",", mids));
				dlsp.setStatus(1);
				
				model.addAttribute("beginDate",beginDate);
				model.addAttribute("beginHour",beginHour);
				model.addAttribute("beginMin",beginMin);
				
				model.addAttribute("endDate",endDate);
				model.addAttribute("endHour",endHour);
				model.addAttribute("endMin",endMin);
				
				String beginTime = beginDate + " " + beginHour + beginMin;
				String endTime = endDate + " " + endHour + endMin;
				
				dlsp.setBeginTime(beginTime);
				dlsp.setEndTime(endTime);
				dlsp.setLifeStatus(0);

				List<Menu> menus = menuService.findAll();
				Map<Integer,Menu> map = new HashMap<Integer,Menu>();
				for(Menu m : menus){
					map.put(m.getId(), m);
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append(loginUser.getName());
				sb.append("申请将权限[");
				
				for(int j=0; j<mList.size(); j++ ){
					Integer i = mList.get(j);
					Menu m = map.get(i);
					sb.append(m.getName());
					if(j!= mList.size()-1 ){
						sb.append(",");
					}
				}
				sb.append("]临时赋予");
				sb.append(emp.getName());
				sb.append("。");
				dlsp.setContent(sb.toString());
				
				dlsp.setCmt("同意（自己的申请）");
				
				dlspService.add(dlsp);
				//roleService.addDaili(uid, mList, loginUser.getId());
				model.addAttribute("msg","操作成功");
			}else{
				//查询是否有正在审核的申请
				List<DaiLiSP> zzsp = this.dlspService.findMineAtSP(loginUser.getId());
				if(zzsp.size()>0){
					model.addAttribute("msg","您有一个申请正在审核，不能重复申请。");
					return "oa/asp/dailisq";
				}
				
				DaiLiSP dlsp = new DaiLiSP();
				
				dlsp.setUid(uid);
				dlsp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
				dlsp.setSender(loginUser.getName());
				dlsp.setSenderId(loginUser.getId());
				dlsp.setStatus(0);
				dlsp.setReason(reason);
				dlsp.setMids(ArrayUtil.toString(",", mids));
				
				model.addAttribute("beginDate",beginDate);
				model.addAttribute("beginHour",beginHour);
				model.addAttribute("beginMin",beginMin);
				
				model.addAttribute("endDate",endDate);
				model.addAttribute("endHour",endHour);
				model.addAttribute("endMin",endMin);
				
				String beginTime = beginDate + " " + beginHour + beginMin;
				String endTime = endDate + " " + endHour + endMin;
				
				dlsp.setBeginTime(beginTime);
				dlsp.setEndTime(endTime);
				dlsp.setLifeStatus(0);//0-未使用，1正在使用，-1 过期。
				
				List<Menu> menus = menuService.findAll();
				Map<Integer,Menu> map = new HashMap<Integer,Menu>();
				for(Menu m : menus){
					map.put(m.getId(), m);
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append(loginUser.getName());
				sb.append("申请将权限[");
				StringBuilder sbb = new StringBuilder();
				for(int j=0; j<mList.size(); j++ ){
					Integer i = mList.get(j);
					Menu m = map.get(i);
					sb.append(m.getName());
					sbb.append(m.getName());
					if(j!= mList.size()-1 ){
						sb.append(",");
						sbb.append(",");
					}
				}
				sb.append("]临时赋予");
				sb.append(emp.getName());
				sb.append("。");
				dlsp.setContent(sb.toString());
				
				//添加一个审批
				dlspService.add(dlsp);
				
				User boss = userService.findById(Consts.managerId);
				String table = buildAuthProxyTable(emp.getName(), sbb.toString(), beginTime, endTime, reason);
				String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, boss.getName(), loginUser.getName(), loginUser.getDeptName(), "权限代理申请", null, table);
				mailUtil.sendForNotice(boss, userService, roleService, menuService, text, "权限代理申请");
				
				
				return "redirect:/web/oa/asp/dlsq?msg=1";
			}
		}
		
		return "oa/asp/dailisq";
	}
	
	
	/**
	 * @Title: dlRecord   
	 * @Description: 代理记录
	 * @param: @param session
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/asp/dlRecord" , method = RequestMethod.GET)
	public String dlRecord(HttpSession session,Model model) {
		try {
			User user = this.getLoginUser(session);
			List<DaiLiSP> lists = this.dlspService.findMyApply(user.getId());
			model.addAttribute("list", lists);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/asp/dlRecord";
	}
	
	//代理权限审批列表
	@RequestMapping("oa/asp/mydlsp")
	public String mydlsp(Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		/*if(loginUser.getId() != Consts.managerId){
			return "oa/asp/mysp";
		}*/
		List<DaiLiSP> list = dlspService.findMySP();
		model.addAttribute("list", list);
		return "oa/asp/mydlsp";
	}
	
	//代理权限审批
	@RequestMapping("oa/asp/dlsp/{id}/{sp}")
	public String dlsp(@PathVariable int id, @PathVariable int sp,String yj ){
		String cmt = null;
		try {
			cmt = new String(yj.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		DaiLiSP dlsp = this.dlspService.findById(id);
//		AuthSP asp = this.aspService.findById(id);
		//asp.setId(id);
		if(sp == 1){
			dlsp.setStatus(sp);
			dlsp.setCmt(cmt);
		}else{
			dlsp.setStatus(-1);
			dlsp.setCmt(cmt);
		}
		dlspService.update(dlsp, roleService);
		
		//发送邮件通知所有相关的人
		User sender = this.userService.findById(dlsp.getSenderId());
		User dler = this.userService.findById(dlsp.getUid());
		
		String to = sender.getEmail();
		String copyTo = dler.getEmail();
		
		String text = null;
		String content = dlsp.getContent();
		content = content.substring(content.indexOf("[") + 1, content.indexOf("]"));
		String table = buildAuthProxyTable(dler.getName(), content, dlsp.getBeginTime(), dlsp.getEndTime(), dlsp.getReason());
		if(sp == 1){
			text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, dlsp.getSender(), null, "权限代理申请", ApprovalStatus.APPROVAL_ACCESS, table);
		}else{
			text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_RESULT_NOTICE, null, dlsp.getSender(), null, "权限代理申请", ApprovalStatus.APPROVAL_NOT_ACCESS, table);
		}
		
		this.mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "权限代理审核通知", text);
		
		return "redirect:/web/oa/asp/mydlsp";
	}
	
	//解除代理权限
	@RequestMapping("dl/undl")
	public String undl( Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		this.roleService.deleteDaili(loginUser.getId());
		session.removeAttribute("mydluser");
		return "redirect:/web/oa/index";
	}
	
	@RequestMapping("oa/asp/mysp")
	public String mySP(Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		/*if(loginUser.getId() != Consts.managerId){
			return "oa/asp/mysp";
		}*/
		List<AuthSP> list = aspService.findMySP();
		model.addAttribute("list", list);
		return "oa/asp/mysp";
	}
	
	@RequestMapping("oa/asp/myAuthSq")
	public String myAuthSq(Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		
		List<AuthSP> list = aspService.findMine(loginUser.getId());
		model.addAttribute("list", list);
		return "oa/asp/myAuthSq";
	}
	
	@RequestMapping("oa/asp/sp/{id}/{sp}")
	public String sp(@PathVariable int id, @PathVariable int sp,String yj ){
		String cmt = null;
		try {
			cmt = new String(yj.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		AuthSP asp = this.aspService.findById(id);
		asp.setId(id);
		if(sp == 1){
			asp.setStatus(sp);
			asp.setCmt(cmt);
		}else{
			asp.setStatus(-1);
			asp.setCmt(cmt);
		}
		aspService.update(asp, roleMapper);
		
		return "redirect:/web/oa/asp/mysp";
	}
	
	private String buildAuthProxyTable(String dlEmp, String menus, String beginTime, String endTime, String reason) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
				+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>代理原因</b></td>"
				+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + reason + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>代理员工</b></td>"
				+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + dlEmp + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>代理员工可使用权限</b></td>"
				+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + menus + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>生效时间</b></td>"
				+ "<td style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + beginTime + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>失效时间</b></td>"
				+ "<td style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + endTime + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "</table>");
		
		return sb.toString();
	}
}
