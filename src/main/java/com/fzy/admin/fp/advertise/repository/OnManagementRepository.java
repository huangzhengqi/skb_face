package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.OnManagement;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author lb
 * @date 2019/7/3 16:05
 * @Description
 */
public interface OnManagementRepository extends BaseRepository<OnManagement> {

    List<OnManagement> findByAdvertiseId(String advertiseId);

    List<OnManagement> findByStrategicId(String strategicId);

    List<OnManagement> findByStatusAndAppType(Integer status, Integer appType);

    List<OnManagement> findByStatusAndAppTypeAndStrategicId(Integer status, Integer appType, String strategicId);

}
