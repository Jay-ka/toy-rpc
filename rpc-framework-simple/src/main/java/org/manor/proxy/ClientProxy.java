package org.manor.proxy;

import org.manor.common.Message;
import org.manor.common.Response;
import org.manor.remoting.client.RpcClient;
import org.manor.remoting.client.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
public class ClientProxy implements InvocationHandler {
    @Autowired
    private RpcClient rpcClient;

    private InetSocketAddress address;

    public Object getProxy(String serviceName, InetSocketAddress address) throws ClassNotFoundException {
        this.address = address;
        return Proxy.newProxyInstance(NettyClient.class.getClassLoader(),
                new Class[]{Class.forName(serviceName)}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Message message = new Message();
        String serviceName = method.getDeclaringClass().getName();
        message.setRequestId(UUID.randomUUID().toString());
        message.setServiceName(serviceName);
        message.setMethodName(method.getName());
        message.setArgs(args);
        Response response = rpcClient.sendMessage(message, address);
        return response.getMessage();
    }
}
