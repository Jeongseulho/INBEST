package com.jrjr.inbest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jrjr.inbest.board.repository.BoardImgRepository;
import com.jrjr.inbest.board.repository.BoardRepository;
import com.jrjr.inbest.board.repository.CoCommentRepository;
import com.jrjr.inbest.board.repository.CommentRepository;

@SpringBootApplication
@EnableDiscoveryClient	//유레카 등록 어노테이션
// @EnableJpaAuditing	//jpa에서 Creatdatetime을 위한 어노테이션
@EnableMongoAuditing
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {BoardRepository.class, BoardImgRepository.class, CommentRepository.class, CoCommentRepository.class}))
public class BoardApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
