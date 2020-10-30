package com.fzy.admin.fp.distribution.guide.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.guide.domain.Guide;

public interface GuideRepository extends BaseRepository<Guide> {
    Guide findByServiceProviderId(String serviceProviderId);
}
