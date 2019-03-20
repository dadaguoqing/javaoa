package com.hj.oa.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.commons.MateriaConstants;
import com.hj.commons.MateriaInsertType;
import com.hj.oa.Consts;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.Seal;
import com.hj.oa.bean.SealApply;
import com.hj.oa.bean.SealApplyDetail;
import com.hj.oa.bean.SealApprove;
import com.hj.oa.bean.SealDate;
import com.hj.oa.bean.SealElse;
import com.hj.oa.bean.SealType;
import com.hj.oa.bean.User;
import com.hj.oa.dao.DateMapper;
import com.hj.oa.dao.SealApplyDetailMapper;
import com.hj.oa.dao.SealApplyMapper;
import com.hj.oa.dao.SealApproveMapper;
import com.hj.oa.dao.SealDateMapper;
import com.hj.oa.dao.SealElseMapper;
import com.hj.oa.dao.SealMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;
import com.hj.util.MailTableUtil;
import com.hj.util.MailUtil;

@Service
public class SealService {

	@Autowired
	SealMapper sealMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	SealApplyMapper saMapper;
	@Autowired
	SealApplyDetailMapper sadMapper;
	@Autowired
	SealApproveMapper seapMapper;
	@Autowired
	MateriaService_v2 maService;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	SealDateMapper sdMapper;
	@Autowired
	SealElseMapper seMapper;
	@Autowired
	DateMapper dateMapper;

	public List<Seal> selectAllSeal() {
		return this.sealMapper.selectAllSeal();
	}

	public void addSeal(Seal seal) {
		this.sealMapper.insertSelective(seal);
	}

	public void updateSeal(Seal seal) {
		this.sealMapper.updateByPrimaryKeySelective(seal);
	}

	public Seal selectSealById(Integer id) {
		return this.sealMapper.selectByPrimaryKey(id);
	}

	public List<SealType> selectAllSealType() {
		return this.sealMapper.selectAllSealType();
	}

	public void saveSealApply(User user, SealApply sa, HttpServletRequest req) throws Exception {
		sa.setEmpId(user.getId());
		sa.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		if(sa.getSealType() == 1) {
			sa.setCurrentId(MateriaConstants.GENERAL_MANAGER);
			sa.setStatus(2);
		} else if(sa.getSealType() == 3) {
			List<User> sealMan = this.userMapper.findUserByRole(MateriaConstants.SEAL_MANAGER);
			if(sealMan.isEmpty()) {
				throw new Exception("暂无印章管理员");
			}
			sa.setCurrentId(sealMan.get(0).getId());
			sa.setStatus(1);
		}
		this.saMapper.insertSelective(sa);
		List<SealApplyDetail> sads = new ArrayList<SealApplyDetail>();
		String[] fileNames = req.getParameterValues("fileName");
		if (fileNames != null) {
			String[] fileDetails = req.getParameterValues("fileDetail");
			String[] sealCompanys = req.getParameterValues("sealCompany");
			String[] sealNames = req.getParameterValues("sealName");
			String[] fileNums = req.getParameterValues("fileNum");
			String[] userNums = req.getParameterValues("userNum");
			String[] locations = req.getParameterValues("location");
			for (int i = 0; i < fileNames.length; i++) {
				SealApplyDetail sad = new SealApplyDetail();
				sad.setFileDetail(fileDetails[i]);
				sad.setFileName(fileNames[i]);
				sad.setSealCompany(sealCompanys[i]);
				sad.setFileName(fileNames[i]);
				sad.setFileNum(Integer.parseInt(fileNums[i]));
				sad.setSealName(sealNames[i]);
				sad.setUserNum(Integer.parseInt(userNums[i]));
				sad.setLoaction(locations[i]);
				sad.setNumber(sa.getNumber());
				this.sadMapper.insertSelective(sad);
				sads.add(sad);
			}
		} else {
			throw new Exception("数据传输失败");
		}
		this.maService.updateSeq(sa.getNumber(), MateriaInsertType.SEAL_APPLY.getType());
		String table = MailTableUtil.createSealTable(sa);
		String table2 = MailTableUtil.createSealDetailTable(sads);
		User user2 = this.userMapper.findById(sa.getCurrentId());
		this.sendMailTable(user2, user, MateriaConstants.SEAL_APPLY, table, table2);
	}

	public List<SealApply> findMyApply(Integer id) {
		return this.saMapper.selectByEmpId(id);
	}

	public List<SealApply> findMyApprove(Integer id) {
		return this.saMapper.selectByCurrentId(id);
	}

	public List<SealApply> findAllApply() {
		return this.saMapper.selectAllApply();
	}

