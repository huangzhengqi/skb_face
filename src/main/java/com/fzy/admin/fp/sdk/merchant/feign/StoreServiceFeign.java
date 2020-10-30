package com.fzy.admin.fp.sdk.merchant.feign;


import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.domain.StoreInfo;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.domain.StoreInfo;

import java.util.List;

/**
 * @author Created by wtl on 2019-05-01 22:15
 * @description 门店feign接口
 */
public interface StoreServiceFeign {

    // 根据门店id查询门店名称
    String findStore(String storeId);

    //查询所有门店
    List<StoreInfo> findStoreInfo(String merchantId);

    //查询数组内所有门店id获取集合
    List<StoreInfo> findStoreInfoList(String[] stores);

    //查询商户的默认门店
    MerchantDefaultStore findDefaultByMchid(String merchantId);

}
