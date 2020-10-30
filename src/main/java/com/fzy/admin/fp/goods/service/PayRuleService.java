package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.PayRule;
import com.fzy.admin.fp.goods.repository.PayRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayRuleService implements BaseService<PayRule> {
    @Resource
    private PayRuleRepository payRuleRepository;

    public BaseRepository<PayRule> getRepository() { return this.payRuleRepository; }



    public PayRule findByCompanyId(String companyId) { return this.payRuleRepository.findByMerchantId(companyId); }


    public PayRule getPayRule(PayRule rule) {
        if (rule == null) {
            rule = new PayRule();
            rule.setRechargeUse(Integer.valueOf(2));
            rule.setIntegralUse(Integer.valueOf(0));
            rule.setSimultaneouslyUse(Integer.valueOf(0));
        }
        return rule;
    }
}

