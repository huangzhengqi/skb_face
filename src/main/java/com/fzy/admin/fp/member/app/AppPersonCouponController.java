package com.fzy.admin.fp.member.app;


import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.CheckCodeService;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.CheckCodeService;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author lb
 * @date 2019/6/18 15:37
 * @Description
 */
@RestController
@RequestMapping(value = "/member/person_coupon/app")
public class AppPersonCouponController extends BaseController<PersonCoupon> {

    @Resource
    private PersonCouponService personCouponService;

    @Resource
    private CheckCodeService checkCodeService;

    @Resource
    private CouponService couponService;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    @Override
    public BaseService<PersonCoupon> getService() {
        return personCouponService;
    }


    @GetMapping(value = "/find_person_coupon")
    public Resp getPersonCouponInfo(@TokenInfo(property = "merchantId") String merchantId, PersonCoupon personCoupon,
                                    PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        //查找所有个人卡券，对有效状态进行更新
        List<PersonCoupon> personCoupons = personCouponService.findAll(merchantId, personCoupon.getMemberId());
        for (PersonCoupon personCouponl : personCoupons) {
            if (!personCouponl.getStatus().equals(PersonCoupon.Status.USE.getCode())) {
                if (personCouponl.getValidTimeEnd().getTime() < System.currentTimeMillis()) {
                    personCouponl.setStatus(PersonCoupon.Status.INVALID.getCode());
                    personCouponService.save(personCouponl);
                }
            }
        }
        return list(personCoupon, pageVo);
    }


    //卡券核销接口
    @PostMapping(value = "/check_code")
    @Transactional
    public Resp checkCode(@TokenInfo(property = "merchantId") String merchantId, String code) {
        checkCodeService.checkCode(merchantId, code);
        return Resp.success("卡券使用成功");
    }

}
