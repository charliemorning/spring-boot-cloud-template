package org.charlie.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;


@Configuration
@MapperScan("org.charlie.example.dao")  // FIXME: modify package name
@EnableScheduling
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

    // To make sure child thread can inherit context from its parent.
    static {
        System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}