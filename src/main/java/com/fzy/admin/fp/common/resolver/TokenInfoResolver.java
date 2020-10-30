package com.fzy.admin.fp.common.resolver;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
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
 * @author Created by zk on 2019-05-16 15:47
 * @description
 */
@Slf4j
@Component
public class TokenInfoResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(TokenInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        TokenInfo tokenInfo = methodParameter.getParameterAnnotation(TokenInfo.class);
        String token = nativeWebRequest.getHeader(tokenInfo.headName());
        if (ParamUtil.isBlank(token)) {
            return null;
        }
        Map<String, Object> payloadMap = JwtUtil.getPayloadMap(token);
        return payloadMap.get(tokenInfo.property());
    }
}
