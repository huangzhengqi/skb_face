package com.fzy.admin.fp.interceptors;

import cn.hutool.core.codec.Base64;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.assist.wraps.RequestWrap;
import com.fzy.wechatopen.gateway.config.WxOpenConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 域名拦截及验证
 *
 * @author llw
 * @date 2019.07.01
 */
@Slf4j
@Component
public class DomainInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    SiteInfoService siteInfoService;


    @Autowired
    CompanyService companyService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map ParameterMap = request.getParameterMap();
        Map reqMap = new HashMap();
        Set<Map.Entry<String, String[]>> entry = ParameterMap.entrySet();
        Iterator<Map.Entry<String, String[]>> it = entry.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> me = it.next();
            String key = me.getKey();
            String value = me.getValue()[0];
            reqMap.put(key, value);
        }
        log.info("拦截地址：" + request.getRequestURL());
        log.info("请求参数：{}", reqMap);
        //log.info("请求头：{}", request.getHeader("authorized"));

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String domainName = request.getServerName();

        //1. 如果没有域名，则返回404
        if (!StringUtils.hasText(domainName)) {
            response.getWriter().write(JacksonUtil.toJson(new Resp().error(Resp.Status.PARAM_ERROR, "不允许没有域名的访问")));
            return false;
        }
        SiteInfo siteInfo = siteInfoService.findByDomainName(domainName);
        //2.域名未经认证，返回 401
        if (siteInfo == null) {
            response.getWriter().write(JacksonUtil.toJson(new Resp().error(Resp.Status.PARAM_ERROR, "域名尚未经过认证")));
            return false;
        }

        Company company = companyService.findOne(siteInfo.getServiceProviderId());

        //3.如果服务商状态不正确，不允许访问
        if (company == null || !Company.Status.SIGNED.getCode().equals(company.getStatus())) {
            response.getWriter().write(JacksonUtil.toJson(new Resp().error(Resp.Status.DOMAIN_ERROR, "服务商状态异常，暂不允许访问")));
            return false;
        }

        String token = request.getHeader("authorized");
        String scheme = new URI(request.getRequestURL().toString()).getScheme();
        //如果用户有登录，服务商id从token中获取
        if (!StringUtils.isEmpty(token)) {
            request.setAttribute(CommonConstant.SERVICE_PROVIDERID, getServiceProviderId(token));
        } else {
            request.setAttribute(CommonConstant.SERVICE_PROVIDERID, siteInfo.getServiceProviderId());
        }
        request.setAttribute(CommonConstant.DOMAIN_NAME, scheme + "://" + siteInfo.getDomainName());
        WxOpenConfiguration.setDomain(RequestWrap.getRequest().getServerName());
        return super.preHandle(request, response, handler);
    }


    String getServiceProviderId(String token) {

        if (ParamUtil.isBlank(token)) {
            return null;
        }

        String[] tokenParts = token.split("\\.");

        if (tokenParts.length != 3) {
            return "";
        }

        String payloadStr = tokenParts[1];

        String payloadJson = Base64.decodeStr(payloadStr);

        Map<String, String> payloadMap = JacksonUtil.toStringMap(payloadJson);


        return payloadMap.get("serviceProviderId");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.error("接口异常", ex);
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
