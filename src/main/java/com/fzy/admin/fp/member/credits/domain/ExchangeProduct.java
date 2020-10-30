package com.fzy.admin.fp.member.credits.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lb
 * @date 2019/5/20 9:45
 * @Description
 */
@Data
public class ExchangeProduct {

    @NotBlank(message = "操作人不能为空")
    private String operator;//操作人

    @NotBlank(message = "手机号不能为空")
    private String phone;//兑换手机号

    @NotBlank(message = "商品id不能为空")
    private String productId;//商品id

    private String merchantId;//商户id

    @NotBlank(message = "会员id不能为空")
    private String memberId;//会员id

}
