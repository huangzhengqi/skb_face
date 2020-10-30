package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.auth.domain.FootInfo;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

public interface FootInfoRepository extends BaseRepository<FootInfo> {
    FootInfo findByServiceProviderId(String serviceProviderId);
}
