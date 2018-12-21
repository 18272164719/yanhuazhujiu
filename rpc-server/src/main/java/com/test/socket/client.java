package com.test.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class client {

    public static void main(String[] args) throws IOException {

        Socket socket = null;

        //输入 输出流
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        //服务器的通讯地址
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1",10001);
        System.out.println("发送请求");
        try{
            socket = new Socket();
            //连接服务器
            socket.connect(addr);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            //向服务器输入请求
            output.writeUTF("老板，我想加工资");
            output.flush();

            //接收服务器的输出
            System.out.println(input.readUTF());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket!= null)socket.close();
            if(output!= null)output.close();
            if(input!= null)input.close();
        }

    }
}
