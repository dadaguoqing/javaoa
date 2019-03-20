package com.hj.oa.dao;

import com.hj.oa.bean.MateriaMould;
import com.hj.oa.bean.MateriaMouldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MateriaMouldMapper {
    long countByExample(MateriaMouldExample example);

    int deleteByPrimaryKey(String type);

    int insert(MateriaMould record);

    int insertSelective(MateriaMould record);

    List<MateriaMould> selectByExample(MateriaMouldExample example);

    MateriaMould selectByPrimaryKey(String type);

    int updateByExampleSelective(@Param("record") MateriaMould record, @Param("example") MateriaMouldExample example);

    int updateByExample(@Param("record") MateriaMould record, @Param("example") MateriaMouldExample example);

    int updateByPrimaryKeySelective(MateriaMould record);

    int updateByPrimaryKey(MateriaMould record);
}