package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.ServiceProviderService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 20:03 2019/7/1
 * @ Description: 服务商控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/company/service_provider")
@Api(value = "ServiceProviderController",tags = "服务商控制层")
public class ServiceProviderController extends BaseController<Company> {

    @Resource
    private ServiceProviderService serviceProviderService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Override
    public ServiceProviderService getService() {
        return serviceProviderService;
    }

    @PostMapping("/save_rewrite")
    public Resp saveRewrite(Company entity, @UserId String userId) {
        entity.setPayProrata(BigDecimal.ZERO);
        Validation.valid(entity);
        entity.setStatus(Company.Status.SIGNED.getCode()); //设置状态为已签约

        return Resp.success(companyService.saveRewrite(entity, userId, Role.Type.PROVIDERS.getCode()));
    }
    @Override
    public Resp update(Company entity) {
        super.update(entity);
        //查询该贴牌对应登录用户信息
        User user = userService.findByUsername(entity.getPhone(), (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        user.setName(entity.getContact());
        userService.save(user);
        return Resp.success("操作成功");
    }

    @GetMapping("/list_rewrite")
    public Resp listRewrite(Company entity, PageVo pageVo) {
        return Resp.success(serviceProviderService.listRewrite(entity, pageVo));
    }

    /*
     * @author drj
     * @date 2019-07-01 21:22
     * @Description :修改状态
     */
    @PostMapping("/update_status")
    public Resp updateStatus(String id, Integer status) {
        return Resp.success(serviceProviderService.updateStatus(id, status));
    }

}
