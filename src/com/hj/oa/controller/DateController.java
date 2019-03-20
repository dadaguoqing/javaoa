package com.hj.oa.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.hj.ke.bean.CheckInFromKs;
import com.hj.ke.bean.JsonResult;
import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.User;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.EmpDayService;
import com.hj.oa.service.KqService;
import com.hj.oa.service.LeaveService;
import com.hj.oa.service.OAUtilService;
import com.hj.oa.service.UserService;
import com.hj.util.AutoAddCheckIn;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.HttpUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.TaskUtil;

@Controller
public class DateController extends BaseController{
	
	@Autowired
	private DateService dateService;
	
	//这块代码用来测试
	@Autowired
	private EmpDayService edSer;
	@Autowired
	private CheckInService cSer;
	@Autowired
	private UserService userSer;
	@Autowired
	private LeaveService leaveSer;
	@Autowired
	private OAUtilService oaSer;
	@Autowired
	private KqService kqService;
	
	private ObjectMapper objMapper = new ObjectMapper();
	
	
	@RequestMapping("oa/qj/hz")
	@ResponseBody
	public HashMap<String,String> huizong(){
		
		List<Leave> list = this.leaveSer.findAllLeaves();
		
		int count1 = 0;
		int countdl1 = 0;
		
		int count2 = 0;
		int countdl2 = 0;
		
		int count3 =0;
		int countdl3 = 0;
		
		int count4 = 0;
		int countdl4 = 0;
		
		for(Leave leave : list){
			Integer dl = leave.getDailiId();
			int hour = leave.getHours();
			int day = leave.getDays();
			int min = leave.getMinutes();
			
			int mins = day*8*60 + hour*60 + min;
			
			if(mins<=0){
				continue;
			}
			
			
			if(mins < 4*60){
				if(mins == 30 && dl !=null){//认为是早上迟到
					continue;
				}
				if(dl == null){
					count1++;
				}else{
					countdl1++;
				}
			}else if( mins< 8*60){
				if(dl == null){
					count2++;
				}else{
					countdl2++;
				}
			}else if(mins <2*8*60){
				if(dl == null){
					count3++;
				}else{
					countdl3++;
				}
			}else{
				if(dl == null){
					count4++;
				}else{
					countdl4++;
				}
			}
		}
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("４小时以内自己请假", count1+"");
		map.put("４小时以内代理请假", countdl1+"");
		
		map.put("４小时（含）—１天自己请假", count2+"");
		map.put("４小时（含）—１天代理请假", countdl2+"");
		
		map.put("１天（含）—２天自己请假", count3+"");
		map.put("１天（含）—２天天代理请假", countdl3+"");
		
		map.put("２天（含）以上自己请假", count4+"");
		map.put("２天（含）以上代理请假", countdl4+"");
		
		map.put("result", "ok");
		return map;
	}
	
