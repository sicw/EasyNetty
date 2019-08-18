package com.channelsoft.netty;

public interface ChannelInboundHandler extends ChannelHandler {
    void channelRead(ChannelHandlerContext ctx,Object msg);
}
