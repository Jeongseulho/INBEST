package com.jrjr.invest.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jrjr.invest.global.filter.DecodingFilter;

@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<DecodingFilter> parameterDecodingFilter() {
		FilterRegistrationBean<DecodingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new DecodingFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}