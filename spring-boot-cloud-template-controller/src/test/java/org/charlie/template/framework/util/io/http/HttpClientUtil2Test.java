package org.charlie.template.framework.util.io.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.utils.io.http.HttpClientUtil;
import org.charlie.template.framework.utils.io.http.HttpClientUtil2;
import org.charlie.template.framework.utils.io.http.Method;
import org.charlie.template.framework.utils.thread.ThreadUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientUtil2Test {

    @Autowired
    private HttpClientUtil2 httpClientUtil;

    @Test
    public void testHttpClient() {
        log.info("start test");
        int postCount = 5;
        StopWatch watch = new StopWatch();
        watch.start();
        while (postCount-- > 0) {
            ThreadUtil.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            httpClientUtil.request("http://127.0.0.1:9000", Method.GET, 1000);
                            Thread.sleep(50);
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
