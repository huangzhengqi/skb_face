package com.fzy.admin.fp.merchant.merchant.feignImpl;


import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.domain.StoreInfo;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-05-01 22:21
 * @description 门店feign接口实现
 */
@Service
@Slf4j
public class StoreServiceFeignImpl implements StoreServiceFeign {

    @Resource
    private StoreService storeService;

    @Resource
    private MerchantUserService merchantUserService;

    @Override
    public String findStore(String storeId) {
        Store store = storeService.findOne(storeId);
        return store.getName();
    }

    @Override
    public List<StoreInfo> findStoreInfo(String merchantId) {
        List<Store> stores = storeService.getRepository().findByMerchantIdAndStatus(merchantId, 1);
        return stores.stream()
                .map(e -> new StoreInfo(e.getName(), e.getPhone(), e.getAddress(), e.getProvince(), e.getCity()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreInfo> findStoreInfoList(String[] stores) {
        List<Store> storeList = new ArrayList<>();
        for (int i = 0; i < stores.length; i++) {
            Store store = storeService.getRepository().findOne(stores[i]);
            if (store != null) {
                storeList.add(store);
            }
        }
        if (storeList.size() > 0) {
            return storeList.stream()
                    .map(e -> new StoreInfo(e.getName(), e.getPhone(), e.getAddress(), e.getProvince(), e.getCity()))
                    .collect(Collectors.toList());
        } else {
            StoreInfo storeInfo = new StoreInfo("不存在", "10086", "不存在", "不存在", "不存在");
            List<StoreInfo> storeInfos = new ArrayList<>();
            storeInfos.add(storeInfo);
            return storeInfos;
        }

    }

    @Override
    public MerchantDefaultStore findDefaultByMchid(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            log.info("storeServiceFeignImpl->findDefaultByMchid->merchantId is null");
        }
        log.info("merId" + merchantId);
        MerchantDefaultStore merchantDefaultStore = new MerchantDefaultStore();
        Store store = storeService.getRepository().findByMerchantIdAndStoreFlag(merchantId, Store.StoreFlag.DEFAULT.getCode());
        if (null == store) {
            // 默认门店为空，说明default_flag没有设置
            // 获取商户的默认用户，再获取默认门店id
            MerchantUser user = merchantUserService.getRepository().findByMerchantIdAndUserTypeAndDelFlag(merchantId, "1", CommonConstant.NORMAL_FLAG);
            log.info("user===" + user);
            store = storeService.findOne(user.getStoreId());
            store.setStoreFlag(Store.StoreFlag.DEFAULT.getCode());
            storeService.save(store);
        }
        merchantDefaultStore.setStoreId(store.getId());
        merchantDefaultStore.setStoreName(store.getName());
        return merchantDefaultStore;
    }
}
