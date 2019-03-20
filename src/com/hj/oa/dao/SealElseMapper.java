package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.SealElse;

public interface SealElseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SealElse record);

    int insertSelective(SealElse record);

    SealElse selectByPrimaryKey(Integer id);
    
    List<SealElse> selectByType(String id);

    int updateByPrimaryKeySelective(SealElse record);

    int updateByPrimaryKey(SealElse record);
}