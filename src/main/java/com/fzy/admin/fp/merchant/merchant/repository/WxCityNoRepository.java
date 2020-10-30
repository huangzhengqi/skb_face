package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.WxCityNo;

public interface WxCityNoRepository extends BaseRepository<WxCityNo> {
    WxCityNo findByCity(String city);
}
