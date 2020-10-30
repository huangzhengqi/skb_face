package com.fzy.admin.fp.order.third.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;

/**
 * @author Created by wtl on 2019-06-04 14:47
 * @description 第三方开放接口订单查询参数
 */
@Data
public class ThirdQueryDTO {

    @NotBlank(message = "商户ID为空")
    private String mid; // 商户id
    @NotBlank(message = "商户订单号为空")
    private String outTradeNo; // 商户订单号
    @NotBlank(message = "随机数为空")
    private String nonceStr; // 随机数
    @NotBlank(message = "签名为空")
    private String sign; // 签名


}
