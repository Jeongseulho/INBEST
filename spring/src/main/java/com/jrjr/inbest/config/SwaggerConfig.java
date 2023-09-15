package com.jrjr.inbest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(
		title = "inBest User-Service API 명세서",
		description = "inBest User-Service API 명세서",
		version = "v2"
	)
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

		SecurityScheme securityScheme = new SecurityScheme()
			.name("Authorization")
			.type(SecurityScheme.Type.HTTP)
			.scheme("Bearer")
			.bearerFormat("JWT");

		Components components = new Components()
			.addSecuritySchemes("Authorization", securityScheme);

		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
