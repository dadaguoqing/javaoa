package com.hj.oa.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.CheckInRemote;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.EmpDaySum;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveDayDetail;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.User;
import com.hj.oa.dao.CheckInMapper;
import com.hj.oa.dao.DateMapper;
import com.hj.oa.dao.KqMapper;
import com.hj.oa.dao.LeaveMapper;
import com.hj.oa.dao.OAUtilMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;
import com.hj.util.DayCountUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.MailTableUtil;
import com.hj.util.OtherUtil;

@Service
public class CheckInService {

	@Autowired
	private CheckInMapper cmapper;
	@Autowired
	DateMapper dateMapper;
	@Autowired
	OAUtilMapper oaUtilMapper;

	@Autowired
	KqMapper kqMapper;
	@Autowired
	LeaveMapper leaveMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	MailTableUtil mailTableUtil;
	
	//A3
	public synchronized void addRemoteCheckIn(List<CheckInRemote> list, String dayStr){
		//cmapper.deleteRemoteCheckInByDay(dayStr,list);//先删除之前的遗留数据
		if(list.isEmpty()){
			return;
		}
		//插入最新数据
		for(CheckInRemote remote:list){
			this.cmapper.addRemoteCheckIn(remote);
		}
	}
	
	public CheckInRemote findRemoteCheckInByName(String name, String dayStr){
		return this.cmapper.findRemoteCheckInByName(name, dayStr);
	}
	
	public List<CheckInRemote> findRemoteCheckInByDay(String dayStr){
		return this.cmapper.findRemoteCheckInByDay(dayStr);
	}
	
//	public void deleteRemoteCheckInByDay(String dayStr){
//		this.cmapper.deleteRemoteCheckInByDay(dayStr);
//	}
	
	//A3 end....

	public List<CheckIn> findByEmp(int empId){
		return this.cmapper.findByEmp(empId);
	}
	
	
	
	public CheckIn findByDayAndEmp(String dayStr, int empId) {
		return this.cmapper.findByDayAndEmp(dayStr, empId);
	}

	public void addCheckIn(List<CheckIn> cs, String day) {

		this.cmapper.deleteCheckinByDay(day);
		cmapper.addCheckIn(cs);
	}
	
