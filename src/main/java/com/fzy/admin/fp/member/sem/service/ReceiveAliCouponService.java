package com.fzy.admin.fp.member.sem.service;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.sem.domain.MemberCoupon;
import com.fzy.admin.fp.member.sem.domain.PersonAliCoupon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReceiveAliCouponService {

    @Resource
    private MemberCouponService memberCouponService;

    @Resource
    private PersonAliCouponService personAliCouponService;

    public Resp receiveCoupon(String merchantId, String memberId,String couponId) {

        //判断条件
        MemberCoupon memberCoupon = memberCouponService.findOne(couponId);
        List<PersonAliCoupon> personCouponList = personAliCouponService.findByCouponId(merchantId, memberId, couponId);
        if (null == memberCoupon) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "编号不存在");
        } else if (memberCoupon.getChangeInventory() + 1 > memberCoupon.getTotalInventory()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券库存不足，无法兑换");
        } else if (memberCoupon.getActTimeEnd().getTime() < System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "活动已过期，无法兑换");
        } else if (memberCoupon.getActTimeStart().getTime() > System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "活动未开始，无法兑换");
        } else if (personCouponList.size() >= memberCoupon.getClaimUpperLimit()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "兑换达到限制，无法兑换");
        } else if (memberCoupon.getValidType() == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券兑换类型出错");
        }

        //1.卡券活动扣库存
        memberCoupon.setChangeInventory(memberCoupon.getChangeInventory() + 1);
        memberCouponService.save(memberCoupon);
        //2.用户得到卡券
        PersonAliCoupon personAliCoupon = new PersonAliCoupon();
        personAliCoupon.setMerchantId(memberCoupon.getMerchantId());
        personAliCoupon.setCouponId(memberCoupon.getId());
        personAliCoupon.setMemberId(memberId);
        personAliCoupon.setMoney(memberCoupon.getMoney());
        personAliCoupon.setPhotoId(memberCoupon.getPhotoId());
        personAliCoupon.setRemark(memberCoupon.getRemark());
        personAliCoupon.setMiniExpendLimit(memberCoupon.getMiniExpendLimit());
        personAliCoupon.setUseTimeWeek(memberCoupon.getUseTimeWeek());
        personAliCoupon.setUseTimeDay(memberCoupon.getUseTimeDay());
        personAliCoupon.setClaimUpperLimit(memberCoupon.getClaimUpperLimit());
        personAliCoupon.setStoreIds(memberCoupon.getStoreIds());
        personAliCoupon.setName(memberCoupon.getName());

        if (memberCoupon.getValidType() == 0) {
            Date validTimeStart = new Date();
            personAliCoupon.setValidTimeStart(validTimeStart);
            int day = memberCoupon.getClaimedTime();
            Long b = System.currentTimeMillis();
            b = b + day * 24 * 60 * 60 * 1000;
            Date c = new Date(b);
            personAliCoupon.setValidTimeEnd(c);
        } else if (memberCoupon.getValidType() == 1) {
            personAliCoupon.setValidTimeStart(memberCoupon.getValidTimeStart());
            personAliCoupon.setValidTimeEnd(memberCoupon.getValidTimeEnd());
        }

        personAliCoupon.setStatus(PersonAliCoupon.Status.NO_USE.getCode());

        //生成个人卡券时处理的同步问题
        if (memberCoupon.getCouponSourceType().equals(MemberCoupon.CouponSourceType.SYNC.getCode())) {
            personAliCoupon.setSynStatus(PersonAliCoupon.SynStatus.NO_SYN.getCode());//标记为 1
        } else {
            personAliCoupon.setSynStatus(PersonAliCoupon.SynStatus.LIMIT.getCode()); //标记为 0
        }

        PersonAliCoupon personCoupon1 = personAliCouponService.save(personAliCoupon);
        //核销码采用个人卡券Id形式
        personCoupon1.setCode(personCoupon1.getId());
        PersonAliCoupon personCoupon2 =personAliCouponService.save(personCoupon1);
        return Resp.success(personCoupon2.getId(), "领取卡券成功！");
    }
}
