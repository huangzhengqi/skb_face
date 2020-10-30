package com.fzy.admin.fp.merchant.management.service;


import com.fzy.admin.fp.merchant.management.repository.MerchantRoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.domain.MerchantRole;
import com.fzy.admin.fp.merchant.management.repository.MerchantRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author zk
 * @description 角色表服务层
 * @create 2018-07-25 15:10:47
 **/
@Slf4j
@Service
@Transactional
public class MerchantRoleService implements BaseService<MerchantRole> {

    @Resource
    private MerchantRoleRepository merchantRoleRepository;

    @Override
    public MerchantRoleRepository getRepository() {
        return merchantRoleRepository;
    }

    /**
     * @author zk
     * @date 2018-08-13 20:26
     * @Description 判断该角色是否存在
     */
    public boolean countRoleId(String id) {
        boolean flag = merchantRoleRepository.countByIdAndDelFlag(id, CommonConstant.NORMAL_FLAG) > 0 ? true : false;
        return flag;
    }


}