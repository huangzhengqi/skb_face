package com.fzy.admin.fp.member.rule.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ：drj.
 * @Date  ：Created in 11:23 2019/5/20
 * @Description:
 **/
@Data
public class StoreAmountVo {


    @ApiModelProperty("实际储值")
    private BigDecimal actualMoney = BigDecimal.ZERO;

    @ApiModelProperty("赠送金额")
    private BigDecimal giftMoney = BigDecimal.ZERO;

    @ApiModelProperty("储值余额")
    private BigDecimal resuletMoney = BigDecimal.ZERO;

    @ApiModelProperty("消费扣款")
    private BigDecimal usedMoney = BigDecimal.ZERO;

    @ApiModelProperty("退款退回")
    private BigDecimal refundMoney = BigDecimal.ZERO;

}
