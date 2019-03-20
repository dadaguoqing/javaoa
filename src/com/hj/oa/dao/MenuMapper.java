package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Menu;

public interface MenuMapper {
	
	public List<Menu> findByEmp(int id);
	
	public List<Menu> findAll();

	public List<Menu> findMenu(@Param("id") Integer id);
	
	public Menu findMenuById(@Param("id") Integer id);
	
	public void addMenu(Menu menu);
	
	public List<Integer> findMenuIdByRole(int roleId);
	
	public List<Menu> findDlMenu(@Param("empId") int empId, @Param("ownerId") int ownerId);
}
