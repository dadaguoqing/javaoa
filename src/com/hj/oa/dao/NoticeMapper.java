package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Notice;
import com.hj.oa.bean.Rule;

public interface NoticeMapper {
	
	public List<Rule> findAllRules();
	
	public Rule findRuleById(int id);
	
	public void addRule(Rule rule);
	
	public void deleteRule(int id);

	public List<Notice> findByUser(@Param("empId") int empId, @Param("status") int status);
	
	public List<Notice> findAll();
	
	public List<Notice> findByCon(@Param("status") int status, @Param("empId") Integer empId,
			@Param("begin") String begin, @Param("end") String end, @Param("type") String type);
	
	public List<Notice> findAllCon(@Param("begin") String begin, @Param("end") String end);
	
	public Notice findById(int id);
	
	public void updateStatus(@Param("empId") int empId, @Param("noticeId") int noticeId);
	
	public void addNotice(Notice notice);
	
	public void addEmpNotice(@Param("empIds") List<Integer> empIds, @Param("noticeId") int noticeId);
	
	public void addEmpNotice2(@Param("empId") Integer empId, @Param("noticeId") int noticeId);
	
	public void delNotice(int id);
	
	public void delEmpNotice(int id);
}
