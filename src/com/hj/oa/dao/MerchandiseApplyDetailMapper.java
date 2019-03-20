package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseApplyDetail;
import com.hj.oa.bean.MerchandiseApplyDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseApplyDetailMapper {
    long countByExample(MerchandiseApplyDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseApplyDetail record);

    int insertSelective(MerchandiseApplyDetail record);

    List<MerchandiseApplyDetail> selectByExample(MerchandiseApplyDetailExample example);

    MerchandiseApplyDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseApplyDetail record, @Param("example") MerchandiseApplyDetailExample example);

    int updateByExample(@Param("record") MerchandiseApplyDetail record, @Param("example") MerchandiseApplyDetailExample example);

    int updateByPrimaryKeySelective(MerchandiseApplyDetail record);

    int updateByPrimaryKey(MerchandiseApplyDetail record);
}