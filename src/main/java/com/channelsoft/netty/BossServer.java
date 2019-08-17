package com.channelsoft.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sicwen
 * @date 2019/04/03
 */
public class BossServer extends Thread {

    private ServerSocketChannel svrSocketChannl;

    private Selector selector;

    private NioLoopExecutorGroup nioLoopExecutorGroup;

    public BossServer(NioLoopExecutorGroup group){
        nioLoopExecutorGroup = group;

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        svrSocketChannl = ServerSocketChannel.open();
        svrSocketChannl.configureBlocking(false);

        selector = Selector.open();
        int ops = svrSocketChannl.validOps();
        svrSocketChannl.register(selector, ops, null);

        InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 10086);
        svrSocketChannl.bind(hostAddress);
    }

    @Override
    public void run() {

        nioLoopExecutorGroup.runAllWorker();

        try {
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> ite = selectedKeys.iterator();
                while (ite.hasNext()) {
                    SelectionKey ky = ite.next();
                    if (ky.isAcceptable()) {
                        SocketChannel clientChannel = svrSocketChannl.accept();
                        clientChannel.configureBlocking(false);
                        // 选择一个NioLoopExcutor 并注册channel
                        nioLoopExecutorGroup.registryChannel(clientChannel,SelectionKey.OP_READ,null);
                    }
                    ite.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}