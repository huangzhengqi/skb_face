package com.fzy.admin.fp.common.annotation;


import com.fzy.admin.fp.common.constant.CommonConstant;

import java.lang.annotation.*;

/**
 * @author zk
 * @date 2018-08-13 15:31
 * @Description 日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String description() default "";

    String tokenName() default CommonConstant.TOKEN_HEADER_NAME;//token在请求头中的key
}
