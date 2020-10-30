package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.domain.WxCityNo;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import com.fzy.admin.fp.merchant.merchant.repository.WxCityNoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-2-18 11:17:15
 * @Desp
 **/
@Service
public class WxCityNoService  implements BaseService<WxCityNo> {


    @Resource
    private WxCityNoRepository wxCityNoRepository;



    @Override
    public WxCityNoRepository getRepository() {
        return wxCityNoRepository;
    }
}
