package com.fzy.admin.fp.common.validation.validate;

import java.lang.annotation.Annotation;

/**
 * @author Created by zk on 2018-11-26 17:06
 * @description 校验方法抽象类  A 注解参数，T 实体值
 */
public abstract class AbstractValidate<A extends Annotation, T> {

    public A annotation;

    public abstract void init(A a);

    public abstract boolean isValid(T t);

}
