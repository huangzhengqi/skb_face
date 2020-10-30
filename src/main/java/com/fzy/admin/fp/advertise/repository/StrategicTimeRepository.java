package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.StrategicTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author lb
 * @date 2019/7/2 18:58
 * @Description
 */
public interface StrategicTimeRepository extends BaseRepository<StrategicTime> {
    List<StrategicTime> findByStrategicId(String strategicId);

    void deleteByStrategicId(String strategicId);
}
