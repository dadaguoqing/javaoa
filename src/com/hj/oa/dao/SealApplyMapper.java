package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.SealApply;
import com.hj.oa.bean.SealApplyDetail;

public interface SealApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SealApply record);

    int insertSelective(SealApply record);

    SealApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SealApply record);

    int updateByPrimaryKey(SealApply record);
    
    List<SealApply> selectByEmpId(Integer id);
    
    List<SealApply> selectByCurrentId(Integer id);
    
    List<SealApply> selectAllApply();
    
}