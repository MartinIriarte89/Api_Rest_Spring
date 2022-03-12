package com.martiniriarte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.martiniriarte.controlador"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Practica Api Rest con Spring")
				.description("Este proyecto es un ejemplo de desarrollo de una api rest con Spring Boot")
				.version("1.0")
				.contact(new Contact("Martin Iriarte", "https://github.com/MartinIriarte89", "iriartemartin2@gmail.com"))
				.build();
	}

}
