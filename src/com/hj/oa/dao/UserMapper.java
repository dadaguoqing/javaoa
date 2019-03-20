package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Dept;
import com.hj.oa.bean.OutAccessCode;
import com.hj.oa.bean.User;

public interface UserMapper {

	public User findUserByCode(String code);

	public List<User> findUserByName(String name);

	public List<User> findUserByRole(String role);

	public List<User> findAll();

	public List<User> findAllUsersStatus();

	public List<User> findAllUsersStatusOrderDept();

	public List<User> findByDept(int deptId);

	public List<User> findByDeptStatus(int deptId);

	public List<User> findAllByDeptOrder();

	public List<User> findAllWithKQ(String n);

	public List<Integer> findUserOnLeave(String n);

	public User findById(int id);

	public List<User> findByDeptId(int id);

	public void deletePhoto(int empId);

	public List<User> findNoticer(@Param("status") Integer status);

	public void updateNoticer(@Param("status") int status, @Param("empId") int empId);

	public void resetAllNoticer();
	// public User findByCode(String code);

	public Dept findDeptById(Integer id);

	public List<Dept> findDeptsByMgr(int mgrId);

	public List<Dept> findAllDept();

	public List<Dept> findAllSubDept();

	public void addUser(User user);

	public void changePassword(User user);

	public void updateUser(User user);

	public void updatePhoto(User user);

	public void updatePdf(User user);

	public void updateUser2(User user);

	public void updateUserDept(User user);

	public void delUser(int id);

	public void delOrderDinner(int id);

	// 查询下属
	public List<User> findDirectSubordinatesForM(int mgrId);

	public List<User> findDirectSubordinatesForD(@Param("deptId") int deptId, @Param("empId") int empId);

	public List<User> findSubordinatesForD(@Param("deptId") int deptId, @Param("empId") int empId);

	// 外网访问
	public OutAccessCode findOutAccessCodeByCode(String accessCode);// 根据访问code查询

	public List<OutAccessCode> findAllOutAccessCode();// 查询所有

	public void addOutAccessCode(OutAccessCode outAccessCode);// 添加

	public List<String> findOutAccessRoleByCode(@Param("empCode") String empCode);

	public void addOutAccessRole(@Param("empCode") String empCode, @Param("role") String roleName);// 添加

	public void deleteOutAccessCode(int id);// 删除

	public void reGenerateOutAccessCode(OutAccessCode outAccessCode);// 重新生成

	public Integer findAdminIdByDeptId(Integer deptId);// 重新生成
	
	public Integer findDeptDirectorId(Integer deptId); 
	
	public Integer findDeptSupremo(Integer deptId);
	
	public List<User> findUsersByEntryDate(@Param("date") String date);
}
