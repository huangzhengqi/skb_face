package com.fzy.admin.fp.merchant.management.service;


import com.fzy.admin.fp.merchant.management.repository.MerchantPermissionRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.domain.MerchantPermission;
import com.fzy.admin.fp.merchant.management.repository.MerchantPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zk
 * @description 权限表服务层
 * @create 2018-07-25 15:32:54
 **/
@Slf4j
@Service
@Transactional
public class MerchantPermissionService implements BaseService<MerchantPermission> {

    @Resource
    private MerchantPermissionRepository merchantPermissionRepository;

    @Resource
    private MerchantRoleService merchantRoleService;

    @Resource
    private MerchantRolePermissionService merchantRolePermissionService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public MerchantPermissionRepository getRepository() {
        return merchantPermissionRepository;
    }

    public List<MerchantPermission> findByUserId(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p from MerchantUser u" +
                " ,MerchantUserRole ur ,MerchantRolePermission rp ,MerchantPermission p where u.id = ur.userId and ur.roleId = rp.roleId and p.id = rp.permissionId" +
                " and u.id = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), MerchantPermission.class).setParameter(1, userId);
        List<MerchantPermission> list = query.getResultList();
        return list;
    }

    public List<MerchantPermission> findByUserIdAndType(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p from MerchantUser u" +
                " ,MerchantUserRole ur ,MerchantRolePermission rp ,MerchantPermission p where u.id = ur.userId and ur.roleId = rp.roleId and p.id = rp.permissionId" +
                " and u.id = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append(" and p.type = 1");
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), MerchantPermission.class).setParameter(1, userId);
        List<MerchantPermission> list = query.getResultList();
        return list;
    }

    public List<MerchantPermission> findByRoleId(String roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p from MerchantRolePermission rp ,MerchantPermission p where p.id = rp.permissionId and rp.roleId = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), MerchantPermission.class).setParameter(1, roleId);
        List<MerchantPermission> list = query.getResultList();
        return list;
    }

    public List<MerchantPermission> findByLevelOrderBySortOrderDesc(Integer level) {
        return merchantPermissionRepository.findByLevelOrderBySortOrder(level);
    }


    public List<MerchantPermission> findByParentIdOrderBySortOrder(String parentId) {
        return merchantPermissionRepository.findByParentIdOrderBySortOrder(parentId);
    }


    /**
     * @author Created by wtl on 2019/3/13 15:51
     * @Description 添加权限
     */
    public String add(MerchantPermission entity) {
        update(entity);
        return "添加成功";
    }

}