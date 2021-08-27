package org.charlie.example.framework.configs.io.http;

import com.google.common.collect.Lists;
import lombok.Data;
import org.charlie.example.framework.constants.io.http.HttpConstants;
import org.charlie.example.framework.entities.config.HttpRouteConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("example.http")
public class HttpCustomConfig {

    private int poolMaxConnections = HttpConstants.DEFAULT_POOL_MANAGER_MAX_TOTAL_CONNECTIONS;

    private int defaultPoolConnectionsPerRoute = HttpConstants.POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS;

    private int retry = HttpConstants.DEFAULT_HTTP_CLIENT_RETRY;

    private int keepAliveMs = HttpConstants.DEFAULT_KEEP_ALIVE_MS;

    private int connectionTimeoutMs = HttpConstants.DEFAULT_CONNECTION_TIMEOUT_MS;

    private int requestTimeoutMs = HttpConstants.DEFAULT_REQUEST_TIMEOUT_MS;

    private int socketTimeoutMs = HttpConstants.DEFAULT_SOCKET_TIMEOUT_MS;

    private final List<HttpRouteConfig> connectionsPerRoute = Lists.newArrayList();

    private int idleAndExpireConnectionsTimeoutSeconds;

    public static class HttpRouteConfig {
        private String url;
        private int connections;
    }
}
