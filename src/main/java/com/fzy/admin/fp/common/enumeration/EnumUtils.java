package com.fzy.admin.fp.common.enumeration;

/**
 * @author Created by zk on 2019-05-17 14:42
 * @description
 */
public class EnumUtils {
    public static <T extends CodeEnum> String findByCode(Integer code, Class<T> tClass) {
        for (T t : tClass.getEnumConstants()) {
            if (t.getCode().equals(code)) {
                return t.getDescription();
            }
        }
        return "unknown";
    }
}
