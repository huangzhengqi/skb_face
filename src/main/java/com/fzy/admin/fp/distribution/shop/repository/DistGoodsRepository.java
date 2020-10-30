package com.fzy.admin.fp.distribution.shop.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DistGoodsRepository extends BaseRepository<DistGoods> {
    Page<DistGoods> findAllByStatusAndServiceProviderIdOrderByWeightDesc(Integer status,String serviceProviderId, Pageable pageable);

    Page<DistGoods> findAllByServiceProviderIdOrderByWeightDesc(String serviceProviderId, Pageable pageable);

    DistGoods findByServiceProviderIdAndId(String serviceProviderId,String id);

    int countByServiceProviderId(String serviceProviderId);

    Integer countByServiceProviderIdAndWeight(String serviceProviderId, Integer weight);
}
