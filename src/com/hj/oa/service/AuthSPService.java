package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.AuthSP;
import com.hj.oa.dao.AuthSPMapper;
import com.hj.oa.dao.RoleMapper;

@Service
public class AuthSPService {
	
	@Autowired
	private AuthSPMapper authSPMapper;
	
	public void add(AuthSP sp){
		authSPMapper.add(sp);
	}
	
	public AuthSP findById(int id){
		return authSPMapper.findById(id);
	}
	
	public List<AuthSP> findMySP(){
		return authSPMapper.findMySP();
	}
	
	public List<AuthSP> findMine(int empId){
		return authSPMapper.findMine(empId);
	}
	
	public void update(AuthSP sp, RoleMapper roleMapper){
		authSPMapper.update(sp);
		
		if(sp.getStatus() != 1){//审核不通过
			return;
		}
		if(sp.getType() == 0){//设置用户角色
			int empId = sp.getTid();
			String rs = sp.getTids();
			String[] ss = rs.split(",");
			Integer[] rids = new Integer[ss.length];
			for(int i=0; i<ss.length; i++){
				String s = ss[i];
				rids[i] = Integer.parseInt(s);
			}
			roleMapper.deleteEmpRoleByEmpId(empId);
			roleMapper.setEmpRoles(empId, rids);
		}else if(sp.getType() == 1){//设置角色对应的用户
			int roleId = sp.getTid();
			String es = sp.getTids();
			String[] ss = es.split(",");
			Integer[] emps = new Integer[ss.length];
			for(int i=0; i<ss.length; i++){
				String s = ss[i];
				emps[i] = Integer.parseInt(s);
			}
			roleMapper.deleteEmpRoleByRoleId(roleId);
			roleMapper.setRoleByEmp(roleId, emps);
		}else if(sp.getType() == 2){//设置角色对应的菜单
			int roleId = sp.getTid();
			String ms = sp.getTids();
			String[] ss = ms.split(",");
			Integer[] menus = new Integer[ss.length];
			for(int i=0; i<ss.length; i++){
				String s = ss[i];
				menus[i] = Integer.parseInt(s);
			}
			
			roleMapper.deleteRoleMenuByRoleId(roleId);
			roleMapper.setMenuByRole(roleId, menus);
		}
	}
}
