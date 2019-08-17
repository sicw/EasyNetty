package com.channelsoft.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioLoopExecutor extends Thread {

    private String name;

    private Selector selector;

    public NioLoopExecutor(){
        this("default-work-executor");
    }

    public NioLoopExecutor(String name) {
        this.name = name;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        selector = Selector.open();
    }

    public void registryChannel(SocketChannel client,int opt,Object att) throws ClosedChannelException {
        client.register(selector,opt,att);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 防止在第一次添加clientChannel时,死循环
                int readyChannels = selector.select(200);
                if (readyChannels == 0) {
                    continue;
                }
                System.out.println("NettyServer: " + name);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> ite = selectedKeys.iterator();
                while (ite.hasNext()) {
                    SelectionKey ky = ite.next();
                    if (ky.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) ky.channel();
                        ByteBuffer buff = ByteBuffer.allocate(48);
                        int ret = 0;
                        try {
                            ret = clientChannel.read(buff);
                        } catch (Exception e) {
                            ky.cancel();
                            clientChannel.close();
                            System.out.println("client has been force closed...");
                        }
                        if (ret <= 0) {
                            if (ky.isValid()) {
                                ky.cancel();
                                clientChannel.close();
                                System.out.println("client has been closed...");
                            }
                        } else {
                            try {
                                buff.flip();
                                if (clientChannel.write(buff) <= 0) {
                                    System.out.println("send data faild");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (ky.isWritable()) {
                        System.out.println("is Writeable");
                    }
                    ite.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
