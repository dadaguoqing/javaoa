package com.hj.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.DayLeave;
import com.hj.oa.bean.EmpDaySum;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveDayDetail;
import com.hj.oa.dao.DateMapper;

/**
 * 结算员工每日的考勤
 * 
 * @author L
 * 
 */
public class DayCountUtil {

	/**
	 * 结算某位员工某一天的考勤
	 * 
	 * @param empId
	 *            这个参数其实没有什么作用
	 * @param ci
	 *            当日的打卡记录
	 * @param leaves
	 *            时间上跨越当天的请假（或者外出）
	 */
	public static EmpDaySum countEmpDay(int empId, String dayStr, CheckIn ci,
			List<LeaveDayDetail> list) {

		int timeLen = 0;
		int wcTimeLen = 0;

		// 1, 获取今日所有请假的详情
		// List<LeaveDayDetail> list = getDayLeaves(leaves, dayStr, empId);
		// //null;

		// 2, 计算每日详情
		EmpDaySum empDay = new EmpDaySum();
		empDay.setDayStr(dayStr);
		empDay.setEmpId(empId);

		List<int[]> timeList = new ArrayList<int[]>();

		if (ci != null
				&& ((ci.getCheckinInt() != 0) && (ci.getCheckoutInt() != 0))) {

			int[] time1 = new int[] { ci.getCheckinInt(), ci.getCheckoutInt() };
			timeList.add(time1);
		}

		if (list != null) {

			for (LeaveDayDetail de : list) {

				int[] time1 = new int[] { de.getBeginTimeInt(),
						de.getEndTimeInt() };
				timeList.add(time1);

				if (de.getWaichu() == 0) { // 请假
					timeLen += de.getTimeLen();
				} else { // 外出
					wcTimeLen += de.getTimeLen();
				}

			}

		}

		// 计算当日的不正常打卡时间
		int unclen = countUncommonTime(ci, timeList, timeLen + wcTimeLen);

		empDay.setTimeLen(timeLen);
		empDay.setWcLen(wcTimeLen);
		empDay.setUncLen(unclen);

		return empDay;
	}

	public static List<LeaveDayDetail> getDayLeaves(List<Leave> leaves,
			String dayStr, int empId) {

		List<LeaveDayDetail> dayleaves = new ArrayList<LeaveDayDetail>();

		for (Leave leave : leaves) {
			DayLeave dl = getDayLeave(leave, dayStr, empId);
			// System.out.println(leave.getId()+",name:"+leave.getProposerName()+",dl:"+dl);
			if (null != dl) {
				// dayleaves.add(dayLeave);
				LeaveDayDetail dayDetail = new LeaveDayDetail();
				dayDetail.setLeaveId(leave.getId());
				dayDetail.setBeginTime(dl.getBeginTime());
				dayDetail.setBeginTimeInt(dl.getBeginTimeInt());
				dayDetail.setDayStr(dl.getDayStr());
				dayDetail.setEmpId(empId);
				dayDetail.setEndTime(dl.getEndTime());
				dayDetail.setEndTimeInt(dl.getEndTimeInt());
				dayDetail.setTimeLen(dl.getTimeLen());
				dayDetail.setWaichu(leave.getWaichu());
				dayDetail.setType(leave.getType());

				dayleaves.add(dayDetail);
			}
		}

		return dayleaves;
	}

