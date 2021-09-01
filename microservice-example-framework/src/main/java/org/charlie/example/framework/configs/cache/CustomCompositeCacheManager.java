package org.charlie.example.framework.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

import javax.annotation.Nullable;
import java.util.*;


@Slf4j
public class CustomCompositeCacheManager implements CacheManager, InitializingBean {

    private LinkedList<CacheManager> cacheManagers = new LinkedList<>();

    private CacheManager redisCacheManager;

    private boolean fallbackToNoOpCache = false;

    public CustomCompositeCacheManager() {
    }

    public CustomCompositeCacheManager(CacheManager ... cacheManagers) {
        addCacheManagers(Arrays.asList(cacheManagers));
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        for (CacheManager cacheManager : this.cacheManagers) {
            Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                return cache;
            }
        }
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        Set<String> names = new LinkedHashSet<>();
        for (CacheManager manager : this.cacheManagers) {
            names.addAll(manager.getCacheNames());
        }
        return Collections.unmodifiableSet(names);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.fallbackToNoOpCache) {
            this.cacheManagers.add(new NoOpCacheManager());
        }
    }

    public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
        this.fallbackToNoOpCache = fallbackToNoOpCache;
    }

    public void addCacheManagers(Collection<CacheManager> cacheManagers) {
        this.cacheManagers.addAll(cacheManagers);
        redisCacheManager = this.cacheManagers.get(0);
    }

    public void deactivateRedisCacheManager() {
        if (!cacheManagers.getFirst().equals(redisCacheManager)) {
            log.warn("First cache manager is not redis cache manager");
            return;
        }
        cacheManagers.removeFirst();
    }

    public void activateRedisCacheManager() {
        if (cacheManagers.getFirst().equals(redisCacheManager)) {
            log.warn("First cache manager already is redis cache manager");
            return;
        }
        cacheManagers.addFirst(redisCacheManager);
    }
}