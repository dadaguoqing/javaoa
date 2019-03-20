package com.hj.oa.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.DcCaidan;
import com.hj.oa.bean.DcCaidanVo;
import com.hj.oa.bean.DcEmpBalance;
import com.hj.oa.bean.DcEmpBalanceRecord;
import com.hj.oa.bean.DcEmpCaidan;
import com.hj.oa.bean.DcEmpDay;
import com.hj.oa.bean.DcProvider;
import com.hj.oa.bean.DingCan;
import com.hj.oa.bean.TodayOrder;
import com.hj.oa.bean.User;
import com.hj.oa.dao.DcMapper;
import com.hj.util.DateUtil;
import com.hj.util.MailUtil;

@Service
public class DcService {
	
	/**
	 * UserService相关服务
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 发送邮件工具类
	 */
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private DcMapper dcMapper;
	
	//供应商管理相关
	public void addProvider(DcProvider provider){
		dcMapper.addProvider(provider);
	}
	
	public void deleteProvider(int id){
		dcMapper.deleteProvider(id);
	}
	
	public void updateProvider(DcProvider provider){
		dcMapper.updateProvider(provider);
	}
	
	public List<DcProvider> findAllProvider(){
		return dcMapper.findAllProvider();
	}
	
	public DcProvider findProviderById(int id){
		return dcMapper.findProviderById(id);
	}
	
	public void addMenu(DcCaidan caidan){
		dcMapper.addMenu(caidan);
	}
	
	public List<DcCaidan> findMenuByProvider(int providerId){
		return dcMapper.findMenuByProvider(providerId);
	}
	
	public List<DcCaidan> findAllMenu(){
		return dcMapper.findAllMenu();
	}
	
	public void deleteMenu(int id){
		dcMapper.deleteMenu(id);
	}
	
	//供应商管理相关（结束）
	
	
	/**inserts the automating info into the database
	 * zeroth cat
	 * @param userId
	 * @param price
	 * @param provider
	 * @param description
	 */
	public void insertAuto(DcEmpBalance dcb){
		this.dcMapper.insertAuto(dcb);
	}
	
	public void insertAutoInfoList(int userId,int classifyId,int num,int autoStatus){
		TodayOrder to = new TodayOrder();
		to.setAutoStatus(autoStatus);
		to.setClassifyId(classifyId);
		to.setEmpId(userId);
		to.setNum(num);
		this.dcMapper.insertAutoInfo(to);
	}
	
	//订餐管理相关
	public void addDc(DingCan dc, List<DcCaidan> cds, List<User> emps){
		this.dcMapper.addDc(dc);
		for(DcCaidan cd : cds){
			cd.setDcId(dc.getId());
			dcMapper.addDcCaidan(cd);
		}
		for(User u : emps){
			DcEmpDay ded = new DcEmpDay();
			ded.setDayStr(dc.getDayStr());
			ded.setDcId(dc.getId());
			ded.setEmpId(u.getId());
			ded.setEmpName(u.getName());
			ded.setPrice(0);
			ded.setStatus(0);
			dcMapper.addEmpDcDay(ded);
		}
		/*
		DcEmpDay ded = new DcEmpDay();
		ded.setDayStr(dc.getDayStr());
		ded.setDcId(dc.getId());
		ded.setEmpId(0);
		ded.setEmpName("客户订餐");
		ded.setPrice(0);
		ded.setStatus(0);
		dcMapper.addEmpDcDay(ded);
		*/
	}
	
	public void delDc(int dcId){
		dcMapper.deleteDcEmpCd(dcId);
		dcMapper.deleteDcEmpDay(dcId);
		dcMapper.deleteDcCd(dcId);
		dcMapper.deleteDc(dcId);
	}
	
	public List<DcCaidan> findCaidanByDcId(int dcId){
		return dcMapper.findCaidanByDcId(dcId);
	}
	public int findDesOIdByDes(String des){
		return dcMapper.findDesOIdByDes(des);
	}
	
