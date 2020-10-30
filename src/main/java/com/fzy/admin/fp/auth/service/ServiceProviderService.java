package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 20:10 2019/7/1
 * @ Description: 服务商服务层
 **/
@Slf4j
@Service
@Transactional
public class ServiceProviderService implements BaseService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserService userService;

    @Override
    public CompanyRepository getRepository() {
        return companyRepository;
    }


    //获取服务商列表
    public Page<Company> listRewrite(Company model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        model.setType(Company.Type.PROVIDERS.getCode());
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Company> page = findAll(specification, pageable);
        return page;
    }


    //改变服务商状态,已签约或注销
    @Transactional
    public String updateStatus(String id, Integer status) {
        Company company = companyRepository.findOne(id);
        if (ParamUtil.isBlank(company)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        //查询出该服务商对应的登录用户
        User user = userService.findByUsername(company.getPhone(), company.getId());
        //如果选择的是注销
        if (Company.Status.CANCELL.getCode().equals(status)) {
            user.setStatus(User.Status.DISABLE.getCode());
            company.setStatus(status);
        }
        //如果选择的是签约
        if (Company.Status.SIGNED.getCode().equals(status)) {
            user.setStatus(User.Status.ENABLE.getCode());
            company.setStatus(status);
        }
        userService.save(user);
        companyRepository.save(company);
        return "操作成功";
    }

}
