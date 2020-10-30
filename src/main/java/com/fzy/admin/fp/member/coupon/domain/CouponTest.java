package com.fzy.admin.fp.member.coupon.domain;

import lombok.Data;

/**
 * @author lb
 * @date 2019/6/5 10:22
 * @Description
 */
@Data
public class CouponTest {
    private String memberId;
    private Long couCount;

    public CouponTest(String memberId, Long couCount) {
        this.memberId = memberId;
        this.couCount = couCount;
    }
}
