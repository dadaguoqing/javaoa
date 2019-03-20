package com.hj.oa.dao;

import com.hj.oa.bean.ExternalDeal;
import com.hj.oa.bean.ExternalDealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExternalDealMapper {
    long countByExample(ExternalDealExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExternalDeal record);

    int insertSelective(ExternalDeal record);

    List<ExternalDeal> selectByExample(ExternalDealExample example);

    ExternalDeal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExternalDeal record, @Param("example") ExternalDealExample example);

    int updateByExample(@Param("record") ExternalDeal record, @Param("example") ExternalDealExample example);

    int updateByPrimaryKeySelective(ExternalDeal record);

    int updateByPrimaryKey(ExternalDeal record);
}