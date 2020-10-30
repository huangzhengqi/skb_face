package com.fzy.admin.fp.member.credits.controller;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.merchant.domain.StoreInfo;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/29 11:37
 * @Description 获取门店信息
 */
@RestController
@RequestMapping(value = "/member/credits/store")
public class CreditsStoreController {

    @Resource
    private StoreServiceFeign storeServiceFeign;

    @GetMapping(value = "/store")
    public Resp getStore(String merchantId) {
        List<StoreInfo> storeInfos = storeServiceFeign.findStoreInfo(merchantId);
        return Resp.success(storeInfos);
    }

    @GetMapping(value = "/store_all")
    public Resp getStoreInfo(String storeIds) {
        String[] stores = storeIds.split(",");
        List<StoreInfo> storeInfoList = storeServiceFeign.findStoreInfoList(stores);
        return Resp.success(storeInfoList);
    }

}
