package com.fzy.admin.fp.member.credits.service;


import com.fzy.admin.fp.member.credits.repository.CreditsRulerRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;
import com.fzy.admin.fp.member.credits.repository.CreditsRulerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/14 11:47
 * @Description 订单规则业务逻辑层
 */
@Slf4j
@Service
@Transactional
public class CreditsRulerService implements BaseService<CreditsRuler> {

    @Resource
    private CreditsRulerRepository creditsRulerRepository;

    @Override
    public BaseRepository getRepository() {
        return creditsRulerRepository;
    }

    public CreditsRuler findByMerchantId(String merchantId) {
        return creditsRulerRepository.findByMerchantId(merchantId);
    }


}
