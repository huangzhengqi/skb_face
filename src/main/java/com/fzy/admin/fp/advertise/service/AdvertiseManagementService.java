package com.fzy.admin.fp.advertise.service;

import com.fzy.admin.fp.advertise.repository.AdvertiseManagementRepository;
import com.fzy.admin.fp.advertise.domain.AdvertiseManagement;
import com.fzy.admin.fp.advertise.repository.AdvertiseManagementRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/7/1 16:14
 * @Description
 */
@Service
public class AdvertiseManagementService implements BaseService<AdvertiseManagement> {

    @Resource
    private AdvertiseManagementRepository advertiseManagementRepository;

    @Override
    public BaseRepository<AdvertiseManagement> getRepository() {
        return advertiseManagementRepository;
    }

    public AdvertiseManagement findOne(String id) {
        return advertiseManagementRepository.findOne(id);
    }


}
