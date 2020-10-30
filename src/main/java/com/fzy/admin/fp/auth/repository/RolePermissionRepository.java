package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.RolePermission;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author zk
 * @description 角色权限关联表数据处理层
 * @create 2018-07-25 15:51:38
 **/
public interface RolePermissionRepository extends BaseRepository<RolePermission> {
    void deleteByRoleId(String id);

    void deleteByPermissionId(String id);

    List<RolePermission> findByRoleId(String roleId);
    List<RolePermission> findByRoleIdAndPermissionIdIn(String roleId,List<String> pid);
}