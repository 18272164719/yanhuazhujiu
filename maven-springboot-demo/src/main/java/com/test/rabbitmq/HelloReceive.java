package com.test.rabbitmq;

import com.test.entity.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class HelloReceive {

    /*@RabbitListener(queues = "queue")
    public void processC(String s){
        System.out.println("SUCCESS:receive:----------"+s);
    }*/

    //@RabbitListener(queues = "notify.payment")
    public void processC1(Goods g){
        System.out.println("SUCCESS:receive:----------"+g.getName());
    }
}
