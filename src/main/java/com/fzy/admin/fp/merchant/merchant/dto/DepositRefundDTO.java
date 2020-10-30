package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DepositRefundDTO {
    @ApiModelProperty("已消费金额 消费金额，单位 分")
    private Integer usedFee;
    @ApiModelProperty("押金id")
    private String depositId;

}
