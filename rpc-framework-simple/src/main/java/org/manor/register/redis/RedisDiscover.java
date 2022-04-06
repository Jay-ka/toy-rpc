package org.manor.register.redis;

import org.manor.config.RedisUtils;
import org.manor.register.RpcDiscover;
import org.manor.serializer.RpcSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author kanghuajie
 * @date 2022/4/1
 */
@Component
public class RedisDiscover implements RpcDiscover {
    @Autowired
    private RpcSerializer rpcSerializer;

    @Override
    public InetSocketAddress discover(String serviceName) {
        Jedis redisConnection = RedisUtils.getRedisConnection();
        byte[] bytes = redisConnection.get(serviceName.getBytes(StandardCharsets.UTF_8));
        return rpcSerializer.decode(bytes, InetSocketAddress.class);
    }
}
