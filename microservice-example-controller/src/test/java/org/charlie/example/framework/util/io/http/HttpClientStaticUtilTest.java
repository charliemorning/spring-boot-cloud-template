package org.charlie.example.framework.util.io.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.example.framework.utils.io.http.HttpClientStaticUtil;
import org.charlie.example.framework.utils.io.http.Method;
import org.charlie.example.framework.utils.thread.ThreadStaticUtil;
import org.junit.Test;
import org.junit.platform.commons.util.ExceptionUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;


/**
 * use multi-thread to test HttpClientStaticUtil
 */
@Slf4j
@RunWith(SpringRunner.class) // start with springboot
@SpringBootTest
public class HttpClientStaticUtilTest {

    private final static String MOCK_SERVER_ADDRESS = "http://127.0.0.1:9000";

    private final static int THREAD_NUM = 100;

    private final static int THREAD_SLEEP_TIME = 50; // this value should not be too small, in case of running out of connection


    @Test
    public void postTest() throws JsonProcessingException {

        log.info("start test");
        int threadNum = THREAD_NUM;
        StopWatch watch = new StopWatch();
        watch.start();
        while (threadNum-- > 0) {
            ThreadStaticUtil.execute(() -> {
                while (true) {
                    try {
                        HttpClientStaticUtil.request(MOCK_SERVER_ADDRESS, Method.GET, 1000);
                        Thread.sleep(THREAD_SLEEP_TIME);
                    } catch (JsonProcessingException | InterruptedException e) {
                        log.info(ExceptionUtils.readStackTrace(e));
                    }
                }
            });
        }
        watch.stop();
        log.info("end");
        log.info(watch.prettyPrint());
        for (;;);

    }
}
