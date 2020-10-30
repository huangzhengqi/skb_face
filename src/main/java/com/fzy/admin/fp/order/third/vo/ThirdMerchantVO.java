package com.fzy.admin.fp.order.third.vo;

import lombok.Data;

/**
 * @author Created by wtl on 2019-06-12 10:47
 * @description 第三方开放接口商户号和密钥
 */
@Data
public class ThirdMerchantVO {

    private String mid; // 商户号
    private String appKey; // 密钥

}
