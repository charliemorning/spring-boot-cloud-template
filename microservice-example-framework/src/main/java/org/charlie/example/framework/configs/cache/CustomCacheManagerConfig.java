package org.charlie.example.framework.configs.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.google.common.collect.Lists;
import io.lettuce.core.event.connection.ConnectionActivatedEvent;
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
import org.charlie.example.framework.constants.cache.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Optional;


/**
 *
 */
@Slf4j
@Configuration
public class CustomCacheManagerConfig extends CachingConfigurerSupport {

    @Autowired
    CacheCustomConfig.LocalCacheConfig localCacheConfig;

    @Autowired
    CacheCustomConfig.DistributedCacheConfig distributedCacheConfig;

    @Autowired
    LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    CustomCompositeCacheManager customCompositeCacheManager;

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(distributedCacheConfig.getTtlSeconds())
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * declare redis cache as distributed cache.
     *
     * @return
     */
    @Bean(CacheConstants.REDIS_CACHE_MANAGER)
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .build();
    }

    /**
     * To declare caffeine cache as local cache.
     *
     * @return
     */
    @Bean(CacheConstants.CAFFEINE_CACHE_MANAGER)
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(localCacheConfig.getInitSize())
                .maximumSize(localCacheConfig.getMaxSize())
                .expireAfterAccess(localCacheConfig.getExpireAfterAccessMs())
                .recordStats());
        return cacheManager;
    }

    @Bean("customCompositeCacheManager")
    @Primary
    public CustomCompositeCacheManager customCompositeCacheManager() {
        RedisCacheManager redisCacheManager = null;
        CaffeineCacheManager caffeineCacheManager = null;
        if (distributedCacheConfig.isEnable()) {
            redisCacheManager = redisCacheManager();
        }
        if (localCacheConfig.isEnable()) {
            caffeineCacheManager = caffeineCacheManager();
        }
        return new CustomCompositeCacheManager(redisCacheManager, caffeineCacheManager);
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
