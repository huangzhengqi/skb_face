package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.TqSxfCityNo;

public interface TqSxfCityNoRepository extends BaseRepository<TqSxfCityNo> {
    TqSxfCityNo findByCityName(String city);
}
