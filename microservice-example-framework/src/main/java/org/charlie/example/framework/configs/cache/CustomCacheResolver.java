package org.charlie.example.framework.configs.cache;

import org.charlie.example.framework.constants.cache.CacheConstants;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Collection;


//@Configuration
public class CustomCacheResolver /*implements CacheResolver*/ {

    /*private final CacheManager redisCacheManager;

    private final CacheManager caffeineCacheManager;

    public CustomCacheResolver(CacheManager caffeineCacheManager, CacheManager redisCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
        this.redisCacheManager = redisCacheManager;
    }

//    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Collection<Cache> caches = new ArrayList<Cache>();
        if ("getOrderDetail".equals(context.getMethod().getName())) {
            caches.add(caffeineCacheManager.getCache(CacheConstants.DEFAULT_LOCAL_CACHE));
        } else {
            caches.add(redisCacheManager.getCache(CacheConstants.DEFAULT_DIST_CACHE));
        }
        return caches;
    }*/
}
