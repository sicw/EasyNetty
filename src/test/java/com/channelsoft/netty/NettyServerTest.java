package com.channelsoft.netty;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NettyServerTest {
    @Test
    public void testNettyServer() throws IOException {
        NioLoopExecutorGroup executorGroup = new NioLoopExecutorGroup();
        List<ChannelHandler> handlers = new ArrayList<>();
        handlers.add(new SimpleInboundHandler());
        handlers.add(new SimpleInboundHandler());
        executorGroup.addWorker(new NioLoopExecutor("worker-1",handlers));
        executorGroup.addWorker(new NioLoopExecutor("worker-2",handlers));
        BossServer bossServer = new BossServer(executorGroup);
        bossServer.start();
        System.in.read();
    }
}