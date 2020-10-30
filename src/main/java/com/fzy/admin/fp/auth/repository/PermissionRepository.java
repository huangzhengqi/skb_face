package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

/**
 * @author zk
 * @description 权限表数据处理层
 * @create 2018-07-25 15:32:54
 **/
public interface PermissionRepository extends BaseRepository<Permission> {
    List<Permission> findByLevelOrderBySortOrder(Integer level);
    List<Permission> findByLevelAndIdInOrderBySortOrder(Integer level,List<String> ids);

    List<Permission> findByParentIdOrderBySortOrder(String parentId);

    List<Permission> findByParentIdAndIdInOrderBySortOrder(String parentId,List<String> ids);

    List<Permission> findByIdInOrderBySortOrder(List<String> ids);


}