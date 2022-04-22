package org.charlie.example.configs.cache;


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
