package com.fzy.admin.fp.sdk.pay.config;

import lombok.Getter;

/**
 * @author Created by wtl on 2019-04-26 11:27
 * @description 支付通道变量
 */
public class PayChannelConstant {

    @Getter
    public enum PayWay {
        WXPAY(1, "微信"),
        ALIPAY(2, "支付宝"),
        BANKCARD(3, "银行卡"),
        BESTPAY(4, "翼支付"),
        LFQPAY(5, "乐百分"),
        UNIONPAY(6, "银联钱包"),
        TQSXFPYA(12,"天阙随行付");
        private Integer code;

        private String status;

        PayWay(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Channel {
        OFFICIAL(1, "官方"),
        HYB(2, "会员宝"),
        YRM(3, "易融码"),
        TTS(4, "统统收"),
        HSF(5, "惠闪付"),
        FY(6, "富友"),
        SXF(7, "随行付"),
        LKL(8, "拉卡拉"),
        TQSXF(12,"天阙随行付");
        private Integer code;

        private String status;

        Channel(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    /**
     * 打开方式，1：直接打开；2：返回4大参数；3：jsbridge打开
     */
    @Getter
    public enum OpenWay {
        OPEN(1, "直接打开"),
        PARAMS(2, "返回参数"),
        JSBRIDGE(3, "JsBridge");
        private Integer code;

        private String status;

        OpenWay(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

}
