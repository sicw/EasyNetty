package com.channelsoft.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author sicwen
 * @date 2019/04/03
 */
public class EasyClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            //创建socket
            Socket client = new Socket("127.0.0.1", 10086);
            //write
            OutputStream outStream = null;
            InputStream is = null;
            try {
                outStream = client.getOutputStream();
                outStream.write("sicwen".getBytes());

                //is = client.getInputStream();
                //byte[] buff = new byte[14];
//                int ret = is.read(buff);
//                if(ret > 0){
//                    System.out.println("接收   " + new String(buff) + " 第 " + i + "次");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                outStream.close();
                //is.close();
                client.close();
            }
        }
        Thread.sleep(500);
    }
}