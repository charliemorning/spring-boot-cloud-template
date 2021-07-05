package org.charlie.template.framework.config;

import org.charlie.template.framework.interceptor.TemplateInterceptorHandler;
import org.charlie.template.framework.interceptor.TemplateInterceptorHandlerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TemplateWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(
                new TemplateInterceptorHandler()
        ).addPathPatterns("/**");

        registry.addInterceptor(
                new TemplateInterceptorHandlerAdapter()
        ).addPathPatterns("/**");
    }

}