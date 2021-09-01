package org.charlie.example.framework.configs.cache;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CaffeineCacheConfig {

    /*@Bean
    @Primary
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customers", "orders");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(500)
                .weakKeys()
                .recordStats());
        return cacheManager;
    }
*/
}
