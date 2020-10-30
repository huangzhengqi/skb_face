package com.fzy.admin.fp.sdk.pay.config;

import lombok.Getter;

/**
 * @author Created by wtl on 2019-05-08 0:05
 * @description 支付结果常量
 */
public class PayResultConstant {

    @Getter
    public enum Result {
        SUCCESS(1, "支付成功"),
        FAIL(2, "支付失败"),
        WAIT(3, "等待输入密码");
        private Integer code;

        private String status;

        Result(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @Getter
    public enum Refund {
        SUCCESS(1, "退款成功"),
        FAIL(2, "退款失败"),
        WAITING(3, "退款中"),
        EXCEPTION(4, "退款异常");
        private Integer code;

        private String status;

        Refund(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


}
