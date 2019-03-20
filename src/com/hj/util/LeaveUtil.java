package com.hj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.hj.commons.ApiResult;
import com.hj.commons.ResultCode;
import com.hj.oa.Consts;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.Role;
import com.hj.oa.service.DateService;

/**
 * 请假工具类
 * 
 * @author longlian
 *
 */
public class LeaveUtil {

	public static void main(String[] args) {

	}

	public static boolean hasRole(List<Role> roles, String roleName) {

		return RoleUtil.hasRole(roles, roleName);
		/*
		 * for(Role r : roles){ if(roleName.equals(r.getName())){ return true; }
		 * } return false;
		 */
	}

	private static boolean inSameDay(Calendar cal1, Calendar cal2) {
		int y1 = cal1.get(Calendar.YEAR);
		int d1 = cal1.get(Calendar.DAY_OF_YEAR);

		int y2 = cal2.get(Calendar.YEAR);
		int d2 = cal2.get(Calendar.DAY_OF_YEAR);

		return (y1 == y2) && (d1 == d2);
	}

	private static boolean isBeforeNDays(Calendar bc, Calendar ec, int days) {
		bc.add(Calendar.DAY_OF_MONTH, days);
		if (bc.after(ec)) {
			if (!inSameDay(bc, ec)) {
				return false;
			}
		}
		return true;
	}

