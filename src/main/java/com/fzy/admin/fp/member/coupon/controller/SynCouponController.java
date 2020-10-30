package com.fzy.admin.fp.member.coupon.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.coupon.service.SynCouponService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lb
 * @date 2019/5/29 9:15
 * @Description 同步至微信卡包
 */

@RestController
@RequestMapping(value = "/member/syn_coupon")
public class SynCouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private PersonCouponService personCouponService;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    @Resource
    private SynCouponService synCouponService;

    @GetMapping(value = "/syn")
    public Resp synCounpon(@TokenInfo(property = "merchantId") String merchantId, String couponId, String personCouponId) {
        Coupon coupon = couponService.findOne(couponId);
        PersonCoupon personCoupon = personCouponService.findOne(personCouponId);
        if (coupon == null || personCoupon == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券与个人卡券信息不匹配");
        }
        String cardId = coupon.getCardId();
        if (cardId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该卡券不支持同步微信卡包");
        } else if (personCoupon.getSynStatus() == null || personCoupon.getSynStatus().equals(PersonCoupon.SynStatus.SYN.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "已经同步，无法再同步");
        }
        System.out.println("------->>" + couponId + "----" + personCouponId);

        String appId = aliConfigServiceFeign.getAppId(merchantId);
        String appSecret = aliConfigServiceFeign.getAppSecret(merchantId);

        Map<String, String> maps = synCouponService.synWX(cardId, personCouponId, appId, appSecret);

        return Resp.success(maps);

    }

}
