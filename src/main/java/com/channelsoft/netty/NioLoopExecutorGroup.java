package com.channelsoft.netty;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioLoopExecutorGroup {

    private List<NioLoopExecutor> workGroup = new ArrayList();

    private int count = 0;

    public void addWorker(NioLoopExecutor executor){
        workGroup.add(executor);
    }

    public List<NioLoopExecutor> getAllNioLoopExecutor(){
        return workGroup;
    }

    public void registryChannel(SocketChannel client, int opt, Object att) throws ClosedChannelException {
        int index = count % workGroup.size();
        NioLoopExecutor executor = workGroup.get(index);
        count++;
        executor.registryChannel(client,opt,att);
    }

    public void runAllWorker(){
        for (NioLoopExecutor nioLoopExecutor : workGroup) {
            nioLoopExecutor.start();
        }
    }
}
