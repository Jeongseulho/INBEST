package com.jrjr.invest.global.config;

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
		title = "inBest invest-service API 명세서",
		description = "inBest invest-service API 명세서",
		version = "v2"
	)
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		String ACCESS_TOKEN_KEY = "Authorization";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(ACCESS_TOKEN_KEY);

		SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
			.name(ACCESS_TOKEN_KEY)
			.type(SecurityScheme.Type.HTTP)
			.scheme("Bearer")
			.bearerFormat("JWT");

		Components components = new Components().addSecuritySchemes(ACCESS_TOKEN_KEY, accessTokenSecurityScheme);

		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
