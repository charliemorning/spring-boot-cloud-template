package org.charlie.example.framework.configs.cache;

import io.lettuce.core.event.connection.ConnectionActivatedEvent;
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Optional;


@Slf4j
@Configuration
@ConditionalOnBean(RedisCacheManager.class)
public class CustomCacheManagerConfig extends CachingConfigurerSupport {

    private LettuceConnectionFactory lettuceConnectionFactory;

    private CustomCompositeCacheManager customCompositeCacheManager;

//    CaffeineCacheManager caffeineCacheManager;

    RedisCacheManager redisCacheManager;

    @Autowired
    public void setLettuceConnectionFactory(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @Autowired
    public void setCustomCompositeCacheManager(CustomCompositeCacheManager customCompositeCacheManager) {
        this.customCompositeCacheManager = customCompositeCacheManager;
    }

    /*@Autowired
    public void setCaffeineCacheManager(CaffeineCacheManager caffeineCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
    }*/

    @Autowired
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

    /*@Bean
    public CacheResolver cacheResolver() {
        return new CustomCacheResolver(caffeineCacheManager, redisCacheManager);
    }*/

    @Bean
    @Primary
    public CustomCompositeCacheManager customCompositeCacheManager() {
        return new CustomCompositeCacheManager(redisCacheManager);
    }

    @PostConstruct
    public void registerListener() {
        LettuceClientConfiguration clientConfiguration = lettuceConnectionFactory.getClientConfiguration();
        Optional<ClientResources> clientResources = clientConfiguration.getClientResources();

        clientResources.ifPresent(resources -> {
            resources.eventBus().get().subscribe(e -> {
                log.info("Caught cache event: {}", e);
                if (e instanceof ConnectionDeactivatedEvent) {
                    log.info("ConnectionDeactivatedEvent");
                    customCompositeCacheManager.deactivateRedisCacheManager();
                } else if (e instanceof ConnectionActivatedEvent) {
                    log.info("ConnectionActivatedEvent");
                    customCompositeCacheManager.activateRedisCacheManager();
                }
            });
        });
    }
}
