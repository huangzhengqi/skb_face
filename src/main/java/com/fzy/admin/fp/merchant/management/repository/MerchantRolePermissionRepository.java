package com.fzy.admin.fp.merchant.management.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantRolePermission;

/**
 * @author zk
 * @description 角色权限关联表数据处理层
 * @create 2018-07-25 15:51:38
 **/
public interface MerchantRolePermissionRepository extends BaseRepository<MerchantRolePermission> {
    void deleteByRoleId(String id);

    void deleteByPermissionId(String id);
}