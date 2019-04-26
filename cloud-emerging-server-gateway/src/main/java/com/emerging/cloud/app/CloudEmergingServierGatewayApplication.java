package com.emerging.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudEmergingServierGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEmergingServierGatewayApplication.class, args);
	}

}
