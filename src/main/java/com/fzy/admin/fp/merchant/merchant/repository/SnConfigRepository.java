package com.fzy.admin.fp.merchant.merchant.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.SnConfig;

/**
 * @ Author ：wtl.
 * @ Date  ：Created in 10:33 2019/6/12
 * @ Description: 第三方设备sn配置DAO
 **/

public interface SnConfigRepository extends BaseRepository<SnConfig> {

    SnConfig findBySnAndDelFlag(String sn, Integer delFlag);

}
