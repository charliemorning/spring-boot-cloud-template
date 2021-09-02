package org.charlie.example.framework.configs.cache;


import lombok.Data;
import org.charlie.example.framework.constants.cache.CacheConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
@Configuration
@ConfigurationProperties("example.cache")
public class CacheCustomConfig {

    @Data
    @Configuration
    @ConfigurationProperties("example.cache.local")
    public static class LocalCacheConfig {
        private boolean enable = CacheConstants.DEFAULT_LOCAL_CACHE_ENABLE;
        private int initSize = CacheConstants.DEFAULT_LOCAL_CACHE_INIT_SIZE;
        private int maxSize = CacheConstants.DEFAULT_LOCAL_CACHE_MAX_SIZE;

        @DurationUnit(ChronoUnit.MILLIS)
        private Duration expireAfterAccessMs = Duration.ofMillis(CacheConstants.DEFAULT_EXPIRE_AFTER_ACCESS_MS);
    }

    @Data
    @Configuration
    @ConfigurationProperties("example.cache.dist")
    public static class DistributedCacheConfig {
        private boolean enable = CacheConstants.DEFAULT_DIST_CACHE_ENABLE;

        @DurationUnit(ChronoUnit.SECONDS)
        private Duration ttlSeconds = Duration.ofSeconds(CacheConstants.DEFAULT_DIST_CACHE_TTL_SECONDS);
    }

}
