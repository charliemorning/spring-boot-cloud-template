package org.charlie.template.framework.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Data
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateConfig {
    private int threadNum;
    private int idleAndExpireConnectionsTimeoutSeconeds;
    private String redisKeyPrefix;
    private Map<String, Integer> connectionsPerRoute;
}
