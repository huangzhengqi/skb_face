package com.fzy.admin.fp.sdk.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Created by zk on 2019-03-19 10:46
 * @description
 */
@Getter
@NoArgsConstructor
public class AuthenticationResult {
    public enum ResultStatus {
        SUCCESS(200, "成功"),
        NO_ACCESS(403, "尚未拥有权限"),
        TOKEN_ERROR(422, "Token错误");

        ResultStatus(int code, String status) {
            this.code = code;
            this.status = status;
        }

        private int code;
        private String status;

        public int getCode() {
            return code;
        }
    }

    private String msg;

    private ResultStatus status;

    public AuthenticationResult(String msg, ResultStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
