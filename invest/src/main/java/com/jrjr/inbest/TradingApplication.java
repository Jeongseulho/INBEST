package com.jrjr.inbest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient	//유레카 등록 어노테이션
@EnableJpaAuditing	//jpa에서 Creatdatetime을 위한 어노테이션
@EnableScheduling
public class TradingApplication {
	public static void main(String[] args) {

		SpringApplication.run(TradingApplication.class, args);


	}
}
