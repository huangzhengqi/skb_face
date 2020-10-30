package com.fzy.admin.fp.common.snowflake;

/**
 * @author Created by zk on 2019-03-04 10:39
 * @description
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by zk on 2019/3/4 10:39
 * @Description 自定义ID生产器配置
 */
@ConfigurationProperties(prefix = "lysj.snowflake")
@Data
public class SnowFlakeProperties {
    /**
     * 工作ID (0~31)
     */
    private long workId = -1;
    /**
     * 数据中心ID (0~31)
     */
    private long centerId = -1;
}
