package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.Notice;
import com.hj.oa.bean.Rule;
import com.hj.oa.dao.NoticeMapper;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	public List<Rule> findAllRules(){
		
		return noticeMapper.findAllRules();
	}
	
	public Rule findRuleById(int id){
		
		return noticeMapper.findRuleById(id);
	}
	
	public void addRule(Rule rule){
		noticeMapper.addRule(rule);
	}
	
	public void deleteRule(int id){
		noticeMapper.deleteRule(id);
	}

	public List<Notice> findByUser(int empId, int status){
		return noticeMapper.findByUser(empId, status);
	}
	
	public List<Notice> findAll(){
		return noticeMapper.findAll();
	}
	
	public List<Notice> findByCon(int status, Integer empId, String begin, String end, String type){
		return noticeMapper.findByCon(status, empId, begin, end, type);
	}
	
	public List<Notice> findAllCon(String begin, String end){
		return noticeMapper.findAllCon(begin, end);
	}
	
	public Notice findById(int id){
		return noticeMapper.findById(id);
	}
	
	public void setReaded(int empId, int noticeId){
		noticeMapper.updateStatus(empId, noticeId);
	}
	
	public void addNotice(Notice notice, List<Integer> empIds){
		noticeMapper.addNotice(notice);
		noticeMapper.addEmpNotice(empIds, notice.getId());
	}
	
	public void addEmpNotice(Integer empId, Integer noticeId){
		noticeMapper.addEmpNotice2(empId, noticeId);
	}
	
	public void delNotice(int id){
		noticeMapper.delNotice(id);
		noticeMapper.delEmpNotice(id);
	}
}
