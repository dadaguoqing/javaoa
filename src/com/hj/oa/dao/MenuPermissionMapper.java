package com.hj.oa.dao;

import com.hj.oa.bean.MenuPermission;
import com.hj.oa.bean.MenuPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuPermissionMapper {
    long countByExample(MenuPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MenuPermission record);

    int insertSelective(MenuPermission record);

    List<MenuPermission> selectByExample(MenuPermissionExample example);

    MenuPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MenuPermission record, @Param("example") MenuPermissionExample example);

    int updateByExample(@Param("record") MenuPermission record, @Param("example") MenuPermissionExample example);

    int updateByPrimaryKeySelective(MenuPermission record);

    int updateByPrimaryKey(MenuPermission record);
}