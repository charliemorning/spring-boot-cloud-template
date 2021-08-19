package org.charlie.template.framework.configs.web;

import org.charlie.template.framework.interceptors.web.GlobalTraceIdInterceptorHandler;
import org.charlie.template.framework.interceptors.web.TemplateInterceptorHandler;
import org.charlie.template.framework.interceptors.web.TemplateInterceptorHandlerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
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
                new TemplateInterceptorHandler()
        ).addPathPatterns("/**");

        // register custom interceptor adapter
        registry.addInterceptor(
                new TemplateInterceptorHandlerAdapter()
        ).addPathPatterns("/**");
    }

}