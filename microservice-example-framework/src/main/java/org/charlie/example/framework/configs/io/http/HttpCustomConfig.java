package org.charlie.example.framework.configs.io.http;

import com.google.common.collect.Lists;
import lombok.Data;
import org.charlie.example.framework.constants.io.http.HttpConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("example.http")
public class HttpCustomConfig {

    private int poolMaxConnections = HttpConstants.DEFAULT_POOL_MANAGER_MAX_TOTAL_CONNECTIONS;

    private int defaultPoolConnectionsPerRoute = HttpConstants.POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS;

    private int retry = HttpConstants.DEFAULT_HTTP_CLIENT_RETRY;

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration managerTtlMs =  Duration.ofSeconds(HttpConstants.HTTP_CLIENT_CONN_MANAGER_TTL);

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration keepAliveMs = Duration.ofMillis(HttpConstants.DEFAULT_KEEP_ALIVE_MS);

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration connectionTimeoutMs = Duration.ofMillis(HttpConstants.DEFAULT_CONNECTION_TIMEOUT_MS);

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration requestTimeoutMs = Duration.ofMillis(HttpConstants.DEFAULT_REQUEST_TIMEOUT_MS);

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration socketTimeoutMs = Duration.ofMillis(HttpConstants.DEFAULT_SOCKET_TIMEOUT_MS);

    private final List<HttpRouteConfig> connectionsPerRoute = Lists.newArrayList();

    @Data
    public static class HttpRouteConfig {
        private String url;
        private int connections;
    }
}
