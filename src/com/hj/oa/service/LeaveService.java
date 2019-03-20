package com.hj.oa.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.DayLeave;
import com.hj.oa.bean.EmpDay;
import com.hj.oa.bean.EmpDaySum;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.LeaveDayDetail;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.SpRecord;
import com.hj.oa.bean.User;
import com.hj.oa.dao.CheckInMapper;
import com.hj.oa.dao.DateMapper;
import com.hj.oa.dao.LeaveMapper;
import com.hj.oa.dao.OAUtilMapper;
import com.hj.oa.dao.SpRecordMapper;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.OtherUtil;

@Service
public class LeaveService {

	@Autowired
	private LeaveMapper leaveMapper;
	@Autowired
	DateMapper dateMapper;
	@Autowired
	OAUtilMapper oaUtilMapper;
	@Autowired
	CheckInMapper checkInMapper;
	@Autowired
	private SpRecordMapper spRecordMapper;
	@Autowired
	private CheckInService checkInService;
	@Autowired
	private DateService dateService;

	public List<Leave> findAllLeaves() {
		return this.leaveMapper.findAllLeaves();
	}

	public List<Leave> findByEmpId(int id, int isWaichu) {
		return leaveMapper.findByEmpId(id, isWaichu);
	}

	public List<Leave> findMyDaili(int empId) {
		return this.leaveMapper.findMyDaili(empId);
	}

	public List<Leave> findMineAtSp(int empId) {
		return this.leaveMapper.findMineAtSp(empId);
	}

