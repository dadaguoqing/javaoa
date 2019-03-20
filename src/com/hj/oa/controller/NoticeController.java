package com.hj.oa.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import com.hj.oa.bean.Notice;
import com.hj.oa.bean.Rule;
import com.hj.oa.bean.User;
import com.hj.oa.service.NoticeService;
import com.hj.oa.service.UserService;
import com.hj.util.ServletUtil;

@Controller
public class NoticeController extends BaseController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("oa/rules/add")
	public String addRules(Rule rule, HttpSession session, Model model){
		
		if(rule == null || rule.getName() == null){
			return "oa/notice/addRules";
		}
		
		//this.noticeService.addRule(rule);
		
		return "oa/notice/addRules";
	}
	
	@RequestMapping("oa/rules/list")
	public String listRules(HttpSession session, Model model){
		
		List<Rule> rules = this.noticeService.findAllRules();
		model.addAttribute("list", rules);
		
		return "oa/notice/listRules";
	}
	
	@RequestMapping("oa/rules/del/{id}")
	public String delRule(@PathVariable int id, Model model){
		
		this.noticeService.deleteRule(id);
		
		model.addAttribute("msg", "删除成功");
		List<Rule> rules = this.noticeService.findAllRules();
		model.addAttribute("list", rules);
		
		return "oa/notice/listRules";
	}
	
	@RequestMapping("oa/rules/{id}")
	public String showRule(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response){
		
		Rule rule = this.noticeService.findRuleById(id);
		model.addAttribute("rule", rule);
		
		if(rule == null){
			return listRules(session, model);
		}
		
		model.addAttribute("rule",rule);
		return "oa/notice/showRule";
	}
	
	@RequestMapping("oa/rules/download/{id}")
	public void downloadRule(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response){
		
		Rule rule = this.noticeService.findRuleById(id);
		model.addAttribute("rule", rule);
		
		if(rule == null){
			return;
		}
		String loc = rule.getLoc();
		
		File file = new File(Consts.uploadFileRootLoc + loc);
		
		ServletUtil.downloadFile(response, file, rule.getName()+".pdf");
	}
	
	@RequestMapping("oa/rules/{id}.pdf")
	public void getPdf(@PathVariable int id, Model model, HttpServletResponse response){
		
		Rule rule = this.noticeService.findRuleById(id);
		model.addAttribute("rule", rule);
		
		if(rule == null){
			return;
		}
		String loc = rule.getLoc();
		
		File file = new File(Consts.uploadFileRootLoc + loc);
		
		ServletUtil.downloadFile(response, file, rule.getName(), "application/pdf");
		//return "oa/notice/showRule";
	}
	
	@RequestMapping("oa/notice/{id}")
	public String showNotice2(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response){
		User loginUser = this.getLoginUser(session);
		
		Notice notice = this.noticeService.findById(id);
		model.addAttribute("notice", notice);

		noticeService.setReaded(loginUser.getId(), id);
		
		return "oa/notice/showNotice";
	}
	
	@RequestMapping("oa/notice/add")
	public String addNotice(Notice notice, String uids, HttpSession session, Model model){
		
		if(notice == null || notice.getContent() == null){
			return "oa/notice/add";
		}
		
		List<Integer> empIds = new ArrayList<Integer>();
		
		if(!StringUtils.isEmpty(uids)){
			String[] ss = uids.split(",");
			for(String s : ss){
				s = s.trim();
				if(s.length()>0){
					empIds.add(Integer.parseInt(s));
				}
			}
		}else{
			List<User> emps = userService.findAllUsers();
			empIds = new ArrayList<Integer>(emps.size());
			for(User u : emps){
				empIds.add(u.getId());
			}
		}
		//处理notice的内容
		String content = notice.getContent();
		String cnt = content.replaceAll("byL.pdf\"", "byL.pdf\" target=\"_blank\" ");
		/*
		StringBuilder sb = new StringBuilder();
		
		String pfix = "byL.pdf";
		
		List<Integer> indx = new ArrayList<Integer>();
		int index = content.indexOf(pfix);
		
		while(index >0){
			indx.add(index);
			index = content.indexOf(pfix, index);
		}
		
		int len = indx.size();
		for(int i=0; i<len; i++){
			
			int now = indx.get(i);
			if(i == 0){
				String s = content.substring(0, now);
				sb.append(s).append(pfix);
			}else if(i== len-1){
				int pre = indx.get(i-1);
			}else{
				int pre = indx.get(i-1);
			}
		}*/
		notice.setContent(cnt);
		
		User loginUser = this.getLoginUser(session);
		notice.setPublisher(loginUser.getName());
		noticeService.addNotice(notice, empIds);
		model.addAttribute("msg", "操作成功");
		return "oa/notice/add";
	}
	
	@RequestMapping("oa/notice/list")
	public String listAll(String type, String begin, String end, HttpSession session, Model model){
		
		
		if(null == type){
			type = "全部";
		}
		model.addAttribute("type",type);
		/*
		if(begin == null || begin == ""){
			begin = null;
		}
		
		if(end == null || end == ""){
			end = null;
		}
		
		
		model.addAttribute("status",0);
		model.addAttribute("begin","");
		model.addAttribute("end","");
		*/
		
		List<User> noticer = this.userService.findNoticer(1);
		
		User loginUser = this.getLoginUser(session);
		List<Notice> list = noticeService.findByCon(0, loginUser.getId(), null, null, type);
			//noticeService.findAll();
		model.addAttribute("list",list);
		model.addAttribute("noticer",noticer);
		return "oa/notice/listAll";
	}
	
	@RequestMapping("oa/notice/show")
	public String showNotice(int id, Model model){
		Notice notice = noticeService.findById(id);
		model.addAttribute("notice",notice);
		return "oa/notice/show";
	}
	
	@RequestMapping("oa/notice/del/{id}")
	@ResponseBody
	public String delNotice(@PathVariable int id, Model model){
		this.noticeService.delNotice(id);
		return "ok";
	}
	
	@RequestMapping("oa/notice/read")
	@ResponseBody
	public Object showNotice(Integer empId, Integer id, Model model){
		//Notice notice = noticeService.findById(id);
		noticeService.setReaded(empId, id);
		//model.addAttribute("notice",notice);
		return "ok";
	}
	
	/*
	@RequestMapping("oa/notice/{id}")
	public String show(@PathVariable int id, Model model){
		Notice notice = noticeService.findById(id);
		model.addAttribute("notice",notice);
		return "oa/notice/detail";
	}
	*/
	@RequestMapping("oa/notice/mgr")
	public String mgr(String begin, String end, HttpSession session, Model model){
		
		if(begin == null || begin == ""){
			begin = null;
		}
		
		if(end == null || end == ""){
			end = null;
		}
		
		model.addAttribute("begin",begin);
		model.addAttribute("end",end);
		
		User loginUser = this.getLoginUser(session);
		
		List<Notice> list = noticeService.findAllCon(begin, end);
		model.addAttribute("list",list);
		
		return "oa/notice/mgr";
	}
	
	@RequestMapping("oa/notice/mine")
	public String list( Model model, HttpSession session){

		User loginUser = this.getLoginUser(session);
		
		List<Notice> list = noticeService.findByUser(loginUser.getId(), 0);
		model.addAttribute("list",list);
		
		return "oa/notice/listMine";
	}
}
