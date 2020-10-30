package com.fzy.admin.fp.common.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Created by zk on 2018-11-26 16:33
 * @description 加上此注解的方法参数会被自动进行校验
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
    Class<?>[] group() default {};
}
