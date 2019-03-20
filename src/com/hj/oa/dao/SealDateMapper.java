package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.SealDate;

public interface SealDateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SealDate record);

    int insertSelective(SealDate record);

    SealDate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SealDate record);
    
    int updateById(Integer id);

    int updateByPrimaryKey(SealDate record);
    
    List<SealDate> selectByStatus();
    
    List<Integer> findWeekNums();
}