package com.fzy.admin.fp.member.credits.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;
import com.fzy.admin.fp.member.credits.service.CreditsRulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author lb
 * @date 2019/5/14 14:13
 * @Description 积分规则
 */

@RestController
@Slf4j
@RequestMapping(value = "/member/credits_ruler")
public class CreditsRulerController extends BaseController<CreditsRuler> {

    @Resource
    private CreditsRulerService creditsRulerService;

    @Override
    public BaseService getService() {
        return creditsRulerService;
    }


    /**
     * 根据传来的商户id返回该商户的积分规则
     *
     * @param merchantId
     * @return
     */
    @GetMapping(value = "/find")
    @Transactional
    public Resp getRuler(@TokenInfo(property = "merchantId") String merchantId) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误!");
        }
        log.info("进入该接口获取id" + merchantId);
        CreditsRuler creditsRuler = creditsRulerService.findByMerchantId(merchantId);
        log.info("查询结果对象" + creditsRuler);
        //如果商户还没有积分规则默认添加一条规则写入数据库
        if (creditsRuler == null) {
            log.info("判断进入");
            CreditsRuler creditsRuler1 = new CreditsRuler();
            creditsRuler1.setCredits(0);
            creditsRuler1.setMerchantId(merchantId);
            creditsRuler1.setConsumptionAmount(new BigDecimal(0));
            creditsRuler1.setIsTrue(1);
            creditsRuler = creditsRulerService.save(creditsRuler1);
        }
        return Resp.success(creditsRuler);
    }

}
