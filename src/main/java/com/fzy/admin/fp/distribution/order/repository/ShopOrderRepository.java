package com.fzy.admin.fp.distribution.order.repository;

import cn.hutool.core.date.DateTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ShopOrderRepository extends BaseRepository<ShopOrder> {

    ShopOrder findByOrderNumber(String orderNumber);

    ShopOrder findByUserIdAndId(String userId,String id);

    ShopOrder findByServiceProviderIdAndId(String serviceProviderId,String id);

    List<ShopOrder> findAllByServiceProviderIdAndOrderNumberLikeAndPhoneLikeAndCreateTimeBetween(String serviceProviderId,  String orderNumber, String phone, Date startTime, Date endTime);

    List<ShopOrder> findAllByServiceProviderIdAndOrderNumberLikeAndPhoneLike(String serviceProviderId,  String orderNumber, String phone);

    Integer countByUserIdAndStatusLessThanEqualAndStatusGreaterThanEqual(String userId,Integer less,Integer than);

    List<ShopOrder> findAllByStatusInAndDelFlag(List<Integer> statusList,Integer delFlag);


}
