package org.manor.remoting.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.manor.protocol.ResponseEncoder;
import org.manor.remoting.server.RpcServer;
import org.manor.remoting.server.handler.ServerResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@Slf4j
@Component("server")
public class NettyRpcServer implements RpcServer {
    @Autowired
    private ServerResponseHandler serverResponseHandler;

    @Autowired
    private ResponseEncoder responseEncoder;

    @Override
    public void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 0));
                            pipeline.addLast(new LoggingHandler());
                            pipeline.addLast(serverResponseHandler);
                            pipeline.addLast(responseEncoder);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器时发生错误");
            e.printStackTrace();
        } finally {
            log.info("关闭资源");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
