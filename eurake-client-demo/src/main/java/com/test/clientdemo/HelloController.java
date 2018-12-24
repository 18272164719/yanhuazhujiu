package com.test.clientdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String helloConsumer() {
        //调用hello-service服务，注意这里用的是服务名，而不是具体的ip+port
        return "hello consumer finish !!!";
    }
}
