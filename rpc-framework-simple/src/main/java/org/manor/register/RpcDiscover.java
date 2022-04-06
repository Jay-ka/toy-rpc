package org.manor.register;

import java.net.InetSocketAddress;

/**
 * @author kanghuajie
 * @date 2022/4/1
 */
public interface RpcDiscover {
    InetSocketAddress discover(String serviceName);
}
