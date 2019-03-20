package com.hj.oa.dao;

import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.MateriaPurchaseDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MateriaPurchaseDetailMapper {
    long countByExample(MateriaPurchaseDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MateriaPurchaseDetail record);

    int insertSelective(MateriaPurchaseDetail record);

    List<MateriaPurchaseDetail> selectByExample(MateriaPurchaseDetailExample example);

    MateriaPurchaseDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MateriaPurchaseDetail record, @Param("example") MateriaPurchaseDetailExample example);

    int updateByExample(@Param("record") MateriaPurchaseDetail record, @Param("example") MateriaPurchaseDetailExample example);

    int updateByPrimaryKeySelective(MateriaPurchaseDetail record);

    int updateByPrimaryKey(MateriaPurchaseDetail record);
}