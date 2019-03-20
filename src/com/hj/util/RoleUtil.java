package com.hj.util;

import java.util.List;

import com.hj.oa.bean.Role;

/**
 * 
 * @author longlian
 *
 */
public class RoleUtil {

	public static boolean hasRole(List<Role> roles, String roleName){
		
		for(Role r : roles){
			if(roleName.equals(r.getName())){
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean hasRole(List<Role> roles, Integer roleId) {
		for(Role r : roles){
			if(roleId.equals(r.getId())){
				return true;
			}
		}
		return false;
	}
}
