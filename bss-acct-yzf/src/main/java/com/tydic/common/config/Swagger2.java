package com.tydic.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
	// xxx/swagger-ui.html 访问路径

	@Value("${swagger.enable}")
	private boolean enableSwagger;

	@Bean
	public Docket createRestApi() {
		List<Parameter> pars = new ArrayList<Parameter>();

		ParameterBuilder token = new ParameterBuilder();
		token.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build();

		ParameterBuilder sign = new ParameterBuilder();
		sign.name("sign").description("sign").modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build();
		
//		pars.add(token.build());
//		pars.add(sign.build());
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.enable(enableSwagger)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.tydic.bus.api"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API文档").version("1.0").build();
	}
	
}
