package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.SendStatus;

public interface SendStatusRepository extends BaseRepository<SendStatus> {

    SendStatus findByServiceProviderId(String id);
}
