package org.manor.remoting.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.manor.common.FutureCommon;
import org.manor.common.Message;
import org.manor.common.Response;
import org.manor.protocol.RequestEncoder;
import org.manor.remoting.client.RpcClient;
import org.manor.remoting.client.handler.ClientHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author kanghuajie
 * @date 2022/4/2
 */
@Component
public class NettyClient implements RpcClient {
    @Autowired
    private RequestEncoder requestEncoder;

    @Autowired
    private ClientHandler clientHandler;

    private final Bootstrap bootstrap = new Bootstrap();

    {
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel){
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 0));
                        pipeline.addLast(new LoggingHandler());
                        pipeline.addLast(clientHandler);
                        pipeline.addLast(requestEncoder);
                    }
                });
    }


    @Override
    public Response sendMessage(Message message, InetSocketAddress address) {
        try {
            SocketChannel channel = getChannel(address);
            String requestId = message.getRequestId();
            CompletableFuture<Response> responseCompletableFuture = new CompletableFuture<>();
            FutureCommon.putFuture(requestId, responseCompletableFuture);
            channel.writeAndFlush(message);
            return responseCompletableFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SocketChannel getChannel(InetSocketAddress address) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(address).sync();
        return (SocketChannel) future.channel();
    }
}
