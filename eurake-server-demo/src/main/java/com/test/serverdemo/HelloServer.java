package com.test.serverdemo;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloServer {

    @RequestMapping("/hello")
    public String hello() {
        //打印服务的服务id
        return "hello,this is hello-service";
    }
}
