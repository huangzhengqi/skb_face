package com.fzy.admin.fp.order.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wtl on 2019-05-05 9:47
 * @description 订单流水统计
 */
@Data
public class OrderFlowCount {

    @ApiModelProperty(value = "订单统计、商户实收、支付宝/微信、退款统计")
    private String title;
    @ApiModelProperty(value = "订单笔数，4笔")
    private String amount;
    @ApiModelProperty(value = "金额")
    private String price;

    public OrderFlowCount() {
    }

    public OrderFlowCount(String title, String amount, String price) {
        this.title = title;
        this.amount = amount;
        this.price = price;
    }
}
