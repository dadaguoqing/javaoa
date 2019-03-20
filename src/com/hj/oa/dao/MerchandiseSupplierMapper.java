package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseSupplier;
import com.hj.oa.bean.MerchandiseSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseSupplierMapper {
    long countByExample(MerchandiseSupplierExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseSupplier record);

    int insertSelective(MerchandiseSupplier record);

    List<MerchandiseSupplier> selectByExample(MerchandiseSupplierExample example);

    MerchandiseSupplier selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseSupplier record, @Param("example") MerchandiseSupplierExample example);

    int updateByExample(@Param("record") MerchandiseSupplier record, @Param("example") MerchandiseSupplierExample example);

    int updateByPrimaryKeySelective(MerchandiseSupplier record);

    int updateByPrimaryKey(MerchandiseSupplier record);
}