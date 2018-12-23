package com.test.eurakeconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurakeConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurakeConsumerApplication.class, args);
	}

}

