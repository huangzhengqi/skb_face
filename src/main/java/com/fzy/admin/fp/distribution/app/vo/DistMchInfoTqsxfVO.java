package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分销天阙进件vo
 */
@Data
public class DistMchInfoTqsxfVO {

    @ApiModelProperty("进件id")
    private String id;

    @ApiModelProperty("天阙随行付mccId")
    private String tqSxfMccId;

    @ApiModelProperty("天阙随行付经营类目名称")
    private String tqSxfMccName;

    @ApiModelProperty(value = "微信费率")
    private BigDecimal tqsxfWxRate;

    @ApiModelProperty(value = "支付宝费率")
    private BigDecimal tqsxfZfbRate;

    @ApiModelProperty("开户许可证 天阙随行付对公结算必传")
    private String openingPermit;

}
