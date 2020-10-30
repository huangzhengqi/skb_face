package com.fzy.admin.fp.merchant.management.service;


import com.fzy.admin.fp.merchant.management.domain.MerchantRolePermission;
import com.fzy.admin.fp.merchant.management.repository.MerchantRolePermissionRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.domain.MerchantRolePermission;
import com.fzy.admin.fp.merchant.management.repository.MerchantRolePermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author zk
 * @description 角色权限关联表服务层
 * @create 2018-07-25 15:51:38
 **/
@Slf4j
@Service
@Transactional
public class MerchantRolePermissionService implements BaseService<MerchantRolePermission> {

    @Resource
    private MerchantRolePermissionRepository merchantRolePermissionRepository;

    @Override
    public MerchantRolePermissionRepository getRepository() {
        return merchantRolePermissionRepository;
    }


    public void deleteByRoleId(String id) {
        merchantRolePermissionRepository.deleteByRoleId(id);
    }

    public void deleteByPermissionId(String id) {
        merchantRolePermissionRepository.deleteByPermissionId(id);
    }
}