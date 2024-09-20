package com.example.netty.udp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


public class NettyUdpServerHandler extends SimpleChannelInboundHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    //protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        // client
        logger.info("===== client =====");
        DatagramPacket datagramPacket = (DatagramPacket) msg;
        final ByteBuf buf = datagramPacket.content();
        int readableBytes = buf.readableBytes();
        byte[] content = new byte[readableBytes];
        buf.readBytes(content);
        String clientMessage = new String(content, StandardCharsets.UTF_8);
        logger.info("clientMessage is : " + clientMessage);

        if (clientMessage.contains("NettyUdpServer")) {
            ctx.writeAndFlush(
                    new DatagramPacket(
                            Unpooled.wrappedBuffer("helloClient".getBytes()), datagramPacket.sender()));
        }
    }
}
