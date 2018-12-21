package com.test.Task;

import net.sf.json.JSONObject;

import java.util.concurrent.*;

public class TestFutureTask {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        Callable<JSONObject> query1 = new Callable<JSONObject>(){
            @Override
            public JSONObject call() throws Exception {

                return null;
            }
        };
        FutureTask<JSONObject> task1 = new FutureTask<JSONObject>(query1);

        executor.submit(task1);

        try {
            System.out.println(task1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
