package com.fzy.admin.fp.pay.pay.feignImpl;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-25 21:17
 * @description 微信支付配置feign实现
 */
@Slf4j
@Service
public class WxConfigServiceFeignImpl implements WxConfigServiceFeign {

    @Resource
    private TopConfigRepository topConfigRepository;

    @Override
    public Map<String, String> getWxConfig(String companyId) {
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG);
        Map<String, String> map = new HashMap<>();
        // 还没配置微信支付参数
        if (ParamUtil.isBlank(topConfig)) {
            map.put("msg", "error");
            throw new BaseException("还未配置微信参数", Resp.Status.PARAM_ERROR.getCode());
        } else {
            map.put("msg", "success");
            map.put("appId", topConfig.getWxAppId());
            map.put("appKey", topConfig.getWxAppKey());
            map.put("appSecret", topConfig.getWxAppSecret());
            map.put("frogAppKey", topConfig.getFrogAppId());
            map.put("frogAppCert", topConfig.getFrogAppCert());
        }
        return map;
    }
}
