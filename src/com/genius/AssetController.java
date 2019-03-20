package com.genius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Class: AssetController
 * Description: TODO
 * Author: Genius
 * Date: 2019/3/19 19:04
 * Version: V1.0
 */
@Controller
@RequestMapping("asset")
public class AssetController {
    @Autowired
    @Qualifier("assetService")
    private IAssetService assetService;

    @RequestMapping(value = "asset", method = RequestMethod.GET)
    public String asset(Model model) {
        Result<List<AssetType>> result = assetService.getAllAssetTypes();
        if (result.getState() == ResultCode.SUCCESS.getCode()) {
            model.addAttribute("types", result.getData());
        } else {
            System.out.println("查询失败");
        }
        return "oa/asset/asset1";
    }
}
