package com.fzy.admin.fp.auth.service;


import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
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
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

/*
 * @author drj
 * @date 2019-04-22 9:15
 * @Description :二级代理商管理服务层
 */
@Slf4j
@Service
@Transactional
public class DistribuService implements BaseService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;


    @Resource
    private MerchantBusinessService merchantServiceFeign;

    public CompanyRepository getRepository() {
        return companyRepository;
    }


    //获取二级代理商商列表
    public Page<Company> listRewrite(Company model, PageVo pageVo, String userId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Company> page = null;
        Specification specification = ConditionUtil.createSpecification(model);
        model.setType(Company.Type.DISTRIBUTUTORS.getCode());
        //查询当前登录用户对应的公司,若为一级代理商根据父id查询,服务商根据idPath查询
        User user = userService.findOne(userId);
        Company companyQuery = companyRepository.findOne(user.getCompanyId());
        if (Company.Type.OPERATOR.getCode().equals(companyQuery.getType())) {
            model.setParentId(companyQuery.getId());
            model.setManagerId(userId);
            page = findAll(specification, pageable);
        }
        if (Company.Type.PROVIDERS.getCode().equals(companyQuery.getType())) {
            //判断该用户为普通服务商,根据idpath查询,否则查询所有
            UserRole userRoleQuery = userRoleService.getRepository().findByUserId(userId);
            Role role = roleService.findOne(userRoleQuery.getRoleId());
            model.setIdPath(user.getCompanyId());
            if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
                model.setIdPath(user.getCompanyId());
            }
            page = findAll(specification, pageable);
        }
        //获取业务员名称,设置合作到期时间
        for (Company company : page) {
            User user1 = userService.findOne(company.getManagerId());
            company.setSaleName(user1.getName()); //归属业务员
            Company companyParet = companyRepository.findOne(company.getParentId());
            company.setParentName(companyParet.getName());
            company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));
        }
        return page;
    }

    //一级代理商详情查看二级代理商列表
    public Page<Company> findByParentId(Company model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Company> page = findAll(specification, pageable);
        if (page.getContent().size() < 1) {
            return page;
        }
        //获取业务员名称,设置合作到期时间
        User user = userService.findOne(page.getContent().get(0).getManagerId());
        for (Company company : page) {
            company.setSaleName(user.getName()); //一级代理商名称
        }
        return page;
    }


    //删除二级代理商
    public String logicalDel(String[] ids) {

        //查询一级代理商底下是否存在商户
        Integer merchantNum = merchantServiceFeign.findByCompanyId(ids[0]);
        if (merchantNum > 0) {
            throw new BaseException("删除失败,运营商有关联的商户信息", Resp.Status.PARAM_ERROR.getCode());
        }
        Company company = companyRepository.findOne(ids[0]);
        company.setDelFlag(CommonConstant.DEL_FLAG);
        companyRepository.save(company);
        //删除运营商对应的登录用户信息
        User user = userService.findByUsername(company.getPhone(), (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        if (user != null) {
            user.setDelFlag(CommonConstant.DEL_FLAG);
            userService.save(user);
        }

        return "操作成功";
    }

    //设置奖励
    public String setReward(String id, BigDecimal wechatReward, BigDecimal alipayReward) {

        Company company = companyRepository.findOne(id);
        if (null == wechatReward || null == alipayReward) {
            throw new BaseException("奖励值不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (BigDecimal.ZERO.compareTo(wechatReward) >= 1 || BigDecimal.ZERO.compareTo(alipayReward) >= 1) {
            throw new BaseException("奖励值必须大于0", Resp.Status.PARAM_ERROR.getCode());
        }
        if (wechatReward.compareTo(new BigDecimal("10000000000")) >= 1 || alipayReward.compareTo(new BigDecimal("10000000000")) >= 1) {
            throw new BaseException("最大值为10000000000", Resp.Status.PARAM_ERROR.getCode());
        }
        company.setWechatReward(wechatReward);
        company.setAlipayReward(alipayReward);
        companyRepository.save(company);
        return "操作成功";
    }


}