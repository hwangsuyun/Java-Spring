package com.example.netty.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class NettySocketServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    //public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        String readMessage = ((ByteBuf) msg).toString(StandardCharsets.UTF_8);
        ctx.write(msg);
        System.out.println("message from received : " + readMessage);
    }

    @Override
    //public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        cause.printStackTrace();
        ctx.close();
    }
}
