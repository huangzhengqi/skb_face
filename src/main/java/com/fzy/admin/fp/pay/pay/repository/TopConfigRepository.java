package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;

/**
 * @author Created by wtl on 2019-06-11 20:36
 * @description 服务商配置dao
 */
public interface TopConfigRepository extends BaseRepository<TopConfig> {


    /**
     * @author Created by wtl on 2019/7/2 15:48
     * @Description 根据服务商ID获取服务商支付配置
     */
    TopConfig findByServiceProviderIdAndDelFlag(String companyId, Integer delFlag);


}
