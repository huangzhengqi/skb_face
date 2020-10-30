package com.fzy.admin.fp.order.order.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-04-29 20:15
 * @description
 */
@Data
public class CountDataVO {


    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "订单金额",orderNum = "3", type = 10, height = 20, width = 20)
    private BigDecimal orderAmount = BigDecimal.ZERO;//订单金额

    @Excel(name = "有效订单数", orderNum = "2", type = 10, height = 20, width = 20)
    private Integer orderNum = 0;//订单数

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "退款金额",orderNum = "4", type = 10, height = 20, width = 20)
    private BigDecimal refundAmount = BigDecimal.ZERO;//退款金额

    private Integer refundNum = 0;//退款数

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "顾客实付",orderNum = "5", type = 10, height = 20, width = 20)
    private BigDecimal customerPaidAmount = BigDecimal.ZERO;//顾客实付

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "优惠",orderNum = "6", type = 10, height = 20, width = 20)
    private BigDecimal discountAmount = BigDecimal.ZERO;//优惠金额

    @Excel(name = "门店", orderNum = "1", height = 20, width = 20)
    private String storeName;

    private String storeId;

    private String orderId;

    private BigDecimal commissionAmount = BigDecimal.ZERO;

    private BigDecimal validDealAmount = BigDecimal.ZERO;

    public CountDataVO() {
    }

    public CountDataVO(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }
}
