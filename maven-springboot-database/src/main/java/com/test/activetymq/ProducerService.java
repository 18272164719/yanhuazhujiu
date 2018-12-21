package com.test.activetymq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;


@Service
public class ProducerService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    public void sendMessage(String msg){
        jmsMessagingTemplate.convertAndSend("goods.queue",msg);
    }

}
