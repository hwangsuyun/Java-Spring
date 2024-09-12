package com.example.netty.udp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class NettyUdpClientHandler extends SimpleChannelInboundHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    //public void channelActive(ChannelHandlerContext ctx) throws Exception {
    public void channelActive(ChannelHandlerContext ctx) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        // channel
        logger.info("client channel is ready!");
        // ctx.writeAndFlush("started");
        // NettyUdpClientHandler.sendMessage("  UdpServer", new InetSocketAddress("127.0.0.1",8888));
        // sendMessageWithInetAddressList(message);
        // logger.info("client send message is:   UdpServer");
    }

    @Override
    //protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // ExceptionController 에서 @ExceptionHandler(Exception.class)로 인해 throws Exception 필요 없음
        // TODO  response
        DatagramPacket datagramPacket = (DatagramPacket) msg;
        final ByteBuf buf = datagramPacket.content();
        int readableBytes = buf.readableBytes();
        byte[] content = new byte[readableBytes];
        buf.readBytes(content);
        String serverMessage = new String(content);
        logger.info("reserveServerResponse is : " + serverMessage);
    }

    /**
     * //@param msg
     * //@param inetSocketAddress
     */
    public static void sendMessage(final String msg,final InetSocketAddress inetSocketAddress){
        if(msg == null) {
            throw new NullPointerException("msg is null");
        }
        // TODO msg Byte buf
        senderInternal(datagramPacket(msg, inetSocketAddress));
    }

    /**
     *
     * //@param msg
     * //@param inetSocketAddress
     * //@return DatagramPacket
     */
    private static DatagramPacket datagramPacket(String msg, InetSocketAddress inetSocketAddress){
        ByteBuf dataBuf = Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8);
        return new DatagramPacket(dataBuf, inetSocketAddress);
    }

    /**
     *
     * //@param datagramPacket
     */
    private static void senderInternal(final DatagramPacket datagramPacket) {
        //logger.info("LogPushUdpClient.channel" + NettyUdpClient.channel);
        if(NettyUdpClient.channel != null) {
            NettyUdpClient.channel.writeAndFlush(datagramPacket).addListener(
                    future -> {
                        //boolean success = future.isSuccess();
                        //if(logger.isInfoEnabled()){
                        //    logger.info("Sender datagramPacket result : "+success);
                        //}
                    });
        }else{
            throw new NullPointerException("channel is null");
        }
    }
}
