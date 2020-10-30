package com.fzy.admin.fp.goods.controller;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.PayRule;
import com.fzy.admin.fp.goods.repository.PayRuleRepository;
import com.fzy.admin.fp.goods.service.PayRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/merchant/member/payrule"})
@Slf4j
@Api(value = "PayRuleController", tags = {"会员中心-会员支付设置"})
public class PayRuleController extends BaseController<PayRule> {

    @Autowired
    private PayRuleService payRuleService;

    @Autowired
    private PayRuleRepository payRuleRepository;

    @Override
    public PayRuleService getService() {
        return payRuleService;
    }



    @ApiOperation(value = "获取分页", notes = "获取分页")
    @GetMapping({""})
    public Resp<PayRule> getPayRule() {
        String merchantId = TokenUtils.getMerchantId();
        PayRule payRule = this.payRuleRepository.findByMerchantId(merchantId);
        return Resp.success(payRule);
    }

    @ApiOperation(value = "支付设置", notes = "支付设置")
    @PostMapping({""})
    public Resp<String> savePayRule(@RequestBody PayRule payRule) {
        String merchantId = TokenUtils.getMerchantId();
        PayRule oldRayRule = this.payRuleRepository.findByMerchantId(merchantId);
        if (oldRayRule != null) {
            payRule.setId(oldRayRule.getId());
        }
        payRule.setMerchantId(merchantId);
        this.payRuleService.save(payRule);
        return Resp.success("操作成功");
    }
}
