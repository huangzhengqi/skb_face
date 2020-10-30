package com.fzy.admin.fp.pay.pay.vo;

import lombok.Data;

/**
 * @author Created by wtl on 2019-04-22 18:26
 * @description jsapi统一下单返回给前端的数据
 */
@Data
public class JsPayResponse {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String pkg;
    private String signType;
    private String paySign;

    private String orderNumber;
    private String price; // 订单金额

}
