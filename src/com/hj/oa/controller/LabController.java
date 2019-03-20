package com.hj.oa.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.LabAll;
import com.hj.oa.bean.LabGw;
import com.hj.oa.bean.LabHj;
import com.hj.oa.bean.LabPcb;
import com.hj.oa.bean.LabPcbSq;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.LabService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.ExcelUtil;
import com.hj.util.MailTableUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;

@Controller
public class LabController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	LabService labService;
	
//	@RequestMapping("oa/lab/myPcbSqRecord")
//	public String myPcbSqRecord(HttpSession session, Model model){
//		User loginUser = this.getLoginUser(session);
//		
//		List<LabPcbSq> list = this.labService.findMyPcbSq(loginUser.getId(), 1);
//		model.addAttribute("list",list);
//		return "oa/lab/myPcbSqRecord";
//	}
	
	@RequestMapping("oa/lab/myPcbSqRecord2")
	public String myPcbSqRecord2(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		List<LabPcbSq> list = this.labService.findMyPcbSq2(loginUser.getId());
		model.addAttribute("list",list);
		return "oa/lab/myPcbSqRecord";
	}
	
	@RequestMapping("oa/lab/myGwSqRecord")
	public String myGwSqRecord(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		List<LabPcbSq> list = this.labService.findMyPcbSq(loginUser.getId(), 2);
		model.addAttribute("list",list);
		return "oa/lab/myGwSqRecord";
	}
	
	@RequestMapping("oa/lab/pcbSqRecord")
	public String pcbSqRecord(Integer jgStatus, HttpSession session, Model model){
		if(null == jgStatus){
			jgStatus = -2;
		}
		
		User loginUser = this.getLoginUser(session);
		
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		
		boolean flag = false;
		if(RoleUtil.hasRole(roles, "制板负责人")){
			model.addAttribute("zbfzr",true);
			flag = true;
		}
		
		if(flag){
//			List<LabPcbSq> list = this.labService.findAllPcbSq(jgStatus, 1);
			List<LabPcbSq> list = this.labService.findAllPcbSq2(jgStatus);
			model.addAttribute("list",list);
			model.addAttribute("jgStatus",jgStatus);
			return "oa/lab/pcbSqRecord2";
		}else{
			List<LabPcbSq> list = this.labService.findPcbSqBySpEmpId(loginUser.getId(), 1);
			model.addAttribute("list",list);
		}
		
		return "oa/lab/pcbSqRecord";
	}
	
	@RequestMapping("oa/lab/myPcbSpRecord")
	public String pcbSqRecord(HttpSession session, Model model){
		
		User loginUser = this.getLoginUser(session);
		
		List<LabPcbSq> list = this.labService.findAllPcbSq(-2, 1);// -2表示所有
		model.addAttribute("list",list);
		return "oa/lab/myPcbSpRecord";
	}
	
	@RequestMapping("oa/lab/gwSqRecord")
	public String gwSqRecord(Integer jgStatus, HttpSession session, Model model){
		if(null == jgStatus){
			jgStatus = -2;
		}
		User loginUser = this.getLoginUser(session);
		
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		
		boolean flag = false;
		if(RoleUtil.hasRole(roles, "制板负责人")){
			model.addAttribute("zbfzr",true);
			flag = true;
		}
		
		if(flag){
			List<LabPcbSq> list = this.labService.findAllPcbSq(jgStatus, 2);
			model.addAttribute("list",list);
			model.addAttribute("jgStatus",jgStatus);
		}else{
			List<LabPcbSq> list = this.labService.findPcbSqBySpEmpId(loginUser.getId(), 2);
			model.addAttribute("list",list);
		}
		return "oa/lab/gwSqRecord";
	}
	
	@RequestMapping("oa/lab/myPcbSp")
	public String myPcbSp(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		
		if(RoleUtil.hasRole(roles, "制板负责人")){
			model.addAttribute("zbfzr",true);
		}
		
		List<LabPcbSq> list = this.labService.findMyPcbSp(loginUser.getId(), 1);
		model.addAttribute("list",list);
		return "oa/lab/myPcbSp";
	}
	
	@RequestMapping("oa/lab/myPcbSp2")
	public String myPcbSp2(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		List<LabPcbSq> list = this.labService.findMyPcbSp2(loginUser.getId());
		model.addAttribute("list",list);
		if(RoleUtil.hasRole(roles, "制板负责人")){
			return "oa/lab/myPcbSp3";
		}
		return "oa/lab/myPcbSp2";
	}
	
	@RequestMapping("oa/lab/myPcbSp3")
	public String myPcbSp3(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		List<LabPcbSq> list = this.labService.findMyPcbSp2(loginUser.getId());
		model.addAttribute("list",list);
		if(RoleUtil.hasRole(roles, "制板负责人")){
			return "oa/lab/myPcbSp3";
		}
		return "oa/lab/myPcbSp2";
	}
	
	@RequestMapping("oa/lab/myGwSp")
	public String myGwSp(HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		List<Role> roles = roleService.findRolesByEmpId(loginUser.getId());
		
		if(RoleUtil.hasRole(roles, "制板负责人")){
			model.addAttribute("zbfzr",true);
		}
		
		List<LabPcbSq> list = this.labService.findMyPcbSp(loginUser.getId(), 2);
		model.addAttribute("list",list);
		return "oa/lab/myGwSp";
	}
	
	@RequestMapping("oa/lab/pcbJg")
	public String pcbJg(Integer jgStatus, Integer status, String bz, int id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		if(status == null || status == 1){
			status = 1;
			sq.setJgStatus(1);
		}else{
			status = -1;
			sq.setJgStatus(-1);
			sq.setJgBz(bz);
		}
		
		this.labService.updatePcbSq(sq);
		
		return "redirect:/web/oa/lab/pcbSqRecord?jg=1&&jgStatus="+status;
	}
	
	@RequestMapping("oa/lab/gwJg")
	public String gwJg(Integer jgStatus, Integer status, String bz, int id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		if(status == null || status == 1){
			status = 1;
			sq.setJgStatus(1);
		}else{
			status = -1;
			sq.setJgStatus(-1);
			sq.setJgBz(bz);
		}
		
		this.labService.updatePcbSq(sq);
		
		return "redirect:/web/oa/lab/gwSqRecord?jg=1&&jgStatus="+status;
	}
	
	@RequestMapping("oa/lab/ajaxPcbName")
	@ResponseBody
	public HashMap<String,String> ajaxPcbName(String fileName, HttpSession session, Model model){
		
		LabPcb pcb = this.labService.findPcbByFileName(fileName);
		HashMap<String,String> map = new HashMap<String,String>();
		
		if(pcb == null){
			map.put("ret", "ok");
		}else{
			map.put("ret", "badName");
		}
		
		return map;
	}
	
	@RequestMapping("oa/lab/pcbSp")
	public String pcbSp(int id, String yj, String sp, 
			String jgcs1, String jhsj1, Double bj1, String bz,String jhsj2, Double bj2, String jgcs2,
			String jhsj3, Double bj3, String jgcs3,String jhsj4, Double bj4, String jgcs4,
			String jhsj5, Double bj5, String jgcs5,Double zbj,String jhsj12,String jhsj22,
			String jhsj32,String jhsj42,String jhsj52,
			String submitCode, Model model, HttpSession session){
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "redirect:/web/oa/lab/myPcbSp";
		}
		session.removeAttribute(Consts.submitCode);
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabAll pcb = this.labService.findLabAllBySqId(id);
		User loginUser = (User)session.getAttribute("loginUser");
		int currentSpId = sq.getCurrentId();
		if(sq == null || loginUser.getId() != currentSpId ){ //不归我审批
			return "redirect:/web/oa/lab/myPcbSp2";
		}
		int status = sq.getStatus();
		if(status == -1 || status == 5){ //已经结束
			return "redirect:/web/oa/lab/myPcbSp2";
		}
		ApproveStaff as = this.userService.findApproveIdByUserId(sq.getProposer());
		if(status == 1){//制板负责人
			sq.setZbCmt(yj);
			sq.setContent(bz);
			sq.setBj(bj1);
			sq.setJgcs(jgcs1);
			sq.setJhsj(jhsj1);
			sq.setBj2(bj2);
			sq.setJgcs2(jgcs2);
			sq.setJhsj2(jhsj2);
			sq.setBj3(bj3);
			sq.setJgcs3(jgcs3);
			sq.setJhsj3(jhsj3);
			sq.setBj4(bj4);
			sq.setJgcs4(jgcs4);
			sq.setJhsj4(jhsj4);
			sq.setBj5(bj5);
			sq.setJgcs5(jgcs5);
			sq.setJhsj5(jhsj5);
			sq.setZbj(zbj);
			sq.setJhsj12(jhsj12);
			sq.setJhsj22(jhsj22);
			sq.setJhsj32(jhsj32);
			sq.setJhsj42(jhsj42);
			sq.setJhsj52(jhsj52);
			if("审批通过".equals(sp)){
				sq.setStatus(2);
				sq.setCurrentId(sq.getMgrId());
				if(as.getDeptDirector() == as.getTreasurer()) {
					sq.setStatus(3);
					sq.setCurrentId(as.getTreasurer());
				}
				if(as.getDeptDirector() == as.getTreasurer() && as.getDeptDirector() == as.getManager()) {
					sq.setStatus(4);
					sq.setCurrentId(as.getManager());
				}
			}else{
				sq.setStatus(-1);
			}
		}else if(status == 2){ //主管
			sq.setMgrCmt(yj);
			if("审批通过".equals(sp)){
				sq.setStatus(3);
				sq.setCurrentId(sq.getCaiwuId());
				if(as.getTreasurer() == as.getManager()) {
					sq.setStatus(4);
					sq.setCurrentId(as.getManager());
				}
			}else{
				sq.setStatus(-1);
			}
			
		}else if(status == 3){ //财务主管
			sq.setCaiwuCmt(yj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(4);
				sq.setCurrentId(sq.getBossId());
			}else{
				sq.setStatus(-1);
			}
			
		}else if(status == 4){ //总经理
			sq.setBossCmt(yj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(5);
			}else{
				sq.setStatus(-1);
			}
		}
		this.labService.updatePcbSq(sq);
		String[] types = pcb.getType().split(",");
		User pps = this.userService.findById(sq.getProposer());
		// TODO 审批
		String table = MailTableUtil.addWxjg(types, sq.getCreateTime(),sq.getZbj());
		int statu = sq.getStatus();
		if (statu >= 0 && statu < 5) {
			User user = this.userService.findById(sq.getCurrentId());
			this.sendMailTable(user, pps, Consts.MAIL_WXJG_APPLY, table);
		} else {
			this.sendMaileResult(pps, statu, Consts.MAIL_WXJG_APPLY,table);
		}
		return "redirect:/web/oa/lab/myPcbSp2?msg=1";
	}
	
	@RequestMapping("oa/lab/gwSp")
	public String gwSp(int id, String yj, String sp, 
			String jgcs, String jhsj, Double bj, String bz,
			String submitCode, Model model, HttpSession session){
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "redirect:/web/oa/lab/myGwSp";
		}
		session.removeAttribute(Consts.submitCode);
		
		//ZcPropCg cg = this.propService.findCgById(id);//.findById(id);
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		User loginUser = (User)session.getAttribute("loginUser");
		
		int currentSpId = sq.getCurrentId();
		
		if(sq == null || loginUser.getId() != currentSpId ){ //不归我审批
			return "redirect:/web/oa/lab/myGwSp";
		}
		int status = sq.getStatus();
		if(status == -1 || status == 5){ //已经结束
			return "redirect:/web/oa/lab/myGwSp";
		}
		
		if(status == 1){//制板负责人
			sq.setZbCmt(yj);
			
			sq.setBj(bj);
			sq.setJgcs(jgcs);
			sq.setContent(bz);
			sq.setJhsj(jhsj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(2);
				sq.setCurrentId(sq.getMgrId());
				
				Integer mgrId = sq.getMgrId();
				if(mgrId == null || mgrId.equals(loginUser.getId())){
					if(mgrId!=null){
						sq.setMgrCmt("本人申请");
					}
					sq.setStatus(3);
					sq.setCurrentId(sq.getCaiwuId());
				}
				
			}else{
				sq.setStatus(-1);
			}
		}else if(status == 2){ //主管
			sq.setMgrCmt(yj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(3);
				sq.setCurrentId(sq.getCaiwuId());
			}else{
				sq.setStatus(-1);
			}
			
		}else if(status == 3){ //财务主管
			sq.setCaiwuCmt(yj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(4);
				sq.setCurrentId(sq.getBossId());
			}else{
				sq.setStatus(-1);
			}
			
		}else if(status == 4){ //总经理
			sq.setBossCmt(yj);
			
			if("审批通过".equals(sp)){
				sq.setStatus(5);
			}else{
				sq.setStatus(-1);
			}
		}
		
		this.labService.updatePcbSq(sq);
		
		User pps = this.userService.findById(sq.getProposer());
		//User handler = this.userService.findById(sq.getCurrentId());
		String copyTo = null;
		/*
		//别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;
		
		if(bdlUser!=null){
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if(OtherUtil.containsMenu(Consts.gdzcsl, menus)){
				
				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if(Consts.devMode == 0){//应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}*/
		
		User zber = this.userService.findById(sq.getZbId());//制板管理员
		if(sq.getStatus() ==5){ //结束
			this.labService.updatePcbBySqId(sq); //跟新订单编号
			String to = pps.getEmail();
			copyTo = zber.getEmail();
			String subject = "外协加工审核通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>各位好");
			
			sb.append("</p><p style='font-size:14px;padding:5px 0 0px 0px;'>");
			sb.append(pps.getName());
			sb.append("的一个外协加工申请<span style='color:green;'>通过审核</span>。文件名称为："+pcb.getFileName()+"</p>");
			sb.append("请相互协助处理。");
			sb.append("<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject, sb.toString());
			
			
		}else if(sq.getStatus() == -1){
			String to = pps.getEmail();
			
			String subject = "外协加工审核通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append("</p><p style='font-size:14px;padding:5px 0 0px 0px;'>您的一个外协加工申请<span style='color:red;'>没有通过审核</span>。请登录系统查看详情。</p>");
			sb.append("<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		}else{//通知有审批
			//new ArrayList<ZcProperty>();
		}
		
		return "redirect:/web/oa/lab/myGwSp?msg=1";
	}
	
	
	@RequestMapping("oa/lab/editPcbSq/{id}")
	public String editPcbSq(@PathVariable("id") Integer id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",pcb);
		
		return "oa/lab/editPcbSq";
	}
	
	@RequestMapping("oa/lab/editPcbSq2/{id}")
	public String editPcbSq2(@PathVariable("id") Integer id, HttpSession session, Model model){
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
//		LabPcb pcb = this.labService.findPcbBySqId(id);
		LabAll all = this.labService.findLabAllBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",all);
		String[] types = all.getType().split(",");
		for(String type:types){
			if(type.equals("4")){
				model.addAttribute("type4", type);
			}
			if(type.equals("5")){
				model.addAttribute("type5", type);
			}
		}
		model.addAttribute("types", types);
		return "oa/lab/editPcbSq2";
	}
	
	@RequestMapping("oa/lab/editGwSq/{id}")
	public String editGwSq(@PathVariable("id") Integer id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",pcb);
		
		return "oa/lab/editGwSq";
	}
	
	@RequestMapping("oa/lab/pcbSqDetail/{id}")
	public String pcbSqDetail(@PathVariable("id") Integer id, HttpSession session, Model model){
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabAll pcb = this.labService.findPcbBySqId2(id);
//		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",pcb);
		
		return "oa/lab/pcbSqDetail";
	}
	
	@RequestMapping("oa/lab/gwSqDetail/{id}")
	public String gwSqDetail(@PathVariable("id") Integer id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",pcb);
		return "oa/lab/gwSqDetail";
	}
	
	@RequestMapping("oa/lab/pcbSqDetail2/{id}")
	public String pcbSqDetail2(@PathVariable("id") Integer id, HttpSession session, Model model){
		LabPcbSq sq = this.labService.findPcbSqById(id);
		Integer type = sq.getType();
//		id小于329的都是之前记录
		if (id >= 329) {
			LabAll pcb = this.labService.findPcbBySqId2(id);
			model.addAttribute("pcb",pcb);
		} else {
			// 1是pcb加工 2是钢网加工
			LabPcb pcb = this.labService.findPcbBySqId(id);
			if (type == 2) {
				pcb.setNumSet2(pcb.getNumSet());
				pcb.setCcChang2(pcb.getCcChang());
				pcb.setSize(pcb.getCcKuang().toString());
				pcb.setCpbh2(pcb.getCpbh());
				pcb.setCl2(pcb.getCl());
				pcb.setBmtc2(pcb.getBmtc());
				pcb.setBmtchd2(pcb.getBmtchd());
				pcb.setNumSet(null);
			}
			model.addAttribute("pcb",pcb);
		}
		model.addAttribute("sq",sq);
		
		return "oa/lab/pcbSqDetail2";
	}
	
	@RequestMapping("oa/lab/gwSqDetail2/{id}")
	public String gwSqDetail2(@PathVariable("id") Integer id, HttpSession session, Model model){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		model.addAttribute("sq",sq);
		model.addAttribute("pcb",pcb);
		
		return "oa/lab/gwSqDetail2";
	}
	
	@RequestMapping("oa/lab/pcbDownload/{id}")
	public void pcbDownload(@PathVariable("id") Integer id, HttpSession session, HttpServletResponse response){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabAll pcb = this.labService.findPcbBySqId2(id);
		
		File file = ExcelUtil.generateForPcb(pcb, sq);
		
		ServletUtil.downloadFile(response, file, pcb.getDdbh()+".xls");
	}
	
	@RequestMapping("oa/lab/gwDownload/{id}")
	public void gwDownload(@PathVariable("id") Integer id, HttpSession session, HttpServletResponse response){
		User loginUser = this.getLoginUser(session);
		
		LabPcbSq sq = this.labService.findPcbSqById(id);
		LabPcb pcb = this.labService.findPcbBySqId(id);
		
		File file = ExcelUtil.generateForGw(pcb, sq);
		ServletUtil.downloadFile(response, file, pcb.getDdbh()+".xls");
	}
	
	@RequestMapping("oa/lab/jgSq")
	public String jgSq(LabPcb pcb, String submitCode, HttpSession session, Model model){
		return "oa/lab/jgSq";
	}
	
	@RequestMapping("oa/lab/jgSp")
	public String jgSp(LabPcb pcb, String submitCode, HttpSession session, Model model){
		return "oa/lab/jgSp";
	}
	
	
	@RequestMapping("oa/lab/wxSq")
	public String wxSq(String fileName,String filePath,String processType,
			LabPcb pcb,LabHj hj,LabGw gw,String size,
			String submitCode, HttpSession session, Model model){
		if(StringUtils.isEmpty(fileName) && StringUtils.isEmpty(filePath)){
			return "oa/lab/wxsq";
		}
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "oa/lab/wxsq";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		LabAll all = new LabAll();
		all.setFileName(pcb.getFileName());
		all.setFilePath(pcb.getFilePath());
		all.setCreateTime(pcb.getCreateTime());
		String[] types = processType.split(",");
		for(String li:types){
			if(li.equals("1")){
				this.labService.addPcbPro(all,pcb);
			}else if(li.equals("2")){
				this.labService.addHjPro(all, hj);
			}else if(li.equals("3")){
				this.labService.addGwPro(all, gw);
				all.setSize(size);
			}
		}
		all.setType(processType);
		User loginUser = this.getLoginUser(session);
		LabPcbSq sq = new LabPcbSq();
		sq.setProposer(loginUser.getId());
		
		//获取制板管理员Id;
		List<User> users = this.userService.findUserByRole("制板负责人");
		for (User user:users) {
			sq.setZbId(user.getId());
		}
		sq.setCurrentId(sq.getZbId());
		sq.setStatus(1);
		sq.setProposerName(loginUser.getName());
		
		ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
		Integer dir = as.getDeptDirector();
		Integer tre = as.getTreasurer();
		Integer man = as.getManager();
		sq.setMgrId(dir);
		sq.setBossId(man);
		sq.setCreateTime(pcb.getCreateTime());
		sq.setCaiwuId(tre);
		sq.setType(1);
		
		if(dir == tre || dir == man) {
			sq.setMgrId(null);
		}
		if(tre == man) {
			sq.setCaiwuId(null);
		}
		this.labService.addAllSq(sq, all);
		//发送邮件，提示提交成功
		model.addAttribute("msg","您的申请已提交，请等待审批");
		String table = MailTableUtil.addWxjg(types, pcb.getCreateTime(),null);
		User user = this.userService.findById(sq.getCurrentId());
		this.sendMailTable(user, loginUser, Consts.MAIL_WXJG_APPLY, table);
		return "oa/lab/wxsq";
	}
	
	@RequestMapping("oa/lab/pcbSq")
	public String pcbSq(LabPcb pcb, String submitCode, HttpSession session, Model model){
		
		if(StringUtils.isEmpty(pcb.getFileName()) && StringUtils.isEmpty(pcb.getFilePath())){
			return "oa/lab/pcbSq";
		}
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "oa/lab/pcbSq";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		
		
		User loginUser = this.getLoginUser(session);
		LabPcbSq sq = new LabPcbSq();
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		sq.setBossId(Consts.managerId);
		sq.setCaiwuId(Consts.caiwuId);
		sq.setZbId(Consts.labGly);//写死了陈丽静
		sq.setCurrentId(sq.getZbId());
		sq.setStatus(1);
		
		//获取用户的职位，如果没有主管，就不需要主管审批
		Integer deptId = loginUser.getDeptId();
		Dept dept = null;
		if(deptId == null || deptId == 0){
			sq.setMgrId(null);
		}else{
			dept = this.userService.findDeptById(deptId);
			sq.setMgrId(dept.getMgrId());
		}
		sq.setCreateTime(pcb.getCreateTime());
		sq.setType(1);//pcb申请
		
		this.labService.addPcbSq(sq, pcb);
		
		//发送邮件，提示提交成功
		model.addAttribute("msg","您的申请已提交，请等待审批");
		return "oa/lab/pcbSq";
	}
	
	@RequestMapping("oa/lab/gwSq")
	public String gwSq(LabPcb pcb, String submitCode, HttpSession session, Model model){
		
		if(StringUtils.isEmpty(pcb.getFileName()) && StringUtils.isEmpty(pcb.getFilePath())){
			return "oa/lab/gwSq";
		}
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			return "oa/lab/gwSq";
		}
		session.removeAttribute(Consts.submitCode);
		session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
		
		
		User loginUser = this.getLoginUser(session);
		LabPcbSq sq = new LabPcbSq();
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		sq.setBossId(Consts.managerId);
		sq.setCaiwuId(Consts.caiwuId);
		sq.setZbId(Consts.labGly);//写死了陈丽静
		sq.setCurrentId(sq.getZbId());
		sq.setStatus(1);
		
		//获取用户的职位，如果没有主管，就不需要主管审批
		Integer deptId = loginUser.getDeptId();
		Dept dept = null;
		if(deptId == null || deptId == 0){
			sq.setMgrId(null);
		}else{
			dept = this.userService.findDeptById(deptId);
			sq.setMgrId(dept.getMgrId());
		}
		sq.setCreateTime(pcb.getCreateTime());
		sq.setType(2);//钢网申请
		
		this.labService.addPcbSq(sq, pcb);
		
		//发送邮件，提示提交成功
		model.addAttribute("msg","您的申请已提交，请等待审批");
		return "oa/lab/gwSq";
	}
	
	
	//订餐扣款邮件
	private void sendDckkMail(String toName, String fromName, double money, double yue, String to, String dayStr){
		
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，");
		sb.append(toName);
		sb.append("。</p>");
		sb.append("<p style='padding:15px 0 15px 0px;'>您的订餐账户因为").append(dayStr).append("订餐扣除").append(money).append("元。目前您的账户余额").append(yue).append("元。如有疑问，请联系");
		sb.append(fromName).append("。</p>");
		sb.append("<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "订餐账户充值（或扣款）通知", text);
		
	}
	
	/********************************NEW MAIL*******************************************/
	/**
	 * 
	 * @param user
	 *            收件人
	 * @param status
	 *            1是通过，其他失败
	 * @param type
	 *            申请类型
	 */
	private void sendMaileResult(User user, int status, String type, String table) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == -1 ? "<a style='color:green;'>未通过审批</a>" : "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type).append(result);
		sb.append("，请登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}


	/**
	 * 物料申请信息详情
	 * 
	 * @param user收件人
	 * @param user2
	 *            发件人
	 * @param type
	 *            邮件类型
	 * @param table
	 */
	private void sendMailTable(User user, User user2, String type, String table) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("向您提交了一个")
				.append(type).append("，请及时登录系统审批。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}
}