	public SealApply findSealApplyById(Integer id) {
		return this.saMapper.selectByPrimaryKey(id);
	}

	public List<SealApprove> findApproveByNumber(String number) {
		return this.seapMapper.findApproveByNumber(number);
	}
	
	public SealApprove findApproveByNumberAndStatus(String number,Integer status) {
		return this.seapMapper.findApproveByNumberAndStatus(number,status);
	}

	public List<SealApplyDetail> findSealDetail(String number) {
		return this.sadMapper.selectByNumber(number);
	}

	public void saveApprove(String sp, String opinion, Integer id,String ptr) throws Exception {
		if (id == null) {
			throw new Exception("数据异常");
		}
		SealApply sa = this.saMapper.selectByPrimaryKey(id);
		int status = sa.getStatus();
		SealApprove seap = new SealApprove();
		seap.setCode(sa.getNumber());
		seap.setApproveId(sa.getCurrentId());
		seap.setApproveStatus(status);
		seap.setApproveResult(sp);
		seap.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		seap.setApproveOpinion(opinion);
		this.seapMapper.insertSelective(seap);
		SealApply sa2 = new SealApply();
		sa2.setId(id);
		if ("通过".equals(sp)) {
			switch(status) {
			case 1:
				sa2.setCurrentId(MateriaConstants.GENERAL_MANAGER);
				sa2.setStatus(status + 1);
				sa2.setPtr(ptr);
				break;
			case 2:
				sa2.setCurrentId(0);
				sa2.setStatus(status + 1);
				break;
			default :
				throw new Exception("审批状态异常");
			}
		} else {
			sa2.setCurrentId(0);
			sa2.setStatus(-1);
		}
		this.saMapper.updateByPrimaryKeySelective(sa2);
		List<SealApplyDetail> sads = this.sadMapper.selectByNumber(sa.getNumber());
		String table = MailTableUtil.createSealTable(sa);
		String table2 = MailTableUtil.createSealDetailTable(sads);
		User user = this.userMapper.findById(sa.getEmpId());
		if(sa2.getStatus() == 2 ) {
			User user3 = this.userMapper.findById(sa2.getCurrentId());
			this.sendMailTable(user3, user, MateriaConstants.SEAL_APPLY, table, table2);
		}

		// 审批成功
		if (sa2.getStatus() == 3) {
			this.sendMaileResult2(user, sa2.getStatus(), MateriaConstants.SEAL_APPLY, table, table2);
			// TODO 邮件通知印章管理小组
			List<User> users = this.getSealteam();
			// 外带人 
			if(StringUtils.isNotBlank(sa.getWdr())) {
				List<User> wdr = this.userMapper.findUserByName(sa.getWdr());
				users.add(wdr.get(0));
			}
			for (User user2 : users) {
				this.sendMaileResult3(user, user2, status, MateriaConstants.SEAL_APPLY, table, table2);
			}
		}
	}

	public List<SealApplyDetail> findSealDeal(Integer status) {
		return this.sadMapper.selectByDealStatus(status);
	}
	
	public void updateSealApplyDetail(SealApplyDetail sealApplyDetail) {
		this.sadMapper.updateByPrimaryKeySelective(sealApplyDetail);
	}

	public void updateSealApplyBySelect(SealApply record) {
		this.saMapper.updateByPrimaryKeySelective(record);
	}

	public List<SealDate> findSealDate() {
		return this.sdMapper.selectByStatus();
	}

	public SealDate findSealDateById(Integer id) {
		return this.sdMapper.selectByPrimaryKey(id);
	}

	public void updateSealDateBySelect(SealDate sd) {
		this.sdMapper.updateByPrimaryKeySelective(sd);
	}

	public void updateById(Integer id) {
		this.sdMapper.updateById(id);
	}

	public List<String> countSealDate() throws Exception {
		Date date = new Date();
		String day = DateUtil.getTimeString(date, Consts.chinaDateFmt);
		SealDate time = this.sdMapper.selectByPrimaryKey(8);
		String hour = time.getWeekday();
		String newDate = day + hour.substring(0, 6);
		Date date1 = DateUtil.stringToDate(newDate, "yyyy年MM月dd日hh时mm分");
		// 提前时间
		SealDate tq = this.sdMapper.selectByPrimaryKey(9);
		if(null == tq) {
			throw new Exception("未设置用章时间提前结束时间");
		}
		Integer js = Integer.parseInt(tq.getWeekday());
		// 提前半个小时
		date1.setTime(date1.getTime() - js * 60 * 1000);
		
		List<Integer> weeknums = this.sdMapper.findWeekNums();
		List<String> dates = new ArrayList<String>();
		// 筛选从现在开始30天内符合条件的日期
		if (!weeknums.isEmpty()) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			if (date.after(date1)) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			for (int i = 0; i < 30; i++) {
				for (Integer wnum : weeknums) {
					if (wnum == (week + i) % 7) {
						String weekDay = numToWeekDay((week + i) % 7);
						String dateStr = DateUtil.getTimeString(calendar.getTime(), Consts.chinaDateFmt);
						Day day1 = this.dateMapper.isHoliday(dateStr);
						if(!Consts.nameOfHoliday.equals(day1.getType())) {
							dates.add( dateStr + "（" + weekDay + "）");
							break;
						}
					}
				}
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} else {
			throw new Exception("未设置用章日期");
		}
		return dates;
	}

