package com.fzy.admin.fp.order.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-04 21:10
 * @description 交易流水
 */
@Data
public class OrderFlow {

    /**
     * 打印流水明细仅支持打印特定时间下的支付成功状态和部分退款的流水明细
     */

    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "订单笔数")
    private Integer orderAmount;
    @ApiModelProperty(value = "流水金额")
    private BigDecimal flowPrice;
    @ApiModelProperty(value = "实收金额")
    private BigDecimal actPrice;
    @ApiModelProperty(value = "打印人")
    private String printer;
    @ApiModelProperty(value = "订单流水列表")
    private List<OrderFlowList> orderFlowLists;
    @ApiModelProperty(value = "订单流水统计详情")
    private List<OrderFlowCount> orderFlowCounts;

}
