package com.hj.oa.service;

import com.hj.oa.bean.Evaluation;
import com.hj.oa.dao.EvaluationMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService
{
  @Autowired
  private EvaluationMapper evMapper;
  
  public List<Evaluation> getEmpEvaluation(Integer empId, Integer zgId, Integer tabId)
  {
    return this.evMapper.getEmpEvaluation(empId, zgId, tabId);
  }
  
  public List<Evaluation> getAllEvaluation()
  {
    return this.evMapper.getAllEvaluation();
  }
  
  public void saveEmpEvaluation(List<Evaluation> list)
  {
    this.evMapper.saveEmpEvaluation(list);
  }
  
  public void saveEmpEvaluationSum(Evaluation ev)
  {
    this.evMapper.saveEmpEvaluationSum(ev);
  }
  
  public void saveEvaluation(List<Evaluation> list, Evaluation ev)
  {
    this.evMapper.saveEmpEvaluationSum(ev);
    this.evMapper.saveEmpEvaluation(list);
  }
  
  public List<Evaluation> findAllEmpSumEavluation(@Param("zgId") Integer zgId, @Param("tabId") Integer tabId)
  {
    return this.evMapper.findAllEmpSumEavluation(zgId, tabId);
  }
  
  public List<Evaluation> findEmpSumEavluationByDept(Integer deptId, Integer zgId, Integer tabId)
  {
    return this.evMapper.findEmpSumEavluationByDept(deptId, zgId, tabId);
  }
  
  public Evaluation getEmpEvaluationSum(Integer empId, Integer zgId, Integer tabId)
  {
    return this.evMapper.getEmpEvaluationSum(empId, zgId, tabId);
  }
}
