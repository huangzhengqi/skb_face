package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.SnConfig;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.SnConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:37 2019/6/13
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/sn_config")
public class SnConfigController extends BaseController<SnConfig> {

    @Resource
    private SnConfigService snConfigService;

    @Resource
    private MerchantService merchantService;


    @Override
    public SnConfigService getService() {
        return snConfigService;
    }


    @GetMapping("/list_rewrite")
    public Resp listRewrite(SnConfig entity, PageVo pageVo, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        entity.setServiceProviderId(companyId);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<SnConfig> page = snConfigService.findAll(specification, pageable);
        for (SnConfig snConfig : page) {
            snConfig.setMerchantName(merchantService.findOne(snConfig.getMerchantId()).getName());
        }
        return Resp.success(page);
    }


    @PostMapping("/save_rewrite")
    public Resp saveRewrite(SnConfig model, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        if (ParamUtil.isBlank(model.getSn())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "sn码不能为空");
        }
        if (ParamUtil.isBlank(model.getMerchantId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择商户");
        }
        SnConfig snConfig = snConfigService.getRepository().findBySnAndDelFlag(model.getSn(), CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(snConfig)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "sn码已存在,请勿重复添加");
        }
        Merchant merchant = merchantService.findOne(model.getMerchantId());
        model.setMerchantName(merchant.getName());
        model.setServiceProviderId(companyId); //保存服务商id
        snConfigService.save(model);
        return Resp.success("添加成功");
    }
}
