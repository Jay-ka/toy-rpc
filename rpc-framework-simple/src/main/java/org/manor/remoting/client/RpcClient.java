package org.manor.remoting.client;

import org.manor.common.Message;
import org.manor.common.Response;

import java.net.InetSocketAddress;

/**
 * @author kanghuajie
 * @date 2022/4/2
 */
public interface RpcClient{

    Response sendMessage(Message message, InetSocketAddress address);
}
