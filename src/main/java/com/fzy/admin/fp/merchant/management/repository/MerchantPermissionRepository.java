package com.fzy.admin.fp.merchant.management.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantPermission;

import java.util.List;

/**
 * @author zk
 * @description 权限表数据处理层
 * @create 2018-07-25 15:32:54
 **/
public interface MerchantPermissionRepository extends BaseRepository<MerchantPermission> {
    List<MerchantPermission> findByLevelOrderBySortOrder(Integer level);

    List<MerchantPermission> findByParentIdOrderBySortOrder(String parentId);
}