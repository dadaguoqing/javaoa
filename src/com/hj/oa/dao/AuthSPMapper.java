package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.AuthSP;

public interface AuthSPMapper {
	
	public void add(AuthSP sp);
	
	public AuthSP findById(int id);
	
	public List<AuthSP> findMySP();
	
	public List<AuthSP> findMine(int id);
	
	public void update(AuthSP sp);
}
