package org.manor.remoting.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.manor.common.FutureCommon;
import org.manor.common.Response;
import org.manor.serializer.RpcSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private RpcSerializer rpcSerializer;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            int length = byteBuf.readInt();
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            Response response = rpcSerializer.decode(bytes, Response.class);
            FutureCommon.completeFuture(response.getRequestId(), response);
        }
        super.channelRead(ctx, msg);
    }
}