	public void importEmpCheckIns(List<File> list, Map<String, User> uMap){
		try{
			for(File exl: list){
				List<CheckIn> cis = ExcelUtil.getCheckInFromFile(new FileInputStream(exl), uMap);
				addEmpCheckIns(cis);
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void addEmpCheckIns(List<CheckIn> list) {
		for (CheckIn ci : list) {
			//addOneCheckIn(ci);
			this.cmapper.deleteCheckinByDayAndEmpId(ci.getDayStr(), ci.getEmpId());
			this.cmapper.addACheckIn(ci);
			//刷新当日考勤
			this.reRijie(ci.getDayStr(), ci);
		}
	}
	
	//销售部专用
	public void addEmpCheckInsForXs(List<CheckIn> list, List<EmpDaySum> empDays ) {
		for (CheckIn ci : list) {
			//addOneCheckIn(ci);
			//this.cmapper.deleteCheckinByDayAndEmpId(ci.getDayStr(), ci.getEmpId());
			this.cmapper.addACheckIn(ci);
			//刷新当日考勤
			//this.reRijie(ci.getDayStr(), ci);
			
		}
		//for(EmpDaySum ed : empDays){
			this.oaUtilMapper.addEmpDaySummer(empDays);
//			oaUtilMapper.addem
		//}
	}

	public void addOneCheckIn(CheckIn ci) {

		try {
			// String str = "";
			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			String dayStr = ci.getDayStr();

			Day d = dateMapper.findByChinaDayStr(ci.getDayStr());

			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好。几乎不可能发生。");
			}

			if (Consts.nameOfHoliday.equals(d.getType())) {// 如果是节假日，直接插入考勤数据，已经emp_day_sum

				EmpDaySum eds = new EmpDaySum();
				eds.setDayStr(ci.getDayStr());
				eds.setEmpId(ci.getEmpId());
				empDays.add(eds);

			} else {// 如果不是节假日
				// 获取当日的请假情况

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				// 当天所有的请假
				List<LeaveDayDetail> list = this.oaUtilMapper
						.findLeaveDayDetailByDayAndEmp(ci.getDayStr(), ci
								.getEmpId());

				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null
						&& ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(),
							ci.getCheckoutInt() };
					timeList.add(time1);
				}

				if (list != null) {

					for (LeaveDayDetail de : list) {

						int[] time1 = new int[] { de.getBeginTimeInt(),
								de.getEndTimeInt() };
						timeList.add(time1);

						if (de.getWaichu() == 0) { // 请假
							timeLen += de.getTimeLen();
							dkLen += de.getDkTimeLen();
							sjLen += de.getSjTimeLen();
						} else { // 外出
							wcTimeLen += de.getTimeLen();
						}

					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen
						+ wcTimeLen);

				empDay.setDayStr(ci.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ci.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);

			this.cmapper.deleteCheckinByDayAndEmpId(dayStr, ci.getEmpId());
			// this.cmapper.deleteCheckinByDay(dayStr);
			List<CheckIn> cs = new ArrayList<CheckIn>();
			cs.add(ci);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateCheckIn(BKqSp bkq, String dayStr, CheckIn ci) {

		try {

			this.cmapper.deleteCheckinByDayAndEmpId(dayStr, ci.getEmpId());
			// this.cmapper.deleteCheckinByDay(dayStr);
			List<CheckIn> cs = new ArrayList<CheckIn>();
			cs.add(ci);
			cmapper.addCheckIn(cs);

			kqMapper.updateBKqSp(bkq);

			this.reRijie(dayStr, ci);//补考勤之后重新日结
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// 最新的每日考勤数据汇总，不处理年假扣除。单独处理一个员工
	public void reRijie(String dayStr, int empId) {
		CheckIn ci = this.cmapper.findByDayAndEmp(dayStr, empId);
		if(null != ci){
			reRijie(dayStr,ci);
		}else{
			// TODO 没有打卡记录不能补请
			// 这种情况只有在测试环境下发生，测试环境没有员工打卡记录，正式环境，员工不打卡也会有记录，只不过记录数据为null或0值，咋办呢，先补上吧,构造一个空的
			ci = new CheckIn();
			ci.setDayStr(dayStr);
			ci.setEmpId(empId);
			ci.setCheckinInt(0);
			ci.setCheckoutInt(0);
			reRijie(dayStr,ci);
			
//			throw new RuntimeException("日结当日没有打卡记录！");
		}
	}
	
	// 最新的每日考勤数据汇总，不处理年假扣除。单独处理一个员工
	public void reRijieEmp(String dayStr, String checkin, String checkout, int empId) {
		CheckIn ci = new CheckIn();
		ci.setCheckin(checkin);
		ci.setCheckout(checkout);
		ci.setDayStr(dayStr);
		ci.setEmpId(empId);
		ci.setCheckinInt(OtherUtil.time2Int(checkin));
		ci.setCheckoutInt(OtherUtil.time2Int(checkout));
		
		reRijie(dayStr, ci);
	}

	private void reRijie(String dayStr, CheckIn ci) {
		try {

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			Day d = dateMapper.findByChinaDayStr(dayStr);
			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好！几乎不可能发生！");
			}
			if (Consts.nameOfHoliday.equals(d.getType())) { // 如果是节假日，直接插入考勤数据，已经emp_day_sum
				EmpDaySum eds = new EmpDaySum();
				eds.setDayStr(ci.getDayStr());
				eds.setEmpId(ci.getEmpId());
				empDays.add(eds);
			} else { // 如果不是节假日

				String beginTime = dayStr + " 08时30分";
				String endTime = dayStr + " 17时30分";
				//查询所有与当日有交接的请假
				List<Leave> dayLeaves = this.leaveMapper.findLeaveByDay(
						beginTime, endTime);


				HashMap<Integer, ArrayList<Leave>> map = new HashMap<Integer, ArrayList<Leave>>();
				for (Leave ldd : dayLeaves) {
					ArrayList<Leave> ary = map.get(ldd.getProposer());
					if (null == ary) {
						ary = new ArrayList<Leave>();
						map.put(ldd.getProposer(), ary);
					}
					ary.add(ldd);
				}

				int empId = ci.getEmpId();

				ArrayList<Leave> leaves = map.get(ci.getEmpId());

				List<LeaveDayDetail> list = null;
				// 获取当日的请假（以及外出）情况
				if (leaves != null) {
					list = DayCountUtil.getDayLeaves(leaves, dayStr, empId);
				}

				// 这里不扣除年假
				//addNianjiaRecord(list, dayStr, empId);

				EmpDaySum empDay = DayCountUtil.countEmpDay(empId, dayStr, ci,
						list);

				empDays.add(empDay);

			}
			
			//删除某天，某些员工的日结信息
			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);
			
			this.cmapper.deleteCheckinByDayAndEmpId(dayStr, ci.getEmpId());
			// this.cmapper.deleteCheckinByDay(dayStr);
			List<CheckIn> cs = new ArrayList<CheckIn>();
			cs.add(ci);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public void kaoQingHuiZongByMan(String dayStr, List<CheckIn> cs, boolean reduceLeave){
		try {

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			Day d = dateMapper.findByChinaDayStr(dayStr);
			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好！几乎不可能发生！");
			}
			if (Consts.nameOfHoliday.equals(d.getType())) { // 如果是节假日，直接插入考勤数据，已经emp_day_sum
				for (CheckIn ci : cs) {
					EmpDaySum eds = new EmpDaySum();
					eds.setDayStr(ci.getDayStr());
					eds.setEmpId(ci.getEmpId());
					empDays.add(eds);
				}
			} else { // 如果不是节假日

				String beginTime = dayStr + " 08时30分";
				String endTime = dayStr + " 17时30分";
				List<Leave> dayLeaves = this.leaveMapper.findLeaveByDay(
						beginTime, endTime);
				
				String dayLimit = "2014年10月29日 22时25分";//新老假期的分界点
				


				HashMap<Integer, ArrayList<Leave>> map = new HashMap<Integer, ArrayList<Leave>>();
				for (Leave ldd : dayLeaves) {
					ArrayList<Leave> ary = map.get(ldd.getProposer());
					if (null == ary) {
						ary = new ArrayList<Leave>();
						map.put(ldd.getProposer(), ary);
					}
					ary.add(ldd);
				}

				for (CheckIn ci : cs) {
					int empId = ci.getEmpId();

					ArrayList<Leave> leaves = map.get(ci.getEmpId());
					List<Integer> olds = new ArrayList<Integer>();//之前请假，不需要扣除年假，但是要日结。
					
					List<LeaveDayDetail> list = null;
					// 获取当日的请假（以及外出）情况
					if (leaves != null) {
						for(Leave ldd : leaves){
							String createTime = ldd.getCreateTime();
							if(createTime.compareTo(dayLimit)<0){//老流程请的假，不扣除年假
								olds.add(ldd.getId());
							}
						}
						list = DayCountUtil.getDayLeaves(leaves, dayStr, empId);
					}
					
					List<LeaveDayDetail> nList = new ArrayList<LeaveDayDetail>();
					
					if(list!= null){
						for(LeaveDayDetail ldd : list){
							if(!olds.contains(ldd.getLeaveId())){
								nList.add(ldd);
							}
						}
					}
					
					// 计算以及扣除年假
					if(reduceLeave){
						addNianjiaRecord(nList, empId);
					}

					EmpDaySum empDay = DayCountUtil.countEmpDay(empId, dayStr,
							ci, list);

					empDays.add(empDay);

				}
			}

			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);
			this.cmapper.deleteCheckinByDay(dayStr);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 最新的每日考勤数据汇总 2014年10月27日
	public void kaoQingHuiZong(String dayStr, List<CheckIn> cs) {
		try {

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			Day d = dateMapper.findByChinaDayStr(dayStr);
			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好！几乎不可能发生！");
			}
			if (Consts.nameOfHoliday.equals(d.getType())) { // 如果是节假日，直接插入考勤数据，已经emp_day_sum
				for (CheckIn ci : cs) {
					EmpDaySum eds = new EmpDaySum();
					eds.setDayStr(ci.getDayStr());
					eds.setEmpId(ci.getEmpId());
					empDays.add(eds);
				}
			} else { // 如果不是节假日

				String beginTime = dayStr + " 08时30分";
				String endTime = dayStr + " 17时30分";
				List<Leave> dayLeaves = this.leaveMapper.findLeaveByDay(
						beginTime, endTime);
				
				String dayLimit = "2014年10月29日 22时25分";//新老假期的分界点
				


				HashMap<Integer, ArrayList<Leave>> map = new HashMap<Integer, ArrayList<Leave>>();
				for (Leave ldd : dayLeaves) {
					ArrayList<Leave> ary = map.get(ldd.getProposer());
					if (null == ary) {
						ary = new ArrayList<Leave>();
						map.put(ldd.getProposer(), ary);
					}
					ary.add(ldd);
				}

				for (CheckIn ci : cs) {
					int empId = ci.getEmpId();

					ArrayList<Leave> leaves = map.get(ci.getEmpId());
					List<Integer> olds = new ArrayList<Integer>();//之前请假，不需要扣除年假，但是要日结。
					
					List<LeaveDayDetail> list = null;
					// 获取当日的请假（以及外出）情况
					if (leaves != null) {
						for(Leave ldd : leaves){
							String createTime = ldd.getCreateTime();
							if(createTime.compareTo(dayLimit)<0){//老流程请的假，不扣除年假
								olds.add(ldd.getId());
							}
						}
						list = DayCountUtil.getDayLeaves(leaves, dayStr, empId);
					}
					
					List<LeaveDayDetail> nList = new ArrayList<LeaveDayDetail>();
					
					if(list!= null){
						for(LeaveDayDetail ldd : list){
							if(!olds.contains(ldd.getLeaveId())){
								nList.add(ldd);
							}
						}
					}
					// 计算以及扣除年假
					addNianjiaRecord(nList, empId);

					EmpDaySum empDay = DayCountUtil.countEmpDay(empId, dayStr,
							ci, list);

					empDays.add(empDay);

				}
			}

			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);
			this.cmapper.deleteCheckinByDay(dayStr);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//对请假只日结，不做年假扣除
	public void reRijieForLeave(Leave leave){

		try{
			int empId = leave.getProposer();
			
			//1,计算出这次请假的每日详情
			List<LeaveDayDetail> list = DayCountUtil.getDayLeaves(leave, dateMapper);

			if (list == null || list.isEmpty()) {
				return;
			}
			
			String today = DateUtil.getCurrentTime("yyyy年MM月dd日");
			//只获取今日之前的日期
			for( LeaveDayDetail ldd : list){
				String dayStr = ldd.getDayStr();
				if(dayStr.compareTo(today) < 0){
					//beforeList.add(ldd);
					reRijie(dayStr, empId);
				}
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	
	}
	
	//为某次请假，添加年假扣除记录（只添加当日考勤结算之前的数据）
	public void addNianjiaRecordForLeave(Leave leave){
		try{
			int empId = leave.getProposer();
			
			//1,计算出这次请假的每日详情
			List<LeaveDayDetail> list = DayCountUtil.getDayLeaves(leave, dateMapper);

			if (list == null || list.isEmpty()) {
				return;
			}
			
			List<LeaveDayDetail> beforeList = new ArrayList<LeaveDayDetail>();
			
			String today = DateUtil.getCurrentTime("yyyy年MM月dd日");
			//只获取没有日结的日期
			for( LeaveDayDetail ldd : list){
				String dayStr = ldd.getDayStr();
				if(dayStr.compareTo(today) < 0){
					beforeList.add(ldd);
					reRijie(dayStr, empId);
				}
			}
			
			//int empId = leave.getProposer();
			
			addNianjiaRecord(beforeList, empId);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//根据每日考勤详情，扣除年假
	private void addNianjiaRecord(List<LeaveDayDetail> list, int empId) {
		try {

			if (list == null || list.isEmpty()) {
				return;
			}
			// 获取年假信息
			EmpNianjia nianjia = oaUtilMapper.getEmpNianjiaById(empId);
			if (nianjia == null) { // 用户没有年假的情况，需要处理
				// throw new RuntimeException("请联系管理员初始化，该员工的年假以及病假信息。");
				nianjia = new EmpNianjia();
				nianjia.setBingjia(0);
				nianjia.setNianjia(0);
				nianjia.setId(-1);
			}

			int bjLen = nianjia.getBingjia();
			int njLen = nianjia.getNianjia();

			// 需要抵扣年假的请假类型
			String[] dks = new String[] { "事假", "病假", "年假", "调休" };
			List<String> dktypes = Arrays.asList(dks);

			List<NianjiaRecord> nianjiaRecords = new ArrayList<NianjiaRecord>();

			for (LeaveDayDetail dayDetail : list) {

				String leaveType = dayDetail.getType();
				String dayStr = dayDetail.getDayStr();
				int waichu = dayDetail.getWaichu();
				if (waichu == 1) {// 如果是外出
					continue;
				}
				
				if (dktypes.contains(leaveType)) {

					int timeLen = dayDetail.getTimeLen();
					int sj = timeLen;

					// 如果是病假，先抵扣病假
					if (bjLen > 0 && "病假".equals(leaveType)) {
						NianjiaRecord nr = new NianjiaRecord();
						nr.setDayStr(dayStr);
						nr.setEmpId(empId);
						nr.setLeaveId(dayDetail.getLeaveId());
						nr.setType("病假");
						nr.setBz("请假扣除");
						if (bjLen >= timeLen) {
							bjLen -= timeLen;
							nr.setTimeLeft(bjLen);
							nr.setTimeLen(timeLen);
							sj = 0;
						} else {
							sj -= bjLen;
							nr.setTimeLen(bjLen);
							bjLen = 0;
							nr.setTimeLeft(bjLen);
						}

						nianjiaRecords.add(nr);
					}

					// 其它情况，扣除年假，调休
					if (sj > 0 && dktypes.contains(leaveType)) {
						NianjiaRecord nr = new NianjiaRecord();
						nr.setDayStr(dayStr);
						nr.setEmpId(empId);
						nr.setLeaveId(dayDetail.getLeaveId());
						nr.setType("年假");
						nr.setBz("请假扣除");

						njLen -= sj;
						nr.setTimeLeft(njLen);
						nr.setTimeLen(sj);

						nianjiaRecords.add(nr);
					}
				}
			}

			nianjia.setNianjia(njLen);
			nianjia.setBingjia(bjLen);
			
			// 年假不足
			if(njLen < 0) {
				User user = this.userMapper.findById(empId);
				mailTableUtil.nianjia(user, njLen,false);
				mailTableUtil.nianjia(user, njLen,true);
			}

			this.oaUtilMapper.updateEmpNianjia(nianjia);

			if (nianjiaRecords != null && !nianjiaRecords.isEmpty()) {
				this.oaUtilMapper.addNianjiaRecord(nianjiaRecords);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 原来每日考勤数据汇总
	public void addCheckInNew(String dayStr, List<CheckIn> cs) {

		try {
			// String str = "";
			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date day = sdf1.parse(dayStr);

			Day d = dateMapper.findByDayStr(sdf2.format(day));
			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好！几乎不可能发生！");
			}
			if (Consts.nameOfHoliday.equals(d.getType())) {// 如果是节假日，直接插入考勤数据，已经emp_day_sum
				for (CheckIn ci : cs) {
					EmpDaySum eds = new EmpDaySum();
					eds.setDayStr(ci.getDayStr());
					eds.setEmpId(ci.getEmpId());
					empDays.add(eds);
				}
			} else { // 如果不是节假日
				// 获取当日的请假（以及外出）情况
				List<LeaveDayDetail> dayDetails = this.oaUtilMapper
						.findLeaveDayDetailByDay(dayStr);

				HashMap<Integer, ArrayList<LeaveDayDetail>> map = new HashMap<Integer, ArrayList<LeaveDayDetail>>();
				for (LeaveDayDetail ldd : dayDetails) {
					ArrayList<LeaveDayDetail> ary = map.get(ldd.getEmpId());
					if (null == ary) {
						ary = new ArrayList<LeaveDayDetail>();
						map.put(ldd.getEmpId(), ary);
					}
					ary.add(ldd);
				}

				for (CheckIn ci : cs) {

					EmpDaySum empDay = new EmpDaySum();

					int wcTimeLen = 0;
					int timeLen = 0;
					int dkLen = 0;
					int sjLen = 0;

					// 当天所有的请假
					ArrayList<LeaveDayDetail> list = map.get(ci.getEmpId());

					List<int[]> timeList = new ArrayList<int[]>();

					if (ci != null
							&& ((ci.getCheckinInt() != 0) && (ci
									.getCheckoutInt() != 0))) {

						int[] time1 = new int[] { ci.getCheckinInt(),
								ci.getCheckoutInt() };
						timeList.add(time1);
					}

					if (list != null) {

						for (LeaveDayDetail de : list) {

							int[] time1 = new int[] { de.getBeginTimeInt(),
									de.getEndTimeInt() };
							timeList.add(time1);

							if (de.getWaichu() == 0) { // 请假
								timeLen += de.getTimeLen();
								dkLen += de.getDkTimeLen();
								sjLen += de.getSjTimeLen();
							} else { // 外出
								wcTimeLen += de.getTimeLen();
							}

						}

					}

					// 计算当日的不正常打卡时间
					int unclen = countUncommonTime(ci, timeList, timeLen
							+ wcTimeLen);

					empDay.setDayStr(ci.getDayStr());
					empDay.setDkLen(dkLen);
					empDay.setEmpId(ci.getEmpId());
					empDay.setSjLen(sjLen);
					empDay.setTimeLen(timeLen);
					empDay.setWcLen(wcTimeLen);
					empDay.setUncLen(unclen);

					empDays.add(empDay);

				}
			}

			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);
			this.cmapper.deleteCheckinByDay(dayStr);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 删除某一天的打卡数据
	private void deleteCheckinByDay(String day) {
		this.cmapper.deleteCheckinByDay(day);
	}

	// 编辑打卡数据
	public void updateCheckin(CheckIn checkin) {
		cmapper.updateCheckin(checkin);
	}

	public List<CheckIn> findByDay(String dayStr) {
		return cmapper.findByDay(dayStr);
	}

	public List<CheckIn> findByEmpAndTime(String beginTime, String endTime,
			int empId) {
		return cmapper.findByEmpAndTime(beginTime, endTime, empId);
	}

	public List<CheckIn> findByTimeSum(String beginTime, String endTime,
			String begin, String end) {
		return cmapper.findByTimeSum(beginTime, endTime, begin, end);
	}

	public List<CheckIn> findByTimeSumForD(int dDeptId, String beginTime,
			String endTime, String begin, String end) {
		return cmapper.findByTimeSumForD(dDeptId, beginTime, endTime, begin,
				end);
	}

	public List<CheckIn> findByTimeSumForDeptMgr(int dDeptId, String beginTime,
			String endTime, String begin, String end) {
		return cmapper.findByTimeSumForDeptMgr(dDeptId, beginTime, endTime,
				begin, end);
	}

	private int countUncommonTime(CheckIn checkIn, List<int[]> list, int timeLen) {
		int count = 0;

		int amInt = Consts.amBeginHour * 60 + Consts.amBeginMinute;
		int pmInt = Consts.pmEndHour * 60 + Consts.pmEndMinute;

		int noonBegin = Consts.amEndHour * 60 + Consts.amEndMinute;
		int noonEnd = Consts.pmBeginHour * 60 + Consts.pmBeginMinute;

		if (checkIn == null || (checkIn.getCheckinInt() == 0)
				|| (checkIn.getCheckoutInt() == 0)) {
			return 480 - timeLen;
		}

		for (int i = amInt; i < pmInt; i++) {
			if (i < noonBegin || i >= noonEnd) {
				if (isUncommon(i, list)) {
					count++;
				}
			}
		}

		return count;

	}

	private boolean isUncommon(int i, List<int[]> list) {

		for (int[] ary : list) {
			if (i >= ary[0] && i < ary[1]) {
				return false;
			}
		}
		return true;
	}

	public void addCheckInTemp(String dayStr, List<CheckIn> cs) {

		try {
			// String str = "";
			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date day = sdf1.parse(dayStr);

			Day d = dateMapper.findByDayStr(sdf2.format(day));
			if (d == null) {
				throw new Exception("导入考勤数据日期的工作日历还没准备好！几乎不可能发生！");
			}
			if (Consts.nameOfHoliday.equals(d.getType())) {// 如果是节假日，直接插入考勤数据，已经emp_day_sum
				for (CheckIn ci : cs) {
					EmpDaySum eds = new EmpDaySum();
					eds.setDayStr(ci.getDayStr());
					eds.setEmpId(ci.getEmpId());
					empDays.add(eds);
				}
			} else {// 如果不是节假日

				for (CheckIn ci : cs) {

					EmpDaySum eds = new EmpDaySum();
					eds.setDayStr(ci.getDayStr());
					eds.setEmpId(ci.getEmpId());

					List<int[]> timeList = new ArrayList<int[]>();

					if (ci != null
							&& ((ci.getCheckinInt() != 0) && (ci
									.getCheckoutInt() != 0))) {

						int[] time1 = new int[] { ci.getCheckinInt(),
								ci.getCheckoutInt() };
						timeList.add(time1);

						eds.setUncLen(countUncommonTime(ci, timeList));
					} else {
						eds.setUncLen(480);
					}

					empDays.add(eds);

				}
			}

			this.oaUtilMapper.deleteEmpDaySumByDayAndEmps(dayStr, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);
			this.cmapper.deleteCheckinByDay(dayStr);
			cmapper.addCheckIn(cs);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int countUncommonTime(CheckIn checkIn, List<int[]> list) {
		int count = 0;

		int amInt = Consts.amBeginHour * 60 + Consts.amBeginMinute;
		int pmInt = Consts.pmEndHour * 60 + Consts.pmEndMinute;

		int noonBegin = Consts.amEndHour * 60 + Consts.amEndMinute;
		int noonEnd = Consts.pmBeginHour * 60 + Consts.pmBeginMinute;

		for (int i = amInt; i < pmInt; i++) {
			if (i < noonBegin || i >= noonEnd) {
				if (isUncommon(i, list)) {
					count++;
				}
			}
		}

		return count;

	}
}
