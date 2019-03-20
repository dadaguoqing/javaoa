package com.hj.oa.dao;

import com.hj.oa.bean.ExternalApplyDetail;
import com.hj.oa.bean.ExternalApplyDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExternalApplyDetailMapper {
    long countByExample(ExternalApplyDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExternalApplyDetail record);

    int insertSelective(ExternalApplyDetail record);

    List<ExternalApplyDetail> selectByExample(ExternalApplyDetailExample example);

    ExternalApplyDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExternalApplyDetail record, @Param("example") ExternalApplyDetailExample example);

    int updateByExample(@Param("record") ExternalApplyDetail record, @Param("example") ExternalApplyDetailExample example);

    int updateByPrimaryKeySelective(ExternalApplyDetail record);

    int updateByPrimaryKey(ExternalApplyDetail record);
}