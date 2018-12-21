package com.test.controller.thread;

public class Thread2 implements Runnable{

    private String name;

    public Thread2(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for(int i=0; i<=50; i++){
            System.out.println(Thread.currentThread().getName()+"     运行"+i);
            try{
                Thread.sleep((int)Math.random()*20);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread2 thread2 = new Thread2();
        Thread thread = new Thread(thread2);
        thread.setName("A");
        Thread thread1 = new Thread(thread2);
        thread1.setName("B");
        thread.start();
        thread1.start();

    }
}
