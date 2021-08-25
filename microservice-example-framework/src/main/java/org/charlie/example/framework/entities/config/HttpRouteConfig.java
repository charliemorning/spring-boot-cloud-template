package org.charlie.example.framework.entities.config;


import lombok.Data;

@Data
public class HttpRouteConfig {
    private String url;
    private int connections;
}
