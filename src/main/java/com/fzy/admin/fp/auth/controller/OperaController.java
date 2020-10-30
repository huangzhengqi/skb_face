package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.RateSetDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.OperaService;
import com.fzy.admin.fp.auth.service.CompanyCommonService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:16 2019/4/22
 * @ Description:一级代理商控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/company/opera")
@Api(value = "OperaController", tags = "一级代理商控制层")
public class OperaController extends BaseController<Company> {


    @Resource
    private OperaService operaService;

    @Resource
    private CompanyService companyService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyCommonService companyCommonService;

    @Override
    public OperaService getService() {
        return operaService;
    }


    @Override
    public Resp save(Company entity) {
        return null;
    }


    @GetMapping("/isable")
    public Resp<String> isable(@ApiParam(value = "公司id") @RequestParam(value = "id") String id,
                               @ApiParam(value = "启用3 禁用5") @RequestParam(value = "status") Integer status) {
        Company company = companyService.findByIdAndType(id, Company.Type.OPERATOR.getCode());
        if (company == null) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "操作失败");
        }
        company.setStatus(status);
        companyService.save(company);
        return Resp.success("", "操作成功");
    }

    @GetMapping("/list_rewrite")
    public Resp listRewrite(Company model, PageVo pageVo, @UserId String userId) {
        return Resp.success(companyCommonService.listRewrite(model, pageVo, userId, Company.Type.OPERATOR.getCode()));
    }

    @GetMapping("/list_rewrite_type")
    public Resp listRewriteType(Company model, PageVo pageVo, @UserId String userId,Integer type ) {
        return Resp.success(companyCommonService.listRewrite(model, pageVo, userId, type));
    }

    @GetMapping("/list_rewrite_count")
    public Resp listRewriteCount(Company model, @UserId String userId) {
        return Resp.success(companyCommonService.listRewriteCount(model, userId, Company.Type.OPERATOR.getCode()));
    }
    /*
     * @author drj
     * @date 2019-04-22 10:32
     * @Description :添加一级代理商
     */
    @PostMapping("/save_rewrite")
    public Resp save(@Valid Company entity, @UserId String userId) {
        // 校验分佣比例
//        if (entity.getPayProrata().compareTo(BigDecimal.ZERO) < 0) {
//            throw new BaseException("分佣比例不能小于0", Resp.Status.PARAM_ERROR.getCode());
//        }
        return Resp.success(companyService.saveAgent(entity, userId, Company.Type.OPERATOR.getCode()));
    }

    @PostMapping("/save_rewrite_type")
    public Resp saveType(Company entity, @UserId String userId){
        return Resp.success(companyService.saveAgent(entity, userId, entity.getType()));
    }

    /*
     * @author drj
     * @date 2019-04-22 11:22
     * @Description :获取一级代理商列表
     */
    @Override
    public Resp list(Company entity, PageVo pageVo) {
        return Resp.success(operaService.list(entity, pageVo));
    }


    /*
     * @author drj
     * @date 2019-04-22 11:22
     * @Description:删除一级代理商
     */
    @Override
    public Resp logicalDel(String[] ids) {
        return Resp.success(operaService.logicalDel(ids));
    }


    @GetMapping("/find_distribute_list")
    public Resp findDistributeList(String companyId) {
        return Resp.success(operaService.findCompanyUsers(companyId));
    }

    @Override
    public Resp update(Company entity) {
        super.update(entity);
        if (!StringUtils.isEmpty(entity.getContact())) {
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

    /*
     * @author drj
     * @date 2019-04-24 11:47
     * @Description :审核或提交审核
     */
    @PostMapping("/audit")
    public Resp audit(String id, Integer status) {

        return Resp.success(companyService.audit(id, status, Role.Type.OPERATOR.getCode()));
    }

    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(companyService.detail(id));
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

    /**
     * 详情页代理商列表
     *
     * @param entity
     * @param pageVo
     * @return
     */
    @GetMapping("/find_by_parent_id")
    public Resp findByParentId(Company entity, PageVo pageVo) {
        return Resp.success(companyCommonService.findByParentId(entity, pageVo));
    }
}
