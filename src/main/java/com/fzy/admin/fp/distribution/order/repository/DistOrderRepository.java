package com.fzy.admin.fp.distribution.order.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;

import java.util.Date;
import java.util.List;

public interface DistOrderRepository extends BaseRepository<DistOrder> {
    DistOrder findByOrderNumber(String orderNumber);

    DistOrder findByUserIdAndTypeAndStatus(String userId,Integer type,Integer status);

    List<DistOrder> findAllByServiceProviderId(String serviceProviderId);

    List<DistOrder> findAllByServiceProviderIdAndCreateTimeBetweenAndStatus(String serviceProviderId, Date begin, Date end,Integer status);

}