	private void addSpRecord(int empId, Integer dailiId, int tid) {

		this.spRecordMapper.addSpRecord(empId, dailiId, Consts.type_qj, tid,
				DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
	}

	private void addXjSpRecord(int empId, Integer dailiId, int tid) {

		this.spRecordMapper.addSpRecord(empId, dailiId, Consts.type_xj, tid,
				DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
	}

	public List<Leave> findMySp(int id, int waichu) {
		return leaveMapper.findMySp(id, waichu);
	}

	public List<Leave> findLeaveByUserAndTime(int uid, String time) {
		return leaveMapper.findLeaveByUserAndTime(uid, time);
	}

	// 查询这段时间内，请的假
	public List<Leave> findIsBeenLeave(int empId, String beginTime, String endTime) {
		return leaveMapper.findIsBeenLeave(empId, beginTime, endTime);
	}

	// 根据请假类型查询
	public List<Leave> findLeaveByType(int empId, String beginTime, String endTime, int waichu) {
		return leaveMapper.findLeaveByType(empId, beginTime, endTime, waichu);
	}
	
	public List<Leave> findLeaveByDate(String beginTime, String endTime, int waichu) {
		return leaveMapper.findLeaveByDate(beginTime, endTime, waichu);
	}

	public Leave findById(int id) {
		return leaveMapper.findById(id);
	}
	
	public void cancelLeaveById(Integer id) {
		this.leaveMapper.cancelLeaveById(id);
	}

	public List<Leave> findByEmpIdAndDate(int empId, String beginTime, String endTime) {

		return leaveMapper.findIsBeenLeave(empId, beginTime, endTime);
	}

	private String changeBegintime(String beginTime, SimpleDateFormat sdf) {
		try {
			if (beginTime.endsWith("08时30分")) {
				Date d = sdf.parse(beginTime);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.set(Calendar.MINUTE, 30);
				c.set(Calendar.HOUR_OF_DAY, 17);
				c.add(Calendar.DATE, -1);

				d = c.getTime();
				beginTime = sdf.format(d);
				Day day = this.dateMapper.findByChinaDayStr(beginTime.substring(0, 11));
				while (day.getType().equals(Consts.nameOfHoliday)) {
					c.add(Calendar.DATE, -1);

					d = c.getTime();
					beginTime = sdf.format(d);
					day = this.dateMapper.findByChinaDayStr(beginTime.substring(0, 11));
				}
			} else if (beginTime.endsWith("13时00分")) {
				Date d = sdf.parse(beginTime);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.set(Calendar.HOUR_OF_DAY, 12);
				d = c.getTime();

				beginTime = sdf.format(d);
			}

			return beginTime;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String changeEndtime(String endTime, SimpleDateFormat sdf) {
		try {
			if (endTime.endsWith("17时30分")) {
				Date d = sdf.parse(endTime);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.set(Calendar.MINUTE, 30);
				c.set(Calendar.HOUR_OF_DAY, 8);
				c.add(Calendar.DATE, 1);

				d = c.getTime();
				endTime = sdf.format(d);
				Day day = this.dateMapper.findByChinaDayStr(endTime.substring(0, 11));
				while (day.getType().equals(Consts.nameOfHoliday)) {
					c.add(Calendar.DATE, -1);

					d = c.getTime();
					endTime = sdf.format(d);
					day = this.dateMapper.findByChinaDayStr(endTime.substring(0, 11));
				}
			} else if (endTime.endsWith("12时00分")) {
				Date d = sdf.parse(endTime);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.set(Calendar.HOUR_OF_DAY, 13);
				d = c.getTime();

				endTime = sdf.format(d);
			}
			return endTime;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 查询用户在这两个时间，是否有请假（所有没有被审批结束的，还有审批通过的）用于判断连续请假
	public List<Leave> findByEmpAndTime(int empId, String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

		beginTime = this.changeBegintime(beginTime, sdf);
		endTime = this.changeEndtime(endTime, sdf);

		List<Leave> list = new ArrayList<Leave>();

		List<Leave> data = leaveMapper.findByEmpAndTime(empId, beginTime, endTime);
		List<Integer> leaveIds = new ArrayList<Integer>();
		if (!data.isEmpty()) {
			for (Leave l : data) {
				if (leaveIds.contains(l.getId())) {
					continue;
				}
				list.add(l);
				leaveIds.add(l.getId());
				List<Leave> list2 = findByEmpAndTime(empId, l, leaveIds, sdf);
				list.addAll(list2);
			}
		}

		return list;
	}

	private List<Leave> findByEmpAndTime(int empId, Leave leave, List<Integer> leaveIds, SimpleDateFormat sdf) {
		List<Leave> list = new ArrayList<Leave>();

		String beginTime = leave.getBeginTime();
		String endTime = leave.getEndTime();

		beginTime = this.changeBegintime(beginTime, sdf);
		endTime = this.changeEndtime(endTime, sdf);

		List<Leave> data = leaveMapper.findByEmpAndTime(empId, beginTime, endTime);
		if (!data.isEmpty()) {

			List<Leave> list1 = new ArrayList<Leave>();
			for (Leave l : data) {
				if (!leaveIds.contains(l.getId())) {
					list1.add(l);
					leaveIds.add(l.getId());
					List<Leave> list2 = findByEmpAndTime(empId, l, leaveIds, sdf);
					list1.addAll(list2);
				}
			}
			list.addAll(list1);
		}
		// return leaveMapper.findByEmpAndTime(empId, beginTime, endTime);

		return list;
	}

	public List<Leave> findBeenLeaveByTimeAndEmp(int empId, String beginTime, String endTime) {

		return leaveMapper.findBeenLeaveByTimeAndEmp(empId, beginTime, endTime);
	}

	public void addLeave(Leave leave) {
		leaveMapper.addLeave(leave);
	}

	public void addLeaveCancel(LeaveCancel leaveCancel) {
		leaveMapper.addLeaveCancel(leaveCancel);
	}

	public List<LeaveCancel> findMyLeaveCancelSpTypeAll(int id) {
		return leaveMapper.findMyLeaveCancelSpTypeAll(id);
	}

	public List<LeaveCancel> findMyLeaveCancelSpByType(int id, int type) {
		return leaveMapper.findMyLeaveCancelSpByType(id, type);
	}

	public LeaveCancel findLeaveCancelById(int id) {
		return leaveMapper.findLeaveCancelById(id);
	}

	public void updateLeaveCancel(LeaveCancel leaveCancel, Leave leave, int currentSpId, Integer dailiId) {
		if (null != leave) {
			updateLeaveForCancel(leave);
		}
		addXjSpRecord(currentSpId, dailiId, leaveCancel.getId());
		leaveMapper.updateLeaveCancel(leaveCancel);
	}

	public List<SpRecord> findMySpRecord(int empId) {
		return this.spRecordMapper.findSpRecordByEmpAndType(empId, Consts.type_qj);
	}

	public SpRecord findSpRecord(int empId, int leaveId) {
		return this.spRecordMapper.findSpRecordByEmpAndSpId(empId, leaveId);
	}

	/**
	 * 外出审批记录
	 * 
	 * @param empId
	 * @return
	 */
	public List<SpRecord> findMySpRecordForWc(int empId) {
		return this.spRecordMapper.findSpRecordByEmpAndType(empId, Consts.type_wc);
	}

	// 更新请假审批信息
	public void updateLeave(Leave leave, int currentSpId, Integer dailiId) {
		addSpRecord(currentSpId, dailiId, leave.getId());
		leaveMapper.updateLeave(leave);
	}

	// 跟新，以及扣除之前的年假
	public void updateLeaveAndCountNianjia(Leave leave, int currentSpId, Integer dailiId) {
		addSpRecord(currentSpId, dailiId, leave.getId());
		leaveMapper.updateLeave(leave);
		// 扣除今天之前的年假
		checkInService.addNianjiaRecordForLeave(leave);
	}

	// 更新请假信息
	public void updateLeaveForCancel(Leave leave) {
		leaveMapper.updateLeaveForCancel(leave);
	}

	// 更新外出审批信息
	public void updateLeaveForWc(Leave leave, int currentSpId, Integer dailiId) {
		this.spRecordMapper.addSpRecord(currentSpId, dailiId, Consts.type_wc, leave.getId(),
				DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
		leaveMapper.updateLeave(leave);
	}

	public void importLeaves(List<File> list, Map<String, User> uMap) {
		try {
			for (File exl : list) {
				List<Leave> leaves = ExcelUtil.getQjFromFile(new FileInputStream(exl), 0, dateService, uMap);
				updateForExcelLeaves(leaves);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void importWaichu(List<File> list, Map<String, User> uMap) {
		try {
			for (File exl : list) {
				List<Leave> leaves = ExcelUtil.getQjFromFile(new FileInputStream(exl), 1, dateService, uMap);
				updateForExcelWaichu(leaves);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateForExcelLeaves(List<Leave> list) {
		for (Leave leave : list) {
			// this.updateForExcelLeave(leave);

			this.addLeave(leave);
			// 刷新与考勤相关的日结
			this.checkInService.reRijieForLeave(leave);
		}
	}

	public void updateForExcelWaichu(List<Leave> list) {
		for (Leave leave : list) {
			// this.updateForExcelLeave(leave);

			this.addLeave(leave);
			// 刷新与考勤相关的日结
			this.checkInService.reRijieForLeave(leave);
		}
	}

	// 导入请假信息
	private void updateForExcelLeave(Leave leave) {
		try {
			// String leaveType = leave.getType();
			// int leaveId = leave.getId();
			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			String beginTime = sdf3.format(d1);
			String endTime = sdf3.format(d2);

			// 1、添加每日请假详情，以及你年假扣除记录
			List<LeaveDayDetail> changes = this.addDayDetailAndNianjia(leave, d1, d2);

			// 2、计算不正常打卡时间，记录到t_emp_day里面

			// 查询所有的这段时间内的数据
			List<LeaveDayDetail> dayDetails = this.oaUtilMapper.findLeaveDayDetailByTime(beginTime, endTime, empId);
			// 查询这段时间的打卡记录
			List<CheckIn> checkInList = checkInMapper.findByEmpAndTime(beginTime, endTime, empId);

			Map<String, CheckIn> checkInMap = new HashMap<String, CheckIn>();
			for (CheckIn ci : checkInList) {
				checkInMap.put(ci.getDayStr(), ci);
			}

			Map<String, ArrayList<LeaveDayDetail>> map = new HashMap<String, ArrayList<LeaveDayDetail>>();
			for (LeaveDayDetail ldd : dayDetails) {
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				if (null == list) {
					list = new ArrayList<LeaveDayDetail>();
					map.put(ldd.getDayStr(), list);
				}
				list.add(ldd);
			}

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			for (LeaveDayDetail ldd : changes) {
				Date date = sdf3.parse(ldd.getDayStr());
				Date today = new Date();

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				CheckIn ci = checkInMap.get(ldd.getDayStr());

				// 当天所有的请假
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null && ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
					timeList.add(time1);
				}

				for (LeaveDayDetail de : list) {

					int[] time1 = new int[] { de.getBeginTimeInt(), de.getEndTimeInt() };
					timeList.add(time1);

					if (de.getWaichu() == 0) { // 请假
						timeLen += de.getTimeLen();
						dkLen += de.getDkTimeLen();
						sjLen += de.getSjTimeLen();
					} else { // 外出
						wcTimeLen += de.getTimeLen();
					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

				empDay.setDayStr(ldd.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ldd.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			if (!empDays.isEmpty()) {
				this.oaUtilMapper.deleteEmpDaySummer(empId, empDays);
				this.oaUtilMapper.addEmpDaySummer(empDays);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 审核通过，要做一系列事情。这个原来的，汇总，已经年假扣除都在审批之后立刻产生。
	public void updateSth(Leave leave, int currentSpId, Integer dailiId) {
		try {
			String leaveType = leave.getType();
			int leaveId = leave.getId();
			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			String beginTime = sdf3.format(d1);
			String endTime = sdf3.format(d2);

			// 1、添加每日请假详情，以及你年假扣除记录
			List<LeaveDayDetail> changes = this.addDayDetailAndNianjia(leave, d1, d2);

			// 2、计算不正常打卡时间，记录到t_emp_day里面

			// 查询所有的这段时间内的数据
			List<LeaveDayDetail> dayDetails = this.oaUtilMapper.findLeaveDayDetailByTime(beginTime, endTime, empId);
			// 查询这段时间的打卡记录
			List<CheckIn> checkInList = checkInMapper.findByEmpAndTime(beginTime, endTime, empId);

			Map<String, CheckIn> checkInMap = new HashMap<String, CheckIn>();
			for (CheckIn ci : checkInList) {
				checkInMap.put(ci.getDayStr(), ci);
			}

			Map<String, ArrayList<LeaveDayDetail>> map = new HashMap<String, ArrayList<LeaveDayDetail>>();
			for (LeaveDayDetail ldd : dayDetails) {
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				if (null == list) {
					list = new ArrayList<LeaveDayDetail>();
					map.put(ldd.getDayStr(), list);
				}
				list.add(ldd);
			}

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			for (LeaveDayDetail ldd : changes) {
				Date date = sdf3.parse(ldd.getDayStr());
				Date today = new Date();

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				CheckIn ci = checkInMap.get(ldd.getDayStr());

				// 当天所有的请假
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null && ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
					timeList.add(time1);
				}

				for (LeaveDayDetail de : list) {

					int[] time1 = new int[] { de.getBeginTimeInt(), de.getEndTimeInt() };
					timeList.add(time1);

					if (de.getWaichu() == 0) { // 请假
						timeLen += de.getTimeLen();
						dkLen += de.getDkTimeLen();
						sjLen += de.getSjTimeLen();
					} else { // 外出
						wcTimeLen += de.getTimeLen();
					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

				empDay.setDayStr(ldd.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ldd.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			if (!empDays.isEmpty()) {
				this.oaUtilMapper.deleteEmpDaySummer(empId, empDays);
				this.oaUtilMapper.addEmpDaySummer(empDays);
			}

			// 最后一步，修改Leave
			this.updateLeave(leave, currentSpId, dailiId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// excel导入的外出
	public void updateForExcelWcs(List<Leave> list) {
		for (Leave leave : list) {
			this.updateForExcelWc(leave);
			this.addLeave(leave);
		}
	}

	// excel导入的外出
	private void updateForExcelWc(Leave leave) {
		try {
			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			String beginTime = sdf3.format(d1);
			String endTime = sdf3.format(d2);

			// 1、添加每日请假详情，以及你年假扣除记录
			List<LeaveDayDetail> changes = this.addDayDetail(leave, d1, d2);

			// 2、计算不正常打卡时间，记录到t_emp_day里面

			// 查询所有的这段时间内的数据
			List<LeaveDayDetail> dayDetails = this.oaUtilMapper.findLeaveDayDetailByTime(beginTime, endTime, empId);
			// 查询这段时间的打卡记录
			List<CheckIn> checkInList = checkInMapper.findByEmpAndTime(beginTime, endTime, empId);

			Map<String, CheckIn> checkInMap = new HashMap<String, CheckIn>();
			for (CheckIn ci : checkInList) {
				checkInMap.put(ci.getDayStr(), ci);
			}

			Map<String, ArrayList<LeaveDayDetail>> map = new HashMap<String, ArrayList<LeaveDayDetail>>();
			for (LeaveDayDetail ldd : dayDetails) {
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				if (null == list) {
					list = new ArrayList<LeaveDayDetail>();
					map.put(ldd.getDayStr(), list);
				}
				list.add(ldd);
			}

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			for (LeaveDayDetail ldd : changes) {
				Date date = sdf3.parse(ldd.getDayStr());
				Date today = new Date();

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				CheckIn ci = checkInMap.get(ldd.getDayStr());

				// 当天所有的请假
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null && ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
					timeList.add(time1);
				}

				for (LeaveDayDetail de : list) {

					int[] time1 = new int[] { de.getBeginTimeInt(), de.getEndTimeInt() };
					timeList.add(time1);

					if (de.getWaichu() == 0) { // 请假
						timeLen += de.getTimeLen();
						dkLen += de.getDkTimeLen();
						sjLen += de.getSjTimeLen();
					} else { // 外出
						wcTimeLen += de.getTimeLen();
					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

				empDay.setDayStr(ldd.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ldd.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			this.oaUtilMapper.deleteEmpDaySummer(empId, empDays);
			this.oaUtilMapper.addEmpDaySummer(empDays);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 出差申请通过，需要的后续处理操作
	 * @param leave
	 *            出差信息
	 * @param currentSpId
	 *            当前审批人ID
	 * @param dailiId
	 *            代理人ID
	 * @author mlsong
	 * @date 2017年8月7日 上午10:48:30
	 */
	public void updateSthForWc(Leave leave, int currentSpId, Integer dailiId) {
		try {

			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			String beginTime = sdf3.format(d1);
			String endTime = sdf3.format(d2);

			// 1、添加每日请假详情，以及你年假扣除记录
			List<LeaveDayDetail> changes = this.addDayDetail(leave, d1, d2);

			// 2、计算不正常打卡时间，记录到t_emp_day里面

			// 查询所有的这段时间内的数据
			List<LeaveDayDetail> dayDetails = this.oaUtilMapper.findLeaveDayDetailByTime(beginTime, endTime, empId);
			// 查询这段时间的打卡记录
			List<CheckIn> checkInList = checkInMapper.findByEmpAndTime(beginTime, endTime, empId);

			Map<String, CheckIn> checkInMap = new HashMap<String, CheckIn>();
			for (CheckIn ci : checkInList) {
				checkInMap.put(ci.getDayStr(), ci);
			}

			Map<String, ArrayList<LeaveDayDetail>> map = new HashMap<String, ArrayList<LeaveDayDetail>>();
			for (LeaveDayDetail ldd : dayDetails) {
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				if (null == list) {
					list = new ArrayList<LeaveDayDetail>();
					map.put(ldd.getDayStr(), list);
				}
				list.add(ldd);
			}

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			for (LeaveDayDetail ldd : changes) {
				Date date = sdf3.parse(ldd.getDayStr());
				Date today = new Date();

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				CheckIn ci = checkInMap.get(ldd.getDayStr());

				// 当天所有的请假
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null && ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
					timeList.add(time1);
				}

				for (LeaveDayDetail de : list) {

					int[] time1 = new int[] { de.getBeginTimeInt(), de.getEndTimeInt() };
					timeList.add(time1);

					if (de.getWaichu() == 0) { // 请假
						timeLen += de.getTimeLen();
						dkLen += de.getDkTimeLen();
						sjLen += de.getSjTimeLen();
					} else { // 外出
						wcTimeLen += de.getTimeLen();
					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

				empDay.setDayStr(ldd.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ldd.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			String today = DateUtil.getCurrentTime("yyyy年MM月dd日");
			if(!empDays.isEmpty()) {
				this.oaUtilMapper.deleteEmpDaySummer(empId, empDays);
			}
			if (empDays != null && !empDays.isEmpty()) {
				for (EmpDaySum item : empDays) {
					String begin = item.getDayStr();
					if (begin.compareTo(today)<0) {
						this.oaUtilMapper.addEmpDaySum(item);
					}
				}
			}
			// 最后一步，修改Leave
			this.updateLeaveForWc(leave, currentSpId, dailiId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @Description: 注销出差申请通过，需要的后续处理操作
	 * @param leave
	 *            出差信息
	 * @param currentSpId
	 *            当前审批人ID
	 * @param dailiId
	 *            代理人ID
	 * @author mlsong
	 * @date 2017年8月7日 上午10:48:30
	 */
	public void updateBusinessCancel(Leave leave, int currentSpId, Integer dailiId) {
		try {

			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			String beginTime = sdf3.format(d1);
			String endTime = sdf3.format(d2);

			// 1、添加每日请假详情，以及你年假扣除记录
			List<LeaveDayDetail> changes = new ArrayList<LeaveDayDetail>();
			if (d1.getTime() != d2.getTime()) {
				changes = this.addDayDetail(leave, d1, d2);
			} else {
				return;
			}

			// 2、计算不正常打卡时间，记录到t_emp_day里面

			// 查询所有的这段时间内的数据
			List<LeaveDayDetail> dayDetails = this.oaUtilMapper.findLeaveDayDetailByTime(beginTime, endTime, empId);
			// 查询这段时间的打卡记录
			List<CheckIn> checkInList = checkInMapper.findByEmpAndTime(beginTime, endTime, empId);

			Map<String, CheckIn> checkInMap = new HashMap<String, CheckIn>();
			for (CheckIn ci : checkInList) {
				checkInMap.put(ci.getDayStr(), ci);
			}

			Map<String, ArrayList<LeaveDayDetail>> map = new HashMap<String, ArrayList<LeaveDayDetail>>();
			for (LeaveDayDetail ldd : dayDetails) {
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				if (null == list) {
					list = new ArrayList<LeaveDayDetail>();
					map.put(ldd.getDayStr(), list);
				}
				list.add(ldd);
			}

			List<EmpDaySum> empDays = new ArrayList<EmpDaySum>();

			for (LeaveDayDetail ldd : changes) {
				Date date = sdf3.parse(ldd.getDayStr());
				Date today = new Date();

				EmpDaySum empDay = new EmpDaySum();

				int wcTimeLen = 0;
				int timeLen = 0;
				int dkLen = 0;
				int sjLen = 0;

				CheckIn ci = checkInMap.get(ldd.getDayStr());

				// 当天所有的请假
				ArrayList<LeaveDayDetail> list = map.get(ldd.getDayStr());
				List<int[]> timeList = new ArrayList<int[]>();

				if (ci != null && ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

					int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
					timeList.add(time1);
				}

				for (LeaveDayDetail de : list) {

					int[] time1 = new int[] { de.getBeginTimeInt(), de.getEndTimeInt() };
					timeList.add(time1);

					if (de.getWaichu() == 0) { // 请假
						timeLen += de.getTimeLen();
						dkLen += de.getDkTimeLen();
						sjLen += de.getSjTimeLen();
					} else { // 外出
						wcTimeLen += de.getTimeLen();
					}

				}

				// 计算当日的不正常打卡时间
				int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

				empDay.setDayStr(ldd.getDayStr());
				empDay.setDkLen(dkLen);
				empDay.setEmpId(ldd.getEmpId());
				empDay.setSjLen(sjLen);
				empDay.setTimeLen(timeLen);
				empDay.setWcLen(wcTimeLen);
				empDay.setUncLen(unclen);

				empDays.add(empDay);

			}

			this.oaUtilMapper.deleteEmpDaySummer(empId, empDays);
			if (!empDays.isEmpty()) {
				this.oaUtilMapper.addEmpDaySummer(empDays);
			}

			// 最后一步，修改Leave
			this.updateLeaveForWc(leave, currentSpId, dailiId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int countUncommonTime(CheckIn checkIn, List<int[]> list, int timeLen) {
		int count = 0;

		int amInt = Consts.amBeginHour * 60 + Consts.amBeginMinute;
		int pmInt = Consts.pmEndHour * 60 + Consts.pmEndMinute;

		int noonBegin = Consts.amEndHour * 60 + Consts.amEndMinute;
		int noonEnd = Consts.pmBeginHour * 60 + Consts.pmBeginMinute;

		if (checkIn == null || (checkIn.getCheckinInt() == 0) || (checkIn.getCheckoutInt() == 0)) {
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

	private List<LeaveDayDetail> addDayDetailAndNianjia(Leave leave, Date d1, Date d2) throws Exception {
		String leaveType = leave.getType();
		int leaveId = leave.getId();
		int empId = leave.getProposer();

		// 需要抵扣年假的请假类型
		String[] dks = new String[] { "事假", "病假", "年假", "调休" };
		List<String> dktypes = Arrays.asList(dks);
		// 获取年假信息
		EmpNianjia nianjia = oaUtilMapper.getEmpNianjiaById(empId);
		if (nianjia == null) {
			// throw new RuntimeException("请联系管理员初始化，该员工的年假以及病假信息。");
			nianjia = new EmpNianjia();
			nianjia.setBingjia(0);
			nianjia.setNianjia(0);
			nianjia.setId(-1);
		}
		int bjLen = nianjia.getBingjia();
		int njLen = nianjia.getNianjia();

		// 1、扣除年假，（事假，病假的调整，等）
		// 2、添加每日请假记录到t_qj_record表里面

		// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		// Date d1 = sdf1.parse(leave.getBeginTime());
		// Date d2 = sdf1.parse(leave.getEndTime());

		List<Day> days = dateMapper.findDays(sdf2.format(d1), sdf2.format(d2));// 获取节假日信息

		DateUtil du = new DateUtil();
		List<DayLeave> list = du.getDayLeaves(d1, d2, days, empId);// 获取每日请假的详情
		// 需要的信息，日期，请假开始、结束时间（整数时间）。请假类型，抵扣的请假，实际请假。

		List<LeaveDayDetail> dayDetails = new ArrayList<LeaveDayDetail>(list.size());
		// 年假抵扣记录List<NianjiaRecord>
		// 病假抵扣记录
		List<NianjiaRecord> nianjiaRecords = new ArrayList<NianjiaRecord>();
		for (DayLeave dl : list) {
			LeaveDayDetail dayDetail = new LeaveDayDetail();
			dayDetail.setLeaveId(leave.getId());
			dayDetail.setBeginTime(dl.getBeginTime());
			dayDetail.setBeginTimeInt(dl.getBeginTimeInt());
			dayDetail.setDayStr(dl.getDayStr());
			dayDetail.setEmpId(empId);
			dayDetail.setEndTime(dl.getEndTime());
			dayDetail.setEndTimeInt(dl.getEndTimeInt());
			dayDetail.setTimeLen(dl.getTimeLen());
			dayDetail.setWaichu(0);
			dayDetail.setType(leave.getType());

			dayDetail.setSjTimeLen(0);
			dayDetail.setDkTimeLen(0);

			if (dktypes.contains(leaveType)) {
				int dk = 0;
				int timeLen = dl.getTimeLen();
				int sj = timeLen;

				// 如果是病假，先抵扣病假
				if (bjLen > 0 && "病假".equals(leaveType)) {
					NianjiaRecord nr = new NianjiaRecord();
					nr.setDayStr(dl.getDayStr());
					nr.setEmpId(empId);
					nr.setLeaveId(leaveId);
					nr.setType("病假");
					nr.setBz("请假扣除");
					if (bjLen >= sj) {
						bjLen -= sj;
						dk += sj;
						nr.setTimeLeft(bjLen);
						nr.setTimeLen(sj);
						sj = 0;
					} else {
						dk += bjLen;
						sj -= bjLen;
						nr.setTimeLen(bjLen);
						bjLen = 0;
						nr.setTimeLeft(bjLen);
					}

					nianjiaRecords.add(nr);
				}

				// 其它情况，扣除年假，调休
				if (sj > 0 && njLen > 0 && dktypes.contains(leaveType)) {
					NianjiaRecord nr = new NianjiaRecord();
					nr.setDayStr(dl.getDayStr());
					nr.setEmpId(empId);
					nr.setLeaveId(leaveId);
					nr.setType("年假");
					nr.setBz("请假扣除");

					if (njLen >= sj) {
						njLen -= sj;
						dk += sj;
						nr.setTimeLeft(njLen);
						nr.setTimeLen(sj);
						sj = 0;
					} else {
						dk += njLen;
						sj -= njLen;
						nr.setTimeLen(njLen);
						njLen = 0;
						nr.setTimeLeft(njLen);
					}

					nianjiaRecords.add(nr);
				}

				dayDetail.setSjTimeLen(sj);
				dayDetail.setDkTimeLen(dk);
			}

			dayDetails.add(dayDetail);

		}

		nianjia.setNianjia(njLen);
		nianjia.setBingjia(bjLen);

		if (nianjia.getId() != -1) {
			this.oaUtilMapper.updateEmpNianjia(nianjia);
		}

		if (!dayDetails.isEmpty()) {
			this.oaUtilMapper.addLeaveDayDetail(dayDetails);
		}

		if (nianjiaRecords != null && !nianjiaRecords.isEmpty()) {
			this.oaUtilMapper.addNianjiaRecord(nianjiaRecords);
		}

		return dayDetails;
	}

	// 外出专用
	private List<LeaveDayDetail> addDayDetail(Leave leave, Date d1, Date d2) throws Exception {

		int empId = leave.getProposer();

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		List<Day> days = dateMapper.findDays(sdf2.format(d1), sdf2.format(d2));// 获取节假日信息

		DateUtil du = new DateUtil();
		// TODO
		List<DayLeave> list = du.getDayLeaves2(d1, d2, days, empId);// 获取每日请假的详情

		List<LeaveDayDetail> dayDetails = new ArrayList<LeaveDayDetail>(list.size());
		if(!list.isEmpty()) {
			for (DayLeave dl : list) {
				LeaveDayDetail dayDetail = new LeaveDayDetail();
				dayDetail.setLeaveId(leave.getId());
				dayDetail.setBeginTime(dl.getBeginTime());
				dayDetail.setBeginTimeInt(dl.getBeginTimeInt());
				dayDetail.setDayStr(dl.getDayStr());
				dayDetail.setEmpId(empId);
				dayDetail.setEndTime(dl.getEndTime());
				dayDetail.setEndTimeInt(dl.getEndTimeInt());
				dayDetail.setTimeLen(dl.getTimeLen());
				dayDetail.setWaichu(1);
				dayDetail.setType(leave.getType());
				
				dayDetail.setSjTimeLen(0);
				dayDetail.setDkTimeLen(0);
				dayDetails.add(dayDetail);
			}
			this.oaUtilMapper.addLeaveDayDetail(dayDetails);
		}

		return dayDetails;
	}

	/**
	 * 计算销假总页数 2017年6月12日
	 * 
	 * @return
	 */
	public int countLeaveCance() {
		int count = leaveMapper.countLeaveCancel();
		if (count % 20 == 0) {
			return count / 20;
		} else {
			return count / 20 + 1;
		}
	}

	public List<LeaveCancel> findAllLeaveCance(int curpage) {
		return leaveMapper.findAllLeaveCancel((curpage - 1) * 20);
	}

	/**
	 * @Description: 根据注销类型查找所有销假/注销出差记录
	 * @param curpage
	 *            页码
	 * @param type
	 *            销假/注销出差
	 * @return List<LeaveCancel>
	 * @author mlsong
	 * @date 2017年8月9日 上午11:59:23
	 */
	public List<LeaveCancel> findLeaveCancelByType(int curpage, int type) {
		return leaveMapper.findLeaveCancelByType((curpage - 1) * 20, type);
	}

	/**
	 * @Description: 根据类型查询所有注销记录数量
	 * @param type
	 *            注销类型

	 * @author mlsong
	 * @date 2017年8月9日 下午12:17:44
	 */
	public int countLeaveCancelByType(int type) {
		int count = leaveMapper.countLeaveCancelByType(type);
		if (count % 20 == 0) {
			return count / 20;
		} else {
			return count / 20 + 1;
		}
	}

	public List<Leave> findmydlChuChai(int id) {
		return this.leaveMapper.findMyDailiChuChai(id);
	}
	
}
