package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxSearchOrderVO {

    @ApiModelProperty(value = "错误码 成功：SUCCESS  ERROR/PARAM_ERROR/SYSTEMERROR")
    private String returnCode;

    @ApiModelProperty(value = "对错误码的描述")
    private String returnMsg;

    @ApiModelProperty(value = "SUCCESS：支付成功 FAIL：根据 err_code 的指引决定下一步操作。")
    private String resultCode;

    @ApiModelProperty(value = "ORDER_PAYING 支付中 SUCCESS 支付成功 PAYERROR 支付失败 REVOKED 已撤销 REFUND 转入退款")
    private String tradeState;

}
