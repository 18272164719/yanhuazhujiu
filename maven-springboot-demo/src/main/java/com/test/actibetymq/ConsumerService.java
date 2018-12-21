package com.test.actibetymq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @JmsListener(destination="goods.queue")
    public void receiveMessage(String text){
        System.out.println("【*** 接收消息 ***】" + text);

    }
}
