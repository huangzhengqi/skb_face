package com.fzy.admin.fp.distribution.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020-3-13 14:31:23
 * @Desp
 **/
@Data
public class DistMchInfoDTO {
    @ApiModelProperty("是否给微信自动进件 0否 1是")
    private Integer isWx;

    @ApiModelProperty("是否给支付宝自动进件 0否 1是")
    private Integer isZfb;
}
