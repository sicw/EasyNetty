package com.channelsoft.netty;

import org.junit.Test;

import java.io.IOException;

public class NettyServerTest {
    @Test
    public void testNettyServer() throws IOException {
        NioLoopExecutorGroup executorGroup = new NioLoopExecutorGroup();
        executorGroup.addWorker(new NioLoopExecutor());
        executorGroup.addWorker(new NioLoopExecutor());
        BossServer bossServer = new BossServer(executorGroup);
        bossServer.start();
        System.in.read();
    }
}