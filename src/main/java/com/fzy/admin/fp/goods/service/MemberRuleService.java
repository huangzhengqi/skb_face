package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.MemberRule;
import com.fzy.admin.fp.goods.repository.MemberRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberRuleService implements BaseService<MemberRule> {
    @Resource
    private MemberRuleRepository memberRuleRepository;

    @Override
    public MemberRuleRepository getRepository() {
        return this.memberRuleRepository;
    }



    public List<MemberRule> findByStatusAndMerchantId(Integer status, String merchantId) {
        return this.memberRuleRepository.findByStatusAndMerchantId(status, merchantId);
    }
}
