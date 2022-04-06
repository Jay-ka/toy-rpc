package org.manor.register;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
public interface RpcRegister {
    void register(String serviceName, Object service);
}
