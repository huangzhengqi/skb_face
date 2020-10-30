package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.RateSetDTO;
import com.fzy.admin.fp.auth.service.AgentPublicService;
import com.fzy.admin.fp.auth.service.CompanyCommonService;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * --PRM列表控制层
 * --版权所有-锋之云科技
 * --作者-hzq
 * --时间-2019-12-06
 */

@Slf4j
@RestController
@RequestMapping("/auth/company/agent")
public class AgentPublicController extends BaseController<Company> {

    @Resource
    private AgentPublicService agentPublicService;

    @Resource
    private CompanyService companyService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyCommonService companyCommonService;

    @Override
    public AgentPublicService getService() {
        return agentPublicService;
    }

    /**
     * 启用/禁用
     * @param id
     * @param status
     * @param type
     * @return
     */
    @GetMapping("/isable_type")
    public Resp<String> isable(@RequestParam(value = "id") String id,
                                @RequestParam(value = "status") Integer status,
                                @RequestParam(value = "type") Integer type) {
        Company company = companyService.findByIdAndType(id, type);
        if (company == null) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "操作失败");
        }
        company.setStatus(status);
        companyService.save(company);
        return Resp.success("", "操作成功");
    }

    /**
     * 查询代理商
     * @param model
     * @param pageVo
     * @param userId
     * @param mark
     * @return
     */
    @GetMapping("/list_rewrite_type")
    public Resp listRewriteType(Company model, PageVo pageVo, @UserId String userId, Integer mark ) {

        if(StringUtils.isEmpty(mark)){
            throw new BaseException("mark参数不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        return Resp.success(agentPublicService.listRewrite(model, pageVo, userId, mark));
    }

    /**
     * 添加代理商
     * @param entity
     * @param userId
     * @return
     */
    @PostMapping("/save_rewrite_type")
    public Resp saveType(Company entity, @UserId String userId){
        return Resp.success(companyService.saveAgent(entity, userId, entity.getType()));
    }

    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(companyService.detail(id));
    }


    @Override
    public Resp update(Company entity) {
        super.update(entity);
        if (!org.apache.commons.lang3.StringUtils.isEmpty(entity.getContact())) {
            //查询该代理对应登录用户信息
            User user = userService.findByUsername(entity.getPhone(), (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
            if (user == null) {
                return Resp.success("操作成功");
            }
            user.setName(entity.getContact());
            userService.save(user);
        }

        return Resp.success("操作成功");
    }

    /**
     * 提交或审核
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/audit_type")
    public Resp audit(String id, Integer status) {
        Company company = companyCommonService.findOne(id);

        return Resp.success(companyService.audit(id, status, company.getType()));
    }


    @PostMapping("/rate_set")
    @ApiOperation(value = "分润设置", notes = "分润设置")
    public Resp rateSet(@RequestBody RateSetDTO rateSetDTO) {
        return companyCommonService.rateSet(rateSetDTO);

    }


    @GetMapping("/rate")
    @ApiOperation(value = "获取分润设置", notes = "分润设置")
    public Resp<RateSetDTO> getRate(String id) {
        return companyCommonService.getRate(id);
    }


    @PostMapping("/advertise_set")
    @ApiOperation(value = "设置广告权限" ,notes = "设置广告权限")
    public Resp advertiseSet(String id, Integer type){
        return companyCommonService.advertiseSet(id,type);
    }

    @GetMapping("/advertise")
    @ApiOperation(value = "获取广告权限" ,notes = "获取广告权限")
    public Resp getAdvertise(String id){
        return companyCommonService.getAdvertise(id);
    }
}
