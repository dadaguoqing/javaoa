package com.hj.oa.dao;

import com.hj.oa.bean.ApproveBean;
import com.hj.oa.bean.ApproveBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApproveBeanMapper {
    long countByExample(ApproveBeanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApproveBean record);

    int insertSelective(ApproveBean record);

    List<ApproveBean> selectByExample(ApproveBeanExample example);

    ApproveBean selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApproveBean record, @Param("example") ApproveBeanExample example);

    int updateByExample(@Param("record") ApproveBean record, @Param("example") ApproveBeanExample example);

    int updateByPrimaryKeySelective(ApproveBean record);

    int updateByPrimaryKey(ApproveBean record);
}