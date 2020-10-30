package com.fzy.admin.fp.member.app.controller;


import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.app.vo.AppStoreRuleVO;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Created by wtl on 2019-06-11 10:02
 * @description App储值规则接口
 */
@RestController
@RequestMapping("/member/store_rule/app")
public class AppStoreRuleController extends BaseContent {

    @Resource
    private StoredRuleService storedRuleService;

    @Resource
    private CouponService couponService;

    //根据商户id获取储值规则
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(@TokenInfo(property = "merchantId") String merchantId) {
        List<StoredRule> storedRules = storedRuleService.getRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        List<AppStoreRuleVO> appStoreRuleVOS = new LinkedList<>();
        for (StoredRule storedRule : storedRules) {
            AppStoreRuleVO appStoreRuleVO = new AppStoreRuleVO();
            String des = "";
            // 赠送数值
            BigDecimal giftMoney = storedRule.getGiftMoney();
            // 赠送类型
            Integer giftType = storedRule.getGiftType();

            if (StoredRule.GiftType.MONEY.getCode().equals(giftType)) {
                des = "送￥" + giftMoney;
            } else if (StoredRule.GiftType.INTEGRAL.getCode().equals(giftType)) {
                des = "送" + giftMoney + "积分";
            } else {
                Coupon coupon = couponService.getRepository().findOne(storedRule.getCouponId());
                if (ParamUtil.isBlank(coupon)) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "获取赠送的卡券失败");
                }
                des = "送" + coupon.getMoney() + "元代金券卡券";
            }
            appStoreRuleVO.setStoreRuleId(storedRule.getId());
            appStoreRuleVO.setStoredMoney(storedRule.getStoredMoney());
            appStoreRuleVO.setName(storedRule.getName());
            appStoreRuleVO.setDescription(des);
            appStoreRuleVOS.add(appStoreRuleVO);
        }
        return Resp.success(appStoreRuleVOS);
    }

}
