package org.manor.remoting.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.manor.common.Message;
import org.manor.common.Response;
import org.manor.provider.ServiceProvider;
import org.manor.serializer.RpcSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class ServerResponseHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private RpcSerializer rpcSerializer;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            int length = byteBuf.readInt();
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            Message message = rpcSerializer.decode(bytes, Message.class);
            String serviceName = message.getServiceName();
            Object service = serviceProvider.getServiceByServiceName(serviceName);
            Class<?> inter = Class.forName(serviceName);
            Method method = inter.getMethod(message.getMethodName());
            Object result = method.invoke(service, message.getArgs());
            String requestId = message.getRequestId();
            Response response = new Response(requestId, 200, result);
            ctx.channel().writeAndFlush(response);
        }
        super.channelRead(ctx, msg);
    }
}
