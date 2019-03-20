package com.hj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.hj.ke.bean.CheckInFromKs;
import com.hj.ke.bean.JsonResult;
import com.hj.oa.Consts;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.CheckInRemote;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.EmpDayVo;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.OutAccessCode;
import com.hj.oa.bean.SealApplyDetail;
import com.hj.oa.bean.User;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.DateService;
import com.hj.oa.service.KqService;
import com.hj.oa.service.OAUtilService;
import com.hj.oa.service.SealService;
import com.hj.oa.service.UserService;

public class TaskUtil {
	Logger log = Logger.getLogger(TaskUtil.class);

	private ObjectMapper objMapper = new ObjectMapper();

	private UserService userSer;
	private CheckInService cSer;
	private MailUtil mailUtil;
	private DateService dateService;
	private KqService kqService;
	private OAUtilService oaUtilService;
	@Autowired
	private SealService sealService;

	public static long A3Status = System.currentTimeMillis();
	public static long maxDuration = 1000 * 60 * 3;// 3分钟，毫秒计算
	public static int mailTimes = 0; // 邮件通知的次数

	// 检查远程服务器是否发送数据过来了。
	public void checkA3Status() {
		if (Consts.devMode == 1) {
			return;
		}
		long tn = System.currentTimeMillis();
		long du = tn - A3Status;
		if (du > maxDuration) {// 超过最大允许的范围
			// 通知邮件，发现异常。
			if (mailTimes < 3) {
				getMailUtil().sendToMe("远程考勤服务异常", "远程服务器已经超过3分钟没有给服务器发送数据了");
				mailTimes++;
			}
		} else { // 一切正常，恢复
			mailTimes = 0;
		}
	}

