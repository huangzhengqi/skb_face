package com.fzy.admin.fp.advertise.service;

import com.fzy.admin.fp.advertise.repository.AdvertiseViewLogRepository;
import com.fzy.admin.fp.advertise.domain.AdvertiseViewLog;
import com.fzy.admin.fp.advertise.repository.AdvertiseViewLogRepository;
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
public class AdvertiseViewLogService implements BaseService<AdvertiseViewLog> {

    @Resource
    private AdvertiseViewLogRepository advertiseViewLogRepository;


    @Override
    public BaseRepository<AdvertiseViewLog> getRepository() {
        return advertiseViewLogRepository;
    }


    public Integer findExposureNumByAdvId(String id) {
        return advertiseViewLogRepository.countByAdvertiseIdAndStatus(id,1);
    }

    public Integer findClickNumByAdvId(String id) {
        return advertiseViewLogRepository.countByAdvertiseIdAndStatus(id,2);
    }
}