	//模拟每日汇总
	@RequestMapping("oa/huizong/day")
	@ResponseBody
	public HashMap<String,String> huizong(HttpSession session,String kqDay, String sysDay, int reduceLeave, String submitCode, Model model){
		
		
		String sc = (String)session.getAttribute(Consts.submitCode);
		if(!sc.equals(submitCode)){//重复提交
			HashMap<String, String> map  = new HashMap<String,String>();
			map.put("result", "重复提交！");
			return map;
		}
		session.removeAttribute(Consts.submitCode);
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		TaskUtil tu = new TaskUtil();
		tu.setcSer(cSer);
		tu.setMailUtil(mailUtil);
		tu.setUserSer(userSer);
		tu.setDateService(dateService);
		tu.setKqService(kqService);
		
		boolean flag = (reduceLeave == 1);
		
		tu.rijieByMan(null, kqDay, sysDay, flag);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	//打印所有用户的年假结余
	@RequestMapping("oa/emp/njjy")
	public String getNyjy(Model model){
		
		List<EmpNianjia> list = oaSer.findAllNianjia2();
		
		for(EmpNianjia emj : list){
			emj.setNianjiaStr(OtherUtil.miniute2StringWithF(emj.getNianjia()));
			emj.setBingjiaStr(OtherUtil.miniute2StringWithF(emj.getBingjia()));
		}
		
		model.addAttribute("list", list);
		
		return "nyjy";
	}
	
	
	//修改从excel导入进去的用户的入职日期类型
	@RequestMapping("oa/emp/ued")
	@ResponseBody
	public HashMap<String,String> updateEmpEntryDate(HttpSession session){
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		List<User> users = this.userSer.findAllUsers();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/M/d");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy月MM月dd日");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		
		for(User u : users){
			String ed1 = u.getEntryDate();
			try{
				Date d = sdf1.parse(ed1);
				String ed = sdf2.format(d);
				u.setEntryDate(ed);
			}catch(Exception e){
				
			}
		}
		
		this.userSer.updateUser2ForExcelImport(users);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	//给初始化年假去个名字。
	@RequestMapping("oa/emp/nj")
	@ResponseBody
	public HashMap<String,String> addnjr(HttpSession session){
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		List<EmpNianjia> list = this.oaSer.findAllNianjia();
		List<NianjiaRecord> nrs = new ArrayList<NianjiaRecord>();
		
		String dayStr = "2014年04月01日";
		
		for(EmpNianjia en : list){
			NianjiaRecord nr = new NianjiaRecord();
			nr.setEmpId(en.getEmpId());
			nr.setDayStr(dayStr);
			nr.setBz("公司带薪年假");
			nr.setTimeLeft(en.getNianjia());
			nr.setTimeLen(-en.getNianjia());
			nr.setType("年假");
			nrs.add(nr);
			
			nr = new NianjiaRecord();
			nr.setEmpId(en.getEmpId());
			nr.setDayStr(dayStr);
			nr.setBz("公司带薪病假");
			nr.setTimeLeft(en.getBingjia());
			nr.setTimeLen(-en.getBingjia());
			nr.setType("病假");
			nrs.add(nr);
		}
		
		this.oaSer.addNianjiaRecord(nrs);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	//每个月、每个人、每天 数据汇总（打卡，请假，补打卡）
	@RequestMapping("oa/emp/huizong")
	@ResponseBody
	public HashMap<String,String> huiZong(String begin, String end, HttpSession session){
		
		User loginUser = this.getLoginUser(session);
//		if(loginUser.getId() != Consts.managerId){
//			throw new RuntimeException("您没有权限");
//		}
		
		List<User> allUser = this.userSer.findAllUsers();
		
		 //DateUtil.getCurrentTime("yyyy年MM月");
		
		//String begin = yf+"01日";
		//String end = yf + "31日";
		
		if(StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)){
			HashMap<String, String> map  = new HashMap<String,String>();
			map.put("result", "汇总失败，请填写开始日期，以及结束日期。");
			return map;
		}
		String yf = begin.substring(0, 8);
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String path = servletContext.getRealPath("/");
		
		for(User u : allUser){
			List<HashMap> list = this.kqService.findByEmpAndDayHuiZong(u.getId(), begin, end);
			//为每个人生成一个excel
			ExcelUtil.generateForKqHuiZong(list, yf, u.getName(),path);
		}
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "汇总成功，数据已经在服务器上生成。目录D:\\web\\upload\\kq\\"+yf);
		return map;
	}
	
