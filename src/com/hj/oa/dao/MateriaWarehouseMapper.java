package com.hj.oa.dao;

import com.hj.oa.bean.MateriaWarehouse;
import com.hj.oa.bean.MateriaWarehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MateriaWarehouseMapper {
    long countByExample(MateriaWarehouseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MateriaWarehouse record);

    int insertSelective(MateriaWarehouse record);

    List<MateriaWarehouse> selectByExample(MateriaWarehouseExample example);

    MateriaWarehouse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MateriaWarehouse record, @Param("example") MateriaWarehouseExample example);

    int updateByExample(@Param("record") MateriaWarehouse record, @Param("example") MateriaWarehouseExample example);

    int updateByPrimaryKeySelective(MateriaWarehouse record);

    int updateByPrimaryKey(MateriaWarehouse record);
}