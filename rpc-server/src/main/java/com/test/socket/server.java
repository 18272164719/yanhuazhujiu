package com.test.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    public static void main(String[] args) throws IOException {

        //jdk线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //服务器的通讯地址
        ServerSocket serverSocket = new ServerSocket(10001);
        System.out.println("老板的手机一直在震动");

        try {
            while (true) {

                Socket socket = serverSocket.accept();
                executorService.execute(new ServerTask(socket));
            }
        } finally {
            serverSocket.close();
        }

    }

    private static class ServerTask implements Runnable {

        private Socket socket;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //实例化与客户端的输入输出流
            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                //接收客户端的输出 也就是服务器的输入
                String userName = input.readUTF();
                System.out.println("accept client message"+userName);

                output.writeUTF("滚蛋！");
                output.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


