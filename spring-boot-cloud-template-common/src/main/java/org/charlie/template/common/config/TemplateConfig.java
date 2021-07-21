package org.charlie.template.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateConfig {
    private int threadNum;
    private String redisKeyPrefix;
}
