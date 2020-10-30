package com.fzy.admin.fp.merchant.management.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantUserRole;

/**
 * @author zk
 * @description 用户角色关联表数据处理层
 * @create 2018-07-25 15:50:59
 **/
public interface MerchantUserRoleRepository extends BaseRepository<MerchantUserRole> {
    void deleteByUserId(String id);

    void deleteByRoleId(String id);
}