	public DcCaidan findCaidanById(int id){
		return dcMapper.findCaidanById(id);
	}
	
	public DcEmpDay findEmpDcByDay(int empId, String dayStr){
		return dcMapper.findEmpDcByDay(empId, dayStr);
	}
	
	public int findEmpIdByName(String empName){
		return dcMapper.findEmpIdByName(empName);
	}
	
	public Integer isAutoDc(int id){
		return dcMapper.isAutoDc(id);
	}
	
	public List<DcEmpDay> findEmpDCListByDcEmpDay(DcEmpDay a){
		return dcMapper.findEmpDCListByDcEmpDay(a);
	}
	
	public DcEmpDay findEmpDCByDcEmpDay(DcEmpDay a){
		return dcMapper.findEmpDCByDcEmpDay(a);
	}
	
	public DcEmpDay findEmpDcById(Integer id){
		return dcMapper.findEmpDcById(id);
	}
	
	public DingCan findDcByDay(String dayStr){
		return dcMapper.findDcByDay(dayStr);
	}
	
	public DingCan findDcById(int id){
		return dcMapper.findDcById(id);
	}
	
	public void updateStatus(DingCan dc){
		 this.dcMapper.updateStatus(dc);
	}
	
	
	//zeroth cat
	public int findEmpDcIdByEmpIDandDcId(int empid, int dcid){
		DcEmpDay tempDay = new DcEmpDay();
		tempDay.setEmpId(empid);
		tempDay.setDcId(dcid);
		
		return this.dcMapper.findEmpDcIdByDcEmpDay(tempDay);
		
	}
	
	/**
	 * zeroth cat's function
	 * @param empCaidans
	 * @param empDay
	 */
	public void AutoEmpDingcai(List<DcEmpCaidan> empCaidans, DcEmpDay empDay){
		
		for(DcEmpCaidan empCaidan : empCaidans){
			dcMapper.addEmpDcCaidan(empCaidan);
		}
		List<DcEmpCaidan> cds = empCaidans;
		int price = 0;
		for(DcEmpCaidan cd : cds){
			price += cd.getPrice();
		}
		
		empDay.setStatus(1);
		empDay.setPrice(price);
		
		dcMapper.updateEmpDcDay(empDay);
	}
	
	public void empDingcai(List<DcEmpCaidan> empCaidans, DcEmpDay empDay){
		
		for(DcEmpCaidan empCaidan : empCaidans){
			dcMapper.addEmpDcCaidan(empCaidan);
		}
		List<DcEmpCaidan> cds = this.findEmpDcCaidanByDcId(empDay.getDcId(), empDay.getId());
		int price = 0;
		for(DcEmpCaidan cd : cds){
			price += cd.getPrice();
		}
		empDay.setStatus(1);
		empDay.setPrice(price);
		
		dcMapper.updateEmpDcDay(empDay);
	}
	
	public void removeEmpDcCaidan(DcEmpDay empDay, Integer cdId){
		dcMapper.removeEmpDcCaidan(cdId, empDay.getId());
		
		List<DcEmpCaidan> cds = this.findEmpDcCaidanByDcId(empDay.getDcId(), empDay.getId());
		Integer price = 0;
		for(DcEmpCaidan cd : cds){
			price += cd.getPrice();
		}
		
		//empDay.setStatus(1);
		empDay.setPrice(price);
		
		dcMapper.updateEmpDcDay(empDay);
	}
	
	public void empCancelDingcai(DcEmpDay empDay){
		empDay.setStatus(1);
		dcMapper.updateEmpDcDay(empDay);
	}
	
	public List<DcEmpCaidan> findEmpDcCaidanByDcId(int dcId, int empDcId){
		return dcMapper.findEmpDcCaidanByDcId(dcId, empDcId);
	}
	
