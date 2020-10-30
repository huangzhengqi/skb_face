package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.DistWxConfig;

public interface DistWxConfigReposotory extends BaseRepository<DistWxConfig> {

    DistWxConfig findByServiceProviderIdAndDelFlag(String serviceId, Integer normalFlag);
}
