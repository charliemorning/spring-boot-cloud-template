package org.charlie.example.framework.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;


/**
 * To define error handler when cache fail to work, for avoiding disturbing the execution.
 */
@Slf4j
public class CustomCacheErrorHandler extends SimpleCacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        log.error(String.format("Error to get key[%s] from cache[%s]", o, cache.getName()));
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        log.error(String.format("Error to put key[%s] to cache[%s]", o, cache.getName()));
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        log.error(String.format("Error to evict key[%s] from cache[%s]", o, cache.getName()));
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error(String.format("Error to clear cache[%s]", cache.getName()));
        log.error(ExceptionUtils.getMessage(e), e);
    }
}
