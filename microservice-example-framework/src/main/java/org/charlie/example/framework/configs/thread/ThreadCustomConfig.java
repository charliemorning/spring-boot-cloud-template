package org.charlie.example.framework.configs.thread;

import lombok.Data;
import org.charlie.example.framework.constants.thread.ThreadConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("example.thread")
public class ThreadCustomConfig {

    private int count = ThreadConstants.THREAD_POOL_DEFAULT_COUNT;

    private int maxCount = ThreadConstants.THREAD_POOL_DEFAULT_MAX_COUNT;

}
