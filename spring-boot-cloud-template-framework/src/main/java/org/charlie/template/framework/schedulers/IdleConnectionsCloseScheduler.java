package org.charlie.template.framework.schedulers;


import org.charlie.template.framework.constants.io.http.HttpConstants;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.charlie.template.framework.utils.io.http.HttpClientUtil.clearIdleAndExpiredConnections;


@Component
public class IdleConnectionsCloseScheduler {

    @Scheduled(fixedRate = HttpConstants.IDEL_CONNECTION_CLOSE_INTERVAL * 1000)
    public void run() {
        clearIdleAndExpiredConnections();
    }
}
