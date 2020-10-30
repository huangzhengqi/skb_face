package com.fzy.admin.fp.member.sem.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.PayRule;
import com.fzy.admin.fp.member.sem.domain.MemberPayRule;

import java.util.List;

public interface MemberPayRuleRepository extends BaseRepository<MemberPayRule>{
    List<MemberPayRule> findByMerchantId(String merchantId);
}
