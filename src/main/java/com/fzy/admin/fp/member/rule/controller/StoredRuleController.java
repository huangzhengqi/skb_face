package com.fzy.admin.fp.member.rule.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:45 2019/5/14
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/member/stored_rule")
@Api(value = "StoredRuleController",tags = {"储存规则控制层"})
public class StoredRuleController extends BaseController<StoredRule> {

    @Resource
    private StoredRuleService storedRuleService;


    @Override
    public StoredRuleService getService() {
        return storedRuleService;
    }


    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(StoredRule model, PageVo pageVo, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRuleService.findByMerchantId(model, pageVo, merchantId));
    }


    /**
     * 添加储值规则
     * @param model
     * @param merchantId
     * @return
     */
    @ApiOperation(value = "添加储值规则",notes = "添加储值规则")
    @PostMapping("/save_rewrite")
    public Resp saveRewrite(@Valid StoredRule model, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRuleService.saveRewrite(model, merchantId));
    }

    /**
     * 启用或禁用
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "启用或禁用",notes = "启用或禁用")
    @PostMapping("/update_stored_rule")
    public Resp updateStoredRule(String id, Integer status) {
        return Resp.success(storedRuleService.updateStoredRule(id, status));
    }


    /**
     * 后台显示所有规则
     * @param model
     * @param pageVo
     * @param merchantId
     * @return
     */
    @ApiOperation(value = "后台显示所有规则",notes = "后台显示所有规则")
    @GetMapping(value = "/list_re")
    public Resp getAllRule(StoredRule model, PageVo pageVo, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRuleService.findAllRule(model, pageVo, merchantId));
    }
}
