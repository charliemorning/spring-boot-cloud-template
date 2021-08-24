package org.charlie.example.framework.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 *
 *
 * @author charlie
 */
@Data
@Component
@ConfigurationProperties(prefix = "example-config")
public class ExampleConfig {
    private int threadNum;
    private int idleAndExpireConnectionsTimeoutSeconeds;
    private String redisKeyPrefix;
    private Map<String, Integer> connectionsPerRoute;
}
