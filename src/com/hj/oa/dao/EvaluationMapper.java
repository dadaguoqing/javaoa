package com.hj.oa.dao;

import com.hj.oa.bean.Evaluation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public abstract interface EvaluationMapper
{
  public abstract List<Evaluation> getEmpEvaluation(@Param("empId") Integer paramInteger1, @Param("zgId") Integer paramInteger2, @Param("tabId") Integer paramInteger3);
  
  public abstract List<Evaluation> getAllEvaluation();
  
  public abstract void saveEmpEvaluation(@Param("empEvals") List<Evaluation> paramList);
  
  public abstract void saveEmpEvaluationByZG(@Param("empEvals") List<Evaluation> paramList);
  
  public abstract void saveEmpEvaluationSum(Evaluation paramEvaluation);
  
  public abstract List<Evaluation> findAllEmpSumEavluation(@Param("zgId") Integer paramInteger1, @Param("tabId") Integer paramInteger2);
  
  public abstract List<Evaluation> findEmpSumEavluationByDept(@Param("deptId") Integer paramInteger1, @Param("zgId") Integer paramInteger2, @Param("tabId") Integer paramInteger3);
  
  public abstract Evaluation getEmpEvaluationSum(@Param("empId") Integer paramInteger1, @Param("zgId") Integer paramInteger2, @Param("tabId") Integer paramInteger3);
}
