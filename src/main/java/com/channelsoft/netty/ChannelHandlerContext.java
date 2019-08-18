package com.channelsoft.netty;

public class ChannelHandlerContext {

    private ChannelHandlerContext prev;
    private ChannelHandlerContext next;

    private ChannelHandler channelHandler;

    private ChannelHandlerContext findContextInbound(){
        return null;
    }

    private ChannelHandlerContext findContextOutbound(){
        return null;
    }

    public ChannelHandlerContext fireChannelRead(Object msg){
        ChannelHandlerContext next = findContextInbound();
        next.invokeChannelRead(msg);
        return this;
    }

    public void invokeChannelRead(Object msg){
        ChannelInboundHandler inboundHandler = (ChannelInboundHandler) channelHandler;
        inboundHandler.channelRead(this,msg);
    }


    /**
     * 自己的handler处理
     * write
     * read
     *
     * firexxx是将请求下发到后面的handler
     */
}