	public List<TodayOrder>  findAutoOrderByEmpId(int id){
		return dcMapper.findAutoOrderByEmpId(id);
	}
	
	public List<TodayOrder>  findAutoOrderAll(){
		return dcMapper.findAutoOrderAll();
	}
	
	public DcEmpCaidan  findDescriptById(int id){
		return dcMapper.findDescriptById(id);
	}
	public void  deleteAutoOrder(int id){
		dcMapper.deleteAutoOrder(id);
	}
	
	public List<DcCaidanVo> findHuizongByDcId(int dcId){
		return dcMapper.findHuizongByDcId(dcId);
	}
	
	public List<DcCaidanVo> findTotalByDcId(int dcId,int id){
		return dcMapper.findTotalByDcId(dcId,id);
	}
	
	public List<DingCan> findOrderStatus(DingCan dc){
		return dcMapper.findOrderStatus(dc);
	}
	
	public String findEmpName(List<DingCan> list){
		String empNames = "";
		for(DingCan li:list){
			empNames += li.getEmpName()+" ";
		}
		return empNames;
	}
	
	public String findEmpNameByEmpId(List<DingCan> list){
		String empNames = "";
		for(DingCan li:list){
			empNames += this.findEmpNameById(li.getEmpId())+" ";
		}
		return empNames;
	}
	
	
	
	public String findEmpNameById(int empId){
		return dcMapper.findEmpNameById(empId);
	}
	
	public List<DingCan> findAutoOrder(int status){
		return dcMapper.findAutoOrder(status);
	}
	
	public List<DcCaidanVo> findDcDetail(int dcId, Integer cdId){
		return dcMapper.findDcDetail(dcId);
	}
	
	public List<DcCaidanVo> findDcDetailByEmp(int empId, String begin, String end){
		return dcMapper.findDcDetailByEmp(empId, begin, end);
	}
	
	public List<DcEmpDay> findEmpDcByDcId(int dcId){
		return dcMapper.findEmpDcByDcId(dcId);
	}
	
	public List<DcEmpDay> findEmpDcByDcIdUnDc(int dcId){
		return dcMapper.findEmpDcByDcIdUnDc(dcId);
	}
	
	public void updateDingcan(DingCan dc, List<DcEmpDay> eds){
		dcMapper.updateDingcan(dc);
		for(DcEmpDay ed : eds){
			if(ed.getStatus()==0){
				ed.setStatus(1);
				dcMapper.updateEmpDcDay(ed);
			}
			
		}
	}
	/*
	public void addEmpDcCaidan(DcEmpCaidan empCaidan){
		dcMapper.addEmpDcCaidan(empCaidan);
	}*/
	
	//订餐费用相关
	public void addEmpBalances(List<DcEmpBalance> balances){//批量添加账号，用户初始化
		for(DcEmpBalance balance: balances){
			addEmpBalance(balance);
		}
	}
	
	public void addEmpBalance(DcEmpBalance balance){//添加账号，一般用于新增用户
		dcMapper.addEmpBalance(balance);
	}
	
	public DcEmpBalance findBalanceByEmpId(int empId){
		return dcMapper.findBalanceByEmpId(empId);
	}
	
	public List<DcCaidan> findCaidanByDate(String today){
		return dcMapper.findCaidanByDate(today);
	}
	
	public List<DcEmpBalance> findAllBalance(){
		return dcMapper.findAllBalance();
	}
	
	public List<DcEmpBalance> findTempBalance(){
		return dcMapper.findTempBalance();
	}
	
	public void updateEmpBalance(DcEmpBalance blance, DcEmpBalanceRecord record){
		dcMapper.updateEmpBalance(blance);
		dcMapper.addBalanceRecord(record);
	}
	
	public void updateEmpBalance(List<DcEmpBalance> blances){
		for(DcEmpBalance blance : blances){
			dcMapper.updateEmpBalance(blance);
		}
	}
	
