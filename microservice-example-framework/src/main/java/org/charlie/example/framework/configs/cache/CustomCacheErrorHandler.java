package org.charlie.example.framework.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;

@Slf4j
public class CustomCacheErrorHandler extends SimpleCacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        log.error(ExceptionUtils.getMessage(e), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error(ExceptionUtils.getMessage(e), e);
    }
}
