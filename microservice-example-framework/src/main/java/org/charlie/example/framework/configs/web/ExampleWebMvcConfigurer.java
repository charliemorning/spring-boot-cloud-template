package org.charlie.example.framework.configs.web;

import org.charlie.example.framework.interceptors.in.web.GlobalTraceIdInterceptorHandler;
import org.charlie.example.framework.interceptors.in.web.ExampleWebInterceptorHandler;
import org.charlie.example.framework.interceptors.in.web.ExampleWebInterceptorHandlerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * To register interceptors.
 *
 * FIXME: remove unnecessary interceptors.
 *
 * @author Charie
 */
@Configuration
public class ExampleWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        // register global trace id handler
        registry.addInterceptor(
                new GlobalTraceIdInterceptorHandler()
        ).addPathPatterns("/**");

        // register custom interceptor
        registry.addInterceptor(
                new ExampleWebInterceptorHandler()
        ).addPathPatterns("/**");

        // register custom interceptor adapter
        registry.addInterceptor(
                new ExampleWebInterceptorHandlerAdapter()
        ).addPathPatterns("/**");
    }

}