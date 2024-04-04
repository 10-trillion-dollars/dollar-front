package org.example.dollar_front.config;

import feign.Feign;
import org.example.dollar_front.interceptor.CookieForwardingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
            .requestInterceptor(new CookieForwardingInterceptor());
    }
}
