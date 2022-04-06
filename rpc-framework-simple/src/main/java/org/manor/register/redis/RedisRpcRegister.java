package org.manor.register.redis;

import org.manor.config.RedisUtils;
import org.manor.register.RpcRegister;
import org.manor.serializer.RpcSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@Component
public class RedisRpcRegister implements RpcRegister {
    @Autowired
    private RpcSerializer rpcSerializer;

    @Override
    public void register(String serviceName, Object service) {
        Jedis redisConnection = RedisUtils.getRedisConnection();
        byte[] object = rpcSerializer.encode(service);
        redisConnection.set(serviceName.getBytes(StandardCharsets.UTF_8), object);
    }
}
