package com.test.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class RomoteInvocationHandler implements InvocationHandler{

    private Class<?> serviceInterface;
    private String host;
    private int port;

    public RomoteInvocationHandler(Class<?> serviceInterface, String host, int port) {
        this.serviceInterface = serviceInterface;
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = null;  //socket 通讯类

        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try{
            socket = new Socket(host,port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(serviceInterface);
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);
            output.flush();

            System.out.println("发送成功");
            return input.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket!= null)socket.close();
            if(output!= null)output.close();
            if(input!= null)input.close();
        }
        return null;
    }
}
