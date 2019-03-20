package com.hj.oa.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.EmpDay;
import com.hj.oa.bean.JiaBan;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.EmpDayService;
import com.hj.oa.service.JiaBanService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.MailUtil;
import com.hj.util.RoleUtil;

@Controller
public class JiabanController extends BaseController {
	
	@Autowired
	private JiaBanService jiabanSer;
	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmpDayService edSer;
	
	@Autowired
	private CheckInService ciSer;
	
	@RequestMapping("oa/jiaban/info/{id}")
	public String info(@PathVariable int id, HttpSession session, Model model){
		
		User loginUser = this.getLoginUser(session);
		
		JiaBan jb = this.jiabanSer.findById(id);
		model.addAttribute("jiaban",jb);
		
		
		return "oa/jiaban/detail";
	}
	
	@RequestMapping("oa/jiaban/edit/{id}")
	public String edit(@PathVariable int id, HttpSession session, Model model){
		
		//User loginUser = this.getLoginUser(session);
		
		JiaBan jb = this.jiabanSer.findById(id);
		model.addAttribute("jiaban",jb);
		
		String dayStr = jb.getDayte();
		
		CheckIn ci=ciSer.findByDayAndEmp(dayStr, jb.getProposer());
		
		model.addAttribute("ci",ci);
		
		return "oa/jiaban/edit";
	}
	
	@RequestMapping("oa/jiaban/mysp")
	public String mySp(HttpSession session, Model model){
		
		User loginUser = this.getLoginUser(session);
		
		List<JiaBan> js = this.jiabanSer.findMySp(loginUser.getId());
		model.addAttribute("list",js);
		
		return "oa/jiaban/mysp";
	}
	
	@RequestMapping("oa/jiaban/mysq")
	public String mySq(HttpSession session, Model model){
		
		User loginUser = this.getLoginUser(session);
		List<JiaBan> js = this.jiabanSer.findByEmpId(loginUser.getId());
		model.addAttribute("list",js);
		
		return "oa/jiaban/mysq";
	}
	
	///员工加班申请，有员工申请，老模式
	/*
	public String sqOld(JiaBan jiaban, HttpSession session, Model model){
		
		model.addAttribute("jiaban",jiaban);
		if(jiaban == null || jiaban.getDayte() == null){//直接进申请界面
			return "oa/jiaban/sq";
		}
		
		User loginUser = this.getLoginUser(session);
		
		jiaban.setProposer(loginUser.getId());
		jiaban.setProposerName(loginUser.getName());
		
		String dayStr = jiaban.getDayte();
		
		JiaBan jb = this.jiabanSer.findByEmpAndDayStr(jiaban.getProposer(), dayStr);
		if(null != jb){
			model.addAttribute("msg","申请失败，您之前已经提交过当前日期的加班申请");
			return "oa/jiaban/sq";
		}
		
		List<Role> roles = this.getLoginUserRole(session);
		Dept dept = this.getLoginUserDept(session);
		
		if(RoleUtil.hasRole(roles,"总经理")){
			model.addAttribute("msg","您不需要申请");
			return "oa/jiaban/sq";
		}else if(RoleUtil.hasRole(roles,"技术总监") || RoleUtil.hasRole(roles,"总监") || dept == null){//技术总监或者总监
			jiaban.setStatus(3);
			jiaban.setBossId(Consts.managerId);
			jiaban.setCurrentId(Consts.managerId);
		}else if(RoleUtil.hasRole(roles,"部门主管")){//主管
			//是否技术部的
			if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
				jiaban.setStatus(2);
				jiaban.setDirectId(Consts.directorId);
				jiaban.setCurrentId(Consts.directorId);
				jiaban.setBossId(Consts.managerId);
				
			}else{//非技术部
				jiaban.setStatus(2);
				jiaban.setDirectId(Consts.managerId);
				jiaban.setBossId(Consts.managerId);
				jiaban.setCurrentId(Consts.managerId);
			}
		}else{//普通员工
			jiaban.setStatus(1);//主管审核
			jiaban.setMgrId(dept.getMgrId());
			jiaban.setCurrentId(dept.getMgrId());
			
			//是否技术部的
			if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
				jiaban.setDirectId(Consts.directorId);
				jiaban.setBossId(Consts.managerId);
			}else{
				jiaban.setDirectId(Consts.managerId);
				jiaban.setBossId(Consts.managerId);
			}
				
		}
		
		this.jiabanSer.addJiaban(jiaban);//添加一个申请
		model.addAttribute("msg","您的加班申请已提交，请等待审批");
		
		//当前审批人
		User user = userService.findById(jiaban.getCurrentId());
		String content = getEmailContent(jiaban.getProposerName());
		String subject = "员工加班申请";
		mailUtil.sendForNotice(user, userService, roleService, menuService, content, subject);
		
		
		return "oa/jiaban/sq";
	}
	*/
	
