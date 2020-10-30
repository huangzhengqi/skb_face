package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;

public interface SystemSetupRepository extends BaseRepository<SystemSetup> {
    SystemSetup findByServiceProviderId(String serviceProviderId);
}
