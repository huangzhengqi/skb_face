package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * @author drj
 * @date 2019-04-22 9:15
 * @Description :一级代理商管理服务层
 */
@Slf4j
@Service
@Transactional
public class OperaService implements BaseService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserService userService;

    @Resource
    private MerchantBusinessService merchantBusinessService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;


    @Value("${secret.header}")
    private String header;

    @Resource
    private HttpServletRequest request;

    public CompanyRepository getRepository() {
        return companyRepository;
    }


    //获取一级代理商列表
    public Page<Company> list(Company model, PageVo pageVo) {

        //获取当前登录用户id
        String header1 = request.getHeader(header);
        final Map<String, Object> payloadMap = JwtUtil.getPayloadMap(header1);
        String currentUserId = (String) payloadMap.get("sub");
        model.setIdPath((String) payloadMap.get("companyId"));
        //判断该用户是否为普通用户,若是普通用户查询自己发展的运营商信息
        UserRole userRoleQuery = userRoleService.getRepository().findByUserId(currentUserId);
        Role role = roleService.findOne(userRoleQuery.getRoleId());
        if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
            model.setManagerId(currentUserId);
        }
        Pageable pageable = PageUtil.initPage(pageVo);
        model.setType(Company.Type.OPERATOR.getCode());
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Company> page = findAll(specification, pageable);
        if (page.getContent().size() < 1) {
            return page;
        }
        for (Company company : page) {
            //获取业务员名称
            User user = userService.findOne(company.getManagerId());
            company.setSaleName(user.getName());
            //到期时间+1年
            company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));//合作到期
        }
        return page;
    }

    //删除一级代理商
    public String logicalDel(String[] ids) {
        //查询一级代理商底下是否存在二级代理商
        List<Company> companyList = companyRepository.findByParentIdAndDelFlag(ids[0], CommonConstant.NORMAL_FLAG);
        if (companyList.size() > 0) {
            throw new BaseException("删除失败,一级代理商有关联的二级代理商信息", Resp.Status.PARAM_ERROR.getCode());
        }
        //查询一级代理商底下是否存在商户
        Integer merchantNum = merchantBusinessService.findByCompanyId(ids[0]);
        if (merchantNum > 0) {
            throw new BaseException("删除失败,一级代理商有关联的商户信息", Resp.Status.PARAM_ERROR.getCode());
        }
        Company company = companyRepository.findOne(ids[0]);
        company.setDelFlag(CommonConstant.DEL_FLAG);
        companyRepository.save(company);
        //删除运营商对应的登录用户信息
        User user = userService.findByUsername(company.getPhone(), (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        user.setDelFlag(CommonConstant.DEL_FLAG);
        userService.save(user);
        return "操作成功";
    }

    //查询业务员列表
    public List<User> findCompanyUsers(String companyId) {
        return userService.getRepository().findByCompanyId(companyId);
    }


}