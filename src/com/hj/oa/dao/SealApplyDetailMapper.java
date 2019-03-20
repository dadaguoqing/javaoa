package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.SealApplyDetail;

public interface SealApplyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SealApplyDetail record);

    int insertSelective(SealApplyDetail record);

    SealApplyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SealApplyDetail record);

    int updateByPrimaryKey(SealApplyDetail record);
    
    List<SealApplyDetail> selectByNumber(String number);
    
    List<SealApplyDetail> selectByDealStatus(Integer status);
}