package com.fzy.admin.fp.common.validation.exception;

/**
 * @author Created by zk on 2018-11-26 16:35
 * @description 校验异常
 */
public class ValidatorException extends RuntimeException {
    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public ValidatorException(String msg) {
        super(msg);
    }
}