	public static DayLeave getDayLeave(Leave leave, String dayStr, int empId) {
		try {

			DayLeave dayLeave = new DayLeave();

			dayLeave.setDayStr(dayStr);
			dayLeave.setEmpId(empId);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");

			Date bg = sdf.parse(leave.getBeginTime());
			Date ed = sdf.parse(leave.getEndTime());

			Calendar bc = Calendar.getInstance();
			bc.setTime(bg);

			Calendar ec = Calendar.getInstance();
			ec.setTime(ed);

			Calendar currentDate = Calendar.getInstance();// 当前日期
			currentDate.setTime(sdf2.parse(dayStr));

			int h = bc.get(Calendar.HOUR_OF_DAY);
			int m = bc.get(Calendar.MINUTE);

			// System.out.println(h + ":" + m);

			if (h < Consts.amBeginHour
					|| (h == Consts.amBeginHour && m < Consts.amBeginMinute)) { // 如果是最早时间之前，设置为上班时间
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			} else if (h == 12) {
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
				bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
			} else if (h > Consts.pmEndHour
					|| (h == Consts.pmEndHour && m > Consts.pmEndMinute)) {// 第一天时间为0
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				bc.set(Calendar.MINUTE, Consts.pmEndMinute);
			}

			h = ec.get(Calendar.HOUR_OF_DAY);
			m = ec.get(Calendar.MINUTE);
			// System.out.println(h + ":" + m);
			if (h < Consts.amBeginHour
					|| (h == Consts.amBeginHour && m < Consts.amBeginMinute)) { // 如果是最早时间之前，设置为上班时间
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			} else if (h == 12) {
				ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
				ec.set(Calendar.MINUTE, Consts.amEndMinute);
			} else if (h > Consts.pmEndHour
					|| (h == Consts.pmEndHour && m > Consts.pmEndMinute)) {// 第一天时间为0
				ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				ec.set(Calendar.MINUTE, Consts.pmEndMinute);
			}

			// System.out.println(bc+","+ec+","+currentDate);
			int count = 0;

			while (true) {

				int by = bc.get(Calendar.YEAR);
				int bm = bc.get(Calendar.MONTH) + 1;
				int bd = bc.get(Calendar.DAY_OF_MONTH);
				int bh = bc.get(Calendar.HOUR_OF_DAY);
				int bmi = bc.get(Calendar.MINUTE);

				int ey = ec.get(Calendar.YEAR);
				int em = ec.get(Calendar.MONTH) + 1;
				int ed2 = ec.get(Calendar.DAY_OF_MONTH);
				int eh = ec.get(Calendar.HOUR_OF_DAY);
				int emi = ec.get(Calendar.MINUTE);

				// System.out.println("bd:"+bd+",ed:"+ed2);

				if (by == ey && bm == em && bd == ed2) {// 同一天了

					boolean flag = isCurrentDate(bc, currentDate);

					// System.out.println("flag:"+flag);

					if (flag) {
						if (count == 0) {
							dayLeave.setBeginTime((bh < 10 ? ("0" + bh) : bh)
									+ ":" + (bmi < 10 ? ("0" + bmi) : bmi));
							dayLeave.setEndTime((eh < 10 ? ("0" + eh) : eh)
									+ ":" + (emi < 10 ? ("0" + emi) : emi));
							dayLeave.setBeginTimeInt(bh * 60 + bmi);
							dayLeave.setEndTimeInt(eh * 60 + emi);

						} else {
							bh = 8;
							dayLeave.setBeginTime("08:30");
							dayLeave.setEndTime((eh < 10 ? ("0" + eh) : eh)
									+ ":" + (emi < 10 ? ("0" + emi) : emi));
							dayLeave.setBeginTimeInt(510);
							dayLeave.setEndTimeInt(eh * 60 + emi);
						}

						int tlen = dayLeave.getEndTimeInt()
								- dayLeave.getBeginTimeInt();
						if (eh >= 13 && bh <= 12) {
							tlen -= 60;
						}
						dayLeave.setTimeLen(tlen);

						return dayLeave;
						// dayLeave.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm
						// )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
					}

					break;
				} else {
					// DayLeave dl = new DayLeave();
					boolean flag = isCurrentDate(bc, currentDate);
					bc.add(Calendar.DAY_OF_MONTH, 1);
					if (flag) {

						if (count == 0) {
							dayLeave.setBeginTime((bh < 10 ? ("0" + bh) : bh)
									+ ":" + (bmi < 10 ? ("0" + bmi) : bmi));
							dayLeave.setEndTime("17:30");
							dayLeave.setBeginTimeInt(bh * 60 + bmi);
							dayLeave.setEndTimeInt(1050);

							dayLeave.setDayStr(by + "年"
									+ (bm < 10 ? ("0" + bm) : bm) + "月"
									+ (bd < 10 ? ("0" + bd) : bd) + "日");

							int tlen = dayLeave.getEndTimeInt()
									- dayLeave.getBeginTimeInt();
							if (bh <= 12) {
								tlen -= 60;
							}
							dayLeave.setTimeLen(tlen);

						} else {
							dayLeave.setBeginTime("08:30");
							dayLeave.setEndTime("17:30");
							dayLeave.setBeginTimeInt(510);
							dayLeave.setEndTimeInt(1050);

							// dayLeave.setDayStr(by+"年"+( bm < 10 ? ("0" + bm)
							// : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
							dayLeave.setTimeLen(480);
						}

						return dayLeave;
						// break;
					}

				}

				count++;

			}

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<LeaveDayDetail> getDayLeaves(Leave leave,
			DateMapper dateMapper) {
		try {
			List<LeaveDayDetail> dayleaves = new ArrayList<LeaveDayDetail>();

			int empId = leave.getProposer();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = sdf1.parse(leave.getBeginTime());
			Date d2 = sdf1.parse(leave.getEndTime());

			List<Day> days = dateMapper.findDays(sdf2.format(d1), sdf2
					.format(d2));// 获取节假日信息

			DateUtil du = new DateUtil();
			List<DayLeave> list = du.getDayLeaves(d1, d2, days, empId);// 获取每日请假的详情

			for (DayLeave dl : list) {
				// System.out.println(leave.getId()+",name:"+leave.getProposerName()+",dl:"+dl);
				if (null != dl) {
					// dayleaves.add(dayLeave);
					LeaveDayDetail dayDetail = new LeaveDayDetail();
					dayDetail.setLeaveId(leave.getId());
					dayDetail.setBeginTime(dl.getBeginTime());
					dayDetail.setBeginTimeInt(dl.getBeginTimeInt());
					dayDetail.setDayStr(dl.getDayStr());
					dayDetail.setEmpId(empId);
					dayDetail.setEndTime(dl.getEndTime());
					dayDetail.setEndTimeInt(dl.getEndTimeInt());
					dayDetail.setTimeLen(dl.getTimeLen());
					dayDetail.setWaichu(leave.getWaichu());
					dayDetail.setType(leave.getType());

					dayleaves.add(dayDetail);
				}
			}

			return dayleaves;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isCurrentDate(Calendar bc, Calendar ec) {

		int by = bc.get(Calendar.YEAR);
		int bm = bc.get(Calendar.MONTH) + 1;
		int bd = bc.get(Calendar.DAY_OF_MONTH);

		int ey = ec.get(Calendar.YEAR);
		int em = ec.get(Calendar.MONTH) + 1;
		int ed2 = ec.get(Calendar.DAY_OF_MONTH);

		return by == ey && bm == em && bd == ed2;
	}

	public static int countUncommonTime(CheckIn checkIn, List<int[]> list,
			int timeLen) {
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

	public static boolean isUncommon(int i, List<int[]> list) {

		for (int[] ary : list) {
			if (i >= ary[0] && i < ary[1]) {
				return false;
			}
		}
		return true;
	}
}
