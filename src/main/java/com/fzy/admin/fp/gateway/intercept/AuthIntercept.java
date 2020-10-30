package com.fzy.admin.fp.gateway.intercept;

import cn.hutool.core.util.ReUtil;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.gateway.GateApplicationRunner;
import com.fzy.admin.fp.sdk.auth.feign.AuthenticationInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Created by zk on 2019-06-30 23:30
 * @description
 */
@Slf4j
@Component
public class AuthIntercept implements HandlerInterceptor {
    @Value("${secret.header}")
    private String header;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        if (shouldFilter(httpServletRequest)) {
            //获取头部token
            String token = httpServletRequest.getHeader(header);
            String pageUrl = httpServletRequest.getHeader("page-Url");
            //获取签发者
            String iss = JwtUtil.getPayloadProperty(token, "iss");
            if (ParamUtil.isBlank(token)) {
                httpServletResponse.getWriter().write(JacksonUtil.toJson(new Resp().error(Resp.Status.NO_LOGIN, "您尚未登录")));
            } else if (ParamUtil.isBlank(iss) || !checkIss(iss)) {
                httpServletResponse.getWriter().write(JacksonUtil.toJson(new Resp().error(Resp.Status.TOKEN_ERROR, "token有误,请重新登录")));
            } else {
                final AuthenticationInterface authenticationInterface = GateApplicationRunner.authenticationInterfaceMap.get(iss);
                final Resp resp = authenticationInterface.authentication(token, httpServletRequest.getRequestURI(),pageUrl);
                if (!Resp.Status.SUCCESS.getCode().equals(resp.getCode())) {
                    httpServletResponse.getWriter().write(JacksonUtil.toJson(resp));
                } else {
                    httpServletResponse.setHeader(header, resp.getMsg());
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public boolean shouldFilter(HttpServletRequest request) {
        //获取需要忽略的权限列表进行匹配
        String path = request.getRequestURI().toUpperCase();
        for (String s : GateApplicationRunner.authIgnoreReg) {
            if (ReUtil.isMatch(s.toUpperCase(), path)) {
                return false;
            }
        }
        return true;
    }

    //判断Iss是否合法
    private boolean checkIss(String iss) {
        return GateApplicationRunner.authenticationInterfaceMap.keySet().contains(iss);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
