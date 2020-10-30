package com.fzy.admin.fp.member.coupon.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.coupon.domain.CouponTest;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.dto.DayNum;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/27 11:36
 * @Description
 */
public interface PersonCouponRepository extends BaseRepository<PersonCoupon> {

    List<PersonCoupon> findByMerchantIdAndMemberId(String merchantId, String memberId);

    List<PersonCoupon> findByMerchantIdAndMemberIdAndCouponId(String merchantId, String memberId, String couponId);

    List<PersonCoupon> findByMerchantIdAndCouponIdAndStatus(String merchantId, String couponId, Integer status);

    //查询当前商户所有会员各个使用过卡券的次数
    @Query(value = "SELECT new com.fzy.admin.fp.member.coupon.domain.CouponTest(a.memberId,count(a.id)) FROM PersonCoupon a WHERE a.merchantId= ?1 AND a.status= ?2 GROUP BY a.memberId")
    List<CouponTest> getPersonCoupons(String merchantId, Integer status);

    //查询活动哪天领了券和当天领取数量
    @Query(nativeQuery = true, value = "SELECT DATE_FORMAT(a.create_time,'%Y%m%d') days,COUNT(a.id) COUNT FROM lysj_member_person_coupon a WHERE a.`merchant_id`=?1 AND a.`coupon_id`=?2 GROUP BY days;")
    List<DayNum> getDays(String merchantId, String couponId);

    //-------------我的个人中心提供个数-------------
    //卡券个数 1未使用 2已使用 3已过期
    Integer countByMerchantIdAndMemberIdAndStatus(String merchantId, String memberId, Integer Status);


    PersonCoupon findByMerchantIdAndStatusAndCode(String merchantId, Integer status, String code);

    @Query(nativeQuery = true, value = "SELECT * FROM lysj_member_person_coupon a WHERE a.`status`=1 AND a.`remind_times`=1 AND a.`valid_time_end`>DATE_ADD(NOW(),INTERVAL 3 DAY) AND a.`valid_time_end`<DATE_ADD(NOW(),INTERVAL 5 DAY);")
    List<PersonCoupon> getPersonCouponByRemindTimes();

    /**
     * @author Created by wtl on 2019/6/28 23:16
     * @Description 获取个人有效卡券
     */
    List<PersonCoupon> findPersonCouponByMemberIdAndStatus(String memberId, Integer status);

    List<PersonCoupon> findByMerchantIdAndStatus(String merchantId, Integer status);

    List<PersonCoupon> findByValidTimeBetweenAndIdIn(Date startTime, Date endTime, String[] ids);

    List<PersonCoupon> findByIdLikeAndIdInOrNameLikeAndIdIn(String couponNum, String[] id, String couponName, String[] ids);
}
