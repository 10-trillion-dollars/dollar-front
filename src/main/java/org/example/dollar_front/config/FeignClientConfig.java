package org.example.dollar_front.config;

import feign.Logger;
import org.example.dollar_front.interceptor.CookieForwardingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public CookieForwardingInterceptor cookieForwardingInterceptor() {
        return new CookieForwardingInterceptor();
    }
}
