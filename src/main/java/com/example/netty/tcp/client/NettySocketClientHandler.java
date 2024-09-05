package com.example.netty.tcp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;


public class NettySocketClientHandler extends ChannelInboundHandlerAdapter {
    private String msg;

    public NettySocketClientHandler(String msg) {
        this.msg = msg;
    }

    @Override
    //public void channelActive(ChannelHandlerContext ctx) throws Exception {
    public void channelActive(ChannelHandlerContext ctx) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        ByteBuf messageBuffer = Unpooled.buffer();
        messageBuffer.writeBytes(msg.getBytes());

        ctx.writeAndFlush(messageBuffer);
        System.out.println("send message {" + msg + "}");
    }

    @Override
    //public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        System.out.println("receive message {" + ((ByteBuf) msg).toString(Charset.defaultCharset()) + "}");
    }

    @Override
    //public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        ctx.close();
    }

    @Override
    //public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        cause.printStackTrace();
        //System.out.println(cause);
        ctx.close();
    }
}