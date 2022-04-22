package org.charlie.example.common.constants.cache;

public class CacheConstants {

    public final static String  REDIS_CACHE_MANAGER = "redisCacheManager";

    public final static String  CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    public final static String DEFAULT_LOCAL_CACHE = "local-cache";

    public final static String DEFAULT_DIST_CACHE = "dist-cache";

    public final static boolean DEFAULT_LOCAL_CACHE_ENABLE = false;

    public final static int DEFAULT_LOCAL_CACHE_INIT_SIZE = 200;

    public final static int DEFAULT_LOCAL_CACHE_MAX_SIZE = 1000;

    public final static int DEFAULT_EXPIRE_AFTER_ACCESS_MS = 60000;


    public final static boolean DEFAULT_DIST_CACHE_ENABLE = false;

    public final static int DEFAULT_DIST_CACHE_TTL_SECONDS = 60;

}
