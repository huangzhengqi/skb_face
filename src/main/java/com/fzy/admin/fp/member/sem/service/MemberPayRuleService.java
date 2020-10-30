package com.fzy.admin.fp.member.sem.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberPayRule;
import com.fzy.admin.fp.member.sem.repository.MemberPayRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberPayRuleService implements BaseService<MemberPayRule>{

    @Resource
    private MemberPayRuleRepository memberPayRuleRepository;

    @Override
    public BaseRepository<MemberPayRule> getRepository() {
        return memberPayRuleRepository;
    }

    public Resp<MemberPayRule> findByMerchantId(String merchantId) {
        List<MemberPayRule> memberPayRule = memberPayRuleRepository.findByMerchantId(merchantId);
        if(memberPayRule.size() > 0 && !memberPayRule.isEmpty()) {
            return Resp.success(memberPayRule.get(0));
        }
        return Resp.success(new MemberPayRule());
    }
}
