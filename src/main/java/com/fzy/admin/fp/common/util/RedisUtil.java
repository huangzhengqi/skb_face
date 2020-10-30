package com.fzy.admin.fp.common.util;


import com.fzy.assist.wraps.LogWrap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class RedisUtil {
    private static final Logger LOG = LogWrap.getLogger(RedisUtil.class);

    private static String host;

    private static Integer port;

    private static String password;

    private static Integer database;

    private static Integer timeout;

    private static Integer maxActive;

    private static Integer maxIdle;

    private static Integer maxWaitMillis;

    private static Integer minIdle;

    private static RedisUtil instance;

    private static JedisPool jedisPool = null;

    private static ReentrantLock lock = new ReentrantLock();

    static {
        host = ConfigUtils.getStringValue("spring.redis.host");
        port = ConfigUtils.getIntegerValue("spring.redis.port");
        password = ConfigUtils.getStringValue("spring.redis.password");
        database = ConfigUtils.getIntegerValue("spring.redis.database");
        if (database == null)
            database = Integer.valueOf(0);
        if (StringUtils.isBlank(ConfigUtils.getStringValue("spring.redis.timeout"))) {
            timeout = Integer.valueOf(10000);
        } else {
            timeout = ConfigUtils.getIntegerValue("spring.redis.timeout");
        }
        maxActive = ConfigUtils.getIntegerValue("spring.redis.pool.max-active");
        if (maxActive == null)
            maxActive = Integer.valueOf(100);
        maxIdle = ConfigUtils.getIntegerValue("spring.redis.pool.max-idle");
        if (maxIdle == null)
            maxIdle = Integer.valueOf(10);
        if (StringUtils.isBlank(ConfigUtils.getStringValue("spring.redis.pool.max-wait"))) {
            maxWaitMillis = ConfigUtils.getIntegerValue("spring.redis.pool.max-wait");
        } else {
            maxWaitMillis = Integer.valueOf(10000);
        }
        minIdle = ConfigUtils.getIntegerValue("spring.redis.pool.min-idle");
        if (minIdle == null)
            minIdle = Integer.valueOf(1);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle.intValue());
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis.intValue());
        jedisPoolConfig.setMinIdle(minIdle.intValue());
        jedisPoolConfig.setMaxTotal(maxActive.intValue());
        jedisPool = new JedisPool((GenericObjectPoolConfig)jedisPoolConfig, host, port.intValue(), timeout.intValue(), password, database.intValue());
    }

    public static synchronized Jedis getJedis() {
        if (jedisPool != null) {
            Jedis resource = jedisPool.getResource();
            return resource;
        }
        return null;
    }

    public static RedisUtil getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null)
                instance = new RedisUtil();
            lock.unlock();
        }
        return instance;
    }

    public static void returnResource(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }

    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String ok = jedis.set(key, value);
            return "OK".equals(ok);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    public static Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setnx(key, value);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return Long.valueOf(0L);
    }

    public static boolean set(String key, String value, Integer expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String ok = jedis.set(key, value);
            if (null != expireTime)
                jedis.expire(key, expireTime.intValue());
            return "OK".equals(ok);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return "";
    }

    public static boolean delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.del(key);
            return (result.longValue() != 0L);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    public static void delKeys(String pattern) {
        try {
            deletekeys(pattern + "_*");
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        }
    }

    private static synchronized void deletekeys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> keySet = jedis.keys(pattern);
            if (keySet == null || keySet.size() == 0)
                return;
            String[] keys = new String[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                keys[i] = key;
                i++;
            }
            jedis.del(keys);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
    }

    public static Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public static Long incrBy(String key, Integer num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrBy(key, num.intValue());
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public static Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decr(key);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public static Long decrBy(String key, Integer num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decrBy(key, num.intValue());
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public static Set<String> keys(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys(key);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", (Throwable)e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
}
