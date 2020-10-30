package com.fzy.admin.fp.auth;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

/**
 * @author Created by zk on 2019-01-21 1:04
 * @description 项目启动的时候加载静态资源
 */
public class VerifyCodeApplicationRunner {

    private static final Integer VERIFY_CODE_EXPIRATION = 1000 * 60 * 15;//验证码过期时间15分钟

    public static TimedCache<String, String> verifyCodeCache = CacheUtil.newTimedCache(VERIFY_CODE_EXPIRATION);

}
