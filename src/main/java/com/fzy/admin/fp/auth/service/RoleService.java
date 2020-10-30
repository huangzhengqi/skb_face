package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.RoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.RoleRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zk
 * @description 角色表服务层
 * @create 2018-07-25 15:10:47
 **/
@Slf4j
@Service
@Transactional
public class RoleService implements BaseService<Role> {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private CompanyRepository companyRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }

    /**
     * @author zk
     * @date 2018-08-13 20:26
     * @Description 判断该角色是否存在
     */
    public boolean countRoleId(String id) {
        boolean flag = roleRepository.countByIdAndDelFlag(id, CommonConstant.NORMAL_FLAG) > 0 ? true : false;
        return flag;
    }

    public List<Role> findByDefaultRole(boolean isDefault) {
        return roleRepository.findByDefaultRoleAndDelFlag(isDefault, CommonConstant.NORMAL_FLAG);
    }

    public List<Role> findBySourceRoleIdIn(List<String> sourceIds) {
        return roleRepository.findBySourceRoleIdIn(sourceIds);
    }

    public List<SelectItem> selectItemByTypeAndKind(String companyId, Integer kind) {
        Company company = companyRepository.findOne(companyId);
        if (ParamUtil.isBlank(companyId)) {
            throw new BaseException("参数异常", Resp.Status.PARAM_ERROR.getCode());
        }
        return roleRepository.selectItemByTypeAndKind(company.getType(), kind);
    }

    public Role findBySourceRoleId(String sourceRoleId){
        return roleRepository.findBySourceRoleId(sourceRoleId);
    }

    public Role findBySourceRoleIdAndCompanyIdAndDelFlag(String sourceRoleId,String companyId,Integer delFalg){
        return roleRepository.findBySourceRoleIdAndCompanyIdAndDelFlag(sourceRoleId,companyId,delFalg);
    }

    public List<Role> findByCompanyIdInAndSourceRoleIdAndAuthCompanyId(List<String> companyIds,String sourceRoleId,String authCompanyId) {
        return roleRepository.findByCompanyIdInAndSourceRoleIdAndAuthCompanyId(companyIds,sourceRoleId,authCompanyId);
    }
}