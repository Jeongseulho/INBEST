package com.jrjr.inbest.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {

	@Value("${cloud.aws.s3.access-key}")
	private String accessKey;

	@Value("${cloud.aws.s3.secret-key}")
	private String secretKey;

	@Bean
	public AmazonS3 amazonS3() {
		final BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
			basicAwsCredentials);

		return AmazonS3ClientBuilder.standard()
			.withCredentials(awsStaticCredentialsProvider)
			.withRegion(Regions.AP_NORTHEAST_2)
			.build();
	}
}
