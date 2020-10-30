package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.AppDown;

public interface AppDownRepository extends BaseRepository<AppDown> {
    AppDown findByServiceProviderId(String serviceProviderId);
}
