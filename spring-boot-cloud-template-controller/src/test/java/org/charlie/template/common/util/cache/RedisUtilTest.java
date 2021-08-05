package org.charlie.template.common.util.cache;


import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.utility.cache.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    RedisUtil<String> redisUtils;

    @Test
    public void crudTest() {
        redisUtils.set("a", "1");
        log.info(redisUtils.get("a"));
        redisUtils.set("b", "2");
        log.info(redisUtils.get("b"));

        redisUtils.set("c", "3");
        log.info(redisUtils.get("c"));
    }
}
