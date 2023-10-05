package com.jrjr.realtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RealTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealTimeApplication.class, args);
	}

}
