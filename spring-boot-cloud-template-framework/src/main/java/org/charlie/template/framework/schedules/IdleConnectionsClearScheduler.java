package org.charlie.template.framework.schedules;


import org.charlie.template.framework.constants.TemplateConstants;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.charlie.template.framework.utils.io.http.HttpClientUtil.clearIdleConnections;


@EnableScheduling
public class IdleConnectionsClearScheduler {
    @Scheduled(fixedDelay = TemplateConstants.CONNECTION_CLOSE_INTERVAL * 1000)
    public void run() {
        clearIdleConnections();
    }
}
