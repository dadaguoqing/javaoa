package com.genius;

import java.util.List;

public interface IAssetService {
    Result<List<AssetType>> getAllAssetTypes();
}
