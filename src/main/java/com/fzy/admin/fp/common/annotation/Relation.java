package com.fzy.admin.fp.common.annotation;


import com.fzy.admin.fp.common.spring.jpa.RelationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zk
 * @Description 关系注解，在实体类中的实体属性使用
 * @date 2018-07-18 15:36
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Relation {
    //服务层名，在该属性名与服务层名中的类名不一致时使用 如studentService
    String serviceName() default "";
//    Class<? extends Runner>

    //Id属性名，如studentId，值应与实体类中的关联实体类的变量名一致
    String idProperty() default "";

    //关系枚举 包含RelationType.ONE RelationType.Many
    RelationType relationType() default RelationType.ONE;

}
