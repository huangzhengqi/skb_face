package com.fzy.admin.fp.advertise.device.service;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceSuccess;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceSuccessRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdvertiseDeviceSuccessService implements BaseService<AdvertiseDeviceSuccess> {

    @Resource
    private AdvertiseDeviceSuccessRepository advertiseDeviceSuccessRepository;

    @Override
    public AdvertiseDeviceSuccessRepository getRepository() {
        return advertiseDeviceSuccessRepository;
    }
}
