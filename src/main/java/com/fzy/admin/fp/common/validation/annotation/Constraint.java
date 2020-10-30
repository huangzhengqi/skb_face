package com.fzy.admin.fp.common.validation.annotation;

import com.fzy.admin.fp.common.validation.validate.AbstractValidate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    Class<? extends AbstractValidate> validatedBy();
}
