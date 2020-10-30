package com.fzy.admin.fp.advertise.device.service;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceMid;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceMidRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdvertiseDeviceMidService implements BaseService<AdvertiseDeviceMid> {

    @Resource
    private AdvertiseDeviceMidRepository advertiseDeviceMidRepository;


    @Override
    public AdvertiseDeviceMidRepository getRepository() {
        return advertiseDeviceMidRepository;
    }
}
