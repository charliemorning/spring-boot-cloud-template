package org.charlie.template.common.util.cache;


import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.utils.cache.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


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
