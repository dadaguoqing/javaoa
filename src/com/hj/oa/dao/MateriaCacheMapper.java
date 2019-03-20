package com.hj.oa.dao;

import com.hj.oa.bean.MateriaCache;
import com.hj.oa.bean.MateriaCacheExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MateriaCacheMapper {
    long countByExample(MateriaCacheExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MateriaCache record);

    int insertSelective(MateriaCache record);

    List<MateriaCache> selectByExample(MateriaCacheExample example);

    MateriaCache selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MateriaCache record, @Param("example") MateriaCacheExample example);

    int updateByExample(@Param("record") MateriaCache record, @Param("example") MateriaCacheExample example);

    int updateByPrimaryKeySelective(MateriaCache record);

    int updateByPrimaryKey(MateriaCache record);
}