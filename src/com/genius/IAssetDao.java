package com.genius;

import java.util.List;

public interface IAssetDao {
    //    查询所有的资产名称类型
    List<AssetType> selectAll();
}
