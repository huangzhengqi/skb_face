package com.fzy.admin.fp.member.rule.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.rule.domain.StoredRule;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:41 2019/5/14
 * @ Description:
 **/

public interface StoredRuleRepository extends BaseRepository<StoredRule> {

    StoredRule findByNameAndDelFlagAndMerchantId(String name, Integer delFlag,String merchantId);

    List<StoredRule> findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

    List<StoredRule> findByMerchantIdAndDelFlagAndStatusOrderByUpdateTimeDesc(String paramString, Integer paramInteger1, Integer paramInteger2);

}
