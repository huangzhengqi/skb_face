package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @author zk
 * @description 用户角色关联表数据处理层
 * @create 2018-07-25 15:50:59
 **/
public interface UserRoleRepository extends BaseRepository<UserRole> {
    void deleteByUserId(String id);

    void deleteByRoleId(String id);

    UserRole findByUserId(String userId);
}