package com.fzy.admin.fp.member.coupon.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.coupon.service.ReceiveCouponService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/5/27 16:12
 * @Description 领取卡券
 */
@RestController
@RequestMapping(value = "/member/receive")
public class ReceiveCouponController {
    @Resource
    private ReceiveCouponService receiveCouponService;

    @GetMapping(value = "/receive_coupon")
    public Resp receive(@TokenInfo(property = "merchantId") String merchantId, ReceiveCoupon receiveCoupon) {
        if (merchantId == null || receiveCoupon.getMemberId() == null || "".equals(receiveCoupon.getMemberId().trim()) || receiveCoupon.getCouponId() == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        return receiveCouponService.receiveCoupon(merchantId, receiveCoupon);
    }
}
