package com.fzy.admin.fp.ali.repository;

import com.fzy.admin.fp.ali.domain.AliPayInfo;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

public interface AliPayInfoRepository extends BaseRepository<AliPayInfo> {

    AliPayInfo findAllByUserId(String userId);
}
