package com.example.netty.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Setter;

@Setter
public class NettySocketServer {
    private int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture future;

    public NettySocketServer(int port) {
        this.port = port;
    }

    public void run() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // tcp
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    //protected void initChannel(SocketChannel ch) throws Exception {
                    protected void initChannel(SocketChannel ch) {
                        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
                        ChannelPipeline pipeline = ch.pipeline();
                        //handler setting
                        pipeline.addLast(new NettySocketServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            future = b.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void serverDestroy() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
        future.channel().closeFuture().sync();
    }
}
