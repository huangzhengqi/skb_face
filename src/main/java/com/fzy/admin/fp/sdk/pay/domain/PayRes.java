package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Created by zk on 2019-03-19 10:46
 * @description
 */
@Getter
@NoArgsConstructor
public class PayRes {
    public enum ResultStatus {
        SUCCESS(1, "支付成功"),
        PAYING(3, "支付中"),
        FAIL(2, "支付失败"),
        REFUNDING(4, "退款中"),
        REFUND(5, "已退款"),
        REFUNDFAIL(6,"退款失败");

        ResultStatus(int code, String status) {
            this.code = code;
            this.status = status;
        }

        private Integer code;
        private String status;

        public Integer getCode() {
            return code;
        }
    }

    private String msg;

    private ResultStatus status;

    private Object object;

    public PayRes(String msg, ResultStatus status) {
        this.msg = msg;
        this.status = status;
    }

    public PayRes(String msg, ResultStatus status, Object object) {
        this.msg = msg;
        this.status = status;
        this.object = object;
    }
}
