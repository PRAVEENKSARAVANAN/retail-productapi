package com.retail.productapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * Configuration for the Swagger Documentation
 */
@Configuration
public class SwaggerConfig {

    /**
     * Swagger UI Configuration
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.retail.productapi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }


    /**
     * Meta data for Swagger UI API Documentation
     *
     * @return
     */
    ApiInfo metaData(){

        ApiInfo apiInfo = new ApiInfo(
                "Retail Product API service",
                "Rest End points for MyRetail Product application",
                "1.0",
                "Terms of service",
                new Contact("Praveen Kumar Saravanan", "https://www.linkedin.com/in/praveenkumarsaravanan/", "be.praveen@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());

        return apiInfo;
    }


}