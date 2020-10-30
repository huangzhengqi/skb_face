package com.fzy.admin.fp.sdk.auth.feign;

/**
 * @author Created by zk on 2019-03-18 20:02
 * @description
 */
public interface MessageServiceFeign {
    boolean sendSms(String phone, String code);

    boolean sendSmsInfo(String name, String phone, String password);

}
