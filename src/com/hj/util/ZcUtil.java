package com.hj.util;

import java.util.List;

import com.hj.oa.Consts;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.bean.ZcPropCg;
import com.hj.oa.bean.ZcPropLy;
import com.hj.oa.bean.ZcPropWb;
import com.hj.oa.bean.ZcPropZy;

public class ZcUtil {
	
	public static void zccg(ZcPropCg ly,  Dept dept, List<Role> roles,Integer cwId){
		try{
			//注意这里财务以及采购人员都是写死的。需要修改。
			if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){ //技术部的人员
				ly.setDirectId(Consts.directorId); //总监的id目前定为12
				ly.setCurrentId(Consts.directorId);
				//ly.setCgId(3);
				ly.setCgId(42);
			}else if(dept!=null && dept.getPid().equals(new Integer(Consts.sellDeptId))){//营销中心
				ly.setDirectId(Consts.sellDirectorId);
				ly.setBossId(Consts.managerId);
				ly.setCgId(13);
			}else{//非技术部
				ly.setDirectId(Consts.managerId);
				ly.setCurrentId(Consts.managerId);
				ly.setCgId(13);
			}
			ly.setCaiwuId(cwId);
			ly.setBossId(Consts.managerId);
			ly.setStatus(1);//总监审批
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public static void zcly(ZcPropLy ly,  Dept dept, List<Role> roles){
		try{
			if(hasRole(roles,"技术总监") || dept == null ){//技术总监或者总监
				ly.setStatus(3);
				ly.setBossId(Consts.managerId); //总经理的id必须为1
				ly.setCurrentId(Consts.managerId); 
			}else if(hasRole(roles,"部门主管") || OtherUtil.isAsDeptMgr(ly.getProposer()) ){//主管
				ly.setStatus(2);   //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
				ly.setBossId(Consts.managerId);
				//是否技术部的
				if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
					ly.setDirectId(Consts.directorId); //总监的id目前定为12
					ly.setCurrentId(Consts.directorId);
				}else{//非技术部
					ly.setDirectId(Consts.managerId);
					ly.setCurrentId(Consts.managerId);
				}
			}else{//普通员工
				ly.setStatus(1);//主管审核
				ly.setMgrId(dept.getMgrId());
				ly.setCurrentId(dept.getMgrId());
				ly.setBossId(Consts.managerId);
				//是否技术部的
				if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
					ly.setDirectId(Consts.directorId); //总监的id目前定为12
				}else if(dept!=null && dept.getPid().equals(new Integer(Consts.sellDeptId))){//营销中心
					ly.setDirectId(Consts.sellDirectorId);
				}else{//非技术部
					ly.setDirectId(Consts.managerId);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void zczy(ZcPropZy zy,  Dept dept, Dept zyDept, User zyUser, List<Role> roles){
		try{
			
			if(hasRole(roles,"总经理")){//
				zy.setStatus(4);
				zy.setBossId(Consts.managerId); //总经理的id必须为1
				zy.setCurrentId(Consts.managerId); 
			}else if(hasRole(roles,"技术总监") || dept == null ){//技术总监或者总监
				zy.setStatus(4);
				zy.setBossId(Consts.managerId); //总经理的id必须为1
				zy.setCurrentId(Consts.managerId); 
			}else{//普通员工，或者主管
				
				zy.setStatus(1);//主管审核
				zy.setMgrId(dept.getMgrId());
				if(zyDept !=null){
					zy.setMgr2Id(zyDept.getMgrId());
				}else{
					if(zyUser.getId() != Consts.managerId){//如果不是总经理，就是技术总监
						zy.setMgr2Id(2);
					}else{
						zy.setMgr2Id(Consts.managerId);
					}
				}
				zy.setCurrentId(dept.getMgrId());
				zy.setBossId(Consts.managerId);
				//是否技术部的
				if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
					zy.setDirectId(Consts.directorId); //总监的id目前定为12
				}else if(dept!=null && dept.getPid().equals(new Integer(Consts.sellDeptId))){//营销中心
					zy.setDirectId(Consts.sellDirectorId);
				}else{//非技术部
					zy.setDirectId(Consts.managerId);
				}
				
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void zcwx(ZcPropWb wb,  Dept dept, List<Role> roles){
		try{
			
			if(hasRole(roles,"技术总监") || dept == null ){//技术总监或者总监
				wb.setStatus(3);
				wb.setBossId(Consts.managerId); //总经理的id必须为1
				wb.setCurrentId(Consts.managerId); 
			}else if(hasRole(roles,"部门主管") || OtherUtil.isAsDeptMgr(wb.getProposer()) ){//主管
				wb.setStatus(2);   //1-部门经理审核，2-总监审核，3-总经理审核，4，审核通过，-1-审核不通过
				wb.setBossId(Consts.managerId);
				//是否技术部的
				if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
					wb.setDirectId(Consts.directorId); //总监的id目前定为12
					wb.setCurrentId(Consts.directorId);
				}else{//非技术部
					wb.setDirectId(Consts.managerId);
					wb.setCurrentId(Consts.managerId);
				}
			}else{//普通员工
				wb.setStatus(1);//主管审核
				wb.setMgrId(dept.getMgrId());
				wb.setCurrentId(dept.getMgrId());
				wb.setBossId(Consts.managerId);
				//是否技术部的
				if(dept!=null && dept.getPid().equals(new Integer(Consts.techDeptId))){
					wb.setDirectId(Consts.directorId); //总监的id目前定为12
				}else if(dept!=null && dept.getPid().equals(new Integer(Consts.sellDeptId))){//营销中心
					wb.setDirectId(Consts.sellDirectorId);
				}else{//非技术部
					wb.setDirectId(Consts.managerId);
				}
				
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private static boolean hasRole(List<Role> roles, String roleName){
		return RoleUtil.hasRole(roles, roleName);
	}
}
