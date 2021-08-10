package org.charlie.template.common.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.utils.http.HttpClientUtil;
import org.charlie.template.common.utils.http.Method;
import org.charlie.template.common.utils.thread.ThreadUtil;
import org.junit.Test;
import org.springframework.util.StopWatch;


@Slf4j
public class HttpClientUtilityTest {

    @Test
    public void postTest() throws JsonProcessingException {

        log.info("start test");
        int postCount = 100;
        StopWatch watch = new StopWatch();
        watch.start();
        while (postCount-- > 0) {
            ThreadUtil.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            HttpClientUtil.request("http://127.0.0.1:9000", Method.GET, 1000);
                            Thread.sleep(10);
                        } catch (JsonProcessingException | InterruptedException e) {
                            e.printStackTrace();
                        }
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
