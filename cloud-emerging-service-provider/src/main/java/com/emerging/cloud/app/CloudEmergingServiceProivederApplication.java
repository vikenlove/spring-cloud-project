package com.emerging.cloud.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.emerging.service.persistence.mapper")
public class CloudEmergingServiceProivederApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEmergingServiceProivederApplication.class, args);
	}

}
