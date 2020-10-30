package com.fzy.admin.fp.common.snowflake;

import cn.hutool.core.lang.Snowflake;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author zk
 * @description 基于雪花算法自定义ID生成策略
 * @create 2018-07-17 15:58
 **/
public class SnowFlakeIdStrategy implements IdentifierGenerator {

    public SnowFlakeIdStrategy() {
    }


    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        Snowflake snowflake = SpringContextUtil.getBean(Snowflake.class);
        return String.valueOf(snowflake.nextId());
    }
}
