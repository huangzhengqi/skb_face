package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.SalesmanRate;
import com.fzy.admin.fp.distribution.money.vo.SalesmanRateVO;

public interface SalesmanRateRepository extends BaseRepository<SalesmanRate> {

    SalesmanRate findByServiceProviderId(String serviceProviderId);
}