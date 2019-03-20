package com.hj.oa.dao;

import java.util.List;
import com.hj.oa.bean.Seal;
import com.hj.oa.bean.SealType;

public interface SealMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Seal record);

	int insertSelective(Seal record);

	Seal selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Seal record);

	int updateByPrimaryKey(Seal record);

	List<Seal> selectAllSeal();

	List<SealType> selectAllSealType();
}