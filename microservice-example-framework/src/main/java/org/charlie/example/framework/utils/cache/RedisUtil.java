package org.charlie.example.framework.utils.cache;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.charlie.example.framework.configs.ExampleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * redis cache
 * @author Charlie
 * @version 0.1.0
 * @param <T>
 */
@Component
public class RedisUtil<T> {

    private RedisTemplate<String, T> redisTemplate;

    private ExampleConfig config;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setConfig(ExampleConfig config) {
        this.config = config;
    }

    /**
     * concatenate application-related prefix string to keys in redis.
     * @param key
     * @return
     */
    private String concatKey(String key) {

        Preconditions.checkNotNull(config);
        Preconditions.checkState(StringUtils.isNotEmpty(config.getRedisKeyPrefix()));
        Preconditions.checkNotNull(key);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(config.getRedisKeyPrefix());
        stringBuilder.append(key);
        return stringBuilder.toString();
    }

    /**
     * Set expire to key.
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean expire(String key, long time, TimeUnit timeUnit){

        Preconditions.checkNotNull(key, "Null key is not permitted.");
        Preconditions.checkArgument(time > 0, "Time must be great than 0.");

        try {
            redisTemplate.expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
            return false;
        }
    }

    /**
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getExpire(String key, TimeUnit timeUnit){
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * check if key exists in redis server.
     * @apiNote
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        return redisTemplate.hasKey(concatKey(key));
    }

    /**
     *
     * @param key
     * @return
     */
    public T get(String key) {
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        T obj = (T) valueOperations.get(concatKey(key));
        return obj;
    }

    public void set(String key, T obj) {

        Preconditions.checkNotNull(key, "Null key is not permitted.");
        Preconditions.checkNotNull(obj, "Null value is not permitted.");

        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        String fullKey = concatKey(key);
        operations.set(fullKey, obj);
    }

    public void set(String key, T obj, int n, TimeUnit timeUnit) {
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        Preconditions.checkNotNull(obj, "Null value is not permitted.");
        Preconditions.checkArgument(n > 0, "Time n must be great than 0.");

        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        String fullKey = concatKey(key);
        operations.set(fullKey, obj, n, timeUnit);
    }

    public void update(String key, T obj) {
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        String fullKey = concatKey(key);
        if (redisTemplate.hasKey(fullKey)) {
            set(fullKey, obj);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void delete(String key) {
        Preconditions.checkNotNull(key, "Null key is not permitted.");
        String fullKey = concatKey(key);
        if (redisTemplate.hasKey(fullKey)) {
            redisTemplate.delete(fullKey);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void delete(String ... keys) {
        for (String key: keys) {
            delete(key);
        }
    }

    public boolean transDelete() {
        redisTemplate.multi();



        redisTemplate.exec();
        return false;
    }

}