package org.charlie.example.framework.configs.thread;

import lombok.Data;
import org.charlie.example.framework.constants.thread.ThreadConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
@Configuration
@ConfigurationProperties("example.thread")
public class ThreadCustomConfig {

    private int count = ThreadConstants.THREAD_POOL_DEFAULT_COUNT;

    private int maxCount = ThreadConstants.THREAD_POOL_DEFAULT_MAX_COUNT;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration keepAliveSec = Duration.ofSeconds(ThreadConstants.THREAD_KEEP_ALIVE_SECOND);
}
