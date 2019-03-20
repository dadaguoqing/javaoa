package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseApply;
import com.hj.oa.bean.MerchandiseApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseApplyMapper {
    long countByExample(MerchandiseApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseApply record);

    int insertSelective(MerchandiseApply record);

    List<MerchandiseApply> selectByExample(MerchandiseApplyExample example);

    MerchandiseApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseApply record, @Param("example") MerchandiseApplyExample example);

    int updateByExample(@Param("record") MerchandiseApply record, @Param("example") MerchandiseApplyExample example);

    int updateByPrimaryKeySelective(MerchandiseApply record);

    int updateByPrimaryKey(MerchandiseApply record);
}