package com.fzy.admin.fp.common.validation.annotation;


import com.fzy.admin.fp.common.validation.validate.LengthValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2019-01-18 23:32
 * @description 字符串长度
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LengthValidate.class)
public @interface Length {
    String max() default "";

    String min() default "";

    boolean maxEqual() default false;

    boolean minEqual() default false;

    String message();

    Class<?>[] groups() default {};
}
