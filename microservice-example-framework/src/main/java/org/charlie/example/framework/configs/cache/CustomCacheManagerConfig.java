package org.charlie.example.framework.configs.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.google.common.collect.Lists;
import io.lettuce.core.event.connection.ConnectionActivatedEvent;
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Configuration
public class CustomCacheManagerConfig extends CachingConfigurerSupport {

    CacheCustomConfig.LocalCacheConfig localCacheConfig;

    CacheCustomConfig.DistributedCacheConfig distributedCacheConfig;

    @Autowired
    LettuceConnectionFactory lettuceConnectionFactory;

    CustomCompositeCacheManager customCompositeCacheManager;

    @Autowired
    public void setLocalCacheConfig(CacheCustomConfig.LocalCacheConfig localCacheConfig) {
        this.localCacheConfig = localCacheConfig;
    }

    @Autowired
    public void setDistributedCacheConfig(CacheCustomConfig.DistributedCacheConfig distributedCacheConfig) {
        this.distributedCacheConfig = distributedCacheConfig;
    }


    public void setLettuceConnectionFactory(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @Autowired
    public void setCustomCompositeCacheManager(CustomCompositeCacheManager customCompositeCacheManager) {
        this.customCompositeCacheManager = customCompositeCacheManager;
    }

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
    private RedisCacheManager redisCacheManager() {
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .build();
    }

    /**
     * To declare caffeine cache as local cache.
     *
     * @return
     */
    private CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(localCacheConfig.getInitSize())
                .maximumSize(localCacheConfig.getMaxSize())
                .expireAfterAccess(localCacheConfig.getExpireAfterAccessMs())
                .weakKeys()
                .recordStats());
        return cacheManager;
    }

    @Bean
    @Primary
    public CustomCompositeCacheManager customCompositeCacheManager() {
        LinkedList<CacheManager> cacheManagers = Lists.newLinkedList();
        if (distributedCacheConfig.isEnable()) {
            cacheManagers.add(redisCacheManager());
        }

        if (localCacheConfig.isEnable()) {
            cacheManagers.add(caffeineCacheManager());
        }
        CacheManager[] cacheManagersArray = new CacheManager[cacheManagers.size()];
        cacheManagers.toArray(cacheManagersArray);
        return new CustomCompositeCacheManager(cacheManagersArray);
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
