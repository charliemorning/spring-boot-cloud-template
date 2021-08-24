package org.charlie.example.framework.schedulers;


import lombok.extern.slf4j.Slf4j;
import org.charlie.example.framework.constants.io.http.HttpConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.charlie.example.framework.utils.io.http.HttpClientStaticUtil.stat;


/**
 * To monitor Http Client Connection pool in fixed rate.
 *
 * @author Charlie
 */
@Slf4j
@Component
public class HttpClientConnectionMonitor {
    @Scheduled(fixedRate = HttpConstants.IDLE_CONNECTION_CLOSE_INTERVAL * 1000)
    public void run() {
        log.info(String.format(String.valueOf(stat())));
    }
}
