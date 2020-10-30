package com.fzy.admin.fp.sdk.merchant.feign;


import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;

import java.util.List;

/**
 * @author Created by wtl on 2019-04-29 19:41
 * @description 商户的用户表feign接口
 */
public interface MerchantUserFeign {

    // 根据用户id获取用户列表下拉框
    List<MerchantUserSelect> findMerchantUser(String userId);

    // 根据用户id获取用户
    MerchantUserDTO findUser(String userId);

    // 根据商户id获取商户key以及商户默认的用户
    MerchantUserDTO findKey(String merchantId);

    // 根据门店id获取门店名称
    String findStore(String storeId);


}
