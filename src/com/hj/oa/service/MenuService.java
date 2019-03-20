package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.commons.DbStatus;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.MenuPermission;
import com.hj.oa.bean.MenuPermissionExample;
import com.hj.oa.dao.MenuMapper;
import com.hj.oa.dao.MenuPermissionMapper;

@Service
public class MenuService {
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private MenuPermissionMapper menuPermissionMapper;
	
	public List<Menu> findDlMenu(int empId, int ownerId){
		return menuMapper.findDlMenu(empId, ownerId);
	}
	
	public List<Menu> findByEmp(int id){
		return menuMapper.findByEmp(id);
	}
	
	public List<Menu> findAll(){
		return menuMapper.findAll();
	}
	
	public void addMenu(Menu menu){
		menuMapper.addMenu(menu);
	}
	
	public List<Menu> findMenu(Integer id){
		return menuMapper.findMenu(id);
	}
	
	public Menu findMenuById(Integer id){
		return menuMapper.findMenuById(id);
	}
	
	public List<Integer> findMenuIdByRole(int roleId){
		return menuMapper.findMenuIdByRole(roleId);
	}
	
	public List<MenuPermission> findMenuPerById(Integer menuId) {
		MenuPermissionExample ex = new MenuPermissionExample();
		ex.createCriteria().andMenuIdEqualTo(menuId).andStatusEqualTo(DbStatus.ACTIVE.getType());
		return this.menuPermissionMapper.selectByExample(ex);
	}
	
	public List<MenuPermission> findAllPermissions() {
		MenuPermissionExample ex = new MenuPermissionExample();
		ex.createCriteria().andStatusEqualTo(DbStatus.ACTIVE.getType());
		return this.menuPermissionMapper.selectByExample(ex);
	}
	
	public void insertMenuPer(MenuPermission mp) {
		this.menuPermissionMapper.insertSelective(mp);
	}
	
	public void updatePermission(Integer id) {
		MenuPermission mp = new MenuPermission();
		mp.setId(id);
		mp.setStatus(DbStatus.FREEZE.getType());
		this.menuPermissionMapper.updateByPrimaryKeySelective(mp);
	}
	
}
