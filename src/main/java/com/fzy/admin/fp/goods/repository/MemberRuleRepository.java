package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.MemberRule;

import java.util.List;

public interface MemberRuleRepository extends BaseRepository<MemberRule> {
    List<MemberRule> findByStatusAndMerchantId(Integer paramInteger, String paramString);
}