package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseManage;
import com.hj.oa.bean.MerchandiseManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseManageMapper {
    long countByExample(MerchandiseManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseManage record);

    int insertSelective(MerchandiseManage record);

    List<MerchandiseManage> selectByExample(MerchandiseManageExample example);

    MerchandiseManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseManage record, @Param("example") MerchandiseManageExample example);

    int updateByExample(@Param("record") MerchandiseManage record, @Param("example") MerchandiseManageExample example);

    int updateByPrimaryKeySelective(MerchandiseManage record);

    int updateByPrimaryKey(MerchandiseManage record);
}