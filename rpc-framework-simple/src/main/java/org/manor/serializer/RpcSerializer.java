package org.manor.serializer;

/**
 * @author kanghuajie
 * @date 2022/4/1
 */
public interface RpcSerializer {
    byte[] encode(Object object);

    <T> T decode(byte[] jsonObject, Class<T> type);
}
