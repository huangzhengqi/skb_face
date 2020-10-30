package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.AdvertiseTarget;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author lb
 * @date 2019/7/1 16:07
 * @Description
 */
public interface AdvertiseTargetRepository extends BaseRepository<AdvertiseTarget> {

    void deleteByAdvertiseId(String id);

    /**
     *
     * @param companyIds
     * @return
     */
    List<AdvertiseTarget> findAllByTargetIdIn(List<String> companyIds);

    /**
     *
     * @param city
     * @return
     */
    List<AdvertiseTarget> findAllByCityIds(String city);

    List<AdvertiseTarget> findAllByAdvertiseId(String id);
}
