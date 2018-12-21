package com.test.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketProxy {
    //jdk线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void publish(Class service,int port){
        ServerSocket serverSocket = null;
        try{
            System.out.println("我一直在外面");
            serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ServerHandler(socket,service));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
