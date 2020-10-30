package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.Rate;

public interface RateRepository extends BaseRepository<Rate> {

    Rate findByIdAndServiceProviderId(String id,String ServiceProviderId);
}
