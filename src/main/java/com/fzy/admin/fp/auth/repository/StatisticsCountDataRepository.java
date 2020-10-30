package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.auth.domain.StatisticsCountData;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

public interface StatisticsCountDataRepository extends BaseRepository<StatisticsCountData> {
    StatisticsCountData findByCompanyIdAndSaveDay(String paramString1, String paramString2);
}
