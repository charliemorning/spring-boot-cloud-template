package org.charlie.template.common.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.utility.http.HttpClientUtil;
import org.charlie.template.common.utility.http.Method;
import org.charlie.template.common.utility.thread.ThreadUtil;
import org.junit.Test;


@Slf4j
public class HttpClientUtilityTest {

    @Test
    public void postTest() throws JsonProcessingException {

        int postCount = 100000;
        while (postCount-- > 0) {
            ThreadUtil.run(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpClientUtil.request("http://www.baidu.com", Method.GET, 10000);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
