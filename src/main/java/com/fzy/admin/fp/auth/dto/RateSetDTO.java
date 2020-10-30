package com.fzy.admin.fp.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @author: huxiangqiang
 * @since: 2019/8/2
 */
@Data
public class RateSetDTO {

    @ApiModelProperty(value = "支付宝分佣比例")
    private BigDecimal zfbPayProrata;
    @ApiModelProperty(value = "微信分佣比例")
    private BigDecimal wxPayProrata;
    @ApiModelProperty(value = "随行付分佣比例")
    private BigDecimal sxfPayProrata;
    @ApiModelProperty(value = "富有分佣比例")
    private BigDecimal fyPayProrata;
    @ApiModelProperty(value = "天阙随行付分佣比例")
    private BigDecimal tqSxfPayProrata ;
    @ApiModelProperty(value = "公司id")
    private String id;
}
