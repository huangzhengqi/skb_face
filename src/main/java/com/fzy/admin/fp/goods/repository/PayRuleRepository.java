package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.PayRule;

public interface PayRuleRepository extends BaseRepository<PayRule> {
    PayRule findByMerchantId(String paramString);
}
