package com.fzy.admin.fp.common.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author soul 服务器向客户端响应的domain对象
 */
@Getter
@Slf4j
public class Resp<T extends Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status {
        SUCCESS(200, "成功"),
        NO_LOGIN(401, "尚未登录"),
        NO_ACCESS(403, "尚未拥有权限"),
        PARAM_ERROR(412, "参数错误"),
        TOKEN_ERROR(422, "Token错误"),
        DOMAIN_ERROR(432, "域名错误"),
        INNER_ERROR(500, "系统内部错误"),
        ;

        private Integer code;
        private String msg;

        Status(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 结果:结果码
     */
    @ApiModelProperty(value = "状态码 200成功" +
            " 401尚未登录 " +
            " 403尚未拥有权限 " +
            " 412参数错误 " +
            " 422Token错误 " +
            " 432域名错误 " +
            " 500系统内部错误 ")
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "消息提示")
    private String msg;

    /**
     * 响应的对象
     */
    @ApiModelProperty(value = "返回对象")
    private T obj;


    /**
     * 创建一个失败(错误码+消息)的对象
     */
    public Resp error(Status status, String msg) {
        this.code = status.code;
        this.msg = msg;
        this.obj = null;
        return this;
    }

    /**
     * 创建一个成功(仅消息)的对象
     */
    public static Resp success(String msg) {
        return success(null, msg);
    }


    /**
     * 创建一个成功(仅数据)的对象,消息默认为'success'
     */
    public static <T> Resp<T> success(T data) {
        return success(data, null);
    }

    /**
     * 创建一个成功(消息+数据)的对象
     *
     * @return
     */
    public static <T> Resp<T> success(T data, String msg) {

        Resp<T> resp = new Resp<>();
        resp.code = Status.SUCCESS.code;
        resp.msg = msg;
        resp.obj = data;

        return resp;
    }

    /**
     * 创建一个通用(结果码 + 消息 + 数据)的对象
     */
    public Resp<T> common(Status status, String msg, T data) {
        this.code = status.code;
        this.msg = msg;
        this.obj = data;
        return this;
    }

    public static Status getStatusByCode(Integer code) {
        if (ParamUtil.isBlank(code)) {
            return Status.INNER_ERROR;
        }
        for (Status status : Status.values()) {
            if (code.equals(status.code)) {
                return status;
            }
        }
        return Status.INNER_ERROR;
    }


}
