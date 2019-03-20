package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseStock;
import com.hj.oa.bean.MerchandiseStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseStockMapper {
    long countByExample(MerchandiseStockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseStock record);

    int insertSelective(MerchandiseStock record);

    List<MerchandiseStock> selectByExample(MerchandiseStockExample example);

    MerchandiseStock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseStock record, @Param("example") MerchandiseStockExample example);

    int updateByExample(@Param("record") MerchandiseStock record, @Param("example") MerchandiseStockExample example);

    int updateByPrimaryKeySelective(MerchandiseStock record);

    int updateByPrimaryKey(MerchandiseStock record);
}