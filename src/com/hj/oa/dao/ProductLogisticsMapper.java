package com.hj.oa.dao;

import java.util.List;

import com.hj.oa.bean.ProductLogistics;

public interface ProductLogisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductLogistics record);

    int insertSelective(ProductLogistics record);

    ProductLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductLogistics record);

    int updateByPrimaryKey(ProductLogistics record);
    
    List<ProductLogistics> finalAll();
}