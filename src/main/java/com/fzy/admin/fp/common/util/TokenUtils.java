package com.fzy.admin.fp.common.util;

import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.util.Map;

public class TokenUtils {

    public static Map<String, Object> getToken() {
        String token = HttpRequestUtils.getRequest().getHeader("authorized");
        if (ParamUtil.isBlank(token)) {
            return null;
        }
        return JwtUtil.getPayloadMap(token);
    }

    public static String getMerchantId() { return getToken().get("merchantId").toString(); }


    public static String getServiceId() {
        return HttpRequestUtils.getRequest().getAttribute("serviceProviderId").toString();
    }

    public static String getUserId() { return getToken().get("sub").toString(); }

    public static String getCompanyId() { return getToken().get("companyId").toString(); }
}
