package org.manor.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.manor.common.Message;
import org.manor.serializer.json.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
public class RequestEncoder extends MessageToByteEncoder<Message> {
    @Autowired
    private JsonSerializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byte[] encode = serializer.encode(message);
        int length = encode.length;
        byteBuf.writeInt(length);
        byteBuf.writeBytes(encode);
    }
}
