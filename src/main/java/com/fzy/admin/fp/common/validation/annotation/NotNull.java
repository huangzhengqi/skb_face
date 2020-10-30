package com.fzy.admin.fp.common.validation.annotation;


import com.fzy.admin.fp.common.validation.validate.NotNullValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Created by zk on 2019/1/8 10:27
 * @Description
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullValidate.class)
public @interface NotNull {
    String message();

    Class<?>[] groups() default {};
}
