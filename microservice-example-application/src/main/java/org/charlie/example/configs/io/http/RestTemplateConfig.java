package org.charlie.example.configs.io.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.charlie.example.interceptors.out.resttemplate.ExampleClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * Rest example configure based on Apache HTTP Client.
 * <p>
 * Using the ClosableHttpClient bean from <class>org.charlie.example.configs.io.http.ApacheHttpClientConfig</class>
 *
 * @author Charlie
 */
@Configuration
public class RestTemplateConfig {

    private final CloseableHttpClient httpClient;

    @Autowired
    public RestTemplateConfig(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Bean
    @ConditionalOnBean(CloseableHttpClient.class)
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .errorHandler(new ExampleClientErrorHandler()) // define your error handler yourself
                .interceptors(new ExampleClientHttpRequestInterceptor())
                .build();
    }
}