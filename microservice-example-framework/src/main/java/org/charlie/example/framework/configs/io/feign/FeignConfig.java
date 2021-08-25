package org.charlie.example.framework.configs.io.feign;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.charlie.example.framework.interceptors.out.feign.GlobalTraceIdRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@ConditionalOnClass(Feign.class)
@Configuration
public class FeignConfig {

    CloseableHttpClient closeableHttpClient;

    @Autowired
    public void setCloseableHttpClient(CloseableHttpClient closeableHttpClient) {
        this.closeableHttpClient = closeableHttpClient;
    }

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
        return new ExampleErrorDecoder();
    }

    @Bean
    @ConditionalOnBean(CloseableHttpClient.class)
    public ApacheHttpClient apacheHttpClient() {
        return new ApacheHttpClient(closeableHttpClient);
    }
}