package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.RegionCity;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author lb
 * @date 2019/7/2 18:57
 * @Description
 */
public interface RegionCityRepository extends BaseRepository<RegionCity> {
    List<RegionCity> findByStrategicId(String strategicId);

    void deleteByStrategicId(String strategicId);
}
