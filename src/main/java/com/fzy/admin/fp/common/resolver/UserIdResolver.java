package com.fzy.admin.fp.common.resolver;

import cn.hutool.core.codec.Base64;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @author Created by zk on 2019-01-21 16:47
 * @description
 */
@Slf4j
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        UserId userIdAn = methodParameter.getParameterAnnotation(UserId.class);
        if (!ParamUtil.isBlank(userIdAn)) {
            String token = nativeWebRequest.getHeader(userIdAn.headName());
            if (ParamUtil.isBlank(token)) {
                return null;
            }
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                return null;
            }
            String payloadStr = tokenParts[1];
            String payloadJson = Base64.decodeStr(payloadStr);
            Map<String, String> payloadMap = JacksonUtil.toStringMap(payloadJson);
            return payloadMap.get("sub");
        }
        return null;
    }
}
