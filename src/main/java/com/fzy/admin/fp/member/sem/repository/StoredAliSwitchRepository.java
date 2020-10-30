package com.fzy.admin.fp.member.sem.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.sem.domain.StoredAliSwitch;

public interface StoredAliSwitchRepository extends BaseRepository<StoredAliSwitch>{

    StoredAliSwitch findByMerchantId(String merchantId);

}
