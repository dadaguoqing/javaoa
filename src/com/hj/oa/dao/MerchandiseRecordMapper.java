package com.hj.oa.dao;

import com.hj.oa.bean.MerchandiseDetailRecord;
import com.hj.oa.bean.MerchandiseRecord;
import com.hj.oa.bean.MerchandiseRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchandiseRecordMapper {
    long countByExample(MerchandiseRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchandiseRecord record);

    int insertSelective(MerchandiseRecord record);

    List<MerchandiseRecord> selectByExample(MerchandiseRecordExample example);

    MerchandiseRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchandiseRecord record, @Param("example") MerchandiseRecordExample example);

    int updateByExample(@Param("record") MerchandiseRecord record, @Param("example") MerchandiseRecordExample example);

    int updateByPrimaryKeySelective(MerchandiseRecord record);

    int updateByPrimaryKey(MerchandiseRecord record);
    
    List<MerchandiseDetailRecord> findDetailRecordByApplyCode(String applyCode);
}