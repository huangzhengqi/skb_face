package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * PRM代理商管理服务层
 * --版权所有-锋之云科技
 * --作者-hzq
 * --时间-2019-12-06
 */

@Slf4j
@Service
@Transactional
public class AgentPublicService implements BaseService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Override
    public CompanyRepository getRepository() {
        return companyRepository;
    }

    public Page<Company> listRewrite(Company model, PageVo pageVo, String userId, Integer mark) {

        Pageable pageable = PageUtil.initPage(pageVo);

        Page<Company> page = null ;

        //查询全部
        if(mark.equals(5)){

            User user = userService.findOne(userId);
            Company userCompany = companyRepository.findOne(user.getCompanyId());

            //判断该用户为普通服务商,查询条件增加管理员id
            UserRole userRoleQuery = userRoleService.getRepository().findByUserId(userId);
            Role role = roleService.findOne(userRoleQuery.getRoleId());
            //判断是否是贴牌商
            if (userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
                model.setIdPath(userCompany.getId());
            } else {
                model.setIdPath(userCompany.getIdPath()+userCompany.getId());
            }
            if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
                model.setManagerId(userId);
            }
            //查询
            Specification specification = ConditionUtil.createSpecification(model);
            page = findAll(specification, pageable);
            //获取业务员名称,设置合作到期时间
            for (Company company : page) {
                User user1 = userService.findOne(company.getManagerId());
                //归属业务员
                company.setSaleName(user1.getName());
                Company companyParent = companyRepository.findOne(company.getParentId());
                company.setParentName(companyParent.getName());
                company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));
                if (userCompany.getId().equals(company.getParentId())) {
                    company.setIsDirect(1);
                } else {
                    company.setIsDirect(0);
                }
            }
        }else {
            model.setType(mark);
            //查询当前登录用户对应的公司,若为一级代理商根据父id查询,服务商根据idPath查询
            User user = userService.findOne(userId);
            Company userCompany = companyRepository.findOne(user.getCompanyId());

            //判断该用户为普通服务商,查询条件增加管理员id
            UserRole userRoleQuery = userRoleService.getRepository().findByUserId(userId);
            Role role = roleService.findOne(userRoleQuery.getRoleId());
            //判断是否是贴牌商
            if (userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
                model.setIdPath(userCompany.getId());
            } else {
                model.setIdPath(userCompany.getIdPath()+userCompany.getId());
            }
            if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
                model.setManagerId(userId);
            }
            //查询
            Specification specification = ConditionUtil.createSpecification(model);
            page = findAll(specification, pageable);
            //获取业务员名称,设置合作到期时间
            for (Company company : page) {
                User user1 = userService.findOne(company.getManagerId());
                //归属业务员
                company.setSaleName(user1.getName());
                Company companyParent = companyRepository.findOne(company.getParentId());
                company.setParentName(companyParent.getName());
                company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));
                if (userCompany.getId().equals(company.getParentId())) {
                    company.setIsDirect(1);
                } else {
                    company.setIsDirect(0);
                }
            }
        }
        return page;
    }
}
