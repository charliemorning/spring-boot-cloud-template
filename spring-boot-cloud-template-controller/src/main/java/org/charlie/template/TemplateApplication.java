package org.charlie.template;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;


@Configuration
@MapperScan("org.charlie.template.dao")  // FIXME: modify package name
@EnableScheduling
@SpringBootApplication
public class TemplateApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    // To make sure child thread can inherit context from its parent.
    static {
        System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}