	public static String shiwaiWaichu(Leave leave, int mits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal) {
		try {

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
				leave.setStatus(3); // 3-总经理审核
				leave.setBossId(Consts.managerId); // 总经理
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
				leave.setBossId(Consts.managerId);

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId); // 总监的id
					leave.setCurrentId(Consts.directorId);
				} else {// 非技术部
					leave.setDirectId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());
				leave.setBossId(Consts.managerId);

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId);
				} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
					leave.setDirectId(Consts.sellDirectorId);
				} else {
					leave.setDirectId(Consts.managerId);
				}

			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String shineiWaichu(Leave leave, int mits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal) {
		try {

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监

				leave.setStatus(3); // 3-总经理审核
				leave.setBossId(Consts.managerId); // 总经理
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId); // 总监的id
					leave.setCurrentId(Consts.directorId);
				} else {// 非技术部
					leave.setDirectId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工

				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String tiaoxiu(Leave leave, int mits, int lmits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean daili) {
		try {

			if (!daili) {
				if (mits > 2400) {
					return "根据公司规定，年假或者调休不能超过5天。";
				}

				if (mits < 480) {// 事假一天以内需提前一天申请
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 1)) {
						return "根据公司规定，年假或者调休一天以内需提前一天申请";
					}
				} else if (mits < 960) {
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 2)) {
						return "根据公司规定，年假或者调休一（含）到两天需提前两天申请";
					}
				} else {
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 7)) {
						return "根据公司规定，年假或者调休两天（含）以上需提前一周申请";
					}
				}
			}

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			mits += lmits;

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监

				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setCurrentId(Consts.directorId);

					// 超过4小时需要总监批准
					if (mits >= 240) {
						leave.setBossId(Consts.managerId);
					}

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 4小时-2天
				if (mits >= 240 && mits < 960) {// 总监批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
					} else {
						leave.setDirectId(Consts.managerId);
					}
				} else if (mits >= 960) {// 两天以上,总经理批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
						leave.setBossId(Consts.managerId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
						leave.setBossId(Consts.managerId);
					} else {
						leave.setDirectId(Consts.managerId);
						leave.setBossId(Consts.managerId);
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// public static String shijia2(Leave leave, int mits, Dept dept, List<Role>
	// roles, Calendar beginTimeCal, Calendar createTimeCal){}

	/**
	 * 
	 * @param leave
	 * @param mits
	 * @param dept
	 * @param roles
	 * @param beginTimeCal
	 * @param createTimeCal
	 * @return
	 */
	public static String shijia(Leave leave, int mits, int lmits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean daili) {
		try {

			if (!daili) { // 如果是代理，不需要合符这些规定。
				if (mits > 2400) {
					return "根据公司规定，事假不能超过5天。";
				}

				if (mits < 480) {// 事假一天以内需提前一天申请
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 1)) {
						return "根据公司规定，事假一天以内需提前一天申请";
					}
				} else if (mits < 960) {
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 2)) {
						return "根据公司规定，事假一（含）到两天需提前两天申请";
					}
				} else {
					if (!isBeforeNDays(createTimeCal, beginTimeCal, 7)) {
						return "根据公司规定，事假两天（含）以上需提前一周申请";
					}
				}
			}

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			mits += lmits;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监

				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setCurrentId(Consts.directorId);

					// 超过4小时需要总监批准
					if (mits >= 240) {
						leave.setBossId(Consts.managerId);
					}

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 4小时-2天
				if (mits >= 240 && mits < 960) {// 总监批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
					} else {
						leave.setDirectId(Consts.managerId);
					}
				} else if (mits >= 960) {// 两天以上,总经理批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
						leave.setBossId(Consts.managerId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
						leave.setBossId(Consts.managerId);
					} else {
						leave.setDirectId(Consts.managerId);
						leave.setBossId(Consts.managerId);
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param leave
	 * @param mits
	 * @param dept
	 * @param roles
	 * @param beginTimeCal
	 * @param createTimeCal
	 * @return
	 */
	public static String sangjia(Leave leave, int mits, Dept dept, List<Role> roles, boolean daili) {
		try {
			if (!daili) {
				if (mits > 480 * 3) {
					return "根据公司规定，丧假不能超过3天。";
				}
			}

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管
				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.directorId);

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId);
					leave.setBossId(Consts.managerId);
				} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
					leave.setDirectId(Consts.sellDirectorId);
					leave.setBossId(Consts.managerId);
				} else {
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 产假
	 * @param leave
	 * @param mits
	 * @param dept
	 * @param roles
	 * @param beginTimeCal
	 * @param createTimeCal
	 * @return
	 */
	public static String chanjia(Leave leave, int mits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean daili,Calendar endTimeCal) {
		try {

			if (!daili) {
				createTimeCal.add(Calendar.DAY_OF_MONTH, 7);
				if (createTimeCal.after(beginTimeCal)) {// 如果还在它之前
					if (!inSameDay(createTimeCal, beginTimeCal)) {
						return "根据公司规定，产假必须提前一周申请。";
					}
				}
				// TODO 产假
				long day = (endTimeCal.getTimeInMillis() - beginTimeCal.getTimeInMillis())
						/(24*3600*1000);
				if (day >= 159) {
					return "根据公司规定，产假不能超过158天。";
				}
			}

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管
				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.directorId);

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId);
					leave.setBossId(Consts.managerId);
				} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
					leave.setDirectId(Consts.sellDirectorId);
					leave.setBossId(Consts.managerId);
				} else {
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param leave
	 * @param mits
	 * @param dept
	 * @param roles
	 * @param beginTimeCal
	 * @param createTimeCal
	 * @return
	 */
	public static String hunjia(Leave leave, int mits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean daili) {
		try {

			if (!daili) {
				createTimeCal.add(Calendar.DAY_OF_MONTH, 7);
				if (createTimeCal.after(beginTimeCal)) {// 如果还在它之前
					if (!inSameDay(createTimeCal, beginTimeCal)) {
						return "根据公司规定，婚假必须提前一周申请。";
					}
				}

				if (mits > 4800) {
					return "根据公司规定，婚假不能超过10天。";
				}
			}

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
															// //没有部门的，统一交给总经理管理
				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管
				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.directorId);

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setDirectId(Consts.directorId);
					leave.setBossId(Consts.managerId);
				} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
					leave.setDirectId(Consts.sellDirectorId);
					leave.setBossId(Consts.managerId);
				} else {
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
				}
			}

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param leave
	 * @param mits
	 * @param dept
	 * @param roles
	 * @return
	 */
	public static String bingjia(Leave leave, int mits, int lmits, Dept dept, List<Role> roles) {
		try {
			// int hours = mits/60;
			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			mits += lmits;

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监
				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管
				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setCurrentId(Consts.directorId);

					// 超过4小时需要总监批准
					if (mits >= 240) {
						leave.setBossId(Consts.managerId);
					}

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else {// 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 4小时-2天
				if (mits >= 240 && mits < 960) {// 总监批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
					} else {
						leave.setDirectId(Consts.managerId);
					}
				} else if (mits >= 960) {// 两天以上,总经理批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
						leave.setBossId(Consts.managerId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
						leave.setBossId(Consts.managerId);
					} else {
						leave.setDirectId(Consts.managerId);
						leave.setBossId(Consts.managerId);
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String xiaojia(LeaveCancel leave, int mits, Dept dept, List<Role> roles) {
		try {

			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			if (hasRole(roles, "技术总监") || dept == null) {// 技术总监或者总监

				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setCurrentId(Consts.directorId);

					// 超过4小时需要总监批准
					if (mits >= 240) {
						leave.setBossId(Consts.managerId);
					}

				} else {// 非技术部
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else { // 普通员工
				leave.setStatus(1);// 主管审核
				leave.setMgrId(dept.getMgrId());
				leave.setCurrentId(dept.getMgrId());

				// 4小时-2天
				if (mits >= 240 && mits < 960) {// 总监批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
					} else {
						leave.setDirectId(Consts.managerId);
					}
				} else if (mits >= 960) {// 两天以上,总经理批准
					// 是否技术部的
					if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
						leave.setDirectId(Consts.directorId);
						leave.setBossId(Consts.managerId);
					} else if (dept != null && dept.getPid().equals(new Integer(Consts.sellDeptId))) {// 营销中心
						leave.setDirectId(Consts.sellDirectorId);
						leave.setBossId(Consts.managerId);
					} else {
						leave.setDirectId(Consts.managerId);
						leave.setBossId(Consts.managerId);
					}
				}
			}

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 出差申请
	 * @param leave
	 *            出差信息
	 * @param mits
	 *            时长
	 * @param dept
	 *            部门
	 * @param roles
	 *            职位
	 * @param beginTimeCal
	 *            开始时间
	 * @param createTimeCal
	 *            申请创建时间
	 * @author mlsong
	 * @date 2017年8月5日 下午1:27:53
	 */
	public static ApiResult travelApplication(Leave leave, int mits, Dept dept, List<Role> roles, Calendar beginTimeCal,
			Calendar createTimeCal, boolean isProxy, DateService dateService) {
		ApiResult result = new ApiResult();
		String message = new String();
		Calendar tempCal = beginTimeCal;
		Date now = new Date(); // 当前时间
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		/**
		 * 出差申请流程
		 */
		// 1.是否是代理，如果是代理，则没有时间限制
		if (!isProxy) {
			// 2.出差条件判断
			if (mits <= 240) { // 四小时以内（含四小时），须提前三十分钟工作时间
				/*if (!isBeforeNDays(createTimeCal, beginTimeCal, 1)) {
					result.setResultCode(ResultCode.FAILED);
					message = "根据公司规定，出差一天以内需提前一天申请";
				}*/
				
				// 出差制度修改
				// 小于两个小时 30'
				if (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 8 && beginTimeCal.get(Calendar.MINUTE) == 30) {
					// 等于8点30 前天17点
					tempCal = dateService.setCalDate(tempCal, 1);
					tempCal.add(Calendar.HOUR, 9);
					tempCal.add(Calendar.MINUTE, -30);
				} else if (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 13 && beginTimeCal.get(Calendar.MINUTE) == 0) {
					// 等于1点 当天11点半
					tempCal.add(Calendar.HOUR, -2);
					tempCal.add(Calendar.MINUTE, 30);
				} else if (beginTimeCal.get(Calendar.MINUTE) == 0) {
					// 整点 小时-1，分钟+30
					tempCal.add(Calendar.HOUR, -1);
					tempCal.add(Calendar.MINUTE, 30);
				} else {
					// 非整点 分钟-30
					tempCal.add(Calendar.MINUTE, -30);
				}
				if (nowCal.after(tempCal)) {
					result.setResultCode(ResultCode.FAILED);
					message = "出差时间必须提前30分钟工作时长";
				}
				
			} else if (mits <= 480) { // 出差4-8小时（含8小时），须提前4小时工作时长
				/*if (!isBeforeNDays(createTimeCal, beginTimeCal, 2)) {
					result.setResultCode(ResultCode.FAILED);
					message = "根据公司规定，出差一（含）到两天需提前两天申请";
				}*/
				
				// 小于8个小时 4h
				if ((beginTimeCal.get(Calendar.HOUR_OF_DAY) == 8 && beginTimeCal.get(Calendar.MINUTE) == 30)
						|| ((beginTimeCal.get(Calendar.HOUR_OF_DAY) > 8 && beginTimeCal.get(Calendar.HOUR_OF_DAY) < 12))
						|| (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 12 && beginTimeCal.get(Calendar.MINUTE) == 0)
						|| (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 13 && beginTimeCal.get(Calendar.MINUTE) == 0)) {
					// 8:30 - 12:00 1:00 前天13:30-17:30
					tempCal = dateService.setCalDate(tempCal, 1);
					tempCal.add(Calendar.HOUR, 5);
				} else if ((beginTimeCal.get(Calendar.HOUR_OF_DAY) > 13 && beginTimeCal.get(Calendar.HOUR_OF_DAY) < 16)
						|| (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 16 && beginTimeCal.get(Calendar.MINUTE) == 0)
						|| (beginTimeCal.get(Calendar.HOUR_OF_DAY) == 13 && beginTimeCal.get(Calendar.MINUTE) == 30)) {
					// 13:30 - 16:30 当天8:30-11:30
					tempCal.add(Calendar.HOUR, -5);
				} else {
					// 其余时段
					tempCal.add(Calendar.HOUR, -4);
				}
				if (nowCal.after(tempCal)) {
					result.setResultCode(ResultCode.FAILED);
					message = "出差时间必须提前4小时工作时长";
				}
			} else if (mits <= 960) {
				// 小于16个小时 8h
				tempCal = dateService.setCalDate(tempCal, 1);
				if (nowCal.after(tempCal)) {
					result.setResultCode(ResultCode.FAILED);
					message = "出差时间必须提前8小时工作时长";
				}
			} else if (mits > 960) {
				// 小于40个小时 16h
				tempCal = dateService.setCalDate(tempCal, 2);
				if (nowCal.after(tempCal)) {
					result.setResultCode(ResultCode.FAILED);
					message = "出差时间必须提前16小时工作时长";
				}
			}
		}

		// 请求出差失败，返回失败原因
		if (result.getState() == ResultCode.FAILED.getCode()) {
			result.setData(message);
			return result;
		}

		// 计算出差时长
		int days = mits / 480;
		int minutes = mits % 480;
		int hours = minutes / 60;
		minutes = minutes % 60;

		leave.setDays(days);
		leave.setHours(hours);
		leave.setMinutes(minutes);

		// 2.审批流程，员工 -> 主管 -> 总监 -> 总经理
		if (hasRole(roles, "技术总监") || dept == null) { // 总监出差，申请提交给总经理

			leave.setStatus(3);
			leave.setBossId(Consts.managerId);
			leave.setCurrentId(Consts.managerId);

		} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) { // 主管出差，申请提交给总监

			if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) { // 是否技术部的

				leave.setStatus(2);
				leave.setDirectId(Consts.directorId);
				leave.setCurrentId(Consts.directorId);
				leave.setBossId(Consts.managerId);

			} else { // 非技术部

				leave.setStatus(2);
				leave.setDirectId(Consts.managerId);
				leave.setBossId(Consts.managerId);
				leave.setCurrentId(Consts.managerId);

			}

		} else { // 普通员工出差，申请提交到部门主管审核

			leave.setStatus(1);
			leave.setMgrId(dept.getMgrId());
			leave.setCurrentId(dept.getMgrId());
			leave.setBossId(Consts.managerId);

		}

		return result;
	}

	/**
	 * @Description: 注销出差处理审批流程，设置相关的审批人
	 * @param leave
	 *            注销后的出差信息
	 * @param mits
	 *            重新计算后的出差时长
	 * @param dept
	 *            部门
	 * @param roles
	 *            职位
	 * @author mlsong
	 * @date 2017年8月8日 下午3:26:13
	 */
	public static void cancelBusiness(LeaveCancel leave, int mits, Dept dept, List<Role> roles) {
		try {
			// 计算时长
			int days = mits / 480;
			int minutes = mits % 480;
			int hours = minutes / 60;
			minutes = minutes % 60;

			leave.setDays(days);
			leave.setHours(hours);
			leave.setMinutes(minutes);

			// 审批流程，员工 -> 主管 -> 总监 -> 总经理
			if (hasRole(roles, "技术总监") || dept == null) { // 技术总监或者总监

				leave.setStatus(3);
				leave.setBossId(Consts.managerId); // 总经理的id必须为1
				leave.setCurrentId(Consts.managerId);
			} else if (hasRole(roles, "部门主管") || OtherUtil.isAsDeptMgr(leave.getProposer())) {// 主管

				// 是否技术部的
				if (dept != null && dept.getPid().equals(new Integer(Consts.techDeptId))) {
					leave.setStatus(2); // 1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
					leave.setDirectId(Consts.directorId); // 总监的id目前定为12
					leave.setCurrentId(Consts.directorId);
					leave.setBossId(Consts.managerId);

				} else {// 非技术部主管直接总经理审核
					leave.setStatus(2);
					leave.setDirectId(Consts.managerId);
					leave.setBossId(Consts.managerId);
					leave.setCurrentId(Consts.managerId);
				}
			} else { // 普通员工
				leave.setStatus(1);
				leave.setMgrId(dept.getMgrId()); // 主管审核
				leave.setCurrentId(dept.getMgrId());
				leave.setBossId(Consts.managerId); // 总经理审核

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
