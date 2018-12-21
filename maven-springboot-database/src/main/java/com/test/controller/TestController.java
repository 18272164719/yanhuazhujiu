package com.test.controller;

import com.test.entity.Goods;
import com.test.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
public class TestController {

    /**
     * 测试悲观锁 和乐观锁
     */

    @Autowired
    private IGoodsService goodsService;

    private CountDownLatch ctl = new CountDownLatch(2);

    @RequestMapping("hello")
    public String hello() {
        return "springBoot hello!";
    }

    @RequestMapping("selectById")
    @ResponseBody
    public String selectById() {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    buy();
                }
            }).start();

        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctl.countDown();
        return "查询成功";
    }

    private void buy (){
        try {
            ctl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            goodsService.buyGoods();
            System.out.println("SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("FAILUE");
        }

    }

    @RequestMapping("list")
    @ResponseBody
    public List<Goods> selectGoods() {
        return goodsService.listGoods();
    }
}
