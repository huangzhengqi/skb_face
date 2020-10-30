package com.fzy.admin.fp.auth.config;

import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.wechatopen.gateway.config.IComponentConfigProvider;
import com.fzy.wechatopen.gateway.config.WxOpenConfigEntity;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author: huxiangqiang
 * @since: 2019/7/19
 */
@Component
public class WeChatOpenConfig implements IComponentConfigProvider {

    @Resource
    private SiteInfoRepository siteInfoRepository;
    @Resource
    private WxOpenConfigRepository wxOpenConfigRepository;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public WxOpenConfigStorage getConfigByDomain(String domain) {

        WxOpenInMemoryConfigStorage configStorage = new WxOpenInRedisConfigStorage(jedisPool, domain + "_");
        SiteInfo siteInfo = siteInfoRepository.findByDomainName(domain);
        if (siteInfo == null) {
            return configStorage;
        }
        WxOpenConfig wxOpenConfig = wxOpenConfigRepository.findByServiceProviderId(siteInfo.getServiceProviderId());
        if (wxOpenConfig == null) {
            return configStorage;
        }
        configStorage.setComponentAppId(wxOpenConfig.getAppId());
        configStorage.setComponentAppSecret(wxOpenConfig.getAppSecret());
        configStorage.setComponentToken(wxOpenConfig.getToken());
        configStorage.setComponentAesKey(wxOpenConfig.getMsgKey());
        return configStorage;

    }

    @Override
    public WxOpenConfigEntity getConfigEntityByDomain(String domain) {
        WxOpenConfigEntity wxOpenConfigEntity = new WxOpenConfigEntity();
        SiteInfo siteInfo = siteInfoRepository.findByDomainName(domain);
        if (siteInfo == null) {
            return wxOpenConfigEntity;
        }
        WxOpenConfig wxOpenConfig = wxOpenConfigRepository.findByServiceProviderId(siteInfo.getServiceProviderId());
        if (wxOpenConfig == null) {
            return wxOpenConfigEntity;
        }
        wxOpenConfigEntity.setComponentAppId(wxOpenConfig.getAppId());
        wxOpenConfigEntity.setComponentAppSecret(wxOpenConfig.getAppSecret());
        wxOpenConfigEntity.setComponentToken(wxOpenConfig.getToken());
        wxOpenConfigEntity.setComponentAesKey(wxOpenConfig.getMsgKey());
        return wxOpenConfigEntity;
    }
}
