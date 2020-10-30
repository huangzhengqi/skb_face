package com.fzy.admin.fp.common.validation.annotation;

import com.fzy.admin.fp.common.validation.validate.PatternValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2018-12-10 17:51
 * @description 正则校验
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatternValidate.class)
public @interface Pattern {
    String pattern();

    String message();

    Class<?>[] groups() default {};
}
