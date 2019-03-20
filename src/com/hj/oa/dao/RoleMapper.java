package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;

public interface RoleMapper {

	public List<Role> findAll();
	
	public Role findById(int id);
	
	public void addRole(Role role);
	
	public List<Integer> findEmpIdByRoleId(int roleId);
	
	public List<Role> findRolesByEmpId(int id);
	
	public void deleteEmpRoleByEmpId(int empId);
	
	public void deleteEmpRoleByRoleId(int roleId);
	
	public void deleteRoleMenuByRoleId(int roleId);
	
	public void setRoleByEmp(@Param("roleId")int roleId, @Param("emps")Integer[] emps);
	
	public void setEmpRoles(@Param("empId")int empId, @Param("roles")Integer[] roles);
	
	public void setMenuByRole(@Param("roleId")int roleId, @Param("menus")Integer[] menus);
	
	//代理
	
	//我代理了谁
	public List<User> findDailiByEmpId(int empId);
	
	public List<User> findWoDailiBieren(int empId);
	//我代理了谁
	//public User findWoDailiBieren(int empId);

	//谁代理了我
	public User findDailiByOwnerId(int ownerId);
	
	public List<Menu> findDailiMenus(int ownerId);
	
	public void deleteDailiByOwner(int ownerId);
	
	public void deleteEmpDailiMenuByOwner(int ownerId);

	public void addDaili(@Param("empId") int empId, @Param("ownerId") int ownerId);
	
	public void addEmpDaili(@Param("empId") int empId, @Param("menus") List<Integer> menus, @Param("ownerId") int ownerId);

	public List<Integer> findRoleIdByName(String roleName);

}
