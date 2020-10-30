package com.fzy.admin.fp.member.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lb
 * @date 2019/6/3 9:37
 * @Description 卡券营销分析需要的信息
 */
@Data
public class CouponAnalysis {
    private Integer totalNum;//卡券投放张数
    private Integer receiveNum;//领取张数
    private Integer usedNum;//使用张数
    private BigDecimal orderTotalMoney;//消费金额
    private BigDecimal preferentialMoney;//抵用金额
    private BigDecimal conversion;//转化率
    private String couponName;//卡券名

    private String[] time;//时间数组
    private Long[] newNumber;//时间数组对应卡券领取张数

    private Integer newNum;//拉新人数
    private Integer repeatCustomersNum;//回头客人数


    private Integer saveLoseNum;//挽回流失人数
    private BigDecimal newAccountedFor;//拉新人数占比
    private BigDecimal repeatAccountedFor;//挽回流失人数占比
    private BigDecimal saveAccountedFor;//挽回流失人数占比

    private Integer sevenDayNum;//七天内
    private Integer oneMonthNum;//1个月内
    private Integer threeMonthNum;//3个月内
    private BigDecimal sevenConversion;//七天内留存占比
    private BigDecimal oneMonthConversion;//1个月内留存占比
    private BigDecimal threeMonthConversion;//3个月内留存占比


}
