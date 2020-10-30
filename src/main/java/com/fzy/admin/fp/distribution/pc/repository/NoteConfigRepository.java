package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.NoteConfig;

public interface NoteConfigRepository extends BaseRepository<NoteConfig> {
    NoteConfig findByServiceProviderId(String serviceProviderId);
}
