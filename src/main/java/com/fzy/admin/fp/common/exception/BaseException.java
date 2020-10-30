package com.fzy.admin.fp.common.exception;


/**
 * @author zk
 * @description
 * @create 2018-07-27 10:13
 **/
public class BaseException extends RuntimeException {
    private String msg;
    private Integer code;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
