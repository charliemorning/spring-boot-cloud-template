package org.charlie.example.framework.configs;

import lombok.Data;
import org.charlie.example.framework.constants.io.http.HttpConstants;
import org.charlie.example.framework.constants.thread.ThreadConstants;
import org.charlie.example.framework.entities.config.HttpRouteConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


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
