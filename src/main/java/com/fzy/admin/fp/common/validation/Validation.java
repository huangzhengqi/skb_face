package com.fzy.admin.fp.common.validation;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fzy.admin.fp.common.validation.annotation.Constraint;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.validation.validate.AbstractValidate;
import com.fzy.admin.fp.common.validation.annotation.Constraint;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.validation.validate.AbstractValidate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Created by zk on 2018-11-27 10:22
 * @description
 */
public class Validation {

    /**
     * @author Created by zk on 2018/12/10 19:19
     * @Description 不含分组
     */
    public static BindingResult valid(Object obj) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        return valid(obj, fields, null, null);
    }

    /**
     * @author Created by zk on 2018/12/10 19:19
     * @Description 含分组
     */
    public static BindingResult valid(Object obj, Class<?>[] groups) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        return valid(obj, fields, groups, null);
    }

    /**
     * @author Created by zk on 2018/12/10 19:19
     * @Description 含忽略属性和分組
     */
    public static BindingResult valid(Object obj, Set<String> ignoreProperties) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        return valid(obj, fields, null, ignoreProperties);
    }

    /**
     * @author Created by zk on 2018/12/10 19:19
     * @Description 含忽略属性
     */
    public static BindingResult valid(Object obj, Class<?>[] groups, Set<String> ignoreProperties) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        return valid(obj, fields, groups, ignoreProperties);
    }

    private static BindingResult valid(Object obj, Field[] fields, Class<?>[] groups, Set<String> ignoreProperties) {
        //初始化校验结果
        BindingResult bindingResult = new BindingResult();
        //遍历该实体类所有属性
        for (Field field : fields) {
            if (ignoreProperties != null && ignoreProperties.contains(field.getName())) {
                continue;
            }
            //获取属性上的注解
            Annotation[] annotations = field.getAnnotations();
            //遍历注解
            for (Annotation annotation : annotations) {
                //获取注解annotationType
                Class cls = annotation.annotationType();
                //以map形式获取注解中的属性
                Map<String, Object> map = AnnotationUtil.getAnnotationValueMap(field, cls);
                Class<?>[] originGroups = (Class<?>[]) map.get("groups");
                if (ArrayUtil.isNotEmpty(originGroups)) {
                    boolean groupFlag = false;
                    if (ArrayUtil.isNotEmpty(groups)) {
                        for (Class<?> group : groups) {
                            if (ArrayUtil.contains(originGroups, group)) {
                                groupFlag = true;
                                break;
                            }
                        }
                    }
                    if (!groupFlag) {
                        continue;
                    }
                }
                //获取注解上的@Constraint注解
                Constraint constraint = (Constraint) cls.getAnnotation(Constraint.class);
                //若不存在此注解，则不参与校验
                if (constraint != null) {
                    //获取校验算法类
                    Class validateClass = constraint.validatedBy();
                    //初始化校验算法类（以接口形式）
                    AbstractValidate abstractValidate = (AbstractValidate) ReflectUtil.newInstance(validateClass);
                    abstractValidate.annotation = annotation;
                    //调用初始化方法
                    abstractValidate.init(annotation);

                    ParameterizedType parameterizedType = (ParameterizedType) validateClass.getGenericSuperclass();
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length < 2) {
                        continue;
                    }
                    //调用校验方法
                    boolean flag = abstractValidate.isValid(Convert.convert(actualTypeArguments[1], ReflectUtil.getFieldValue(obj, field)));
                    //若校验不通过
                    if (!flag) {
                        bindingResult.setFlag(false);
                        //将校验结果填充进结果集
                        bindingResult.getMessage().add(map.get("message") == null ?
                                field.getName() + ":Check not pass!" : map.get("message").toString());
                    }
                }
            }
        }
        return bindingResult;
    }

}
