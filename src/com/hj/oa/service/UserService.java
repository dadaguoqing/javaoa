package com.hj.oa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.OutAccessCode;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.dao.UserMapper;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;

@Service
public class UserService {
	
	public static User zjl = null;//总经理
	public static User xzzg = null;//行政主管
	public static User jszj = null;//技术总监

	@Autowired
	UserMapper userMapper;

	public void reset() {
		try {
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat f3 = new SimpleDateFormat("yyyy.MM");
			SimpleDateFormat f4 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat f5 = new SimpleDateFormat("yyyy年MM月");

			List<User> users = userMapper.findAll();

			for (User u : users) {
				String ed = u.getEntryDate();
				if (ed.contains("-")) {
					Date d = f1.parse(ed);
					ed = f4.format(d);
				} else if (ed.contains("/")) {
					Date d = f2.parse(ed);
					ed = f4.format(d);
				}
				u.setEntryDate(ed);
				
				String bd = u.getBirthday();
				if (bd.contains("-")) {
					Date d = f1.parse(bd);
					bd = f4.format(d);
				} else if (bd.contains("/")) {
					Date d = f2.parse(bd);
					bd = f4.format(d);
				}
				u.setBirthday(bd);
				
				String gd = u.getGrdMonth();
				if(gd.contains(".")){
					Date d = f3.parse(gd);
					gd = f5.format(d);
				}
				u.setGrdMonth(gd);
				
				userMapper.updateUser2(u);
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findUserForLogin(String code, String password) {
		User user = userMapper.findUserByCode(code);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	public User findById(int id) {
		return userMapper.findById(id);
	}
	
	public List<User> findByDeptId(int id) {
		return userMapper.findByDeptId(id);
	}
	
	public User findUserByCode(String code){
		return userMapper.findUserByCode(code);
	}
	
	public List<User> findUserByName(String name){
		return userMapper.findUserByName(name);
	}
	
	public List<User> findUserByRole(String role){
		return userMapper.findUserByRole(role);
	}
	
	public List<User> findByDept(int deptId){
		return userMapper.findByDept(deptId);
	}
	
	public List<User> findByDeptStatus(int deptId){
		return userMapper.findByDeptStatus(deptId);
	}
	
	/**
	 * 查询所有上级
	 * 
	 * @param empId
	 * @param roles
	 * @return
	 */
	public List<User> findSuperiors(User user, List<Role> roles) {
		List<User> users = new ArrayList<User>();
		
		if(zjl ==null){
			zjl = this.userMapper.findById(Consts.managerId);
			xzzg = this.userMapper.findById(Consts.mgrOfXz);
			jszj = this.userMapper.findById(Consts.directorId);
		}
		 
		
		if (RoleUtil.hasRole(roles, "总经理")) {
			users.add(xzzg);
			return users;
		}else if(RoleUtil.hasRole(roles, "技术总监")) {
			users.add(zjl);
			users.add(xzzg);
		}else if(RoleUtil.hasRole(roles, "部门主管")) {
			users.add(zjl);
			if(user.getId() != Consts.mgrOfXz){
				users.add(xzzg);
			}
			if(user.getDeptId() !=null && user.getDeptId() == Consts.techDeptId){//如果是技术部门
				users.add(jszj);
			}
		}else{
			users.add(zjl);
			users.add(xzzg);
			
			Integer deptId = user.getDeptId();
			if(null!=deptId){
				if(deptId == Consts.techDeptId){//如果是技术部门
					users.add(jszj);
				}
				Dept dept = this.userMapper.findDeptById(deptId);
				Integer mgrId = dept.getMgrId();
				if(mgrId!=null && mgrId!=Consts.managerId && mgrId!=Consts.directorId && mgrId!=Consts.mgrOfXz ){
					User u = this.userMapper.findById(mgrId);
					users.add(u);
				}
			}
		}
		
		return users;
	}
	
	/**
	 * 查询直接下属
	 * 
	 * @param empId
	 * @param roles
	 * @return
	 */
	public List<User> findDirectSubordinates(int empId, List<Role> roles) {
		
		if (RoleUtil.hasRole(roles, "总经理")) {
			//自己部门的所有人
			List<User> list1 =  userMapper.findDirectSubordinatesForM(empId);
			//所有非技术部的主管，除了他自己
			List<User> list2 = userMapper.findDirectSubordinatesForD(Consts.nonTechDeptId, empId);
			
			//技术总监
			User d = userMapper.findById(Consts.directorId);
			
			list1.addAll(list2);
			list1.add(d);
			return list1;
		}

		if (RoleUtil.hasRole(roles, "技术总监")) {
			
			//自己部门的所有人、
			List<User> list1 =  userMapper.findDirectSubordinatesForM(empId);
			//所有技术部的主管，除了他自己
			List<User> list2 = userMapper.findDirectSubordinatesForD(Consts.techDeptId, empId);
			
			list1.addAll(list2);
			User du = null;
			
			User dg = null;
			int count = 0;
			for(User us : list1){
				
				if(us.getName().equals("冀玉东")){
					dg = us;
					count++;
				}
				
				if(us.getId() == Consts.managerId){
					du = us;
				}
			}
			
			if( null != du){
				list1.remove(du);
			}
			if(count > 1){
				list1.remove(dg);
			}
			
			for(Integer eid : Consts.techDeptMgr){
				User user = this.userMapper.findById(eid);
				list1.add(user);
			}
			
			//Set<User> set = new HashSet(User);
			
			return list1;
		}
		
		if (RoleUtil.hasRole(roles, "部门主管")) {
			List<User> subs = userMapper.findDirectSubordinatesForM(empId);
			
			List<User> rms = new ArrayList<User>();
			for(User u : subs){
				if(OtherUtil.isAsDeptMgr(u.getId())){
					rms.add(u);
				}
			}
			for(User u : rms){
				subs.remove(u);
			}
			return subs;//userMapper.findDirectSubordinatesForM(empId);
		}

		//返回空
		return new ArrayList<User>();
	}

	/**
	 * 查询所有下属
	 * 
	 * @param empId
	 * @param roles
	 * @return
	 */
	public List<User> findSubordinates(int empId, List<Role> roles) {
		if (RoleUtil.hasRole(roles, "总经理")) {
			List<User> list = userMapper.findAll();
			User du = null;
			for(User us : list){
				if(us.getId() == Consts.managerId){
					du = us;
					break;
				}
			}
			if( null != du){
				list.remove(du);
			}
			return list;
		}

		if (RoleUtil.hasRole(roles, "技术总监")) {
			List<User> list = userMapper.findSubordinatesForD(Consts.techDeptId, empId);
			
			List<User> du = new ArrayList<User>();
			for(User us : list){
				if(us.getId() == Consts.managerId || us.getId() == Consts.directorId){
					du.add(us);
				}
			}
			
			for(User us : du){
				list.remove(us);
			}
			
			return list;
		}
		
		if (RoleUtil.hasRole(roles, "部门主管")) {
			return userMapper.findDirectSubordinatesForM(empId);
		}

		return new ArrayList<User>();
	}

	public Dept findDeptById(Integer id) {
		return userMapper.findDeptById(id);
	}
	
	public List<Dept> findDeptsByMgr(int mgrId){
		return userMapper.findDeptsByMgr(mgrId);
	}

	public List<Dept> findAllDept() {
		return userMapper.findAllDept();
	}
	
	public List<Dept> findAllSubDept(){
		return userMapper.findAllSubDept();
	}

	public List<User> findAllUsers() {
		return userMapper.findAll();
	}
	
	public List<User> findAllUsersStatus() {
		return userMapper.findAllUsersStatus();
	}
	
	public List<User> findAllUsersStatusOrderDept(){
		return userMapper.findAllUsersStatusOrderDept();
	}
	
	public List<User> findAllUsersByDeptOrder(){
		List<User> list = userMapper.findAllByDeptOrder();
		/*
		List<User> users = new ArrayList<User>();
		
		User u1 = null;
		User u2 = null;
		*/
		return list;
	}

	/**
	 * 带考勤信息的查询
	 * 
	 * @param n
	 * @return
	 */
	public List<User> findAllWithKQ(String n) {
		return userMapper.findAllWithKQ(n);
	}
	
	public List<User> findNoticer(Integer status){
		return userMapper.findNoticer(status);
	}
	public void deletePhoto(int empId){
		 userMapper.deletePhoto(empId);
	}
	
	public void resetNoticer(int[] empIds){
		this.userMapper.resetAllNoticer();
		int len = empIds.length;
		for(int i=0; i<len; i++){
			this.userMapper.updateNoticer(1, empIds[i]);
		}
	}
	
	public List<Integer> findUserOnLeave(String n){
		return userMapper.findUserOnLeave(n);
	}

	public void addUser(User user) {
		this.userMapper.addUser(user);
	}

	public void changePassword(User user) {
		this.userMapper.changePassword(user);
	}

	public void updateUser(User user) {
		this.userMapper.updateUser(user);
	}
	
	public void updateUser2ForExcelImport(List<User> users){
		for(User u : users){
			this.userMapper.updateUser2(u);
		}
	}
	
	public void updateUserDept(List<User> users){
		for(User u : users){
			this.userMapper.updateUserDept(u);
		}
	}
	
	public void updatePhoto(User user){
		this.userMapper.updatePhoto(user);
	}
	
	public void updatePdf(User user){
		this.userMapper.updatePdf(user);
	}

	
	public void delUser(int id){
		this.userMapper.delUser(id);
	}
	
	public void delOrderDinner(int id){
		this.userMapper.delOrderDinner(id);
	}
	
	//外网访问
	public OutAccessCode findOutAccessCodeByCode(String accessCode){
		return userMapper.findOutAccessCodeByCode(accessCode);
	}
	
	public List<OutAccessCode> findAllOutAccessCode(){
		return userMapper.findAllOutAccessCode();
	}
	
	public void addOutAccessCode(OutAccessCode outAccessCode){
		userMapper.addOutAccessCode(outAccessCode);
		//添加外网角色
		List<String> roles = this.userMapper.findOutAccessRoleByCode(outAccessCode.getEmpCode());
		if(roles.isEmpty()){
			userMapper.addOutAccessRole(outAccessCode.getEmpCode(), "outlogin");
		}
	}
	
	
	
	public void deleteOutAccessCode(int id){
		userMapper.deleteOutAccessCode(id);
	}
	
	public Integer findAdminIdByDeptId(int id){
		return userMapper.findAdminIdByDeptId(id);
	}
	
	public void reGenerateOutAccessCode(OutAccessCode outAccessCode){
		userMapper.reGenerateOutAccessCode(outAccessCode);
	}
	
	
	/**
	 * 查询审批人员id
	 * @return
	 */
	public ApproveStaff findApproveIdByUserId(Integer userId){
		User user = this.userMapper.findById(userId);
		ApproveStaff as = new ApproveStaff();
		Integer deptDirector = this.userMapper.findDeptDirectorId(user.getDeptId());
		Integer deptSupremo = this.userMapper.findDeptSupremo(user.getDeptId());
		Integer treasurer = this.userMapper.findDeptDirectorId(5);
		as.setDeptDirector(deptDirector);
		as.setDeptSupremo(deptSupremo);
		as.setManager(Consts.managerId);
		as.setTreasurer(treasurer);
		return as;
	}
	
	public List<User> findUsersByEntryDate(String date) {
		return this.userMapper.findUsersByEntryDate(date);
	}
	
	/**
	 * @Title: getApproveStatus   
	 * @Description: 主管、总监、总经理审批流程 
	 * @return: void      
	 * @throws
	 */
	public Map<String, Integer> getApproveStatus(Integer userId, Integer status) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int currentId = 0;
		ApproveStaff as = this.findApproveIdByUserId(userId);
		int dir = as.getDeptDirector();
		int sup = as.getDeptSupremo();
		int man = as.getManager();
		switch (status) {
			// 初始化
			case 0: 
				currentId = dir;
				status = 1;
				if(userId == dir || dir == sup || dir == man) {
					status++;
					currentId = sup;
				} 
				if (userId == sup || (dir == sup && dir == man) || 
						(userId == dir && sup == man)) {
					status++;
					currentId = man;
				}
				if(userId == man) {
					status++;
					currentId = 0;
				}
				break;
		}
		map.put("currentId", currentId);
		map.put("status", status);
		return map;
	}
	
	
}
