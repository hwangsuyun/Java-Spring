package com.example.netty.udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@SuppressWarnings("unused")
public class NettyUdpClient {
    private final Bootstrap bootstrap;
    public final NioEventLoopGroup workerGroup;
    public static Channel channel;
    //private static final Charset ASCII = Charset.forName("ASCII");

    private NettyUdpClient() {
        bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class) // udp
                .option(ChannelOption.SO_BROADCAST, true) // udp
                .handler(new ChannelInitializer<>() {
                    @Override
                    //protected void initChannel(Channel ch) throws Exception {
                    protected void initChannel(Channel ch) {
                        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
                        ChannelPipeline pipeline = ch.pipeline();
                        //pipeline.addLast(new StringDecoder(ASCII))
                        //        .addLast(new StringEncoder(ASCII))
                        pipeline.addLast(new NettyUdpClientHandler());
                    }
                });
    }

    public static NettyUdpClient getInstance() {
        return NettyUdpClientHolder.INSTANCE;
    }

    private static final class NettyUdpClientHolder {
        static final NettyUdpClient INSTANCE = new NettyUdpClient();
    }

    public Channel getChannel(){
        return channel;
    }

    public void start(int port) throws Exception {
        try {
            channel = bootstrap.bind(port).sync().channel();
            System.out.println("UdpServer start succeed " + port);
            channel.closeFuture().await(1000);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void serverDestroy() throws InterruptedException {
        workerGroup.shutdownGracefully().sync();
        channel.closeFuture().sync();
    }
}
