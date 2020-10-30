package com.fzy.admin.fp.member.rule.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.rule.domain.StoredSwitch;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:41 2019/5/14
 * @ Description:
 **/

public interface StoredSwitchRepository extends BaseRepository<StoredSwitch> {

    StoredSwitch findByMerchantId(String merchantId);
}
