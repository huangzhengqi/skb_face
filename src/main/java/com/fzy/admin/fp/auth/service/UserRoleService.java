package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.UserRoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.UserRoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
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
 * @description 用户角色关联表服务层
 * @create 2018-07-25 15:50:59
 **/
@Slf4j
@Service
@Transactional
public class UserRoleService implements BaseService<UserRole> {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRoleRepository getRepository() {
        return userRoleRepository;
    }

    public void deleteByUserId(String id) {
        userRoleRepository.deleteByUserId(id);
    }

    public void deleteByRoleId(String id) {
        userRoleRepository.deleteByRoleId(id);
    }

    public List<Role> findUserRoleByUserId(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r from UserRole ur , Role r where ur.roleId = r.id and ur.userId = ? and r.delFlag = ").append(CommonConstant.NORMAL_FLAG);
        Query query = em.createQuery(sb.toString(), Role.class).setParameter(1, id);
        List<Role> list = query.getResultList();
        return list;
    }
}