package org.charlie.template.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component()
@ConfigurationProperties(prefix = "foo")
public class FooConfig {
    private String redisKeyPrefix;
}
