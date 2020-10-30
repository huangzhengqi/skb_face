package com.fzy.admin.fp.distribution.order.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.AfterSale;

public interface AfterSaleRepository extends BaseRepository<AfterSale> {

    AfterSale findByServiceProviderId(String serviceProviderId);
}
