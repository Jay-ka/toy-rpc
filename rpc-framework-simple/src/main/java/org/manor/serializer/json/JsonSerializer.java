package org.manor.serializer.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.manor.serializer.RpcSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author kanghuajie
 * @date 2022/4/1
 */
@Component
public class JsonSerializer implements RpcSerializer {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] encode(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化失败");
        }
    }

    @Override
    public <T> T decode(byte[] jsonObject, Class<T> type) {
        try {
            return objectMapper.readValue(jsonObject, type);
        } catch (IOException e) {
            throw new RuntimeException("反序列化失败");
        }
    }
}
