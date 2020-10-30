package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.Costs;

/***
 * @author yy
 * @Date 2019-11-15 14:30:15
 */
public interface CostsRepository extends BaseRepository<Costs> {

    Costs findByIdAndServiceProviderId(String id,String ServiceProviderId);


    Costs findByTypeAndServiceProviderId(Integer type,String ServiceProviderId);


}
