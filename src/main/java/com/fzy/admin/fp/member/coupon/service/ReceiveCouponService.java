package com.fzy.admin.fp.member.coupon.service;


import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/27 16:14
 * @Description 领取卡券
 */
@Service
@Transactional
public class ReceiveCouponService {

    @Resource
    private CouponService couponService;

    @Resource
    private PersonCouponService personCouponService;

    public Resp receiveCoupon(String merchantId, ReceiveCoupon receiveCoupon) {

        //判断条件
        Coupon coupon = couponService.findOne(receiveCoupon.getCouponId());
        List<PersonCoupon> personCouponList = personCouponService.findByCouponId(merchantId, receiveCoupon.getMemberId(), receiveCoupon.getCouponId());
        if (coupon == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券编号不存在");
        } else if (coupon.getChangeInventory() + 1 > coupon.getTotalInventory()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券数量不足，无法兑换");
        } else if (coupon.getActTimeEnd().getTime() < System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "活动时间已过，无法兑换");
        } else if (coupon.getActTimeStart().getTime() > System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "活动时间未开始，无法兑换");
        } else if (personCouponList.size() >= coupon.getClaimUpperLimit()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "兑换达到限制，无法兑换");
        } else if (coupon.getValidType() == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券兑换类型出错");
        }
        //1.卡券活动扣库存
        coupon.setChangeInventory(coupon.getChangeInventory() + 1);
        couponService.save(coupon);
        //2.用户得到卡券
        PersonCoupon personCoupon = new PersonCoupon();
        personCoupon.setMerchantId(coupon.getMerchantId());
        personCoupon.setCouponId(coupon.getId());
        personCoupon.setMemberId(receiveCoupon.getMemberId());
        personCoupon.setMoney(coupon.getMoney());
        personCoupon.setPhotoId(coupon.getPhotoId());
        personCoupon.setRemark(coupon.getRemark());
        personCoupon.setMiniExpendLimit(coupon.getMiniExpendLimit());
        personCoupon.setUseTimeWeek(coupon.getUseTimeWeek());
        personCoupon.setUseTimeDay(coupon.getUseTimeDay());
        personCoupon.setClaimUpperLimit(coupon.getClaimUpperLimit());
        personCoupon.setStoreIds(coupon.getStoreIds());
        personCoupon.setName(coupon.getName());

        //判断卡券活动是否有提醒功能
        if (coupon.getRemindType().equals(Coupon.RemindType.EFFECTIVE.getCode())) {
            personCoupon.setRemindTimes(1);
        } else {
            personCoupon.setRemindTimes(0);
        }
       /* String random= RandomUtil.randomNumbers(13);
        personCoupon.setCode(random);*/
        if (coupon.getValidType() == 0) {
            Date a = new Date();
            //System.out.println(a);
            personCoupon.setValidTimeStart(a);
            int day = coupon.getClaimedTime();
            Long b = System.currentTimeMillis();
            // System.out.println("----->>>"+b);
            b = b + day * 24 * 60 * 60 * 1000;
            // System.out.println("----->>>"+b);
            Date c = new Date(b);
            personCoupon.setValidTimeEnd(c);
        } else if (coupon.getValidType() == 1) {
            personCoupon.setValidTimeStart(coupon.getValidTimeStart());
            personCoupon.setValidTimeEnd(coupon.getValidTimeEnd());
        }
        personCoupon.setStatus(PersonCoupon.Status.NO_USE.getCode());
        //生成个人卡券时处理的同步问题
        if (coupon.getCouponSourceType().equals(Coupon.CouponSourceType.SYNC.getCode())) {
            //标记为 1
            personCoupon.setSynStatus(PersonCoupon.SynStatus.NO_SYN.getCode());
        } else {
            //标记为 0
            personCoupon.setSynStatus(PersonCoupon.SynStatus.LIMIT.getCode());
        }

        System.out.println("------>>>" + personCoupon);
        PersonCoupon personCoupon1 = personCouponService.save(personCoupon);
        //核销码采用个人卡券Id形式
        personCoupon1.setCode(personCoupon1.getId());
        PersonCoupon personCoupon2 = personCouponService.save(personCoupon1);
        return Resp.success(personCoupon2.getId(), "领取卡券成功！");
    }

}