	//由主管帮助员工申请。
	@RequestMapping("oa/jiaban/sq")
	public String sq(JiaBan jiaban, HttpSession session, Model model){
		
		User curUser = getLoginUser(session);
		//应该获取申请人的role.
		List<Role> curoles = this.getLoginUserRole(session);
		
		List<User> users = userService.findDirectSubordinates(curUser.getId(), curoles);
		model.addAttribute("users",users);
		
		model.addAttribute("jiaban",jiaban);
		if(jiaban == null || jiaban.getDayte() == null){//直接进申请界面
			return "oa/jiaban/sq";
		}
		
		//User loginUser = this.getLoginUser(session);
		User propUser = this.userService.findById(jiaban.getProposer());
		//jiaban.setProposer(propUser.getId());
		jiaban.setProposerName(propUser.getName());
		
		String dayStr = jiaban.getDayte();
		
		JiaBan jb = this.jiabanSer.findByEmpAndDayStr(jiaban.getProposer(), dayStr);
		if(null != jb){
			model.addAttribute("msg","申请失败，该用户之前已经提交过当前日期的加班申请");
			return "oa/jiaban/sq";
		}
		
		List<Role> roles = this.roleService.findRolesByEmpId(jiaban.getProposer());//this.getLoginUserRole(session);
		Dept dept = this.userService.findDeptById(propUser.getDeptId());//this.getLoginUserDept(session);
		
		if(RoleUtil.hasRole(roles,"总经理")){
			model.addAttribute("msg","该用户是总经理不需要申请");
			return "oa/jiaban/sq";
		}else if(RoleUtil.hasRole(roles,"技术总监") || RoleUtil.hasRole(roles,"总监") || dept == null){//技术总监或者总监
			jiaban.setStatus(3);
			jiaban.setBossId(Consts.managerId);
			jiaban.setCurrentId(Consts.managerId);
		}else if(RoleUtil.hasRole(roles,"部门主管")){//主管
			//是否技术部的
			if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
				jiaban.setStatus(2);
				jiaban.setDirectId(Consts.directorId);
				jiaban.setCurrentId(Consts.directorId);
				jiaban.setBossId(Consts.managerId);
				
			}else{//非技术部
				jiaban.setStatus(2);
				jiaban.setDirectId(Consts.managerId);
				jiaban.setBossId(Consts.managerId);
				jiaban.setCurrentId(Consts.managerId);
			}
		}else{//普通员工
			jiaban.setStatus(1);//主管审核
			jiaban.setMgrId(dept.getMgrId());
			jiaban.setCurrentId(dept.getMgrId());
			
			//是否技术部的
			if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
				jiaban.setDirectId(Consts.directorId);
				jiaban.setBossId(Consts.managerId);
			}else if(dept!=null && dept.getPid().equals(new Integer(Consts.sellDeptId))){//营销中心
				jiaban.setDirectId(Consts.sellDirectorId);
				jiaban.setBossId(Consts.managerId);
			}else{
				jiaban.setDirectId(Consts.managerId);
				jiaban.setBossId(Consts.managerId);
			}
				
		}
		
		this.jiabanSer.addJiaban(jiaban);//添加一个申请
		model.addAttribute("msg","加班申请已提交，请等待审批");
		
		//当前审批人
		User user = userService.findById(jiaban.getCurrentId());
		String content = getEmailContent(jiaban.getProposerName());
		String subject = "员工加班申请";
		mailUtil.sendForNotice(user, userService, roleService, menuService, content, subject);
		
		
		return "oa/jiaban/sq";
	}
	
	@RequestMapping("oa/jiaban/sp")
	public String sp(int id, String yj, String sp, Model model, HttpSession session){
		
		JiaBan jiaban = this.jiabanSer.findById(id);
		User loginUser = (User)session.getAttribute("loginUser");
		
		int currentSpId = jiaban.getCurrentId();
		
		if(loginUser.getId() != currentSpId){//不归我审批
			return "redirect:/web/oa/jiaban/mysp";
		}
		int status = jiaban.getStatus();
		if(status == -1 || status == 4){//已经结束
			return "redirect:/web/oa/jiaban/mysp";
		}
		
		if(status == 1){
			jiaban.setMgrCmt(yj);
			if("审批通过".equals(sp)){
				jiaban = spNext(jiaban,loginUser,status,yj);
			}else{
				jiaban.setStatus(-1);
			}
		}else if(status == 2){
			jiaban.setDirectCmt(yj);
			
			if("审批通过".equals(sp)){
				jiaban = spNext(jiaban,loginUser,status,yj);
			}else{
				jiaban.setStatus(-1);
			}
			
		}else if(status == 3){
			jiaban.setBossCmt(yj);
			if("审批通过".equals(sp)){
				jiaban.setStatus(4);
			}else{
				jiaban.setStatus(-1);
			}
		}
		
		boolean isDaili = this.isDailiLogin(session);
		Integer dailiId = null;
		if(isDaili){
			User dlUser = this.getDailiUser(session);
			dailiId = dlUser.getId();
		}
		
		if(jiaban.getStatus() == 4){
			this.jiabanSer.updateSth(jiaban, currentSpId, dailiId);
		}else{
			this.jiabanSer.updateJiaBan(jiaban, currentSpId, dailiId);
		}
		
		User pps = userService.findById(jiaban.getProposer());
		User handler = userService.findById(jiaban.getCurrentId());
		
		
		
		if(jiaban.getStatus() ==4){//结束
			String to = pps.getEmail();
			String subject = "加班审核通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append("</p><p style='font-size:14px;padding:5px 0px;'>您的一个加班审批<span style='color:green;'>通过审核</span>。请登录系统查看详情。</p>");
			sb.append("<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		}else if(jiaban.getStatus() == -1){
			String to = pps.getEmail();
			String subject = "加班审核通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append("</p><p style='font-size:14px;padding:5px 0px;'>您的一个加班审批<span style='color:red;'>没有通过审核</span>。请登录系统查看详情。</p>");
			sb.append("<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		}else{//通知有审批
			//String to = handler.getEmail();
			//String subject = "员工加班审批";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(handler.getName());
			sb.append("</p><p style='font-size:14px;padding:5px 0px;'>员工<span style='color:red;'>");
			sb.append(jiaban.getProposerName());
			sb.append("</span>向您提交了一个加班申请，请及时登录OA系统审批。</p>");
			sb.append("<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			//mailUtil.sendEMail(to, Consts.defaultFrom, subject, sb.toString());
			mailUtil.sendForNotice(handler, userService, roleService, menuService, sb.toString(), "员工加班审批");
		}
		
		return "redirect:/web/oa/jiaban/mysp?msg=1";
	}
	
	private JiaBan spNext(JiaBan jiaban, User user, int status, String yj){
		status++;
		if(status == 2){
			Integer did = jiaban.getDirectId();
			if(did == null){//审批结束
				jiaban.setStatus(4);
			}else{
				if(did == user.getId()){//同一个人
					jiaban.setDirectCmt(yj);
					return spNext(jiaban, user, status, yj);
				}else{
					jiaban.setCurrentId(did);
					jiaban.setStatus(status);
				}
			}
		}else if(status == 3){
			Integer bid = jiaban.getBossId();
			if(bid == null){//审批结束
				jiaban.setStatus(4);
			}else{
				if(bid == user.getId()){//同一个人
					jiaban.setBossCmt(yj);
					//return spNext(leave, user, status, yj);
					jiaban.setStatus(4);
				}else{
					jiaban.setCurrentId(bid);
					jiaban.setStatus(status);
				}
			}
		}
		
		return jiaban;
	}
	
	private String getEmailContent(String name){
		return "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好。</p>" +
		"<p style='padding:15px 0 15px 0px;'>员工<span style='color:red'>" + name+"</span>向您提交了一个加班申请，请及时审批。</p>" +
		"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
	}
	
}
