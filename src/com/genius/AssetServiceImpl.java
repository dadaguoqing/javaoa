package com.genius;

import com.hj.commons.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class: AssetServiceImpl
 * Description: TODO
 * Author: Genius
 * Date: 2019/3/20 11:10
 * Version: V1.0
 */
@Service("assetService")
public class AssetServiceImpl implements IAssetService {
    @Autowired
    @Qualifier("assetDao")
    private IAssetDao assetDao;

    @Autowired
    @Qualifier("result")
    private Result<List<AssetType>> result;

    @Override
    public Result<List<AssetType>> getAllAssetTypes() {
        List<AssetType> assetTypes = assetDao.selectAll();
        if (assetTypes.isEmpty()) {
            result.setResultCode(ResultCode.FAILED);
        }
        result.setData(assetTypes);
        return result;
    }
}
