package org.charlie.template.framework.schedules;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.charlie.template.framework.utils.io.http.HttpClientUtil.clearIdleConnections;


@EnableScheduling
public class IdleConnectionsClearScheduler {

    @Scheduled(fixedDelay = 1000)
    public void run() {
        clearIdleConnections();
    }
}
