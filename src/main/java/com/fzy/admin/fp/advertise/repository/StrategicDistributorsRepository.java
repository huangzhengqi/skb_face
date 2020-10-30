package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.StrategicDistributors;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author lb
 * @date 2019/7/2 18:47
 * @Description
 */
public interface StrategicDistributorsRepository extends BaseRepository<StrategicDistributors> {

    List<StrategicDistributors> findByStrategicId(String strategicId);

    void deleteByStrategicId(String strategicId);

    List<StrategicDistributors> findByDistributorsIdAndStrategicId(String dis, String strategicId);
}
