package com.channelsoft.netty;

public class ChannelHandlerContext {

    private ChannelHandlerContext prev;
    private ChannelHandlerContext next;

    private ChannelHandler channelHandler;

    private boolean inbound;
    private boolean outbound;

    private ChannelHandlerContext findContextInbound(){
        ChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.inbound);
        return ctx;
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

    public ChannelHandlerContext getPrev() {
        return prev;
    }

    public void setPrev(ChannelHandlerContext prev) {
        this.prev = prev;
    }

    public ChannelHandlerContext getNext() {
        return next;
    }

    public void setNext(ChannelHandlerContext next) {
        this.next = next;
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    public boolean isInbound() {
        return inbound;
    }

    public void setInbound(boolean inbound) {
        this.inbound = inbound;
    }

    public boolean isOutbound() {
        return outbound;
    }

    public void setOutbound(boolean outbound) {
        this.outbound = outbound;
    }
}