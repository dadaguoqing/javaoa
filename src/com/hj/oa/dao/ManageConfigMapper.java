package com.hj.oa.dao;

import com.hj.oa.bean.ManageConfig;
import com.hj.oa.bean.ManageConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ManageConfigMapper {
    long countByExample(ManageConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ManageConfig record);

    int insertSelective(ManageConfig record);

    List<ManageConfig> selectByExample(ManageConfigExample example);

    ManageConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ManageConfig record, @Param("example") ManageConfigExample example);

    int updateByExample(@Param("record") ManageConfig record, @Param("example") ManageConfigExample example);

    int updateByPrimaryKeySelective(ManageConfig record);

    int updateByPrimaryKey(ManageConfig record);
}