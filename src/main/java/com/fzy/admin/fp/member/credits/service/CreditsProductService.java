package com.fzy.admin.fp.member.credits.service;


import com.fzy.admin.fp.member.credits.repository.CreditsProductRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.repository.CreditsProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/15 16:06
 * @Description 积分商城业务逻辑层
 */
@Service
@Slf4j
public class CreditsProductService implements BaseService<CreditsProduct> {

    @Resource
    private CreditsProductRepository creditsProductRepository;

    @Override
    public BaseRepository<CreditsProduct> getRepository() {
        return creditsProductRepository;
    }

    public CreditsProduct search(String id) {
        return creditsProductRepository.findOne(id);
    }

    public List<CreditsProduct> findByMerchantId(String merchantId) {
        return creditsProductRepository.findByMerchantId(merchantId);
    }

}
