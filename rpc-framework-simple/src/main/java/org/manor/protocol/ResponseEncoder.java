package org.manor.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.manor.common.Message;
import org.manor.common.Response;
import org.manor.serializer.json.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
@ChannelHandler.Sharable
public class ResponseEncoder extends MessageToByteEncoder<Response> {
    @Autowired
    private JsonSerializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        byte[] encode = serializer.encode(response);
        int length = encode.length;
        byteBuf.writeInt(length);
        byteBuf.writeBytes(encode);
    }
}
