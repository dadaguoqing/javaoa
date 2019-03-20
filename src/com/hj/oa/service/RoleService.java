package com.hj.oa.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.Menu;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.dao.RoleMapper;

@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	public List<Role> findAllRoles(){
		return roleMapper.findAll();
	}
	
	public Role findRoleById(int id){
		return roleMapper.findById(id);
	}
	
	public void addRole(Role role){
		roleMapper.addRole(role);
	}
	
	public List<Integer> findEmpIdByRoleId(int roleId){
		return roleMapper.findEmpIdByRoleId(roleId);
	}
	
	public List<Role> findRolesByEmpId(int id){
		List<Role> list = roleMapper.findRolesByEmpId(id);
		
		//特殊情况，id 64 程军 相当于部门主管
		if(id == 64){
			Role r = new Role();
			r.setId(4);
			r.setName("部门主管");
			list.add(r);
		}
		return list;
	}
	
	public void setEmpRoles(int empId, Integer[] roles){
		roleMapper.deleteEmpRoleByEmpId(empId);
		roleMapper.setEmpRoles(empId, roles);
	}
	
	public void setRoles(int roleId, Integer[] emps){
		roleMapper.deleteEmpRoleByRoleId(roleId);
		roleMapper.setRoleByEmp(roleId, emps);
	}
	
	public void setRoleMenu(int roleId, Integer[] menus){
		roleMapper.deleteRoleMenuByRoleId(roleId);
		roleMapper.setMenuByRole(roleId, menus);
	}
	
	//代理
	
	//我代理了谁
	public List<User> findDailiByEmpId(int empId){
		return roleMapper.findDailiByEmpId(empId);
	}

	//谁代理了我
	public User findDailiByOwnerId(int ownerId){
		return roleMapper.findDailiByOwnerId(ownerId);
	}
	
	public List<Menu> findDailiMenus(int ownerId){
		return roleMapper.findDailiMenus(ownerId);
	}
	
	public void deleteDaili(int ownerId){
		roleMapper.deleteDailiByOwner(ownerId);
		roleMapper.deleteEmpDailiMenuByOwner(ownerId);
	}
	
	public void addDaili(int empId, List<Integer> menus, int ownerId){
		roleMapper.addDaili(empId, ownerId);
		roleMapper.addEmpDaili(empId, menus, ownerId);
	}

	public Integer findRoleIdByName(String roleName) {
		List<Integer> roleIds = roleMapper.findRoleIdByName(roleName);
		if (roleIds != null && !roleIds.isEmpty()) {
			return roleIds.get(0);
		}
		return null;
	}
	
}
