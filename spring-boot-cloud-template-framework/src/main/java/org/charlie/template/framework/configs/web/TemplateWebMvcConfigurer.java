package org.charlie.template.framework.configs.web;

import org.charlie.template.framework.interceptors.in.web.GlobalTraceIdInterceptorHandler;
import org.charlie.template.framework.interceptors.in.web.CustomWebInterceptorHandler;
import org.charlie.template.framework.interceptors.in.web.CustomWebInterceptorHandlerAdapter;
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
public class TemplateWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        // register global trace id handler
        registry.addInterceptor(
                new GlobalTraceIdInterceptorHandler()
        ).addPathPatterns("/**");

        // register custom interceptor
        registry.addInterceptor(
                new CustomWebInterceptorHandler()
        ).addPathPatterns("/**");

        // register custom interceptor adapter
        registry.addInterceptor(
                new CustomWebInterceptorHandlerAdapter()
        ).addPathPatterns("/**");
    }

}