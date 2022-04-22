package org.charlie.example.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * user configure.
 *
 * @author charlie
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "example")
public class ExampleConfig {
    private String redisKeyPrefix;
}
