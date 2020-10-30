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
import com.fzy.admin.fp.auth.service.DistribuService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:59 2019/4/24
 * @ Description: 二级代理商控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/company/distribute")
@Api(value = "DistribuController",tags = "二级代理商控制层")
public class DistribuController extends BaseController<Company> {


    @Resource
    private CompanyCommonService companyCommonService;

    @Resource
    private DistribuService distribuService;

    @Resource
    private CompanyService companyService;

    @Resource
    private UserService userService;

    @Override
    public DistribuService getService() {
        return distribuService;
    }


    @GetMapping("/isable")
    public Resp<String> listRewrite(@ApiParam(value = "公司id") @RequestParam(value = "id") String id,
                                    @ApiParam(value = "启用3 禁用5") @RequestParam(value = "status") Integer status) {
        Company company = companyService.findByIdAndType(id,Company.Type.DISTRIBUTUTORS.getCode());
        if (company == null) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"操作失败");
        }
        company.setStatus(status);
        companyService.save(company);
        return Resp.success("","操作成功");
    }



    @GetMapping("/list_rewrite")
    public Resp listRewrite(Company model, PageVo pageVo, @UserId String userId) {
        return Resp.success(companyCommonService.listRewrite(model, pageVo, userId,Company.Type.DISTRIBUTUTORS.getCode()));
    }

    @GetMapping("/list_rewrite_count")
    public Resp listRewriteCount(Company model, @UserId String userId) {
        return Resp.success(companyCommonService.listRewriteCount(model, userId, Company.Type.DISTRIBUTUTORS.getCode()));
    }

    @PostMapping("/save_rewrite")
    public Resp saveRewrite(@Valid Company model, @UserId String userId) {
        // 校验分佣比例
//        if (model.getPayProrata().compareTo(BigDecimal.ZERO) < 0) {
//            throw new BaseException("分佣比例不能小于0", Resp.Status.PARAM_ERROR.getCode());
//        }
        return Resp.success(companyService.saveAgent(model, userId, Company.Type.DISTRIBUTUTORS.getCode()));
    }

    @Override
    public Resp update(Company entity) {
        super.update(entity);
        //查询该代理对应登录用户信息
        User user = userService.findByUsername(entity.getPhone(), (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        if (user != null) {
            user.setName(entity.getContact());
            userService.save(user);
        }
        return Resp.success("操作成功");
    }

    /*
     * @author drj
     * @date 2019-04-24 15:15
     * @Description 一级代理商登录点击详情查看二级代理商列表
     */

    @GetMapping("/find_by_parent_id")
    public Resp findByParentId(Company entity, PageVo pageVo) {
        return Resp.success(distribuService.findByParentId(entity, pageVo));
    }


    /*
     * @author drj
     * @date 2019-04-25 10:43
     * @Description ：删除二级代理商
     */
    @Override
    public Resp logicalDel(String[] ids) {
        return Resp.success(distribuService.logicalDel(ids));
    }


    /*
     * @author drj
     * @date 2019-04-25 11:12
     * @Description :设置奖励
     */
    @PostMapping("/set_reward")
    public Resp setReward(String id, BigDecimal wechatReward, BigDecimal alipayReward) {
        return Resp.success(distribuService.setReward(id, wechatReward, alipayReward));
    }


    /*
     * @author drj
     * @date 2019-04-24 20:08
     * @Description :审核或拒绝审核
     */
    @PostMapping("/audit")
    public Resp audit(String id, Integer status) {
        return Resp.success(companyService.audit(id, status, Role.Type.DISTRIBUTUTORS.getCode()));
    }


    /*
     * @author drj
     * @date 2019-05-06 16:33
     * @Description :查看二级代理商详情
     */
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
}
