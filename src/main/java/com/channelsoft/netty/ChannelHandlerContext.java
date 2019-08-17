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

    public Object fireChannelRead(){
        ChannelHandlerContext next = findContextInbound();
        return next.invokeChannelRead();
    }

    public Object invokeChannelRead(){
        return null;
    }


    /**
     * 自己的handler处理
     * write
     * read
     *
     * firexxx是将请求下发到后面的handler
     */
}