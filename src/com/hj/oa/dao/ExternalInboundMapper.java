package com.hj.oa.dao;

import com.hj.oa.bean.ExternalInbound;
import com.hj.oa.bean.ExternalInboundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExternalInboundMapper {
    long countByExample(ExternalInboundExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExternalInbound record);

    int insertSelective(ExternalInbound record);

    List<ExternalInbound> selectByExample(ExternalInboundExample example);

    ExternalInbound selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExternalInbound record, @Param("example") ExternalInboundExample example);

    int updateByExample(@Param("record") ExternalInbound record, @Param("example") ExternalInboundExample example);

    int updateByPrimaryKeySelective(ExternalInbound record);

    int updateByPrimaryKey(ExternalInbound record);
}