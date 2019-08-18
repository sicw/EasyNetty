package com.channelsoft.netty;

import java.nio.ByteBuffer;

public class SimpleInboundHandler implements ChannelInboundHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuffer buff = (ByteBuffer) msg;
        byte[] bytes = buff.array();
        String recvMsg = new String(bytes);
        System.out.println(recvMsg);
        ctx.fireChannelRead(msg);
    }
}
