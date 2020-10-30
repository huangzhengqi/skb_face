package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.app.domain.DistAdvertise;
import com.fzy.admin.fp.distribution.app.repository.AppDistAdvertiseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppDistAdvertiseService implements BaseService<DistAdvertise> {

    @Resource
    private AppDistAdvertiseRepository appDistAdvertiseRepository;

    @Override
    public AppDistAdvertiseRepository getRepository() {
        return appDistAdvertiseRepository;
    }

    public int findCountAdvertise(String serviceProviderId, Integer targetRange) {
        return appDistAdvertiseRepository.countByServiceProviderIdAndTargetRange(serviceProviderId, targetRange);
    }
}
