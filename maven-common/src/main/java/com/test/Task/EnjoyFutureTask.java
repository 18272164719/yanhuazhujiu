package com.test.Task;

import java.util.concurrent.*;

public class EnjoyFutureTask<V> implements Runnable, Future<V>{

    private Callable<V> callable;

    private V result;

    public EnjoyFutureTask(Callable<V> callable){
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (this){
            this.notifyAll();
        }
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        if(result != null){
            return result;
        }
        //等待线程返回的结果
        synchronized (this){
            this.wait();
        }
        return result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }


    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
