package com.fzy.admin.fp.merchant.management.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantRole;

/**
 * @author zk
 * @description 角色表数据处理层
 * @create 2018-07-25 15:10:47
 **/
public interface MerchantRoleRepository extends BaseRepository<MerchantRole> {
    int countByIdAndDelFlag(String id, Integer delFlag);
}