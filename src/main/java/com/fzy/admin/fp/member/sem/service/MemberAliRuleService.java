package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.sem.domain.MemberAliRule;
import com.fzy.admin.fp.member.sem.repository.MemberAliRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberAliRuleService implements BaseService<MemberAliRule>{

    @Resource
    private MemberAliRuleRepository memberAliRuleRepository;

    @Override
    public BaseRepository<MemberAliRule> getRepository() {
        return memberAliRuleRepository;
    }

}
