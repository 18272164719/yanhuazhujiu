package com.test.springbootdatabase;

import com.test.activetymq.ProducerService;
import com.test.entity.Goods;
import com.test.rabbitmq.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRabbitMq {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private ProducerService producerService;



    @Test
    public void send(){
        System.out.println("开始发送-----------------");
        Goods goods = new Goods();
        goods.setId(1);
        goods.setName("张三");
        rabbitTemplate.convertAndSend("notify.payment",goods);
    }

    @Test
    public void sendActiveMq(){
        System.out.println("开始发送-----------------");
        producerService.sendMessage("hello activetyMq");
    }
}
