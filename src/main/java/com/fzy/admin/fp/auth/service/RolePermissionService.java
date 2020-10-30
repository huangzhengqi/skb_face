package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.RolePermissionRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.RolePermission;
import com.fzy.admin.fp.auth.repository.RolePermissionRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zk
 * @description 角色权限关联表服务层
 * @create 2018-07-25 15:51:38
 **/
@Slf4j
@Service
@Transactional
public class RolePermissionService implements BaseService<RolePermission> {

    @Resource
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public RolePermissionRepository getRepository() {
        return rolePermissionRepository;
    }


    public void deleteByRoleId(String id) {
        rolePermissionRepository.deleteByRoleId(id);
    }

    public void deleteByPermissionId(String id) {
        rolePermissionRepository.deleteByPermissionId(id);
    }

    public List<RolePermission> findByRoleId (String roleId) {
        return rolePermissionRepository.findByRoleId(roleId);
    }
}