	//个人每天 数据汇总（打卡，请假，补打卡）
	@RequestMapping("oa/emp/gr")
	@ResponseBody
	public HashMap<String,String> gr(HttpSession session){
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		User user = this.userSer.findById(12);
		
		
		 
		List<User> allUser = this.userSer.findAllUsers();//.findByDept(4)//new ArrayList<User>();
		allUser.add(user);
		
		String yf = "2014年03月";//DateUtil.getCurrentTime("yyyy年MM月");
		
		String begin = "2014年04月01日";
		String end = "2015年03月31日";
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String path = servletContext.getRealPath("/");
		
		for(User u : allUser){
			List<HashMap> list = this.kqService.findByEmpAndDayHuiZong(u.getId(), begin, end);
			//为每个人生成一个excel
			ExcelUtil.generateForKqHuiZong(list, yf, u.getName(),path);
		}
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	
	//给所有用户初始化年假
	@RequestMapping("oa/emp/hld")
	@ResponseBody
	public HashMap<String,String> addHoliday(HttpSession session){
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		List<EmpNianjia> list = new ArrayList<EmpNianjia>();
		
		List<User> users = userSer.findAllUsers();
		
		for(User user : users){
			EmpNianjia enj = new EmpNianjia(); 
			
			int id = user.getId();
			
			if(id == 31 || id == 53 ){
				enj.setBingjia(480 * 6);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*9);
			}else if (id == 13 || id == 46 || id == 49 ){
				enj.setBingjia(480 * 6);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*8);
			}else if (id == 4 || id == 25 ){
				enj.setBingjia(480 * 5);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*8);
			}else if (id == 14 || id == 39 || id == 26 || id == 47 || id == 48 || id == 19 || id == 40 ){ //7
				enj.setBingjia(480 * 5);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*7);
			}else if (id == 50 ){ //牛晓芬
				enj.setBingjia(480 * 4);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*6);
			}else if (id == 20 ){//李墩
				enj.setBingjia(480*4);
				enj.setEmpId(user.getId());
				enj.setNianjia(480*5);
			}else{
				enj.setBingjia(480 * 7);
				enj.setEmpId(user.getId());
				enj.setNianjia(4800);
			}
			
			list.add(enj);
		}
		
		this.oaSer.addEmpNianjia(list);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	} 
	
	//导入历史年假病假记录
	@RequestMapping("oa/emp/hnj")
	@ResponseBody
	public HashMap<String,String> historyNianjia(HttpSession session){
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		List<User> users = userSer.findAllUsers();
		Map<String, Integer> uMap = new HashMap<String, Integer>();
		for(User u : users){
			uMap.put(u.getName(), u.getId());
		}
		List<EmpNianjia> list = this.oaSer.findAllNianjia();
		Map<Integer, EmpNianjia> njMap = new HashMap<Integer, EmpNianjia>();
		for(EmpNianjia nj : list){
			njMap.put(nj.getEmpId(), nj);
		}
		
		List<NianjiaRecord> nrs = oaSer.findHistoryRecod();
		
		List<NianjiaRecord> adds = new ArrayList<NianjiaRecord>();
		Set<EmpNianjia> ups = new HashSet<EmpNianjia>();
		
		for(NianjiaRecord njr : nrs){
			NianjiaRecord nr = new NianjiaRecord();
			
			String name = njr.getTempName();
			Integer empId = uMap.get(name);
			if(empId == null){
				continue;
			}
			
			EmpNianjia enj = njMap.get(empId);
			if(enj == null){
				throw new RuntimeException("导入错误。必须有年假");
			}
			
			ups.add(enj);
			
			nr.setEmpId(empId);
			nr.setDayStr(njr.getDayStr());
			int timeLen = njr.getTimeLen();
			String type = njr.getType();
			String bz = njr.getBz();
			
			nr.setTimeLen(timeLen);
			if("年假".equals(type)){
				int njl = enj.getNianjia();
				enj.setNianjia(njl - timeLen);
				nr.setTimeLeft(enj.getNianjia());
				nr.setType("年假");
			}else if("病假".equals(type)){
				int bjl = enj.getBingjia();
				enj.setBingjia(bjl - timeLen);
				nr.setTimeLeft(enj.getBingjia());
				nr.setType("病假");
			}else{
				throw new RuntimeException("导入错误。");
			}
			
			bz = StringUtils.isNotEmpty(bz)?bz:"请假扣除";
			nr.setBz(bz);
			
			adds.add(nr);
		}
		
		oaSer.addNianjiaRecord(adds);
		oaSer.updataNianjia(ups);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	} 
	
	//导入历史考勤数据
	@RequestMapping("oa/emp/kqHst")
	@ResponseBody
	public HashMap<String,String> kqHst(HttpSession session) 
	throws JsonParseException, JsonMappingException, IOException, ParseException{
		String day = ""; //注意格式
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		String url = Consts.ksurl + "/rest/ks/all";
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, Calendar.APRIL);
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf22 = new SimpleDateFormat("yyyy-MM-dd");
		
		while(true){
			
			int y = 2014;
			int m = c.get(Calendar.MONTH);
			int dday = c.get(Calendar.DAY_OF_MONTH);
			
			Date dd = c.getTime();
			
			if(m==9 && dday == 13){
				break;
			}
		
			Map<String,String> params = new HashMap<String,String>();
			params.put("day", sdf22.format(dd));//格式
			day = sdf11.format(dd);
			
			String content = HttpUtil.getContent(url, params, null, null);
			
			JsonResult jr = objMapper.readValue(content, JsonResult.class);
			
			if(jr.getRet() == -1){
				HashMap<String, String> map  = new HashMap<String,String>();
				map.put("result", "notok");
				map.put("msg", jr.getMsg());
				return map;
			}
			
			//2014-06-14 08:41:22.0
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			
			List<CheckInFromKs> data = jr.getData();
			List<CheckIn> list = new ArrayList<CheckIn>();
			List<User> users = userSer.findAllUsers();
			Map<String,CheckInFromKs> cMap = new HashMap<String,CheckInFromKs>();
			
			for(CheckInFromKs cifk : data){
				cMap.put(cifk.getName(), cifk);
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if(null!=checkout){
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}
				cifk.setCheckin(checkin);
				cifk.setCheckout(checkout);
			}
			
			for(User u : users){
				CheckIn ci = new CheckIn();
				ci.setEmpId(u.getId());
				ci.setDayStr(day);
				CheckInFromKs cifk = cMap.get(u.getName());
				if(cifk != null){
					String checkin = cifk.getCheckin();
					String checkout = cifk.getCheckout();
					
					int checkinInt = time2Int(checkin);
					int checkoutInt = 0;
					if(null != checkout){
						checkoutInt = time2Int(checkout);
					}
					ci.setCheckin(checkin);
					ci.setCheckinInt(checkinInt);
					ci.setCheckout(checkout);
					ci.setCheckoutInt(checkoutInt);
				}
				list.add(ci);
			}
			
			this.cSer.addCheckInTemp(day, list);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	@RequestMapping("oa/emp/kq")
	@ResponseBody
	public HashMap<String,String> addkq(String realDay, String day, HttpSession session) 
		throws JsonParseException, JsonMappingException, IOException, ParseException{
		
		User loginUser = this.getLoginUser(session);
		if(loginUser.getId() != Consts.managerId){
			throw new RuntimeException("您没有权限");
		}
		
		String url = Consts.ksurl + "/rest/ks/all";
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("day", realDay);
		
		String content = HttpUtil.getContent(url, params, null, null);
		
		JsonResult jr = objMapper.readValue(content, JsonResult.class);
		
		if(jr.getRet() == -1){
			HashMap<String, String> map  = new HashMap<String,String>();
			map.put("result", "notok");
			map.put("msg", jr.getMsg());
			return map;
		}
		
		//2014-06-14 08:41:22.0
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		
		List<CheckInFromKs> data = jr.getData();
		List<CheckIn> list = new ArrayList<CheckIn>();
		List<User> users = userSer.findAllUsers();
		Map<String,CheckInFromKs> cMap = new HashMap<String,CheckInFromKs>();
		
		for(CheckInFromKs cifk : data){
			cMap.put(cifk.getName(), cifk);
			String checkin = cifk.getCheckin();
			String checkout = cifk.getCheckout();
			Date d = sdf1.parse(checkin);
			checkin = sdf2.format(d);
			if(null!=checkout){
				d = sdf1.parse(checkout);
				checkout = sdf2.format(d);
			}
			cifk.setCheckin(checkin);
			cifk.setCheckout(checkout);
		}
		
		for(User u : users){
			CheckIn ci = new CheckIn();
			ci.setEmpId(u.getId());
			ci.setDayStr(day);
			CheckInFromKs cifk = cMap.get(u.getName());
			if(cifk != null){
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				
				int checkinInt = time2Int(checkin);
				int checkoutInt = 0;
				if(null != checkout){
					checkoutInt = time2Int(checkout);
				}
				ci.setCheckin(checkin);
				ci.setCheckinInt(checkinInt);
				ci.setCheckout(checkout);
				ci.setCheckoutInt(checkoutInt);
			}
			list.add(ci);
		}
		
		this.cSer.addCheckInNew(day, list);
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	} 
	
	
	/*
	@RequestMapping("oa/emp/day")
	public HashMap<String,String> test(){
		
		
		
		
		for(int i=1; i<15; i++){
			String day = i<10? "0"+i : ""+i;
			cSer.addCheckIn( this.randomCheckin("2014年07月"+day+"日", userSer.findAllUsers()) ,"2014年07月"+day+"日");
			
			//AutoAddCheckIn.addCheckin(cSer, edSer, "2014年06月"+day+"日");
		}
		
		
		
		//获取用户打卡记录
		//List<CheckIn> cList = cSer.findByDay("2014年06月09日");
		
		//判断是否为节假日，如果是，则不需要做任何其它事情。
		
		//获取用户当天的请假记录
		
		
		//获取用户当天的加班记录
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	*/
	private List<CheckIn> randomCheckin(String dayStr, List<User> users){
		
		Random r = new Random();
		String[] cis = new String[]{"08:05","08:30","07:40","08:35","08:55","08:25","9:35",
				"08:05","08:30","07:40","08:05","08:30","07:40","08:05","08:30","07:40",
				"08:05","08:30","07:40","08:05","08:30","07:40","08:05","08:30","07:40",
				"08:05","08:30","07:40","08:05","08:30","07:40","08:05","08:30","07:40",
				"08:05","08:30","07:40","08:05","08:30","07:40","08:05","08:30","07:40"};
		
		String[] cos = new String[]{"18:05","17:30","17:50","17:15","16:55","18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50"
				,"18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50"
				,"18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50","18:25","17:45","17:30","17:50"};
		
		List<CheckIn> list = new ArrayList<CheckIn>();
		
		for(User u : users){
			CheckIn c = new CheckIn();
			
			c.setCheckin(cis[r.nextInt(cis.length)]);
			c.setCheckout(cos[r.nextInt(cos.length)]);
			String checkin = c.getCheckin();
			String checkout = c.getCheckout();
			c.setCheckinInt(time2Int(checkin));
			c.setCheckoutInt(time2Int(checkout));
			c.setEmpId(u.getId());
			c.setDayStr(dayStr);
			
			list.add(c);
		}
		
		return list;
	}
	
	private int time2Int(String time){
		String[] ss = time.split(":");
		
		String hStr = ss[0];
		String mStr = ss[1];
		
		if(hStr.startsWith("0")){
			hStr = hStr.substring(1);
		}
		if(mStr.startsWith("0")){
			mStr = mStr.substring(1);
		}
		int h = Integer.parseInt(hStr);
		int m = Integer.parseInt(mStr);
		
		return h * 60 + m;
	}
	
	@Autowired
	private MailUtil mailUtil;
	
	//测试发送mail
	@RequestMapping("oa/mail/test")
	@ResponseBody
	public HashMap<String,String> sendMail(){
		
		mailUtil.sendToMe("测试邮件发送", "This is a test email. from macroSilicon WE System.");
		
		HashMap<String, String> map  = new HashMap<String,String>();
		map.put("result", "ok");
		return map;
	}
	
	//测试代码结束
	
	@RequestMapping("oa/date/set")
	public String setDate(Day day, int[] days, Model model){
		if(days == null){
			days = new int[]{};
		}
		List<Day> list = new ArrayList<Day>(days.length);
		for(int i : days){
			Day d = new Day();
			d.setDay(i);
			d.setMonth(day.getMonth());
			d.setYear(day.getYear());
			d.setType(day.getType());
			list.add(d);
		}
		dateService.updateDate(list);
		return "redirect:/web/oa/date/mgr2?year="+day.getYear()+"&month="+day.getMonth();
	}
	
	@RequestMapping("oa/date/mgr2")
	public String mgrDate2(int year, int month, Model model){
		Day day = new Day();
		
		if(year !=0 && month!=0){
			day.setYear(year);
			day.setMonth(month);
		}else{
			day = new Day(true);
		}
		
		
		List<Day> days = dateService.setByMonth(day);

		List<HashMap<String,Day>> list = new ArrayList<HashMap<String,Day>>();
		
		HashMap<String,Day> map = new HashMap<String,Day>();
		for(int i=0; i<days.size(); i++){
			Day d = days.get(i);
			int dow = d.getDayOfWeek();
			switch(dow){
			case 1:
				map.put("mon", d);
				break;
			case 2:
				map.put("tue", d);
				break;
			case 3:
				map.put("wed", d);
				break;
			case 4:
				map.put("thu", d);
				break;
			case 5:
				map.put("fri", d);
				break;
			case 6:
				map.put("sat", d);
				list.add(map);
				map = new HashMap<String,Day>();
				break;
			case 7:
				map.put("sun", d);
				break;
			}
			
			if(i==days.size()-1 && dow!=6){
				list.add(map);
			}
		}
		model.addAttribute("list",list);
		model.addAttribute("day",day);
		return "oa/date/mgr";
	}
	
	@RequestMapping("oa/date/cal")
	public String cal(String yam, String pn, Model model){
		Day day = null;
		if(yam != null && yam.length()!=0){
			String fmt = "yyyy年M月";
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			try {
				Date d = sdf.parse(yam);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				
				if("pre".equals(pn)){
					c.add(Calendar.MONTH, -1);
				}else if("next".equals(pn)){
					c.add(Calendar.MONTH, 1);
				}
				
				day = new Day();
				day.setYear(c.get(Calendar.YEAR));
				day.setMonth(c.get(Calendar.MONTH)+1);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}else{
			day = new Day(true);
		}
		
		List<Day> days = dateService.setByMonth(day);
		
		Day today = new Day(true);
		boolean flag = false;
		if(today.getYear() == day.getYear() && today.getMonth() == day.getMonth()){//设置当前日期
			//model.addAttribute("curMonth",today.getDay());
			flag = true;
		}
		
		List<HashMap<String,Day>> list = new ArrayList<HashMap<String,Day>>();
		
		HashMap<String,Day> map = new HashMap<String,Day>();
		for(int i=0; i<days.size(); i++){
			Day d = days.get(i);
			
			if(flag && d.getDay() == today.getDay()){
				d.setDayStr("curDay");
			}
			int dow = d.getDayOfWeek();
			switch(dow){
			case 1:
				map.put("mon", d);
				break;
			case 2:
				map.put("tue", d);
				break;
			case 3:
				map.put("wed", d);
				break;
			case 4:
				map.put("thu", d);
				break;
			case 5:
				map.put("fri", d);
				break;
			case 6:
				map.put("sat", d);
				list.add(map);
				map = new HashMap<String,Day>();
				break;
			case 7:
				map.put("sun", d);
				break;
			}
			
			if(i==days.size()-1 && dow!=6){
				list.add(map);
			}
		}
		
		model.addAttribute("list",list);
		model.addAttribute("day",day);
		
		return "oa/date/rili";
	}
	
	@RequestMapping("oa/date/mgr")
	public String mgrDate(String yam, String pn, Model model){
		Day day = null;
		if(yam != null && yam.length()!=0){
//			try {
//				yam = new String(yam.getBytes("iso8859-1"),"utf-8");
//			} catch (UnsupportedEncodingException e1) {
//				throw new RuntimeException(e1);
//			}
			String fmt = "yyyy年M月";
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			try {
				Date d = sdf.parse(yam);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				
				if("pre".equals(pn)){
					c.add(Calendar.MONTH, -1);
				}else if("next".equals(pn)){
					c.add(Calendar.MONTH, 1);
				}
				
				day = new Day();
				day.setYear(c.get(Calendar.YEAR));
				day.setMonth(c.get(Calendar.MONTH)+1);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}else{
			day = new Day(true);
		}
		List<Day> days = dateService.setByMonth(day);

		List<HashMap<String,Day>> list = new ArrayList<HashMap<String,Day>>();
		
		HashMap<String,Day> map = new HashMap<String,Day>();
		for(int i=0; i<days.size(); i++){
			Day d = days.get(i);
			int dow = d.getDayOfWeek();
			switch(dow){
			case 1:
				map.put("mon", d);
				break;
			case 2:
				map.put("tue", d);
				break;
			case 3:
				map.put("wed", d);
				break;
			case 4:
				map.put("thu", d);
				break;
			case 5:
				map.put("fri", d);
				break;
			case 6:
				map.put("sat", d);
				list.add(map);
				map = new HashMap<String,Day>();
				break;
			case 7:
				map.put("sun", d);
				break;
			}
			
			if(i==days.size()-1 && dow!=6){
				list.add(map);
			}
		}
		model.addAttribute("list",list);
		model.addAttribute("day",day);
		return "oa/date/mgr";
	}
}
