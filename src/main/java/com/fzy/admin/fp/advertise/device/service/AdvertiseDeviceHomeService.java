package com.fzy.admin.fp.advertise.device.service;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceHome;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceHomeRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdvertiseDeviceHomeService implements BaseService<AdvertiseDeviceHome> {

    @Resource
    private AdvertiseDeviceHomeRepository advertiseDeviceHomeRepository;

    @Override
    public AdvertiseDeviceHomeRepository getRepository() {
        return advertiseDeviceHomeRepository;
    }
}
