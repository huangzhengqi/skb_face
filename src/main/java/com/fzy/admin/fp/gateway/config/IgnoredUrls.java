package com.fzy.admin.fp.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by zk on 2019-01-02 19:20
 * @description 初始化需要忽略的权限列表
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrls {
    private List<String> urls = new ArrayList<>();
}
