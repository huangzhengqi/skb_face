package com.fzy.admin.fp.common.spring.configuration;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.snowflake.SnowFlakeProperties;
import com.fzy.admin.fp.common.snowflake.SnowFlakeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by zk on 2019-03-04 10:54
 * @description 默认配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SnowFlakeProperties.class)
public class AutoConfiguration {
    /**
     * @author Created by zk on 2019/3/4 10:56
     * @Description ID生成器配置
     */
    @Bean
    @ConditionalOnMissingBean(Snowflake.class)
    public Snowflake snowflakeIdWorker(SnowFlakeProperties properties) {
        if (properties.getCenterId() < 0 || properties.getCenterId() > 31L) {
            properties.setCenterId(RandomUtil.randomLong(0, 31));
        }
        if (properties.getWorkId() < 0 || properties.getWorkId() > 31L) {
            properties.setWorkId(RandomUtil.randomLong(0, 31));
        }
        Snowflake snowflake = new Snowflake(properties.getWorkId(), properties.getCenterId());
        log.debug("注入snowflakeIdGenerator:{},{}", properties, snowflake);
        return snowflake;
    }
}
