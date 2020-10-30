package com.fzy.admin.fp.order.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-04-29 20:15
 * @description
 */
@Data
public class SummaryVO {
    @ApiModelProperty(value = "已结算佣金")
    BigDecimal settleCommisson;

    @ApiModelProperty(value = "未结算佣金")
    BigDecimal unSettleCommisson;
    public SummaryVO(){}

    public SummaryVO(BigDecimal settleCommisson,BigDecimal unSettleCommisson) {
        this.settleCommisson=settleCommisson;
        this.unSettleCommisson=unSettleCommisson;
    }


}
