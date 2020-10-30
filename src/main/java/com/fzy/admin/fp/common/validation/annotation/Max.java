package com.fzy.admin.fp.common.validation.annotation;


import com.fzy.admin.fp.common.validation.validate.MaxValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2018-12-10 17:28
 * @description 大于
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxValidate.class)
public @interface Max {
    String value() default "";

    boolean maxEqual() default false;

    String message();

    Class<?>[] groups() default {};
}
