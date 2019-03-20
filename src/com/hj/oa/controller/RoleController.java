package com.hj.oa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.oa.Consts;
import com.hj.oa.bean.AuthSP;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.AuthSPService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.ArrayUtil;
import com.hj.util.DateUtil;
import com.hj.util.MailUtil;

@Controller
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private AuthSPService aspService;
	
	@RequestMapping("oa/role/mgr")
	public String roleMgr(Model model){
		List<User> users = userService.findAllUsers();
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("users", users);
		model.addAttribute("roles", roles);
		return "oa/role/mgr";
	}
	
	@RequestMapping("oa/role/add")
	public String addRolo(String roleName, Model model){
		Role role = new Role();
		role.setName(roleName);
		roleService.addRole(role);
		return "redirect:/web/oa/role/mgr";
	}
	
	@RequestMapping("oa/role/ajaxEmpsByRole/{id}")
	@ResponseBody
	public List<Integer> ajaxGetEmpByRoleId(@PathVariable int id){
		List<Integer> list = roleService.findEmpIdByRoleId(id);
		return list;
	}
	
	@RequestMapping("oa/role/ajaxMenuByRole/{id}")
	@ResponseBody
	public List<Integer> ajaxGetMenuByRoleId(@PathVariable int id){
		List<Integer> list = menuService.findMenuIdByRole(id);
		return list;
	}
	
	@RequestMapping("oa/role/emp")
	public String goSetEmpRole(Integer empId, Model model){
		User emp = userService.findById(empId);
		List<Role> roles = roleService.findAllRoles();
		List<Role> empRoles = roleService.findRolesByEmpId(empId);
		StringBuilder sb = new StringBuilder();
		int size = empRoles.size();
		for(int i=0; i<size; i++	){
			Role r = empRoles.get(i);
			if(i==0){
				sb.append('[');
			}
			sb.append('\'');
			sb.append(r.getId());
			sb.append('\'');
			if(i==(size-1)){
				sb.append(']');
			}else{
				sb.append(',');
			}
		}
		model.addAttribute("emp", emp);
		model.addAttribute("roles", roles);
		model.addAttribute("rids", sb.toString());
		return "oa/role/setEmpRoles";
	}
	
	@RequestMapping("oa/role/setEmp")
	public String setEmpRole(Integer empId, Integer[] roleIds, Model model, HttpSession session){
		
		User loginUser = this.getLoginUser(session); 
		//如果是老总，不需要审批，直接提交
		if(loginUser.getId() == Consts.managerId){
			roleService.setEmpRoles(empId, roleIds);
			return "redirect:/web/oa/role/emp?empId="+empId+"&msg=1";
		}
		
		List<Role> roles = roleService.findRolesByEmpId(empId);
		List<Integer> oldRoleIds = new ArrayList<Integer>(roles.size());
		for(int i=0; i<roles.size(); i++){
			Role r = roles.get(i);
			oldRoleIds.add(r.getId());
		}
		
		List<Integer> newRoleIds = Arrays.asList(roleIds);
		
		//比较新老的区别
		List<Integer> olds = ArrayUtil.notIn(oldRoleIds, newRoleIds);//老的被遗弃的
		List<Integer> news = ArrayUtil.notIn(newRoleIds, oldRoleIds);//新增加的
		
		//如果没有变化，直接返回，不需要审批
		if(olds.isEmpty() && news.isEmpty()){
			return "redirect:/web/oa/role/emp?empId="+empId+"&msg=2";
		}
		
		//需要审批
		AuthSP asp = new AuthSP();
		asp.setSender(loginUser.getName());
		asp.setSenderId(loginUser.getId());
		asp.setStatus(0);
		asp.setTid(empId);
		asp.setTids(ArrayUtil.toString(",", roleIds));
		asp.setType(0);
		
		User emp = userService.findById(empId);
		List<Role> rList = roleService.findAllRoles();
		Map<Integer, Role> rMap = new HashMap<Integer, Role>();
		for(Role r : rList){
			rMap.put(r.getId(), r);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(loginUser.getName());
		sb.append("申请将用户:");
		sb.append(emp.getName());
		sb.append("的权限，由之前的[");
		
		if(oldRoleIds.isEmpty()){
			
			sb.append("缺省]");
		}else{
		
			for(int j=0; j<oldRoleIds.size(); j++ ){
				Integer i = oldRoleIds.get(j);
				Role r = rMap.get(i);
				sb.append(r.getName());
				if(j!= oldRoleIds.size()-1 ){
					sb.append(",");
				}else{
					sb.append("]");
				}
			}
		}
		sb.append("，变化为[");
		for(int j=0; j<newRoleIds.size(); j++ ){
			Integer i = newRoleIds.get(j);
			Role r = rMap.get(i);
			sb.append(r.getName());
			if(j!= newRoleIds.size()-1 ){
				sb.append(",");
			}else{
				sb.append("]");
			}
		}
		sb.append("。");
		if(!news.isEmpty()){
			sb.append("新增权限：[");
			for(int j=0; j<news.size(); j++ ){
				Integer i = news.get(j);
				Role r = rMap.get(i);
				sb.append(r.getName());
				if(j!= news.size()-1 ){
					sb.append(",");
				}else{
					sb.append("]。");
				}
			}
		}
		
		if(!olds.isEmpty()){
			sb.append("取消权限：[");
			for(int j=0; j<olds.size(); j++ ){
				Integer i = olds.get(j);
				Role r = rMap.get(i);
				sb.append(r.getName());
				if(j!= olds.size()-1 ){
					sb.append(",");
				}else{
					sb.append("]。");
				}
			}
		}
		asp.setContent(sb.toString());
		asp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
		
		String ctnt = "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好。</p>" +
		"<p style='padding:15px 0 15px 0px;'>"+
		sb.toString()+
		"请及时审批。</p>" +
		"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
		
		//添加一个审批
		aspService.add(asp);
		
		User boss = userService.findById(Consts.managerId);
		mailUtil.sendForNotice(boss, userService, roleService, menuService, ctnt, "权限变更申请");
		return "redirect:/web/oa/role/emp?empId="+empId+"&msg=3";
	}

	//设置权限对应的用户
	@RequestMapping("oa/role/setRoles")
	public String setRole(int rid, Integer[] uids, Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		
		//如果是老总，不需要审批，直接提交
		if(loginUser.getId() == Consts.managerId){
			roleService.setRoles(rid,uids);
			return "redirect:/web/oa/role/mgr?msg=1";//设置成功
		}
		
		List<Integer> oldEmpIds = roleService.findEmpIdByRoleId(rid);
		List<Integer> newEmpIds = Arrays.asList(uids);
		
		//比较新老的区别
		List<Integer> olds = ArrayUtil.notIn(oldEmpIds, newEmpIds);//老的被遗弃的
		List<Integer> news = ArrayUtil.notIn(newEmpIds, oldEmpIds);//新增加的
		
		//如果没有变化，直接返回，不需要审批
		if(olds.isEmpty() && news.isEmpty()){
			return "redirect:/web/oa/role/mgr?msg=2";//权限没有发生变化，不需要任何操作
		}
		
		//需要审批
		AuthSP asp = new AuthSP();
		asp.setSender(loginUser.getName());
		asp.setSenderId(loginUser.getId());
		asp.setStatus(0);
		asp.setTid(rid);
		asp.setTids(ArrayUtil.toString(",", uids));
		asp.setType(1);//表示角色对应用户发生变化
		
		Role r = roleService.findRoleById(rid);
		List<User> users = userService.findAllUsers();
		Map<Integer,User> uMap = new HashMap<Integer,User>();
		for(User u : users){
			uMap.put(u.getId(), u);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(loginUser.getName());
		sb.append("申请将角色:");
		sb.append(r.getName());
		sb.append("对应的员工，由之前的[");
		for(int j=0; j<oldEmpIds.size(); j++ ){
			Integer i = oldEmpIds.get(j);
			User u = uMap.get(i);
			sb.append(u.getName());
			if(j!= oldEmpIds.size()-1 ){
				sb.append(",");
			}
		}
		sb.append("]，变化为[");
		for(int j=0; j<newEmpIds.size(); j++ ){
			Integer i = newEmpIds.get(j);
			User u = uMap.get(i);
			sb.append(u.getName());
			if(j!= newEmpIds.size()-1 ){
				sb.append(",");
			}
		}
		sb.append("]。");
		if(!news.isEmpty()){
			sb.append("新增员工：[");
			for(int j=0; j<news.size(); j++ ){
				Integer i = news.get(j);
				User u = uMap.get(i);
				sb.append(u.getName());
				if(j!= news.size()-1 ){
					sb.append(",");
				}
			}
			sb.append("]。");
		}
		
		if(!olds.isEmpty()){
			sb.append("移除员工：[");
			for(int j=0; j<olds.size(); j++ ){
				Integer i = olds.get(j);
				User u = uMap.get(i);
				sb.append(u.getName());
				if(j!= olds.size()-1 ){
					sb.append(",");
				}
			}
			sb.append("]。");
		}
		
		asp.setContent(sb.toString());
		asp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
		
		String ctnt = "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好。</p>" +
		"<p style='padding:15px 0 15px 0px;'>"+
		sb.toString()+
		"请及时审批。</p>" +
		"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
		
		//添加一个审批
		aspService.add(asp);
		
		User boss = userService.findById(Consts.managerId);
		mailUtil.sendForNotice(boss, userService, roleService, menuService, ctnt, "权限变更申请");
		
		
//		roleService.setRoles(rid,uids);
		
		return "redirect:/web/oa/role/mgr?msg=3";//提交申请
	}
	
	@RequestMapping("oa/role/setRoleMenu")
	public String setRoleMenu(int rid, Integer[] mids, Model model, HttpSession session){
		User loginUser = this.getLoginUser(session);
		
		//如果是老总，不需要审批，直接提交
		if(loginUser.getId() == Consts.managerId){
			roleService.setRoleMenu(rid, mids);
			return "redirect:/web/oa/role/menu?msg=1";//设置成功
		}
		
		List<Integer> oldMenuIds = menuService.findMenuIdByRole(rid);
		List<Integer> newMenuIds = Arrays.asList(mids);
		
		//比较新老的区别
		List<Integer> olds = ArrayUtil.notIn(oldMenuIds, newMenuIds);//老的被遗弃的
		List<Integer> news = ArrayUtil.notIn(newMenuIds, oldMenuIds);//新增加的
		
		//如果没有变化，直接返回，不需要审批
		if(olds.isEmpty() && news.isEmpty()){
			return "redirect:/web/oa/role/menu?msg=2";//权限没有发生变化，不需要任何操作
		}
		
		//需要审批
		AuthSP asp = new AuthSP();
		asp.setSender(loginUser.getName());
		asp.setSenderId(loginUser.getId());
		asp.setStatus(0);
		asp.setTid(rid);
		asp.setTids(ArrayUtil.toString(",", mids));
		asp.setType(2);//表示角色对应菜单发生变化
		
		Role r = roleService.findRoleById(rid);
		List<Menu> menus = menuService.findAll();
		Map<Integer,Menu> map = new HashMap<Integer,Menu>();
		for(Menu m : menus){
			map.put(m.getId(), m);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(loginUser.getName());
		sb.append("申请将角色:");
		sb.append(r.getName());
		sb.append("对应的菜单，由之前的[");
		for(int j=0; j<oldMenuIds.size(); j++ ){
			Integer i = oldMenuIds.get(j);
			Menu m = map.get(i);
			sb.append(m.getName());
			if(j!= oldMenuIds.size()-1 ){
				sb.append(",");
			}
		}
		sb.append("]，变化为[");
		for(int j=0; j<newMenuIds.size(); j++ ){
			Integer i = newMenuIds.get(j);
			Menu m = map.get(i);
			sb.append(m.getName());
			if(j!= newMenuIds.size()-1 ){
				sb.append(",");
			}
		}
		sb.append("]。");
		if(!news.isEmpty()){
			sb.append("新增菜单：[");
			for(int j=0; j<news.size(); j++ ){
				Integer i = news.get(j);
				Menu m = map.get(i);
				sb.append(m.getName());
				if(j!= news.size()-1 ){
					sb.append(",");
				}
			}
			sb.append("]。");
		}
		
		if(!olds.isEmpty()){
			sb.append("移除菜单：[");
			for(int j=0; j<olds.size(); j++ ){
				Integer i = olds.get(j);
				Menu m = map.get(i);
				sb.append(m.getName());
				if(j!= olds.size()-1 ){
					sb.append(",");
				}
			}
			sb.append("]。");
		}
		
		asp.setContent(sb.toString());
		asp.setCreateTime(DateUtil.getCurrentTime("yyyy年M月d日 HH时m分"));
		
		String ctnt = "<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好。</p>" +
		"<p style='padding:15px 0 15px 0px;'>"+
		sb.toString()+
		"请及时审批。</p>" +
		"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>";
		
		//添加一个审批
		aspService.add(asp);
		
		User boss = userService.findById(Consts.managerId);
		mailUtil.sendForNotice(boss, userService, roleService, menuService, ctnt, "权限变更申请");
		//roleService.setRoleMenu(rid, mids);
		return "redirect:/web/oa/role/menu?msg=3";
	}
	
	@RequestMapping("oa/role/menu")
	public String roleMenu(Model model){
		List<Menu> menus = menuService.findAll();
		List<Role> roles = roleService.findAllRoles();
		
		HashMap<Integer,List<Menu>> map = new HashMap<Integer,List<Menu>>();
		List<Menu> menuList = new ArrayList<Menu>();
		for(Menu m : menus){
			if(m.getPid() != null){
				List<Menu> mlist = map.get(m.getPid());
				if(mlist==null){
					mlist = new ArrayList<Menu>();
					map.put(m.getPid(), mlist);
				}
				mlist.add(m);
			}else{
				List<Menu> mlist = map.get(m.getId());
				if(mlist==null){
					mlist = new ArrayList<Menu>();
					map.put(m.getId(), mlist);
				}
				menuList.add(m);
			}
		}
		
		model.addAttribute("roles", roles);
		model.addAttribute("menus", menuList);
		model.addAttribute("map",map);
		return "oa/role/roleMenu";
	}
}
