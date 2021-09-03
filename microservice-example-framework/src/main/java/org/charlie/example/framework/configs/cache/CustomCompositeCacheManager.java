package org.charlie.example.framework.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import javax.annotation.Nullable;
import java.util.*;


/**
 * To composite distributed cache and local cache. User configure the cache properties in <class>org.charlie.example.framework.configs.cache.CacheCustomConfig</class>
 * When both distributed cache and local cache are enabled, distributed cache will be hit first if specified cache name exists.
 *
 * @author Charlie
 */
@Slf4j
public class CustomCompositeCacheManager implements CacheManager, InitializingBean {

    /**
     * Distributed cache.
     */
    private CacheManager redisCacheManager;

    /**
     * To record redis status
     */
    private boolean redisAvailable;

    /**
     * Local cache.
     */
    private CacheManager caffeineCacheManager;

    private CacheManager noOpCacheMaanger;

    public CustomCompositeCacheManager() {
    }

    public CustomCompositeCacheManager(RedisCacheManager redisCacheManager, CaffeineCacheManager caffeineCacheManager) {
        this.redisCacheManager = redisCacheManager;
        this.caffeineCacheManager = caffeineCacheManager;
    }

    /**
     * If redis cache is enabled and redis service is available, take the cache from redis caache manager.
     * <p>
     * Then if local cache is enable, take the cache from caffein cache manager.
     * <p>
     * Otherwise, operate nothing.
     *
     * @param name
     * @return
     */
    @Override
    @Nullable
    public Cache getCache(String name) {

        if (redisAvailable && Objects.nonNull(redisCacheManager)) {
            Cache cache = redisCacheManager.getCache(name);
            if (cache != null) {
                log.debug(String.format("Redis cache [%s] hit.", name));
                return cache;
            }
        }

        if (Objects.nonNull(caffeineCacheManager)) {
            Cache cache = caffeineCacheManager.getCache(name);
            if (cache != null) {
                log.debug(String.format("Caffeine cache [%s] hit.", name));
                return cache;
            }
        }

        if (Objects.nonNull(noOpCacheMaanger)) {
            Cache cache = noOpCacheMaanger.getCache(name);
            if (cache != null) {
                log.debug(String.format("No op cache [%s] hit.", name));
                return cache;
            }
        }

        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        Set<String> names = new LinkedHashSet<>();
        if (Objects.nonNull(redisCacheManager)) {
            names.addAll(redisCacheManager.getCacheNames());
        }
        if (Objects.nonNull(caffeineCacheManager)) {
            names.addAll(caffeineCacheManager.getCacheNames());
        }
        if (Objects.nonNull(noOpCacheMaanger)) {
            names.addAll(noOpCacheMaanger.getCacheNames());
        }
        return Collections.unmodifiableSet(names);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        noOpCacheMaanger = new NoOpCacheManager();
    }

    public void deactivateRedisCacheManager() {
        log.debug(String.format("Set redis available status from [%s] to [%s].", redisAvailable, false));
        redisAvailable = false;
    }

    public void activateRedisCacheManager() {
        log.debug(String.format("Set redis available status from [%s] to [%s].", redisAvailable, true));
        redisAvailable = true;
    }
}