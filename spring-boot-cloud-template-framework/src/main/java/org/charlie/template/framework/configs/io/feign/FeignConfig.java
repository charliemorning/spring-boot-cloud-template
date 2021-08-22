package org.charlie.template.framework.configs.io.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.interceptors.feign.GlobalTraceIdRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level Logger() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new GlobalTraceIdRequestInterceptor();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("username", "password");
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}