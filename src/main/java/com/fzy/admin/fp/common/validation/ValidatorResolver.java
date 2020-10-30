package com.fzy.admin.fp.common.validation;

import cn.hutool.core.util.ReflectUtil;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.exception.ValidatorException;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.validation.exception.ValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;

/**
 * @author Created by zk on 2018-11-26 17:58
 * @description 自定义参数解析器，用于对参数进行校验
 */
@Slf4j
@Component
public class ValidatorResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Valid.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Class clazz = methodParameter.getParameterType();
        //实例化参数对象
        Object object = ReflectUtil.newInstance(clazz);
        //获取对象中所有的成员变量，包括父类中的成员变量
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            String paramName = field.getName();
            String arg[] = nativeWebRequest.getParameterValues(paramName);
            if (arg != null && arg.length == 1) {
                String value = arg[0];
                try {
                    ReflectUtil.setFieldValue(object, field, value);
                } catch (Exception e) {
                    log.error("参数设置有误{}", e.getMessage());
                    e.printStackTrace();
                    throw new ValidatorException(e.getMessage());
                }
            }
        }
        BindingResult bindingResult = Validation.valid(object);
        if (!bindingResult.isFlag()) {
            throw new ValidatorException(bindingResult.getMessage().get(0));
        }
        return object;
    }
}
