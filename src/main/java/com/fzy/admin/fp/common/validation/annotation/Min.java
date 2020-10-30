package com.fzy.admin.fp.common.validation.annotation;


import com.fzy.admin.fp.common.validation.validate.MinValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2018/12/10 17:39
 * @Description 小于
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinValidate.class)
public @interface Min {
    String value();

    boolean minEqual() default false;

    String message() default "";

    Class<?>[] groups() default {};
}
