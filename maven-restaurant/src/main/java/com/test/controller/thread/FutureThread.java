package com.test.controller.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

@SuppressWarnings("unchecked")
public class FutureThread {

    public static void main(String[] args) {
        System.out.println("程序开始运行------------");
        Date d = new Date();

        int taskSize = 5;
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        //创建有多个返回值的任务
        List<Future> list = new ArrayList<>();
        for(int i = 0 ; i < taskSize;i++){
            Callable c = new MyCallable(i+"");
            Future f = pool.submit(c);
            list.add(f);
        }
        pool.shutdown();
        try{
            for(Future f : list){
                // 从Future对象上获取任务的返回值，并输出到控制台
                System.out.println(">>>" + f.get().toString());
            }
            Date date2 = new Date();
            System.out.println("----程序结束运行----，程序运行时间【"
                    + (date2.getTime() - d.getTime()) + "毫秒】");
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException  e){
            e.printStackTrace();
        }
    }

}

class MyCallable implements Callable<Object>{

    private String taskNum;

    MyCallable(String taskNum){
        this.taskNum = taskNum;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(">>>" + taskNum + "任务启动");
        Date time1 = new Date();
        Thread.sleep(1000);
        Date time2 = new Date();
        long time = time2.getTime() - time1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}
