package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredSwitch;
import com.fzy.admin.fp.member.sem.domain.MemberAliStoredRule;
import com.fzy.admin.fp.member.sem.domain.StoredAliSwitch;
import com.fzy.admin.fp.member.sem.repository.StoredAliSwitchRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StoredAliSwitchService implements BaseService<StoredAliSwitch>{

    @Resource
    private StoredAliSwitchRepository storedAliSwitchRepository;

    @Override
    public BaseRepository<StoredAliSwitch> getRepository() {
        return storedAliSwitchRepository;
    }

    public Resp<StoredAliSwitch> findByMerchantId(MemberAliStoredRule memberAliStoredRule){
        if(null == memberAliStoredRule.getMerchantId()) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        StoredAliSwitch storedAliSwitch = storedAliSwitchRepository.findByMerchantId(memberAliStoredRule.getMerchantId());
        if(null == storedAliSwitch){
            storedAliSwitch = new StoredAliSwitch();
            storedAliSwitch.setMerchantId(memberAliStoredRule.getMerchantId());
            storedAliSwitch.setStatus(StoredSwitch.Status.DISABLE.getCode());
            storedAliSwitch = storedAliSwitchRepository.save(storedAliSwitch);
        }
        if(StringUtils.isNotBlank(memberAliStoredRule.getId()) && null != memberAliStoredRule.getStatus()) {
            storedAliSwitch.setStatus(memberAliStoredRule.getStatus());
            storedAliSwitch = storedAliSwitchRepository.save(storedAliSwitch);
        }
        return Resp.success(storedAliSwitch);
    }
}
