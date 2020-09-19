package org.charlie.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component()
@ConfigurationProperties(prefix = "foo")
public class FooConfig {
    private String redisKeyPrefix;
    private NLPConfig nlp;
}
