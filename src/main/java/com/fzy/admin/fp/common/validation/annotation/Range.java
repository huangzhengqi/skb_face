package com.fzy.admin.fp.common.validation.annotation;


import com.fzy.admin.fp.common.validation.validate.RangeValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2018/12/10 17:28
 * @Description
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidate.class)
public @interface Range {
    String min() default "";

    //是否小于等于
    boolean minEqual() default false;

    String max() default "";

    //是否大于等于
    boolean maxEqual() default false;

    String message();

    Class<?>[] groups() default {};
}
