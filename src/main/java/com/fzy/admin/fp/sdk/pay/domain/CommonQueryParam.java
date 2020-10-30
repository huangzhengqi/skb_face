package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;

/**
 * @author Created by wtl on 2019-06-16 20:08
 * @description 通用的支付查询参数
 */
@Data
public class CommonQueryParam {

    private String merchantId;
    private String orderNumber;

}
