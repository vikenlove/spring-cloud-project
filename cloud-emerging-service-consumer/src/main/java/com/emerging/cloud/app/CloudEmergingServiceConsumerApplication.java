package com.emerging.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class CloudEmergingServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEmergingServiceConsumerApplication.class, args);
	}

}
