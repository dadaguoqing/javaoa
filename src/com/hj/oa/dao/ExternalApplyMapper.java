package com.hj.oa.dao;

import com.hj.oa.bean.ExternalApply;
import com.hj.oa.bean.ExternalApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExternalApplyMapper {
    long countByExample(ExternalApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExternalApply record);

    int insertSelective(ExternalApply record);

    List<ExternalApply> selectByExample(ExternalApplyExample example);

    ExternalApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExternalApply record, @Param("example") ExternalApplyExample example);

    int updateByExample(@Param("record") ExternalApply record, @Param("example") ExternalApplyExample example);

    int updateByPrimaryKeySelective(ExternalApply record);

    int updateByPrimaryKey(ExternalApply record);
    
    List<String> findApproveRecord(Integer id, String str);
    
}