	// 定期删除访问码
	public void checkOutAccessCode() {
		try {
			List<OutAccessCode> outs = userSer.findAllOutAccessCode();
			for (OutAccessCode oac : outs) {
				if (oac.getType() == 1) {
					continue;
				}
				String timeNow = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
				if (timeNow.compareTo(oac.getEndTime()) > 0) {
					// model.addAttribute("msg","您的访问码过期，请重新申请。");
					userSer.deleteOutAccessCode(oac.getId());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ninajiaClear() { // 年假结算，每年4月1号凌晨 1点左右执行
		try {

			List<EmpNianjia> list1 = this.oaUtilService.findAllNianjia();
			this.oaUtilService.addAnEmpNianjiaBak(list1);

			// 获取所有员工的年假，病假。
			List<EmpNianjia> list = this.oaUtilService.findAllNianjia2();

			List<NianjiaRecord> nrs = new ArrayList<NianjiaRecord>();
			List<EmpNianjia> njs = new ArrayList<EmpNianjia>();

			for (EmpNianjia nj : list) {
				int nianjia = nj.getNianjia();
				int bingjia = nj.getBingjia();
				int empId = nj.getEmpId();
				// String empName = nj.getEmpName();

				int sevenDay = 7 * 8 * 60;
				int tenDay = 10 * 8 * 60;
				// int twentyDay = 20 * 8 * 60;
				int twentyFiveDay = 25 * 8 * 60;

				int addDay = getEmpYearsOfEntry(nj.getEmpId()) * 8 * 60;

				// 病假相关
				int nBj = sevenDay;
				int bNum = -(nBj - bingjia);

				String bz = "年度结算";
				String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);// "";
				NianjiaRecord br = new NianjiaRecord();
				br.setBz(bz);
				br.setType("病假");
				br.setDayStr(dayStr);
				br.setEmpId(empId);
				br.setLeaveId(0);
				br.setTimeLen(bNum);
				br.setTimeLeft(nBj);

				nrs.add(br);
				nj.setBingjia(nBj);

				br.setTimeLenStr(OtherUtil.miniute2StringWithF(br.getTimeLen()));
				br.setTimeLeftStr(OtherUtil.miniute2StringWithF(br.getTimeLeft()));

				// 年假相关

				br = new NianjiaRecord();
				br.setBz(bz);
				br.setType("年假");
				br.setDayStr(dayStr);
				br.setEmpId(empId);
				br.setLeaveId(0);

				int nNj = 0;
				int nNum = 0;
				// 新版的年假制度
				if (nianjia < 0) { // 年假不足
					nNj = tenDay + addDay;
					nNum = -(nNj - nianjia);
				} else { // 年假未用完
					// 年假结算之后的剩余总年假
					int sumDays = nianjia + tenDay + addDay;

					if (sumDays <= twentyFiveDay) { // 总年假不超过25天
						nNj = sumDays;
						nNum = -(tenDay + addDay);
					} else { // 总年假超过25天，按25天处理
						nNj = twentyFiveDay;
						nNum = -(twentyFiveDay - nianjia);
					}
				}

				br.setTimeLen(nNum);
				br.setTimeLeft(nNj);

				nrs.add(br);
				nj.setNianjia(nNj);

				br.setTimeLenStr(OtherUtil.miniute2StringWithF(br.getTimeLen()));
				br.setTimeLeftStr(OtherUtil.miniute2StringWithF(br.getTimeLeft()));
				nj.setNianjiaStr(OtherUtil.miniute2StringWithF(nj.getNianjia()));
				nj.setBingjiaStr(OtherUtil.miniute2StringWithF(nj.getBingjia()));

				njs.add(nj);
			}

			this.oaUtilService.nianjiaClear(njs, nrs);

			getMailUtil().sendToMe("年度结算成功", "年度结算成功");
		} catch (Exception e) {
			e.printStackTrace();

			getMailUtil().sendToMe("成功年度结算", Arrays.toString(e.getStackTrace()));
		}
	}

	// 最新日结
	public void rijie() {
		Calendar tc = Calendar.getInstance();
		tc.add(Calendar.DAY_OF_MONTH, -1);// 日期，设置为昨天
		// tc.add(Calendar.DAY_OF_MONTH, +2);// just for test
		Date today = tc.getTime();

		rijie(today, null, null);
	}

	/**
	 * 
	 * @param today
	 *            日期，如果传入这个参数，后面两个参数无效
	 * @param kqDay
	 *            格式yyyy-MM-dd
	 * @param sysDay
	 *            格式yyyy年MM月dd日
	 */
	public void rijie(Date today, String kqDay, String sysDay) {
		try {
			/*
			 * Calendar tc = Calendar.getInstance();
			 * tc.add(Calendar.DAY_OF_MONTH, -1);//日期，设置为昨天 Date today =
			 * tc.getTime();
			 */
			SimpleDateFormat remoteFmt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat localeFmt = new SimpleDateFormat("yyyy年MM月dd日");

			String realDay = kqDay;// remoteFmt.format(today);
			String day = sysDay;// localeFmt.format(today);

			if (today != null) {
				realDay = remoteFmt.format(today);
				day = localeFmt.format(today);
			}
			String url = Consts.ksurl + "/rest/ks/all";

			Map<String, String> params = new HashMap<String, String>();
			params.put("day", realDay);

			String content = HttpUtil.getContent(url, params, null, null);

			JsonResult jr = objMapper.readValue(content, JsonResult.class);// 从考勤点获取json数据

			if (jr.getRet() == -1) {
				throw new RuntimeException(jr.getMsg());
			}

			// 2014-06-14 08:41:22
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			List<CheckInFromKs> data = jr.getData();

			List<CheckIn> list = new ArrayList<CheckIn>();
			List<User> users = getUserSer().findAllUsers();
			Map<String, CheckInFromKs> cMap = new HashMap<String, CheckInFromKs>();

			for (CheckInFromKs cifk : data) {
				cMap.put(cifk.getName(), cifk);
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if (null != checkout) {
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}
				cifk.setCheckin(checkin);
				cifk.setCheckout(checkout);
			}

			for (User u : users) {
				CheckIn ci = new CheckIn();
				ci.setName(u.getName());
				ci.setEmpId(u.getId());
				ci.setDayStr(day);
				CheckInFromKs cifk = cMap.get(u.getName());
				if (cifk != null) {
					String checkin = cifk.getCheckin();
					String checkout = cifk.getCheckout();

					int checkinInt = OtherUtil.time2Int(checkin);
					int checkoutInt = 0;
					if (null != checkout) {
						checkoutInt = OtherUtil.time2Int(checkout);
					}
					ci.setCheckin(checkin);
					ci.setCheckinInt(checkinInt);
					ci.setCheckout(checkout);
					ci.setCheckoutInt(checkoutInt);
				}
				list.add(ci);
			}

			// A3数据
			List<CheckIn> listA3 = new ArrayList<CheckIn>();
			List<CheckInRemote> remotes = this.cSer.findRemoteCheckInByDay(day);
			if (remotes != null && !remotes.isEmpty()) {

				Map<String, CheckInRemote> rMap = new HashMap<String, CheckInRemote>();
				for (CheckInRemote cir : remotes) {
					rMap.put(cir.getName(), cir);
				}

				for (User u : users) {

					CheckInRemote cir = rMap.get(u.getName());
					if (cir != null) {
						CheckIn ci = new CheckIn();
						ci.setName(u.getName());
						ci.setEmpId(u.getId());
						ci.setDayStr(day);

						String checkin = cir.getCheckin();
						String checkout = cir.getCheckout();

						int checkinInt = OtherUtil.time2Int(checkin);
						int checkoutInt = 0;
						if (StringUtils.isNotEmpty(checkout)) {
							checkoutInt = OtherUtil.time2Int(checkout);
						}

						ci.setCheckin(checkin);
						ci.setCheckinInt(checkinInt);
						ci.setCheckout(checkout);
						ci.setCheckoutInt(checkoutInt);

						listA3.add(ci);
					}

				}
			}
			// end A3
			list = CheckInUtil.mergeCheckIn(list, listA3);

			this.getcSer().kaoQingHuiZong(day, list);

			int size = data.size() + remotes.size();
			getMailUtil().sendToMe("成功导入考勤数据", day + "， 成功导入：" + size + "条考勤数据。");

			if (today == null) { // 说明是延期汇总的数据
				return;
			}

			Day d = dateService.findByDayStrChina(day);

			if (Consts.nameOfHoliday.equals(d.getType())) {
				return;
			}

			StringBuffer sbu = new StringBuffer();
			StringBuffer sbu2 = new StringBuffer();
			sbu2.append("<html><head>");
			sbu2.append(
					"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，昨日打卡异常情况如下：")
					.append("</p>");
			sbu2.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>姓名</th>"
					+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>不正常打卡</th></tr>");

			List<EmpDayVo> empDays = kqService.findByEmpAndDayAll(day, day);

			for (EmpDayVo vo : empDays) { // 发送邮件

				if (vo.getEmpId() == 1) {
					continue;
				}

				if (vo.getEmpId() == 196 || vo.getEmpId() == 137 || vo.getEmpId() == 172 || vo.getEmpId() == 5) {
					continue;
				}

				int unlen = vo.getUncLen();
				if (unlen > 0) {
					vo.setUncLenStr(OtherUtil.miniute2String(unlen));
					sbu.append("[" + vo.getEmpName() + ":" + vo.getUncLenStr() + "], ");
					if (vo.getEmpName().length() < 6) {
						sbu2.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ vo.getEmpName()
								+ "</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ vo.getUncLenStr() + "</td></tr>");
					}

				}
			}
			sbu2.append("</table>");
			sbu2.append(
					"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
			String unStr = sbu2.toString();
			if (StringUtils.isNotEmpty(unStr)) {
				getMailUtil().sendToOaTeam("昨日（" + day + "）不正常打卡用户", unStr);
			}

			// 发送邮件通知用户最近一个月的不正常打卡情况
			sendUncEmail();

		} catch (Exception e) {
			e.printStackTrace();
			getMailUtil().sendToMe("定时导入考勤数据出现问题", "错误信息：" + e.getMessage() + "。" + Arrays.toString(e.getStackTrace()));
		}
	}

	public void rijieByMan(Date today, String kqDay, String sysDay, boolean reduceLeave) {// 是否扣除年假，以及添加年假记录
		try {
			/*
			 * Calendar tc = Calendar.getInstance();
			 * tc.add(Calendar.DAY_OF_MONTH, -1);//日期，设置为昨天 Date today =
			 * tc.getTime();
			 */
			SimpleDateFormat remoteFmt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat localeFmt = new SimpleDateFormat("yyyy年MM月dd日");

			String realDay = kqDay;// remoteFmt.format(today);
			String day = sysDay;// localeFmt.format(today);

			if (today != null) {
				realDay = remoteFmt.format(today);
				day = localeFmt.format(today);
			}
			String url = Consts.ksurl + "/rest/ks/all";

			Map<String, String> params = new HashMap<String, String>();
			params.put("day", realDay);

			String content = HttpUtil.getContent(url, params, null, null);

			JsonResult jr = objMapper.readValue(content, JsonResult.class);

			if (jr.getRet() == -1) {
				throw new RuntimeException(jr.getMsg());
			}

			// 2014-06-14 08:41:22
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			List<CheckInFromKs> data = jr.getData();

			if (data.isEmpty()) {

			}

			List<CheckIn> list = new ArrayList<CheckIn>();
			List<User> users = getUserSer().findAllUsers();
			Map<String, CheckInFromKs> cMap = new HashMap<String, CheckInFromKs>();

			for (CheckInFromKs cifk : data) {
				cMap.put(cifk.getName(), cifk);
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if (null != checkout) {
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}
				cifk.setCheckin(checkin);
				cifk.setCheckout(checkout);
			}

			for (User u : users) {
				CheckIn ci = new CheckIn();
				ci.setName(u.getName());
				ci.setEmpId(u.getId());
				ci.setDayStr(day);
				CheckInFromKs cifk = cMap.get(u.getName());
				if (cifk != null) {
					String checkin = cifk.getCheckin();
					String checkout = cifk.getCheckout();

					int checkinInt = OtherUtil.time2Int(checkin);
					int checkoutInt = 0;
					if (null != checkout) {
						checkoutInt = OtherUtil.time2Int(checkout);
					}
					ci.setCheckin(checkin);
					ci.setCheckinInt(checkinInt);
					ci.setCheckout(checkout);
					ci.setCheckoutInt(checkoutInt);
				}
				list.add(ci);
			}

			// A3数据
			List<CheckIn> listA3 = new ArrayList<CheckIn>();
			List<CheckInRemote> remotes = this.cSer.findRemoteCheckInByDay(day);
			if (remotes != null && !remotes.isEmpty()) {

				Map<String, CheckInRemote> rMap = new HashMap<String, CheckInRemote>();
				for (CheckInRemote cir : remotes) {
					rMap.put(cir.getName(), cir);
				}

				for (User u : users) {

					CheckInRemote cir = rMap.get(u.getName());
					if (cir != null) {
						CheckIn ci = new CheckIn();
						ci.setName(u.getName());
						ci.setEmpId(u.getId());
						ci.setDayStr(day);

						String checkin = cir.getCheckin();
						String checkout = cir.getCheckout();

						int checkinInt = OtherUtil.time2Int(checkin);
						int checkoutInt = 0;
						if (StringUtils.isNotEmpty(checkout)) {
							checkoutInt = OtherUtil.time2Int(checkout);
						}

						ci.setCheckin(checkin);
						ci.setCheckinInt(checkinInt);
						ci.setCheckout(checkout);
						ci.setCheckoutInt(checkoutInt);

						listA3.add(ci);
					}

				}
			}
			// end A3
			list = CheckInUtil.mergeCheckIn(list, listA3);

			this.getcSer().kaoQingHuiZongByMan(day, list, reduceLeave);

		} catch (Exception e) {
			e.printStackTrace();
			getMailUtil().sendToMe("定时导入考勤数据出现问题", "错误信息：" + e.getMessage() + "。" + Arrays.toString(e.getStackTrace()));
		}
	}

	private void sendUncEmail() {
		try {
			Calendar tc = Calendar.getInstance();
			tc.add(Calendar.DAY_OF_MONTH, -1);// 日期，设置为昨天
			Date endDay = tc.getTime();

			// tc = Calendar.getInstance();
			tc.add(Calendar.MONTH, -1);
			Date beginDay = tc.getTime();

			SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
			String begin = fmt.format(beginDay);
			String end = fmt.format(endDay);

			List<EmpDayVo> list = this.kqService.findUnEmpDay(begin, end);

			Map<String, ArrayList<EmpDayVo>> map = new HashMap<String, ArrayList<EmpDayVo>>();

			for (EmpDayVo vo : list) {
				ArrayList<EmpDayVo> days = map.get(vo.getEmpName());
				if (null == days) {
					days = new ArrayList<EmpDayVo>();
					map.put(vo.getEmpName(), days);
				}
				days.add(vo);
			}

			Set<String> empNames = map.keySet();
			for (String name : empNames) {
				if ("刘伟".equals(name)) {
					continue;
				}

				// TODO 考勤邮件 吴赛光、施浩、李告告忽略
				if ("吴赛光".equals(name) || "施浩".equals(name) || "李告告".equals(name) || "齐芳".equals(name)) {
					continue;
				}

				String email = null;
				ArrayList<EmpDayVo> days = map.get(name);
				StringBuilder daysSb = new StringBuilder();
				for (EmpDayVo d : days) {
					email = d.getEmail();
					daysSb.append(d.getDayStr());
					daysSb.append("、");
				}

				StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
				sb.append(name);
				sb.append("</p><p style='font-size:14px;padding:5px 0px;'>您" + daysSb.toString()
						+ "考勤不正常，请及时处理。如有疑问请联系IT-朱明</p>");
				sb.append(
						"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");

				String to = email;
				getMailUtil().sendEMail(to, null, Consts.defaultFrom, "考勤提醒", sb.toString());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 检查员工8.30是否打卡，如果没有请假，外出就发邮件通知
	 */
	public void checkInEmail() {
		try {
			// String today = DateUtil.getCurrentTime("yyyy-MM-dd");
			Calendar tc = Calendar.getInstance();// 当天
			// tc.add(Calendar.DAY_OF_MONTH, -1);
			tc.set(Calendar.HOUR_OF_DAY, 8);
			tc.set(Calendar.MINUTE, 30);
			tc.set(Calendar.SECOND, 0);
			Date today = tc.getTime();

			SimpleDateFormat remoteFmt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat localeFmt = new SimpleDateFormat("yyyy年MM月dd日");

			String realDay = remoteFmt.format(today);
			String dayStr = localeFmt.format(today);

			Day day = this.dateService.findByDayStrChina(dayStr);

			if (Consts.nameOfHoliday.equals(day.getType())) {
				return;
			}

			String url = Consts.ksurl + "/rest/ks/all";

			Map<String, String> params = new HashMap<String, String>();
			params.put("day", realDay);

			String content = HttpUtil.getContent(url, params, null, null);

			JsonResult jr = objMapper.readValue(content, JsonResult.class);

			if (jr.getRet() == -1) {

				throw new RuntimeException(jr.getMsg());
			}

			// 2014-06-14 08:41:22
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			List<CheckInFromKs> data = jr.getData();

			if (data.isEmpty()) {

			}

			// 新增代码。
			// 获取远程机器的打卡情况。
			List<CheckInFromKs> data2 = new ArrayList<CheckInFromKs>();

			List<CheckInRemote> remotes = this.cSer.findRemoteCheckInByDay(dayStr);
			for (CheckInRemote cir : remotes) {
				CheckInFromKs chfk = new CheckInFromKs();
				chfk.setCheckin(realDay + " " + cir.getCheckin() + ":00");
				if (StringUtils.isNotEmpty(cir.getCheckout())) {
					chfk.setCheckout(realDay + " " + cir.getCheckout() + ":00");
				}
				chfk.setName(cir.getName());
				data2.add(chfk);
			}

			data = CheckInUtil.mergeCheckIn2(data, data2);

			// end 新增代码

			Map<String, CheckInFromKs> cMap = new HashMap<String, CheckInFromKs>();
			List<User> users = this.userSer.findAllWithKQ(DateUtil.getTimeString(today, "yyyy年MM月dd日 HH时mm分"));

			for (CheckInFromKs cifk : data) {
				cMap.put(cifk.getName(), cifk);
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if (null != checkout) {
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}
				cifk.setCheckin(checkin);
				cifk.setCheckout(checkout);
			}

			StringBuffer sbu = new StringBuffer();
			StringBuffer sbu2 = new StringBuffer();
			sbu2.append("<html><head>");
			sbu2.append(
					"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，早上打卡异常情况如下：")
					.append("</p>");
			sbu2.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>姓名</th>"
					+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>打卡时间</th></tr>");
			for (User u : users) {
				if (u.getId() == 1) {
					continue;
				}

				// TODO 考勤邮件 吴赛光、施浩、李告告忽略
				if (u.getId() == 196 || u.getId() == 137 || u.getId() == 172 || u.getId() == 5) {
					continue;
				}

				CheckInFromKs cifk = cMap.get(u.getName());
				if (cifk != null) {// 打过卡的
					String checkin = cifk.getCheckin();
					int checkinInt = OtherUtil.time2Int(checkin);
					if (StringUtils.isEmpty(u.getEdu()) && checkinInt > (8 * 60 + 30)) { // 没有请假，并且没有外出
						sbu.append("[" + u.getName() + "/" + checkin + "]，");
						sbu2.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ u.getName()
								+ "</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ checkin + "</td></tr>");
						String to = u.getEmail();
						StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
						sb.append(u.getName());
						sb.append("</p><p style='font-size:14px;padding:5px 0px;'>您今天上班打卡时间" + checkin
								+ "，请及时处理。如有疑问请联系IT-朱明。</p>");
						sb.append(
								"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
						getMailUtil().sendEMail(to, null, Consts.defaultFrom, "打卡提醒", sb.toString());
					}
				} else if (StringUtils.isEmpty(u.getEdu())) {
					// 模拟账号不发送邮件 名字长度大于4就不是正式账号不发邮件
					if (u.getName().length() < 6) {
						sbu.append("[" + u.getName() + "/未打卡]，");
						sbu2.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ u.getName()
								+ "</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>"
								+ "未打卡</td></tr>");
					}
					String to = u.getEmail();
					StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
					sb.append(u.getName());
					sb.append("</p><p style='font-size:14px;padding:5px 0px;'>截止到目前为止您今天还没有打卡，请及时处理。如有疑问请联系IT-朱明</p>");
					sb.append(
							"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");

					getMailUtil().sendEMail(to, null, Consts.defaultFrom, "打卡提醒", sb.toString());
				}
			}
			sbu2.append("</table>");
			sbu2.append(
					"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
			String unStr = sbu2.toString();
			if (StringUtils.isNotEmpty(unStr)) {
				getMailUtil().sendToOaTeam("早上打卡异常情况", unStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getMailUtil().sendToMe("早晨邮件通知打卡发生异常", Arrays.toString(e.getStackTrace()));
		}
	}

	public void setUserSer(UserService userSer) {
		this.userSer = userSer;
	}

	public UserService getUserSer() {
		return userSer;
	}

	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public MailUtil getMailUtil() {
		return mailUtil;
	}

	public void setcSer(CheckInService cSer) {
		this.cSer = cSer;
	}

	public CheckInService getcSer() {
		return cSer;
	}

	public void setDateService(DateService dateService) {
		this.dateService = dateService;
	}

	public DateService getDateService() {
		return dateService;
	}

	public void setKqService(KqService kqService) {
		this.kqService = kqService;
	}

	public KqService getKqService() {
		return kqService;
	}

	/**
	 * 定时从考勤服务器上获取前一天的考勤数据
	 */
	public void job1() {
		try {

			// String today = DateUtil.getCurrentTime("yyyy-MM-dd");
			Calendar tc = Calendar.getInstance();
			tc.add(Calendar.DAY_OF_MONTH, -1);
			Date today = tc.getTime();

			SimpleDateFormat remoteFmt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat localeFmt = new SimpleDateFormat("yyyy年MM月dd日");

			String realDay = remoteFmt.format(today);
			String day = localeFmt.format(today);
			String url = Consts.ksurl + "/rest/ks/all";

			Map<String, String> params = new HashMap<String, String>();
			params.put("day", realDay);

			String content = HttpUtil.getContent(url, params, null, null);

			JsonResult jr = objMapper.readValue(content, JsonResult.class);

			if (jr.getRet() == -1) {

				throw new RuntimeException(jr.getMsg());
			}

			// 2014-06-14 08:41:22
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			List<CheckInFromKs> data = jr.getData();

			if (data.isEmpty()) {

			}

			List<CheckIn> list = new ArrayList<CheckIn>();
			List<User> users = getUserSer().findAllUsers();
			Map<String, CheckInFromKs> cMap = new HashMap<String, CheckInFromKs>();

			for (CheckInFromKs cifk : data) {
				cMap.put(cifk.getName(), cifk);
				String checkin = cifk.getCheckin();
				String checkout = cifk.getCheckout();
				Date d = sdf1.parse(checkin);
				checkin = sdf2.format(d);
				if (null != checkout) {
					d = sdf1.parse(checkout);
					checkout = sdf2.format(d);
				}
				cifk.setCheckin(checkin);
				cifk.setCheckout(checkout);
			}

			for (User u : users) {
				CheckIn ci = new CheckIn();
				ci.setEmpId(u.getId());
				ci.setDayStr(day);
				CheckInFromKs cifk = cMap.get(u.getName());
				if (cifk != null) {
					String checkin = cifk.getCheckin();
					String checkout = cifk.getCheckout();

					int checkinInt = OtherUtil.time2Int(checkin);
					int checkoutInt = 0;
					if (null != checkout) {
						checkoutInt = OtherUtil.time2Int(checkout);
					}
					ci.setCheckin(checkin);
					ci.setCheckinInt(checkinInt);
					ci.setCheckout(checkout);
					ci.setCheckoutInt(checkoutInt);
				}
				list.add(ci);
			}

			this.getcSer().addCheckInNew(day, list);

			getMailUtil().sendToMe("成功导入考勤数据", day + "， 成功导入：" + data.size() + "条考勤数据。");
		} catch (Exception e) {
			e.printStackTrace();
			getMailUtil().sendToMe("定时导入考勤数据出现问题", Arrays.toString(e.getStackTrace()));
		}
	}

	public void setOaUtilService(OAUtilService oaUtilService) {
		this.oaUtilService = oaUtilService;
	}

	/**
	 * @Description: 根据员工id获取员工的入职年限
	 * @param empId
	 * @return int
	 * @author mlsong
	 * @throws ParseException
	 * @date 2017年11月20日 上午8:57:16
	 */
	private int getEmpYearsOfEntry(int empId) throws ParseException {
		// 获取员工信息
		User user = userSer.findById(empId);

		// 获取员工入职信息
		String entryDateStr = user.getEntryDate();
		if (StringUtils.isEmpty(entryDateStr)) {
			return 0;
		}

		// 将入职时间格式化
		SimpleDateFormat yearFmt = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFmt = new SimpleDateFormat("MM");
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy年MM月dd日");

		Date today = new Date();
		Date entryDate = strFmt.parse(entryDateStr);

		// 今年
		int thisYear = Integer.parseInt(yearFmt.format(today));

		// 入职年、月
		int entryYear = Integer.parseInt(yearFmt.format(entryDate));
		int entryMonth = Integer.parseInt(monthFmt.format(entryDate));

		int ret = thisYear - entryYear;
		// 如果在四月后日志，则不能算满一年
		if (entryMonth >= 4) {
			ret -= 1;
		}

		// 十天为基数，最高不多于15天
		if (ret > 5) {
			ret = 5;
		}

		return ret;
	}

	// 定时清理超时印章申请
	public void cleanOutTimeSealApply() {
		System.out.println("正在清理超时印章处理...");
		try {
			List<SealApplyDetail> sads = this.sealService.findSealDeal(1);
			for (SealApplyDetail sad : sads) {
				String date = sad.getYzDate();
				Date yzDate  = DateUtil.stringToDate(date.substring(0, 11), Consts.chinaDateFmt);
				Date now = new Date();
				if (yzDate.before(now)) {
					SealApplyDetail sealApply = new SealApplyDetail();
					sealApply.setId(sad.getId());
					sealApply.setStatus(-1);
					this.sealService.updateSealApplyDetail(sealApply);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("清理超时印章处理完成");
	}

	/**
	 * 为新入职的员工添加年假和病假 @Title: addNewEmpNianjia @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return: void @throws
	 */
	@SuppressWarnings("deprecation")
	public void addNewEmpNianjia() {
		System.out.println("新入职员工年假结算开始");
		// 年假公式X/12=Y/10，（X为入职的月份到次年的4月总月数，Y为计入年假天数）
		// 病假公式X/12=Y/7,（X为入职的月份到次年的4月总月数，Y为计入病假天数）

		// 每月1号早上一点结算，当前时间减一天，截取年月
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// TODO 修改数值
		cal.add(Calendar.MONTH, -1);
		Date date = cal.getTime();
		String dateStr = DateUtil.getTimeString(date, Consts.chinaDateFmt);
		// 计算年假和病假
		int ys = 15 - date.getMonth();
		int nj = (int) Math.ceil(ys * 10.0 / 12);
		int bj = (int) (Math.ceil(ys * 7.0 / 12));
		String  str = dateStr.substring(0, 7);
		// 查询这个月入职的员工信息
		List<User> users = this.userSer.findUsersByEntryDate(str);

		List<NianjiaRecord> nrs = new ArrayList<NianjiaRecord>();
		List<EmpNianjia> njs = new ArrayList<EmpNianjia>();
		for (User user : users) {
			EmpNianjia empNj = this.oaUtilService.getEmpNianjiaById(user.getId());
			// 年假病假
			empNj.setBingjia(bj * 60 * 8);
			empNj.setNianjia(nj * 60 * 8);
			njs.add(empNj);

			// 年假记录
			NianjiaRecord record = new NianjiaRecord();
			record.setEmpId(user.getId());
			record.setType("年假");
			record.setBz("新入职员工假期计入");
			record.setDayStr(DateUtil.getTimeString(new Date(), Consts.chinaDateFmt));
			record.setTimeLen(-nj * 60 * 8);
			record.setTimeLeft(nj * 60 * 8);
			record.setLeaveId(0);
			nrs.add(record);
			// 病假
			NianjiaRecord record2 = new NianjiaRecord();
			record2.setEmpId(user.getId());
			record2.setType("病假");
			record2.setBz("新入职员工假期计入");
			record2.setDayStr(DateUtil.getTimeString(new Date(), Consts.chinaDateFmt));
			record2.setTimeLen(-bj * 60 * 8);
			record2.setTimeLeft(bj * 60 * 8);
			record2.setLeaveId(0);
			nrs.add(record2);
		}
		this.oaUtilService.nianjiaClear(njs, nrs);
		getMailUtil().sendToMe("新入职员工年假结算成功", "新入职员工年假结算成功");
		System.out.println("新入职员工年假结算结束");
	}

}
