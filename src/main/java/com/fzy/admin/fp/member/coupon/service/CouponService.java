package com.fzy.admin.fp.member.coupon.service;


import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.repository.CouponRepository;
import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.dto.CancelAfterVerificationListDTO;
import com.fzy.admin.fp.member.coupon.repository.CouponRepository;
import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/5/24 9:23
 * @Description
 */
@Service
public class CouponService implements BaseService<Coupon> {

    @Resource
    private CouponRepository couponRepository;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    @Resource
    private PersonCouponRepository personCouponRepository;

    @Override
    public CouponRepository getRepository() {
        return couponRepository;
    }

    public List<Coupon> findByMerchantId(String merchantId, Integer type) {
        return couponRepository.findByCardTypeAndMerchantIdAndDelFlag(type, merchantId, 1);
    }

    public List<Coupon> findByMerchantId(String merchantId,Integer cardType,Integer type){
        return  couponRepository.findByCardTypeAndTypeAndMerchantIdAndDelFlag(cardType,type,merchantId,1);
    }

    public List<Coupon> findByMerchantIdAndName(String merchantId, String name) {
        int del = 1;
        return couponRepository.findByMerchantIdAndNameAndDelFlag(merchantId, name, del);
    }

    public List<Coupon> findByType(String merchantId, Integer type) {
        return couponRepository.findByMerchantIdAndCardTypeAndDelFlag(merchantId, type, 1);
    }

    public List<PersonCoupon> cancelAfterVerificationList(CancelAfterVerificationListDTO req) {

        List<PersonCoupon> personCoupons = personCouponRepository.findByMerchantIdAndStatus(req.getMerchantId(), PersonCoupon.Status.USE.getCode());
        String[] ids = getIds(personCoupons);
        if (req.getStart_createTime() != null && req.getEnd_createTime() != null) {
            personCoupons = personCouponRepository.findByValidTimeBetweenAndIdIn(req.getStart_createTime(), req.getEnd_createTime(), ids);
            ids = getIds(personCoupons);
        }
        if (req.getCoupon() != null) {
            personCoupons = personCouponRepository.findByIdLikeAndIdInOrNameLikeAndIdIn("%" + req.getCoupon() + "%", ids, "%" + req.getCoupon() + "%", ids);
        }

        return personCoupons;
    }

    private String[] getIds(List<PersonCoupon> personCoupons) {
        List<String> collect = personCoupons.stream().map(BaseEntity::getId).collect(Collectors.toList());
        return collect.toArray(new String[collect.size()]);
    }

    public Page<Coupon> fingByActIn(String merchantId, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        return couponRepository.findByMerchantIdAndCardTypeAndDelFlagAndActStatusIn(merchantId, 1, 1, 1, 2, pageable);
    }

    /**
     * 修改代金券库存接口
     * @param type
     * @param userId
     * @param number
     * @param cardId
     */
    public String updateCardModifystock(Integer type, String userId, Integer number, String cardId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        String appId = aliConfigServiceFeign.getAppId(merchantUser.getMerchantId());
        String appSecret = aliConfigServiceFeign.getAppSecret(merchantUser.getMerchantId());
        WXUtil.getAccessToken(appId, appSecret);
        return WXUtil.cardModifystock(type, number, cardId);
    }
}
