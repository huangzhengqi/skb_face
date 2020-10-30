package com.fzy.admin.fp.member.coupon.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lb
 * @date 2019/5/27 16:23
 * @Description 领取卡券参数实体类
 */
@Data
public class ReceiveCoupon {

    @ApiModelProperty("领取人的会员Id")
    private String memberId;

    @ApiModelProperty("卡券Id")
    private String couponId;

}