	public void updateEmpBalance(List<DcEmpBalance> blances, List<DcEmpBalanceRecord> records, DingCan dc){
		for(DcEmpBalance blance : blances){
			dcMapper.updateEmpBalance(blance);
		}
		for(DcEmpBalanceRecord record : records){
			dcMapper.addBalanceRecord(record);
		}
		dc.setStatus(2);
		dcMapper.updateDingcan(dc);
	}

	public List<DcEmpBalanceRecord> findBalanceRecordByEmpId(int empId, String begin, String end){
		return dcMapper.findBalanceRecordByEmpId(empId, begin, end);
	}
	
	public List<DcEmpBalanceRecord> findBalanceRecord(String begin, String end){
		return dcMapper.findBalanceRecord(begin, end);
	}
	
	/**
	 * 添加今天订餐的订单信息
	 * */
	public List<DcEmpCaidan> addTodayOrder(String cdId,String num,int empDcId,DcEmpDay empDc){
		String[] cdIds = cdId.split(",");
		String[] nums = num.split(",");

		int len = cdIds.length;
		List<DcEmpCaidan> cds = new ArrayList<DcEmpCaidan>();
		for (int i = 0; i < len; i++) {

			int cdIdInt = Integer.parseInt(cdIds[i]);
			int numInt = Integer.parseInt(nums[i]);

			DcCaidan cd = this.findCaidanById(cdIdInt);

			int price = numInt * cd.getPrice();
			DcEmpCaidan caidan = new DcEmpCaidan();
			caidan.setCdId(cdIdInt);
			caidan.setNum(numInt);
			caidan.setEmpDcId(empDcId);
			caidan.setDcId(empDc.getDcId());
			caidan.setPrice(price);
			cds.add(caidan);
		}
		return cds;
	}
	
	/**
	 * zeroth cat
	 * @param empid
	 */
	public void endAuto(int empid){
		this.dcMapper.endAuto(empid);
	}
	
	/**
	 * zeroth cat's 
	 * @param start_time
	 * @throws ParseException
	 */
	public Timer autoDC(String start_time) throws ParseException{
		DateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		
		String real_str = start_time.substring(0,4)+"/"+start_time.substring(5,7)+"/"+start_time.substring(8,10);
		//string when you decide the program will run
		real_str = real_str + " " + start_time.substring(12,14)+":"+start_time.substring(15,17)+":00";
		Date run_d = dayFormat.parse(real_str);//parse("2017/06/13 01:40:50");
		Timer timer = new Timer();
		timer.schedule(new myTimeTask(), run_d);
		
		return timer;
	}
	
