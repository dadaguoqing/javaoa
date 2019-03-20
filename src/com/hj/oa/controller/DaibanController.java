package com.hj.oa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.oa.Consts;
import com.hj.oa.bean.Daiban;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.SpRecord;
import com.hj.oa.bean.User;
import com.hj.oa.service.DaibanService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.LeaveUtil;
import com.hj.util.MailUtil;
import com.hj.util.RoleUtil;

@Controller class DaibanController extends BaseController {

	@Autowired
	private DaibanService daibanService;
	
	//实际获取
	@RequestMapping("oa/daiban/mine")
	public String mydb(HttpSession session, Model model){
		
		List<Daiban> myDaiban = (List<Daiban>)session.getAttribute("mines");//daibanService.findDaibanByEmpId(loginUser.getId(), roles);
		HashMap<String, ArrayList<Daiban>> dlDaiban = (HashMap<String, ArrayList<Daiban>>)session.getAttribute("dls");;//daibanService.findDailiDaibanByEmpId(loginUser.getId());
		
		session.removeAttribute("mines");
		session.removeAttribute("dls");
		
		if(myDaiban == null){
			User loginUser = getLoginUser(session);//获取当前登陆的用户
			List<Role> roles = this.getLoginUserRole(session);
			myDaiban = daibanService.findDaibanByEmpId(loginUser.getId(), roles);
			dlDaiban = daibanService.findDailiDaibanByEmpId(loginUser.getId());
		}
		
		//model.addAllAttributes(arg0)
		model.addAttribute("isEmptyDls", isEmpty(dlDaiban));
		model.addAttribute("dlnames", dlDaiban.keySet());
		model.addAttribute("mines",myDaiban);
		model.addAttribute("dls",dlDaiban);
		
		return "oa/daiban/mine";
	}
	
	//ajax查询有没有，然后放到session里面
	@RequestMapping("oa/daiban/has")
	@ResponseBody
	public HashMap<String,String> hasDb(HttpSession session, Model model){
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		User loginUser = getLoginUser(session);//获取当前登陆的用户
		List<Role> roles = this.getLoginUserRole(session);
		List<Daiban> myDaiban = daibanService.findDaibanByEmpId(loginUser.getId(), roles);
		HashMap<String, ArrayList<Daiban>> dlDaiban = daibanService.findDailiDaibanByEmpId(loginUser.getId());
		
		if(myDaiban.isEmpty() && isEmpty(dlDaiban)){
			map.put("ret", "0");
			return map;
		}
		
		//session.setAttribute("dlnames", dlDaiban.keySet());
		session.setAttribute("mines",myDaiban);
		session.setAttribute("dls",dlDaiban);
		
		map.put("ret", "1");
		
		return map; 
	}
	
	public static boolean isEmpty( HashMap<String, ArrayList<Daiban>> dlDaiban ){
		Set<String> keyset = dlDaiban.keySet();
		
		for(String key : keyset){
			ArrayList<Daiban> list=dlDaiban.get(key);
			if(!list.isEmpty()){
				return false;
			}
		}
		
		return true;
	}
	
}
