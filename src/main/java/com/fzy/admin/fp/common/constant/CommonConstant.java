package com.fzy.admin.fp.common.constant;


/**
 * @author zk
 * @Description 常量  若系统正式上线，此处变量不可再修改
 * @date 2018-07-18 14:43
 */
public interface CommonConstant {
    /**
     * @author zk
     * @Description 删除标志delFlag(已删除)
     * @date 2018-07-18 14:45
     */
    Integer DEL_FLAG = -1;

    /**
     * @author zk
     * @Description 删除标志delFlag(可用)
     * @date 2018-07-18 14:46
     */
    Integer NORMAL_FLAG = 1;

    Integer IS_TRUE = 1;

    Integer IS_FALSE = -1;

    String SNOW_FLAKE_ID_STRATEGY = "com.fzy.admin.fp.common.snowflake.SnowFlakeIdStrategy";
    /**
     * @author Created by zk on 2018/12/26 10:05
     * @Description 以下为基础实体的字段名 base.BaseEntity
     */
    String ID_NAME = "id";
    String NAME_NAME = "name";
    String CREATE_TIME_NAME = "createTime";
    String UPDATE_TIME_NAME = "updateTime";
    String DEL_FLAG_NAME = "delFlag";

    String TOKEN_HEADER_NAME = "authorized";
//
//    String TOKEN_USER_ID_NAME = "userId";

    String SERVICE_PROVIDERID = "serviceProviderId";

    String DOMAIN_NAME = "domainName";

    String HEAD_IMG="http://resouce.400fzy.com/img/head.png";


    /**
     * @author yy
     * 分销代理
     * 2019-12-20 15:04:10
     */
    Integer distType=1;
}
