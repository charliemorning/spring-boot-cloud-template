package org.charlie.template.common.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.utility.http.HttpClientUtil;
import org.charlie.template.common.utility.http.Method;
import org.charlie.template.common.utility.thread.ThreadUtil;
import org.junit.Test;
import org.springframework.util.StopWatch;


@Slf4j
public class HttpClientUtilityTest {

    @Test
    public void postTest() throws JsonProcessingException {

        log.info("start test");
        int postCount = 100000;
        StopWatch watch = new StopWatch();
        watch.start();
        while (postCount-- > 0) {
            ThreadUtil.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info(HttpClientUtil.request("http://127.0.0.1:9000", Method.GET, 10000));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
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
