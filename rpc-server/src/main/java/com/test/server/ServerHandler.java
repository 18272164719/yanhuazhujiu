package com.test.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ServerHandler implements Runnable {
    private Socket socket;
    private Class service;

    public ServerHandler(Socket socket, Class service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            //拿到接口
            Class<?> serviceInteface = (Class<?>) input.readObject();
            //拿到方法名
            String methodName = input.readUTF();
            //拿到参数类型
            Class<?>[] paramType = (Class<?>[]) input.readObject();
            //拿到参数值
            Object[] params = (Object[]) input.readObject();

            Method method = serviceInteface.getMethod(methodName, paramType);
            Object invoke = method.invoke(service.newInstance(), params);
            //把结果返回客户端
            output.writeObject(invoke);

            socket.close();
            output.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
