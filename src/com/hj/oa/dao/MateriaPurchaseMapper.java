package com.hj.oa.dao;

import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MateriaPurchaseMapper {
    long countByExample(MateriaPurchaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MateriaPurchase record);

    int insertSelective(MateriaPurchase record);

    List<MateriaPurchase> selectByExample(MateriaPurchaseExample example);

    MateriaPurchase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MateriaPurchase record, @Param("example") MateriaPurchaseExample example);

    int updateByExample(@Param("record") MateriaPurchase record, @Param("example") MateriaPurchaseExample example);

    int updateByPrimaryKeySelective(MateriaPurchase record);

    int updateByPrimaryKey(MateriaPurchase record);
}