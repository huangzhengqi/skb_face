package com.fzy.admin.fp.advertise.device.repository;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceViewLog;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

public interface AdvertiseDeviceViewLogRepository extends BaseRepository<AdvertiseDeviceViewLog> {
    Integer countByAdvertiseDeviceIdAndStatus(String advId, Integer status);
}
