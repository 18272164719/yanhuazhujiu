package com.test.controller.thread;




public class TestAccount {
    public static void main(String[] args) {
        Account account = new Account();
        Custome c1 = new Custome(account,1000);
        Custome c2 = new Custome(account,1000);

        Thread t1 = new Thread(c1);
        t1.setName("甲");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(c2);
        t2.setName("乙");
        t1.start();
        t2.start();
    }

}

class Account{
    private int count ;

    public void des(int money){
        synchronized(this){
            for(int i=0 ; i< 5 ;i++){
                count += money;
                System.out.println(Thread.currentThread().getName() +": 当前余额为 "+ count);
            }
        }
    }
}

class Custome implements Runnable{
    private Account account;
    private int money ;

    public Custome(Account account,int money){
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {
        //进行转账
        account.des(money);
    }
}