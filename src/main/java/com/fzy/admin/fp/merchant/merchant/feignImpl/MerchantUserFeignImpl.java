package com.fzy.admin.fp.merchant.merchant.feignImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-29 21:00
 * @description 商户用户feign接口实现
 */
@Service
public class MerchantUserFeignImpl implements MerchantUserFeign {

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private StoreService storeService;

    @Resource
    private MerchantService merchantService;

    @Override
    public List<MerchantUserSelect> findMerchantUser(String userId) {
        return merchantUserService.findMerchantUser(userId);
    }

    private MerchantUserDTO user2merchantUser(String userId) {
        MerchantUser user = merchantUserService.findOne(userId);
        if (ParamUtil.isBlank(user)) {
            return null;
        }
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(user, merchantUserDTO, copyOptions);
        // 获取门店名称
        Store store = storeService.findOne(user.getStoreId());
        merchantUserDTO.setStoreName(store.getName());
        // 获取商户并取出服务商ID
        Merchant merchant = merchantService.getRepository().findOne(user.getMerchantId());
        merchantUserDTO.setServiceProviderId(merchant.getServiceProviderId());
        return merchantUserDTO;
    }

    @Override
    public MerchantUserDTO findUser(String userId) {
        return user2merchantUser(userId);
    }

    @Override
    public MerchantUserDTO findKey(String merchantId) {
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        Merchant merchant = merchantService.findOne(merchantId);
        if (ParamUtil.isBlank(merchant)) {
            merchantUserDTO.setAppKey("error");
            return merchantUserDTO;
        }
        // 获取商户对应的账户
        MerchantUser user = merchantUserService.getRepository().findByMerchantIdAndUserTypeAndDelFlag(merchantId, "1", CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            merchantUserDTO.setAppKey("error");
            return merchantUserDTO;
        }
        merchantUserDTO = user2merchantUser(user.getId());
        if (!ParamUtil.isBlank(merchantUserDTO)) {
            merchantUserDTO.setAppKey(merchant.getAppKey());
            merchantUserDTO.setServiceProviderId(merchant.getServiceProviderId());
        }
        return merchantUserDTO;
    }

    @Override
    public String findStore(String storeId) {
        Store store = storeService.getRepository().findOne(storeId);
        if (ParamUtil.isBlank(store)) {
            return "error";
        }
        return store.getName();
    }
}
