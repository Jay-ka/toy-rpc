package org.manor.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author kanghuajie
 * @date 2022/4/1
 */
public class RedisUtils {
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(8);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMinIdle(0);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }

    public static Jedis getRedisConnection() {
        return jedisPool.getResource();
    }


}