	public List<String> countSealDate2() throws ParseException {
		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		for (int i = 0; i < 30; i++) {
			String weekDay = numToWeekDay((week + i) % 7);
			String dateStr = DateUtil.getTimeString(calendar.getTime(), Consts.chinaDateFmt);
			Day day1 = this.dateMapper.isHoliday(dateStr);
			if(!Consts.nameOfHoliday.equals(day1.getType())) {
				dates.add( dateStr + "（" + weekDay + "）");
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dates;
	}

	public String numToWeekDay(int i) {
		String weekDay = "";
		switch (i) {
		case 0:
			weekDay = "星期日";
			break;
		case 1:
			weekDay = "星期一";
			break;
		case 2:
			weekDay = "星期二";
			break;
		case 3:
			weekDay = "星期三";
			break;
		case 4:
			weekDay = "星期四";
			break;
		case 5:
			weekDay = "星期五";
			break;
		case 6:
			weekDay = "星期六";
			break;
		default:
			break;
		}
		return weekDay;
	}

	public List<SealElse> getYzsy() {
		List<SealElse> ses = this.seMapper.selectByType(MateriaConstants.SEAL_YZSY);
		return ses;
	}

	public List<SealElse> getYyc() {
		List<SealElse> ses = this.seMapper.selectByType(MateriaConstants.SEAL_YYC);
		return ses;
	}
	
	public List<SealElse> getEealElseByType(String type) {
		List<SealElse> ses = this.seMapper.selectByType(type);
		return ses;
	}
	
	public void updateSealElse(SealElse se) {
		this.seMapper.updateByPrimaryKeySelective(se);
	}
	public void insertSealElse(SealElse se) {
		this.seMapper.insertSelective(se);
	}
	
	public List<Integer> findMyDepts(Integer id) {
		return this.seapMapper.findMyDepts(id);
	}
	

	/************************************************ 邮件相关 ******************************************************************/

	/**
	 * 获取印章小组成员 张丽、陈珊珊、陈军、杨树娟、李玲艺
	 * 
	 * @return
	 */
	public List<User> getSealteam() {
		List<User> users = new ArrayList<User>();
		User user = this.userMapper.findById(96);// 张丽
		User user1 = this.userMapper.findById(12);
		User user2 = this.userMapper.findById(6);
		User user3 = this.userMapper.findById(9);
		User user4 = this.userMapper.findById(61);
		User user5 = this.userMapper.findById(171);
		users.add(user5);
		users.add(user4);
		users.add(user2);
		users.add(user3);
		users.add(user1);
		users.add(user);
		return users;
	}

	/**
	 * 获取登录人的权限 常规用章权限人：行政人事部、综合发展部、财务部、信息技术中心、产品业务部、品牌运营部、王莉君、陈利静、戴炫蕊、马慧琳
	 * 临时用章权限人：各部门主管、张丽、陈利静 外带用章权限人：财务部主管
	 * @return
	 */
	public Integer getSealAccess(Integer id) {
		// 财务主管id
		List<User> users = this.userMapper.findUserByRole("外带用章");
		for (User user : users) {
			if (user.getId() == id) {
				return 3;
			}
		}
		return 1;
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
	public void sendMailTable(User user, User user2, String type, String table, String table2) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("向您提交了一个").append(type)
				.append("，请及时登录系统审批。详情如下：</p>");
		sb.append(table + "<br/>");
		sb.append(table2);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}

	public void sendMaileResult2(User user, int status, String type, String table, String table2) {
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
		sb.append(table + "<br/>");
		sb.append(table2);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}

	public void sendMaileResult3(User user1, User user, int status, String type, String table, String table2) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == -1 ? "<a style='color:green;'>未通过审批</a>" : "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(user1.getDeptName() + user1.getName()).append("的一个")
				.append(type).append(result);
		sb.append("，请及时处理。详情如下：</p>");
		sb.append(table + "<br/>");
		sb.append(table2);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "处理通知", text);
	}
}
