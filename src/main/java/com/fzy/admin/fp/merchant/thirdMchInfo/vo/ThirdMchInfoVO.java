package com.fzy.admin.fp.merchant.thirdMchInfo.vo;

import lombok.Data;
import lombok.Getter;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:31 2019/6/26
 * @ Description: 第三方进件VO
 **/
@Data
public class ThirdMchInfoVO {


    private String merchantId; //商户id
    private String merchantName;//商户名
    private String merchantAccount;//商户状态
    private Integer signStatus;//签约状态


    public ThirdMchInfoVO(String merchantId, String merchantName, String merchantAccount) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantAccount = merchantAccount;
    }
}
