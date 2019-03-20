package com.hj.oa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;

public class BaseController {
	protected static Logger logger = Logger.getLogger(BaseController.class);

	protected User getLoginUser(HttpSession session){
		return (User)session.getAttribute("loginUser");
	}
	
	protected boolean isDailiLogin(HttpSession session){
		Boolean daili = (Boolean)session.getAttribute("daili");
		return (Boolean.TRUE).equals(daili);
	}
	
	/**
	 * 或者当前代理的用户
	 * @param session
	 * @return
	 */
	protected User getDailiUser(HttpSession session){
		return (User)session.getAttribute("dailiUser");
	}
	
	protected HashMap<String,ArrayList<Menu>> getLoginUserMenuMap(HttpSession session){
		return (HashMap<String,ArrayList<Menu>>)session.getAttribute("loginUserMenuMap");
	}
	
	protected List<Menu> getLoginUserMenus(HttpSession session){
		return (List<Menu>)session.getAttribute("loginUserMenus");
	}
	
	protected List<Role> getLoginUserRole(HttpSession session){
		return (List<Role>)session.getAttribute("loginUserRoles");
	}
	
	protected Dept getLoginUserDept(HttpSession session){
		return (Dept)session.getAttribute("loginUserDept");
	}
	
	/**
	 * 获取代理我的用户
	 * @param session
	 * @return
	 */
	protected User getMyDlUser(HttpSession session){
		return (User)session.getAttribute("mydluser");
	}
	
	/**
	 * 获取我正在代理的用户
	 * @param session
	 * @return
	 
	protected List<User> getMyDlUsers(HttpSession session){
		return (List<User>)session.getAttribute("mydlusers");
	}*/
}
