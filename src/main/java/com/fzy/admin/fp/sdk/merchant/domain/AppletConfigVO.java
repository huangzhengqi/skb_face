package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.Data;

/**
 * @author Created by zk on 2019-05-29 16:30
 * @description
 */
@Data
public class AppletConfigVO {

    private String merchantId;
    private String appKey;
    private String appId;

    public AppletConfigVO() {
    }

    public AppletConfigVO(String merchantId, String appKey) {
        this.merchantId = merchantId;
        this.appKey = appKey;
    }

    public AppletConfigVO(String merchantId, String appKey, String appId) {
        this.merchantId = merchantId;
        this.appKey = appKey;
        this.appId = appId;
    }
}
