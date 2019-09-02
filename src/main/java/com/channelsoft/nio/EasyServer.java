package com.channelsoft.nio;

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
public class EasyServer {
    public static void main(String[] args) throws Exception {
        int clientNum = 0;

        ServerSocketChannel svrSocketChannl = ServerSocketChannel.open();
        svrSocketChannl.configureBlocking(false);

        Selector selector = Selector.open();
        int ops = svrSocketChannl.validOps();
        svrSocketChannl.register(selector, ops, null);

        InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 10086);
        svrSocketChannl.bind(hostAddress);

        while (true) {
            int readyChannels = selector.select();
            if(readyChannels == 0){
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> ite = selectedKeys.iterator();
            while (ite.hasNext()) {
                SelectionKey ky = ite.next();
                if (ky.isAcceptable()) {
                    SocketChannel clientChannel = svrSocketChannl.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("Accept a new Client: " + clientNum++);
                } else if (ky.isReadable()) {
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
    }
}
