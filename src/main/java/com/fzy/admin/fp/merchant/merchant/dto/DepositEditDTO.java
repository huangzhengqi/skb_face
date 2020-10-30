package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositEditDTO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("微信/支付宝 官方订单编号")
    private String transactionId;

}
