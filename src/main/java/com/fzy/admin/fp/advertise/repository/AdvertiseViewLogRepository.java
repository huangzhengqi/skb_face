package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.AdvertiseViewLog;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @author lb
 * @date 2019/7/1 16:07
 * @Description
 */
public interface AdvertiseViewLogRepository extends BaseRepository<AdvertiseViewLog> {
    Integer countByAdvertiseIdAndStatus(String advId, Integer status);

}
