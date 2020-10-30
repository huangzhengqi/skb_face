package com.fzy.admin.fp.helper;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * 获取微信小程序相关的接口
 *
 * @doc https://github.com/Wechat-Group/WxJava?tdsourcetag=s_pctim_aiomsg
 */
@Slf4j
@Component
public final class WxMapServiceHelper {


    @Resource
    private MerchantBusinessService merchantBusinessService;

    /**
     * 获取微信小程序相关服务
     *
     * @param appId
     * @return
     */
    public WxMaService getWxMaService(String appId) {
        WxMaService wxMaService = new WxMaServiceImpl();
        AppletConfigVO appletConfigVO = MemberConstant.MERCHANT_APPKEY_CACHE.get(appId);
        synchronized (appId.intern()) {
            if (ParamUtil.isBlank(appletConfigVO)) {
                //通过feign获取该小程序的APPKEY
                appletConfigVO = merchantBusinessService.findByAppId(appId);
                if (ParamUtil.isBlank(appletConfigVO)) {
                    String format = MessageFormat.format("can''t find merchant for appId:{0}", appId);
                    log.error(format);
                    throw new RuntimeException(format);
                }
                MemberConstant.MERCHANT_APPKEY_CACHE.put(appId, appletConfigVO);
            }
        }

        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        wxMaConfig.setAppid(appletConfigVO.getAppId());
        wxMaConfig.setSecret(appletConfigVO.getAppKey());
        wxMaService.setWxMaConfig(wxMaConfig);

        return wxMaService;
    }
}
