package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.RateStandards;

public interface RateStandardsRepository  extends BaseRepository<RateStandards> {
    RateStandards findByServiceProviderId(String serviceProviderId);
}
