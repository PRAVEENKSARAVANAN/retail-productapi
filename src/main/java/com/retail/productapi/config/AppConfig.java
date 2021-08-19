package com.retail.productapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${config.api.read.timeout}")
    private int readTimeOut;

    @Value("${config.api.conn.timeout}")
    private int connectionTimeOut;

    /**
     *
     * Build RestTemplate for invoking external API
     *
     * @return
     */
    @Bean
    RestTemplate getRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(connectionTimeOut));
        restTemplateBuilder.setReadTimeout(Duration.ofSeconds(readTimeOut));
        return restTemplateBuilder.build();
    }
}