	/**
	 * zeroth cat
	 * @author zychen
	 *
	 */
	private class myTimeTask extends TimerTask{
		public void run(){
			//my task  that I'll run
			
			String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
			DingCan dc = findDcByDay(dayStr);
			
			int TodayDingCanId = dc.getId();
			//I have to grab this dc
			
			if(dc!=null){
				List<DcCaidanVo> list = findHuizongByDcId(dc.getId());
				
				//model.addAttribute("list", list);
				DcCaidanVo total = new DcCaidanVo();
				int num = 0;
				int priceAll = 0;
				for(DcCaidanVo vo : list){
					num += vo.getNum();
					priceAll += vo.getPriceAll();
				}
				total.setNum(num);
				total.setPriceAll(priceAll);
				//I also have to grab this total
				//model.addAttribute("total", total);
			}
			
			
			//the enddc
			int id = dc.getId();
			DingCan dc_second = findDcById(id);
			dc_second.setStatus(1);
			
			
			List<DcEmpDay> eds = findEmpDcByDcId(dc_second.getId());
			updateDingcan(dc_second, eds);
			
			//the taking away of money from accounts
			
			/**
			 * this is the part that doesn't allow every user to update
			User loginUser = this.getLoginUser(session);
			List<Role> roles = this.getLoginUserRole(session);
			
			if(!RoleUtil.hasRole(roles, "考勤管理员")){
				return "redirect:/web/oa/index";
			}
			*/
			
			DingCan dc_third = findDcById(id);
			int dcStatus = dc_third.getStatus();
			
			if(dcStatus != 1){//当前订餐的状态必须是 （1-结束订餐)
				return;
			}
			
			List<DcEmpDay> empDays = findEmpDcByDcId(dc_third.getId());
			List<DcEmpBalance> balances = findAllBalance();
			
			Map<Integer,DcEmpBalance> bMap = new HashMap<Integer,DcEmpBalance>();
			
			for( DcEmpBalance b : balances){
				bMap.put(b.getEmpId(), b);
			}
			
			List<DcEmpBalance> updateBalance = new ArrayList<DcEmpBalance>();
			List<DcEmpBalanceRecord> addRecords = new ArrayList<DcEmpBalanceRecord>();
			
			String dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
			String dayStr_second = dc_third.getDayStr();//dayTime.substring(0,11);
			String bz = null;
			if(!DateUtil.getCurrentTime(Consts.chinaDateFmt).equals(dc_third.getDayStr())){
				//dayTime = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
				bz = "扣除"+dayStr+"订餐餐费";
			}
			
			for(DcEmpDay empDay : empDays){
				int money = empDay.getPrice(); //用户订餐总额必须计算正确
				if(money==0){ //没有订餐、或者不需要扣款
					continue;
				}
				
				DcEmpBalance balance = bMap.get(empDay.getEmpId());
				DcEmpBalanceRecord record = new DcEmpBalanceRecord();
				if (balance==null){
					continue;
				}
				double yue = balance.getBalance() - money;
				
				balance.setBalance(yue);
				
				record.setBalance(yue);
				record.setMoney(money);
				record.setType("订餐扣除");
				record.setEmpId(balance.getEmpId());
				record.setDayTime(dayTime);
				record.setDayStr(dayStr);
				record.setBz(bz);
				
				updateBalance.add(balance);
				addRecords.add(record);
			}
			
			
			
			//zeroth cat's commenting out a really importnat part
			updateEmpBalance(updateBalance, addRecords, dc_third);
			
			// 给所有扣款用户发送扣款邮件
			List<User> allUsers = userService.findAllUsers();
			HashMap<Integer, User> uMap = new HashMap<Integer, User>();
			for (User user : allUsers) {
				uMap.put(user.getId(), user);
			}
			sendKkMail(uMap, addRecords);
			
		}
	}
	
	/**
	 * 
	* @Description: 不做大修改，保持现有功能正常运行 
	* @param uMap
	* @param addRecords
	* @author mlsong
	* @date 2017年8月3日 下午3:31:54
	 */
	private void sendKkMail(HashMap<Integer, User> uMap, List<DcEmpBalanceRecord> addRecords) {

		for (DcEmpBalanceRecord r : addRecords) {
			int uid = r.getEmpId();
			User user = uMap.get(uid);
			String to = user.getEmail();
			sendDckkMail(user.getName(), r.getMoney(), r.getBalance(), to, r.getDayStr());
		}
	}

	/** 
	* @Description: 订餐扣款邮件 
	* @param toName
	* @param money
	* @param yue
	* @param to
	* @param dayStr
	* @author mlsong
	* @date 2017年8月3日 下午3:33:18
	*/
	private void sendDckkMail(String toName, double money, double yue, String to, String dayStr) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<html><head></head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，");
		sb.append(toName);
		sb.append("。</p>");
		sb.append("<p style='padding:15px 0 15px 0px;'>您的订餐账户因为").append(dayStr).append("订餐扣除").append(money)
				.append("元。目前您的账户余额").append(yue).append("元。如有疑问，请联系");
		sb.append("相关工作人员").append("。</p>");
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "订餐账户充值（或扣款）通知", text);

	}
	
	
	
}
