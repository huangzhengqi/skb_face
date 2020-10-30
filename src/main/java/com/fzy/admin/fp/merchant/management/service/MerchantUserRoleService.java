package com.fzy.admin.fp.merchant.management.service;

import com.fzy.admin.fp.merchant.management.repository.MerchantUserRoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.domain.MerchantRole;
import com.fzy.admin.fp.merchant.management.domain.MerchantUserRole;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRoleRepository;
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
public class MerchantUserRoleService implements BaseService<MerchantUserRole> {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private MerchantUserRoleRepository merchantUserRoleRepository;

    @Override
    public MerchantUserRoleRepository getRepository() {
        return merchantUserRoleRepository;
    }

    public void deleteByUserId(String id) {
        merchantUserRoleRepository.deleteByUserId(id);
    }

    public void deleteByRoleId(String id) {
        merchantUserRoleRepository.deleteByRoleId(id);
    }

    public List<MerchantUserRole> findUserRoleByUserId(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r from MerchantUserRole ur , MerchantRole r where ur.roleId = r.id and ur.userId = ? and r.delFlag = ").append(CommonConstant.NORMAL_FLAG);
        Query query = em.createQuery(sb.toString(), MerchantRole.class).setParameter(1, id);
        List<MerchantUserRole> list = query.getResultList();
        return list;
    }


}