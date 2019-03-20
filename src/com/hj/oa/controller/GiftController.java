package com.hj.oa.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hj.oa.Consts;
import com.hj.oa.bean.Gift;
import com.hj.oa.bean.GiftEvent;
import com.hj.oa.bean.User;
import com.hj.oa.service.GiftService;
import com.hj.oa.service.UserService;
import com.hj.util.ServletUtil;

@Controller
public class GiftController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GiftService giftService;
	

	//所有活动
	@RequestMapping("oa/gift/evt/list")
	public String listEvt(HttpSession session, Integer status, Model model){
		if(status == null){
			status = -2;
		}
		model.addAttribute("status", status);
		
		List<GiftEvent> list = giftService.findEvents(status);
		model.addAttribute("list", list);
		
		return "oa/gift/listEvt";
	}
	
	//开始活动
	@RequestMapping("oa/gift/evt/begin")
	public String beginEvt(HttpSession session, int evtId, Model model){
		
		GiftEvent evt = this.giftService.getEventById(evtId);
		
		if(evt.getStatus() == 0){
			evt.setStatus(1);
			this.giftService.updateEvent(evt);
		}
		
		List<GiftEvent> list = giftService.findEvents(null);
		model.addAttribute("list", list);
		
		return "oa/gift/listEvt";
	}
	
	//结束
	@RequestMapping("oa/gift/evt/end")
	public String endEvt(HttpSession session, int evtId, Model model){
		
		GiftEvent evt = this.giftService.getEventById(evtId);
		
		if(evt.getStatus() == 1){
			evt.setStatus(2);
			this.giftService.updateEvent(evt);
		}
		
		List<GiftEvent> list = giftService.findEvents(null);
		model.addAttribute("list", list);
		
		return "oa/gift/listEvt";
	}
	
	//删除活动
	@RequestMapping("oa/gift/evt/del")
	public String delEvt(HttpSession session, int evtId, Model model){
		
		GiftEvent evt = this.giftService.getEventById(evtId);
		
		if(evt.getStatus() != -1){
			evt.setStatus(-1);
			this.giftService.updateEvent(evt);
		}
		
		List<GiftEvent> list = giftService.findEvents(null);
		model.addAttribute("list", list);
		
		return "oa/gift/listEvt";
	}
	
	//添加活动
	@RequestMapping("oa/gift/evt/add")
	public String listEvt(HttpSession session, GiftEvent evt, Model model){
		
		if(evt == null || evt.getEndTime() ==null){
			return "oa/gift/addEvt";
		}
		
		evt.setCreateUser(this.getLoginUser(session).getId());
		evt.setStatus(0);
		
		giftService.addEvent(evt);
		
		List<GiftEvent> list = giftService.findEvents(null);
		model.addAttribute("list", list);
		model.addAttribute("msg", "操作成功，请继续给活动添加礼品。");
		
		return "oa/gift/listEvt";
	}
	
	//显示活动的礼物列表
	@RequestMapping("oa/gift/gift/list")
	public String addGift(HttpSession session, int evtId, Model model){
		
		GiftEvent evt = this.giftService.getEventById(evtId);
		
		List<Gift> list = giftService.getAllGift(evtId);
		model.addAttribute("list", list);
		model.addAttribute("evt", evt);
		model.addAttribute("size", list.size()+1);
		
		return "oa/gift/listGift";
	}
	
	//显示活动的礼物列表
	@RequestMapping("oa/gift/gift/del")
	public String delGift(HttpSession session, int id, Model model){
		
		List<Gift> list = this.giftService.findByGift(id);
		
		if(list.size()>0){
			model.addAttribute("msg","该礼物已经被选择，不能删除");
			return "oa/gift/listGift";
		}
		
		this.giftService.delGift(id);
		model.addAttribute("msg","操作成功");
		
		return "oa/gift/listGift";
	}
	
	//添加礼物
	@RequestMapping("oa/gift/gift/add")
	public String addGift(@RequestParam("name") String name, @RequestParam("eventId") Integer eventId, 
            @RequestParam("file") MultipartFile file, HttpSession session,  Model model){
		
		Gift gift = new Gift();
		gift.setName(name);
		gift.setEventId(eventId);
		
		if (!file.isEmpty()) {
            try {
            	String oName = file.getOriginalFilename();
            	if(oName==null){
            		oName = "";
            	}
            	oName = oName.toLowerCase();
            	if(!oName.contains("pdf")){
            		//上传文件格式不对
            		GiftEvent evt = this.giftService.getEventById(eventId);
            		List<Gift> list = giftService.getAllGift(gift.getEventId());
            		model.addAttribute("list", list);
            		model.addAttribute("evt", evt);
            		model.addAttribute("size", list.size()+1);
            		
            		model.addAttribute("msg", "您上传文件的格式不符合要求，请上传pdf文件");
            		return "oa/gift/listGift";
            	}
                byte[] bytes = file.getBytes();
                
                String savePath = "empInfo";
		        String filename = UUID.randomUUID()+".pdf";
		        String loc = savePath + File.separator +filename;
//                String filepath = Consts.uploadFileRootLoc + filename;
		        
		        File f = new File(Consts.uploadFileRootLoc + savePath);
				if(!f.exists()){
					f.mkdirs();
				}
				
				f = new File(Consts.uploadFileRootLoc + loc);
                
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(f));
                stream.write(bytes);
                stream.close();
                
                gift.setUrl(loc);//文件的存放位置
            } catch (Exception e) {
            	throw new RuntimeException(e);
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } 
		
		giftService.addGift(gift);
		
		GiftEvent evt = this.giftService.getEventById(eventId);
		List<Gift> list = giftService.getAllGift(gift.getEventId());
		model.addAttribute("list", list);
		model.addAttribute("evt", evt);
		model.addAttribute("size", list.size()+1);
		model.addAttribute("msg", "操作成功");
		
		return "oa/gift/listGift";
	}
	
	//编辑礼物信息
	@RequestMapping("oa/gift/gift/edit")
	public String editGift(@RequestParam("nameEdit") String nameEdit, @RequestParam("eventId") Integer eventId, @RequestParam("idEdit") Integer idEdit, 
            @RequestParam("fileEdit") MultipartFile fileEdit, HttpSession session,  Model model){
		
		Gift gift = this.giftService.getById(idEdit);
		gift.setName(nameEdit);
//		gift.setEventId(eventId);
		
		if (!fileEdit.isEmpty()) {
            try {
            	String oName = fileEdit.getOriginalFilename();
            	if(oName==null){
            		oName = "";
            	}
            	oName = oName.toLowerCase();
            	if(!oName.contains("pdf")){
            		//上传文件格式不对
            		GiftEvent evt = this.giftService.getEventById(eventId);
            		List<Gift> list = giftService.getAllGift(gift.getEventId());
            		model.addAttribute("list", list);
            		model.addAttribute("evt", evt);
            		model.addAttribute("size", list.size()+1);
            		
            		model.addAttribute("msg", "您上传文件的格式不符合要求，请上传pdf文件");
            		return "oa/gift/listGift";
            	}
                byte[] bytes = fileEdit.getBytes();
                
                String savePath = "empInfo";
		        String filename = UUID.randomUUID()+".pdf";
		        String loc = savePath + File.separator +filename;
//                String filepath = Consts.uploadFileRootLoc + filename;
		        
		        File f = new File(Consts.uploadFileRootLoc + savePath);
				if(!f.exists()){
					f.mkdirs();
				}
				
				f = new File(Consts.uploadFileRootLoc + loc);
                
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(f));
                stream.write(bytes);
                stream.close();
                
                gift.setUrl(loc);//文件的存放位置
            } catch (Exception e) {
            	throw new RuntimeException(e);
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } 
		
		giftService.updateGift(gift);
		
		GiftEvent evt = this.giftService.getEventById(eventId);
		List<Gift> list = giftService.getAllGift(eventId);
		model.addAttribute("list", list);
		model.addAttribute("evt", evt);
		model.addAttribute("size", list.size()+1);
		model.addAttribute("msg", "操作成功");
		
		return "oa/gift/listGift";
	}
	
	//显示用户参加的活动列表
	@RequestMapping("oa/gift/list")
	public String list(HttpSession session, Model model){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		String dayStr = sdf.format(new Date());
		
		List<GiftEvent> list = giftService.findEmpEvents(dayStr);
		model.addAttribute("list", list);
		
		return "oa/gift/listEmpEvt";
	}
	
	
	//我的 礼物
	@RequestMapping("oa/gift/mine")
	public String mine(HttpSession session, Integer evtId, Integer giftId, Model model){
		User loginUser = this.getLoginUser(session);
		
		GiftEvent evt = giftService.getEventById(evtId);
		
		if(evt==null || evt.getStatus() == -1){//活动不存在，或者被删除了
			return "redirect:/web/oa/gift/list";
		}
		
		model.addAttribute("evt", evt);
		
		Gift gift = giftService.getByEmp(loginUser.getId(),evtId);
		model.addAttribute("gift", gift);
		
		List<Gift> gifts = giftService.getAllGift(evtId);//
		model.addAttribute("gifts", gifts);
		
		model.addAttribute("over", false);//是否结束
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		String dayStr = sdf.format(new Date());
		
		if(evt.getStatus() == 2){//结束了
			model.addAttribute("over", true);
			return "oa/gift/mine";
		}
		
		if(dayStr.compareTo(evt.getEndTime()) >= 0){//结束了
			
			evt.setStatus(2);
			this.giftService.updateEvent(evt);//第一个进来发现到时间了，就改变状态
			
			model.addAttribute("over", true);
			return "oa/gift/mine";
		}
		
		/*
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		
		
		if(month>Calendar.JUNE){
			model.addAttribute("over", true);
			return "oa/gift/mine";
		}
		
		if(month==Calendar.JUNE && day>9){
			model.addAttribute("over", true);
			return "oa/gift/mine";
		}
		
		if(month==Calendar.JUNE && day==9 && hour>=10){
			model.addAttribute("over", true);
			return "oa/gift/mine";
		}*/
		
		if(null == giftId ){//查询
			return "oa/gift/mine";
		}
		
		//添加礼物
		this.giftService.saveEmpGift(gift, loginUser.getId(), giftId, evtId);
		gift = giftService.getById(giftId);
		model.addAttribute("gift", gift);
		model.addAttribute("msg", "操作成功");
		
		return "oa/gift/mine";
	}
	
	//用户的选择
	@RequestMapping("oa/gift/emps")
	public String emps(HttpSession session, Integer evtId, Model model){
		
		GiftEvent evt = giftService.getEventById(evtId);
		model.addAttribute("evt", evt);
		
		User loginUser = this.getLoginUser(session);
		
		if(evt == null || evt.getStatus() == -1 || evt.getManagerId() != loginUser.getId() ){
			return "redirect:/web/oa/gift/list";
		}
		
		List<Gift> gifts = giftService.getAllGift(evtId);//
		model.addAttribute("gifts", gifts);
		
		List<User> emps = giftService.getUnEmps(evtId);
		model.addAttribute("emps", emps);
		model.addAttribute("empsSize", emps.size());
		
		return "oa/gift/emps";
	}
	
	//礼品被选择的详情
	@RequestMapping("oa/gift/detail/{id}")
	public String giftDetail(@PathVariable("id") Integer id, Integer evtId, HttpSession session, Model model){
		
		GiftEvent evt = giftService.getEventById(evtId);
		model.addAttribute("evt", evt);
		
		Gift gift = giftService.getById(id);
		model.addAttribute("gift", gift);
		
		List<Gift> gifts = giftService.findByGift(id);
		model.addAttribute("gifts", gifts);
		
		return "oa/gift/detail";
	}
	
	//我的下属的测评
	@RequestMapping("oa/gift/show/{id}")
	public String showGift(@PathVariable("id") Integer id, HttpSession session, Model model){
		Gift gift = giftService.getById(id);
		model.addAttribute("gift", gift);
		
		return "oa/gift/showGift";
	}
	
	@RequestMapping("oa/gift/{id}.pdf")
	public void gift(@PathVariable("id") Integer id, HttpSession session, Model model, HttpServletResponse response){
		
		Gift gift = giftService.getById(id);
		
		if(gift == null){
			return;
		}
		GiftEvent evt = this.giftService.getEventById(gift.getEventId());
		if(evt==null || evt.getStatus() == -1){
			return;
		}
		
		String loc = gift.getUrl();
		
		File file = new File(Consts.uploadFileRootLoc + loc);
		
		ServletUtil.downloadFile(response, file, gift.getName(), "application/pdf");
	}
}
