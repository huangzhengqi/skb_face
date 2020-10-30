package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.*;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.*;
import com.fzy.admin.fp.auth.repository.*;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 权限表服务层
 * @create 2018-07-25 15:32:54
 **/
@Slf4j
@Service
@Transactional
public class PermissionService implements BaseService<Permission> {

    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private RolePermissionRepository rolePermissionRepository;
    @Resource
    private CompanyRepository companyRepository;


    @PersistenceContext
    private EntityManager em;

    @Override
    public PermissionRepository getRepository() {
        return permissionRepository;
    }

    public List<Permission> findByUserIdNew(User user) {
        //查看是否将角色下发给当前公司，有则使用下放的权限，否则默认权限
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role sourceRole = roleRepository.findOne(userRole.getRoleId());


        //查询自己给自己设置的权限
        Role role = roleRepository.findByCompanyIdAndLevelAndTypeAndKindAndDelFlag(user.getCompanyId(),sourceRole.getLevel(),sourceRole.getType(),sourceRole.getKind(), 1);
        if (role != null) {
            //默认角色的权限集
            List<RolePermission> defaultRolePermission = rolePermissionRepository.findByRoleId(userRole.getRoleId());
            List<String> defaultpermissionId = defaultRolePermission.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

            //使用上级给自己的权限
            List<RolePermission> superRolePermissions = rolePermissionRepository.findByRoleIdAndPermissionIdIn(role.getSourceRoleId(),defaultpermissionId);
            List<String> superpermissionId = superRolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());


            //自己给自己勾选的权限
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleIdAndPermissionIdIn(role.getId(),superpermissionId);
            if (rolePermissions.size() > 0) {
                List<String> permissionId = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                List<Permission> result = permissionRepository.findByIdInOrderBySortOrder(permissionId);
                return result;
            } else {
                //使用上级给自己的权限
                if (superRolePermissions.size() > 0) {
                    List<String> permissionId = superRolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                    List<Permission> result = permissionRepository.findByIdInOrderBySortOrder(permissionId);
                    return result;
                } else {
                    //使用超级后台设置的权限
                    return this.findByUserId(user.getId());
                }
            }
        } else {
            //使用超级后台设置的权限
            return this.findByUserId(user.getId());
        }
    }

    public List<Permission> findByUserId(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p from User u" +
                " ,UserRole ur ,RolePermission rp ,Permission p where u.id = ur.userId and ur.roleId = rp.roleId and p.id = rp.permissionId" +
                " and u.id = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), Permission.class).setParameter(1, userId);
        List<Permission> list = query.getResultList();
        return list;
    }

    public List<Permission> findByUserIdAndType(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p from User u" +
                " ,UserRole ur ,RolePermission rp ,Permission p where u.id = ur.userId and ur.roleId = rp.roleId and p.id = rp.permissionId" +
                " and u.id = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append(" and p.type = 1");
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), Permission.class).setParameter(1, userId);
        List<Permission> list = query.getResultList();
        return list;
    }

    public List<Permission> findByRoleId(String roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p from RolePermission rp ,Permission p where p.id = rp.permissionId and rp.roleId = ? and p.delFlag =");
        sb.append(CommonConstant.NORMAL_FLAG);
        sb.append("  order by p.sortOrder");
        Query query = em.createQuery(sb.toString(), Permission.class).setParameter(1, roleId);
        List<Permission> list = query.getResultList();
        return list;
    }

    public List<Permission> findByLevelOrderBySortOrderDesc(Integer level) {
        return permissionRepository.findByLevelOrderBySortOrder(level);
    }


    public List<Permission> findByLevelAndIdInOrderBySortOrder(Integer level, List<String> ids) {
        return permissionRepository.findByLevelAndIdInOrderBySortOrder(level, ids);
    }


    public List<Permission> findByParentIdOrderBySortOrder(String parentId) {
        return permissionRepository.findByParentIdOrderBySortOrder(parentId);
    }
    public List<Permission> findByParentIdAndIdInOrderBySortOrder(String parentId,List<String> ids) {
        return permissionRepository.findByParentIdAndIdInOrderBySortOrder(parentId,ids);
    }

}