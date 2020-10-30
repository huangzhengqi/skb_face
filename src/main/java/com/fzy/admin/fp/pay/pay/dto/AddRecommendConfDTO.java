package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 特约商户配置-关注配置
 * <p>
 *
 * @since: 2019/8/20 20:16
 * @author: wsj
 */
@Data
public class AddRecommendConfDTO {

    @ApiModelProperty(value = "推荐关注APPID")
    private String subscribe_appid;
    @ApiModelProperty(value = "支付凭证推荐小程序appid")
    private String receipt_appid;
}
