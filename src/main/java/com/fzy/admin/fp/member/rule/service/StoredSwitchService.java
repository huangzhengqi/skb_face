package com.fzy.admin.fp.member.rule.service;


import com.fzy.admin.fp.member.rule.repository.StoredSwitchRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.member.rule.domain.StoredSwitch;
import com.fzy.admin.fp.member.rule.repository.StoredSwitchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:43 2019/5/14
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class StoredSwitchService implements BaseService<StoredSwitch> {

    @Resource
    private StoredSwitchRepository storedSwitchRepository;


    @Override
    public StoredSwitchRepository getRepository() {
        return storedSwitchRepository;
    }


    public StoredSwitch findByMerchantId(String merchantId) {
        //查询商户是否有储值开关
        StoredSwitch storedSwitch = storedSwitchRepository.findByMerchantId(merchantId);
        //若为空,则初始化一个
        if (ParamUtil.isBlank(storedSwitch)) {
            storedSwitch = new StoredSwitch();
            storedSwitch.setMerchantId(merchantId);
            storedSwitch.setStatus(StoredSwitch.Status.DISABLE.getCode());
            storedSwitchRepository.save(storedSwitch);
        }
        return storedSwitch;
    }
}
