package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.Data;
import lombok.Getter;

/**
 * @author Created by wtl on 2019-06-12 15:13
 * @description 第三方付款设备sn序列号配置
 */
@Data
public class SnConfigVO {

    @Getter
    public enum Flag {
        INITIAL(1, "未下载"),
        FINISH(2, "已下载");
        private Integer code;

        private String status;

        Flag(Integer code, String status) {
            this.code = code;
            this.status = status;
        }

    }

    private String mid; // 商户号
    private String appKey; // 密钥

    private Integer flag; // 是否下载过


}
