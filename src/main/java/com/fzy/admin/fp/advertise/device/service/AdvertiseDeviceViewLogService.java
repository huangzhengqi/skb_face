package com.fzy.admin.fp.advertise.device.service;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceViewLog;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceViewLogRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdvertiseDeviceViewLogService implements BaseService<AdvertiseDeviceViewLog> {

    @Resource
    private AdvertiseDeviceViewLogRepository advertiseDeviceViewLogRepository;

    @Override
    public AdvertiseDeviceViewLogRepository getRepository() {
        return advertiseDeviceViewLogRepository;
    }

    public Integer findExposureNumByAdvId(String id) {
        return advertiseDeviceViewLogRepository.countByAdvertiseDeviceIdAndStatus(id,1);
    }

    public Integer findClickNumByAdvId(String id) {
        return advertiseDeviceViewLogRepository.countByAdvertiseDeviceIdAndStatus(id,2);
    }
}
