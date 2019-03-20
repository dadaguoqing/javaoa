package com.hj.oa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.SealApprove;

public interface SealApproveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SealApprove record);

    int insertSelective(SealApprove record);

    SealApprove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SealApprove record);

    int updateByPrimaryKey(SealApprove record);
    
    List<SealApprove> findApproveByNumber(String number);
    
    List<Integer> findMyDepts(Integer id);
    
    SealApprove findApproveByNumberAndStatus(@Param(value = "number") String number,@Param(value = "status") Integer status);
}