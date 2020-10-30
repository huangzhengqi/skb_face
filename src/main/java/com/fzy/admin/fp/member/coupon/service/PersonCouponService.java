package com.fzy.admin.fp.member.coupon.service;


import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.coupon.domain.CouponTest;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.dto.DayNum;
import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/27 11:37
 * @Description
 */
@Service
public class PersonCouponService implements BaseService<PersonCoupon> {

    @Resource
    private PersonCouponRepository personCouponRepository;

    @Override
    public PersonCouponRepository getRepository() {
        return personCouponRepository;
    }

    public List<PersonCoupon> findAll(String merchantId, String memberId) {
        return personCouponRepository.findByMerchantIdAndMemberId(merchantId, memberId);
    }

    public List<PersonCoupon> findByCouponId(String merchantId, String memberId, String couponId) {
        return personCouponRepository.findByMerchantIdAndMemberIdAndCouponId(merchantId, memberId, couponId);
    }

    public List<PersonCoupon> findByMerchntIdAndCouponIdAndStatus(String merchantId, String couponId, Integer status) {
        return personCouponRepository.findByMerchantIdAndCouponIdAndStatus(merchantId, couponId, status);
    }

    /**
     * 查询会员们的用券次数
     */
    public List<CouponTest> getByCount(String merchantId, Integer status) {
        return personCouponRepository.getPersonCoupons(merchantId, status);
    }

    /**
     * 获取时间数量
     */
    public List<DayNum> getDays(String merchantId, String couponId) {
        return personCouponRepository.getDays(merchantId, couponId);
    }

    public PersonCoupon findByMerchantIdAndStatusAndCode(String merchantId, Integer status, String code) {
        return personCouponRepository.findByMerchantIdAndStatusAndCode(merchantId, status, code);
    }

}
