package com.jrjr.inbest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsSesConfig {

	@Value("${cloud.aws.ses.access-key}")
	private String accessKey;

	@Value("${cloud.aws.ses.secret-key}")
	private String secretKey;

	@Bean
	public AmazonSimpleEmailService amazonSimpleEmailService() {
		final BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
			basicAwsCredentials);

		return AmazonSimpleEmailServiceClientBuilder.standard()
			.withCredentials(awsStaticCredentialsProvider)
			.withRegion("ap-northeast-2")
			.build();
	}
}
