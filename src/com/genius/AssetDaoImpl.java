package com.genius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class: AssetDaoImpl
 * Description: TODO
 * Author: Genius
 * Date: 2019/3/20 10:49
 * Version: V1.0
 */
@Repository("assetDao")
public class AssetDaoImpl implements IAssetDao {
    @Autowired
    @Qualifier("assetDao")
    private static IAssetDao assetDao;
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AssetType> selectAll() {

        String sql = "select id, pId, name from t_zc_prop_type";
        // 使用template查询数据
        List<AssetType> types = jdbcTemplate.query(sql, new BeanPropertyRowMapper<AssetType>(AssetType.class));
        if (types.isEmpty()) {
            return null;
        }
        return types;
    }

    public static void main(String[] args) {
        assetDao.selectAll();
    }
}
