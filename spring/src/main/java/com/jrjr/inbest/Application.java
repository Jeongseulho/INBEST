package com.jrjr.inbest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jrjr.inbest.dictionary.repository.FinancialWordRepository;
import com.jrjr.inbest.email.repository.EmailVerificationCodeRepository;
import com.jrjr.inbest.jwt.repository.RefreshTokenRepository;

@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {FinancialWordRepository.class,
		EmailVerificationCodeRepository.class,
		RefreshTokenRepository.class,
	}))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
