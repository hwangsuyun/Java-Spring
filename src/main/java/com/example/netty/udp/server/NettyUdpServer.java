package com.example.netty.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@SuppressWarnings("unused")
public class NettyUdpServer {
    private final Bootstrap bootstrap;
    private final NioEventLoopGroup acceptGroup;
    private Channel channel;

    private NettyUdpServer() {
        bootstrap = new Bootstrap();
        acceptGroup = new NioEventLoopGroup();
        bootstrap.group(acceptGroup)
                .channel(NioDatagramChannel.class) // udp
                .option(ChannelOption.SO_BROADCAST, true) // udp
                .handler(new ChannelInitializer<>() {
                    @Override
                    //protected void initChannel(Channel ch) throws Exception {
                    protected void initChannel(Channel ch) {
                        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyUdpServerHandler());
                    }
                });
    }

    public static NettyUdpServer getInstance() {
        return NettyUdpServerHolder.INSTANCE;
    }

    private static final class NettyUdpServerHolder {
        static final NettyUdpServer INSTANCE = new NettyUdpServer();
    }

    public Channel getChannel(){
        return channel;
    }

    public void start(int port) throws Exception {
        try {
            channel = bootstrap.bind(port).sync().channel();
            System.out.println("UdpServer start succeed " + port);
            channel.closeFuture().await();
        } finally {
            acceptGroup.shutdownGracefully();
        }
    }

    public void serverDestroy() throws InterruptedException {
        acceptGroup.shutdownGracefully().sync();
        channel.closeFuture().sync();
    }
}
